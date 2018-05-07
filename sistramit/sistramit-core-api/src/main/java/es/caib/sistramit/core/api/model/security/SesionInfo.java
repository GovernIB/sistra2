package es.caib.sistramit.core.api.model.security;

import java.io.Serializable;

/**
 * Informacion de la sesion web.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class SesionInfo implements Serializable {

	/**
	 * User agent normalizado como una serie de propiedad/valor.
	 */
	private String userAgent;

	/**
	 * Idioma.
	 */
	private String idioma;

	/**
	 * Método de acceso a userAgent.
	 *
	 * @return userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * Método para establecer userAgent.
	 *
	 * @param pUserAgent
	 *            userAgent a establecer
	 */
	public void setUserAgent(final String pUserAgent) {
		userAgent = pUserAgent;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param pIdioma
	 *            idioma a establecer
	 */
	public void setIdioma(final String pIdioma) {
		idioma = pIdioma;
	}

}
