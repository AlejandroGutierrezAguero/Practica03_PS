package es.unican.ps.hoteles.business;

import java.time.LocalDate;
import java.util.List;

import es.unican.ps.hoteles.common.businessLayer.IReservasPersonalLocal;
import es.unican.ps.hoteles.common.businessLayer.IReservasPersonalRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOHotelRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOReservaRemota;
import es.unican.ps.hoteles.common.entities.Cliente;
import es.unican.ps.hoteles.common.entities.Habitacion;
import es.unican.ps.hoteles.common.entities.Pago;
import es.unican.ps.hoteles.common.entities.Reserva;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarReserva'");
    }

    @Override
    public Reserva cancelarReserva(int idReserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelarReserva'");
    }

    @Override
    public List<Habitacion> consultarHabitacionesHotel(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarHabitacionesHotel'");
    }

    @Override
    public double solictarHabitaciones(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida,
            List<SolicitudHabitacion> solicitudes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solictarHabitaciones'");
    }

    @Override
    public Reserva reservar(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida,
            List<SolicitudHabitacion> solicitudes, Cliente cliente, Pago pago) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reservar'");
    }

}
