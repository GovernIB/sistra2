package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;
import java.util.Date;

/**
 * Datos finalización formulario: devuelve el XML y el PDF.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosFinalizacionFormulario implements Serializable {

	/**
	 * Indica fecha finalizacion sesion formulario.
	 */
	private Date fechaFinalizacion;

	/**
	 * Indica si el formulario se ha cancelado.
	 */
	private boolean cancelado;

	/**
	 * Xml con los datos del formulario.
	 */
	private byte[] xml;

	/**
	 * Pdf con la visualizacón del formulario.
	 */
	private byte[] pdf;

	/**
	 * Método de acceso a xml.
	 *
	 * @return xml
	 */
	public final byte[] getXml() {
		return xml;
	}

	/**
	 * Método para establecer xml.
	 *
	 * @param pXml
	 *            xml a establecer
	 */
	public final void setXml(final byte[] pXml) {
		xml = pXml;
	}

	/**
	 * Método de acceso a pdf.
	 *
	 * @return pdf
	 */
	public final byte[] getPdf() {
		return pdf;
	}

	/**
	 * Método para establecer pdf.
	 *
	 * @param pPdf
	 *            pdf a establecer
	 */
	public final void setPdf(final byte[] pPdf) {
		pdf = pPdf;
	}

	/**
	 * Método de acceso a cancelado.
	 *
	 * @return cancelado
	 */
	public final boolean isCancelado() {
		return cancelado;
	}

	/**
	 * Método para establecer cancelado.
	 *
	 * @param pCancelado
	 *            cancelado a establecer
	 */
	public final void setCancelado(final boolean pCancelado) {
		cancelado = pCancelado;
	}

	/**
	 * Método de acceso a fechaFinalizacion.
	 *
	 * @return fechaFinalizacion
	 */
	public final Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	/**
	 * Método para establecer fechaFinalizacion.
	 *
	 * @param pFechaFinalizacion
	 *            fechaFinalizacion a establecer
	 */
	public final void setFechaFinalizacion(final Date pFechaFinalizacion) {
		fechaFinalizacion = pFechaFinalizacion;
	}

}
