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
    public void setPropiedadesCampo(RPropiedadesCampo propiedadesCampo) {
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
    public void setTipoSelector(String tipoSelector) {
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
    public void setTipoListaValores(String tipoListaValores) {
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
    public void setListaFija(List<RValorListaFija> listaFija) {
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
    public void setListaDominio(RListaDominio listaDominio) {
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
    public void setScriptListaValores(RScript scriptListaValores) {
        this.scriptListaValores = scriptListaValores;
    }

}
