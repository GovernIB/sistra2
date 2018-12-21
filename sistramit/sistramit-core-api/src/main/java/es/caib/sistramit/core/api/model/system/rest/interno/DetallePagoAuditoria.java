package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;

/**
 * La clase DetallePagoAuditoria (RestApiInternaService).
 */
@SuppressWarnings("serial")
public final class DetallePagoAuditoria implements Serializable {

	/** Datos sesion pago que se encuentran en el asistente. */
	private DatosSesionPago datos;

	/** Datos obtenidos de la pasarela de pagos (en caso necesario). */
	private VerificacionPago verificacion;

	public DatosSesionPago getDatos() {
		return datos;
	}

	public void setDatos(final DatosSesionPago datos) {
		this.datos = datos;
	}

	public VerificacionPago getVerificacion() {
		return verificacion;
	}

	public void setVerificacion(final VerificacionPago verificacion) {
		this.verificacion = verificacion;
	}

}
