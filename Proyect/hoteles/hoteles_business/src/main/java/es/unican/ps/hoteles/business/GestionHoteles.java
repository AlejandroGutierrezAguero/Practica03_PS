package es.unican.ps.hoteles.business;

import java.util.List;

import es.unican.ps.hoteles.common.businessLayer.IBusquedaHotelesLocal;
import es.unican.ps.hoteles.common.businessLayer.IBusquedaHotelesRemota;
import es.unican.ps.hoteles.common.businessLayer.IGestionHotelesLocal;
import es.unican.ps.hoteles.common.businessLayer.IGestionHotelesRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOHotelRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOReservaRemota;
import es.unican.ps.hoteles.common.entities.Habitacion;
import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.entities.Reserva;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class GestionHoteles implements IBusquedaHotelesLocal, IBusquedaHotelesRemota, IGestionHotelesLocal, IGestionHotelesRemota {

    @EJB
    private IDAOReservaRemota reservaDAO;

    @EJB
    private IDAOHotelRemota hotelDAO;

    @Override
    public Hotel buscarHotelPorNombre(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarHotelPorNombre'");
    }

    @Override
    public List<Hotel> buscarHotelPorLocalidad(String localidad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarHotelPorLocalidad'");
    }

    @Override
    public Hotel consultarHotel(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarHotel'");
    }

    @Override
    public List<Reserva> consultarReservasHotel(int idHotel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarReservasHotel'");
    }

    @Override
    public List<Habitacion> modificaeHabitaciones(int idHotel, List<Habitacion> habitaciones) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificaeHabitaciones'");
    }

    @Override
    public Habitacion anhadirTipoDeHabitacionEnHotel(int idHotel, Habitacion habitacion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'anhadirTipoDeHabitacionEnHotel'");
    }

    @Override
    public Habitacion eliminarTipoDeHabitacionEnHotel(int idHotel, int idHabitacion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarTipoDeHabitacionEnHotel'");
    }

}
