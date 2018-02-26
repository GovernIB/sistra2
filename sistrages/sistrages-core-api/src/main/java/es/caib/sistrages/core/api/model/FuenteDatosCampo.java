package es.caib.sistrages.core.api.model;

/**
 *
 * Fuente Datos.
 *
 * @author Indra
 *
 */
public class FuenteDatosCampo {

	/** Id. */
	private Long id;

	/** Fuente de datos. **/
	private FuenteDatos fuenteDatos;

	/** Id campo **/
	private String codigo;

	/** Indica si el campo forma parte de la clave primaria **/
	private boolean clavePrimaria;

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
}
