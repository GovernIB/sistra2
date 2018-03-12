package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Tramite Paso Tasa.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TramitePasoTasa extends TramitePaso {

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

}
