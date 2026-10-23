package es.unican.ps.hoteles.common.businessLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Hotel;

public interface IBusquedaHoteles {

    public Hotel buscarHotelPorNombre(String nombre);
    public List<Hotel> buscarHotelPorLocalidad(String localidad);
}
