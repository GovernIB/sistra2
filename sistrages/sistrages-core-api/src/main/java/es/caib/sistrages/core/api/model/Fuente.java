package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Fuente Datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Fuente extends ModelApi {

	/** Id. */
	private Long id;

	/** Codigo. */
	private String codigo;

	/** Descripcion. */
	private String descripcion;

	/** Campos. **/
	private List<FuenteCampo> campos;

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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the campos
	 */
	public List<FuenteCampo> getCampos() {
		return campos;
	}

	/**
	 * @param campos
	 *            the campos to set
	 */
	public void setCampos(final List<FuenteCampo> campos) {
		this.campos = campos;
	}
}
