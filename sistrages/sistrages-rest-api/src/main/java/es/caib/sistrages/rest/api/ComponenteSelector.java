package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Componente selector.
 *
 * @author Indra
 *
 */
public class ComponenteSelector extends Componente {

    /** Propiedades campo. */
    private PropiedadesCampo propiedadesCampo;

    /** Tipo selector: SELECTOR, DESPLEGABLE, MULTIPLE, UNICA. */
    private String tipoSelector;

    /** Tipo lista valores: FIJA("F"), DOMINIO("D"), SCRIPT("S"). */
    private String tipoListaValores;

    /** Lista valores fija. */
    private List<ValorListaFija> listaFija;

    /** Lista valores por dominio. */
    private ListaDominio listaDominio;

    /** Lista valores por script. */
    private Script scriptListaValores;

    /**
     * Método de acceso a propiedadesCampo.
     * 
     * @return propiedadesCampo
     */
    public PropiedadesCampo getPropiedadesCampo() {
        return propiedadesCampo;
    }

    /**
     * Método para establecer propiedadesCampo.
     * 
     * @param propiedadesCampo
     *            propiedadesCampo a establecer
     */
    public void setPropiedadesCampo(PropiedadesCampo propiedadesCampo) {
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
    public List<ValorListaFija> getListaFija() {
        return listaFija;
    }

    /**
     * Método para establecer listaFija.
     * 
     * @param listaFija
     *            listaFija a establecer
     */
    public void setListaFija(List<ValorListaFija> listaFija) {
        this.listaFija = listaFija;
    }

    /**
     * Método de acceso a listaDominio.
     * 
     * @return listaDominio
     */
    public ListaDominio getListaDominio() {
        return listaDominio;
    }

    /**
     * Método para establecer listaDominio.
     * 
     * @param listaDominio
     *            listaDominio a establecer
     */
    public void setListaDominio(ListaDominio listaDominio) {
        this.listaDominio = listaDominio;
    }

    /**
     * Método de acceso a scriptListaValores.
     * 
     * @return scriptListaValores
     */
    public Script getScriptListaValores() {
        return scriptListaValores;
    }

    /**
     * Método para establecer scriptListaValores.
     * 
     * @param scriptListaValores
     *            scriptListaValores a establecer
     */
    public void setScriptListaValores(Script scriptListaValores) {
        this.scriptListaValores = scriptListaValores;
    }

}
