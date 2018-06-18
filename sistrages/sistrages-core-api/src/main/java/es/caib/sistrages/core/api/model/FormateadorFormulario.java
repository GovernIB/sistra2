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
	private Long codigo;

	/** Identificador. **/
	private String identificador;

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
	 * Crea una nueva instancia de Formateador Formulario.
	 *
	 * @param pCodigo
	 *            codigo
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
	public void setCodigo(final Long id) {
		this.codigo = id;
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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
	}

}
