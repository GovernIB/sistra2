package es.caib.sistrages.core.api.model;

/**
 * Traduccion.
 *
 * @author indra
 *
 */
public class Traduccion {

	/** Idioma. **/
	private String idioma;

	/** Texto. **/
	private String texto;

	/**
	 * Constructor.
	 *
	 * @param iIdioma
	 *            ca/es/en
	 * @param iTexto
	 *            texto
	 */
	public Traduccion(final String iIdioma, final String iTexto) {
		this.idioma = iIdioma;
		this.texto = iTexto;
	}

	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma
	 *            the idioma to set
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto
	 *            the texto to set
	 */
	public void setTexto(final String texto) {
		this.texto = texto;
	}

}
