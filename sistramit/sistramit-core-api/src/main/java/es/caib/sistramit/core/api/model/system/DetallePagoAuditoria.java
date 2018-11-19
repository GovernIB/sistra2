package es.caib.sistramit.core.api.model.system;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;

/**
 * La clase PagoAuditoria.
 */
@SuppressWarnings("serial")
public final class DetallePagoAuditoria implements Serializable {
	public DetallePagoAuditoria() {
		super();
	}

	DatosSesionPago datos;

	VerificacionPago verificacion;

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
