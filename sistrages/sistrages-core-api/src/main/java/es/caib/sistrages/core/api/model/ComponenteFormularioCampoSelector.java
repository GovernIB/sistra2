package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeCampoIndexado;
import es.caib.sistrages.core.api.model.types.TypeListaValores;

/**
 * La clase ComponenteFormularioCampoSelector.
 */

public final class ComponenteFormularioCampoSelector extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * tipo campo indexado.
	 */
	private TypeCampoIndexado tipoCampoIndexado;

	/**
	 * tipo lista valores.
	 */
	private TypeListaValores tipoListaValores;

	/**
	 * script valores posibles.
	 */
	private Script scriptValoresPosibles;

	/**
	 * codDominio.
	 */
	private Long codDominio;

	/**
	 * campo codDominio codigo.
	 */
	private String campoDominioCodigo;

	/**
	 * campo codDominio descripcion.
	 */
	private String campoDominioDescripcion;

	/**
	 * indice alfabetico.
	 */
	private boolean indiceAlfabetico;

	/**
	 * lista parametros codDominio.
	 */
	private List<ParametroDominio> listaParametrosDominio;

	/**
	 * lista valor lista fija.
	 */
	private List<ValorListaFija> listaValorListaFija;

	/**
	 * altura.
	 */
	private int altura;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoSelector.
	 */
	public ComponenteFormularioCampoSelector() {
		super();
	}

	/**
	 * Obtiene el valor de tipoCampoIndexado.
	 *
	 * @return el valor de tipoCampoIndexado
	 */
	public TypeCampoIndexado getTipoCampoIndexado() {
		return tipoCampoIndexado;
	}

	/**
	 * Establece el valor de tipoCampoIndexado.
	 *
	 * @param tipoCampoIndexado
	 *            el nuevo valor de tipoCampoIndexado
	 */
	public void setTipoCampoIndexado(final TypeCampoIndexado tipoCampoIndexado) {
		this.tipoCampoIndexado = tipoCampoIndexado;
	}

	/**
	 * Obtiene el valor de tipoListaValores.
	 *
	 * @return el valor de tipoListaValores
	 */
	public TypeListaValores getTipoListaValores() {
		return tipoListaValores;
	}

	/**
	 * Establece el valor de tipoListaValores.
	 *
	 * @param tipoListaValores
	 *            el nuevo valor de tipoListaValores
	 */
	public void setTipoListaValores(final TypeListaValores tipoListaValores) {
		this.tipoListaValores = tipoListaValores;
	}

	/**
	 * Obtiene el valor de scriptValoresPosibles.
	 *
	 * @return el valor de scriptValoresPosibles
	 */
	public Script getScriptValoresPosibles() {
		return scriptValoresPosibles;
	}

	/**
	 * Establece el valor de scriptValoresPosibles.
	 *
	 * @param scriptValoresPosibles
	 *            el nuevo valor de scriptValoresPosibles
	 */
	public void setScriptValoresPosibles(final Script scriptValoresPosibles) {
		this.scriptValoresPosibles = scriptValoresPosibles;
	}

	/**
	 * Obtiene el valor de codDominio.
	 *
	 * @return el valor de codDominio
	 */
	public Long getCodDominio() {
		return codDominio;
	}

	/**
	 * Establece el valor de codDominio.
	 *
	 * @param codDominio
	 *            el nuevo valor de codDominio
	 */
	public void setCodDominio(final Long pCodDominio) {
		this.codDominio = pCodDominio;
	}

	/**
	 * Obtiene el valor de campoDominioCodigo.
	 *
	 * @return el valor de campoDominioCodigo
	 */
	public String getCampoDominioCodigo() {
		return campoDominioCodigo;
	}

	/**
	 * Establece el valor de campoDominioCodigo.
	 *
	 * @param campoDominioCodigo
	 *            el nuevo valor de campoDominioCodigo
	 */
	public void setCampoDominioCodigo(final String campoDominioCodigo) {
		this.campoDominioCodigo = campoDominioCodigo;
	}

	/**
	 * Obtiene el valor de campoDominioDescripcion.
	 *
	 * @return el valor de campoDominioDescripcion
	 */
	public String getCampoDominioDescripcion() {
		return campoDominioDescripcion;
	}

	/**
	 * Establece el valor de campoDominioDescripcion.
	 *
	 * @param campoDominioDescripcion
	 *            el nuevo valor de campoDominioDescripcion
	 */
	public void setCampoDominioDescripcion(final String campoDominioDescripcion) {
		this.campoDominioDescripcion = campoDominioDescripcion;
	}

	/**
	 * Verifica si es indice alfabetico.
	 *
	 * @return true, si es indice alfabetico
	 */
	public boolean isIndiceAlfabetico() {
		return indiceAlfabetico;
	}

	/**
	 * Establece el valor de indiceAlfabetico.
	 *
	 * @param indiceAlfabetico
	 *            el nuevo valor de indiceAlfabetico
	 */
	public void setIndiceAlfabetico(final boolean indiceAlfabetico) {
		this.indiceAlfabetico = indiceAlfabetico;
	}

	/**
	 * Obtiene el valor de listaParametrosDominio.
	 *
	 * @return el valor de listaParametrosDominio
	 */
	public List<ParametroDominio> getListaParametrosDominio() {
		return listaParametrosDominio;
	}

	/**
	 * Establece el valor de listaParametrosDominio.
	 *
	 * @param parametrosDominio
	 *            el nuevo valor de listaParametrosDominio
	 */
	public void setListaParametrosDominio(final List<ParametroDominio> parametrosDominio) {
		this.listaParametrosDominio = parametrosDominio;
	}

	/**
	 * Obtiene el valor de listaValorListaFija.
	 *
	 * @return el valor de listaValorListaFija
	 */
	public List<ValorListaFija> getListaValorListaFija() {
		return listaValorListaFija;
	}

	/**
	 * Establece el valor de listaValorListaFija.
	 *
	 * @param listaValorListaFija
	 *            el nuevo valor de listaValorListaFija
	 */
	public void setListaValorListaFija(final List<ValorListaFija> listaValorListaFija) {
		this.listaValorListaFija = listaValorListaFija;
	}

	/**
	 * Obtiene el valor de altura.
	 *
	 * @return el valor de altura
	 */
	public int getAltura() {
		return altura;
	}

	/**
	 * Establece el valor de altura.
	 *
	 * @param altura
	 *            el nuevo valor de altura
	 */
	public void setAltura(final int altura) {
		this.altura = altura;
	}

}
