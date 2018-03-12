package es.caib.sistrages.core.api.model;

/**
 *
 * Fuente Datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FuenteCampo extends ModelApi {

	/** Id. */
	private Long id;

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
	 * @return the idString
	 */
	public String getIdString() {
		if (id == null) {
			return null;
		} else {
			return id.toString();
		}
	}

	/**
	 * @param idString
	 *            the id to set
	 */
	public void setIdString(final String idString) {
		if (id == null) {
			this.id = null;
		} else {
			this.id = Long.valueOf(idString);
		}
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
