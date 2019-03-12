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

	/** Bloquear. **/
	private boolean porDefecto;

	/** Bloquear. **/
	private boolean bloquear;

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
	 * @return the bloquear
	 */
	public boolean isBloquear() {
		return bloquear;
	}

	/**
	 * @param bloquear
	 *            the bloquear to set
	 */
	public void setBloquear(final boolean bloquear) {
		this.bloquear = bloquear;
	}

	/**
	 * @return the porDefecto
	 */
	public boolean isPorDefecto() {
		return porDefecto;
	}

	/**
	 * @param porDefecto
	 *            the porDefecto to set
	 */
	public void setPorDefecto(final boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

}
