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

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Sesion;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeOpcionMenuAdmOper;
import es.caib.sistrages.frontend.model.types.TypeOpcionMenuSuperAdministrador;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

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
	 * Idiomas
	 */
	private List<String> idiomas;

	/**
	 * Configuracion global..
	 */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

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

	@Inject
	private SystemService systemService;

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

	/** Atributos del growl. **/
	private Boolean growlSticky = false;
	private Integer growlLife = 4000;

	/** Datos que obtienen el ancho y alto de la pantalla. **/
	private Integer height;
	private Integer width;

	/** Paginación */
	private Integer paginacion;

	public void submitAspect() {

		String swidth = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("hiddenWidth");
		String sheight = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("hiddenHeight");
		if (swidth != null) {
			width = Integer.parseInt(swidth);
		}
		if (sheight != null) {
			height = Integer.parseInt(sheight);
		}

	}

	private Map<String, Object> mochilaDatos;

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

		// asignamos idioma por defecto si lo tiene
		if (sesion != null && StringUtils.isNotEmpty(sesion.getIdioma())
				&& ("es".equals(sesion.getIdioma()) || "ca".equals(sesion.getIdioma()))) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(sesion.getIdioma()));
		}

		// asignamos paginación por defecto si no tiene
		if (getPaginacion() == null) {
			setPaginacion(10);
		}
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
		if (sesion != null && StringUtils.isNotEmpty(sesion.getPerfil())
				&& rolesList.contains(TypeRoleAcceso.fromString(sesion.getPerfil()))) {

			if (TypeRoleAcceso.SUPER_ADMIN.equals(TypeRoleAcceso.fromString(sesion.getPerfil()))) {
				activeRole = TypeRoleAcceso.SUPER_ADMIN;
				systemService.updateSesionEntidad(userName, null);

			} else if (TypeRoleAcceso.ADMIN_ENT.equals(TypeRoleAcceso.fromString(sesion.getPerfil()))) {
				activeRole = TypeRoleAcceso.ADMIN_ENT;
				listaEntidades = listaEntidadesAdministrador;

				if (sesion.getEntidad() != null) {
					final Long idEntidad = sesion.getEntidad();
					entidad = listaEntidades.stream().filter(o -> o.getCodigo().equals(idEntidad)).findAny()
							.orElse(null);
				}

				if (entidad == null) {
					entidad = listaEntidades.get(0);
					systemService.updateSesionEntidad(userName, entidad.getCodigo());
				}
			} else if (TypeRoleAcceso.DESAR.equals(TypeRoleAcceso.fromString(sesion.getPerfil()))) {
				activeRole = TypeRoleAcceso.DESAR;
				listaEntidades = listaEntidadesDesarrollador;

				if (sesion.getEntidad() != null) {
					final Long idEntidad = sesion.getEntidad();
					entidad = listaEntidades.stream().filter(o -> o.getCodigo().equals(idEntidad)).findAny()
							.orElse(null);
				}

				if (entidad == null) {
					entidad = listaEntidades.get(0);
					systemService.updateSesionEntidad(userName, entidad.getCodigo());
				}
			}

		} else if (rolesList.contains(TypeRoleAcceso.SUPER_ADMIN)) {
			activeRole = TypeRoleAcceso.SUPER_ADMIN;
			systemService.updateSesionPerfil(userName, activeRole.toString());
			systemService.updateSesionIdioma(userName, locale.getLanguage());
			systemService.updateSesionEntidad(userName, null);
		} else if (rolesList.contains(TypeRoleAcceso.ADMIN_ENT)) {
			activeRole = TypeRoleAcceso.ADMIN_ENT;
			listaEntidades = listaEntidadesAdministrador;
			entidad = listaEntidades.get(0);
			systemService.updateSesionPerfil(userName, activeRole.toString());
			systemService.updateSesionIdioma(userName, locale.getLanguage());
			systemService.updateSesionEntidad(userName, entidad.getCodigo());
		} else if (rolesList.contains(TypeRoleAcceso.DESAR)) {
			activeRole = TypeRoleAcceso.DESAR;
			listaEntidades = listaEntidadesDesarrollador;
			entidad = listaEntidades.get(0);
			systemService.updateSesionPerfil(userName, activeRole.toString());
			systemService.updateSesionIdioma(userName, locale.getLanguage());
			systemService.updateSesionEntidad(userName, entidad.getCodigo());
		} else {
			UtilJSF.redirectJsfPage("/error/errorUsuarioSinRol.xhtml", null);
			return;
		}

		idiomas = UtilTraducciones
				.getIdiomas(configuracionGlobalService.getConfiguracionGlobal("sistramit.idiomas").getValor());

		// Establece logo segun role y entidad
		cambiarLogo();

		// inicializamos mochila
		mochilaDatos = new HashMap<>();

		prepararGrowl(configuracionGlobalService.getConfiguracionGlobal("growl.propiedades").getValor());
	}

	/**
	 * Para extraer la info para el growl.
	 *
	 * @param valor
	 */
	private void prepararGrowl(final String valor) {
		if (valor != null && !valor.isEmpty()) {
			for (final String prop : valor.split("#")) {
				if (prop.contains("=") && prop.split("=").length == 2) {
					final String[] propiedad = prop.split("=");
					switch (propiedad[0]) {
					case "sticky":
						setGrowlSticky(Boolean.valueOf(propiedad[1]));
						break;

					case "life":
						growlLife = Integer.valueOf(propiedad[1]);
						break;
					default:
						break;
					}
				}
			}
		}

	}

	/**
	 * Muestra el mensaje de error
	 */
	public void showError() {
		if (getMochilaDatos().get(Constantes.CLAVE_MOCHILA_ERRORES_MSG) != null) {
			final String msg = (String) getMochilaDatos().get(Constantes.CLAVE_MOCHILA_ERRORES_MSG);
			String title = null;
			if (getMochilaDatos().get(Constantes.CLAVE_MOCHILA_ERRORES_TLE) != null) {
				title = (String) getMochilaDatos().get(Constantes.CLAVE_MOCHILA_ERRORES_TLE);
			}
			final TypeNivelGravedad gravedad = TypeNivelGravedad
					.valueOf(getMochilaDatos().get(Constantes.CLAVE_MOCHILA_ERRORES_NVL).toString());
			if (title == null) {
				UtilJSF.addMessageContext(gravedad, msg);
			} else {
				UtilJSF.addMessageContext(gravedad, msg, title);
			}
			limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_ERRORES_MSG);
			limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_ERRORES_NVL);
			limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_ERRORES_TLE);
		}
	}

	/** Cambio de idioma. */
	public void cambiarIdioma(final String idioma) {
		// Cambia idioma
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		// actualizamos sesion usuario
		systemService.updateSesionIdioma(userName, idioma);

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
		} else if (activeRole == TypeRoleAcceso.SUPER_ADMIN) {
			listaEntidades = null;
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
			systemService.updateSesionEntidad(userName, entidad.getCodigo());
		} else {
			systemService.updateSesionEntidad(userName, null);

		}

		// Cambia logo
		cambiarLogo();

		// actualizamos sesion usuario
		systemService.updateSesionPerfil(userName, role);

		// Recarga pagina principal segun role
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());
	}

	/** Cambio entidad activa. */
	public void cambiarEntidadActivo(final long idEntidad) {
		// Cambia entidad
		for (final Entidad e : listaEntidades) {
			if (e.getCodigo().longValue() == idEntidad) {
				entidad = e;
			}
		}
		// Cambio logo
		cambiarLogo();

		// actualizamos sesion usuario
		systemService.updateSesionEntidad(userName, idEntidad);

		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole, obtenerIdEntidad());
	}

	/** Abrir dialog info sesion. */
	public void infoSesion() {
		UtilJSF.openDialog(DialogInfoSesion.class, TypeModoAcceso.CONSULTA, null, true, 620, 340);
	}

	/** Genera menu segun role activo. */
	public MenuModel getMenuModel() {
		final MenuModel model = new DefaultMenuModel();
		if (!TypeRoleAcceso.SUPER_ADMIN.equals(activeRole)) {
			final String nombreEntidad = (entidad.getNombre().getTraduccion(this.lang).length() < 28
					? entidad.getNombre().getTraduccion(this.lang)
					: entidad.getNombre().getTraduccion(this.lang).substring(0, 25) + "...");
			final DefaultSubMenu entidadSubmenu = new DefaultSubMenu(nombreEntidad);
			entidadSubmenu.setIcon("fa-li fa fa-institution");
			for (final Entidad newEntidad : listaEntidades) {
				if (!entidad.equals(newEntidad)) {
					final String nombreSubEntidad = (newEntidad.getNombre().getTraduccion(this.lang).length() < 28
							? newEntidad.getNombre().getTraduccion(this.lang)
							: newEntidad.getNombre().getTraduccion(this.lang).substring(0, 25) + "...");
					final DefaultMenuItem item3 = new DefaultMenuItem(nombreSubEntidad);
					item3.setCommand("#{sessionBean.cambiarEntidadActivo(" + newEntidad.getCodigo() + ")}");
					item3.setIcon("fa-li fa fa-institution");
					entidadSubmenu.addElement(item3);
				}
			}
			model.addElement(entidadSubmenu);
		}

		final DefaultSubMenu firstSubmenu = new DefaultSubMenu(getUserName());
		firstSubmenu.setIcon("fa-li fa fa-user-o");
		final DefaultMenuItem item0 = new DefaultMenuItem(UtilJSF.getLiteral("cabecera.opciones.datosSesion"));
		item0.setCommand("#{sessionBean.infoSesion()}");
		item0.setProcess("@this");
		item0.setUpdate("growlHeader");
		firstSubmenu.addElement(item0);
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
				if (!TypeRoleAcceso.HELPDESK.equals(role) && !TypeRoleAcceso.SUPERVISOR_ENTIDAD.equals(role)) {
					secondSubmenu.addElement(item2);
				}

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
				if (!opcion.equals(TypeOpcionMenuAdmOper.ENVIOS_REMOTOS) || UtilJSF.isServicioActivado()) {
					item = new DefaultMenuItem(UtilJSF.getLiteral("cabecera.opciones." + opcion.name().toLowerCase()));
					item.setUrl(UtilJSF.getUrlOpcionMenuAdmOper(opcion, entidad.getCodigo()));
					model.addElement(item);
				}
			}

			// Como es una opción del desplegable, solo se tiene que ver para adm de entidad
			// y en entorno de desarrollo
			if (activeRole == TypeRoleAcceso.ADMIN_ENT
					&& UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
				item = new DefaultMenuItem(UtilJSF.getLiteral("cabecera.opciones.migracion"));
				item.setUrl(UtilJSF.getUrlArbolDefinicionVersion("migracion/viewMigracion"));
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
			entidad = entidadService.loadEntidad(entidad.getCodigo());

			// Cambia entidad
			for (final Entidad e : listaEntidades) {
				if (e.getCodigo().longValue() == entidad.getCodigo()) {
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
				logo = Constantes.DESCARGA_FICHEROS_URL + "?id=" + entidad.getLogoGestor().getCodigo();
			} else {
				logo = url + Constantes.ENTIDAD_NO_LOGO;
			}
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

	/**
	 * @return the idiomas
	 */
	public List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas the idiomas to set
	 */
	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * @return the growlLife
	 */
	public Integer getGrowlLife() {
		return growlLife;
	}

	/**
	 * @param growlLife the growlLife to set
	 */
	public void setGrowlLife(final Integer growlLife) {
		this.growlLife = growlLife;
	}

	/**
	 * @return the growlSticky
	 */
	public Boolean getGrowlSticky() {
		return growlSticky;
	}

	/**
	 * @param growlSticky the growlSticky to set
	 */
	public void setGrowlSticky(final Boolean growlSticky) {
		this.growlSticky = growlSticky;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(final SystemService systemService) {
		this.systemService = systemService;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
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
		this.paginacion = paginacion;
	}

	public void loggerErrorFront(Object tipo, Object mensaje, Object traza, Exception exception) {
		if (tipo != null) {
			UtilJSF.loggearErrorFront("Error en el front. " + tipo + " " + mensaje, exception);
		}
	}
}
