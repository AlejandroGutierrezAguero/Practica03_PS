package es.unican.ps.hoteles.common.daoLayer;

import java.util.List;

import es.unican.ps.hoteles.common.entities.Reserva;

public interface IDAOReserva {
    
    public Reserva creaReserva(Reserva r);
    public List<Reserva> reservas();
    public Reserva reserva(int idReserva);
    public boolean modificaReserva(Reserva r);
    public Reserva eliminaReserva(int idReserva);
}
