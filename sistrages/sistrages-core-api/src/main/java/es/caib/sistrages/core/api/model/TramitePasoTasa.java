package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Tramite Paso Tasa.
 *
 * @author Indra
 *
 */

public class TramitePasoTasa extends TramitePaso {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Tasas. **/
	private List<Tasa> tasas;

	/**
	 * @return the tasas
	 */
	public List<Tasa> getTasas() {
		return tasas;
	}

	/**
	 * @param tasas
	 *            the tasas to set
	 */
	public void setTasas(final List<Tasa> tasas) {
		this.tasas = tasas;
	}

	/**
	 * Método que fuerza a reordenar la lista de tasas.
	 */
	public void reordenar() {
		int ordenTasa = 1;
		if (this.tasas != null) {
			for (final Tasa tasa : this.tasas) {
				tasa.setOrden(ordenTasa);
				ordenTasa++;
			}
		}
	}
}
