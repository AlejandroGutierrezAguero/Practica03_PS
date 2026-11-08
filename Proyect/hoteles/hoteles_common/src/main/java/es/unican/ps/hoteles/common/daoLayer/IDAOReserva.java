package es.unican.ps.hoteles.common.daoLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;

public interface IDAOReserva {
    
    public Reserva creaReserva(Reserva r) throws DataAccessException;
    public List<Reserva> reservas() throws DataAccessException;
    public Reserva reserva(int idReserva) throws DataAccessException;
    public boolean modificaReserva(Reserva r) throws DataAccessException;
    public Reserva eliminaReserva(int idReserva) throws DataAccessException;
}
