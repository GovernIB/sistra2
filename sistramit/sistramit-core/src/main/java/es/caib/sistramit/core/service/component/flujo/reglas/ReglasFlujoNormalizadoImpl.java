package es.caib.sistramit.core.service.component.flujo.reglas;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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

    @Resource(name = "reglaPasosInicialesNormalizado")
    private ReglaTramitacion reglaPasosInicialesNormalizado;

    @Resource(name = "reglaBloqueoPasos")
    private ReglaTramitacion reglaBloqueoPasos;

    /**
     * Constructor.
     */
    @PostConstruct
    public void inicializarReglas() {

        // Establecemos reglas

        // INICIAR TRAMITE
        // TODO Dinamicamente en el JBoss Standalone no funciona, en el embebed
        // y JUnit sí
        // this.addRegla("reglaPasosInicialesNormalizado",
        // TypeFaseEjecucion.PRE_INICIO);
        this.addRegla(reglaPasosInicialesNormalizado,
                TypeFaseEjecucion.PRE_INICIO);

        // CARGAR TRAMITE
        // this.addRegla("reglaBloqueoPasos", TypeFaseEjecucion.POST_CARGA);
        this.addRegla(reglaBloqueoPasos, TypeFaseEjecucion.POST_CARGA);

        // TODO PENDIENTE ANALIZAR

    }
}
