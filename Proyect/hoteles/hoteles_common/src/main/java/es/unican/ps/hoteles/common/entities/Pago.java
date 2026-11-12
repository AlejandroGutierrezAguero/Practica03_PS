package es.unican.ps.hoteles.common.entities;

import java.io.Serializable;
import java.time.LocalDate;

import es.unican.ps.hoteles.common.exceptions.ParametrosInvalidosException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pagos")
public class Pago implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int idPago;

    @Column(name = "numTar", nullable = false)
    private String numTarjeta;

    @Column(nullable = false)
    private String cvc;

    @Column(name = "mesCad", nullable = false)
    private int mesCaducidad;

    @Column(name = "anhoCad", nullable = false)
    private int anhoCaducidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoTar", nullable = false)
    private TipoTarjeta tipoTarjeta;

    public Pago () {}

    public Pago(String numTarjeta, String cvc, int mesCaducidad, int anhoCaducidad, TipoTarjeta tipoTarjeta) throws ParametrosInvalidosException {
        if (numTarjeta == null || cvc == null || mesCaducidad <= 0 || mesCaducidad > 12 || anhoCaducidad < LocalDate.now().getYear() || tipoTarjeta == null) {
            throw new ParametrosInvalidosException();
        }
        this.numTarjeta = numTarjeta;
        this.cvc = cvc;
        this.mesCaducidad = mesCaducidad;
        this.anhoCaducidad = anhoCaducidad;
        this.tipoTarjeta = tipoTarjeta;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public int getMesCaducidad() {
        return mesCaducidad;
    }

    public void setMesCaducidad(int mesCaducidad) {
        this.mesCaducidad = mesCaducidad;
    }

    public int getAnhoCaducidad() {
        return anhoCaducidad;
    }

    public void setAnhoCaducidad(int anhoCaducidad) {
        this.anhoCaducidad = anhoCaducidad;
    }

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numTarjeta == null) ? 0 : numTarjeta.hashCode());
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
        Pago other = (Pago) obj;
        if (numTarjeta == null) {
            if (other.numTarjeta != null)
                return false;
        } else if (!numTarjeta.equals(other.numTarjeta))
            return false;
        return true;
    }
}