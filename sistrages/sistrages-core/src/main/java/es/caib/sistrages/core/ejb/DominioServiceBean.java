package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DominioServiceBean implements DominioService {

	@Autowired
	DominioService dominioService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public Dominio loadDominio(final Long idDominio) {
		return dominioService.loadDominio(idDominio);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, final String filtro) {
		return dominioService.listDominio(ambito, id, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void addDominio(final Dominio entidad, final Long idEntidad, final Long idArea) {
		dominioService.addDominio(entidad, idEntidad, idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void removeDominio(final Long idDominio) {
		dominioService.removeDominio(idDominio);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void updateDominio(final Dominio dominio) {
		dominioService.updateDominio(dominio);
	}
}
