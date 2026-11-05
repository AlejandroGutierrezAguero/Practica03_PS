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
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class GestionReservas implements IReservasPersonalLocal, IReservasPersonalRemota {

    @EJB
    private IDAOReservaRemota reservaDAO;
    
    @EJB
    private IDAOHotelRemota hotelDAO;

    @Override
    public Reserva consultarReserva(int idReserva, String dni) {
        Reserva reserva = reservaDAO.reserva(idReserva);
        if (reserva == null || !reserva.getCliente().getDni().equals(dni)) {
            return null;
        }
        return reserva;
    }

    @Override
    public Reserva cancelarReserva(int idReserva) {
        Reserva reserva = reservaDAO.reserva(idReserva);
        if (reserva == null || reserva.getFechaEntrada().isBefore(LocalDate.now().plusDays(2))) {
            return null;
        }
        return reservaDAO.eliminaReserva(idReserva);
    }

    @Override
    public List<SolicitudHabitacion> consultarHabitacionesHotel(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida) {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return null;
        }

        Map<Habitacion, Integer> disponibilidad = consultaDisponibilidad(hotel, fechaEntrada, fechaSalida);

        List<SolicitudHabitacion> habitacionesDisponibles = new ArrayList<>();
        for (Map.Entry<Habitacion, Integer> entry : disponibilidad.entrySet()) {
            if (entry.getValue() > 0) {
                habitacionesDisponibles.add(new SolicitudHabitacion(entry.getKey(), entry.getValue()));
            }
        }

        return habitacionesDisponibles;
    }

    @Override
    public double solictarHabitaciones(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida, List<SolicitudHabitacion> solicitudes) {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return -1;
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
    public Reserva reservar(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida, List<SolicitudHabitacion> solicitudes, Cliente cliente, Pago pago) {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return null;
        }

        double precio = solictarHabitaciones(idHotel, fechaEntrada, fechaSalida, solicitudes);
        if (precio == -1) {
            return null;
        }

        Reserva reserva = new Reserva(0, fechaEntrada, fechaSalida, cliente, pago);
        for (SolicitudHabitacion s : solicitudes) {
            reserva.getReservasTipo().add(new ReservaTipoHabitacion(0, s.getCantidad(), s.getHabitacion().getPrecioPorNoche(), s.getHabitacion()));
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
    private static Map<Habitacion, Integer> consultaDisponibilidad (Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida) {
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
    
}
