package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Datos de inicio de sesión en un gestor de formularios.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInicioSesionFormulario implements Serializable {

	/** Ticket sesion formulario. */
	private String ticket;

	/** Id sesión de tramitación. */
	private String idSesionTramitacion;

	/** Entidad. */
	private String entidad;

	/** Id paso en el que se encuentra el formulario. */
	private String idPaso;

	/** Idioma. */
	private String idioma;

	/** Información autenticación. */
	private UsuarioAutenticadoInfo infoAutenticacion;

	/** Id tramite. */
	private String idTramite;

	/** Versión trámite. */
	private int versionTramite;

	/** Release trámite. */
	private int releaseTramite;

	/** Id formulario. */
	private String idFormulario;

	/** Indica si el formulario es interno. */
	private boolean interno;

	/** Xml con los datos actuales del formulario. */
	private byte[] xmlDatosActuales;

	/** Parametros apertura del formulario. */
	private ParametrosAperturaFormulario parametros;

	/** Id gestor formularios (para externos). */
	private String idGestorFormulariosExterno;

	/** Id formulario externo (para externos). */
	private String idFormularioExterno;

	/** Título procedimiento. */
	private String tituloProcedimiento;

	/** Código sia procedimiento. */
	private String codigoSiaProcedimiento;

	/** Código dir3 responsable. */
	private String dir3ResponsableProcedimiento;

	/**
	 * Método de acceso a idSesionTramitacion.
	 *
	 * @return idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Método para establecer idSesionTramitacion.
	 *
	 * @param idSesionTramitacion
	 *                                idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	/**
	 * Método de acceso a idPaso.
	 *
	 * @return idPaso
	 */
	public String getIdPaso() {
		return idPaso;
	}

	/**
	 * Método para establecer idPaso.
	 *
	 * @param idPaso
	 *                   idPaso a establecer
	 */
	public void setIdPaso(final String idPaso) {
		this.idPaso = idPaso;
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
	 * @param idTramite
	 *                      idTramite a establecer
	 */
	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
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
	 * @param versionTramite
	 *                           versionTramite a establecer
	 */
	public void setVersionTramite(final int versionTramite) {
		this.versionTramite = versionTramite;
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
	 * @param releaseTramite
	 *                           releaseTramite a establecer
	 */
	public void setReleaseTramite(final int releaseTramite) {
		this.releaseTramite = releaseTramite;
	}

	/**
	 * Método de acceso a idFormulario.
	 *
	 * @return idFormulario
	 */
	public String getIdFormulario() {
		return idFormulario;
	}

	/**
	 * Método para establecer idFormulario.
	 *
	 * @param idFormulario
	 *                         idFormulario a establecer
	 */
	public void setIdFormulario(final String idFormulario) {
		this.idFormulario = idFormulario;
	}

	/**
	 * Método de acceso a xmlDatosActuales.
	 *
	 * @return xmlDatosActuales
	 */
	public byte[] getXmlDatosActuales() {
		return xmlDatosActuales;
	}

	/**
	 * Método para establecer xmlDatosActuales.
	 *
	 * @param xmlDatosActuales
	 *                             xmlDatosActuales a establecer
	 */
	public void setXmlDatosActuales(final byte[] xmlDatosActuales) {
		this.xmlDatosActuales = xmlDatosActuales;
	}

	/**
	 * Método de acceso a parametros.
	 *
	 * @return parametros
	 */
	public ParametrosAperturaFormulario getParametros() {
		return parametros;
	}

	/**
	 * Método para establecer parametros.
	 *
	 * @param parametros
	 *                       parametros a establecer
	 */
	public void setParametros(final ParametrosAperturaFormulario parametros) {
		this.parametros = parametros;
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
	 * Método de acceso a idFormularioExterno.
	 *
	 * @return idFormularioExterno
	 */
	public String getIdFormularioExterno() {
		return idFormularioExterno;
	}

	/**
	 * Método para establecer idFormularioExterno.
	 *
	 * @param idFormularioExterno
	 *                                idFormularioExterno a establecer
	 */
	public void setIdFormularioExterno(final String idFormularioExterno) {
		this.idFormularioExterno = idFormularioExterno;
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
	public UsuarioAutenticadoInfo getInfoAutenticacion() {
		return infoAutenticacion;
	}

	/**
	 * Método para establecer infoAutenticacion.
	 *
	 * @param infoAutenticacion
	 *                              infoAutenticacion a establecer
	 */
	public void setInfoAutenticacion(final UsuarioAutenticadoInfo infoAutenticacion) {
		this.infoAutenticacion = infoAutenticacion;
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
	 * Método de acceso a ticket.
	 *
	 * @return ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Método para establecer ticket.
	 *
	 * @param ticket
	 *                   ticket a establecer
	 */
	public void setTicket(final String ticket) {
		this.ticket = ticket;
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

	/**
	 * Método de acceso a dir3Responsable.
	 * 
	 * @return dir3Responsable
	 */
	public String getDir3ResponsableProcedimiento() {
		return dir3ResponsableProcedimiento;
	}

	/**
	 * Método para establecer dir3Responsable.
	 * 
	 * @param dir3Responsable
	 *                            dir3Responsable a establecer
	 */
	public void setDir3ResponsableProcedimiento(final String dir3Responsable) {
		this.dir3ResponsableProcedimiento = dir3Responsable;
	}

}
