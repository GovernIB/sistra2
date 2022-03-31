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

	/** Timeout ajax con carácter general (0 sin timeout). */
	private String timeoutAjaxGeneral = "0";

	/** Timeout ajax para registro (0 sin timeout). */
	private String timeoutAjaxRegistro = "0";

	/** Iframe firma width. */
	private String iframeFirmaWidth;

	/** Iframe firma height. */
	private String iframeFirmaHeight;

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
	 *                url a establecer
	 */
	public void setUrl(final String url) {
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
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
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
	 *                    version a establecer
	 */
	public void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * Método de acceso a timeoutAjaxGeneral.
	 *
	 * @return timeoutAjaxGeneral
	 */
	public String getTimeoutAjaxGeneral() {
		return timeoutAjaxGeneral;
	}

	/**
	 * Método para establecer timeoutAjaxGeneral.
	 *
	 * @param timeoutAjaxGeneral
	 *                               timeoutAjaxGeneral a establecer
	 */
	public void setTimeoutAjaxGeneral(final String timeoutAjaxGeneral) {
		this.timeoutAjaxGeneral = timeoutAjaxGeneral;
	}

	/**
	 * Método de acceso a timeoutAjaxRegistro.
	 *
	 * @return timeoutAjaxRegistro
	 */
	public String getTimeoutAjaxRegistro() {
		return timeoutAjaxRegistro;
	}

	/**
	 * Método para establecer timeoutAjaxRegistro.
	 *
	 * @param timeoutAjaxRegistro
	 *                                timeoutAjaxRegistro a establecer
	 */
	public void setTimeoutAjaxRegistro(final String timeoutAjaxRegistro) {
		this.timeoutAjaxRegistro = timeoutAjaxRegistro;
	}

	/**
	 * Método de acceso a iframeFirmaWidth.
	 *
	 * @return iframeFirmaWidth
	 */
	public String getIframeFirmaWidth() {
		return iframeFirmaWidth;
	}

	/**
	 * Método para establecer iframeFirmaWidth.
	 *
	 * @param iframeFirmaWidth
	 *                             iframeFirmaWidth a establecer
	 */
	public void setIframeFirmaWidth(final String iframeFirmaWidth) {
		this.iframeFirmaWidth = iframeFirmaWidth;
	}

	/**
	 * Método de acceso a iframeFirmaHeight.
	 *
	 * @return iframeFirmaHeight
	 */
	public String getIframeFirmaHeight() {
		return iframeFirmaHeight;
	}

	/**
	 * Método para establecer iframeFirmaHeight.
	 *
	 * @param iframeFirmaHeight
	 *                              iframeFirmaHeight a establecer
	 */
	public void setIframeFirmaHeight(final String iframeFirmaHeight) {
		this.iframeFirmaHeight = iframeFirmaHeight;
	}

}
