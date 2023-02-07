package es.caib.sistrages.core.api.model;

/**
 * Traduccion.
 *
 * @author indra
 *
 */

public class Traduccion extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** codigo. */
	private Long codigo;

	/** idioma. */
	private String idioma;

	/** literal. */
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

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "Traduccio. ");
           texto.append(tabulacion +"\t Codi:" + codigo + "\n");
           texto.append(tabulacion +"\t Idioma:" + this.idioma + "\n");
           texto.append(tabulacion +"\t Literal:" + literal + "\n");
           return texto.toString();
     }

	public void limpiarIds() {
		this.setCodigo(null);
	}

}
