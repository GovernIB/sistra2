package es.caib.sistramit.frontend.model;

/**
 * Configuración asistente.
 *
 * @author Indra
 *
 */
public final class AsistenteConfig {

	/** Versión aplicación. */
	private String version;

	/** Url asistente. */
	private String url;

	/** Idioma. */
	private String idioma;

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
	public void setUrl(String url) {
		this.url = url;
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
	 * @param idioma
	 *            idioma a establecer
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a version.
	 * 
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Método para establecer version.
	 * 
	 * @param version
	 *            version a establecer
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
