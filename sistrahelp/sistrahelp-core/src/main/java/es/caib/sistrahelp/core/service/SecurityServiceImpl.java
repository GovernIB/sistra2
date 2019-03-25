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
	public List<Area> obtenerAreas() {
		final List<Area> res = new ArrayList<>();
		if (contextService.getRoles().contains(TypeRoleAcceso.HELPDESK)
				|| contextService.getRoles().contains(TypeRoleAcceso.SUPERVISOR_ENTIDAD)) {

			for (final RPermisoHelpDesk permiso : sistragesApiComponent.obtenerPermisosHelpdesk()) {

				if (StringUtils.isNoneEmpty(permiso.getValor())) {
					if ("A".equals(permiso.getTipoPermiso())) {
						if ("R".equals(permiso.getTipo().trim()) && contextService.hashRole(permiso.getValor().trim())
								|| "U".equals(permiso.getTipo().trim())
										&& contextService.getUsername().equals(permiso.getValor().trim())) {
							final Area area = new Area();
							area.setCodigoDIR3Entidad(permiso.getCodigoDIR3Entidad());
							area.setIdentificador(permiso.getIdentificadorArea());
							if (!res.contains(area)) {
								res.add(area);
							}
						}
					} else if ("E".equals(permiso.getTipoPermiso())
							&& contextService.hashRole(permiso.getValor().trim())
							&& contextService.getRoles().contains(TypeRoleAcceso.SUPERVISOR_ENTIDAD)
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
		return res;
	}

}
