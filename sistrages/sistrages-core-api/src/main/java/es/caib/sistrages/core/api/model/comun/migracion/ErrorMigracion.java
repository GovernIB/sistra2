package es.caib.sistrages.core.api.model.comun.migracion;

import es.caib.sistrages.core.api.model.ModelApi;

/**
 * La clase ErrorValidacion.
 */
public class ErrorMigracion extends ModelApi {

	private static final long serialVersionUID = 1L;

	/**
	 * elemento.
	 */
	private String elemento;

	/**
	 * descripcion.
	 */
	private String descripcion;

	/**
	 * tipo.
	 */
	private TypeErrorMigracion tipo;

	public String getElemento() {
		return elemento;
	}

	public void setElemento(final String elemento) {
		this.elemento = elemento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public TypeErrorMigracion getTipo() {
		return tipo;
	}

	public void setTipo(final TypeErrorMigracion tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param elemento
	 * @param descripcion
	 * @param tipo
	 */
	public ErrorMigracion(final String elemento, final String descripcion, final TypeErrorMigracion tipo) {
		super();
		this.elemento = elemento;
		this.descripcion = descripcion;
		this.tipo = tipo;
	}

	public ErrorMigracion(final String elemento, final String descripcion) {
		super();
		this.elemento = elemento;
		this.descripcion = descripcion;
	}

}
