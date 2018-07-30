package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.RestApiExternaService;

/**
 * Servicios de API Rest.
 *
 * @author Indra
 *
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RestApiExternaServiceBean implements RestApiExternaService {

    /** System service. */
    @Autowired
    private RestApiExternaService restApiService;

    @Override
    @RolesAllowed(ConstantesRolesAcceso.REST_EXT)
    public String test(String echo) {
        return restApiService.test(echo);
    }

    @Override
    @RolesAllowed(ConstantesRolesAcceso.REST_EXT)
    public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro){
    	return restApiService.listConfiguracionGlobal(filtro);
    }

}
