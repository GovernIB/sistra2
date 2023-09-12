package es.caib.sistrahelp.core.ejb;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.digester.plugins.PluginException;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrahelp.core.api.model.ContenidoFichero;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class ConfiguracionServiceBean implements ConfiguracionService {

	@Autowired
	ConfiguracionService configuracionService;

	@Override
	@PermitAll
	public Entidad obtenerDatosEntidad(final String idEntidad) {
		return configuracionService.obtenerDatosEntidad(idEntidad);
	}

	@Override
	@PermitAll
	public Entidad obtenerDatosEntidadByArea(final String idArea) {
		return configuracionService.obtenerDatosEntidadByArea(idArea);
	}

	@Override
	@PermitAll
	public ContenidoFichero getContentFicheroByPath(final String fichero) {
		return configuracionService.getContentFicheroByPath(fichero);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad) {
		return configuracionService.obtenerPropiedadConfiguracion(propiedad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public String obtenerPropiedadConfiguracionSistrages(TypePropiedadConfiguracion propiedad) {
		return configuracionService.obtenerPropiedadConfiguracionSistrages(propiedad);
	}

	@Override
	@PermitAll
	public IPlugin obtenerPluginGlobal(TypePluginGlobal tipoPlugin) throws PluginException {
		return configuracionService.obtenerPluginGlobal(tipoPlugin);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public List<String> obtenerTramitesPorArea(String idArea) {
		return configuracionService.obtenerTramitesPorArea(idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public List<Integer> obtenerVersionTramite(String identificador, String tramite) {
		return configuracionService.obtenerVersionTramite(identificador,tramite);
	}

}
