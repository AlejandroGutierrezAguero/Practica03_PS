package es.unican.ps.hoteles.common.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reservas")
public class Reserva implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "idRes", nullable = false)
    private int idReserva;

    @Column(name = "fechEnt", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fechSal", nullable = false)
    private LocalDate fechaSalida;

    @OneToOne
    @JoinColumn(name = "Cliente_FK", nullable = false)
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "Pago_FK", nullable = false)
    private Pago pago;

    @OneToMany
    @JoinColumn(name = "Reserva_FK", nullable = false)
    private List<ReservaTipoHabitacion> reservasTipo;

    public Reserva () {}

    public Reserva(LocalDate fechaEntrada, LocalDate fechaSalida, Cliente cliente, Pago pago) throws ParametrosInvalidosException {
        if (fechaEntrada == null || fechaEntrada == null || cliente == null || pago == null) {
            throw new ParametrosInvalidosException();
        }
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.cliente = cliente;
        this.pago = pago;
        this.reservasTipo = new ArrayList<>();
    }

    private double calculaImporte() {
        double importe = 0;
        for (ReservaTipoHabitacion rt : reservasTipo) {
            importe += rt.getNumHabitaciones() * rt.getPrecioHabitacion() * ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        }
        return importe;
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
        return calculaImporte();
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