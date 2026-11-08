package es.unican.ps.hoteles.common.daoLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;

public interface IDAOHotel {

    public Hotel creaHotel(Hotel h) throws DataAccessException;
    public List<Hotel> hoteles() throws DataAccessException;
    public Hotel hotel(int idHotel) throws DataAccessException;
    public Hotel hotelPorNombre(String nombre) throws DataAccessException;
    public boolean modificaHotel(Hotel h) throws DataAccessException;
    public Hotel eliminaHotel(int idHotel) throws DataAccessException;
}
