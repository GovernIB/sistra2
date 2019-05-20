package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;

/**
 * Parámetro apertura del formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ParametroAperturaFormulario implements Serializable {

	/**
	 * Código.
	 */
	private String codigo;

	/**
	 * Valor.
	 */
	private String valor;

	/**
	 * Constructor.
	 * 
	 * @param codigo
	 *            codigo
	 * @param valor
	 *            valor
	 */
	public ParametroAperturaFormulario(final String codigo, final String valor) {
		super();
		this.codigo = codigo;
		this.valor = valor;
	}

	/**
	 * Constructor.
	 */
	public ParametroAperturaFormulario() {
		super();
	}

	/**
	 * Método de acceso a codigo.
	 *
	 * @return codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 *
	 * @param codigo
	 *            codigo a establecer
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método de acceso a valor.
	 *
	 * @return valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Método para establecer valor.
	 *
	 * @param valor
	 *            valor a establecer
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

}
