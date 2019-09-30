package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Datos trámite proveniente del Catálogo de Procedimientos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DefinicionTramiteCP implements Serializable {

	/** Identificador trámite en el catálogo de procedimientos. */
	private String identificador;

	/** Título trámite. */
	private String descripcion;

	/** Email soporte incidencias. */
	private String emailSoporte;

	/** Procedimiento asociado. */
	private DefinicionProcedimientoCP procedimiento;

	/** Indica si es vigente. */
	private boolean vigente;

	/** Plazo inicio. */
	private Date plazoInicio;

	/** Plazo fin. */
	private Date plazoFin;

	/** Url info. */
	private String urlInformacion;

	/** Código destino del órgano DIR3. */
	private String organoDestinoDir3;

	/** Documentos asociados. */
	private List<DefinicionDocumentoTramiteCP> documentos;

	/** Tasas asociadas. */
	private List<DefinicionTasaTramiteCP> tasas;

	/** Indica si es telematico. */
	private boolean telematico;

	/** Definicion tramite telematico. **/
	private DefinicionTramiteTelematico tramiteTelematico;

	/**
	 * Método de acceso a identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer identificador.
	 *
	 * @param identificador identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * Método de acceso a descripcion.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para establecer descripcion.
	 *
	 * @param descripcion descripcion a establecer
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método de acceso a procedimiento.
	 *
	 * @return procedimiento
	 */
	public DefinicionProcedimientoCP getProcedimiento() {
		return procedimiento;
	}

	/**
	 * Método para establecer procedimiento.
	 *
	 * @param procedimiento procedimiento a establecer
	 */
	public void setProcedimiento(final DefinicionProcedimientoCP procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * Método de acceso a vigente.
	 *
	 * @return vigente
	 */
	public boolean isVigente() {
		return vigente;
	}

	/**
	 * Método para establecer vigente.
	 *
	 * @param vigente vigente a establecer
	 */
	public void setVigente(final boolean vigente) {
		this.vigente = vigente;
	}

	/**
	 * Método de acceso a plazoInicio.
	 *
	 * @return plazoInicio
	 */
	public Date getPlazoInicio() {
		return plazoInicio;
	}

	/**
	 * Método para establecer plazoInicio.
	 *
	 * @param plazoInicio plazoInicio a establecer
	 */
	public void setPlazoInicio(final Date plazoInicio) {
		this.plazoInicio = plazoInicio;
	}

	/**
	 * Método de acceso a plazoFin.
	 *
	 * @return plazoFin
	 */
	public Date getPlazoFin() {
		return plazoFin;
	}

	/**
	 * Método para establecer plazoFin.
	 *
	 * @param plazoFin plazoFin a establecer
	 */
	public void setPlazoFin(final Date plazoFin) {
		this.plazoFin = plazoFin;
	}

	/**
	 * Método de acceso a urlInformacion.
	 *
	 * @return urlInformacion
	 */
	public String getUrlInformacion() {
		return urlInformacion;
	}

	/**
	 * Método para establecer urlInformacion.
	 *
	 * @param urlInformacion urlInformacion a establecer
	 */
	public void setUrlInformacion(final String urlInformacion) {
		this.urlInformacion = urlInformacion;
	}

	/**
	 * Método de acceso a organoDestinoDir3.
	 *
	 * @return organoDestinoDir3
	 */
	public String getOrganoDestinoDir3() {
		return organoDestinoDir3;
	}

	/**
	 * Método para establecer organoDestinoDir3.
	 *
	 * @param organoDestinoDir3 organoDestinoDir3 a establecer
	 */
	public void setOrganoDestinoDir3(final String organoDestinoDir3) {
		this.organoDestinoDir3 = organoDestinoDir3;
	}

	/**
	 * Método de acceso a documentos.
	 *
	 * @return documentos
	 */
	public List<DefinicionDocumentoTramiteCP> getDocumentos() {
		return documentos;
	}

	/**
	 * Método para establecer documentos.
	 *
	 * @param documentos documentos a establecer
	 */
	public void setDocumentos(final List<DefinicionDocumentoTramiteCP> documentos) {
		this.documentos = documentos;
	}

	/**
	 * Método de acceso a tasas.
	 *
	 * @return tasas
	 */
	public List<DefinicionTasaTramiteCP> getTasas() {
		return tasas;
	}

	/**
	 * Método para establecer tasas.
	 *
	 * @param tasas tasas a establecer
	 */
	public void setTasas(final List<DefinicionTasaTramiteCP> tasas) {
		this.tasas = tasas;
	}

	/**
	 * Método de acceso a emailSoporte.
	 *
	 * @return emailSoporte
	 */
	public String getEmailSoporte() {
		return emailSoporte;
	}

	/**
	 * Método para establecer emailSoporte.
	 *
	 * @param emailSoporte emailSoporte a establecer
	 */
	public void setEmailSoporte(final String emailSoporte) {
		this.emailSoporte = emailSoporte;
	}

	/**
	 * @return the telematico
	 */
	public boolean isTelematico() {
		return telematico;
	}

	/**
	 * @param telematico the telematico to set
	 */
	public void setTelematico(final boolean telematico) {
		this.telematico = telematico;
	}

	/**
	 * @return the tramiteTelematico
	 */
	public DefinicionTramiteTelematico getTramiteTelematico() {
		return tramiteTelematico;
	}

	/**
	 * @param tramiteTelematico the tramiteTelematico to set
	 */
	public void setTramiteTelematico(final DefinicionTramiteTelematico tramiteTelematico) {
		this.tramiteTelematico = tramiteTelematico;
	}

}
