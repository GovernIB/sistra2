package es.caib.sistrages.core.api.model;

/**
 * La clase Script.
 */
@SuppressWarnings("serial")
public class Script extends ModelApi {

	/** Codigo. */
	private Long codigo;

	/** Contenido. */
	private String contenido;

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

}
