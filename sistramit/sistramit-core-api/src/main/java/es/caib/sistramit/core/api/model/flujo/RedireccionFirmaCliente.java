package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 *
 * Respuesta a la acción de iniciar una firma de un documento.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RedireccionFirmaCliente implements ModelApi {

	/** Url inicio pago. */
	private String url;

	/** Indica si se abre en iframe. */
	private TypeSiNo iframe;

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
