package es.unican.ps.hoteles.business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import es.unican.ps.hoteles.common.businessLayer.IBusquedaHotelesLocal;
import es.unican.ps.hoteles.common.businessLayer.IBusquedaHotelesRemota;
import es.unican.ps.hoteles.common.businessLayer.IGestionHotelesLocal;
import es.unican.ps.hoteles.common.businessLayer.IGestionHotelesRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOHotelRemota;
import es.unican.ps.hoteles.common.entities.Habitacion;
import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class GestionHoteles implements IBusquedaHotelesLocal, IBusquedaHotelesRemota, IGestionHotelesLocal, IGestionHotelesRemota {

    @EJB
    private IDAOHotelRemota hotelDAO;

    @Override
    public Hotel buscarHotelPorNombre(String nombre) throws DataAccessException {
        return hotelDAO.hotelPorNombre(nombre);
    }

    @Override
    public List<Hotel> buscarHotelPorLocalidad(String localidad) throws DataAccessException {
        List<Hotel> hoteles = hotelDAO.hoteles();
        List<Hotel> result = new ArrayList<>();
        for (Hotel h : hoteles) {
            if (h.getLocalidad().equals(localidad)) {
                result.add(h);
            }
        }
        return result;
    }

    @Override
    public List<Reserva> consultarReservasHotel(int idHotel) throws DataAccessException {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return null;
        }
        List<Reserva> reservas = hotel.getReservas();
        reservas.sort(Comparator.comparing(Reserva::getFechaEntrada));
        return reservas;
    }

    @Override
    public List<Habitacion> modificarHabitaciones(int idHotel, List<Habitacion> habitaciones) throws DataAccessException {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return null;
        }
        List<Habitacion> habitacionesHotel = hotel.getHabitaciones();
        List<Habitacion> habitacionesModificadas = new ArrayList<>();
        for (Habitacion h : habitaciones) {
            if (habitacionesHotel.contains(h)) {
                habitacionesHotel.remove(h);
                habitacionesHotel.add(h);
                habitacionesModificadas.add(h);
            }
        }
        hotelDAO.modificaHotel(hotel);
        return habitacionesModificadas;
    }

    @Override
    public Habitacion anhadirTipoDeHabitacionEnHotel(int idHotel, Habitacion habitacion) throws DataAccessException {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null || hotel.getHabitaciones().contains(habitacion)) {
            return null;
        }
        hotel.getHabitaciones().add(habitacion);
        hotelDAO.modificaHotel(hotel);
        return habitacion;
    }

    @Override
    public Habitacion eliminarTipoDeHabitacionEnHotel(int idHotel, int idHabitacion) throws DataAccessException {
        Hotel hotel = hotelDAO.hotel(idHotel);
        if (hotel == null) {
            return null;
        }
        List<Habitacion> habitaciones = hotel.getHabitaciones();
        for (Habitacion h : habitaciones) {
            if (h.getIdHabitacion() == idHabitacion) {
                habitaciones.remove(h);
                hotelDAO.modificaHotel(hotel);
                return h;
            }
        }
        return null;
    }

}
