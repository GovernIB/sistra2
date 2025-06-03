package es.caib.sistrages.core.ejb;

import es.caib.sistrages.core.api.model.FuncionarioHabilitadoInfo;
import es.caib.sistrages.core.api.model.PersonaInfo;
import es.caib.sistrages.core.api.model.TramiteFH;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.SistramitApiExternaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SistramitApiExternaServiceBean implements SistramitApiExternaService {

    @Autowired
    SistramitApiExternaService sistramitApiExternaService;

    @Override
    @RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
    public String obtenerTicketAccesoFH(FuncionarioHabilitadoInfo funcionarioHabilitadoInfo, PersonaInfo interesado, PersonaInfo representante, TramiteFH tramiteFH) {
        return sistramitApiExternaService.obtenerTicketAccesoFH(funcionarioHabilitadoInfo, interesado, representante, tramiteFH);
    }
}
