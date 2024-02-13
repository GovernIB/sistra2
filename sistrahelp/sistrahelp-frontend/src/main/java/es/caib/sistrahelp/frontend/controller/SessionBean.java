package es.caib.sistrahelp.frontend.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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

import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrahelp.core.api.model.comun.Propiedad;
import es.caib.sistrahelp.core.api.service.SystemService;
import es.caib.sistrahelp.core.api.util.UtilJSON;
import es.caib.sistrahelp.core.api.model.Sesion;
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
	private int indexPerfil = -1;

	/**
	 * Roles del usuario.
	 */
	private List<TypeRoleAcceso> rolesList;

	/**
	 * Idioma actual.
	 */
	private String lang;
	private int indexIdioma = -1;

	/**
	 * Locale actual.
	 */
	private Locale locale;

	/**
	 * Entidad actual.
	 */
	private Entidad entidad;
	private int indexEntidad = -1;

	/** Lista de areas de cada perfil. **/
	private List<Area> listaAreasHelpDesk = new ArrayList<>();
	private List<Area> listaAreasHelpSupervisor = new ArrayList<>();

	/** Lista de entidades segun cada rol. **/
	private List<Entidad> listaEntidadesHelpDesk;
	private List<Entidad> listaEntidadesHelpSupervisor;

	/** Lista de entidades activa en ese momento. **/
	private List<Entidad> listaEntidades;

	/** Lista de areas de cada rol. **/

	/** Lista de areas de la entidad seleccionada. **/
	private List<Area> listaAreasEntidad;

	/**
	 * Servicio seguridad.
	 */
	@Inject
	private SecurityService securityService;

	@Inject
	private SystemService systemService;

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

	/** URL DEL USUARIO SIN ROL. **/
	private static final String URL_ERROR_USUARIO_SIN_ROL = "/error/errorUsuarioSinRol.xhtml";

	private Integer maxInactiveInterval = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getMaxInactiveInterval()*1000;;

	/** Paginación */
	private Integer paginacion;
	private int indexPaginacion = -1;

	/** Propiedad */
	private Propiedad propiedad;

	/** Propiedades */
	private List<Propiedad> propiedades = new ArrayList<>();

	private boolean paginacionCm = false;


	/** Inicio sesión. */
	@PostConstruct
	public void init() {

		Sesion sesion = null;

		// Recupera info usuario
		userName = securityService.getUsername();
		// recuperamos datos por defecto del usuario
		if (StringUtils.isNotEmpty(userName)) {
			sesion = systemService.getSesion(userName);
		}

		if (sesion == null) {
			// asignamos paginación por defecto si no tiene
			setPaginacion(10);
		} else {
			// Obtenemos las propiedades
			propiedades = sesion.getPropiedades();
			obtenerPaginacion(propiedades);

			obtenerPerfil(propiedades);
			obtenerIdioma(propiedades);
			obtenerEntidad(propiedades);
		}

		// asignamos idioma por defecto si lo tiene
		if (sesion != null && StringUtils.isNotEmpty(lang)
				&& ("es".equals(lang) || "ca".equals(lang))) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(lang));
		}

		if(lang == null) {
			lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		}
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		rolesList = securityService.getRoles();

		// Lista de areas según cada rol
		if (rolesList.contains(TypeRoleAcceso.HELPDESK)) {
			listaAreasHelpDesk = securityService.obtenerAreas(TypeRoleAcceso.HELPDESK);
			// Si no tiene areas asociadas a helpdesk, quitar el rol
			if (listaAreasHelpDesk.isEmpty()) {
				rolesList.remove(TypeRoleAcceso.HELPDESK);
			}
		}
		if (rolesList.contains(TypeRoleAcceso.SUPERVISOR_ENTIDAD)) {
			listaAreasHelpSupervisor = securityService.obtenerAreas(TypeRoleAcceso.SUPERVISOR_ENTIDAD);
			if (listaAreasHelpSupervisor.isEmpty()) {
				rolesList.remove(TypeRoleAcceso.SUPERVISOR_ENTIDAD);
			}
		}

		// Lista de entidades según cada rol
		if (rolesList.contains(TypeRoleAcceso.HELPDESK)) {
			listaEntidadesHelpDesk = obtenerEntidades(this.listaAreasHelpDesk);
		}
		if (rolesList.contains(TypeRoleAcceso.SUPERVISOR_ENTIDAD)) {
			listaEntidadesHelpSupervisor = obtenerEntidades(this.listaAreasHelpSupervisor);
		}

		// Limpieza de entidades (si ya están en nivel supervisor, se deben quitar del
		// helpdesk). Si se queda sin entidades el helpDesk, se borra el permiso.
		if (listaEntidadesHelpDesk != null && listaEntidadesHelpSupervisor != null) {
			final List<Entidad> entidadesBorrarHelpDesk = new ArrayList<>();
			for (final Entidad ent : listaEntidadesHelpDesk) {
				for (final Entidad ent2 : listaEntidadesHelpSupervisor) {
					if (ent2.getCodigoDIR3().equals(ent.getCodigoDIR3())) {
						entidadesBorrarHelpDesk.add(ent);
					}
				}
			}

			if (!entidadesBorrarHelpDesk.isEmpty()) {
				listaEntidadesHelpDesk.removeAll(entidadesBorrarHelpDesk);
			}

			if (listaEntidadesHelpDesk.isEmpty()) {
				rolesList.remove(TypeRoleAcceso.HELPDESK);
			}
		}

		// Por defecto, se activa el rol supervisor por defecto y luego el operador
		// helpdesk
		if(activeRole == null) {
			if (rolesList.contains(TypeRoleAcceso.SUPERVISOR_ENTIDAD)) {
				activeRole = TypeRoleAcceso.SUPERVISOR_ENTIDAD;
			} else if (rolesList.contains(TypeRoleAcceso.HELPDESK)) {
				activeRole = TypeRoleAcceso.HELPDESK;
			} else {
				UtilJSF.redirectJsfPage(URL_ERROR_USUARIO_SIN_ROL, null);
				return;
			}
		}

		cargarDatos();

	}


	private void obtenerPerfil(List<Propiedad> list) {
		// recuperamos el perfil
		for (final Propiedad prop : list) {
			if (prop.getCodigo().equals("perfil")) {
				activeRole = TypeRoleAcceso.fromString(prop.getValor());
				indexPerfil = list.indexOf(prop);
				break;
			}
		}
	}

	private void obtenerIdioma(List<Propiedad> list) {
		// recuperamos el idioma
		for (final Propiedad prop : list) {
			if (prop.getCodigo().equals("idioma")) {
				lang = prop.getValor();
				indexIdioma = list.indexOf(prop);
				break;
			}
		}
	}

	private void obtenerEntidad(List<Propiedad> list) {
		// recuperamos el idioma
		for (final Propiedad prop : list) {
			if (prop.getCodigo().equals("entidad")) {
				for (final Entidad e : listaEntidades) {
					if (e.getCodigo().toString() == prop.getValor()) {
						entidad = e;
					}
				}
				indexEntidad = list.indexOf(prop);
				break;
			}
		}
	}

	private void obtenerPaginacion(List<Propiedad> list) {
		// recuperamos la paginación
		for (final Propiedad prop : list) {
			if (prop.getCodigo().equals("paginacion")) {
				paginacion = Integer.valueOf(prop.getValor());
				indexPaginacion = list.indexOf(prop);
				break;
			}
		}
	}

	/** Método que carga las entidades ya las areas. **/
	private void cargarDatos() {
		if (activeRole == TypeRoleAcceso.SUPERVISOR_ENTIDAD) {
			listaEntidades = listaEntidadesHelpSupervisor;
		} else if (activeRole == TypeRoleAcceso.HELPDESK) {
			listaEntidades = listaEntidadesHelpDesk;
		} else {
			UtilJSF.redirectJsfPage(URL_ERROR_USUARIO_SIN_ROL, null);
			return;
		}

		if (!listaEntidades.isEmpty()) {
			if(entidad == null) {
				entidad = listaEntidades.get(0);
			}
			if (activeRole == TypeRoleAcceso.SUPERVISOR_ENTIDAD) {
				listaAreasEntidad = obtenerAreasEntidad(listaAreasHelpSupervisor, entidad);
			} else {
				listaAreasEntidad = obtenerAreasEntidad(listaAreasHelpDesk, entidad);
			}
		} else {
			UtilJSF.redirectJsfPage(URL_ERROR_USUARIO_SIN_ROL, null);
			return;
		}

		// Cambia logo
		cambiarLogo();

		// inicializamos mochila
		mochilaDatos = new HashMap<>();
	}

	/** Cambiar rol **/
	public void cambiarRoleActivo(final String role) {

		// Cambia role
		final TypeRoleAcceso roleChange = TypeRoleAcceso.fromString(role);
		activeRole = roleChange;

		// Cargamos los datos según el rol
		cargarDatos();

		// actualizamos sesion usuario
		setActiveRole(roleChange);

		// Recarga pagina principal segun role
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());

	}

	/** Cambio de idioma. */
	public void cambiarIdioma(final String idioma) {
		// Cambia idioma
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		// actualizamos sesion usuario
		setLang(idioma);

		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());
	}

	/** Genera menu segun role activo. */
	public MenuModel getMenuModel() {
		final MenuModel model = new DefaultMenuModel();

		DefaultSubMenu entidadSubmenu = null;

		if (entidad != null) {
			final String nombreEntidad = (entidad.getNombre().getTraduccion(this.lang).length() < 28
					? entidad.getNombre().getTraduccion(this.lang)
					: entidad.getNombre().getTraduccion(this.lang).substring(0, 25) + "...");

			entidadSubmenu = new DefaultSubMenu(nombreEntidad);
		} else {
			entidadSubmenu = new DefaultSubMenu("-");
		}

		entidadSubmenu.setIcon("fa-li fa fa-institution");

		if (entidad != null && listaEntidades != null) {
			for (final Entidad newEntidad : listaEntidades) {
				if (!entidad.equals(newEntidad)) {
					final String nombreSubEntidad = (newEntidad.getNombre().getTraduccion(this.lang).length() < 28
							? newEntidad.getNombre().getTraduccion(this.lang)
							: newEntidad.getNombre().getTraduccion(this.lang).substring(0, 25) + "...");

					final DefaultMenuItem item3 = new DefaultMenuItem(nombreSubEntidad);
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
				if (activeRole == TypeRoleAcceso.SUPERVISOR_ENTIDAD) {
					listaAreasEntidad = obtenerAreasEntidad(listaAreasHelpSupervisor, entidad);
				} else {
					listaAreasEntidad = obtenerAreasEntidad(listaAreasHelpDesk, entidad);
				}
			}
		}
		// Cambio logo
		cambiarLogo();

		// actualizamos sesion usuario
		setEntidad(entidad);

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
			if (!opcion.name().equals("alertas")
					|| (opcion.name().equals("alertas") && this.activeRole.equals(TypeRoleAcceso.SUPERVISOR_ENTIDAD))) {
				item = new DefaultMenuItem(UtilJSF.getLiteral("cabecera.opciones." + opcion.name().toLowerCase()));
				item.setUrl(UtilJSF.getUrlOpcionMenu(opcion, idEntidad));
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

			HttpSession sessionHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			if (sessionHttp != null) {
				sessionHttp.setAttribute("LOGO_ENTIDAD_ACTIVA", entidad.getLogoGestor());
			}else {
				sessionHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(true);
				sessionHttp.setAttribute("LOGO_ENTIDAD_ACTIVA", entidad.getLogoGestor());
			}
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

	private List<Area> obtenerAreasEntidad(final List<Area> pListaAreas, final Entidad pEntidad) {
		List<Area> listaResultado = null;

		if (pListaAreas != null && !pListaAreas.isEmpty()) {
			listaResultado = new ArrayList<>();
			for (final Area area : pListaAreas) {
				if (pEntidad.getCodigoDIR3().equals(area.getCodigoDIR3Entidad())) {
					listaResultado.add(area);
				}
			}
		}

		return listaResultado;
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

		// actualizamos propiedades de sesión
		propiedad = new Propiedad();
		propiedad.setCodigo("perfil");
		propiedad.setValor(activeRole.toString());

		if (UtilJSON.toJSON(propiedades).contains(propiedad.getCodigo())) {
			propiedades.removeIf(prop -> prop.getCodigo().equals("perfil"));
		}
		propiedades.add(propiedad);
		String pPropiedades = (UtilJSON.toJSON(propiedades));

		// actualizamos sesion usuario
		systemService.updateSesionPropiedades(userName, pPropiedades);
		indexPerfil = propiedades.indexOf(propiedad);
	}

	public String getLang() {
		return lang;
	}

	public void setLang(final String lang) {
		this.lang = lang;

		// actualizamos propiedades de sesión
		propiedad = new Propiedad();
		propiedad.setCodigo("idioma");
		propiedad.setValor(lang);

		if (UtilJSON.toJSON(propiedades).contains(propiedad.getCodigo())) {
			propiedades.removeIf(prop -> prop.getCodigo().equals("idioma"));
		}
		propiedades.add(propiedad);
		String pPropiedades = (UtilJSON.toJSON(propiedades));

		// actualizamos sesion usuario
		systemService.updateSesionPropiedades(userName, pPropiedades);
		indexIdioma = propiedades.indexOf(propiedad);
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

		// actualizamos propiedades de sesión
		propiedad = new Propiedad();
		propiedad.setCodigo("entidad");
		propiedad.setValor(entidad.getCodigo().toString());

		if (UtilJSON.toJSON(propiedades).contains(propiedad.getCodigo())) {
			propiedades.removeIf(prop -> prop.getCodigo().equals("entidad"));
		}
		propiedades.add(propiedad);
		String pPropiedades = (UtilJSON.toJSON(propiedades));

		// actualizamos sesion usuario
		systemService.updateSesionPropiedades(userName, pPropiedades);
		indexEntidad = propiedades.indexOf(propiedad);
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

	public List<Area> getListaAreasEntidad() {
		return listaAreasEntidad;
	}

	public void setListaAreasEntidad(final List<Area> listaAreasEntidad) {
		this.listaAreasEntidad = listaAreasEntidad;
	}

	/**
	 * @return the paginacion
	 */
	public final Integer getPaginacion() {
		return paginacion;
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public final void setPaginacion(Integer paginacion) {
		// Si se ha cambiado la paginación se actualiza en la sesión
		if (!paginacion.equals(this.paginacion)) {
			// Cambia paginación
			this.paginacion = paginacion;

			// actualizamos propiedades de sesión
			propiedad = new Propiedad();
			propiedad.setCodigo("paginacion");
			propiedad.setValor(Integer.toString(paginacion));

			if (UtilJSON.toJSON(propiedades).contains(propiedad.getCodigo())) {
				propiedades.removeIf(prop -> prop.getCodigo().equals("paginacion"));
			}
			propiedades.add(propiedad);
			String pPropiedades = (UtilJSON.toJSON(propiedades));

			// actualizamos sesion usuario
			systemService.updateSesionPropiedades(userName, pPropiedades);
			indexPaginacion = propiedades.indexOf(propiedad);

			if(isPaginacionCm()) {
				setPaginacionCm(false);
				PrimeFaces.current().executeScript("document.getElementById('form:btnBuscar').click()");
			}
		}
	}

	/**
	 * @return the maxInactiveInterval
	 */
	public Integer getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	/**
	 * @param maxInactiveInterval the maxInactiveInterval to set
	 */
	public void setMaxInactiveInterval(Integer maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	/**
	 * @return the indexPaginacion
	 */
	public int getIndexPaginacion() {
		return indexPaginacion;
	}

	/**
	 * @param indexPaginacion the indexPaginacion to set
	 */
	public void setIndexPaginacion(int indexPaginacion) {
		this.indexPaginacion = indexPaginacion;
	}

	/**
	 * @return the propiedad
	 */
	public Propiedad getPropiedad() {
		return propiedad;
	}

	/**
	 * @param propiedad the propiedad to set
	 */
	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

	/**
	 * @return the propiedades
	 */
	public List<Propiedad> getPropiedades() {
		return propiedades;
	}

	/**
	 * @param propiedades the propiedades to set
	 */
	public void setPropiedades(List<Propiedad> propiedades) {
		this.propiedades = propiedades;
	}

	/**
	 * @return the indexPerfil
	 */
	public int getIndexPerfil() {
		return indexPerfil;
	}

	/**
	 * @param indexPerfil the indexPerfil to set
	 */
	public void setIndexPerfil(int indexPerfil) {
		this.indexPerfil = indexPerfil;
	}

	/**
	 * @return the indexIdioma
	 */
	public int getIndexIdioma() {
		return indexIdioma;
	}


	/**
	 * @param indexIdioma the indexIdioma to set
	 */
	public void setIndexIdioma(int indexIdioma) {
		this.indexIdioma = indexIdioma;
	}

	/**
	 * @return the indexEntidad
	 */
	public int getIndexEntidad() {
		return indexEntidad;
	}


	/**
	 * @param indexEntidad the indexEntidad to set
	 */
	public void setIndexEntidad(int indexEntidad) {
		this.indexEntidad = indexEntidad;
	}

	/**
	 * @return the paginacionCm
	 */
	public boolean isPaginacionCm() {
		return paginacionCm;
	}


	/**
	 * @param paginacionCm the paginacionCm to set
	 */
	public void setPaginacionCm(boolean paginacionCm) {
		this.paginacionCm = paginacionCm;
	}
}
