package es.unican.ps.hoteles.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import es.unican.ps.hoteles.common.entities.Cliente;
import es.unican.ps.hoteles.common.entities.Pago;
import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.entities.TipoTarjeta;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class DAOReservaTest {

    @InjectMocks
    private DAOReserva sut;

    @Mock
    private EntityManager mockEntityManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void creaReservaTest_1a() throws ParametrosInvalidosException, DataAccessException {
        
        Cliente cliente = new Cliente("11111111A", "Juan", "Pérez", "López", "pepelopez@gmail.com");
        Pago pago = new Pago("1111222233334444", "123", 10, 2030, TipoTarjeta.MASTERCARD);
        Reserva nuevaReserva = new Reserva(LocalDate.now().plusDays(2), LocalDate.now().plusDays(6), cliente, pago);

        doAnswer(new Answer<>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Reserva r = invocation.getArgument(0);
                r.setIdReserva(1);
                return null;
            }
        }).when(mockEntityManager).persist(nuevaReserva);

        Reserva result = sut.creaReserva(nuevaReserva);
        assertSame(nuevaReserva, result);
        assertEquals(1, result.getIdReserva());
        verify(mockEntityManager).persist(nuevaReserva);
    }

    @Test
    public void creaReservaTest_1b() throws ParametrosInvalidosException {
        
        Cliente cliente = new Cliente("11111111A", "Juan", "Pérez", "López", "pepelopez@gmail.com");
        Pago pago = new Pago("1111222233334444", "123", 10, 2030, TipoTarjeta.MASTERCARD);
        Reserva nuevaReserva = new Reserva(LocalDate.now().plusDays(2), LocalDate.now().plusDays(6), cliente, pago);

        doThrow(new EntityExistsException()).when(mockEntityManager).persist(nuevaReserva);
        assertThrows(DataAccessException.class, () -> sut.creaReserva(nuevaReserva));
        verify(mockEntityManager).persist(nuevaReserva);
    }

    @Test
    public void creaReservaTest_1c() throws ParametrosInvalidosException {
        
        Cliente cliente = new Cliente("11111111A", "Juan", "Pérez", "López", "pepelopez@gmail.com");
        Pago pago = new Pago("1111222233334444", "123", 10, 2030, TipoTarjeta.MASTERCARD);
        Reserva nuevaReserva = new Reserva(LocalDate.now().plusDays(2), LocalDate.now().plusDays(6), cliente, pago);

        doThrow(new PersistenceException()).when(mockEntityManager).persist(any(Reserva.class));
        assertThrows(DataAccessException.class, () -> sut.creaReserva(nuevaReserva));
        verify(mockEntityManager).persist(nuevaReserva);
    }
}