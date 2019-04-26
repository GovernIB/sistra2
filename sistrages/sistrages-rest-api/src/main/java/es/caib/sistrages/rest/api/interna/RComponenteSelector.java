package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Componente selector.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RComponenteSelector", description = "Descripcion de RComponenteSelector")
public class RComponenteSelector extends RComponente {

	/** Propiedades campo. */
	@ApiModelProperty(value = "Propiedades campo")
	private RPropiedadesCampo propiedadesCampo;

	/** Tipo selector: SELECTOR, DESPLEGABLE, MULTIPLE, UNICA. */
	@ApiModelProperty(value = "Tipo selector: SELECTOR, DESPLEGABLE, MULTIPLE, UNICA")
	private String tipoSelector;

	/** Tipo lista valores: FIJA("F"), DOMINIO("D"), SCRIPT("S"). */
	@ApiModelProperty(value = "Tipo lista valores: FIJA(F), DOMINIO(D), SCRIPT(S)")
	private String tipoListaValores;

	/** Lista valores fija. */
	@ApiModelProperty(value = "Lista valores fija")
	private List<RValorListaFija> listaFija;

	/** Lista valores por dominio. */
	@ApiModelProperty(value = "Lista valores por dominio")
	private RListaDominio listaDominio;

	/** Lista valores por script. */
	@ApiModelProperty(value = "Lista valores por script")
	private RScript scriptListaValores;

	@ApiModelProperty(value = "Indice alfabetico")
	private boolean indiceAlfabetico;

	/**
	 * Método de acceso a propiedadesCampo.
	 *
	 * @return propiedadesCampo
	 */
	public RPropiedadesCampo getPropiedadesCampo() {
		return propiedadesCampo;
	}

	/**
	 * Método para establecer propiedadesCampo.
	 *
	 * @param propiedadesCampo
	 *            propiedadesCampo a establecer
	 */
	public void setPropiedadesCampo(final RPropiedadesCampo propiedadesCampo) {
		this.propiedadesCampo = propiedadesCampo;
	}

	/**
	 * Método de acceso a tipoSelector.
	 *
	 * @return tipoSelector
	 */
	public String getTipoSelector() {
		return tipoSelector;
	}

	/**
	 * Método para establecer tipoSelector.
	 *
	 * @param tipoSelector
	 *            tipoSelector a establecer
	 */
	public void setTipoSelector(final String tipoSelector) {
		this.tipoSelector = tipoSelector;
	}

	/**
	 * Método de acceso a tipoListaValores.
	 *
	 * @return tipoListaValores
	 */
	public String getTipoListaValores() {
		return tipoListaValores;
	}

	/**
	 * Método para establecer tipoListaValores.
	 *
	 * @param tipoListaValores
	 *            tipoListaValores a establecer
	 */
	public void setTipoListaValores(final String tipoListaValores) {
		this.tipoListaValores = tipoListaValores;
	}

	/**
	 * Método de acceso a listaFija.
	 *
	 * @return listaFija
	 */
	public List<RValorListaFija> getListaFija() {
		return listaFija;
	}

	/**
	 * Método para establecer listaFija.
	 *
	 * @param listaFija
	 *            listaFija a establecer
	 */
	public void setListaFija(final List<RValorListaFija> listaFija) {
		this.listaFija = listaFija;
	}

	/**
	 * Método de acceso a listaDominio.
	 *
	 * @return listaDominio
	 */
	public RListaDominio getListaDominio() {
		return listaDominio;
	}

	/**
	 * Método para establecer listaDominio.
	 *
	 * @param listaDominio
	 *            listaDominio a establecer
	 */
	public void setListaDominio(final RListaDominio listaDominio) {
		this.listaDominio = listaDominio;
	}

	/**
	 * Método de acceso a scriptListaValores.
	 *
	 * @return scriptListaValores
	 */
	public RScript getScriptListaValores() {
		return scriptListaValores;
	}

	/**
	 * Método para establecer scriptListaValores.
	 *
	 * @param scriptListaValores
	 *            scriptListaValores a establecer
	 */
	public void setScriptListaValores(final RScript scriptListaValores) {
		this.scriptListaValores = scriptListaValores;
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

}
