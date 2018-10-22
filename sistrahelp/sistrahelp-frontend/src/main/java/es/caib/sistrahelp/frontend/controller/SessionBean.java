package es.caib.sistrahelp.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.SecurityService;
import es.caib.sistrahelp.frontend.model.types.TypeOpcionMenu;
import es.caib.sistrahelp.frontend.util.UtilJSF;

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
	 * Role activo principal.
	 */
	private TypeRoleAcceso activeRole;

	/**
	 * Roles del usuario.
	 */
	private List<TypeRoleAcceso> rolesList;

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

	private List<Entidad> listaEntidades;

	private List<Area> listaAreas;

	/**
	 * Servicio seguridad.
	 */
	@Inject
	private SecurityService securityService;

	@Inject
	private ConfiguracionService configuracionService;

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

		if (rolesList.contains(TypeRoleAcceso.HELPDESK)) {
			activeRole = TypeRoleAcceso.HELPDESK;
		} else {
			UtilJSF.redirectJsfPage("/error/errorUsuarioSinRol.xhtml", new HashMap<String, List<String>>());
			return;
		}

		listaAreas = securityService.obtenerAreas();

		listaEntidades = obtenerEntidades(listaAreas);

		if (!listaEntidades.isEmpty()) {
			entidad = listaEntidades.get(0);
		} else {
			UtilJSF.redirectJsfPage("/error/errorUsuarioSinRol.xhtml", new HashMap<String, List<String>>());
			return;
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

	/** Genera menu segun role activo. */
	public MenuModel getMenuModel() {
		final MenuModel model = new DefaultMenuModel();

		DefaultSubMenu entidadSubmenu = null;

		if (entidad != null) {
			entidadSubmenu = new DefaultSubMenu(entidad.getNombre().getTraduccion(this.lang));
		} else {
			entidadSubmenu = new DefaultSubMenu("-");
		}

		entidadSubmenu.setIcon("fa-li fa fa-institution");

		if (entidad != null && listaEntidades != null) {
			for (final Entidad newEntidad : listaEntidades) {
				if (!entidad.equals(newEntidad)) {
					final DefaultMenuItem item3 = new DefaultMenuItem(newEntidad.getNombre().getTraduccion(this.lang));
					item3.setCommand("#{sessionBean.cambiarEntidadActivo(\"" + newEntidad.getCodigoDIR3() + "\")}");
					item3.setIcon("fa-li fa fa-institution");
					entidadSubmenu.addElement(item3);
				}
			}
		}

		model.addElement(entidadSubmenu);

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

	/** Cambio entidad activa. */
	public void cambiarEntidadActivo(final String idEntidad) {
		// Cambia entidad
		for (final Entidad e : listaEntidades) {
			if (e.getCodigoDIR3().equals(idEntidad)) {
				entidad = e;
			}
		}
		// Cambio logo
		cambiarLogo();
		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());
	}

	/** Opciones del menú. */
	public MenuModel getMenuModelOpciones() {
		final MenuModel model = new DefaultMenuModel();

		DefaultMenuItem item = null;
		Long idEntidad = null;

		if (entidad != null) {
			idEntidad = entidad.getCodigo();
		}

		for (final TypeOpcionMenu opcion : TypeOpcionMenu.values()) {
			item = new DefaultMenuItem(UtilJSF.getLiteral("cabecera.opciones." + opcion.name().toLowerCase()));
			item.setUrl(UtilJSF.getUrlOpcionMenu(opcion, idEntidad));
			model.addElement(item);
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
	 * Limpia mochila datos.
	 */
	public void limpiaMochilaDatos() {
		mochilaDatos.clear();
	}

	public void limpiaMochilaDatos(final String pClave) {
		mochilaDatos.remove(pClave);
	}

	public Entidad getEntidad(final String id) {
		Entidad res = null;

		for (final Entidad ent : listaEntidades) {
			if (ent.getCodigoDIR3().equals(id)) {
				res = ent;
			}
		}

		return res;
	}

	// --------- PRIVATE METHODS --------------------

	/** Cambiar logo. */
	private void cambiarLogo() {
		final String url = "/resources/images/";

		if (entidad != null && entidad.getLogoGestor() != null) {
			logo = Constantes.DESCARGA_FICHEROS_URL;

			final HttpSession sessionHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);

			sessionHttp.setAttribute("LOGO_ENTIDAD_ACTIVA", entidad.getLogoGestor());
		} else {
			logo = url + Constantes.ENTIDAD_NO_LOGO;
		}

	}

	/** Obtiene id entidad. */
	private Long obtenerIdEntidad() {
		Long idEntidad = null;
		if (entidad != null) {
			idEntidad = entidad.getCodigo();
		}
		return idEntidad;
	}

	private List<Entidad> obtenerEntidades(final List<Area> listaAreas) {
		final Set<String> cEntidades = new HashSet<>();
		final List<Entidad> lista = new ArrayList<>();
		for (final Area area : listaAreas) {
			if (!cEntidades.contains(area.getCodigoDIR3Entidad())) {
				lista.add(configuracionService.obtenerDatosEntidad(area.getCodigoDIR3Entidad()));
				cEntidades.add(area.getCodigoDIR3Entidad());
			}
		}

		return lista;

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

	public List<TypeRoleAcceso> getRolesList() {
		return rolesList;
	}

	public void setRolesList(final List<TypeRoleAcceso> rolesList) {
		this.rolesList = rolesList;
	}

	public Map<String, Object> getMochilaDatos() {
		return mochilaDatos;
	}

	public void setMochilaDatos(final Map<String, Object> mapaDatos) {
		this.mochilaDatos = mapaDatos;
	}

}
