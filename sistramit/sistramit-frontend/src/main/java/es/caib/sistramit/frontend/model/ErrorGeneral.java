package es.caib.sistramit.frontend.model;

/**
 * Error a mostrar en la pagina general de error.
 *
 * @author Indra
 *
 */
public final class ErrorGeneral {

	/**
	 * Estilo error.
	 */
	private String estilo;

	/**
	 * Idioma.
	 */
	private String idioma;

	/**
	 * Mensaje usuario.
	 */
	private MensajeUsuario mensaje;

	/**
	 * Url a seguir tras el mensaje.
	 */
	private String url;

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
	 *                    idioma a establecer
	 */
	public void setIdioma(final String pIdioma) {
		idioma = pIdioma;
	}

	/**
	 * Método de acceso a mensaje.
	 *
	 * @return mensaje
	 */
	public MensajeUsuario getMensaje() {
		return mensaje;
	}

	/**
	 * Método para establecer mensaje.
	 *
	 * @param pMensaje
	 *                     mensaje a establecer
	 */
	public void setMensaje(final MensajeUsuario pMensaje) {
		mensaje = pMensaje;
	}

	/**
	 * Método de acceso a url.
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Método para establecer url.
	 *
	 * @param pUrl
	 *                 url a establecer
	 */
	public void setUrl(final String pUrl) {
		url = pUrl;
	}

	/**
	 * Método de acceso a estilo.
	 * 
	 * @return estilo
	 */
	public String getEstilo() {
		return estilo;
	}

	/**
	 * Método para establecer estilo.
	 * 
	 * @param estilo
	 *                   estilo a establecer
	 */
	public void setEstilo(final String estilo) {
		this.estilo = estilo;
	}

}
