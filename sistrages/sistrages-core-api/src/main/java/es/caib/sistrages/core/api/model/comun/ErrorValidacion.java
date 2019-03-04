package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.ModelApi;

/**
 * La clase ErrorValidacion.
 */
public class ErrorValidacion extends ModelApi {

	public ErrorValidacion() {
		super();
	}

	public ErrorValidacion(final String elemento, final String descripcion) {
		super();
		this.elemento = elemento;
		this.descripcion = descripcion;
	}

	/**
	 * elemento.
	 */
	private String elemento;

	/**
	 * descripcion.
	 */
	private String descripcion;

	/**
	 * Obtiene el valor de elemento.
	 *
	 * @return el valor de elemento
	 */
	public String getElemento() {
		return elemento;
	}

	/**
	 * Establece el valor de elemento.
	 *
	 * @param elemento
	 *            el nuevo valor de elemento
	 */
	public void setElemento(final String elemento) {
		this.elemento = elemento;
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
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}
}
