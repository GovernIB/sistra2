package es.caib.sistrages.core.api.model;

/**
 * Traduccion.
 *
 * @author indra
 *
 */
@SuppressWarnings("serial")
public class Traduccion extends ModelApi {

	/**
	 * id.
	 */
	private Long id;

	/**
	 * idioma.
	 */
	private String idioma;

	/**
	 * literal.
	 */
	private String literal;

	/** Constructor. **/
	public Traduccion() {
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

}
