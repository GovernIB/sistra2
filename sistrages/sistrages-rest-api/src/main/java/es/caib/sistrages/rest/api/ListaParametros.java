package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Lista parámetros.
 *
 * @author Indra
 *
 */
public class ListaParametros {

    /** Lista parametros. */
    private List<ValorParametro> parametros;

    /**
     * Método de acceso a parametros.
     *
     * @return parametros
     */
    public List<ValorParametro> getParametros() {
        return parametros;
    }

    /**
     * Método para establecer parametros.
     *
     * @param parametros
     *            parametros a establecer
     */
    public void setParametros(List<ValorParametro> parametros) {
        this.parametros = parametros;
    }

}
