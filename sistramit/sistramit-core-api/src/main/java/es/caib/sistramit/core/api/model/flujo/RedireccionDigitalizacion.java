package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 *
 * Respuesta a la acción de iniciar digitalización documento.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RedireccionDigitalizacion implements ModelApi {

	/** Id sesión digitalizacion. */
	private String idSesionDigitalizacion;

	/** Url redirección. */
	private String url;

	/** Indica si se abre en iframe. */
	private TypeSiNo iframe;

	public String getIdSesionDigitalizacion() {
		return idSesionDigitalizacion;
	}

	public void setIdSesionDigitalizacion(String idSesionDigitalizacion) {
		this.idSesionDigitalizacion = idSesionDigitalizacion;
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
	 * @param url
	 *            url a establecer
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Método de acceso a iframe.
	 *
	 * @return iframe
	 */
	public TypeSiNo getIframe() {
		return iframe;
	}

	/**
	 * Método para establecer iframe.
	 *
	 * @param iframe
	 *            iframe a establecer
	 */
	public void setIframe(TypeSiNo iframe) {
		this.iframe = iframe;
	}
}
