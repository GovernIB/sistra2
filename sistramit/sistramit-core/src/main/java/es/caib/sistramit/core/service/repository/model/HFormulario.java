package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Mapeo tabla STT_FORMUL.
 */

@Entity
@Table(name = "STT_FORMUL")
@SuppressWarnings("serial")
public final class HFormulario implements IModelApi {

	/** Atributo codigo. */
	@Id
	@SequenceGenerator(name = "STT_FORMUL_SEQ", sequenceName = "STT_FORMUL_SEQ", allocationSize = ConstantesNumero.N1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_FORMUL_SEQ")
	@Column(name = "SFR_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
	private Long codigo;

	/** Atributo ticket. */
	@Column(name = "SFR_TICKET")
	private String ticket;

	/** Atributo entidad. */
	@Column(name = "SFR_ENTIDA")
	private String entidad;

	/** Atributo fecha inicio. */
	@Column(name = "SFR_FECINI")
	private Date fechaInicio;

	/** Atributo id sesion tramitacion. */
	@Column(name = "SFR_IDESTR")
	private String idSesionTramitacion;

	/** Atributo id trámite. */
	@Column(name = "SFR_IDTRAM")
	private String idTramite;

	/** Atributo versión trámite. */
	@Column(name = "SFR_VERSIO")
	private int versionTramite;

	/** Atributo release trámite. */
	@Column(name = "SFR_RELESE")
	private int releaseTramite;

	/** Atributo idioma trámite. */
	@Column(name = "SFR_IDIOMA")
	private String idioma;

	/** Atributo id paso. */
	@Column(name = "SFR_IDPASO")
	private String idPaso;

	/** Atributo id formulario. */
	@Column(name = "SFR_IDFORM")
	private String idFormulario;

	/** Atributo formulario interno. */
	@Column(name = "SFR_INTERN")
	private boolean interno;

	/** Atributo datos actuales. */
	@Lob
	@Column(name = "SFR_DATFOR")
	private byte[] datosActuales;

	/** Atributo informacion autenticación. */
	@Lob
	@Column(name = "SFR_INFAUT")
	private String infoAutenticacion;

	/** Atributo parametros formulario. */
	@Column(name = "SFR_PARFOR")
	private String parametrosFormulario;

	/** Atributo fecha fin. */
	@Column(name = "SFR_FECFIN")
	private Date fechaFin;

	/** Atributo cancelado. */
	@Column(name = "SFR_CANCEL")
	private boolean cancelado;

	/** Atributo titulo procedimiento. */
	@Column(name = "SFR_TITPRO")
	private String tituloProcedimiento;

	/** Atributo codigo sia procedimiento. */
	@Column(name = "SFR_SIAPRO")
	private String codigoSiaProcedimiento;

	/** Atributo xml. */
	@Lob
	@Column(name = "SFR_XML")
	private byte[] xml;

	/** Atributo pdf. */
	@Lob
	@Column(name = "SFR_PDF")
	private byte[] pdf;

	/** Atributo ticket usado. */
	@Column(name = "SFR_TCKUSA")
	private boolean usadoRetorno;

	/** Ticket formulario externo. */
	@Column(name = "SFR_TCKGFE")
	private String ticketFormularioExterno;

	/** Id gestor formularios externo. */
	@Column(name = "SFR_IDGFE")
	private String idGestorFormulariosExterno;

	/**
	 * Método para obtener el campo codigo.
	 *
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método para settear el campo codigo.
	 *
	 * @param pCodigo
	 *                    el campo codigo a settear
	 */
	public void setCodigo(final Long pCodigo) {
		codigo = pCodigo;
	}

	/**
	 * Método para obtener el campo ticket.
	 *
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Método para settear el campo ticket.
	 *
	 * @param pTicket
	 *                    el campo ticket a settear
	 */
	public void setTicket(final String pTicket) {
		ticket = pTicket;
	}

	/**
	 * Método para obtener el campo fechaInicio.
	 *
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Método para settear el campo fechaInicio.
	 *
	 * @param pFechaInicio
	 *                         el campo fechaInicio a settear
	 */
	public void setFechaInicio(final Date pFechaInicio) {
		fechaInicio = pFechaInicio;
	}

	/**
	 * Método para obtener el campo idSesionTramitacion.
	 *
	 * @return the idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Método para settear el campo idSesionTramitacion.
	 *
	 * @param pIdSesionTramitacion
	 *                                 el campo idSesionTramitacion a settear
	 */
	public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
		idSesionTramitacion = pIdSesionTramitacion;
	}

	/**
	 * Método para obtener el campo idPaso.
	 *
	 * @return the idPaso
	 */
	public String getIdPaso() {
		return idPaso;
	}

	/**
	 * Método para settear el campo idPaso.
	 *
	 * @param pIdPaso
	 *                    el campo idPaso a settear
	 */
	public void setIdPaso(final String pIdPaso) {
		idPaso = pIdPaso;
	}

	/**
	 * Método para obtener el campo idFormulario.
	 *
	 * @return the idFormulario
	 */
	public String getIdFormulario() {
		return idFormulario;
	}

	/**
	 * Método para settear el campo idFormulario.
	 *
	 * @param pIdFormulario
	 *                          el campo idFormulario a settear
	 */
	public void setIdFormulario(final String pIdFormulario) {
		idFormulario = pIdFormulario;
	}

	/**
	 * Método para obtener el campo datosActuales.
	 *
	 * @return the datosActuales
	 */
	public byte[] getDatosActuales() {
		return datosActuales;
	}

	/**
	 * Método para settear el campo datosActuales.
	 *
	 * @param pDatosActuales
	 *                           el campo datosActuales a settear
	 */
	public void setDatosActuales(final byte[] pDatosActuales) {
		datosActuales = pDatosActuales;
	}

	/**
	 * Método para obtener el campo parametrosFormulario.
	 *
	 * @return the parametrosFormulario
	 */
	public String getParametrosFormulario() {
		return parametrosFormulario;
	}

	/**
	 * Método para settear el campo parametrosFormulario.
	 *
	 * @param pParametrosFormulario
	 *                                  el campo parametrosFormulario a settear
	 */
	public void setParametrosFormulario(final String pParametrosFormulario) {
		parametrosFormulario = pParametrosFormulario;
	}

	/**
	 * Método para obtener el campo fechaFin.
	 *
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * Método para settear el campo fechaFin.
	 *
	 * @param pFechaFin
	 *                      el campo fechaFin a settear
	 */
	public void setFechaFin(final Date pFechaFin) {
		fechaFin = pFechaFin;
	}

	/**
	 * Método para obtener el campo cancelado.
	 *
	 * @return the cancelado
	 */
	public boolean isCancelado() {
		return cancelado;
	}

	/**
	 * Método para settear el campo cancelado.
	 *
	 * @param pCancelado
	 *                       el campo cancelado a settear
	 */
	public void setCancelado(final boolean pCancelado) {
		cancelado = pCancelado;
	}

	/**
	 * Método para obtener el campo xml.
	 *
	 * @return the xml
	 */
	public byte[] getXml() {
		return xml;
	}

	/**
	 * Método para settear el campo xml.
	 *
	 * @param pXml
	 *                 el campo xml a settear
	 */
	public void setXml(final byte[] pXml) {
		xml = pXml;
	}

	/**
	 * Método para obtener el campo pdf.
	 *
	 * @return the pdf
	 */
	public byte[] getPdf() {
		return pdf;
	}

	/**
	 * Método para settear el campo pdf.
	 *
	 * @param pPdf
	 *                 el campo pdf a settear
	 */
	public void setPdf(final byte[] pPdf) {
		pdf = pPdf;
	}

	/**
	 * Método de acceso a idTramite.
	 *
	 * @return idTramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * Método para establecer idTramite.
	 *
	 * @param pIdTramite
	 *                       idTramite a establecer
	 */
	public void setIdTramite(final String pIdTramite) {
		idTramite = pIdTramite;
	}

	/**
	 * Método de acceso a versionTramite.
	 *
	 * @return versionTramite
	 */
	public int getVersionTramite() {
		return versionTramite;
	}

	/**
	 * Método para establecer versionTramite.
	 *
	 * @param pVersionTramite
	 *                            versionTramite a establecer
	 */
	public void setVersionTramite(final int pVersionTramite) {
		versionTramite = pVersionTramite;
	}

	/**
	 * Método de acceso a releaseTramite.
	 *
	 * @return releaseTramite
	 */
	public int getReleaseTramite() {
		return releaseTramite;
	}

	/**
	 * Método para establecer releaseTramite.
	 *
	 * @param pReleaseTramite
	 *                            releaseTramite a establecer
	 */
	public void setReleaseTramite(final int pReleaseTramite) {
		releaseTramite = pReleaseTramite;
	}

	/**
	 * Método de acceso a usadoRetorno.
	 *
	 * @return usadoRetorno
	 */
	public boolean isUsadoRetorno() {
		return usadoRetorno;
	}

	/**
	 * Método para establecer usadoRetorno.
	 *
	 * @param pUsadoRetorno
	 *                          usadoRetorno a establecer
	 */
	public void setUsadoRetorno(final boolean pUsadoRetorno) {
		usadoRetorno = pUsadoRetorno;
	}

	/**
	 * Método de acceso a interno.
	 *
	 * @return interno
	 */
	public boolean isInterno() {
		return interno;
	}

	/**
	 * Método para establecer interno.
	 *
	 * @param interno
	 *                    interno a establecer
	 */
	public void setInterno(final boolean interno) {
		this.interno = interno;
	}

	/**
	 * Método de acceso a infoAutenticacion.
	 *
	 * @return infoAutenticacion
	 */
	public String getInfoAutenticacion() {
		return infoAutenticacion;
	}

	/**
	 * Método para establecer infoAutenticacion.
	 *
	 * @param infoAutenticacion
	 *                              infoAutenticacion a establecer
	 */
	public void setInfoAutenticacion(final String infoAutenticacion) {
		this.infoAutenticacion = infoAutenticacion;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a tituloProcedimiento.
	 *
	 * @return tituloProcedimiento
	 */
	public String getTituloProcedimiento() {
		return tituloProcedimiento;
	}

	/**
	 * Método para establecer tituloProcedimiento.
	 *
	 * @param tituloProcedimiento
	 *                                tituloProcedimiento a establecer
	 */
	public void setTituloProcedimiento(final String tituloProcedimiento) {
		this.tituloProcedimiento = tituloProcedimiento;
	}

	/**
	 * Método de acceso a ticketFormularioExterno.
	 *
	 * @return ticketFormularioExterno
	 */
	public String getTicketFormularioExterno() {
		return ticketFormularioExterno;
	}

	/**
	 * Método para establecer ticketFormularioExterno.
	 *
	 * @param ticketFormularioExterno
	 *                                    ticketFormularioExterno a establecer
	 */
	public void setTicketFormularioExterno(final String ticketFormularioExterno) {
		this.ticketFormularioExterno = ticketFormularioExterno;
	}

	/**
	 * Método de acceso a entidad.
	 *
	 * @return entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * Método para establecer entidad.
	 *
	 * @param entidad
	 *                    entidad a establecer
	 */
	public void setEntidad(final String entidad) {
		this.entidad = entidad;
	}

	/**
	 * Método de acceso a idGestorFormulariosExterno.
	 *
	 * @return idGestorFormulariosExterno
	 */
	public String getIdGestorFormulariosExterno() {
		return idGestorFormulariosExterno;
	}

	/**
	 * Método para establecer idGestorFormulariosExterno.
	 *
	 * @param idGestorFormulariosExterno
	 *                                       idGestorFormulariosExterno a establecer
	 */
	public void setIdGestorFormulariosExterno(final String idGestorFormulariosExterno) {
		this.idGestorFormulariosExterno = idGestorFormulariosExterno;
	}

	/**
	 * Método de acceso a codigoSiaProcedimiento.
	 * 
	 * @return codigoSiaProcedimiento
	 */
	public String getCodigoSiaProcedimiento() {
		return codigoSiaProcedimiento;
	}

	/**
	 * Método para establecer codigoSiaProcedimiento.
	 * 
	 * @param codigoSiaProcedimiento
	 *                                   codigoSiaProcedimiento a establecer
	 */
	public void setCodigoSiaProcedimiento(final String codigoSiaProcedimiento) {
		this.codigoSiaProcedimiento = codigoSiaProcedimiento;
	}

}
