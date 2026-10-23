package es.unican.ps.hoteles.common.entities;

import java.time.LocalDate;
import java.util.List;

public class Reserva {

    private int idReserva;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private double importe;
    private Cliente cliente;
    private Pago pago;
    private List<ReservaTipoHabitacion> reservasTipo;

    public Reserva(int idReserva, LocalDate fechaEntrada, LocalDate fechaSalida, Cliente cliente, Pago pago) {
        this.idReserva = idReserva;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.cliente = cliente;
        this.pago = pago;
    }

    private double calculaImporte() {
        // TODO
        return 0.0;
    }
    
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
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

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public List<ReservaTipoHabitacion> getReservasTipo() {
        return reservasTipo;
    }

    public void setReservasTipo(List<ReservaTipoHabitacion> reservasTipo) {
        this.reservasTipo = reservasTipo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idReserva;
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
        Reserva other = (Reserva) obj;
        if (idReserva != other.idReserva)
            return false;
        return true;
    }
}