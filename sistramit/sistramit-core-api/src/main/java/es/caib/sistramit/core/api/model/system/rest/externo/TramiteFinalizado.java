package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.api.model.flujo.FuncionarioHabilitado;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Tramite finalizado. (RestApiExternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TramiteFinalizado implements Serializable {

	/** Id sesión tramitación. */
	private String idSesionTramitacion;
	/** Idioma. */
	private String idioma;
	/** Id trámite. */
	private String idTramite;
	/** Version Tramite. */
	private int versionTramite;
	/** Descripcion Tramite */
	private String descripcionTramite;
	/** Id entidad. */
	private String idEntidad;
	/** Código Procedimiento catálogo procedimientos. */
	private String idProcedimientoCP;
	/** Id procedimiento SIA */
	private String idProcedimientoSIA;
	/** Fecha finalización. */
	private Date fechaFin;
	/** Autenticación. */
	private TypeAutenticacion autenticacion;
	/** Método autenticación. */
	private TypeMetodoAutenticacion metodoAutenticacion;
	/** Nif. */
	private String nif;
	/** Nombre y apellidos. */
	private String nombreApellidos;
	/** Número registro  (en caso registro). */
	private String numeroRegistro;
	/** Fecha registro (en caso registro). */
	private Date fechaRegistro;
	/** Número entrega (si modo CES2). */
	private String numeroEntrega;
	/** Funcionario habilitado: USERNAME. */
	private String funcionarioHabilitadoUsername;
	/** Funcionario habilitado: NIF. */
	private String funcionarioHabilitadoNif;
	/** Funcionario habilitado: Nombre. */
	private String funcionarioHabilitadoNombre;
	/** Funcionario habilitado: Apellido 1. */
	private String funcionarioHabilitadoApellido1;
	/** Funcionario habilitado: Apellido 2. */
	private String funcionarioHabilitadoApellido2;
	/** Funcionario habilitado: Id actuación. */
	private String funcionarioHabilitadoIdActuacion;
	/** Funcionario habilitado: Fecha aviso si tiene establecido id actuación. */
	private Date funcionarioHabilitadoFechaAviso;

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
	 * Método de acceso a descripcionTramite.
	 * 
	 * @return descripcionTramite
	 */
	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	/**
	 * Método para establecer descripcionTramite.
	 * 
	 * @param descripcionTramite
	 *                               descripcionTramite a establecer
	 */
	public void setDescripcionTramite(final String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}

	/**
	 * Método de acceso a idProcedimientoSIA.
	 * 
	 * @return idProcedimientoSIA
	 */
	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	/**
	 * Método para establecer idProcedimientoSIA.
	 * 
	 * @param idProcedimientoSIA
	 *                               idProcedimientoSIA a establecer
	 */
	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	/**
	 * Método de acceso a fechaFin.
	 * 
	 * @return fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * Método para establecer fechaFin.
	 * 
	 * @param fechaFin
	 *                     fechaFin a establecer
	 */
	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Método de acceso a autenticacion.
	 * 
	 * @return autenticacion
	 */
	public TypeAutenticacion getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 * 
	 * @param autenticacion
	 *                          autenticacion a establecer
	 */
	public void setAutenticacion(final TypeAutenticacion autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 * 
	 * @return metodoAutenticacion
	 */
	public TypeMetodoAutenticacion getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 * 
	 * @param metodoAutenticacion
	 *                                metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final TypeMetodoAutenticacion metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}

	/**
	 * Método de acceso a nif.
	 * 
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 * 
	 * @param nif
	 *                nif a establecer
	 */
	public void setNif(final String nif) {
		this.nif = nif;
	}

	/**
	 * Método de acceso a nombreApellidos.
	 * 
	 * @return nombreApellidos
	 */
	public String getNombreApellidos() {
		return nombreApellidos;
	}

	/**
	 * Método para establecer nombreApellidos.
	 * 
	 * @param nombreApellidos
	 *                            nombreApellidos a establecer
	 */
	public void setNombreApellidos(final String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	/**
	 * Método de acceso a numeroRegistro.
	 * 
	 * @return numeroRegistro
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Método para establecer numeroRegistro.
	 * 
	 * @param numeroRegistro
	 *                           numeroRegistro a establecer
	 */
	public void setNumeroRegistro(final String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Método de acceso a numeroEntrega.
	 *
	 * @return numeroEntrega
	 */
	public String getNumeroEntrega() {
		return numeroEntrega;
	}
	/**
	 * Método para establecer numeroEntrega.
	 *
	 * @param numeroEntrega
	 *                           numeroEntrega a establecer
	 */
	public void setNumeroEntrega(String numeroEntrega) {
		this.numeroEntrega = numeroEntrega;
	}

	/**
	 * Método de acceso a idProcedimientoCP.
	 *
	 * @return idProcedimientoCP
	 */
	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	/**
	 * Método para establecer idProcedimientoCP.
	 *
	 * @param idProcedimientoCP
	 *                           idProcedimientoCP a establecer
	 */
	public void setIdProcedimientoCP(String idProcedimientoCP) {
		this.idProcedimientoCP = idProcedimientoCP;
	}

	/**
	 * Método de acceso a fechaRegistro.
	 *
	 * @return fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Método para establecer fechaRegistro.
	 *
	 * @param fechaRegistro
	 *                           fechaRegistro a establecer
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Método de acceso a funcionarioHabilitadoNif.
	 *
	 * @return funcionarioHabilitadoNif
	 */
	public String getFuncionarioHabilitadoNif() {
		return funcionarioHabilitadoNif;
	}

	/**
	 * Método para establecer funcionarioHabilitadoNif.
	 *
	 * @param funcionarioHabilitadoNif
	 *                                funcionarioHabilitadoNif a establecer
	 */
	public void setFuncionarioHabilitadoNif(final String funcionarioHabilitadoNif) {
		this.funcionarioHabilitadoNif = funcionarioHabilitadoNif;
	}

	/**
	 * Método de acceso a funcionarioHabilitadoNombre.
	 *
	 * @return funcionarioHabilitadoNombre
	 */
	public String getFuncionarioHabilitadoNombre() {
		return funcionarioHabilitadoNombre;
	}

	/**
	 * Método para establecer funcionarioHabilitadoNombre.
	 *
	 * @param funcionarioHabilitadoNombre
	 *                                    funcionarioHabilitadoNombre a establecer
	 */
	public void setFuncionarioHabilitadoNombre(final String funcionarioHabilitadoNombre) {
		this.funcionarioHabilitadoNombre = funcionarioHabilitadoNombre;
	}

	/**
	 * Método de acceso a funcionarioHabilitadoApellido1.
	 *
	 * @return funcionarioHabilitadoApellido1
	 */
	public String getFuncionarioHabilitadoApellido1() {
		return funcionarioHabilitadoApellido1;
	}

	/**
	 * Método para establecer funcionarioHabilitadoApellido1.
	 *
	 * @param funcionarioHabilitadoApellido1
	 *                                       funcionarioHabilitadoApellido1 a establecer
	 */
	public void setFuncionarioHabilitadoApellido1(final String funcionarioHabilitadoApellido1) {
		this.funcionarioHabilitadoApellido1 = funcionarioHabilitadoApellido1;
	}

	/**
	 * Método de acceso a funcionarioHabilitadoApellido2.
	 *
	 * @return funcionarioHabilitadoApellido2
	 */
	public String getFuncionarioHabilitadoApellido2() {
		return funcionarioHabilitadoApellido2;
	}

	/**
	 * Método para establecer funcionarioHabilitadoApellido2.
	 *
	 * @param funcionarioHabilitadoApellido2
	 *                                       funcionarioHabilitadoApellido2 a establecer
	 */
	public void setFuncionarioHabilitadoApellido2(final String funcionarioHabilitadoApellido2) {
		this.funcionarioHabilitadoApellido2 = funcionarioHabilitadoApellido2;
	}

	/**
	 * Método de acceso a funcionarioHabilitadoUsername.
	 *
	 * @return funcionarioHabilitadoUsername
	 */
	public String getFuncionarioHabilitadoUsername() {
		return funcionarioHabilitadoUsername;
	}

	/**
	 * Método para establecer funcionarioHabilitadoUsername.
	 *
	 * @param funcionarioHabilitadoUsername
	 *                                       funcionarioHabilitadoUsername a establecer
	 */
	public void setFuncionarioHabilitadoUsername(final String funcionarioHabilitadoUsername) {
		this.funcionarioHabilitadoUsername = funcionarioHabilitadoUsername;
	}

	/**
	 * Método de acceso a funcionarioHabilitadoIdActuacion.
	 *
	 * @return funcionarioHabilitadoIdActuacion
	 */
	public String getFuncionarioHabilitadoIdActuacion() {
		return funcionarioHabilitadoIdActuacion;
	}

	/**
	 * Método para establecer funcionarioHabilitadoIdActuacion.
	 *
	 * @param funcionarioHabilitadoIdActuacion
	 *                                       funcionarioHabilitadoIdActuacion a establecer
	 */
	public void setFuncionarioHabilitadoIdActuacion(final String funcionarioHabilitadoIdActuacion) {
		this.funcionarioHabilitadoIdActuacion = funcionarioHabilitadoIdActuacion;
	}

	/**
	 * Método de acceso a funcionarioHabilitadoFechaAviso.
	 *
	 * @return funcionarioHabilitadoFechaAviso
	 */
	public Date getFuncionarioHabilitadoFechaAviso() {
		return funcionarioHabilitadoFechaAviso;
	}

	/**
	 * Método para establecer funcionarioHabilitadoFechaAviso.
	 *
	 * @param funcionarioHabilitadoFechaAviso
	 *                                       funcionarioHabilitadoFechaAviso a establecer
	 */
	public void setFuncionarioHabilitadoFechaAviso(final Date funcionarioHabilitadoFechaAviso) {
		this.funcionarioHabilitadoFechaAviso = funcionarioHabilitadoFechaAviso;
	}

	/**
	 * Devuelve el idEntidad.
	 * @return idEntidad
	 */
	public String getIdEntidad() {
		return idEntidad;
	}

	/**
	 * Establece el idEntidad.
	 * @param idEntidad el nuevo idEntidad
	 */
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
}
