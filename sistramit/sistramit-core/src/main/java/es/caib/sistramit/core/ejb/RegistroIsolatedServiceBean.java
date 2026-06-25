package es.caib.sistramit.core.ejb;

import es.caib.sistramit.core.api.model.flujo.RegistroIsolatedData;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.service.RegistroIsolatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors({
        SpringBeanAutowiringInterceptor.class,
        EJBExceptionInterceptor.class
})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RegistroIsolatedServiceBean implements RegistroIsolatedService {

    @Autowired
    private RegistroIsolatedService registroIsolatedService;

    @Override
    public ResultadoRegistrar registrar(String idSesionTramitacion, RegistroIsolatedData registroIsolated, boolean reintentar) {
        return registroIsolatedService.registrar(idSesionTramitacion, registroIsolated, reintentar);
    }

}
