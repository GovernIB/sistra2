package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.ContextService;
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
	 * Roles.
	 */
	private List<TypeRoleAcceso> rolesList;

	/**
	 * Role activo principal (superadmin, admin entidad o desarrollador).
	 */
	private TypeRoleAcceso activeRole;

	/**
	 * Idioma.
	 */
	private String lang;

	/**
	 * Locale.
	 */
	private Locale locale;

	/**
	 * Entidad.
	 */
	private Entidad entidad;

	/**
	 * Contexto.
	 */
	// @Autowired
	@Inject
	private ContextService context;

	private List<Entidad> listaEntidades;

	private String logo;

	private String literalTituloPantalla;

	public String getLiteralTituloPantalla() {
		return literalTituloPantalla;
	}

	public void setLiteralTituloPantalla(final String titulo) {
		this.literalTituloPantalla = titulo;
	}

	@PostConstruct
	public void init() {

		userName = context.getUsername();
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		rolesList = context.getRoles();

		listaEntidades = new ArrayList<>();
		Entidad newEntidad = new Entidad();
		newEntidad.setCodigo(Long.valueOf(1));
		newEntidad.setNombre("Govern de les Illes Balears");
		newEntidad.setLogoGestor(new Fichero(0l, "caib.png"));
		listaEntidades.add(newEntidad);
		entidad = newEntidad;

		newEntidad = new Entidad();
		newEntidad.setCodigo(Long.valueOf(2));
		newEntidad.setNombre("Entidad 1");
		newEntidad.setLogoGestor(new Fichero(0l, "caibe1.png"));
		listaEntidades.add(newEntidad);

		newEntidad = new Entidad();
		newEntidad.setCodigo(Long.valueOf(3));
		newEntidad.setNombre("Entidad 2");
		newEntidad.setLogoGestor(new Fichero(0l, "caibe2.png"));
		listaEntidades.add(newEntidad);

		selectActiveRole();

		setLiteralTituloPantalla("Mantenimiento de Test");

	}

	private void selectActiveRole() {
		if (rolesList.contains(TypeRoleAcceso.SUPER_ADMIN)) {
			activeRole = TypeRoleAcceso.SUPER_ADMIN;
			logo = Constantes.SUPER_ADMIN_LOGO;
		} else if (rolesList.contains(TypeRoleAcceso.ADMIN_ENT)) {
			activeRole = TypeRoleAcceso.ADMIN_ENT;
			logo = entidad.getLogoGestor().getNombre();

		} else if (rolesList.contains(TypeRoleAcceso.DESAR)) {
			activeRole = TypeRoleAcceso.DESAR;
			logo = entidad.getLogoGestor().getNombre();
		}
	}

	public void cambiarIdioma(final String idioma) {

		// Cambia idioma
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole, entidad.getCodigo());
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

	public ContextService getContext() {
		return context;
	}

	public void setContext(final ContextService context) {
		this.context = context;
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

	public String getChangeLang() {
		String res = null;
		if (Constantes.LANG_ES.equals(lang)) {
			res = Constantes.LANG_CA;
		} else {
			res = Constantes.LANG_ES;
		}
		return res;
	}

	private void cambiarLogo() {
		if (TypeRoleAcceso.SUPER_ADMIN.equals(activeRole)) {
			logo = Constantes.SUPER_ADMIN_LOGO;
		} else {
			logo = entidad.getLogoGestor().getNombre();

		}
	}

	public void cambiarRoleActivo(final String role) {

		// Cambia role
		activeRole = TypeRoleAcceso.fromString(role);

		cambiarLogo();

		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole, entidad.getCodigo());
	}

	public void cambiarEntidadActivo(final String activeEntidad) {

		// Cambia entidad
		for (final Entidad newEntidad : listaEntidades) {
			if (activeEntidad.equals(newEntidad.getNombre())) {
				entidad = newEntidad;
			}
		}

		cambiarLogo();

		// Recarga pagina principal
		UtilJSF.redirectJsfDefaultPageRole(activeRole, entidad.getCodigo());
	}

	public MenuModel getMenuModel() {
		final MenuModel model = new DefaultMenuModel();

		if (!TypeRoleAcceso.SUPER_ADMIN.equals(activeRole)) {
			final DefaultSubMenu entidadSubmenu = new DefaultSubMenu(entidad.getNombre());
			entidadSubmenu.setIcon("fa-li fa fa-institution");
			for (final Entidad newEntidad : listaEntidades) {
				if (!entidad.equals(newEntidad)) {
					final DefaultMenuItem item3 = new DefaultMenuItem(newEntidad.getNombre());
					item3.setCommand("#{sessionBean.cambiarEntidadActivo(\"" + newEntidad.getNombre() + "\")}");
					item3.setIcon("fa-li fa fa-institution");
					entidadSubmenu.addElement(item3);
				}
			}
			model.addElement(entidadSubmenu);
		}

		final DefaultSubMenu firstSubmenu = new DefaultSubMenu(getUserName());
		firstSubmenu.setIcon("fa-li fa fa-user-o");
		final DefaultMenuItem item = new DefaultMenuItem(UtilJSF.getLiteral(getChangeLang()));
		item.setCommand("#{sessionBean.cambiarIdioma(sessionBean.changeLang)}");
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
				item.setUrl(UtilJSF.getUrlOpcionMenuAdmOper(opcion, entidad.getCodigo()));
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
		UtilJSF.redirectJsfDefaultPageRole(activeRole, entidad.getCodigo());
	}
}