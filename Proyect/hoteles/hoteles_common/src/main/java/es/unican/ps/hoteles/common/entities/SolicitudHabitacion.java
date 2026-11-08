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
}
