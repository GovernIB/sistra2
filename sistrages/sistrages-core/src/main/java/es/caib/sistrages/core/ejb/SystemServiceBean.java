package es.caib.sistrages.core.ejb;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Sesion;
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
	public String obtenerPropiedadConfiguracion(final String propiedad) {
		return systemService.obtenerPropiedadConfiguracion(propiedad);
	}

	@Override
	@PermitAll
	public boolean verificarMaestro(final String appId) {
		return systemService.verificarMaestro(appId);
	}

	@Override
	@PermitAll
	public Sesion getSesion(final String pUserName) {
		return systemService.getSesion(pUserName);
	}

	@Override
	@PermitAll
	public void updateSesionPerfil(final String pUserName, final String pPerfil) {
		systemService.updateSesionPerfil(pUserName, pPerfil);

	}

	@Override
	@PermitAll
	public void updateSesionIdioma(final String pUserName, final String pIdioma) {
		systemService.updateSesionIdioma(pUserName, pIdioma);
	}

	@Override
	@PermitAll
	public void updateSesionEntidad(final String pUserName, final Long pIdEntidad) {
		systemService.updateSesionEntidad(pUserName, pIdEntidad);
	}

}
