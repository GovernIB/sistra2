package es.caib.sistra2.gte.frontend.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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
	 * Role activo.
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
	 * Contexto.
	 */
	@Inject
	private ContextService context;

	@PostConstruct
    public void init() {
		userName = context.getUsername();
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

	public void cambiarIdioma(String idioma) {

		// Cambia idioma
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
		lang = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

		// Recarga pagina principal
		UtilJSF.redirectJsfPage("/secure/test/test.xhtml");

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

}