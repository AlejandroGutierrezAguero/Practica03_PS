package es.unican.ps.hoteles.common.entities;

import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;

public class SolicitudHabitacion {

    private Habitacion habitacion;
    private int cantidad;

    public SolicitudHabitacion(Habitacion habitacion, int cantidad) throws ParametrosInvalidosException {
        if (habitacion == null || cantidad < 0) {
            throw new ParametrosInvalidosException();
        }
        this.habitacion = habitacion;
        this.cantidad = cantidad;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((habitacion == null) ? 0 : habitacion.hashCode());
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
        SolicitudHabitacion other = (SolicitudHabitacion) obj;
        if (habitacion == null) {
            if (other.habitacion != null)
                return false;
        } else if (!habitacion.equals(other.habitacion))
            return false;
        return true;
    }
}
