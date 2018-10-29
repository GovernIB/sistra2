package es.caib.sistrages.core.service.repository.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;

/**
 * JVersionTramite
 */
@Entity
@Table(name = "STG_VERTRA", uniqueConstraints = @UniqueConstraint(columnNames = { "VTR_CODTRM", "VTR_NUMVER" }))
public class JVersionTramite implements IModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_VERTRA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_VERTRA_SEQ", sequenceName = "STG_VERTRA_SEQ")
	@Column(name = "VTR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Script inicialización trámite. **/
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "VTR_SCRINTRA")
	private JScript scriptInicializacionTramite;

	/** Script personalizacion. **/
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "VTR_SCRPER")
	private JScript scriptPersonalizacion;

	/** Mensaje desactivacion. **/
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "VTR_DESMEN")
	private JLiteral mensajeDesactivacion;

	/** Tramite. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VTR_CODTRM", nullable = false)
	private JTramite tramite;

	/** Numero versión. **/
	@Column(name = "VTR_NUMVER", nullable = false, precision = 2, scale = 0)
	private int numeroVersion;

	/** Tipo flujo. **/
	@Column(name = "VTR_TIPFLU", nullable = false, length = 1)
	private String tipoflujo;

	/** Autenticado. **/
	@Column(name = "VTR_AUTENT", nullable = false, precision = 1, scale = 0)
	private boolean autenticado;

	/** Nivel QAA . **/
	@Column(name = "VTR_AUTQAA", precision = 1, scale = 0)
	private Integer nivelQAA;

	/** No autenticado. **/
	@Column(name = "VTR_AUTENO", nullable = false, precision = 1, scale = 0)
	private boolean noAutenticado;

	/**
	 * Idiomas soportados, sin espacios y separados por ;. Por ejemplo, catalan y
	 * español sería ca;es
	 **/
	@Column(name = "VTR_IDISOP", nullable = false, length = 50)
	private String idiomasSoportados;

	/** Admite resitencia. **/
	@Column(name = "VTR_PERSIS", nullable = false, precision = 1, scale = 0)
	private boolean admitePersistencia;

	/** Persistencia infinita. **/
	@Column(name = "VTR_PERINF", nullable = false, precision = 1, scale = 0)
	private boolean persistenciaInfinita;

	/** Persistencia dias. **/
	@Column(name = "VTR_PERDIA", precision = 2, scale = 0)
	private Integer persistenciaDias;

	/** Bloqueada. **/
	@Column(name = "VTR_BLOQ", precision = 1, scale = 0)
	private Boolean bloqueada;

	/** Usuario id bloqueo. **/
	@Column(name = "VTR_BLOQID", length = 10)
	private String usuarioIdBloqueo;

	/** Usuario datos bloqueo. **/
	@Column(name = "VTR_BLOQUS")
	private String usuarioDatosBloqueo;

	/** Release. **/
	@Column(name = "VTR_RELEAS", precision = 8, scale = 0)
	private Integer release;

	/** Activa. */
	@Column(name = "VTR_ACTIVO", nullable = false, precision = 1, scale = 0)
	private boolean activa;

	/** Debug. **/
	@Column(name = "VTR_DEBUG", nullable = false, precision = 1, scale = 0)
	private boolean debug;

	/** Limite tramitacion. **/
	@Column(name = "VTR_LIMTIP", nullable = false, length = 1)
	private String limiteTramitacion;

	/** Limite tramitacion numero. **/
	@Column(name = "VTR_LIMNUM", precision = 5, scale = 0)
	private Integer limiteTramitacionNumero;

	/** Limite tramitacion intervalo. **/
	@Column(name = "VTR_LIMINT", precision = 2, scale = 0)
	private Integer limiteTramitacionIntervalo;

	/** Desactivacion temporal. **/
	@Column(name = "VTR_DESPLZ", nullable = false, precision = 1, scale = 0)
	private boolean desactivacionTemporal;

	/** Plazo inicio desactivacion. **/
	@Temporal(TemporalType.DATE)
	@Column(name = "VTR_DESINI", length = 7)
	private Date plazoInicioDesactivacion;

	/** Plazo fin desactivacion. **/
	@Temporal(TemporalType.DATE)
	@Column(name = "VTR_DESFIN", length = 7)
	private Date plazoFinDesactivacion;

	/** Constructor. **/
	public JVersionTramite() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the scriptInicializacionTramite
	 */
	public JScript getScriptInicializacionTramite() {
		return scriptInicializacionTramite;
	}

	/**
	 * @param scriptInicializacionTramite
	 *            the scriptInicializacionTramite to set
	 */
	public void setScriptInicializacionTramite(final JScript scriptInicializacionTramite) {
		this.scriptInicializacionTramite = scriptInicializacionTramite;
	}

	/**
	 * @return the scriptPersonalizacion
	 */
	public JScript getScriptPersonalizacion() {
		return scriptPersonalizacion;
	}

	/**
	 * @param scriptPersonalizacion
	 *            the scriptPersonalizacion to set
	 */
	public void setScriptPersonalizacion(final JScript scriptPersonalizacion) {
		this.scriptPersonalizacion = scriptPersonalizacion;
	}

	/**
	 * @return the mensajeDesactivacion
	 */
	public JLiteral getMensajeDesactivacion() {
		return mensajeDesactivacion;
	}

	/**
	 * @param mensajeDesactivacion
	 *            the mensajeDesactivacion to set
	 */
	public void setMensajeDesactivacion(final JLiteral mensajeDesactivacion) {
		this.mensajeDesactivacion = mensajeDesactivacion;
	}

	/**
	 * @return the tramite
	 */
	public JTramite getTramite() {
		return tramite;
	}

	/**
	 * @param tramite
	 *            the tramite to set
	 */
	public void setTramite(final JTramite tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the numeroVersion
	 */
	public int getNumeroVersion() {
		return numeroVersion;
	}

	/**
	 * @param numeroVersion
	 *            the numeroVersion to set
	 */
	public void setNumeroVersion(final int numeroVersion) {
		this.numeroVersion = numeroVersion;
	}

	/**
	 * @return the tipoflujo
	 */
	public String getTipoflujo() {
		return tipoflujo;
	}

	/**
	 * @param tipoflujo
	 *            the tipoflujo to set
	 */
	public void setTipoflujo(final String tipoflujo) {
		this.tipoflujo = tipoflujo;
	}

	/**
	 * @return the autenticado
	 */
	public boolean isAutenticado() {
		return autenticado;
	}

	/**
	 * @param autenticado
	 *            the autenticado to set
	 */
	public void setAutenticado(final boolean autenticado) {
		this.autenticado = autenticado;
	}

	/**
	 * @return the nivelQAA
	 */
	public Integer getNivelQAA() {
		return nivelQAA;
	}

	/**
	 * @param nivelQAA
	 *            the nivelQAA to set
	 */
	public void setNivelQAA(final Integer nivelQAA) {
		this.nivelQAA = nivelQAA;
	}

	/**
	 * @return the noAutenticado
	 */
	public boolean isNoAutenticado() {
		return noAutenticado;
	}

	/**
	 * @param noAutenticado
	 *            the noAutenticado to set
	 */
	public void setNoAutenticado(final boolean noAutenticado) {
		this.noAutenticado = noAutenticado;
	}

	/**
	 * @return the idiomasSoportados
	 */
	public String getIdiomasSoportados() {
		return idiomasSoportados;
	}

	/**
	 * @param idiomasSoportados
	 *            the idiomasSoportados to set
	 */
	public void setIdiomasSoportados(final String idiomasSoportados) {
		this.idiomasSoportados = idiomasSoportados;
	}

	/**
	 * @return the admitePersistencia
	 */
	public boolean isAdmitePersistencia() {
		return admitePersistencia;
	}

	/**
	 * @param admitePersistencia
	 *            the admitePersistencia to set
	 */
	public void setAdmitePersistencia(final boolean admitePersistencia) {
		this.admitePersistencia = admitePersistencia;
	}

	/**
	 * @return the persistenciaInfinita
	 */
	public boolean isPersistenciaInfinita() {
		return persistenciaInfinita;
	}

	/**
	 * @param persistenciaInfinita
	 *            the persistenciaInfinita to set
	 */
	public void setPersistenciaInfinita(final boolean persistenciaInfinita) {
		this.persistenciaInfinita = persistenciaInfinita;
	}

	/**
	 * @return the persistenciaDias
	 */
	public Integer getPersistenciaDias() {
		return persistenciaDias;
	}

	/**
	 * @param persistenciaDias
	 *            the persistenciaDias to set
	 */
	public void setPersistenciaDias(final Integer persistenciaDias) {
		this.persistenciaDias = persistenciaDias;
	}

	/**
	 * @return the bloqueada
	 */
	public Boolean getBloqueada() {
		return bloqueada;
	}

	/**
	 * @param bloqueada
	 *            the bloqueada to set
	 */
	public void setBloqueada(final Boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	/**
	 * @return the usuarioIdBloqueo
	 */
	public String getUsuarioIdBloqueo() {
		return usuarioIdBloqueo;
	}

	/**
	 * @param usuarioIdBloqueo
	 *            the usuarioIdBloqueo to set
	 */
	public void setUsuarioIdBloqueo(final String usuarioIdBloqueo) {
		this.usuarioIdBloqueo = usuarioIdBloqueo;
	}

	/**
	 * @return the usuarioDatosBloqueo
	 */
	public String getUsuarioDatosBloqueo() {
		return usuarioDatosBloqueo;
	}

	/**
	 * @param usuarioDatosBloqueo
	 *            the usuarioDatosBloqueo to set
	 */
	public void setUsuarioDatosBloqueo(final String usuarioDatosBloqueo) {
		this.usuarioDatosBloqueo = usuarioDatosBloqueo;
	}

	/**
	 * @return the release
	 */
	public Integer getRelease() {
		return release;
	}

	/**
	 * @param release
	 *            the release to set
	 */
	public void setRelease(final Integer release) {
		this.release = release;
	}

	/**
	 * @return the activa
	 */
	public boolean isActiva() {
		return activa;
	}

	/**
	 * @param activa
	 *            the activa to set
	 */
	public void setActiva(final boolean activa) {
		this.activa = activa;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the limiteTramitacion
	 */
	public String getLimiteTramitacion() {
		return limiteTramitacion;
	}

	/**
	 * @param limiteTramitacion
	 *            the limiteTramitacion to set
	 */
	public void setLimiteTramitacion(final String limiteTramitacion) {
		this.limiteTramitacion = limiteTramitacion;
	}

	/**
	 * @return the limiteTramitacionNumero
	 */
	public Integer getLimiteTramitacionNumero() {
		return limiteTramitacionNumero;
	}

	/**
	 * @param limiteTramitacionNumero
	 *            the limiteTramitacionNumero to set
	 */
	public void setLimiteTramitacionNumero(final Integer limiteTramitacionNumero) {
		this.limiteTramitacionNumero = limiteTramitacionNumero;
	}

	/**
	 * @return the limiteTramitacionIntervalo
	 */
	public Integer getLimiteTramitacionIntervalo() {
		return limiteTramitacionIntervalo;
	}

	/**
	 * @param limiteTramitacionIntervalo
	 *            the limiteTramitacionIntervalo to set
	 */
	public void setLimiteTramitacionIntervalo(final Integer limiteTramitacionIntervalo) {
		this.limiteTramitacionIntervalo = limiteTramitacionIntervalo;
	}

	/**
	 * @return the desactivacionTemporal
	 */
	public boolean isDesactivacionTemporal() {
		return desactivacionTemporal;
	}

	/**
	 * @param desactivacionTemporal
	 *            the desactivacionTemporal to set
	 */
	public void setDesactivacionTemporal(final boolean desactivacionTemporal) {
		this.desactivacionTemporal = desactivacionTemporal;
	}

	/**
	 * @return the plazoInicioDesactivacion
	 */
	public Date getPlazoInicioDesactivacion() {
		return plazoInicioDesactivacion;
	}

	/**
	 * @param plazoInicioDesactivacion
	 *            the plazoInicioDesactivacion to set
	 */
	public void setPlazoInicioDesactivacion(final Date plazoInicioDesactivacion) {
		this.plazoInicioDesactivacion = plazoInicioDesactivacion;
	}

	/**
	 * @return the plazoFinDesactivacion
	 */
	public Date getPlazoFinDesactivacion() {
		return plazoFinDesactivacion;
	}

	/**
	 * @param plazoFinDesactivacion
	 *            the plazoFinDesactivacion to set
	 */
	public void setPlazoFinDesactivacion(final Date plazoFinDesactivacion) {
		this.plazoFinDesactivacion = plazoFinDesactivacion;
	}

	/**
	 * ToModel.
	 *
	 * @return
	 */
	public TramiteVersion toModel() {
		final TramiteVersion tramiteVersion = new TramiteVersion();
		tramiteVersion.setActiva(this.isActiva());
		tramiteVersion.setAutenticado(this.isAutenticado());
		tramiteVersion.setNoAutenticado(this.isNoAutenticado());
		tramiteVersion.setBloqueada(this.getBloqueada());
		tramiteVersion.setCodigoUsuarioBloqueo(this.getUsuarioIdBloqueo());
		tramiteVersion.setDatosUsuarioBloqueo(this.getUsuarioDatosBloqueo());
		tramiteVersion.setDebug(this.isDebug());
		tramiteVersion.setDesactivacion(this.isDesactivacionTemporal());

		tramiteVersion.setCodigo(this.getCodigo());
		if (this.getTramite() != null) {
			tramiteVersion.setIdTramite(this.getTramite().getCodigo());
		}
		tramiteVersion.setIdiomasSoportados(this.getIdiomasSoportados());
		if (this.getScriptInicializacionTramite() != null) {
			tramiteVersion.setScriptInicializacionTramite(this.getScriptInicializacionTramite().toModel());
		}
		if (this.getScriptPersonalizacion() != null) {
			tramiteVersion.setScriptPersonalizacion(this.getScriptPersonalizacion().toModel());
		}
		tramiteVersion.setIntLimiteTramitacion(this.getLimiteTramitacionIntervalo());
		if (this.getLimiteTramitacion() != null && "S".equals(this.getLimiteTramitacion())) {
			tramiteVersion.setLimiteTramitacion(true);
		} else {
			tramiteVersion.setLimiteTramitacion(false);
		}
		tramiteVersion.setNumLimiteTramitacion(this.getLimiteTramitacionNumero());

		if (this.getMensajeDesactivacion() != null) {
			tramiteVersion.setMensajeDesactivacion(this.getMensajeDesactivacion().toModel());
		}

		tramiteVersion.setNivelQAA(this.getNivelQAA());
		tramiteVersion.setNumeroVersion(this.getNumeroVersion());
		tramiteVersion.setPersistencia(this.isAdmitePersistencia());
		tramiteVersion.setPersistenciaDias(this.getPersistenciaDias());
		tramiteVersion.setPersistenciaInfinita(this.isPersistenciaInfinita());
		tramiteVersion.setPlazoFinDesactivacion(this.getPlazoFinDesactivacion());
		tramiteVersion.setPlazoInicioDesactivacion(this.getPlazoInicioDesactivacion());
		tramiteVersion.setRelease(this.getRelease());
		if (this.getTipoflujo() != null) {
			tramiteVersion.setTipoFlujo(TypeFlujo.fromString(this.getTipoflujo()));
		}
		return tramiteVersion;
	}

	/**
	 * fromModel.
	 *
	 * @param model
	 * @return
	 */
	public static JVersionTramite fromModel(final TramiteVersion model) {
		JVersionTramite jversionTramite = null;
		if (model != null) {
			jversionTramite = new JVersionTramite();
			jversionTramite.setActiva(model.isActiva());
			jversionTramite.setAutenticado(model.isAutenticado());
			jversionTramite.setNoAutenticado(model.isNoAutenticado());
			jversionTramite.setBloqueada(model.getBloqueada());
			jversionTramite.setUsuarioIdBloqueo(model.getCodigoUsuarioBloqueo());
			jversionTramite.setUsuarioDatosBloqueo(model.getDatosUsuarioBloqueo());
			jversionTramite.setDebug(model.isDebug());
			jversionTramite.setDesactivacionTemporal(model.isDesactivacion());

			jversionTramite.setCodigo(model.getCodigo());
			jversionTramite.setIdiomasSoportados(model.getIdiomasSoportados());
			jversionTramite.setScriptInicializacionTramite(JScript.fromModel(model.getScriptInicializacionTramite()));
			jversionTramite.setScriptPersonalizacion(JScript.fromModel(model.getScriptPersonalizacion()));
			if (model.isLimiteTramitacion()) {
				jversionTramite.setLimiteTramitacion("S");
			} else {
				jversionTramite.setLimiteTramitacion("N");
			}
			jversionTramite.setLimiteTramitacionIntervalo(model.getIntLimiteTramitacion());
			jversionTramite.setLimiteTramitacionNumero(model.getNumLimiteTramitacion());
			jversionTramite.setMensajeDesactivacion(JLiteral.fromModel(model.getMensajeDesactivacion()));
			jversionTramite.setNivelQAA(model.getNivelQAA());
			jversionTramite.setNumeroVersion(model.getNumeroVersion());
			jversionTramite.setAdmitePersistencia(model.isPersistencia());
			jversionTramite.setPersistenciaDias(model.getPersistenciaDias());
			jversionTramite.setPersistenciaInfinita(model.isPersistenciaInfinita());
			jversionTramite.setPlazoFinDesactivacion(model.getPlazoFinDesactivacion());
			jversionTramite.setPlazoInicioDesactivacion(model.getPlazoInicioDesactivacion());
			jversionTramite.setRelease(model.getRelease());
			jversionTramite.setTipoflujo(model.getTipoFlujo().toString());
		}
		return jversionTramite;
	}

	/**
	 * Clona un jversionTramite.
	 *
	 * @return
	 */
	public static JVersionTramite clonar(final JVersionTramite origVersionTramite, final int numVersionNuevo) {
		JVersionTramite jversionTramite = null;

		if (origVersionTramite != null) {
			jversionTramite = new JVersionTramite();
			jversionTramite.setCodigo(null);
			jversionTramite.setRelease(1);
			jversionTramite.setNumeroVersion(numVersionNuevo);
			jversionTramite.setTramite(origVersionTramite.getTramite());
			jversionTramite.setActiva(origVersionTramite.isActiva());
			jversionTramite.setAutenticado(origVersionTramite.isAutenticado());
			jversionTramite.setNoAutenticado(origVersionTramite.isNoAutenticado());
			jversionTramite.setBloqueada(false);
			jversionTramite.setDebug(origVersionTramite.isDebug());
			jversionTramite.setDesactivacionTemporal(origVersionTramite.isDesactivacionTemporal());
			jversionTramite.setIdiomasSoportados(origVersionTramite.getIdiomasSoportados());
			jversionTramite.setScriptInicializacionTramite(
					JScript.clonar(origVersionTramite.getScriptInicializacionTramite()));
			jversionTramite.setScriptPersonalizacion(JScript.clonar(origVersionTramite.getScriptPersonalizacion()));
			jversionTramite.setLimiteTramitacion(origVersionTramite.getLimiteTramitacion());
			jversionTramite.setLimiteTramitacionIntervalo(origVersionTramite.getLimiteTramitacionIntervalo());
			jversionTramite.setLimiteTramitacionNumero(origVersionTramite.getLimiteTramitacionNumero());
			jversionTramite.setMensajeDesactivacion(JLiteral.clonar(origVersionTramite.getMensajeDesactivacion()));
			jversionTramite.setNivelQAA(origVersionTramite.getNivelQAA());
			jversionTramite.setAdmitePersistencia(origVersionTramite.isAdmitePersistencia());
			jversionTramite.setPersistenciaDias(origVersionTramite.getPersistenciaDias());
			jversionTramite.setPersistenciaInfinita(origVersionTramite.isPersistenciaInfinita());
			jversionTramite.setPlazoFinDesactivacion(origVersionTramite.getPlazoFinDesactivacion());
			jversionTramite.setPlazoInicioDesactivacion(origVersionTramite.getPlazoInicioDesactivacion());
			jversionTramite.setTipoflujo(origVersionTramite.getTipoflujo());
		}
		return jversionTramite;
	}

}
