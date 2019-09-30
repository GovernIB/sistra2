package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;

import es.caib.sistra2.commons.plugins.registro.api.types.TypeJustificante;

/**
 * Datos del resultado de obtener justificante registro.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ResultadoJustificante implements Serializable {

	/** Tipo justificante. */
	private TypeJustificante tipo;

	/** Contenido justificante */
	private byte[] contenido;

	/** Url acceso justificante */
	private String url;

	/**
	 * Método de acceso a contenido.
	 *
	 * @return contenido
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * Método para establecer contenido.
	 *
	 * @param contenido
	 *                      contenido a establecer
	 */
	public void setContenido(final byte[] contenido) {
		this.contenido = contenido;
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
	 *                url a establecer
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Método de acceso a tipo.
	 * 
	 * @return tipo
	 */
	public TypeJustificante getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 * 
	 * @param tipo
	 *                 tipo a establecer
	 */
	public void setTipo(final TypeJustificante tipo) {
		this.tipo = tipo;
	}

}
