package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeCampoIndexado;
import es.caib.sistrages.core.api.model.types.TypeListaValores;

/**
 * Componente formulario de tipo campo selector.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ComponenteFormularioCampoSelector extends ComponenteFormularioCampo {

	private TypeCampoIndexado tipoCampoIndexado;

	private TypeListaValores tipoListaValores;

	private Script scriptValoresPosibles;

	private Dominio dominio;

	private String campoDominioCodigo;

	private String campoDominioDescripcion;

	private boolean indiceAlfabetico;

	private List<ParametroDominio> listaParametrosDominio;

	private List<ValorListaFija> listaValorListaFija;

	public ComponenteFormularioCampoSelector() {
		super();
	}

	public TypeCampoIndexado getTipoCampoIndexado() {
		return tipoCampoIndexado;
	}

	public void setTipoCampoIndexado(final TypeCampoIndexado tipoCampoIndexado) {
		this.tipoCampoIndexado = tipoCampoIndexado;
	}

	public TypeListaValores getTipoListaValores() {
		return tipoListaValores;
	}

	public void setTipoListaValores(final TypeListaValores tipoListaValores) {
		this.tipoListaValores = tipoListaValores;
	}

	public Script getScriptValoresPosibles() {
		return scriptValoresPosibles;
	}

	public void setScriptValoresPosibles(final Script scriptValoresPosibles) {
		this.scriptValoresPosibles = scriptValoresPosibles;
	}

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(final Dominio dominio) {
		this.dominio = dominio;
	}

	public String getCampoDominioCodigo() {
		return campoDominioCodigo;
	}

	public void setCampoDominioCodigo(final String campoDominioCodigo) {
		this.campoDominioCodigo = campoDominioCodigo;
	}

	public String getCampoDominioDescripcion() {
		return campoDominioDescripcion;
	}

	public void setCampoDominioDescripcion(final String campoDominioDescripcion) {
		this.campoDominioDescripcion = campoDominioDescripcion;
	}

	public boolean isIndiceAlfabetico() {
		return indiceAlfabetico;
	}

	public void setIndiceAlfabetico(final boolean indiceAlfabetico) {
		this.indiceAlfabetico = indiceAlfabetico;
	}

	public List<ParametroDominio> getListaParametrosDominio() {
		return listaParametrosDominio;
	}

	public void setListaParametrosDominio(final List<ParametroDominio> parametrosDominio) {
		this.listaParametrosDominio = parametrosDominio;
	}

	public List<ValorListaFija> getListaValorListaFija() {
		return listaValorListaFija;
	}

	public void setListaValorListaFija(final List<ValorListaFija> listaValorListaFija) {
		this.listaValorListaFija = listaValorListaFija;
	}

}
