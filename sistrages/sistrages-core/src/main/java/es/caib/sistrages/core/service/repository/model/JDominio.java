package es.caib.sistrages.core.service.repository.model;

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
import javax.persistence.UniqueConstraint;

import org.apache.commons.codec.binary.Base64;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeCache;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.util.UtilJSON;

/**
 * JDominio
 */
@Entity
@Table(name = "STG_DOMINI", uniqueConstraints = @UniqueConstraint(columnNames = "DOM_IDENTI"))
public class JDominio implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_DOMINI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_DOMINI_SEQ", sequenceName = "STG_DOMINI_SEQ")
	@Column(name = "DOM_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOM_FDIDFD")
	private JFuenteDatos fuenteDatos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOM_CODCAU")
	private JConfiguracionAutenticacion configuracionAutenticacion;

	@Column(name = "DOM_AMBITO", nullable = false, length = 1)
	private String ambito;

	@Column(name = "DOM_IDENTI", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "DOM_DESCR", nullable = false)
	private String descripcion;

	@Column(name = "DOM_CACHEO", nullable = false, precision = 1, scale = 0)
	private String cacheo;

	@Column(name = "DOM_TIPO", nullable = false, length = 1)
	private String tipo;

	@Column(name = "DOM_BDJNDI", length = 500)
	private String datasourceJndi;

	@Column(name = "DOM_BDSQL", length = 2000)
	private String sql;

	@Column(name = "DOM_LFVALS", length = 4000)
	private String listaFijaValores;

	@Column(name = "DOM_REURL", length = 500)
	private String servicioRemotoUrl;

	@Column(name = "DOM_PARAMS", length = 4000)
	private String parametros;

	@Column(name = "DOM_TIMEOUT")
	private Long timeout;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_DOMENT", joinColumns = {
			@JoinColumn(name = "DEN_CODDOM", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DEN_CODENT", nullable = false, updatable = false) })
	private Set<JEntidad> entidades = new HashSet<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dominio")
	private Set<JCampoFormularioIndexado> camposFormularioIndexado = new HashSet<>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_AREDOM", joinColumns = {
			@JoinColumn(name = "DMA_CODDOM", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DMA_CODARE", nullable = false, updatable = false) })
	private Set<JArea> areas = new HashSet<>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_DOMVER", joinColumns = {
			@JoinColumn(name = "DVT_CODDOM", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DVT_CODVTR", nullable = false, updatable = false) })
	private Set<JVersionTramite> versionesTramite = new HashSet<>(0);

	public JDominio() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFuenteDatos getFuenteDatos() {
		return this.fuenteDatos;
	}

	public void setFuenteDatos(final JFuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public String getAmbito() {
		return this.ambito;
	}

	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the cacheo
	 */
	public String getCacheo() {
		return cacheo;
	}

	/**
	 * @param cacheo the cacheo to set
	 */
	public void setCacheo(final String cacheo) {
		this.cacheo = cacheo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public String getDatasourceJndi() {
		return this.datasourceJndi;
	}

	public void setDatasourceJndi(final String datasourceJndi) {
		this.datasourceJndi = datasourceJndi;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(final String sql) {
		this.sql = sql;
	}

	public String getListaFijaValores() {
		return this.listaFijaValores;
	}

	public void setListaFijaValores(final String listaFijaValores) {
		this.listaFijaValores = listaFijaValores;
	}

	public String getServicioRemotoUrl() {
		return this.servicioRemotoUrl;
	}

	public void setServicioRemotoUrl(final String servicioRemotoUrl) {
		this.servicioRemotoUrl = servicioRemotoUrl;
	}

	public String getParametros() {
		return this.parametros;
	}

	public void setParametros(final String parametros) {
		this.parametros = parametros;
	}

	public Set<JEntidad> getEntidades() {
		return this.entidades;
	}

	public void setEntidades(final Set<JEntidad> entidades) {
		this.entidades = entidades;
	}

	public Set<JCampoFormularioIndexado> getCamposFormularioIndexado() {
		return this.camposFormularioIndexado;
	}

	public void setCamposFormularioIndexado(final Set<JCampoFormularioIndexado> camposFormularioIndexado) {
		this.camposFormularioIndexado = camposFormularioIndexado;
	}

	public Set<JArea> getAreas() {
		return this.areas;
	}

	public void setAreas(final Set<JArea> areas) {
		this.areas = areas;
	}

	public Set<JVersionTramite> getVersionesTramite() {
		return this.versionesTramite;
	}

	public void setVersionesTramite(final Set<JVersionTramite> versionesTramite) {
		this.versionesTramite = versionesTramite;
	}

	/**
	 * @return the timeout
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the configuracionAutenticacion
	 */
	public JConfiguracionAutenticacion getConfiguracionAutenticacion() {
		return configuracionAutenticacion;
	}

	/**
	 * @param configuracionAutenticacion the configuracionAutenticacion to set
	 */
	public void setConfiguracionAutenticacion(JConfiguracionAutenticacion configuracionAutenticacion) {
		this.configuracionAutenticacion = configuracionAutenticacion;
	}

	public Dominio toModel() {
		final Dominio dominio = new Dominio();
		dominio.setCodigo(this.codigo);
		dominio.setCache(TypeCache.fromString(this.cacheo));
		dominio.setIdentificador(this.identificador);
		dominio.setDescripcion(this.descripcion);
		dominio.setTimeout(this.timeout);

		dominio.setJndi(this.datasourceJndi);
		dominio.setListaFija((List<Propiedad>) UtilJSON.fromListJSON(this.listaFijaValores, Propiedad.class));
		dominio.setParametros((List<Propiedad>) UtilJSON.fromListJSON(this.parametros, Propiedad.class));
		if (this.sql != null) {
			dominio.setSql(encodeSql(this.sql));
			dominio.setSqlDecoded(sql);
		}
		dominio.setTipo(TypeDominio.fromString(this.tipo));
		dominio.setAmbito(TypeAmbito.fromString(this.ambito));
		dominio.setUrl(this.servicioRemotoUrl);

		if (this.getFuenteDatos() != null) {
			dominio.setIdFuenteDatos(this.getFuenteDatos().getCodigo());
		}
		if (this.getConfiguracionAutenticacion () != null) {
			dominio.setConfiguracionAutenticacion(this.getConfiguracionAutenticacion().toModel());
		}
		if (this.getAreas() != null) {
			final Set<Area> idAreas = new HashSet<>();
			for (final JArea area : this.areas) {
				idAreas.add(area.toModel());
			}
			dominio.setAreas(idAreas);
		}
		if (this.getEntidades() != null && !this.getEntidades().isEmpty()) {
			dominio.setEntidad(((JEntidad) this.getEntidades().toArray()[0]).getCodigo());
		}
		return dominio;
	}

	public static JDominio fromModelStatic(final Dominio dominio) {
		JDominio jdominio = null;
		if (dominio != null) {
			jdominio = new JDominio();
			jdominio.fromModel(dominio);
		}
		return jdominio;
	}

	public JDominio fromModel(final Dominio dominio) {
		if (dominio != null) {
			this.setCodigo(dominio.getCodigo());
			this.setCacheo(dominio.getCache().toString());
			this.setIdentificador(dominio.getIdentificador());
			this.setDescripcion(dominio.getDescripcion());
			this.setDatasourceJndi(dominio.getJndi());
			this.setTimeout(dominio.getTimeout());
			this.setListaFijaValores(UtilJSON.toJSON(dominio.getListaFija()));
			this.setParametros(UtilJSON.toJSON(dominio.getParametros()));
			if (dominio.getSql() != null) {
				this.setSql(decodeSql(dominio.getSql()));
			}
			if (dominio.getConfiguracionAutenticacion () != null) {
				this.setConfiguracionAutenticacion(JConfiguracionAutenticacion.fromModel(dominio.getConfiguracionAutenticacion()));
			}
			this.setTipo(dominio.getTipo().toString());
			this.setAmbito(dominio.getAmbito().toString());
			this.setServicioRemotoUrl(dominio.getUrl());
		}
		return this;
	}

	public static String encodeSql(final String sqlPlain) {
		return Base64.encodeBase64String(sqlPlain.getBytes());
	}

	public static String decodeSql(final String sqlEncoded) {
		if (sqlEncoded == null) {
			return null;
		} else {
			return new String(Base64.decodeBase64(sqlEncoded));
		}
	}

	/**
	 * Clona un dominio.
	 *
	 * @param dominio
	 * @param nuevoIdentificador
	 * @param jareas
	 * @param jfuenteDatos
	 * @param jentidad
	 * @return
	 */
	public static JDominio clonar(final JDominio dominio, final String nuevoIdentificador, final Set<JArea> jareas,
			final JFuenteDatos jfuenteDatos, final JEntidad jentidad) {
		JDominio jdominio = null;
		if (dominio != null) {
			jdominio = new JDominio();
			jdominio.setAmbito(dominio.getAmbito());
			jdominio.setAreas(jareas);
			jdominio.setCacheo(dominio.getCacheo());
			jdominio.setCamposFormularioIndexado(null);
			jdominio.setDatasourceJndi(dominio.getDatasourceJndi());
			jdominio.setDescripcion(dominio.getDescripcion());
			jdominio.setEntidades(new HashSet<>(0));
			jdominio.setTimeout(dominio.timeout);
			jdominio.setConfiguracionAutenticacion(dominio.getConfiguracionAutenticacion());
			if (jentidad != null) {
				jdominio.getEntidades().add(jentidad);
			}
			jdominio.setFuenteDatos(jfuenteDatos);
			jdominio.setIdentificador(nuevoIdentificador);
			jdominio.setListaFijaValores(dominio.getListaFijaValores());
			jdominio.setParametros(dominio.getParametros());
			jdominio.setServicioRemotoUrl(dominio.getServicioRemotoUrl());
			jdominio.setSql(dominio.getSql());
			jdominio.setTipo(dominio.getTipo());
			jdominio.setVersionesTramite(null);
		}
		return jdominio;
	}
}
