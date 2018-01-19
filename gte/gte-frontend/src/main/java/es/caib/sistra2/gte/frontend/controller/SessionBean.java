package es.caib.sistra2.gte.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistra2.gte.core.api.model.Entidad;
import es.caib.sistra2.gte.core.api.service.ContextService;
import es.caib.sistra2.gte.frontend.util.UtilJSF;

/**
 * Información de sesión.
 * @author Indra
 *
 */
@ManagedBean
@SessionScoped
public class SessionBean {

	/**
	 * Usuario.
	 */
	private String userName;

	/**
	 * Roles.
	 */
	private List<String> roles;

	/**
	 * Role activo principal (superadmin, admin entidad o desarrollador).
	 */
	private String activeRole;

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
	@Inject
	private ContextService context;

	private List<Entidad> listaEntidades;

	private String logo;

	private ResourceBundle bundle;

	private String titulo;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@PostConstruct
    public void init() {
		userName = context.getUsername();
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		roles = new ArrayList<>();
		roles.add("SuperAdministrador");
		roles.add("Administrador Entidad");
		roles.add("Desarrollador");

		activeRole = "SuperAdministrador";

		logo = "super-adminb.png";

		listaEntidades = new ArrayList<>();
		Entidad newEntidad = new Entidad();
		newEntidad.setCodigo(Long.valueOf(1));
		newEntidad.setDescripcion("Govern de les Illes Balears");
		listaEntidades.add(newEntidad);
		entidad = newEntidad;

		newEntidad = new Entidad();
		newEntidad.setCodigo(Long.valueOf(2));
		newEntidad.setDescripcion("Entidad 1");
		listaEntidades.add(newEntidad);

		newEntidad = new Entidad();
		newEntidad.setCodigo(Long.valueOf(3));
		newEntidad.setDescripcion("Entidad 2");
		listaEntidades.add(newEntidad);

		setTitulo("Mantenimiento de Test");

    }

	public void cambiarIdioma(String idioma) {

		// Cambia idioma
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		// Recarga pagina principal
		UtilJSF.redirectJsfPage("/secure/test/test.xhtml");
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String user) {
		this.userName = user;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getActiveRole() {
		return activeRole;
	}

	public void setActiveRole(String activeRole) {
		this.activeRole = activeRole;
	}


	public ContextService getContext() {
		return context;
	}

	public void setContext(ContextService context) {
		this.context = context;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}


	public List<Entidad> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<Entidad> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public String getChangeLang() {
		String res = null;
		if ("es".equals(lang)) {
			res = "ca";
		} else {
			res = "es";
		}
		return res;
	}

	public void cambiarLogo() {
		if ("SuperAdministrador".equals(activeRole)) {
			logo = "super-adminb.png";
		}
		else {
			if (entidad.getCodigo()==Long.valueOf(1)) {
				logo = "caib.png";
			} else if (entidad.getCodigo()==Long.valueOf(2)) {
				logo = "caibe1.png";
			} else if (entidad.getCodigo()==Long.valueOf(3)) {
				logo = "caibe2.png";
			}

		}
	}

	public void cambiarRoleActivo(String role) {

		// Cambia role
		activeRole = role;

		cambiarLogo();

		// Recarga pagina principal
		UtilJSF.redirectJsfPage("/secure/test/test.xhtml");
	}

	public void cambiarEntidadActivo(String activeEntidad) {

		// Cambia entidad
		for (Entidad newEntidad : listaEntidades) {
			if (activeEntidad.equals(newEntidad.getDescripcion())) {
				entidad = newEntidad;
			}
		}

		cambiarLogo();

		// Recarga pagina principal
		UtilJSF.redirectJsfPage("/secure/test/test.xhtml");
	}

	public MenuModel getMenuModel() {
		MenuModel model = new DefaultMenuModel();

		if (!"SuperAdministrador".equals(activeRole)) {
			DefaultSubMenu entidadSubmenu = new DefaultSubMenu(entidad.getDescripcion());
			entidadSubmenu.setIcon("ui-icon-home");
			for (Entidad newEntidad : listaEntidades) {
				if (!entidad.equals(newEntidad)) {
					DefaultMenuItem item3 = new DefaultMenuItem(newEntidad.getDescripcion());
					item3.setCommand("#{sessionBean.cambiarEntidadActivo(\""+newEntidad.getDescripcion()+"\")}");
					item3.setIcon("ui-icon-home");
					entidadSubmenu.addElement(item3);
				}
			}
			model.addElement(entidadSubmenu);
		}

		DefaultSubMenu firstSubmenu = new DefaultSubMenu(getUserName());
		firstSubmenu.setIcon("ui-icon-person");

		DefaultMenuItem item = new DefaultMenuItem(bundle.getString(getChangeLang()));
		item.setCommand("#{sessionBean.cambiarIdioma(sessionBean.changeLang)}");
		item.setIcon("ui-icon-flag");
		firstSubmenu.addElement(item);

		model.addElement(firstSubmenu);

		DefaultSubMenu secondSubmenu = new DefaultSubMenu(activeRole);
		secondSubmenu.setIcon("ui-icon-contact");
		for (String role : roles) {
			if (!activeRole.equals(role)) {
				DefaultMenuItem item2 = new DefaultMenuItem(role);
				item2.setCommand("#{sessionBean.cambiarRoleActivo(\""+role+"\")}");
				item2.setIcon("ui-icon-contact");
				secondSubmenu.addElement(item2);
			}
		}
		model.addElement(secondSubmenu);
		model.generateUniqueIds();
		return model;
	}

	public MenuModel getMenuModelOpciones() {
		MenuModel model = new DefaultMenuModel();

		if ("SuperAdministrador".equals(activeRole)) {
			DefaultMenuItem item = new DefaultMenuItem(bundle.getString("cabecera.opciones.entidades"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.propsglobales"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.pluginsglobales"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.dominiosglobales"));
			model.addElement(item);
		} else {
			DefaultMenuItem item = new DefaultMenuItem(bundle.getString("cabecera.opciones.tramites"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.configuracionentidad"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.pluginsentidad"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.gestoresformularios"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.dominiosentidad"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.fuentesdatos"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.mensajes"));
			model.addElement(item);

			item = new DefaultMenuItem(bundle.getString("cabecera.opciones.rolesypermisos"));
			model.addElement(item);
		}

		model.generateUniqueIds();
		return model;
	}

}