package es.unican.ps.hoteles.dao;

import java.util.List;

import es.unican.ps.hoteles.common.daoLayer.IDAOReservaLocal;
import es.unican.ps.hoteles.common.daoLayer.IDAOReservaRemota;
import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

@Stateless
public class DAOReserva implements IDAOReservaLocal, IDAOReservaRemota {

    @PersistenceContext(unitName = "")
    private EntityManager em;

    @Override
    public Reserva creaReserva(Reserva r) throws DataAccessException {
        try {
            em.persist(r);
            return r;
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public List<Reserva> reservas() throws DataAccessException {
        try {
            TypedQuery<Reserva> q = em.createNamedQuery("reservas", Reserva.class);
            return q.getResultList();
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public Reserva reserva(int idReserva) throws DataAccessException {
        try {
            return em.find(Reserva.class, idReserva);
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public boolean modificaReserva(Reserva r) throws DataAccessException {
        try {
            Reserva exists = em.find(Reserva.class, r.getIdReserva());
            if (exists == null) {
                return false;
            }
            em.merge(r);
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public Reserva eliminaReserva(int idReserva) throws DataAccessException {
        try {
            Reserva removed = em.find(Reserva.class, idReserva);
            if (removed == null) {
                return null;
            }
            em.remove(removed);
            return removed;
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }
}