package es.caib.sistrages.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ContextService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	ContextService contextService;

	@Autowired
	EntidadDao entidadDao;

	@Override
	@NegocioInterceptor
	public List<TypeRoleAcceso> getRoles() {
		return contextService.getRoles();
	}

	@Override
	@NegocioInterceptor
	public boolean isSuperAdministrador() {
		return contextService.getRoles().contains(TypeRoleAcceso.SUPER_ADMIN);
	}

	@Override
	@NegocioInterceptor
	public boolean isAdministradorEntidad(final long codigoEntidad) {
		boolean res = false;
		if (contextService.getRoles().contains(TypeRoleAcceso.SUPER_ADMIN)) {
			final List<Entidad> entidades = getEntidadesAdministrador();
			for (final Entidad e : entidades) {
				if (e.getId().longValue() == codigoEntidad) {
					res = true;
					break;
				}
			}
		}
		return res;
	}

	@Override
	@NegocioInterceptor
	public boolean isDesarrolladorEntidad(final long codigoEntidad) {
		boolean res = false;
		if (contextService.getRoles().contains(TypeRoleAcceso.ADMIN_ENT)) {
			final List<Entidad> entidades = getEntidadesDesarrollador();
			for (final Entidad e : entidades) {
				if (e.getId().longValue() == codigoEntidad) {
					res = true;
					break;
				}
			}
		}
		return res;
	}

	@Override
	@NegocioInterceptor
	public List<TypeRolePermisos> getPermisosDesarrolladorEntidad(final long codigoArea) {
		// TODO Auto-generated method stub
		final List<TypeRolePermisos> permisos = new ArrayList<TypeRolePermisos>();
		permisos.add(TypeRolePermisos.ALTA_BAJA);
		permisos.add(TypeRolePermisos.MODIFICACION);
		permisos.add(TypeRolePermisos.CONSULTA);
		return permisos;
	}

	@Override
	@NegocioInterceptor
	public List<Entidad> getEntidadesAdministrador() {
		// TODO Pendiente filtro por role
		final List<Entidad> listaEntidades = entidadDao.getAll();
		return listaEntidades;
	}

	@Override
	@NegocioInterceptor
	public List<Entidad> getEntidadesDesarrollador() {
		// TODO Pendiente filtro por role
		final List<Entidad> listaEntidades = entidadDao.getAll();
		return listaEntidades;
	}

	@Override
	@NegocioInterceptor
	public String getUsername() {
		return contextService.getUsername();
	}

}
