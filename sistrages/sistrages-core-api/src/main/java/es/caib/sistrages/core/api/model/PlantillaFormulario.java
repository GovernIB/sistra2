package es.caib.sistrages.core.api.model;

/**
 * La clase PlantillaFormulario.
 */
@SuppressWarnings("serial")
public class PlantillaFormulario extends ModelApi {

	/**
	 * codigo.
	 */
	private Long codigo;

	/**
	 * identificador del formateador de formulario.
	 */
	private Long idFormateadorFormulario;

	/**
	 * descripcion.
	 */
	private String descripcion;

	/**
	 * indica si es el valor por defecto.
	 */
	private boolean porDefecto;

	/**
	 * Crea una nueva instancia de PlantillaFormulario.
	 */
	public PlantillaFormulario() {
		super();
	}

	/**
	 * Crea una nueva instancia de PlantillaFormulario.
	 *
	 * @param codigo
	 *            codigo
	 */
	public PlantillaFormulario(final Long codigo) {
		super();
		setCodigo(codigo);
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
	 * Obtiene el valor de idFormateadorFormulario.
	 *
	 * @return el valor de idFormateadorFormulario
	 */
	public Long getIdFormateadorFormulario() {
		return idFormateadorFormulario;
	}

	/**
	 * Establece el valor de idFormateadorFormulario.
	 *
	 * @param formateadorFormulario
	 *            el nuevo valor de idFormateadorFormulario
	 */
	public void setIdFormateadorFormulario(final Long formateadorFormulario) {
		this.idFormateadorFormulario = formateadorFormulario;
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
	 * Verifica si es por defecto.
	 *
	 * @return true, si es por defecto
	 */
	public boolean isPorDefecto() {
		return porDefecto;
	}

	/**
	 * Establece el valor de porDefecto.
	 *
	 * @param porDefecto
	 *            el nuevo valor de porDefecto
	 */
	public void setPorDefecto(final boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	/**
	 * Obtiene el valor de hashCode.
	 *
	 * @return el valor de hashCode
	 */
	public int getHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((idFormateadorFormulario == null) ? 0 : idFormateadorFormulario.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + (porDefecto ? 1231 : 1237);
		return result;
	}

}
