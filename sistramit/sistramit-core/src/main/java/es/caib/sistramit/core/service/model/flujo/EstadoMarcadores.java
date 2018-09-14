package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Marcadores de estado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EstadoMarcadores implements Serializable {

    /**
     * Marcadores.
     */
    private final Map<String, Boolean> marcadores = new HashMap<>();

    /**
     * Añade marcador estado.
     *
     * @param marcador
     *            Marcador estado
     * @param valor
     *            Valor
     */
    public void addMarcador(final String marcador, final boolean valor) {
        marcadores.put(marcador, valor);
    }

    /**
     * Obtiene valor marcador.
     *
     * @param marcador
     *            Marcador
     * @return valor marcador
     */
    public boolean getMarcador(final String marcador) {
        boolean res = false;
        if (marcadores.containsKey(marcador)) {
            res = marcadores.get(marcador);
        }
        return res;
    }

}
