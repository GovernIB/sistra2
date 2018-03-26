package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.EntidadService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EntidadServiceBean implements EntidadService {

	@Autowired
	EntidadService entidadService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public Entidad loadEntidad(final Long idEntidad) {
		return entidadService.loadEntidad(idEntidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public List<Entidad> listEntidad(final TypeIdioma idioma, final String filtro) {
		return entidadService.listEntidad(idioma, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void addEntidad(final Entidad entidad) {
		entidadService.addEntidad(entidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void removeEntidad(final Long idEntidad) {
		entidadService.removeEntidad(idEntidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void updateEntidadSuperAdministrador(final Entidad entidad) {
		entidadService.updateEntidadSuperAdministrador(entidad);
	}

}
