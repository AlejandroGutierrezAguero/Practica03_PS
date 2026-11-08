package es.unican.ps.hoteles.common.businessLayer;

import java.time.LocalDate;
import java.util.List;

import es.unican.ps.hoteles.common.entities.Cliente;
import es.unican.ps.hoteles.common.entities.Pago;
import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.entities.SolicitudHabitacion;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
import es.unican.ps.hoteles.common.exceptions.FechasInvalidasException;
import es.unican.ps.hoteles.common.exceptions.LimiteCancelacionExcedidoException;
import es.unican.ps.hoteles.common.exceptions.ReservaNoCorrespondeConDNIException;

/**
 * Interfaz que define los métodos para la gestión de las reservas personales.
 */
public interface IReservasPersonal {

    /**
     * Consulta una reserva por su ID y el DNI del cliente.
     * 
     * @param idReserva ID de la reserva a consultar.
     * @param dni DNI del cliente que realizó la reserva.
     * @return Reserva con el ID y DNI indicados, null si no existe la reserva.
     * @throws ReservaNoCorrespondeConDNIException Si la reserva no se corresponde con el DNI indicado.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Reserva consultarReserva(int idReserva, String dni) throws DataAccessException, ReservaNoCorrespondeConDNIException;

    /**
     * Cancela una reserva por su ID si quedan más de dos días para la fecha de entrada.
     * 
     * @param idReserva ID de la reserva a cancelar.
     * @return Reserva cancelada, null si la reserva no existe.
     * @throws LimiteCancelacionExcedidoException Si quedan menos de 2 días para la fecha de entrada.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Reserva cancelarReserva(int idReserva) throws DataAccessException, LimiteCancelacionExcedidoException;

    /**
     * Consulta las habitaciones disponibles en un hotel para un periodo determinado.
     * 
     * @param idHotel ID del hotel del que se quieren consultar las habitaciones.
     * @param fechaEntrada Fecha de entrada.
     * @param fechaSalida Fecha de salida.
     * @return Lista de habitaciones disponibles y su disponibilidad en el hotel para el periodo indicado, null si no existe el hotel.
     * @throws FechasInvalidasException Si las fechas introducidas no son válidas.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public List<SolicitudHabitacion> consultarHabitacionesHotel(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida) throws DataAccessException, FechasInvalidasException;

    /**
     * Consulta si hay disponibilidad de las habitaciones indicadas para una reserva en un hotel y para un periodo determinado.
     * 
     * @param idHotel ID del hotel en el que se desea realizar la reserva.
     * @param fechaEntrada Fecha de entrada.
     * @param fechaSalida Fecha de salida.
     * @param solicitudes Lista con las solicitudes de habitaciones a reservar. Tipos de habitación y número de cada uno a reservar.
     * @return Precio total de la reserva, o -1 si no hay disponibibilidad para todas las habitaciones solicitadas en el hotel y periodo indicado, o el hotel no existe.
     * @throws FechasInvalidasException Si las fechas introducidas no son válidas.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public double solictarHabitaciones(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida, List<SolicitudHabitacion> solicitudes) throws DataAccessException, FechasInvalidasException;

    /**
     * Realiza una reserva en un hotel para un periodo determinado.
     * 
     * @param idHotel ID del hotel en el que se desea realizar la reserva.
     * @param fechaEntrada Fecha de entrada.
     * @param fechaSalida Fecha de salida.
     * @param solicitudes Lista con las solicitudes de habitaciones a reservar. Tipos de habitación y número de cada uno a reservar.
     * @param cliente Cliente que realiza la reserva.
     * @param pago Pago asociado a la reserva.
     * @return Reserva realizada, o null si no se puede realizar la reserva.
     * @throws FechasInvalidasException Si las fechas introducidas no son válidas.
     * @throws DataAccessException Si se produce un error en el acceso a la base de datos.
     */
    public Reserva reservar(int idHotel, LocalDate fechaEntrada, LocalDate fechaSalida, List<SolicitudHabitacion> solicitudes, Cliente cliente, Pago pago) throws DataAccessException, FechasInvalidasException;
}
