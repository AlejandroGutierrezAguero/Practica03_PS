package es.unican.ps.hoteles.common.entities;

public class ReservaTipoHabitacion {

    private int idReservaTipo;
    private int numHabitaciones;
    private double precioHabitacion;
    private Habitacion tipoHabitacion;

    public ReservaTipoHabitacion(int idReservaTipo, int numHabitaciones, double precioHabitacion, Habitacion tipoHabitacion) {
        this.idReservaTipo = idReservaTipo;
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
        result = prime * result + idReservaTipo;
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
        if (idReservaTipo != other.idReservaTipo)
            return false;
        return true;
    }
}