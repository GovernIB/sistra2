package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Valor elemento de una lista de elementos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValorElemento implements Serializable {

	/**
	 * Descripción valor indexado.
	 */
	private List<ValorCampo> elemento;

	/**
	 * Obtiene elemento.
	 * 
	 * @return elemento
	 */
	public List<ValorCampo> getElemento() {
		return elemento;
	}

	/**
	 * Establece elemento.
	 * 
	 * @param elemento
	 *                     elemento
	 */
	public void setElemento(final List<ValorCampo> elemento) {
		this.elemento = elemento;
	}

	/**
	 * Genera duplicado.
	 * 
	 * @return ValorElemento
	 */
	public ValorElemento duplicar() {
		ValorElemento res = null;
		if (elemento != null) {
			final List<ValorCampo> e = new ArrayList<ValorCampo>();
			for (final ValorCampo vc : elemento) {
				e.add(vc.duplicar());
			}
			res = new ValorElemento();
			res.setElemento(e);
		}
		return res;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elemento == null) ? 0 : elemento.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		final ValorElemento other = (ValorElemento) obj;

		if (elemento == null && other.elemento != null) {
			return false;
		}

		if (elemento != null && other.elemento == null) {
			return false;
		}

		if (elemento == null && other.elemento == null) {
			return true;
		}

		if (elemento.size() != other.elemento.size()) {
			return false;
		}

		for (int i = 0; i < elemento.size(); i++) {
			if (!elemento.get(i).esValorIgual(other.getElemento().get(i))) {
				return false;
			}
		}

		return true;

	}

}
