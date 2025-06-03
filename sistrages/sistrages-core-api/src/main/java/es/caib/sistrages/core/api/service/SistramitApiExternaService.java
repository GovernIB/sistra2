package es.caib.sistrages.core.api.service;

import es.caib.sistrages.core.api.model.FuncionarioHabilitadoInfo;
import es.caib.sistrages.core.api.model.PersonaInfo;
import es.caib.sistrages.core.api.model.TramiteFH;

public interface SistramitApiExternaService {

    public String obtenerTicketAccesoFH(FuncionarioHabilitadoInfo funcionarioHabilitadoInfo, PersonaInfo interesado, PersonaInfo representante, TramiteFH tramiteFH);

}
