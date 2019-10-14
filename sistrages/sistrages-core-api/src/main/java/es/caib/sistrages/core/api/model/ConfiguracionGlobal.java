package es.caib.sistrages.core.api.model;

/**
 * La clase ConfiguracionGlobal.
 */

public class ConfiguracionGlobal extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * codigo.
	 */
	private Long codigo;

	/**
	 * propiedad.
	 */
	private String propiedad;

	/**
	 * valor.
	 */
	private String valor;

	/**
	 * descripcion.
	 */
	private String descripcion;

	/**
	 * Es no modificable.
	 */
	private boolean noModificable;

	/**
	 * Constructor
	 * 
	 * @param codigo
	 * @param propiedad
	 * @param valor
	 * @param descripcion
	 * @param noModificable
	 */
	public ConfiguracionGlobal(final Long codigo, final String propiedad, final String valor, final String descripcion,
			final boolean noModificable) {
		super();
		this.codigo = codigo;
		this.propiedad = propiedad;
		this.valor = valor;
		this.descripcion = descripcion;
		this.noModificable = noModificable;
	}

	/**
	 * Constructor.
	 */
	public ConfiguracionGlobal() {
		super();
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
	 *                   el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de propiedad.
	 *
	 * @return el valor de propiedad
	 */
	public String getPropiedad() {
		return propiedad;
	}

	/**
	 * Establece el valor de propiedad.
	 *
	 * @param codigo
	 *                   el nuevo valor de propiedad
	 */
	public void setPropiedad(final String codigo) {
		this.propiedad = codigo;
	}

	/**
	 * Obtiene el valor de valor.
	 *
	 * @return el valor de valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Establece el valor de valor.
	 *
	 * @param valor
	 *                  el nuevo valor de valor
	 */
	public void setValor(final String valor) {
		this.valor = valor;
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
	 *                        el nuevo valor de descripcion
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the noModificable
	 */
	public boolean isNoModificable() {
		return noModificable;
	}

	/**
	 * @param noModificable
	 *                          the noModificable to set
	 */
	public void setNoModificable(final boolean noModificable) {
		this.noModificable = noModificable;
	}

}
