package es.caib.sistrages.core.api.model;

/**
 * La clase PlantillaIdiomaFormulario.
 */

public class PlantillaIdiomaFormulario extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** codigo. */
	private Long codigo;

	/**
	 * fichero.
	 */
	private Fichero fichero;

	/**
	 * idioma.
	 */
	private String idioma;

	/**
	 * Crea una nueva instancia de PlantillaIdiomaFormulario.
	 */
	public PlantillaIdiomaFormulario() {
		super();
	}

	/**
	 * Crea una nueva instancia de PlantillaIdiomaFormulario.
	 *
	 * @param pIdioma
	 *            idioma
	 */
	public PlantillaIdiomaFormulario(final String pIdioma) {
		super();
		setIdioma(pIdioma);
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
	 * Obtiene el valor de fichero.
	 *
	 * @return el valor de fichero
	 */
	public Fichero getFichero() {
		return fichero;
	}

	/**
	 * Establece el valor de fichero.
	 *
	 * @param fichero
	 *            el nuevo valor de fichero
	 */
	public void setFichero(final Fichero fichero) {
		this.fichero = fichero;
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

}
