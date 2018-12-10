package es.caib.sistrahelp.core.api.model;

/**
 * La clase ResultadoAuditoriaDetallePago.
 */
public final class ResultadoAuditoriaDetallePago extends ModelApi {
	private static final long serialVersionUID = 1L;

	public ResultadoAuditoriaDetallePago() {
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
