package es.unican.ps.hoteles.common.exceptions;

public class ReservaNoCorrespondeConDNIException extends Exception {

	public ReservaNoCorrespondeConDNIException() {
		super("Reserva no corresponde con DNI");
	}

}
