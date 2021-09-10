package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeFichero;

/**
 * La clase PlantillaEntidad.
 */

public class PlantillaEntidad extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** codigo. */
	private Long codigo;

	/** Formateador. **/
	private Entidad entidad;

	/** Tipo **/
	private TypeFichero tipo;

	/** Idioma. **/
	private String idioma;

	/** Fichero. **/
	private Fichero fichero;

	/**
	 * Crea una nueva instancia de PlantillaFormateador.
	 */
	public PlantillaEntidad() {
		super();
	}

	/**
	 * Crea una nueva instancia de PlantillaFormateador.
	 *
	 * @param codigo
	 *            codigo
	 */
	public PlantillaEntidad(final Long codigo) {
		super();
		setCodigo(codigo);
	}

	/**
	 * Crea una nueva instancia de PlantillaFormateador
	 *
	 * @param idioma2
	 */
	public PlantillaEntidad(final String iIdioma) {
		super();
		setIdioma(iIdioma);
	}

	/**
	 * Crea una nueva instancia de PlantillaFormateador
	 *
	 * @param idioma2
	 */
	public PlantillaEntidad(final String iIdioma, final TypeFichero iTipo) {
		super();
		setIdioma(iIdioma);
		setTipo(iTipo);
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
	 * @return the entidad
	 */
	public Entidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
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
	 * @return the tipo
	 */
	public TypeFichero getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TypeFichero tipo) {
		this.tipo = tipo;
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
