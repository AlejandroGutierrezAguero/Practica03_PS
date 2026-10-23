package es.unican.ps.hoteles.common.entities;

public class Pago {

    private int idPago;
    private String numTarjeta;
    private String cvc;
    private int mesCaducidad;
    private int anhoCaducidad;
    private TipoTarjeta tipoTarjeta;

    public Pago(int idPago, String numTarjeta, String cvc, int mesCaducidad, int anhoCaducidad, TipoTarjeta tipoTarjeta) {
        this.idPago = idPago;
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