package es.caib.sistrages.core.api.model;

/**
 *
 * Formateador Formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FormateadorFormulario extends ModelApi {

	/** Id. */
	private Long id;

	/**
	 * classname.
	 */
	private String classname;

	/**
	 * descripcion.
	 */
	private String descripcion;

	/**
	 * Crea una nueva instancia de Formateador Formulario.
	 */
	public FormateadorFormulario() {
		super();
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

}
