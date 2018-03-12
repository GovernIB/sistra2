package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JCampoFormularioIndexado
 */
@Entity
@Table(name = "STG_FORCIN")
public class JCampoFormularioIndexado implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CIN_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CIN_DOMCOD")
	private JDominio dominio;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "CIN_CODIGO")
	private JCampoFormulario campoFormulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CIN_SCRVAP")
	private JScript scriptValoresPosibles;

	@Column(name = "CIN_TIPO", nullable = false, length = 10)
	private String tipo;

	@Column(name = "CIN_TIPLST", nullable = false, length = 1)
	private String tipoListaValores;

	@Column(name = "CIN_DOMCCD", length = 100)
	private String campoDominioCodigo;

	@Column(name = "CIN_DOMCDS", length = 100)
	private String campoDominioDescripcion;

	@Column(name = "CIN_INDICE", nullable = false, precision = 1, scale = 0)
	private boolean indiceAlfabetico;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campoFormularioIndexado")
	private Set<JParametroDominioCampoIndexado> parametrosDominioCampoIndexado = new HashSet<JParametroDominioCampoIndexado>(
			0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campoFormularioIndexado")
	private Set<JListaFijaValoresCampoIndexado> listaFijaValoresCampoIndexado = new HashSet<JListaFijaValoresCampoIndexado>(
			0);

	public JCampoFormularioIndexado() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JDominio getDominio() {
		return this.dominio;
	}

	public void setDominio(final JDominio dominio) {
		this.dominio = dominio;
	}

	public JCampoFormulario getCampoFormulario() {
		return this.campoFormulario;
	}

	public void setCampoFormulario(final JCampoFormulario campoFormulario) {
		this.campoFormulario = campoFormulario;
	}

	public JScript getScriptValoresPosibles() {
		return this.scriptValoresPosibles;
	}

	public void setScriptValoresPosibles(final JScript scriptValoresPosibles) {
		this.scriptValoresPosibles = scriptValoresPosibles;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public String getTipoListaValores() {
		return this.tipoListaValores;
	}

	public void setTipoListaValores(final String tipoListaValores) {
		this.tipoListaValores = tipoListaValores;
	}

	public String getCampoDominioCodigo() {
		return this.campoDominioCodigo;
	}

	public void setCampoDominioCodigo(final String campoDominioCodigo) {
		this.campoDominioCodigo = campoDominioCodigo;
	}

	public String getCampoDominioDescripcion() {
		return this.campoDominioDescripcion;
	}

	public void setCampoDominioDescripcion(final String campoDominioDescripcion) {
		this.campoDominioDescripcion = campoDominioDescripcion;
	}

	public boolean isIndiceAlfabetico() {
		return this.indiceAlfabetico;
	}

	public void setIndiceAlfabetico(final boolean indiceAlfabetico) {
		this.indiceAlfabetico = indiceAlfabetico;
	}

	public Set<JParametroDominioCampoIndexado> getParametrosDominioCampoIndexado() {
		return this.parametrosDominioCampoIndexado;
	}

	public void setParametrosDominioCampoIndexado(final Set<JParametroDominioCampoIndexado> parametrosDominioCampoIndexado) {
		this.parametrosDominioCampoIndexado = parametrosDominioCampoIndexado;
	}

	public Set<JListaFijaValoresCampoIndexado> getListaFijaValoresCampoIndexado() {
		return this.listaFijaValoresCampoIndexado;
	}

	public void setListaFijaValoresCampoIndexado(
			final Set<JListaFijaValoresCampoIndexado> listaFijaValoresCampoIndexado) {
		this.listaFijaValoresCampoIndexado = listaFijaValoresCampoIndexado;
	}

}
