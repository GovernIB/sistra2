package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Resultado de realizar búsqueda sobre selector dinámico.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoBuscadorDinamico implements Serializable {

	/**
	 * Valores posibles selector.
	 */
	private List<ValorIndexado> valores;

	/**
	 * Método de acceso a valores.
	 * 
	 * @return valores
	 */
	public List<ValorIndexado> getValores() {
		return valores;
	}

	/**
	 * Método para establecer valores.
	 * 
	 * @param valores
	 *                    valores a establecer
	 */
	public void setValores(final List<ValorIndexado> valores) {
		this.valores = valores;
	}

}
