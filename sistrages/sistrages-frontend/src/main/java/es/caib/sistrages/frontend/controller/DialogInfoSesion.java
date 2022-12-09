package es.caib.sistrages.frontend.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;

@ManagedBean
@ViewScoped
public class DialogInfoSesion extends DialogControllerBase {
	/**
	 * Servicio seguridad.
	 */
	@Inject
	private SecurityService securityService;

	/** Servicio tramite. */
	@Inject
	private TramiteService tramiteService;

	/**
	 * Usuario
	 */
	private String usr;

	/**
	 * Roles
	 */
	private String roles;

	/**
	 * Lista Roles
	 */
	private List<Rol> listaRoles;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {
		if (securityService != null) {
			usr = securityService.getUsername();
			roles = securityService.getRoles().toString().replace("[", "").replace("]", "");
			List<Area> listaAreas = tramiteService.listArea(UtilJSF.getSessionBean().getEntidad().getCodigo(), null);
			Rol rol;
			String permisosStr;
			listaRoles = new ArrayList<>();
			for (Area area : listaAreas) {
				List<TypeRolePermisos> permisos = securityService
						.getPermisosDesarrolladorEntidadByArea(area.getCodigo());
				permisosStr = "";
				if (permisos != null && !permisos.isEmpty()) {
					Iterator<TypeRolePermisos> iterator = permisos.iterator();
					while (iterator.hasNext()) {
						TypeRolePermisos permiso = iterator.next();
						if (iterator.hasNext()) {
							permisosStr += UtilJSF.getLiteral("typeRolePermisos." + permiso.toString()) + ", ";
						} else {
							permisosStr += UtilJSF.getLiteral("typeRolePermisos." + permiso.toString());
						}
					}
				}
				if (securityService.isAdministradorEntidad(UtilJSF.getSessionBean().getEntidad().getCodigo())) {
					permisosStr = UtilJSF.getLiteral("typeRolePermisos.A") + ", "
							+ UtilJSF.getLiteral("typeRolePermisos.H");
				}
				if (!permisosStr.isEmpty()) {
					rol = new Rol();
					rol.setArea(area);
					rol.setValor(permisosStr);

					listaRoles.add(rol);
				}
			}
		}
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the usr
	 */
	public final String getUsr() {
		return usr;
	}

	/**
	 * @param usr the usr to set
	 */
	public final void setUsr(String usr) {
		this.usr = usr;
	}

	/**
	 * @return the roles
	 */
	public final String getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public final void setRoles(String roles) {
		this.roles = roles;
	}

	/**
	 * @return the listaRoles
	 */
	public final List<Rol> getListaRoles() {
		return listaRoles;
	}

	/**
	 * @param listaRoles the listaRoles to set
	 */
	public final void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
	}

}
