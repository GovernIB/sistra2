package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;

/**
 * Mapeo tabla STT_TRAFIN.
 */
@Entity
@Table(name = "STT_TRAFIN")
@SuppressWarnings("serial")
public final class HTramiteFinalizado implements IModelApi {

	/** Atributo codigo. */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_TRAFIN_SEQ")
	@SequenceGenerator(name = "STT_TRAFIN_SEQ", sequenceName = "STT_TRAFIN_SEQ", allocationSize = ConstantesNumero.N1)
	@Column(name = "TRF_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
	private Long codigo;

	/** Atributo id sesion tramitacion. */
	@Column(name = "TRF_IDESTR")
	private String idSesionTramitacion;

	/** Atributo fecha registro. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRF_FECFIN")
	private Date fechaFinalizacion;

	/** Atributo idioma. */
	@Column(name = "TRF_IDIOMA")
	private String idioma;

	/** Atributo id tramite. */
	@Column(name = "TRF_IDETRA")
	private String idTramite;

	/** Atributo version tramite. */
	@Column(name = "TRF_VERTRA")
	private int versionTramite;

	/** Atributo descripción tramite. */
	@Column(name = "TRF_DESTRA")
	private String descripcionTramite;

	/** Código Procedimiento catálogo procedimientos. */
	@Column(name = "TRF_PROCP")
	private String idProcedimientoCP;

	/** Atributo procedimiento SIA. */
	@Column(name = "TRF_PROSIA")
	private String idProcedimientoSIA;

	/** Atributo autenticacion. */
	@Column(name = "TRF_NIVAUT")
	private String autenticacion;

	/** Atributo metodo autenticacion inicio tramite. */
	@Column(name = "TRF_METAUT")
	private String metodoAutenticacion;

	/** Atributo nif iniciador. */
	@Column(name = "TRF_NIFINI")
	private String nifIniciador;

	/** Atributo nombre iniciador. */
	@Column(name = "TRF_NOMINI")
	private String nombreIniciador;

	/** Atributo nif presentador. */
	@Column(name = "TRF_NIFFIN")
	private String nifPresentador;

	/** Atributo nombre presentador. */
	@Column(name = "TRF_NOMFIN")
	private String nombrePresentador;

	/** Atributo numero registro (en caso registro). */
	@Column(name = "TRF_NUMREG")
	private String numeroRegistro;

	/** Fecha registro (en caso registro). */
	@Column(name = "TRF_FECREG")
	private Date fechaRegistro;

	/** Atributo numero entrega (si modo CES2). */
	@Column(name = "TRF_NUMENV")
	private String numeroEntrega;

	/** Funcionario habilitado: USERNAME. */
	@Column(name = "TRF_FHUSER")
	private String funcionarioHabilitadoUsername;

	/** Funcionario habilitado: NIF. */
	@Column(name = "TRF_FHNIF")
	private String funcionarioHabilitadoNif;

	/** Funcionario habilitado: Nombre. */
	@Column(name = "TRF_FHNOM")
	private String funcionarioHabilitadoNombre;

	/** Funcionario habilitado: Apellido 1. */
	@Column(name = "TRF_FHAPE1")
	private String funcionarioHabilitadoApellido1;

	/** Funcionario habilitado: Apellido 2. */
	@Column(name = "TRF_FHAPE2")
	private String funcionarioHabilitadoApellido2;

	/**
	 * Método de acceso a codigo.
	 *
	 * @return codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 *
	 * @param codigo
	 *                   codigo a establecer
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
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
	 * Método de acceso a fechaRegistro.
	 *
	 * @return fechaRegistro
	 */
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	/**
	 * Método para establecer fechaRegistro.
	 *
	 * @param fechaRegistro
	 *                          fechaRegistro a establecer
	 */
	public void setFechaFinalizacion(final Date fechaRegistro) {
		this.fechaFinalizacion = fechaRegistro;
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
	 * Método de acceso a autenticacion.
	 *
	 * @return autenticacion
	 */
	public String getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 *
	 * @param autenticacion
	 *                          autenticacion a establecer
	 */
	public void setAutenticacion(final String autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 *
	 * @return metodoAutenticacion
	 */
	public String getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 *
	 * @param metodoAutenticacion
	 *                                metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final String metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}

	/**
	 * Método de acceso a nifIniciador.
	 *
	 * @return nifIniciador
	 */
	public String getNifIniciador() {
		return nifIniciador;
	}

	/**
	 * Método para establecer nifIniciador.
	 *
	 * @param nifIniciador
	 *                         nifIniciador a establecer
	 */
	public void setNifIniciador(final String nifIniciador) {
		this.nifIniciador = nifIniciador;
	}

	/**
	 * Método de acceso a nombreIniciador.
	 *
	 * @return nombreIniciador
	 */
	public String getNombreIniciador() {
		return nombreIniciador;
	}

	/**
	 * Método para establecer nombreIniciador.
	 *
	 * @param nombreIniciador
	 *                            nombreIniciador a establecer
	 */
	public void setNombreIniciador(final String nombreIniciador) {
		this.nombreIniciador = nombreIniciador;
	}

	/**
	 * Método de acceso a nifPresentador.
	 *
	 * @return nifPresentador
	 */
	public String getNifPresentador() {
		return nifPresentador;
	}

	/**
	 * Método para establecer nifPresentador.
	 *
	 * @param nifPresentador
	 *                           nifPresentador a establecer
	 */
	public void setNifPresentador(final String nifPresentador) {
		this.nifPresentador = nifPresentador;
	}

	/**
	 * Método de acceso a nombrePresentador.
	 *
	 * @return nombrePresentador
	 */
	public String getNombrePresentador() {
		return nombrePresentador;
	}

	/**
	 * Método para establecer nombrePresentador.
	 *
	 * @param nombrePresentador
	 *                              nombrePresentador a establecer
	 */
	public void setNombrePresentador(final String nombrePresentador) {
		this.nombrePresentador = nombrePresentador;
	}

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
	 * Método de acceso a fhNif.
	 *
	 * @return fhNif
	 */
	public String getFuncionarioHabilitadoNif() {
		return funcionarioHabilitadoNif;
	}

	/**
	 * Método para establecer fhNif.
	 *
	 * @param fhNif
	 *                   fhNif a establecer
	 */
	public void setFuncionarioHabilitadoNif(final String fhNif) {
		this.funcionarioHabilitadoNif = fhNif;
	}

	/**
	 * Método de acceso a fhNombre.
	 *
	 * @return fhNombre
	 */
	public String getFuncionarioHabilitadoNombre() {
		return funcionarioHabilitadoNombre;
	}

	/**
	 * Método para establecer fhNombre.
	 *
	 * @param fhNombre
	 *                   fhNombre a establecer
	 */
	public void setFuncionarioHabilitadoNombre(final String fhNombre) {
		this.funcionarioHabilitadoNombre = fhNombre;
	}

	/**
	 * Método de acceso a fhApellido1.
	 *
	 * @return fhApellido1
	 */
	public String getFuncionarioHabilitadoApellido1() {
		return funcionarioHabilitadoApellido1;
	}

	/**
	 * Método para establecer fhApellido1.
	 *
	 * @param fhApellido1
	 *                   fhApellido1 a establecer
	 */
	public void setFuncionarioHabilitadoApellido1(final String fhApellido1) {
		this.funcionarioHabilitadoApellido1 = fhApellido1;
	}

	/**
	 * Método de acceso a fhApellido2.
	 *
	 * @return fhApellido2
	 */
	public String getFuncionarioHabilitadoApellido2() {
		return funcionarioHabilitadoApellido2;
	}

	/**
	 * Método para establecer fhApellido2.
	 *
	 * @param fhApellido2
	 *                   fhApellido2 a establecer
	 */
	public void setFuncionarioHabilitadoApellido2(final String fhApellido2) {
		this.funcionarioHabilitadoApellido2 = fhApellido2;
	}

	/**
	 * Método de acceso a funcionarioUsername.
	 *
	 * @return funcionarioUsername
	 */
	public String getFuncionarioHabilitadoUsername() {
		return funcionarioHabilitadoUsername;
	}

	/**
	 * Método para establecer funcionarioUsername.
	 *
	 * @param funcionarioUsername
	 *                   funcionarioUsername a establecer
	 */
	public void setFuncionarioHabilitadoUsername(final String funcionarioUsername) {
		this.funcionarioHabilitadoUsername = funcionarioUsername;
	}


	/**
	 * Convierte a model.
	 * 
	 * @param h
	 *              Objeto hibernate
	 * @return Objeto modelo
	 */
	public static TramiteFinalizado toModel(final HTramiteFinalizado h) {
		TramiteFinalizado m = null;
		if (h != null) {
			m = new TramiteFinalizado();
			m.setIdSesionTramitacion(h.getIdSesionTramitacion());
			m.setFechaFin(h.getFechaFinalizacion());
			m.setIdTramite(h.getIdTramite());
			m.setVersionTramite(h.getVersionTramite());
			m.setDescripcionTramite(h.getDescripcionTramite());
			m.setIdProcedimientoCP(h.getIdProcedimientoCP());
			m.setIdProcedimientoSIA(h.getIdProcedimientoSIA());
			m.setAutenticacion(TypeAutenticacion.fromString(h.getAutenticacion()));
			m.setMetodoAutenticacion(TypeMetodoAutenticacion.fromString(h.getMetodoAutenticacion()));
			m.setNif(h.getNifPresentador());
			m.setNombreApellidos(h.getNombrePresentador());
			m.setIdioma(h.getIdioma());
			m.setNumeroRegistro(h.getNumeroRegistro());
			m.setFechaRegistro(h.getFechaRegistro());
			m.setNumeroEntrega(h.getNumeroEntrega());
			m.setFuncionarioHabilitadoUsername(h.getFuncionarioHabilitadoUsername());
			m.setFuncionarioHabilitadoNif(h.getFuncionarioHabilitadoNif());
			m.setFuncionarioHabilitadoNombre(h.getFuncionarioHabilitadoNombre());
			m.setFuncionarioHabilitadoApellido1(h.getFuncionarioHabilitadoApellido1());
			m.setFuncionarioHabilitadoApellido2(h.getFuncionarioHabilitadoApellido2());
		}
		return m;
	}

}
