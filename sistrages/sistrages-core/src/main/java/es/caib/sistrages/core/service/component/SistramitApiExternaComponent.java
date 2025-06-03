package es.caib.sistrages.core.service.component;

import es.caib.sistrages.core.api.model.FuncionarioHabilitadoInfo;
import es.caib.sistrages.core.api.model.PersonaInfo;
import es.caib.sistrages.core.api.model.TramiteFH;

/**
 * Acceso a componente SISTRAMIT.
 *
 * @author Indra
 *
 */
public interface SistramitApiExternaComponent {

    public String obtenerTicketAccesoFH(FuncionarioHabilitadoInfo funcionarioHabilitadoInfo, PersonaInfo interesado, PersonaInfo representante, TramiteFH tramiteFH);

}
