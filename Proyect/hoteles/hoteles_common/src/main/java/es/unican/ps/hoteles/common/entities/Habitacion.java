package es.unican.ps.hoteles.common.entities;

import java.io.Serializable;

import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Habitaciones")
public class Habitacion implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "idHab", nullable = false)
    private int idHabitacion;

    @Column(name = "prcNoche", nullable = false)
    private double precioPorNoche;

    @Column(name = "disps", nullable = false)
    private int disponibles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitacion tipo;

    public Habitacion () {}

    public Habitacion(double precioPorNoche, int disponibles, TipoHabitacion tipo) throws ParametrosInvalidosException {
        if (precioPorNoche < 0 || disponibles < 0 || tipo == null) {
            throw new ParametrosInvalidosException();
        }
        this.precioPorNoche = precioPorNoche;
        this.disponibles = disponibles;
        this.tipo = tipo;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
        Habitacion other = (Habitacion) obj;
        if (tipo != other.tipo)
            return false;
        return true;
    }
}