package es.caib.sistramit.core.service.component.flujo.reglas;

import java.util.List;

import es.caib.sistramit.core.service.model.flujo.types.TypeFaseEjecucion;

/**
 * Reglas de un flujo de tramitación.
 *
 * @author Indra
 *
 */
public interface ReglasFlujo {

    /**
     * Obtiene lista de reglas para una fase de ejecución dentro del flujo.
     *
     * @param faseEjecucion
     *            Fase de ejecución
     * @return Lista de reglas
     */
    List<ReglaTramitacion> getReglasTramitacion(
            TypeFaseEjecucion faseEjecucion);

}
