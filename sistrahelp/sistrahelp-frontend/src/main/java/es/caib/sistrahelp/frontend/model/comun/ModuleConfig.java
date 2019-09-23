package es.caib.sistrahelp.frontend.model.comun;

/**
 * @author Indra
 *
 */
public final class ModuleConfig {

	/** Entorno. **/
	private String entorno;

	/**
	 * @return the entorno
	 */
	public String getEntorno() {
		return entorno;
	}

	/**
	 * @param entorno
	 *                    the entorno to set
	 */
	public void setEntorno(final String entorno) {
		this.entorno = entorno;
	}

	/**
	 * @return the entorno
	 */
	public String getEntornoUpper() {
		if (entorno == null) {
			return "";
		} else {
			return entorno.toUpperCase();
		}
	}

}
