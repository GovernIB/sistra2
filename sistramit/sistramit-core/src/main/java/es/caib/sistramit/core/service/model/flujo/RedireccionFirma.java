package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 *
 * Respuesta a la acción de iniciar una firma.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RedireccionFirma implements Serializable {

	/** Url redireccion firma. */
	private String url;

	/** Id sesion firma. */
	private String idSesion;

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
	 * Método de acceso a idSesion.
	 *
	 * @return idSesion
	 */
	public String getIdSesion() {
		return idSesion;
	}

	/**
	 * Método para establecer idSesion.
	 *
	 * @param idSesion
	 *            idSesion a establecer
	 */
	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

}
