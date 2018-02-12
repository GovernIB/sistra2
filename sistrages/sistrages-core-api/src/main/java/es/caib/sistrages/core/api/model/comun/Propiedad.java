package es.caib.sistrages.core.api.model.comun;

/**
 *
 * Clase propiedad básica de codigo por valor.
 *
 * @author Indra
 *
 */
public final class Propiedad {

	/**
	 * Código.
	 */
	private String codigo;

	/**
	 * Valor
	 */
	private String valor;

	/**
	 * Descripcion.
	 */
	private String descripcion;

	/**
	 * Orden.
	 */
	private Integer orden;

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
