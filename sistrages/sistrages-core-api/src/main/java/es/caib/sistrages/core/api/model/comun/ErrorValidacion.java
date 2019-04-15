package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.types.TypeErrorValidacion;

/**
 * La clase ErrorValidacion.
 */
public class ErrorValidacion extends ModelApi {

	private static final long serialVersionUID = 1L;

	public ErrorValidacion() {
		super();
	}

	public ErrorValidacion(final String elemento, final String descripcion) {
		super();
		this.elemento = elemento;
		this.descripcion = descripcion;
	}

	public ErrorValidacion(final String elemento, final String descripcion, final TypeErrorValidacion tipo) {
		super();
		this.elemento = elemento;
		this.descripcion = descripcion;
		this.tipo = tipo;
	}

	public ErrorValidacion(final String elemento, final String descripcion, final TypeErrorValidacion tipo,
			final ModelApi item) {
		super();
		this.elemento = elemento;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.item = item;
	}

	public ErrorValidacion(final String elemento, final String descripcion, final TypeErrorValidacion tipo,
			final ModelApi item, final String params) {
		super();
		this.elemento = elemento;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.item = item;
		this.params = params;
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
	 * tipo.
	 */
	private TypeErrorValidacion tipo;

	private ModelApi item;

	private String params;

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

	public TypeErrorValidacion getTipo() {
		return tipo;
	}

	public void setTipo(final TypeErrorValidacion tipo) {
		this.tipo = tipo;
	}

	public ModelApi getItem() {
		return item;
	}

	public void setItem(final ModelApi item) {
		this.item = item;
	}

	public String getParams() {
		return params;
	}

	public void setParams(final String params) {
		this.params = params;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((elemento == null) ? 0 : elemento.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ErrorValidacion)) {
			return false;
		}
		final ErrorValidacion other = (ErrorValidacion) obj;
		if (descripcion == null) {
			if (other.descripcion != null) {
				return false;
			}
		} else if (!descripcion.equals(other.descripcion)) {
			return false;
		}
		if (elemento == null) {
			if (other.elemento != null) {
				return false;
			}
		} else if (!elemento.equals(other.elemento)) {
			return false;
		}
		if (tipo != other.tipo) {
			return false;
		}
		return true;
	}

}
