package es.caib.sistrages.core.api.model;

/**
 * Script.
 *
 * @author Indra
 *
 */
public class Script {

	/** Código. **/
	private Long id;

	/** Script. **/
	private String contenido;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * @param contenido
	 *            the contenido to set
	 */
	public void setContenido(final String contenido) {
		this.contenido = contenido;
	}

}
