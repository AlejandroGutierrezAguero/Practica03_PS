package es.unican.ps.hoteles.common.businessLayer;

import java.time.LocalDate;
import java.util.List;

import es.unican.ps.hoteles.common.entities.Cliente;
import es.unican.ps.hoteles.common.entities.Habitacion;
import es.unican.ps.hoteles.common.entities.Pago;
import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.entities.SolicitudHabitacion;

public interface IReservasPersonal {

    public Reserva consultarReserva(int idReserva, String dni);
    public Reserva cancelarReserva(int idReserva);
    public List<Habitacion> consultarHabitacionesHotel(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida);
    public double solictarHabitaciones(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida, List<SolicitudHabitacion> solicitudes);
    public Reserva reservar(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida, List<SolicitudHabitacion> solicitudes, Cliente cliente, Pago pago);
}
