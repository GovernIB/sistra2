package es.caib.sistrahelp.core.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrahelp.core.api.model.ContenidoFichero;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class ConfiguracionServiceBean implements ConfiguracionService {

	@Autowired
	ConfiguracionService configuracionService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public Entidad obtenerDatosEntidad(final String idEntidad) {
		return configuracionService.obtenerDatosEntidad(idEntidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public ContenidoFichero getContentFicheroByPath(final String fichero) {
		return configuracionService.getContentFicheroByPath(fichero);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad) {
		return configuracionService.obtenerPropiedadConfiguracion(propiedad);
	}

}
