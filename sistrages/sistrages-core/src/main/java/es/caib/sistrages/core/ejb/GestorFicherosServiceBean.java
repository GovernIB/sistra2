package es.caib.sistrages.core.ejb;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.ContenidoFichero;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.GestorFicherosService;

/**
 * GestorFicherosServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class GestorFicherosServiceBean implements GestorFicherosService {

	@Autowired
	GestorFicherosService gestorFicherosService;

	@Override
	@PermitAll
	public void purgarFicheros() {
		gestorFicherosService.purgarFicheros();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ContenidoFichero obtenerContenidoFichero(final Long id) {
		return gestorFicherosService.obtenerContenidoFichero(id);
	}

}
