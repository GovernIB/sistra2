package es.caib.sistrages.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.model.types.TypeRoleUser;
import es.caib.sistrages.core.api.service.ContextService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;
import es.caib.sistrages.core.service.repository.dao.RolDao;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	private ContextService contextService;

	@Autowired
	private EntidadDao entidadDao;

	@Autowired
	private AreaDao areaDao;

	@Autowired
	private RolDao rolDao;

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
		final List<Entidad> entidades = obtenerEntidadesAdministrador();
		for (final Entidad e : entidades) {
			if (e.getCodigo().longValue() == codigoEntidad) {
				res = true;
				break;
			}
		}
		return res;
	}

	@Override
	@NegocioInterceptor
	public boolean isDesarrolladorEntidad(final long codigoEntidad) {
		boolean res = false;
		final List<Entidad> entidades = obtenerEntidadesDesarrollador();
		for (final Entidad e : entidades) {
			if (e.getCodigo().longValue() == codigoEntidad) {
				res = true;
				break;
			}
		}
		return res;
	}

	@Override
	@NegocioInterceptor
	public List<TypeRolePermisos> getPermisosDesarrolladorEntidadByArea(final long codigoArea) {
		return obtenerPermisosDesarrolladorEntidad(codigoArea);
	}

	@Override
	@NegocioInterceptor
	public boolean tienePermisosDesarrolladorEntidadByArea(final Long codigoArea) {
		final List<TypeRolePermisos> permisos = obtenerPermisosDesarrolladorEntidad(codigoArea);
		return !permisos.isEmpty();
	}

	@Override
	@NegocioInterceptor
	public List<Entidad> getEntidadesAdministrador() {
		return obtenerEntidadesAdministrador();
	}

	@Override
	@NegocioInterceptor
	public List<Entidad> getEntidadesDesarrollador() {
		return obtenerEntidadesDesarrollador();
	}

	@Override
	@NegocioInterceptor
	public String getUsername() {
		return contextService.getUsername();
	}

	/**
	 * Obtiene permisos desarrollador para un area.
	 *
	 * @param codigoArea
	 *            Código area
	 * @return Permisos
	 */
	private List<TypeRolePermisos> obtenerPermisosDesarrolladorEntidad(final long codigoArea) {
		final List<TypeRolePermisos> permisos = new ArrayList<>();
		final String userName = contextService.getUsername();
		final List<Rol> rolesArea = rolDao.getAllByArea(codigoArea);
		for (final Rol r : rolesArea) {
			if ((r.getTipo() == TypeRoleUser.USUARIO && userName.equals(r.getValor()))
					|| (r.getTipo() == TypeRoleUser.ROL && contextService.hashRole(r.getValor()))) {
				if (r.isAlta() && !permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
					permisos.add(TypeRolePermisos.ADMINISTRADOR_AREA);
				}
				if (r.isModificacion() && !permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {
					permisos.add(TypeRolePermisos.DESARROLLADOR_AREA);
				}
				if (r.isConsulta() && !permisos.contains(TypeRolePermisos.CONSULTA)) {
					permisos.add(TypeRolePermisos.CONSULTA);
				}
				if (r.isHelpdesk() && !permisos.contains(TypeRolePermisos.HELPDESK)) {
					permisos.add(TypeRolePermisos.HELPDESK);
				}
			}
		}
		return permisos;
	}

	/**
	 * Obtiene entidades desarrollador.
	 *
	 * @return lista entidades
	 */
	private List<Entidad> obtenerEntidadesDesarrollador() {
		final List<Entidad> res = new ArrayList<Entidad>();
		if (contextService.getRoles().contains(TypeRoleAcceso.DESAR)) {
			final List<Entidad> listaEntidades = entidadDao.getAll();
			for (final Entidad e : listaEntidades) {
				if (e.isActivo()) {
					// Verificamos si existe alguna area de la entidad para la
					// que tenga acceso
					final List<Area> listaAreas = areaDao.getAll(e.getCodigo());
					for (final Area area : listaAreas) {
						if (!obtenerPermisosDesarrolladorEntidad(area.getCodigo()).isEmpty()) {
							res.add(e);
							break; // Pasamos a siguiente entidad
						}
					}
				}
			}
		}
		return res;
	}

	/**
	 * Obtener entidades administrador.
	 *
	 * @return lista entidades
	 */
	private List<Entidad> obtenerEntidadesAdministrador() {
		final List<Entidad> res = new ArrayList<Entidad>();
		if (contextService.getRoles().contains(TypeRoleAcceso.ADMIN_ENT)) {
			final List<Entidad> listaEntidades = entidadDao.getAll();
			for (final Entidad e : listaEntidades) {
				if (e.isActivo() && contextService.hashRole(e.getRol().trim())) {
					res.add(e);
				}
			}
		}
		return res;
	}

}
