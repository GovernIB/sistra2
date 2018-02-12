package es.caib.sistrages.core.api.model;

/**
 *
 * Propiedad de configuracion.
 *
 * @author Indra
 *
 */
public class PropiedadConfiguracion {

	/**
	 * Codigo.
	 */
	private String codigo;

	/**
	 * Valor.
	 */
	private String valor;

	/**
	 * Descripcion.
	 */
	private String descripcion;

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
