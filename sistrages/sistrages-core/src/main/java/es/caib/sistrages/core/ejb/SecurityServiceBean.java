package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.SecurityService;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SecurityServiceBean implements SecurityService {

	/** Security service. */
	@Autowired
	SecurityService securityService;

	@Override
	@PermitAll
	public boolean isSuperAdministrador() {
		return securityService.isSuperAdministrador();
	}

	@Override
	@PermitAll
	public boolean isAdministradorEntidad(final long codigoEntidad) {
		return securityService.isAdministradorEntidad(codigoEntidad);
	}

	@Override
	@PermitAll
	public boolean isDesarrolladorEntidad(final long codigoEntidad) {
		return securityService.isDesarrolladorEntidad(codigoEntidad);
	}

	@Override
	@PermitAll
	public List<TypeRolePermisos> getPermisosDesarrolladorEntidad(final long codigoArea) {
		return securityService.getPermisosDesarrolladorEntidad(codigoArea);
	}

	@Override
	@PermitAll
	public List<TypeRoleAcceso> getRoles() {
		return securityService.getRoles();
	}

	@Override
	@PermitAll
	public List<Entidad> getEntidadesAdministrador() {
		return securityService.getEntidadesAdministrador();
	}

	@Override
	@PermitAll
	public List<Entidad> getEntidadesDesarrollador() {
		return securityService.getEntidadesDesarrollador();
	}

	@Override
	@PermitAll
	public String getUsername() {
		return securityService.getUsername();
	}

}
