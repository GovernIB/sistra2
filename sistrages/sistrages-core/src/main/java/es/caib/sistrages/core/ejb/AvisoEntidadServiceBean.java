package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.AvisoEntidadService;

/**
 * La clase AvisoEntidadServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AvisoEntidadServiceBean implements AvisoEntidadService {

	@Autowired
	AvisoEntidadService avisoEntidadService;

	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public AvisoEntidad getAvisoEntidad(final Long id) {
		return avisoEntidadService.getAvisoEntidad(id);
	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public void addAvisoEntidad(final Long idEntidad, final AvisoEntidad avisoEntidad) {
		avisoEntidadService.addAvisoEntidad(idEntidad, avisoEntidad);

	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public void removeAvisoEntidad(final Long id) {
		avisoEntidadService.removeAvisoEntidad(id);

	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public void updateAvisoEntidad(final AvisoEntidad avisoEntidad) {
		avisoEntidadService.updateAvisoEntidad(avisoEntidad);

	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.ADMIN_ENT)
	public List<AvisoEntidad> listAvisoEntidad(final Long idEntidad, final TypeIdioma idioma, final String filtro) {
		return avisoEntidadService.listAvisoEntidad(idEntidad, idioma, filtro);
	}

}
