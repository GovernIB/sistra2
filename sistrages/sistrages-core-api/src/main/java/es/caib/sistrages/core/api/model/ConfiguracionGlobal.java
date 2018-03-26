package es.caib.sistrages.core.api.model;

/**
 *
 * Propiedad de configuracion global.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionGlobal extends ModelApi {

	/** Id. */
	private Long id;

	/** Propiedad. **/
	private String propiedad;

	/** Valor. */
	private String valor;

	/** Descripcion. */
	private String descripcion;

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
	 * @return the propiedad
	 */
	public String getPropiedad() {
		return propiedad;
	}

	/**
	 * @param propiedad
	 *            the propiedad to set
	 */
	public void setPropiedad(final String codigo) {
		this.propiedad = codigo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(final String valor) {
		this.valor = valor;
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

}
