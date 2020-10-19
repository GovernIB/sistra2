package es.caib.sistra2.commons.plugins.formulario.rest;

import java.io.Serializable;

/**
 * Datos retoron formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RDatosRetornoFormulario implements Serializable {

	/** Id sesión formulario en Sistra2. */
	private String idSesionFormulario;

	/**
	 * Indica si el formulario se ha cancelado.
	 */
	private boolean cancelado;

	/**
	 * Xml con los datos del formulario (Base 64).
	 */
	private String xml;

	/**
	 * Pdf con la visualizacón del formulario (Base 64).
	 */
	private String pdf;

	/**
	 * Método de acceso a cancelado.
	 *
	 * @return cancelado
	 */
	public boolean isCancelado() {
		return cancelado;
	}

	/**
	 * Método para establecer cancelado.
	 *
	 * @param cancelado
	 *                      cancelado a establecer
	 */
	public void setCancelado(final boolean cancelado) {
		this.cancelado = cancelado;
	}

	/**
	 * Método de acceso a idSesionFormulario.
	 *
	 * @return idSesionFormulario
	 */
	public String getIdSesionFormulario() {
		return idSesionFormulario;
	}

	/**
	 * Método para establecer idSesionFormulario.
	 *
	 * @param idSesionFormulario
	 *                               idSesionFormulario a establecer
	 */
	public void setIdSesionFormulario(final String idSesionFormulario) {
		this.idSesionFormulario = idSesionFormulario;
	}

	/**
	 * Método de acceso a xml.
	 * 
	 * @return xml
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * Método para establecer xml.
	 * 
	 * @param xml
	 *                xml a establecer
	 */
	public void setXml(final String xml) {
		this.xml = xml;
	}

	/**
	 * Método de acceso a pdf.
	 * 
	 * @return pdf
	 */
	public String getPdf() {
		return pdf;
	}

	/**
	 * Método para establecer pdf.
	 * 
	 * @param pdf
	 *                pdf a establecer
	 */
	public void setPdf(final String pdf) {
		this.pdf = pdf;
	}

}
