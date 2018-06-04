package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;
import java.util.Map;

/**
 * Respuesta de una acción sobre un paso de tramitación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoAccionPaso implements Serializable {

    /**
     * Parámetros de retorno específicos de la acción.
     */
    private Map<String, Object> parametrosRetorno;

    /**
     * Indica si el tramite se ha finalizado como consecuencia de realizar la
     * acción sobre el paso.
     */
    private boolean finalizadoTrasAccion;

    /**
     * Obtiene un parámetro de retorno de la respuesta.
     *
     * @param idParametro
     *            Id parámetro retorno
     * @return Valor parámetro
     *
     */
    public Object getParametroRetorno(final String idParametro) {
        return parametrosRetorno.get(idParametro);
    }

    /**
     * Método de acceso a finalizadoTrasAccion.
     *
     * @return finalizadoTrasAccion
     */
    public boolean isFinalizadoTrasAccion() {
        return finalizadoTrasAccion;
    }

    /**
     * Método para establecer finalizadoTrasAccion.
     *
     * @param pFinalizadoTrasAccion
     *            finalizadoTrasAccion a establecer
     */
    public void setFinalizadoTrasAccion(final boolean pFinalizadoTrasAccion) {
        finalizadoTrasAccion = pFinalizadoTrasAccion;
    }

    /**
     * Método de acceso a parametrosRetorno.
     *
     * @return parametrosRetorno
     */
    public Map<String, Object> getParametrosRetorno() {
        return parametrosRetorno;
    }

    /**
     * Método para establecer parametrosRetorno.
     *
     * @param pParametrosRetorno
     *            parametrosRetorno a establecer
     */
    public void setParametrosRetorno(
            final Map<String, Object> pParametrosRetorno) {
        parametrosRetorno = pParametrosRetorno;
    }

}
