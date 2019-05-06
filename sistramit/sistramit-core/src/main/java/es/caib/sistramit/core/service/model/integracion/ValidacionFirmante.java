package es.caib.sistramit.core.service.model.integracion;

import java.io.Serializable;

/**
 * Validación firmante.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValidacionFirmante implements Serializable {

	/** Indica si ha firmado el firmante. */
	private boolean correcto;

	/** En caso error, indica detalle error. */
	private String detalleError;

	/**
	 * Método de acceso a correcto.
	 * 
	 * @return correcto
	 */
	public boolean isCorrecto() {
		return correcto;
	}

	/**
	 * Método para establecer correcto.
	 * 
	 * @param correcto
	 *            correcto a establecer
	 */
	public void setCorrecto(final boolean correcto) {
		this.correcto = correcto;
	}

	/**
	 * Método de acceso a detalleError.
	 * 
	 * @return detalleError
	 */
	public String getDetalleError() {
		return detalleError;
	}

	/**
	 * Método para establecer detalleError.
	 * 
	 * @param detalleError
	 *            detalleError a establecer
	 */
	public void setDetalleError(final String detalleError) {
		this.detalleError = detalleError;
	}

}
