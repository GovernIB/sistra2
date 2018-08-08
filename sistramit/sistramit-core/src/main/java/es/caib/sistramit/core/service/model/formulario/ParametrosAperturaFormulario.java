package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parámetros apertura del formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ParametrosAperturaFormulario implements Serializable {

    /**
     * Parámetros de apertura.
     */
    private final Map<String, String> parametros = new HashMap<String, String>();

    /**
     * Añade parámetro.
     *
     * @param pCodigo
     *            Código
     * @param pValor
     *            Valor
     */
    public void addParametro(final String pCodigo, final String pValor) {
        parametros.put(pCodigo, pValor);
    }

    /**
     * Obtiene parámetros.
     *
     * @param idParametro
     *            id parametro
     * @return Valor
     */
    public String getParametro(final String idParametro) {
        String res = "";
        if (parametros != null && parametros.containsKey(idParametro)) {
            res = parametros.get(idParametro);
        }
        return res;
    }

    /**
     * Obtiene lista de los id de parametros.
     *
     * @return Lista de los id de parametros.
     */
    public List<String> getIdParametros() {
        final List<String> res = new ArrayList<String>();
        if (parametros != null) {
            for (final String idParam : parametros.keySet()) {
                res.add(idParam);
            }
        }
        return res;
    }
}
