package es.unican.ps.hoteles.common.entities;

import java.util.ArrayList;
import java.util.List;

import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;

public class Hotel {

    private int idHotel;
    private String nombre;
    private String direccion;
    private String localidad;
    private Categoria categoria;
    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;

    public Hotel(String nombre, String direccion, String localidad, Categoria categoria) throws ParametrosInvalidosException {
        if (nombre == null || direccion == null || localidad == null || categoria == null) {
            throw new ParametrosInvalidosException();
        }
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.categoria = categoria;
        this.habitaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hotel other = (Hotel) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }
}