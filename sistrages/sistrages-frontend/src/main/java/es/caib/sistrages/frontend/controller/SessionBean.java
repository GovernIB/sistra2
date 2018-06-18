package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeOpcionMenuAdmOper;
import es.caib.sistrages.frontend.model.types.TypeOpcionMenuSuperAdministrador;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Información de sesión.
 *
 * @author Indra
 *
 */
@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean {

	/**
	 * Usuario.
	 */
	private String userName;

	/**
	 * Roles del usuario.
	 */
	private List<TypeRoleAcceso> rolesList;

	/**
	 * Role activo principal (superadmin, admin entidad o desarrollador).
	 */
	private TypeRoleAcceso activeRole;

	/**
	 * Idioma actual.
	 */
	private String lang;

	/**
	 * Locale actual.
	 */
	private Locale locale;

	/**
	 * Entidad actual.
	 */
	private Entidad entidad;

	/**
	 * Servicio seguridad.
	 */
	@Inject
	private SecurityService securityService;

	/**
	 * Servicio entidad.
	 */
	@Inject
	private EntidadService entidadService;

	/**
	 * Lista entidades a las que tiene acceso desde el menú (si es Admin Entidad o
	 * Desarrollador).
	 */
	private List<Entidad> listaEntidades;

	/**
	 * Lista entidades a las que tiene acceso como administrador.
	 */
	private List<Entidad> listaEntidadesAdministrador;

	/**
	 * Lista entidades a las que tiene acceso como desarrollador.
	 */
	private List<Entidad> listaEntidadesDesarrollador;

	/**
	 * Titulo pantalla.
	 */
	private String literalTituloPantalla;

	/**
	 * Logo entidad.
	 */
	private String logo; // TODO PENDIENTE CAMBIAR

	private Map<String, Object> mochilaDatos;

	/** Inicio sesión. */
	@PostConstruct
	public void init() {

		// Recupera info usuario
		userName = securityService.getUsername();
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		rolesList = securityService.getRoles();
		listaEntidadesAdministrador = securityService.getEntidadesAdministrador();
		listaEntidadesDesarrollador = securityService.getEntidadesDesarrollador();

		// Eliminamos role admin y desarrollador si no tiene entidades asociadas
		if (rolesList.contains(TypeRoleAcceso.ADMIN_ENT) && listaEntidadesAdministrador.isEmpty()) {
			rolesList.remove(TypeRoleAcceso.ADMIN_ENT);
		}
		if (rolesList.contains(TypeRoleAcceso.DESAR) && listaEntidadesDesarrollador.isEmpty()) {
			rolesList.remove(TypeRoleAcceso.DESAR);
		}

		// Establece role activo por defecto
		activeRole = null;
		if (rolesList.contains(TypeRoleAcceso.SUPER_ADMIN)) {
			activeRole = TypeRoleAcceso.SUPER_ADMIN;
		} else if (rolesList.contains(TypeRoleAcceso.ADMIN_ENT)) {
			activeRole = TypeRoleAcceso.ADMIN_ENT;
			listaEntidades = listaEntidadesAdministrador;
			entidad = listaEntidades.get(0);
		} else if (rolesList.contains(TypeRoleAcceso.DESAR)) {
			activeRole = TypeRoleAcceso.DESAR;
			listaEntidades = listaEntidadesDesarrollador;
			entidad = listaEntidades.get(0);
		} else {
			// TODO VER GESTION EXCEPCION. GENERAR EXCEPCION PARTICULARIZADA PARA SACAR
			// MENSAJE PARTICULAR Y NO EXCEPCION
			throw new FrontException("No tiene ningún role de acceso o entidad asociada");
		}

		// Establece logo segun role y entidad
		cambiarLogo();

		// inicializamos mochila
		mochilaDatos = new HashMap<>();

	}

	/** Cambio de idioma. */
	public void cambiarIdioma(final String idioma) {
		// Cambia idioma
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());
	}

	/** Cambio role activo. */
	public void cambiarRoleActivo(final String role) {

		// Cambia role
		final TypeRoleAcceso roleChange = TypeRoleAcceso.fromString(role);
		if (!rolesList.contains(roleChange)) {
			throw new FrontException("No tiene el role indicado");
		}
		activeRole = roleChange;

		// Establecemos entidades a mostrar en funcion del role
		listaEntidades = null;
		if (activeRole == TypeRoleAcceso.ADMIN_ENT) {
			listaEntidadesAdministrador = securityService.getEntidadesAdministrador();
			listaEntidades = listaEntidadesAdministrador;
		} else if (activeRole == TypeRoleAcceso.DESAR) {
			listaEntidadesDesarrollador = securityService.getEntidadesDesarrollador();
			listaEntidades = listaEntidadesDesarrollador;
		}

		entidad = null;

		// Para role Admin entidad o Desarrollador verificamos que tenga al menos una
		// entidad y establecemos la primera como activa
		if (activeRole == TypeRoleAcceso.ADMIN_ENT || activeRole == TypeRoleAcceso.DESAR) {
			if (listaEntidades == null || listaEntidades.isEmpty()) {
				// TODO VER GESTION EXCEPCION. GENERAR EXCEPCION PARTICULARIZADA PARA SACAR
				// MENSAJE PARTICULAR Y NO EXCEPCION
				throw new FrontException("No tiene ninguna entidad asociada al role activo");
			}
			entidad = listaEntidades.get(0);
		}

		// Cambia logo
		cambiarLogo();

		// Recarga pagina principal segun role
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());
	}

	/** Cambio entidad activa. */
	public void cambiarEntidadActivo(final long idEntidad) {
		// Cambia entidad
		for (final Entidad e : listaEntidades) {
			if (e.getId().longValue() == idEntidad) {
				entidad = e;
			}
		}
		// Cambio logo
		cambiarLogo();
		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());
	}

	/** Genera menu segun role activo. */
	public MenuModel getMenuModel() {
		final MenuModel model = new DefaultMenuModel();
		if (!TypeRoleAcceso.SUPER_ADMIN.equals(activeRole)) {
			final DefaultSubMenu entidadSubmenu = new DefaultSubMenu(entidad.getNombre().getTraduccion(this.lang));
			entidadSubmenu.setIcon("fa-li fa fa-institution");
			for (final Entidad newEntidad : listaEntidades) {
				if (!entidad.equals(newEntidad)) {
					final DefaultMenuItem item3 = new DefaultMenuItem(newEntidad.getNombre().getTraduccion(this.lang));
					item3.setCommand("#{sessionBean.cambiarEntidadActivo(" + newEntidad.getId() + ")}");
					item3.setIcon("fa-li fa fa-institution");
					entidadSubmenu.addElement(item3);
				}
			}
			model.addElement(entidadSubmenu);
		}

		final DefaultSubMenu firstSubmenu = new DefaultSubMenu(getUserName());
		firstSubmenu.setIcon("fa-li fa fa-user-o");
		final DefaultMenuItem item = new DefaultMenuItem(UtilJSF.getLiteral(getChangeLang()));
		item.setCommand("#{sessionBean.cambiarIdioma(sessionBean.getChangeLang())}");
		item.setIcon("fa-li fa fa-flag");
		firstSubmenu.addElement(item);

		model.addElement(firstSubmenu);

		final DefaultSubMenu secondSubmenu = new DefaultSubMenu(
				UtilJSF.getLiteral("roles." + activeRole.name().toLowerCase()));
		secondSubmenu.setIcon("fa-li fa fa-id-card-o");
		for (final TypeRoleAcceso role : rolesList) {
			if (!activeRole.equals(role)) {
				final DefaultMenuItem item2 = new DefaultMenuItem(
						UtilJSF.getLiteral("roles." + role.name().toLowerCase()));
				item2.setCommand("#{sessionBean.cambiarRoleActivo(\"" + role.toString() + "\")}");
				item2.setIcon("fa-li fa fa-id-card-o");
				secondSubmenu.addElement(item2);
			}
		}
		model.addElement(secondSubmenu);
		model.generateUniqueIds();
		return model;
	}

	/** Opciones del menú. */
	public MenuModel getMenuModelOpciones() {
		final MenuModel model = new DefaultMenuModel();

		DefaultMenuItem item = null;

		if (TypeRoleAcceso.SUPER_ADMIN.equals(activeRole)) {

			for (final TypeOpcionMenuSuperAdministrador opcion : TypeOpcionMenuSuperAdministrador.values()) {
				item = new DefaultMenuItem(UtilJSF.getLiteral("cabecera.opciones." + opcion.name().toLowerCase()));
				item.setUrl(UtilJSF.getUrlOpcionMenuSuperadministrador(opcion));
				model.addElement(item);
			}

		} else {

			for (final TypeOpcionMenuAdmOper opcion : TypeOpcionMenuAdmOper.values()) {
				item = new DefaultMenuItem(UtilJSF.getLiteral("cabecera.opciones." + opcion.name().toLowerCase()));
				item.setUrl(UtilJSF.getUrlOpcionMenuAdmOper(opcion, entidad.getId()));
				model.addElement(item);
			}

		}

		model.generateUniqueIds();
		return model;
	}

	/**
	 * Redirige a la URL por defecto para el rol activo.
	 *
	 */
	public void redirectDefaultUrl() {
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());
	}

	/**
	 * Redirige a la URL por defecto para el rol activo.
	 *
	 */
	public String getDefaultUrl() {
		final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		final String contextPath = servletContext.getContextPath();
		return contextPath + UtilJSF.getDefaultUrlRole(activeRole, obtenerIdEntidad());
	}

	/**
	 * Obtiene lenguaje opuesto al seleccionado (supone solo castellano/catalan).
	 *
	 * @return lang
	 */
	public String getChangeLang() {
		String res = null;
		if ("es".equals(lang)) {
			res = "ca";
		} else {
			res = "es";
		}
		return res;
	}

	/**
	 * Refrescar datos de la entidad.
	 */
	public void refrescarEntidad() {
		int i = 0;
		if (entidad != null) {
			entidad = entidadService.loadEntidad(entidad.getId());

			// Cambia entidad
			for (final Entidad e : listaEntidades) {
				if (e.getId().longValue() == entidad.getId()) {
					break;
				}
				i++;
			}

			listaEntidades.set(i, entidad);

			// Cambio logo
			cambiarLogo();
		}
	}

	/**
	 * Limpia mochila datos.
	 */
	public void limpiaMochilaDatos() {
		mochilaDatos.clear();
	}

	public void limpiaMochilaDatos(final String pClave) {
		mochilaDatos.remove(pClave);
	}

	/**
	 * tiene acceso area.
	 *
	 * @param idArea
	 * @return
	 */
	public boolean tieneAccesoArea(final Long idArea) {
		return securityService.tienePermisosDesarrolladorEntidadByArea(idArea);
	}

	// --------- PRIVATE METHODS --------------------

	/** Cambiar logo. */
	private void cambiarLogo() {
		final String url = "/resources/images/";
		if (TypeRoleAcceso.SUPER_ADMIN.equals(activeRole)) {
			logo = url + Constantes.SUPER_ADMIN_LOGO;
		} else {
			if (entidad.getLogoGestor() != null) {
				logo = Constantes.DESCARGA_FICHEROS_URL + "?id=" + entidad.getLogoGestor().getId();
			} else {
				logo = url + Constantes.ENTIDAD_NO_LOGO;
			}
		}
	}

	/** Obtiene id entidad. */
	private Long obtenerIdEntidad() {
		Long idEntidad = null;
		if (entidad != null) {
			idEntidad = entidad.getId();
		}
		return idEntidad;
	}

	// --------- GETTERS / SETTERS ------------------

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(final SecurityService securityService) {
		this.securityService = securityService;
	}

	public String getLiteralTituloPantalla() {
		return literalTituloPantalla;
	}

	public void setLiteralTituloPantalla(final String titulo) {
		this.literalTituloPantalla = titulo;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(final String logo) {
		this.logo = logo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String user) {
		this.userName = user;
	}

	public TypeRoleAcceso getActiveRole() {
		return activeRole;
	}

	public void setActiveRole(final TypeRoleAcceso activeRole) {
		this.activeRole = activeRole;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(final String lang) {
		this.lang = lang;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
	}

	public List<Entidad> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(final List<Entidad> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public List<Entidad> getListaEntidadesAdministrador() {
		return listaEntidadesAdministrador;
	}

	public void setListaEntidadesAdministrador(final List<Entidad> listaEntidadesAdministrador) {
		this.listaEntidadesAdministrador = listaEntidadesAdministrador;
	}

	public List<Entidad> getListaEntidadesDesarrollador() {
		return listaEntidadesDesarrollador;
	}

	public void setListaEntidadesDesarrollador(final List<Entidad> listaEntidadesDesarrollador) {
		this.listaEntidadesDesarrollador = listaEntidadesDesarrollador;
	}

	public List<TypeRoleAcceso> getRolesList() {
		return rolesList;
	}

	public void setRolesList(final List<TypeRoleAcceso> rolesList) {
		this.rolesList = rolesList;
	}

	public EntidadService getEntidadService() {
		return entidadService;
	}

	public void setEntidadService(final EntidadService entidadService) {
		this.entidadService = entidadService;
	}

	public Map<String, Object> getMochilaDatos() {
		return mochilaDatos;
	}

	public void setMochilaDatos(final Map<String, Object> mapaDatos) {
		this.mochilaDatos = mapaDatos;
	}

}
