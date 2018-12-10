package es.caib.sistrahelp.core.api.model;

import java.util.Date;

/**
 * Datos finalización pago.
 *
 * @author Indra
 *
 */
public final class VerificacionPago extends ModelApi {
	private static final long serialVersionUID = 1L;

	/** Indica si se ha podido verificar. */
	private boolean verificado;

	/** Indica si se ha pagado. */
	private boolean pagado;

	/** Indica mensaje de error. */
	private String codigoError;

	/** Indica mensaje de error. */
	private String mensajeError;

	/** Fecha pago. */
	private Date fechaPago;

	/** Identificador pago en pasarela. */
	private String localizador;

	/** PDF Justificante. */
	private byte[] justificantePDF;

	/**
	 * Método de acceso a verificado.
	 *
	 * @return verificado
	 */
	public boolean isVerificado() {
		return verificado;
	}

	/**
	 * Método para establecer verificado.
	 *
	 * @param verificado
	 *            verificado a establecer
	 */
	public void setVerificado(final boolean verificado) {
		this.verificado = verificado;
	}

	/**
	 * Método de acceso a pagado.
	 *
	 * @return pagado
	 */
	public boolean isPagado() {
		return pagado;
	}

	/**
	 * Método para establecer pagado.
	 *
	 * @param pagado
	 *            pagado a establecer
	 */
	public void setPagado(final boolean pagado) {
		this.pagado = pagado;
	}

	/**
	 * Método de acceso a codigoError.
	 *
	 * @return codigoError
	 */
	public String getCodigoError() {
		return codigoError;
	}

	/**
	 * Método para establecer codigoError.
	 *
	 * @param codigoError
	 *            codigoError a establecer
	 */
	public void setCodigoError(final String codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * Método de acceso a mensajeError.
	 *
	 * @return mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * Método para establecer mensajeError.
	 *
	 * @param mensajeError
	 *            mensajeError a establecer
	 */
	public void setMensajeError(final String mensajeError) {
		this.mensajeError = mensajeError;
	}

	/**
	 * Método de acceso a fechaPago.
	 *
	 * @return fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * Método para establecer fechaPago.
	 *
	 * @param fechaPago
	 *            fechaPago a establecer
	 */
	public void setFechaPago(final Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * Método de acceso a localizador.
	 *
	 * @return localizador
	 */
	public String getLocalizador() {
		return localizador;
	}

	/**
	 * Método para establecer localizador.
	 *
	 * @param localizador
	 *            localizador a establecer
	 */
	public void setLocalizador(final String localizador) {
		this.localizador = localizador;
	}

	/**
	 * Método de acceso a justificantePDF.
	 *
	 * @return justificantePDF
	 */
	public byte[] getJustificantePDF() {
		return justificantePDF;
	}

	/**
	 * Método para establecer justificantePDF.
	 *
	 * @param justificantePDF
	 *            justificantePDF a establecer
	 */
	public void setJustificantePDF(final byte[] justificantePDF) {
		this.justificantePDF = justificantePDF;
	}

}
