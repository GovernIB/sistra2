package es.caib.sistramit.frontend.model;

import java.util.List;

import es.caib.sistramit.core.api.model.flujo.AvisoPlataforma;

/**
 * Información para la página de login.
 *
 * @author Indra
 *
 */
public final class LoginFormInfo {

	/**
	 * Idioma.
	 */
	private String idioma;

	/**
	 * Titulo trámite.
	 */
	private String tituloTramite;

	/**
	 * Url logout.
	 */
	private String urlLogout;

	/**
	 * Indica si se realiza login anonimo.
	 */
	private boolean loginAnonimo;

	/**
	 * Indica si se realiza login autenticado.
	 */
	private boolean loginAutenticado;

	/**
	 * Avisos plataforma.
	 */
	private List<AvisoPlataforma> avisos;

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	public String getUrlLogout() {
		return urlLogout;
	}

	public void setUrlLogout(final String urlLogout) {
		this.urlLogout = urlLogout;
	}

	public List<AvisoPlataforma> getAvisos() {
		return avisos;
	}

	public void setAvisos(final List<AvisoPlataforma> avisos) {
		this.avisos = avisos;
	}

	public boolean isLoginAnonimo() {
		return loginAnonimo;
	}

	public void setLoginAnonimo(final boolean loginAnonimo) {
		this.loginAnonimo = loginAnonimo;
	}

	public boolean isLoginAutenticado() {
		return loginAutenticado;
	}

	public void setLoginAutenticado(final boolean loginAutenticado) {
		this.loginAutenticado = loginAutenticado;
	}

	public String getTituloTramite() {
		return tituloTramite;
	}

	public void setTituloTramite(final String tituloTramite) {
		this.tituloTramite = tituloTramite;
	}

}
