package es.caib.sistrages.core.api.model;

/**
 * Traduccion.
 *
 * @author indra
 *
 */
public class Traduccion {

	/**
	 * id.
	 */
	private Long id;

	/**
	 * idTraduccion.
	 */
	private Long idTraduccion;

	/**
	 * idioma.
	 */
	private String idioma;

	/**
	 * literal.
	 */
	private String literal;

	/** Constructor vacio. **/
	public Traduccion() {
		id = null;
		idTraduccion = null;
		idioma = null;
		literal = null;
	}

	/**
	 * Constructor.
	 *
	 * @param iIdioma
	 *            ca/es/en
	 * @param iLiteral
	 *            literal
	 */
	public Traduccion(final String iIdioma, final String iLiteral) {
		this.idioma = iIdioma;
		this.literal = iLiteral;
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de idioma.
	 *
	 * @return el valor de idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Establece el valor de idioma.
	 *
	 * @param idioma
	 *            el nuevo valor de idioma
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Obtiene el valor de literal.
	 *
	 * @return el valor de literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Establece el valor de literal.
	 *
	 * @param literal
	 *            el nuevo valor de literal
	 */
	public void setLiteral(final String literal) {
		this.literal = literal;
	}

	/**
	 * Obtiene el valor de idTraduccion.
	 *
	 * @return el valor de idTraduccion
	 */
	public Long getIdTraduccion() {
		return idTraduccion;
	}

	/**
	 * Establece el valor de idTraduccion.
	 *
	 * @param idTraduccion
	 *            el nuevo valor de idTraduccion
	 */
	public void setIdTraduccion(final Long idTraduccion) {
		this.idTraduccion = idTraduccion;
	}

}
