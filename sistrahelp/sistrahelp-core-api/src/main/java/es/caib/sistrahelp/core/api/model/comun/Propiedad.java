package es.caib.sistrahelp.core.api.model.comun;

import es.caib.sistrahelp.core.api.model.ModelApi;

/**
 *
 * Clase propiedad básica de codigo por valor.
 *
 * @author Indra
 *
 */

public final class Propiedad extends ModelApi {

	/**
	 * Código.
	 */
	private String codigo;

	/**
	 * Valor
	 */
	private String valor;

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

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "Propietat. ");
           texto.append(tabulacion +"\t Codi:" + codigo + "\n");
           texto.append(tabulacion +"\t Valor:" + valor + "\n");
           texto.append(tabulacion +"\t Ordre:" + orden + "\n");
           return texto.toString();
     }

}
