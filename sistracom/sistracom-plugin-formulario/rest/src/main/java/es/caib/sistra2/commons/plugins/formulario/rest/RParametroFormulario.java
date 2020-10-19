package es.caib.sistra2.commons.plugins.formulario.rest;

import java.io.Serializable;

/**
 * Parámetro formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RParametroFormulario implements Serializable {
	/**
	 * Código parámetro.
	 */
	private String codigo;

	/**
	 * Valor parámetro.
	 */
	private String valor;

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
	 * @param pCodigo
	 *                    codigo a establecer
	 */
	public void setCodigo(final String pCodigo) {
		codigo = pCodigo;
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
	 * @param pValor
	 *                   valor a establecer
	 */
	public void setValor(final String pValor) {
		valor = pValor;
	}

}
