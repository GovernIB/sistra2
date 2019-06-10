package es.caib.sistrages.core.api.model;

/**
 * La clase FormateadorFormulario.
 */

public class FormateadorFormulario extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Identificador. */
	private String identificador;

	/** Classname. */
	private String classname;

	/** Descripcion. */
	private String descripcion;

	/** Genérico. **/
	private boolean generico;

	/** Desactivar personalizacion. **/
	private boolean desactivarPersonalizacion;

	/**
	 * Crea una nueva instancia de FormateadorFormulario.
	 */
	public FormateadorFormulario() {
		super();
	}

	/**
	 * Crea una nueva instancia de FormateadorFormulario.
	 *
	 * @param pCodigo
	 *            the codigo
	 */
	public FormateadorFormulario(final Long pCodigo) {
		super();
		this.codigo = pCodigo;
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
	 * Obtiene el valor de classname.
	 *
	 * @return el valor de classname
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * Establece el valor de classname.
	 *
	 * @param classname
	 *            el nuevo valor de classname
	 */
	public void setClassname(final String classname) {
		this.classname = classname;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de identificador.
	 *
	 * @return el valor de identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Establece el valor de identificador.
	 *
	 * @param codigo
	 *            el nuevo valor de identificador
	 */
	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
	}

	/**
	 * @return the desactivarPersonalizacion
	 */
	public boolean isDesactivarPersonalizacion() {
		return desactivarPersonalizacion;
	}

	/**
	 * @param desactivarPersonalizacion
	 *            the desactivarPersonalizacion to set
	 */
	public void setDesactivarPersonalizacion(final boolean desactivarPersonalizacion) {
		this.desactivarPersonalizacion = desactivarPersonalizacion;
	}

	/**
	 * @return the generico
	 */
	public boolean isGenerico() {
		return generico;
	}

	/**
	 * @param generico
	 *            the generico to set
	 */
	public void setGenerico(final boolean generico) {
		this.generico = generico;
	}

}
