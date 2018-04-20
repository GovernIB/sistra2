package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;

/**
 * JVersionTramite
 */
@Entity
@Table(name = "STG_VERTRA", uniqueConstraints = @UniqueConstraint(columnNames = { "VTR_CODTRM", "VTR_NUMVER" }))
public class JVersionTramite implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_VERTRA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_VERTRA_SEQ", sequenceName = "STG_VERTRA_SEQ")
	@Column(name = "VTR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VTR_SCRINTRA")
	private JScript scriptInicializacionTramite;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VTR_SCRPER")
	private JScript scriptPersonalizacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VTR_DESMEN")
	private JLiteral mensajeDesactivacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VTR_CODTRM", nullable = false)
	private JTramite tramite;

	@Column(name = "VTR_NUMVER", nullable = false, precision = 2, scale = 0)
	private int numeroVersion;

	@Column(name = "VTR_TIPFLU", nullable = false, length = 1)
	private String tipoflujo;

	@Column(name = "VTR_AUTENT", nullable = false, precision = 1, scale = 0)
	private boolean autenticado;

	@Column(name = "VTR_AUTQAA", precision = 1, scale = 0)
	private Boolean nivelQAA;

	@Column(name = "VTR_AUTENO", nullable = false, precision = 1, scale = 0)
	private boolean noAutenticado;

	@Column(name = "VTR_IDISOP", nullable = false, length = 50)
	private String idiomasSoportados;

	@Column(name = "VTR_PERSIS", nullable = false, precision = 1, scale = 0)
	private boolean admitePersistencia;

	@Column(name = "VTR_PERINF", nullable = false, precision = 1, scale = 0)
	private boolean persistenciaInfinita;

	@Column(name = "VTR_PERDIA", precision = 2, scale = 0)
	private Byte persistenciaDias;

	@Column(name = "VTR_BLOQ", precision = 1, scale = 0)
	private Boolean bloqueada;

	@Column(name = "VTR_BLOQID", length = 10)
	private String usuarioIdBloqueo;

	@Column(name = "VTR_BLOQUS")
	private String usuarioDatosBloqueo;

	@Column(name = "VTR_RELEAS", precision = 8, scale = 0)
	private Integer release;

	@Column(name = "VTR_ACTIVO", nullable = false, precision = 1, scale = 0)
	private boolean activa;

	@Column(name = "VTR_DEBUG", nullable = false, precision = 1, scale = 0)
	private boolean debug;

	@Column(name = "VTR_LIMTIP", nullable = false, length = 1)
	private String limiteTramitacion;

	@Column(name = "VTR_LIMNUM", precision = 5, scale = 0)
	private Integer limiteTramitacionNumero;

	@Column(name = "VTR_LIMINT", precision = 2, scale = 0)
	private Byte limiteTramitacionIntervalo;

	@Column(name = "VTR_DESPLZ", nullable = false, precision = 1, scale = 0)
	private boolean desactivacionTemporal;

	@Temporal(TemporalType.DATE)
	@Column(name = "VTR_DESINI", length = 7)
	private Date plazoInicioDesactivacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "VTR_DESFIN", length = 7)
	private Date plazoFinDesactivacion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "versionTramite")
	private Set<JHistorialVersion> historialVersion = new HashSet<JHistorialVersion>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_DOMVER", joinColumns = {
			@JoinColumn(name = "DVT_CODVTR", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DVT_CODDOM", nullable = false, updatable = false) })
	private Set<JDominio> dominios = new HashSet<JDominio>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "versionTramite")
	private Set<JPasoTramitacion> pasoTramite = new HashSet<JPasoTramitacion>(0);

	public JVersionTramite() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JScript getScriptInicializacionTramite() {
		return this.scriptInicializacionTramite;
	}

	public void setScriptInicializacionTramite(final JScript scriptInicializacionTramite) {
		this.scriptInicializacionTramite = scriptInicializacionTramite;
	}

	public JScript getScriptPersonalizacion() {
		return this.scriptPersonalizacion;
	}

	public void setScriptPersonalizacion(final JScript scriptPersonalizacion) {
		this.scriptPersonalizacion = scriptPersonalizacion;
	}

	public JLiteral getMensajeDesactivacion() {
		return this.mensajeDesactivacion;
	}

	public void setMensajeDesactivacion(final JLiteral mensajeDesactivacion) {
		this.mensajeDesactivacion = mensajeDesactivacion;
	}

	public JTramite getTramite() {
		return this.tramite;
	}

	public void setTramite(final JTramite tramite) {
		this.tramite = tramite;
	}

	public int getNumeroVersion() {
		return this.numeroVersion;
	}

	public void setNumeroVersion(final int numeroVersion) {
		this.numeroVersion = numeroVersion;
	}

	public String getTipoflujo() {
		return this.tipoflujo;
	}

	public void setTipoflujo(final String tipoflujo) {
		this.tipoflujo = tipoflujo;
	}

	public boolean isAutenticado() {
		return this.autenticado;
	}

	public void setAutenticado(final boolean autenticado) {
		this.autenticado = autenticado;
	}

	public Boolean getNivelQAA() {
		return this.nivelQAA;
	}

	public void setNivelQAA(final Boolean nivelQAA) {
		this.nivelQAA = nivelQAA;
	}

	public boolean isNoAutenticado() {
		return this.noAutenticado;
	}

	public void setNoAutenticado(final boolean noAutenticado) {
		this.noAutenticado = noAutenticado;
	}

	public String getIdiomasSoportados() {
		return this.idiomasSoportados;
	}

	public void setIdiomasSoportados(final String idiomasSoportados) {
		this.idiomasSoportados = idiomasSoportados;
	}

	public boolean isAdmitePersistencia() {
		return this.admitePersistencia;
	}

	public void setAdmitePersistencia(final boolean admitePersistencia) {
		this.admitePersistencia = admitePersistencia;
	}

	public boolean isPersistenciaInfinita() {
		return this.persistenciaInfinita;
	}

	public void setPersistenciaInfinita(final boolean persistenciaInfinita) {
		this.persistenciaInfinita = persistenciaInfinita;
	}

	public Byte getPersistenciaDias() {
		return this.persistenciaDias;
	}

	public void setPersistenciaDias(final Byte persistenciaDias) {
		this.persistenciaDias = persistenciaDias;
	}

	public Boolean getBloqueada() {
		return this.bloqueada;
	}

	public void setBloqueada(final Boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	public String getUsuarioIdBloqueo() {
		return this.usuarioIdBloqueo;
	}

	public void setUsuarioIdBloqueo(final String usuarioIdBloqueo) {
		this.usuarioIdBloqueo = usuarioIdBloqueo;
	}

	public String getUsuarioDatosBloqueo() {
		return this.usuarioDatosBloqueo;
	}

	public void setUsuarioDatosBloqueo(final String usuarioDatosBloqueo) {
		this.usuarioDatosBloqueo = usuarioDatosBloqueo;
	}

	public Integer getRelease() {
		return this.release;
	}

	public void setRelease(final Integer release) {
		this.release = release;
	}

	public boolean isActiva() {
		return this.activa;
	}

	public void setActiva(final boolean activa) {
		this.activa = activa;
	}

	public boolean isDebug() {
		return this.debug;
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	public String getLimiteTramitacion() {
		return this.limiteTramitacion;
	}

	public void setLimiteTramitacion(final String limiteTramitacion) {
		this.limiteTramitacion = limiteTramitacion;
	}

	public Integer getLimiteTramitacionNumero() {
		return this.limiteTramitacionNumero;
	}

	public void setLimiteTramitacionNumero(final Integer limiteTramitacionNumero) {
		this.limiteTramitacionNumero = limiteTramitacionNumero;
	}

	public Byte getLimiteTramitacionIntervalo() {
		return this.limiteTramitacionIntervalo;
	}

	public void setLimiteTramitacionIntervalo(final Byte limiteTramitacionIntervalo) {
		this.limiteTramitacionIntervalo = limiteTramitacionIntervalo;
	}

	public boolean isDesactivacionTemporal() {
		return this.desactivacionTemporal;
	}

	public void setDesactivacionTemporal(final boolean desactivacionTemporal) {
		this.desactivacionTemporal = desactivacionTemporal;
	}

	public Date getPlazoInicioDesactivacion() {
		return this.plazoInicioDesactivacion;
	}

	public void setPlazoInicioDesactivacion(final Date plazoInicioDesactivacion) {
		this.plazoInicioDesactivacion = plazoInicioDesactivacion;
	}

	public Date getPlazoFinDesactivacion() {
		return this.plazoFinDesactivacion;
	}

	public void setPlazoFinDesactivacion(final Date plazoFinDesactivacion) {
		this.plazoFinDesactivacion = plazoFinDesactivacion;
	}

	public Set<JHistorialVersion> getHistorialVersion() {
		return this.historialVersion;
	}

	public void setHistorialVersion(final Set<JHistorialVersion> historialVersion) {
		this.historialVersion = historialVersion;
	}

	public Set<JDominio> getDominios() {
		return this.dominios;
	}

	public void setDominios(final Set<JDominio> dominios) {
		this.dominios = dominios;
	}

	public Set<JPasoTramitacion> getPasoTramite() {
		return this.pasoTramite;
	}

	public void setPasoTramite(final Set<JPasoTramitacion> pasoTramite) {
		this.pasoTramite = pasoTramite;
	}

	public TramiteVersion toModel() {
		final TramiteVersion tramiteVersion = new TramiteVersion();
		tramiteVersion.setActiva(this.isActiva());
		tramiteVersion.setAutenticado(this.isAutenticado());
		tramiteVersion.setNoAutenticado(this.isNoAutenticado());
		if (this.getBloqueada()) {
			tramiteVersion.setBloqueada(1);
		} else {
			tramiteVersion.setBloqueada(0);
		}
		tramiteVersion.setCodigoUsuarioBloqueo(this.getUsuarioIdBloqueo());
		tramiteVersion.setDatosUsuarioBloqueo(this.getUsuarioDatosBloqueo());
		tramiteVersion.setDebug(this.isDebug());
		tramiteVersion.setDesactivacion(this.isDesactivacionTemporal());

		tramiteVersion.setId(this.getCodigo());
		tramiteVersion.setIdiomasSoportados(this.getIdiomasSoportados());
		tramiteVersion.setIdScriptInicializacionTramite(this.getScriptInicializacionTramite().getCodigo());
		tramiteVersion.setIdScriptPersonalizacion(this.getScriptPersonalizacion().getCodigo());
		tramiteVersion.setIntLimiteTramitacion(this.getLimiteTramitacionNumero());
		tramiteVersion.setLimiteTramitacion(Boolean.valueOf(this.getLimiteTramitacion()));
		if (this.dominios != null) {

			final List<Dominio> listaDominios = new ArrayList<>();
			for (final JDominio dominio : this.getDominios()) {
				listaDominios.add(dominio.toModel());
			}

			tramiteVersion.setListaDominios(listaDominios);
		}
		if (this.pasoTramite != null) {

			final List<TramitePaso> listaPasos = new ArrayList<>();
			for (final JPasoTramitacion paso : this.getPasoTramite()) {
				listaPasos.add(paso.toModel());
			}

			tramiteVersion.setListaPasos(listaPasos);
		}
		tramiteVersion.setMensajeDesactivacion(this.getMensajeDesactivacion().toModel());

		if (this.getNivelQAA()) {
			tramiteVersion.setNivelQAA(1);
		} else {
			tramiteVersion.setNivelQAA(0);
		}
		tramiteVersion.setNumeroVersion(this.getNumeroVersion());
		tramiteVersion.setNumLimiteTramitacion(this.getLimiteTramitacionNumero());
		tramiteVersion.setPersistencia(this.isAdmitePersistencia());
		tramiteVersion.setPersistenciaDias(this.getPersistenciaDias());
		tramiteVersion.setPersistenciaInfinita(this.isPersistenciaInfinita());
		tramiteVersion.setPlazoFinDesactivacion(this.getPlazoFinDesactivacion());
		tramiteVersion.setPlazoInicioDesactivacion(this.getPlazoInicioDesactivacion());
		tramiteVersion.setRelease(this.getRelease());
		tramiteVersion.setTipoFlujo(TypeFlujo.fromString(this.getTipoflujo()));
		return tramiteVersion;
	}

	public void fromModel(final TramiteVersion model) {

		if (model != null) {
			this.setActiva(model.isActiva());
			this.setAutenticado(model.isAutenticado());
			this.setNoAutenticado(model.isNoAutenticado());
			if (model.getBloqueada() == 1) {
				this.setBloqueada(true);
			} else {
				this.setBloqueada(false);
			}
			this.setUsuarioIdBloqueo(model.getCodigoUsuarioBloqueo());
			this.setUsuarioDatosBloqueo(model.getDatosUsuarioBloqueo());
			this.setDebug(model.isDebug());
			this.setDesactivacionTemporal(model.isDesactivacion());

			this.setCodigo(model.getId());
			this.setIdiomasSoportados(model.getIdiomasSoportados());
			// jModel.setIdScriptInicializacionTramite(model.getScriptInicializacionTramite().getCodigo());
			// jModel.setIdScriptPersonalizacion(model.getScriptPersonalizacion().getCodigo());
			this.setLimiteTramitacionNumero(model.getIntLimiteTramitacion());
			if (model.isLimiteTramitacion()) {
				this.setLimiteTramitacion("1");
			} else {
				this.setLimiteTramitacion("0");
			}

			if (model.getListaDominios() != null) {

				final Set<JDominio> listaDominios = new HashSet<>();
				for (final Dominio dominio : model.getListaDominios()) {
					final JDominio jdominio = new JDominio();
					listaDominios.add(jdominio.fromModel(dominio));
				}

				this.setDominios(listaDominios);
			}
			if (model.getListaPasos() != null) {

				final Set<JPasoTramitacion> listaPasos = new HashSet<>();
				for (final TramitePaso paso : model.getListaPasos()) {
					final JPasoTramitacion jpaso = new JPasoTramitacion();
					listaPasos.add(jpaso.fromModel(paso));
				}

				this.setPasoTramite(listaPasos);
			}
			if (model.getMensajeDesactivacion() != null) {
				final JLiteral mensaje = JLiteral.fromModel(model.getMensajeDesactivacion());
				this.setMensajeDesactivacion(mensaje);
			}

			if (model.getNivelQAA() == 1) {
				this.setNivelQAA(true);
			} else {
				this.setNivelQAA(false);
			}
			this.setNumeroVersion(model.getNumeroVersion());
			this.setLimiteTramitacionNumero(model.getNumLimiteTramitacion());
			this.setAdmitePersistencia(model.isPersistencia());
			this.setPersistenciaDias((byte) model.getPersistenciaDias());
			this.setPersistenciaInfinita(model.isPersistenciaInfinita());
			this.setPlazoFinDesactivacion(model.getPlazoFinDesactivacion());
			this.setPlazoInicioDesactivacion(model.getPlazoInicioDesactivacion());
			this.setRelease(model.getRelease());
			this.setTipoflujo(model.getTipoFlujo().toString());

		}

	}
}
