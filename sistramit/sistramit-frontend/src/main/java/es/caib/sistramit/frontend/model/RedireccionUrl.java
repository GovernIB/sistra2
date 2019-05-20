package es.caib.sistramit.frontend.model;

/**
 *
 * Modeliza una respuesta JSON para redirigir a url.
 *
 */
public final class RedireccionUrl {

	/**
	 * Url.
	 */
	private String url;

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
	 * @param url
	 *            url a establecer
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

}
