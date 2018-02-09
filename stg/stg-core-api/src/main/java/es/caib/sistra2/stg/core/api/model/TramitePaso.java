package es.caib.sistra2.stg.core.api.model;

/**
 *
 * Area.
 *
 * @author Indra
 *
 */
public class TramitePaso {

	/**
	 * Id.
	 */
	private Long id;

	/**
	 * Codigo.
	 */
	private String codigo;

	/**
	 * Descripcion.
	 */
	private String descripcion;

	/** orden. **/
	private int orden;

	/**
	 * Crea una nueva instancia de TramitePaso.
	 */
	public TramitePaso() {
		super();
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de orden.
	 *
	 * @return el valor de orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * Establece el valor de orden.
	 *
	 * @param orden
	 *            el nuevo valor de orden
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

}
