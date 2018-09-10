package es.caib.sistrages.core.api.model;

public class ValorParametroDominio extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	private String codigo;
	/** Valor. **/
	private String valor;

	/**
	 * Get codigo.
	 * 
	 * @return
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Set codigo.
	 * 
	 * @param codigo
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Get valor.
	 * 
	 * @return
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Set valor.w
	 * 
	 * @param valor
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

}
