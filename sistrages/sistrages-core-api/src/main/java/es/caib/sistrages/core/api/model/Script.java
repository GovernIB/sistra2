package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 * La clase Script.
 */

public class Script extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Contenido. */
	private String contenido;

	/** Mensajes. **/
	private List<LiteralScript> mensajes;

	/** Indica si los mensajes han sido alterados. Por defecto, a falso **/
	private Boolean mensajesAlterado = false;

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de contenido.
	 *
	 * @return el valor de contenido
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * Establece el valor de contenido.
	 *
	 * @param contenido
	 *            el nuevo valor de contenido
	 */
	public void setContenido(final String contenido) {
		this.contenido = contenido;
	}

	/**
	 * @return the mensajes
	 */
	public List<LiteralScript> getMensajes() {
		return mensajes;
	}

	/**
	 * @param mensajes
	 *            the mensajes to set
	 */
	public void setMensajes(final List<LiteralScript> mensajes) {
		this.mensajes = mensajes;
	}

	/**
	 * @return the mensajesAlterado
	 */
	public Boolean isMensajesAlterado() {
		return mensajesAlterado;
	}

	/**
	 * @param mensajesAlterado
	 *            the mensajesAlterado to set
	 */
	public void setMensajesAlterado(final Boolean mensajesAlterado) {
		this.mensajesAlterado = mensajesAlterado;
	}

}
