package es.caib.sistrages.core.api.model;

/**
 * La clase PlantillaFormulario.
 */

public class PlantillaFormateador extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** codigo. */
	private Long codigo;

	/** Formateador. **/
	private FormateadorFormulario formateador;

	/** Idioma. **/
	private String idioma;

	/** Fichero. **/
	private Fichero fichero;

	/**
	 * Crea una nueva instancia de PlantillaFormateador.
	 */
	public PlantillaFormateador() {
		super();
	}

	/**
	 * Crea una nueva instancia de PlantillaFormateador.
	 *
	 * @param codigo
	 *            codigo
	 */
	public PlantillaFormateador(final Long codigo) {
		super();
		setCodigo(codigo);
	}

	/**
	 * Crea una nueva instancia de PlantillaFormateador
	 *
	 * @param idioma2
	 */
	public PlantillaFormateador(final String iIdioma) {
		super();
		setIdioma(iIdioma);
	}

	/**
	 * @return the codigo
	 */
	public final Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public final void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the formateador
	 */
	public final FormateadorFormulario getFormateador() {
		return formateador;
	}

	/**
	 * @param formateador
	 *            the formateador to set
	 */
	public final void setFormateador(final FormateadorFormulario formateador) {
		this.formateador = formateador;
	}

	/**
	 * @return the idioma
	 */
	public final String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma
	 *            the idioma to set
	 */
	public final void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the fichero
	 */
	public final Fichero getFichero() {
		return fichero;
	}

	/**
	 * @param fichero
	 *            the fichero to set
	 */
	public final void setFichero(final Fichero fichero) {
		this.fichero = fichero;
	}

	/**
	 * Obtiene el valor de hashCode.
	 *
	 * @return el valor de hashCode
	 */
	public int getHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((idioma == null) ? 0 : idioma.hashCode());
		return result;
	}

}
