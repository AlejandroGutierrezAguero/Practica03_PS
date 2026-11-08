package es.unican.ps.hoteles.common.businessLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Habitacion;
import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;

/**
 * Interfaz que define los métodos para la gestión de hoteles.
 */
public interface IGestionHoteles {

    /**
     * Busca las reservas de un hotel indicado.
     * 
     * @param idHotel Id del hotel cuyas reservas se quieren consultar.
     * @return Lista de reservas del hotel especificado ordenadas por fecha de entrada, null si no existe el hotel.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public List<Reserva> consultarReservasHotel(int idHotel) throws DataAccessException;

    /**
     * Modifica las habitaciones de un hotel indicado.
     * 
     * @param idHotel Id del hotel cuyas habitaciones se quieren modificar.
     * @param habitaciones Lista con las habitaciones a modificar del hotel.
     * @return Lista con las habitaciones modificadas del hotel, null si no existe el hotel.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public List<Habitacion> modificarHabitaciones(int idHotel, List<Habitacion> habitaciones) throws DataAccessException;
    
    /**
     * Añade un tipo de habitación a un hotel indicado.
     * 
     * @param idHotel Id del hotel al que se desea añadir el tipo de habitación.
     * @param habitacion Tipo de habitación que se desea añadir al hotel.
     * @return Habitacion añadida al hotel, null si no existe el hotel o si ya existe la habitación en el hotel.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Habitacion anhadirTipoDeHabitacionEnHotel(int idHotel, Habitacion habitacion) throws DataAccessException;

    /**
     * Elimina un tipo de habitación de un hotel indicado.
     * 
     * @param idHotel Id del hotel del que se desea eliminar el tipo de habitación.
     * @param idHabitacion Id del tipo de habitación que se desea eliminar del hotel.
     * @return Tipo de habitacion eliminada del hotel, null si no existe el hotel o la habitación.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Habitacion eliminarTipoDeHabitacionEnHotel(int idHotel, int idHabitacion) throws DataAccessException;
}
