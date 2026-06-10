package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Datos verificación firma.
 *
 * @author Indra
 *
 */
public final class FirmaVerificacion {

	/** Indica si se ha realizado la firma en cliente. */
	private TypeSiNo realizada = TypeSiNo.NO;

	/** Indica si se ha podido verificar la firma realizada. */
	private TypeSiNo verificada = TypeSiNo.NO;

	/** Indica si se ha cancelado la firma. */
	private TypeSiNo cancelada = TypeSiNo.NO;

	/** Codigo error. */
	private String codigoError;

	/** Mensaje error. */
	private String detalleError;

	/** Sesión de firma. */
	private String sesionFirma;

	/** En caso correcto, indica metodo de firma (proporcionado por el plugin de firma). */
	private String metodoFirma;

	/**
	 * Método de acceso a realizada.
	 *
	 * @return realizada
	 */
	public TypeSiNo getRealizada() {
		return realizada;
	}

	/**
	 * Método para establecer realizada.
	 *
	 * @param realizada
	 *            realizada a establecer
	 */
	public void setRealizada(TypeSiNo realizada) {
		this.realizada = realizada;
	}

	/**
	 * Método de acceso a verificada.
	 *
	 * @return verificada
	 */
	public TypeSiNo getVerificada() {
		return verificada;
	}

	/**
	 * Método para establecer verificada.
	 *
	 * @param verificada
	 *            verificada a establecer
	 */
	public void setCancelada(TypeSiNo cancelada) {
		this.cancelada = cancelada;
	}

	/**
	 * Método de acceso a verificada.
	 *
	 * @return verificada
	 */
	public TypeSiNo getCancelada() {
		return cancelada;
	}

	/**
	 * Método para establecer verificada.
	 *
	 * @param verificada
	 *            verificada a establecer
	 */
	public void setVerificada(TypeSiNo verificada) {
		this.verificada = verificada;
	}

	/**
	 * Método de acceso a mensajeError.
	 *
	 * @return mensajeError
	 */
	public String getDetalleError() {
		return detalleError;
	}

	/**
	 * Método para establecer mensajeError.
	 *
	 * @param mensajeError
	 *            mensajeError a establecer
	 */
	public void setDetalleError(String mensajeError) {
		this.detalleError = mensajeError;
	}

	/**
	 * Método de acceso a sesionFirma.
	 */
	public String getSesionFirma() {
		return sesionFirma;
	}

	/**
	 * Método para establecer sesionFirma.
	 */
	public void setSesionFirma(final String sesionFirma) {
		this.sesionFirma = sesionFirma;
	}

	/**
	 * Método de acceso a metodoFirma.
	 */
	public String getMetodoFirma() {
		return metodoFirma;
	}

	/**
	 * Método para establecer metodoFirma.
	 */
	public void setMetodoFirma(final String metodoFirma) {
		this.metodoFirma = metodoFirma;
	}

	/**
	 * Método de acceso a codigoError.
	 * @return codigoError
	 */
	public String getCodigoError() {
		return codigoError;
	}

	/**
	 * Método para establecer codigoError.
	 * @param codigoError codigoError a establecer
	 */
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
}
