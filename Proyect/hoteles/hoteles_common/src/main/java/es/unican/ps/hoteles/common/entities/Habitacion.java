package es.unican.ps.hoteles.common.entities;

public class Habitacion {

    private int idHabitacion;
    private double precioPorNoche;
    private int disponibles;
    private TipoHabitacion tipo;

    public Habitacion(int idHabitacion, double precioPorNoche, int disponibles, TipoHabitacion tipo) {
        this.idHabitacion = idHabitacion;
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