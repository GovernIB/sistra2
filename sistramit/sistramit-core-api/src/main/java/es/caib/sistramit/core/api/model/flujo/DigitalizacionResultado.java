package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Datos digitalización anexo.
 *
 * @author Indra
 *
 */
public final class DigitalizacionResultado {

	/** Id sesion digitalizacion. */
	private String idSesionDigitalizacion;

	/** Indica si se ha digitalizado. */
	private TypeSiNo digitalizado = TypeSiNo.NO;

	/** Indica error en caso de error. */
	private String detalleError;

	public String getIdSesionDigitalizacion() {
		return idSesionDigitalizacion;
	}

	public void setIdSesionDigitalizacion(String idSesionDigitalizacion) {
		this.idSesionDigitalizacion = idSesionDigitalizacion;
	}

	public TypeSiNo getDigitalizado() {
		return digitalizado;
	}

	public void setDigitalizado(TypeSiNo digitalizado) {
		this.digitalizado = digitalizado;
	}

	public String getDetalleError() {
		return detalleError;
	}

	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}
}
