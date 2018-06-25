package es.caib.sistramit.core.api.model.flujo;

import java.util.HashMap;
import java.util.Map;

/**
 * Parámetros de una acción sobre un paso de tramitación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ParametrosAccionPaso implements ModelApi {

    /**
     * Parámetros de entrada específicos de la acción.
     */
    private final Map<String, Object> parametrosEntrada = new HashMap<>();

    /**
     * Añade un parámetro de entrada.
     *
     * @param idParametro
     *            Id parámetro entrada
     * @param parametro
     *            Valor parámetro
     *
     */
    public void addParametroEntrada(final String idParametro,
            final Object parametro) {
        parametrosEntrada.put(idParametro, parametro);
    }

    /**
     * Obtiene un parámetro de entrada.
     *
     * @param idParametro
     *            Id parámetro entrada
     * @return Valor parámetro
     *
     */
    public Object getParametroEntrada(final String idParametro) {
        return parametrosEntrada.get(idParametro);
    }

}
