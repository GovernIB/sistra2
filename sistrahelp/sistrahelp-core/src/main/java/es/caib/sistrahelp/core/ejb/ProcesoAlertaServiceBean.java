package es.caib.sistrahelp.core.ejb;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.service.ProcesoAlertaService;

/**
 * La clase alertaServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProcesoAlertaServiceBean implements ProcesoAlertaService {

	/**
	 * Alerta service.
	 */
	@Autowired
	ProcesoAlertaService procesoAlertaService;

	@Override
    @PermitAll
    public boolean procesarAlertas(Alerta al) {
		return procesoAlertaService.procesarAlertas(al);
    }

}
