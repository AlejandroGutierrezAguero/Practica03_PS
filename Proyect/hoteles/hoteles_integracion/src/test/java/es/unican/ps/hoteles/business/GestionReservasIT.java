package es.unican.ps.hoteles.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import es.unican.ps.hoteles.common.businessLayer.IReservasPersonalRemota;
import es.unican.ps.hoteles.common.daoLayer.IDAOHotelRemota;
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
import jakarta.ejb.embeddable.EJBContainer;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class GestionReservasIT {

    private static EJBContainer ec;

    private static IReservasPersonalRemota sut;
    private static IDAOHotelRemota hotelDAO;

    int idHotel2 = 0;

    public static void reiniciaBBDD() throws DataAccessException {
        for (Hotel h : hotelDAO.hoteles()) {
            hotelDAO.eliminaHotel(h.getIdHotel());
        }
    }

    @BeforeAll
    public static void initContainer() throws Exception {

        String glassfishHome = System.getenv("GLASSFISH_HOME");
        Map<String, Object> properties = new HashMap<>();
        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", glassfishHome);

        ec = EJBContainer.createEJBContainer(properties);
	    sut = (IReservasPersonalRemota) ec.getContext().lookup("java:global/ejb-app/UCHotelesBusiness/GestionReservas!es.unican.ps.hoteles.common.businessLayer.IReservasPersonalRemota");
        hotelDAO = (IDAOHotelRemota) ec.getContext().lookup("java:global/ejb-app/UCHotelesDAO/DAOHotel!es.unican.ps.hoteles.common.daoLayer.IDAOHotelRemota");

        reiniciaBBDD();
    }

    @AfterAll
    public static void closeContainer() throws Exception {

        reiniciaBBDD();

        if (ec != null) {
            ec.close();
        }
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

        hotel = hotelDAO.creaHotel(hotel);

        List<SolicitudHabitacion> result = sut.consultarHabitacionesHotel(hotel.getIdHotel(), LocalDate.now().plusDays(4), LocalDate.now().plusDays(6));
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

        Hotel hotel = new Hotel("Hotel Madrid", "Madrid", "Madrid", Categoria.STAR_5);

        Habitacion habitacionDoble = new Habitacion(1000, 3, TipoHabitacion.DOBLE);
        hotel.getHabitaciones().add(habitacionDoble);

        hotel = hotelDAO.creaHotel(hotel);
        idHotel2 = hotel.getIdHotel();

        List<SolicitudHabitacion> result = sut.consultarHabitacionesHotel(hotel.getIdHotel(), LocalDate.now(), LocalDate.now().plusDays(1));
        assertEquals(1, result.size());
        SolicitudHabitacion disponibleDoble = result.get(0);
        assertEquals(habitacionDoble, disponibleDoble.getHabitacion());
        assertEquals(3, disponibleDoble.getCantidad());
    }

    @Test
    public void consultarHabitacionesHotelTest_1c() throws DataAccessException, FechasInvalidasException {

        List<SolicitudHabitacion> result = sut.consultarHabitacionesHotel(Integer.MAX_VALUE, LocalDate.now().plusDays(1), LocalDate.now().plusDays(4));
        assertNull(result);
    }

    @Test
    public void consultarHabitacionesHotelTest_1d_h() throws DataAccessException {

        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(idHotel2, LocalDate.now().minusDays(4), LocalDate.now().plusDays(4)));
        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(idHotel2, LocalDate.now().minusDays(1), LocalDate.now().plusDays(4)));
        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(idHotel2, LocalDate.now().plusDays(4), LocalDate.now().plusDays(2)));
        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(idHotel2, LocalDate.now().plusDays(4), LocalDate.now().plusDays(3)));
        assertThrows(FechasInvalidasException.class, () -> sut.consultarHabitacionesHotel(idHotel2, LocalDate.now().plusDays(4), LocalDate.now().plusDays(4)));
    }

    @Test
    public void consultarHabitacionesHotelTest_1i() throws ParametrosInvalidosException, DataAccessException, FechasInvalidasException {

        Hotel hotel = new Hotel("Hotel Berlín", "Berlín", "Berlín", Categoria.STAR_5);

        Habitacion habitacionDoble = new Habitacion(500, 6, TipoHabitacion.DOBLE);
        Habitacion habitacionIndividual = new Habitacion(200, 2, TipoHabitacion.INDIVIDUAL);
        hotel.getHabitaciones().add(habitacionDoble);
        hotel.getHabitaciones().add(habitacionIndividual);

        Cliente cliente = new Cliente("11111111A", "Juan", "Pérez", "López", "pepelopez@gmail.com");
        Pago pago = new Pago("1111222233334444", "123", 10, 2030, TipoTarjeta.MASTERCARD);
        Reserva reserva1 = new Reserva(LocalDate.now().minusDays(4), LocalDate.now().plusDays(4), cliente, pago);
        ReservaTipoHabitacion reservaTipoDoble = new ReservaTipoHabitacion(6, 500, habitacionDoble);
        reserva1.getReservasTipo().add(reservaTipoDoble);

        Reserva reserva2 = new Reserva(LocalDate.now().plusDays(12), LocalDate.now().plusDays(20), cliente, pago);
        ReservaTipoHabitacion reservaTipoIndividual = new ReservaTipoHabitacion(2, 200, habitacionIndividual);
        reserva2.getReservasTipo().add(reservaTipoIndividual);

        hotel.getReservas().add(reserva1);
        hotel.getReservas().add(reserva2);

        hotel = hotelDAO.creaHotel(hotel);

        List<SolicitudHabitacion> result = sut.consultarHabitacionesHotel(hotel.getIdHotel(), LocalDate.now().plusDays(3), LocalDate.now().plusDays(13));
        assertEquals(0, result.size());
    }
}