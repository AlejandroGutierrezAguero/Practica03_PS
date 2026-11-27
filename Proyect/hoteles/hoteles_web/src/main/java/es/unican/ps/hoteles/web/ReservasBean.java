package es.unican.ps.hoteles.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.unican.ps.hoteles.common.businessLayer.IBusquedaHotelesRemota;
import es.unican.ps.hoteles.common.businessLayer.IReservasPersonalRemota;
import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.entities.SolicitudHabitacion;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
import es.unican.ps.hoteles.common.exceptions.FechasInvalidasException;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ReservasBean implements Serializable {

    @EJB
    private IBusquedaHotelesRemota busquedaHoteles;
    @EJB
    private IReservasPersonalRemota reservasPersonal;

    private String nombreHotel;
    private String localidad;
    private List<Hotel> hoteles;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private List<SolicitudHabitacion> habitacionesDisponibles;
    private Hotel hotel;
    private Map<Integer, Integer> cantidadesSolicitadas;
    private List<SolicitudHabitacion> solicitudes;
    private double precioTotal;

    public String doBuscarHotel() {
        boolean tieneNombre = (nombreHotel != null && !nombreHotel.trim().isEmpty());
        boolean tieneLocalidad = (localidad != null && !localidad.trim().isEmpty());

        if (tieneNombre == tieneLocalidad) {
            musetraError("Debes rellenar únicamente el nombre del hotel o la localidad.");
            return null;
        }

        hoteles = new ArrayList<>();

        try{
            if (tieneNombre) {
                Hotel h = busquedaHoteles.buscarHotelPorNombre(nombreHotel.trim());
                if (h == null) {
                    musetraError("Hotel no encontrado.");
                    return null;
                }
                hoteles.add(h);

            } else {
                hoteles = busquedaHoteles.buscarHotelPorLocalidad(localidad.trim());
                if (hoteles.isEmpty()) {
                    musetraError("No existen hoteles para esa localidad.");
                    return null;
                }
            }
        } catch (DataAccessException e) {
            musetraError("Error en acceso a datos.");
            return null;
        }

        return "listaHoteles.xhtml";
    }

    public String doVerDisponibilidad(Hotel hotel) {
        this.hotel = hotel;
        try {
            habitacionesDisponibles = reservasPersonal.consultarHabitacionesHotel(hotel.getIdHotel(), fechaEntrada, fechaSalida);
            if (habitacionesDisponibles.isEmpty()) {
                musetraError("No hay disponibilidad, pruebe otras fechas.");
                return null;
            }
        } catch (DataAccessException e) {
            musetraError("Error en acceso a datos.");
            return null;
        } catch (FechasInvalidasException e) {
            musetraError("Debes introducir unas fechas válidas.");
            return null;
        }
        return "disponibilidadHotel.xhtml";
    }

    public String doReservar() {
        return null;
    }

    private void musetraError (String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", mensaje));
    }

    public ReservasBean() {}

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    
    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public void setHoteles(List<Hotel> hoteles) {
        this.hoteles = hoteles;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public List<SolicitudHabitacion> getHabitacionesDisponibles() {
        return habitacionesDisponibles;
    }

    public void setHabitacionesDisponibles(List<SolicitudHabitacion> habitacionesDisponibles) {
        this.habitacionesDisponibles = habitacionesDisponibles;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Map<Integer, Integer> getCantidadesSolicitadas() {
        return cantidadesSolicitadas;
    }

    public void setCantidadesSolicitadas(Map<Integer, Integer> cantidadesSolicitadas) {
        this.cantidadesSolicitadas = cantidadesSolicitadas;
    }

    public List<SolicitudHabitacion> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudHabitacion> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

}