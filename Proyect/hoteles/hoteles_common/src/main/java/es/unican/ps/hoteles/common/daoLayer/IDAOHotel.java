package es.unican.ps.hoteles.common.daoLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;

/**
 * Interfaz que define los métodos de la dao de hoteles.
 */
public interface IDAOHotel {

    /**
     * Crea un hotel en la base de datos.
     * 
     * @param h El hotel a crear.
     * @return El hotel creado con su ID asignado.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Hotel creaHotel(Hotel h) throws DataAccessException;

    /**
     * Obtiene todos los hoteles de la base de datos.
     * 
     * @return Una lista con todos los hoteles.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public List<Hotel> hoteles() throws DataAccessException;

    /**
     * Obtiene un hotel por su ID.
     * 
     * @param idHotel El ID del hotel a obtener.
     * @return El hotel correspondiente al ID proporcionado, o null si no se encuentra.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Hotel hotel(int idHotel) throws DataAccessException;

    /**
     * Obtiene un hotel por su nombre.
     * 
     * @param nombre El nombre del hotel a obtener.
     * @return El hotel correspondiente al nombre proporcionado, o null si no se encuentra.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Hotel hotelPorNombre(String nombre) throws DataAccessException;

    /**
     * Modifica un hotel existente en la base de datos.
     * 
     * @param h El hotel a modificar con los datos actualizados.
     * @return true si la modificación fue exitosa, false en caso contrario.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public boolean modificaHotel(Hotel h) throws DataAccessException;

    /**
     * Elimina un hotel de la base de datos por su ID.
     * 
     * @param idHotel El ID del hotel a eliminar.
     * @return El hotel eliminado, o null si no se encontró.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Hotel eliminaHotel(int idHotel) throws DataAccessException;
}
