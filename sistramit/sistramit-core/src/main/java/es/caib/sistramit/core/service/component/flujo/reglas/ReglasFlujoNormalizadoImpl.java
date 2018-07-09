package es.caib.sistramit.core.service.component.flujo.reglas;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import es.caib.sistramit.core.service.model.flujo.types.TypeFaseEjecucion;

/**
 * Reglas del flujo de tramitación normalizado.
 *
 * @author Indra
 *
 */
@Component("reglasFlujoNormalizado")
public final class ReglasFlujoNormalizadoImpl extends ReglasFlujoImpl {

    /**
     * Constructor.
     */
    @PostConstruct
    public void inicializarReglas() {

        // Establecemos reglas

        // INICIAR TRAMITE
        this.addRegla("reglaPasosInicialesNormalizado",
                TypeFaseEjecucion.PRE_INICIO);

        // CARGAR TRAMITE
        this.addRegla("reglaBloqueoPasos", TypeFaseEjecucion.POST_CARGA);

        // TODO PENDIENTE ANALIZAR

    }
}
