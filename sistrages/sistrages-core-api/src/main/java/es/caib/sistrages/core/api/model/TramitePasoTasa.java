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

	/** Permite subsanación paso registrar. **/
	private boolean permiteSubsanar;

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
	 * @return the permiteSubsanar
	 */
	public boolean isPermiteSubsanar() {
		return permiteSubsanar;
	}

	/**
	 * @param permiteSubsanar
	 *            the permiteSubsanar to set
	 */
	public void setPermiteSubsanar(final boolean permiteSubsanar) {
		this.permiteSubsanar = permiteSubsanar;
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
