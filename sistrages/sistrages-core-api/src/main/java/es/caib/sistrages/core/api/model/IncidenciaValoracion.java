package es.caib.sistrages.core.api.model;

/**
 * La clase Valoracion.
 */

public class IncidenciaValoracion extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Código interno */
	private Long codigo;

	/** Entidad */
	private Entidad entidad;

	/** Código DIR3 */
	private String identificador;

	/** Descripcion */
	private Literal descripcion;

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
	 * @return the entidad
	 */
	public final Entidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public final void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the identificador
	 */
	public final String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public final void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public final Literal getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public final void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

}
