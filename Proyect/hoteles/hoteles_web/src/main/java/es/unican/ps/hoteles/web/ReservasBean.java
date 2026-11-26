package es.unican.ps.hoteles.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.unican.ps.hoteles.common.businessLayer.IBusquedaHotelesRemota;
import es.unican.ps.hoteles.common.businessLayer.IReservasPersonalRemota;
import es.unican.ps.hoteles.common.entities.Hotel;
import es.unican.ps.hoteles.common.exceptions.DataAccessException;
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
                    musetraError("No existen hoteles para esa localidad");
                    return null;
                }
            }
        } catch (DataAccessException e) {
            musetraError("Error en acceso a datos");
            return null;
        }

        return "listaHoteles.xhtml";
    }

    private void musetraError (String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", mensaje));
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

}