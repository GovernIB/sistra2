package es.caib.sistrages.core.api.model;

/**
 *
 * Fuente Datos.
 *
 * @author Indra
 *
 */

public class FuenteDatosCampo extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;
	/** Id. */
	private Long codigo;

	/** Id campo **/
	private String identificador;

	/** Indica si el campo forma parte de la clave primaria **/
	private boolean clavePrimaria;

	/** Orden. **/
	private Integer orden;

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codigo
	 */
	public String getIdString() {
		if (codigo == null) {
			return "";
		} else {
			return String.valueOf(codigo);
		}
	}

	/**
	 * @param icodigo
	 *            the codigo to set
	 */
	public void setIdString(final String icodigo) {
		if (icodigo == null) {
			this.codigo = null;
		} else {
			this.codigo = Long.valueOf(icodigo);
		}
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
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

}
