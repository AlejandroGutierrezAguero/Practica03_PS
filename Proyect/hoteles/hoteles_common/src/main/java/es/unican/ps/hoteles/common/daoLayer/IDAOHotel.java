package es.unican.ps.hoteles.common.daoLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Hotel;

public interface IDAOHotel {

    public Hotel creaHotel(Hotel h);
    public List<Hotel> hoteles();
    public Hotel hotel(int idHotel);
    public boolean modificaHotel(Hotel h);
    public Hotel eliminaHotel(int idHotel);
}
