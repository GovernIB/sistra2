package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
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

/**
 * JDominio
 */
@Entity
@Table(name = "STG_DOMINI", uniqueConstraints = @UniqueConstraint(columnNames = "DOM_IDENTI"))
public class JDominio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_DOMINI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_DOMINI_SEQ", sequenceName = "STG_DOMINI_SEQ")
	@Column(name = "DOM_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOM_FDIDFD")
	private JFuenteDatos fuenteDatos;

	@Column(name = "DOM_AMBITO", nullable = false, length = 1)
	private String ambito;

	@Column(name = "DOM_IDENTI", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "DOM_DESCR", nullable = false)
	private String descripcion;

	@Column(name = "DOM_CACHE", nullable = false, precision = 1, scale = 0)
	private boolean cacheo;

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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_DOMENT", joinColumns = {
			@JoinColumn(name = "DEN_CODDOM", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DEN_CODENT", nullable = false, updatable = false) })
	private Set<JEntidades> entidades = new HashSet<JEntidades>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dominio")
	private Set<JCampoFormularioIndexado> camposFormularioIndexado = new HashSet<JCampoFormularioIndexado>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_AREDOM", joinColumns = {
			@JoinColumn(name = "DMA_CODDOM", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DMA_CODARE", nullable = false, updatable = false) })
	private Set<JArea> areas = new HashSet<JArea>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_DOMVER", joinColumns = {
			@JoinColumn(name = "DVT_CODDOM", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DVT_CODVTR", nullable = false, updatable = false) })
	private Set<JVersionTramite> versionesTramite = new HashSet<JVersionTramite>(0);

	public JDominio() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
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

	public boolean isCacheo() {
		return this.cacheo;
	}

	public void setCacheo(final boolean cacheo) {
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

	public Set<JEntidades> getEntidades() {
		return this.entidades;
	}

	public void setEntidades(final Set<JEntidades> entidades) {
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

}
