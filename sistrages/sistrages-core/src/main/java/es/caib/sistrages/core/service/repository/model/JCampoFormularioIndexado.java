package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.Collections;
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

import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.types.TypeCampoIndexado;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JCampoFormularioIndexado
 */
@Entity
@Table(name = "STG_FORCIN")
public class JCampoFormularioIndexado implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CIN_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

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
	private String tipoCampoIndexado;

	@Column(name = "CIN_TIPLST", nullable = false, length = 1)
	private String tipoListaValores;

	@Column(name = "CIN_DOMCCD", length = 100)
	private String campoDominioCodigo;

	@Column(name = "CIN_DOMCDS", length = 100)
	private String campoDominioDescripcion;

	@Column(name = "CIN_INDICE", nullable = false, precision = 1, scale = 0)
	private boolean indiceAlfabetico;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campoFormularioIndexado")
	private Set<JParametroDominioCampoIndexado> parametrosDominio = new HashSet<JParametroDominioCampoIndexado>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campoFormularioIndexado")
	private Set<JListaFijaValoresCampoIndexado> listaFijaValores = new HashSet<JListaFijaValoresCampoIndexado>(0);

	public JCampoFormularioIndexado() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
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

	public String getTipoCampoIndexado() {
		return this.tipoCampoIndexado;
	}

	public void setTipoCampoIndexado(final String tipo) {
		this.tipoCampoIndexado = tipo;
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

	public Set<JParametroDominioCampoIndexado> getParametrosDominio() {
		return this.parametrosDominio;
	}

	public void setParametrosDominio(final Set<JParametroDominioCampoIndexado> parametrosDominioCampoIndexado) {
		this.parametrosDominio = parametrosDominioCampoIndexado;
	}

	public Set<JListaFijaValoresCampoIndexado> getListaFijaValores() {
		return this.listaFijaValores;
	}

	public void setListaFijaValores(final Set<JListaFijaValoresCampoIndexado> listaFijaValoresCampoIndexado) {
		this.listaFijaValores = listaFijaValoresCampoIndexado;
	}

	public ComponenteFormularioCampoSelector toModel() {
		ComponenteFormularioCampoSelector campoSelector = null;

		if (campoFormulario != null) {
			campoSelector = (ComponenteFormularioCampoSelector) campoFormulario
					.toModel(ComponenteFormularioCampoSelector.class);
			if (campoSelector != null) {
				campoSelector.setTipoCampoIndexado(TypeCampoIndexado.fromString(tipoCampoIndexado));
				campoSelector.setTipoListaValores(TypeListaValores.fromString(tipoListaValores));
				if (scriptValoresPosibles != null) {
					campoSelector.setScriptValoresPosibles(scriptValoresPosibles.toModel());
				}
				if (dominio != null) {
					campoSelector.setDominio(dominio.toModel());
				}

				campoSelector.setCampoDominioCodigo(campoDominioCodigo);
				campoSelector.setCampoDominioDescripcion(campoDominioDescripcion);
				campoSelector.setIndiceAlfabetico(indiceAlfabetico);

				if (parametrosDominio != null) {
					if (campoSelector.getListaParametrosDominio() == null) {
						campoSelector.setListaParametrosDominio(new ArrayList<>());
					}
					for (final JParametroDominioCampoIndexado jParametroDominio : parametrosDominio) {
						campoSelector.getListaParametrosDominio().add(jParametroDominio.toModel());
					}
				}

				if (listaFijaValores != null) {
					if (campoSelector.getListaValorListaFija() == null) {
						campoSelector.setListaValorListaFija(new ArrayList<>());
					}
					for (final JListaFijaValoresCampoIndexado jListaFijaValores : listaFijaValores) {
						campoSelector.getListaValorListaFija().add(jListaFijaValores.toModel());
					}

					// ordenamos lista de valores
					if (!campoSelector.getListaValorListaFija().isEmpty()) {
						Collections.sort(campoSelector.getListaValorListaFija(),
								(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
					}
				}

			}
		}

		return campoSelector;
	}

	public static JCampoFormularioIndexado createDefault(final int pOrden, final JLineaFormulario pJLinea) {
		final JCampoFormularioIndexado jModel = new JCampoFormularioIndexado();
		jModel.setTipoCampoIndexado(TypeCampoIndexado.SELECTOR.name());
		jModel.setTipoListaValores(TypeListaValores.FIJA.toString());
		jModel.setIndiceAlfabetico(false);
		jModel.setCampoFormulario(
				JCampoFormulario.createDefault(TypeObjetoFormulario.LISTA_ELEMENTOS, pOrden, pJLinea));
		return jModel;
	}
}
