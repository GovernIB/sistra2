package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;

/**
 * Interface FuncionarioHabilitadoComponent.
 */
public interface FuncionarioHabilitadoComponent {

    /**
     * Avisar a funcionario habilitado de trámite finalizado.
     *
     * @param tramiteFinalizado trámite finalizado
     * @param debug
     * @return Mensaje de error (null si no ha habido error)
     */
    String avisarTramiteFinalizado(TramiteFinalizado tramiteFinalizado, boolean debug);

}
