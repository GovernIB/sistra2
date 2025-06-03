package es.caib.sistrages.core.service;

import es.caib.sistrages.core.api.model.FuncionarioHabilitadoInfo;
import es.caib.sistrages.core.api.model.PersonaInfo;
import es.caib.sistrages.core.api.model.TramiteFH;
import es.caib.sistrages.core.api.service.SistramitApiExternaService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.SistramitApiExternaComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SistramitApiExternaServiceImpl implements SistramitApiExternaService {

    private static final Logger LOG = LoggerFactory.getLogger(SistramitApiExternaServiceImpl.class);

    @Autowired
    SistramitApiExternaComponent sistramitApiExternaComponent;

    @Override
    @NegocioInterceptor
    public String obtenerTicketAccesoFH(FuncionarioHabilitadoInfo funcionarioHabilitadoInfo, PersonaInfo interesado, PersonaInfo representante, TramiteFH tramiteFH) {
        return sistramitApiExternaComponent.obtenerTicketAccesoFH(funcionarioHabilitadoInfo, interesado, representante, tramiteFH);
    }
}
