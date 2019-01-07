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

	/** Mensaje error. */
	private String detalleError;

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

}
