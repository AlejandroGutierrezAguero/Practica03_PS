package es.unican.ps.hoteles.business;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.unican.ps.hoteles.common.businessLayer.IReservasPersonalLocal;
import es.unican.ps.hoteles.common.businessLayer.IReservasPersonalRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOHotelRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOReservaRemota;
import es.unican.ps.hoteles.common.entities.Cliente;
import es.unican.ps.hoteles.common.entities.Habitacion;
import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.entities.Pago;
import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.entities.ReservaTipoHabitacion;
import es.unican.ps.hoteles.common.entities.SolicitudHabitacion;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
import es.unican.ps.hoteles.common.exceptions.FechasInvalidasException;
import es.unican.ps.hoteles.common.exceptions.LimiteCancelacionExcedidoException;
import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;
import es.unican.ps.hoteles.common.exceptions.ReservaNoCorrespondeConDNIException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class GestionReservas implements IReservasPersonalLocal, IReservasPersonalRemota {

    @EJB
    private IDAOReservaRemota reservaDAO;
    
    @EJB
    private IDAOHotelRemota hotelDAO;

    @Override
    public Reserva consultarReserva(int idReserva, String dni) throws DataAccessException, ReservaNoCorrespondeConDNIException {
        Reserva reserva = reservaDAO.reserva(idReserva);
        if (reserva != null && !reserva.getCliente().getDni().equals(dni)) {
            throw new ReservaNoCorrespondeConDNIException();
        }
        return reserva;
    }

    @Override
    public Reserva cancelarReserva(int idReserva) throws DataAccessException, LimiteCancelacionExcedidoException {
        Reserva reserva = reservaDAO.reserva(idReserva);
        if (reserva == null) {
            return null;
        }
        if (reserva.getFechaEntrada().isBefore(LocalDate.now().plusDays(2))) {
            throw new LimiteCancelacionExcedidoException();
        }
        return reservaDAO.eliminaReserva(idReserva);
    }

    @Override
    public List<SolicitudHabitacion> consultarHabitacionesHotel(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida) throws DataAccessException, FechasInvalidasException {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return null;
        }

        if (!fechasValidas(fechaEntrada, fechaSalida)) {
            throw new FechasInvalidasException();
        }

        Map<Habitacion, Integer> disponibilidad = consultaDisponibilidad(hotel, fechaEntrada, fechaSalida);

        List<SolicitudHabitacion> habitacionesDisponibles = new ArrayList<>();
        for (Map.Entry<Habitacion, Integer> entry : disponibilidad.entrySet()) {
            if (entry.getValue() > 0) {
                try {
                    habitacionesDisponibles.add(new SolicitudHabitacion(entry.getKey(), entry.getValue()));
                } catch (ParametrosInvalidosException e) {
                    e.printStackTrace();
                }
            }
        }

        return habitacionesDisponibles;
    }

    @Override
    public double solictarHabitaciones(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida, List<SolicitudHabitacion> solicitudes) throws DataAccessException, FechasInvalidasException {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return -1;
        }

        if (!fechasValidas(fechaEntrada, fechaSalida)) {
            throw new FechasInvalidasException();
        }

        long numeroNoches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        double precio = 0;
        Map<Habitacion, Integer> disponibilidad = consultaDisponibilidad(hotel, fechaEntrada, fechaSalida);

        for (SolicitudHabitacion sh : solicitudes) {
            if (disponibilidad.getOrDefault(sh.getHabitacion(), 0) >= sh.getCantidad()) {
                precio += sh.getHabitacion().getPrecioPorNoche() * sh.getCantidad() * numeroNoches;
            } else {
                return -1;
            }
        }

        return precio;
    }

    @Override
    public Reserva reservar(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida, List<SolicitudHabitacion> solicitudes, Cliente cliente, Pago pago) throws DataAccessException, FechasInvalidasException {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return null;
        }

        if (!fechasValidas(fechaEntrada, fechaSalida)) {
            throw new FechasInvalidasException();
        }

        double precio = solictarHabitaciones(idHotel, fechaEntrada, fechaSalida, solicitudes);
        if (precio == -1) {
            return null;
        }

        Reserva reserva = null;
        try {
            reserva = new Reserva(fechaEntrada, fechaSalida, cliente, pago);
            for (SolicitudHabitacion s : solicitudes) {
                reserva.getReservasTipo().add(new ReservaTipoHabitacion(s.getCantidad(), s.getHabitacion().getPrecioPorNoche(), s.getHabitacion()));
            }
        } catch (ParametrosInvalidosException e) {
            e.printStackTrace();
        }
        
        hotel.getReservas().add(reserva);
        hotelDAO.modificaHotel(hotel);
        return reservaDAO.creaReserva(reserva);
    }

    /**
     * Consulta la disponibilidad de habitaciones en un hotel y rango de fechas dados.
     * 
     * @param hotel Hotel en el que se desea consultar la disponibilidad.
     * @param fechaEntrada Fecha de entrada.
     * @param fechaSalida Fecha de salida.
     * @return Las habitaciones disponibles en el periodo y hotel con la cantidad.
     */
    private static Map<Habitacion, Integer> consultaDisponibilidad(Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida) {
        Map<Habitacion, Integer> disponibilidad = new HashMap<>();

        for (Habitacion h : hotel.getHabitaciones()) {
            disponibilidad.put(h, h.getDisponibles());
        }
        
        for (Reserva r : hotel.getReservas()) {
            if (!(r.getFechaSalida().isBefore(fechaEntrada) || fechaSalida.isBefore(r.getFechaEntrada()))) {
                for (ReservaTipoHabitacion rt : r.getReservasTipo()) {
                    disponibilidad.replace(rt.getTipoHabitacion(), disponibilidad.get(rt.getTipoHabitacion()) - rt.getNumHabitaciones());
                }
            }
        }

        return disponibilidad;
    }

    /**
     * Comprueba si una pareja de fechas de entrada y salida es válida.
     * Las fechas serán válidas si la fecha de entrada es anterior a la de salida, y posterior o igual a la actual.
     * 
     * @param fechaEntrada Fecha de entrada.
     * @param fechaSalida Fecha de salida.
     * @return ture si las fechas son válidas, false en caso contrario.
     */
    private static boolean fechasValidas(LocalDate fechaEntrada, LocalDate fechaSalida) {
        return !fechaEntrada.isBefore(LocalDate.now()) && fechaEntrada.isBefore(fechaSalida);
    }
    
}
