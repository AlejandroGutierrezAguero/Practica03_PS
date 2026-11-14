package es.unican.ps.hoteles.common.daoLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;

/**
 * Interfaz que define los métodos de la dao de reservas.
 */
public interface IDAOReserva {
    
    /**
     * Crea una reserva en la base de datos.
     * 
     * @param r La reserva a crear.
     * @return La reserva creada con su ID asignado.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Reserva creaReserva(Reserva r) throws DataAccessException;

    /**
     * Obtiene todas las reservas de la base de datos.
     * 
     * @return Una lista con todas las reservas.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public List<Reserva> reservas() throws DataAccessException;

    /**
     * Obtiene una reserva por su ID.
     * 
     * @param idReserva El ID de la reserva a obtener.
     * @return La reserva correspondiente al ID proporcionado, o null si no se encuentra.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Reserva reserva(int idReserva) throws DataAccessException;

    /**
     * Modifica una reserva existente en la base de datos.
     * 
     * @param r La reserva a modificarcon los datos actualizados.
     * @return true si la modificación fue exitosa, false en caso contrario.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public boolean modificaReserva(Reserva r) throws DataAccessException;

    /**
     * Elimina una reserva de la base de datos por su ID.
     * 
     * @param idReserva El ID de la reserva a eliminar.
     * @return La reserva eliminada, o null si no se encontró.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Reserva eliminaReserva(int idReserva) throws DataAccessException;
}
