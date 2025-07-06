package es.caib.sistrahelp.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.rest.api.interna.RPermisoHelpDesk;
import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrahelp.core.api.service.ContextService;
import es.caib.sistrahelp.core.api.service.SecurityService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;
import es.caib.sistrahelp.core.service.component.SistragesApiComponent;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private ContextService contextService;

	@Autowired
	private SistragesApiComponent sistragesApiComponent;

	@Override
	@NegocioInterceptor
	public List<TypeRoleAcceso> getRoles() {
		return contextService.getRoles();
	}

	@Override
	@NegocioInterceptor
	public String getUsername() {
		return contextService.getUsername();
	}

	@Override
	@NegocioInterceptor
	public List<Area> obtenerAreas(final TypeRoleAcceso rol) {
		final List<Area> res = new ArrayList<>();

		boolean permisoCau = false;

		if (contextService.getRoles().contains(rol)) {

			for (final RPermisoHelpDesk permiso : sistragesApiComponent.obtenerPermisosHelpdesk()) {

				if (StringUtils.isNoneEmpty(permiso.getValor())) {

					// añadimos areas de permisos de areas para rol operador
					if ("A".equals(permiso.getTipoPermiso()) && esPermisoDeUsuario(permiso)) {

						if(rol == TypeRoleAcceso.HELPDESK) {
							final Area area = new Area();
							area.setCodigoDIR3Entidad(permiso.getCodigoDIR3Entidad());
							area.setIdentificador(permiso.getIdentificadorArea());
							if (!res.contains(area)) {
								res.add(area);
							}
						}

						if(rol == TypeRoleAcceso.PERSONAL_CAU) {
							permisoCau = true;
						}
					}

					// añadimos areas de permisos entidad para roles supervisor y cau. Se añade el listado de areas si tiene
					else if ("E".equals(permiso.getTipoPermiso()) && ( TypeRoleAcceso.SUPERVISOR_ENTIDAD.equals(rol) ||  TypeRoleAcceso.PERSONAL_CAU.equals(rol)  )
							&& contextService.hashRole(permiso.getValor().trim())
							&& ( contextService.getRoles().contains(TypeRoleAcceso.SUPERVISOR_ENTIDAD) || contextService.getRoles().contains(TypeRoleAcceso.PERSONAL_CAU) )
							&& permiso.getListaIdentificadorArea() != null) {
						for (final String idArea : permiso.getListaIdentificadorArea()) {
							final Area area = new Area();
							area.setCodigoDIR3Entidad(permiso.getCodigoDIR3Entidad());
							area.setIdentificador(idArea);
							if (!res.contains(area)) {
								res.add(area);
							}
						}
					}
				}
			}
		}

		// Si tiene rol CAU pero con acceso inhabilitado para sth le quitamos los permisos por entidades
		if(rol == TypeRoleAcceso.PERSONAL_CAU && !permisoCau){
			res.clear();
		}

		return res;
	}


	private boolean esPermisoDeUsuario(RPermisoHelpDesk permiso){
		boolean permisoSuRol = "R".equals(permiso.getTipo().trim()) && contextService.hashRole(permiso.getValor().trim());

		return  permisoSuRol ||
				( "U".equals(permiso.getTipo().trim()) && contextService.getUsername().equals(permiso.getValor().trim() ));
	}
}
