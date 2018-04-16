package es.caib.sistrages.core.api.model;

/**
 *
 * Fuente Datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FuenteDatosCampo extends ModelApi {

	/** Id. */
	private Long id;

	/** Id campo **/
	private String codigo;

	/** Indica si el campo forma parte de la clave primaria **/
	private boolean clavePrimaria;

	/** Orden. **/
	private Integer orden;

	/** Fuente de datos. */
	private FuenteDatos fuenteDatos;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the clavePrimaria
	 */
	public boolean isClavePrimaria() {
		return clavePrimaria;
	}

	/**
	 * @param clavePrimaria
	 *            the clavePrimaria to set
	 */
	public void setClavePrimaria(final boolean clavePrimaria) {
		this.clavePrimaria = clavePrimaria;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return the fuenteDatos
	 */
	public FuenteDatos getFuenteDatos() {
		return fuenteDatos;
	}

	/**
	 * @param fuenteDatos
	 *            the fuenteDatos to set
	 */
	public void setFuenteDatos(final FuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

}
