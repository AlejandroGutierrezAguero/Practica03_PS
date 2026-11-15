package es.unican.ps.hoteles.dao;

import java.util.List;

import es.unican.ps.hoteles.common.daoLayer.IDAOHotelLocal;
import es.unican.ps.hoteles.common.daoLayer.IDAOHotelRemota;
import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

@Stateless
public class DAOHotel implements IDAOHotelLocal, IDAOHotelRemota {

    @PersistenceContext(unitName = "hotelesPU")
    private EntityManager em;

    @Override
    public Hotel creaHotel(Hotel h) throws DataAccessException {
        try {
            em.persist(h);
            return h;
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public List<Hotel> hoteles() throws DataAccessException {
        try {
            TypedQuery<Hotel> q = em.createNamedQuery("hoteles", Hotel.class);
            return q.getResultList();
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public Hotel hotel(int idHotel) throws DataAccessException {
        try {
            return em.find(Hotel.class, idHotel);
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public Hotel hotelPorNombre(String nombre) throws DataAccessException {
        try {
            TypedQuery<Hotel> q = em.createNamedQuery("hotelPorNombre", Hotel.class);
            q.setParameter("nomb", nombre);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public boolean modificaHotel(Hotel h) throws DataAccessException {
        try {
            Hotel exists = em.find(Hotel.class, h.getIdHotel());
            if (exists == null) {
                return false;
            }
            em.merge(h);
            return true;
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new DataAccessException();
        }
    }

    @Override
    public Hotel eliminaHotel(int idHotel) throws DataAccessException {
        try {
            Hotel removed = em.find(Hotel.class, idHotel);
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