package es.unican.ps.hoteles.common.businessLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;

/**
 * Interfaz que define los métodos para la búsqueda de hoteles.
 */
public interface IBusquedaHoteles {

    /**
     * Busca un hotel por su nombre.
     * 
     * @param nombre Nombre del hotel a buscar.
     * @return El hotel encontrado o null si no se encuentra.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Hotel buscarHotelPorNombre(String nombre) throws DataAccessException;

    /**
     * Busca hoteles por su localidad.
     * 
     * @param localidad Localidad donde se encuentran los hoteles a buscar.
     * @return Lista de hoteles encontrados en la localidad especificada.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public List<Hotel> buscarHotelPorLocalidad(String localidad) throws DataAccessException;
}
