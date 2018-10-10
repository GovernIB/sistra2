package es.caib.sistrages.core.ejb;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.service.SystemService;

/**
 * Servicios de sistema.
 *
 * @author Indra
 *
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SystemServiceBean implements SystemService {

    /** System service. */
    @Autowired
    private SystemService systemService;

    @Override
    @PermitAll
    public void purgarFicheros() {
        systemService.purgarFicheros();
    }

    @Override
    @PermitAll
    public String obtenerPropiedadConfiguracion(String propiedad) {
        return systemService.obtenerPropiedadConfiguracion(propiedad);
    }

    @Override
    @PermitAll
    public boolean verificarMaestro(String appId) {
        return systemService.verificarMaestro(appId);
    }

}
