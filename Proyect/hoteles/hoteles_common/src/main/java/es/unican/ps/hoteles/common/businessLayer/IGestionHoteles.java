package es.unican.ps.hoteles.common.businessLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Habitacion;
import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.entities.Reserva;

public interface IGestionHoteles {

    public Hotel consultarHotel (String nombre);
    public List<Reserva> consultarReservasHotel(int idHotel);
    public List<Habitacion> modificaeHabitaciones(int idHotel, List<Habitacion> habitaciones);
    public Habitacion anhadirTipoDeHabitacionEnHotel(int idHotel, Habitacion habitacion);
    public Habitacion eliminarTipoDeHabitacionEnHotel(int idHotel, int idHabitacion);
}
