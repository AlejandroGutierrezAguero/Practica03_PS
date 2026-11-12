package es.unican.ps.hoteles.common.entities;

import java.io.Serializable;

import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ReservasTipoHabitacion")
public class ReservaTipoHabitacion implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "idResTip", nullable = false)
    private int idReservaTipo;

    @Column(name = "numHabs", nullable = false)
    private int numHabitaciones;

    @Column(name = "prcHab", nullable = false)
    private double precioHabitacion;

    @OneToOne
    @JoinColumn(name = "Habitacion_FK", nullable = false)
    private Habitacion tipoHabitacion;

    public ReservaTipoHabitacion () {}

    public ReservaTipoHabitacion(int numHabitaciones, double precioHabitacion, Habitacion tipoHabitacion) throws ParametrosInvalidosException {
        if (numHabitaciones <= 0 || precioHabitacion < 0 || tipoHabitacion == null) {
            throw new ParametrosInvalidosException();
        }
        this.numHabitaciones = numHabitaciones;
        this.precioHabitacion = precioHabitacion;
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getIdReservaTipo() {
        return idReservaTipo;
    }

    public void setIdReservaTipo(int idReservaTipo) {
        this.idReservaTipo = idReservaTipo;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public double getPrecioHabitacion() {
        return precioHabitacion;
    }

    public void setPrecioHabitacion(double precioHabitacion) {
        this.precioHabitacion = precioHabitacion;
    }

    public Habitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(Habitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tipoHabitacion == null) ? 0 : tipoHabitacion.hashCode());
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
        ReservaTipoHabitacion other = (ReservaTipoHabitacion) obj;
        if (tipoHabitacion == null) {
            if (other.tipoHabitacion != null)
                return false;
        } else if (!tipoHabitacion.equals(other.tipoHabitacion))
            return false;
        return true;
    }

}