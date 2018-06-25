package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Lista parámetros.
 *
 * @author Indra
 *
 */
public class RListaParametros {

    /** Lista parametros. */
    private List<RValorParametro> parametros;

    /**
     * Método de acceso a parametros.
     *
     * @return parametros
     */
    public List<RValorParametro> getParametros() {
        return parametros;
    }

    /**
     * Método para establecer parametros.
     *
     * @param parametros
     *            parametros a establecer
     */
    public void setParametros(List<RValorParametro> parametros) {
        this.parametros = parametros;
    }

}
