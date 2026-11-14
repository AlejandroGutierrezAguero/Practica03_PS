package es.unican.ps.hoteles.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.unican.ps.hoteles.common.daoLayer.IDAOHotelRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOReservaRemota;
import es.unican.ps.hoteles.common.entities.Categoria;
import es.unican.ps.hoteles.common.entities.Cliente;
import es.unican.ps.hoteles.common.entities.Habitacion;
import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.entities.Pago;
import es.unican.ps.hoteles.common.entities.Reserva;
import es.unican.ps.hoteles.common.entities.ReservaTipoHabitacion;
import es.unican.ps.hoteles.common.entities.SolicitudHabitacion;
import es.unican.ps.hoteles.common.entities.TipoHabitacion;
import es.unican.ps.hoteles.common.entities.TipoTarjeta;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
import es.unican.ps.hoteles.common.exceptions.FechasInvalidasException;
import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;

public class GestionReservasTest {

    @InjectMocks
    private GestionReservas sut;

    @Mock
    private IDAOHotelRemota mockIDAOHotel;
    @Mock
    private IDAOReservaRemota mockIDAOReserva;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void consultarHabitacionesHotelTest_1a() throws ParametrosInvalidosException, DataAccessException, FechasInvalidasException {

        Hotel hotel = new Hotel("Hotel Santander", "Sardinero", "Santander", Categoria.STAR_5);

        Habitacion habitacionDoble = new Habitacion(100, 10, TipoHabitacion.DOBLE);
        Habitacion habitacionIndividual = new Habitacion(50, 5, TipoHabitacion.INDIVIDUAL);
        hotel.getHabitaciones().add(habitacionDoble);
        hotel.getHabitaciones().add(habitacionIndividual);

        Cliente cliente = new Cliente("11111111A", "Juan", "Pérez", "López", "pepelopez@gmail.com");
        Pago pago = new Pago("1111222233334444", "123", 10, 2030, TipoTarjeta.MASTERCARD);
        Reserva reserva = new Reserva(LocalDate.now().minusDays(4), LocalDate.now().plusDays(4), cliente, pago);
        ReservaTipoHabitacion reservaTipo = new ReservaTipoHabitacion(8, 100, habitacionDoble);
        reserva.getReservasTipo().add(reservaTipo);
        hotel.getReservas().add(reserva);

        when(mockIDAOHotel.hotel(0001)).thenReturn(hotel);
        List<SolicitudHabitacion> result = sut.consultarHabitacionesHotel(0001, LocalDate.now().plusDays(4), LocalDate.now().plusDays(6));
        verify(mockIDAOHotel).hotel(0001);
        assertEquals(2, result.size());
        SolicitudHabitacion disponibleDoble = result.stream()
            .filter(sh -> sh.getHabitacion().equals(habitacionDoble))
            .findFirst()
            .orElseThrow();
        SolicitudHabitacion disponibleIndividual = result.stream()
            .filter(sh -> sh.getHabitacion().equals(habitacionIndividual))
            .findFirst()
            .orElseThrow();
        assertEquals(habitacionDoble, disponibleDoble.getHabitacion());
        assertEquals(2, disponibleDoble.getCantidad());
        assertEquals(habitacionIndividual, disponibleIndividual.getHabitacion());
        assertEquals(5, disponibleIndividual.getCantidad());
    }

    @Test
    public void consultarHabitacionesHotelTest_1b() throws ParametrosInvalidosException, DataAccessException, FechasInvalidasException {

        Hotel hotel = new Hotel("Hotel Santander", "Sardinero", "Santander", Categoria.STAR_5);

        Habitacion habitacionDoble = new Habitacion(100, 3, TipoHabitacion.DOBLE);
        hotel.getHabitaciones().add(habitacionDoble);

        when(mockIDAOHotel.hotel(0002)).thenReturn(hotel);
        List<SolicitudHabitacion> result = sut.consultarHabitacionesHotel(0002, LocalDate.now(), LocalDate.now().plusDays(1));
        verify(mockIDAOHotel).hotel(0002);
        assertEquals(1, result.size());
        SolicitudHabitacion disponibleDoble = result.get(0);
        assertEquals(habitacionDoble, disponibleDoble.getHabitacion());
        assertEquals(3, disponibleDoble.getCantidad());
    }

    @Test
    public void consultarHabitacionesHotelTest_1c() throws DataAccessException, FechasInvalidasException {

        when(mockIDAOHotel.hotel(0003)).thenReturn(null);
        List<SolicitudHabitacion> result = sut.consultarHabitacionesHotel(0003, LocalDate.now().plusDays(1), LocalDate.now().plusDays(4));
        verify(mockIDAOHotel).hotel(0003);
        assertNull(result);
    }

    @Test
    public void consultarHabitacionesHotelTest_1d_h() throws DataAccessException {

        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(0002, LocalDate.now().minusDays(4), LocalDate.now().plusDays(4)));
        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(0002, LocalDate.now().minusDays(1), LocalDate.now().plusDays(4)));
        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(0002, LocalDate.now().plusDays(4), LocalDate.now().plusDays(2)));
        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(0002, LocalDate.now().plusDays(4), LocalDate.now().plusDays(3)));
        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(0002, LocalDate.now().plusDays(4), LocalDate.now().plusDays(4)));
        verify(mockIDAOHotel, never()).hotel(anyInt());
    }

    @Test
    public void consultarHabitacionesHotelTest_1i() throws ParametrosInvalidosException, DataAccessException, FechasInvalidasException {

        Hotel hotel = new Hotel("Hotel Santander", "Sardinero", "Santander", Categoria.STAR_5);

        Habitacion habitacionDoble = new Habitacion(100, 6, TipoHabitacion.DOBLE);
        Habitacion habitacionIndividual = new Habitacion(50, 2, TipoHabitacion.INDIVIDUAL);
        hotel.getHabitaciones().add(habitacionDoble);
        hotel.getHabitaciones().add(habitacionIndividual);

        Cliente cliente = new Cliente("11111111A", "Juan", "Pérez", "López", "pepelopez@gmail.com");
        Pago pago = new Pago("1111222233334444", "123", 10, 2030, TipoTarjeta.MASTERCARD);
        Reserva reserva1 = new Reserva(LocalDate.now().minusDays(4), LocalDate.now().plusDays(4), cliente, pago);
        ReservaTipoHabitacion reservaTipoDoble = new ReservaTipoHabitacion(6, 100, habitacionDoble);
        reserva1.getReservasTipo().add(reservaTipoDoble);

        Reserva reserva2 = new Reserva(LocalDate.now().plusDays(12), LocalDate.now().plusDays(20), cliente, pago);
        ReservaTipoHabitacion reservaTipoIndividual = new ReservaTipoHabitacion(2, 100, habitacionIndividual);
        reserva2.getReservasTipo().add(reservaTipoIndividual);

        hotel.getReservas().add(reserva1);
        hotel.getReservas().add(reserva2);

        when(mockIDAOHotel.hotel(0004)).thenReturn(hotel);
        List<SolicitudHabitacion> result = sut.consultarHabitacionesHotel(0004, LocalDate.now().plusDays(3), LocalDate.now().plusDays(13));
        verify(mockIDAOHotel).hotel(0004);
        assertEquals(0, result.size());
    }

    @Test
    public void consultarHabitacionesHotelTest_1j() throws DataAccessException {

        when(mockIDAOHotel.hotel(0002)).thenThrow(new DataAccessException());
        assertThrows(DataAccessException.class, () -> sut.consultarHabitacionesHotel(0002, LocalDate.now().plusDays(1), LocalDate.now().plusDays(4)));
        verify(mockIDAOHotel).hotel(0002);
    }

}
