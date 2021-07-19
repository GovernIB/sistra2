package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.service.model.formulario.types.TipoFinalizacionFormulario;

/**
 * Datos finalización formulario: devuelve el XML y el PDF.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosFinalizacionFormulario implements Serializable {

	/** Indica fecha finalizacion sesion formulario. */
	private Date fechaFinalizacion;

	/** Indica como se ha finalizado el formulario. */
	private TipoFinalizacionFormulario estadoFinalizacion;

	/** Xml con los datos del formulario. */
	private byte[] xml;

	/** Pdf con la visualizacón del formulario. */
	private byte[] pdf;

	/** Ticket externo para formulario externo. */
	private String ticketExterno;

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
	 *                 xml a establecer
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
	 *                 pdf a establecer
	 */
	public final void setPdf(final byte[] pPdf) {
		pdf = pPdf;
	}

	/**
	 * Método de acceso a cancelado.
	 *
	 * @return cancelado
	 */
	public final TipoFinalizacionFormulario getEstadoFinalizacion() {
		return estadoFinalizacion;
	}

	/**
	 * Método para establecer cancelado.
	 *
	 * @param pCancelado
	 *                       cancelado a establecer
	 */
	public final void setEstadoFinalizacion(final TipoFinalizacionFormulario pCancelado) {
		estadoFinalizacion = pCancelado;
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
	 *                               fechaFinalizacion a establecer
	 */
	public final void setFechaFinalizacion(final Date pFechaFinalizacion) {
		fechaFinalizacion = pFechaFinalizacion;
	}

	/**
	 * Método de acceso a ticketExterno.
	 *
	 * @return ticketExterno
	 */
	public String getTicketExterno() {
		return ticketExterno;
	}

	/**
	 * Método para establecer ticketExterno.
	 *
	 * @param ticketExterno
	 *                          ticketExterno a establecer
	 */
	public void setTicketExterno(final String ticketExterno) {
		this.ticketExterno = ticketExterno;
	}

}
