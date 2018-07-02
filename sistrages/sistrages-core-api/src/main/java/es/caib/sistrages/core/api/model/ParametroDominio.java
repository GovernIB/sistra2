package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeParametroDominio;

/**
 * La clase ParametroDominio.
 */
@SuppressWarnings("serial")
public class ParametroDominio extends ModelApi {

	/**
	 * codigo.
	 */
	private Long codigo;

	/**
	 * tipo.
	 */
	private TypeParametroDominio tipo;

	/**
	 * valor.
	 */
	private String valor;

	/**
	 * parametro.
	 */
	private String parametro;

	/**
	 * Crea una nueva instancia de ParametroDominio.
	 */
	public ParametroDominio() {
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
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de tipo.
	 *
	 * @return el valor de tipo
	 */
	public TypeParametroDominio getTipo() {
		return tipo;
	}

	/**
	 * Establece el valor de tipo.
	 *
	 * @param tipo
	 *            el nuevo valor de tipo
	 */
	public void setTipo(final TypeParametroDominio tipo) {
		this.tipo = tipo;
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
	 *            el nuevo valor de valor
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

	/**
	 * Obtiene el valor de parametro.
	 *
	 * @return el valor de parametro
	 */
	public String getParametro() {
		return parametro;
	}

	/**
	 * Establece el valor de parametro.
	 *
	 * @param parametro
	 *            el nuevo valor de parametro
	 */
	public void setParametro(final String parametro) {
		this.parametro = parametro;
	}
}
