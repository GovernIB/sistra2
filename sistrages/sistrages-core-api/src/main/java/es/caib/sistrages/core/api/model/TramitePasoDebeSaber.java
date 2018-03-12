package es.caib.sistrages.core.api.model;

/**
 *
 * Tramite Paso Debe Saber.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TramitePasoDebeSaber extends TramitePaso {

	/** Instrucciones iniciales. **/
	private Traducciones instruccionesIniciales;

	/**
	 * @return the instruccionesInciales
	 */
	public Traducciones getInstruccionesIniciales() {
		return instruccionesIniciales;
	}

	/**
	 * @param instruccionesInciales
	 *            the instruccionesInciales to set
	 */
	public void setInstruccionesIniciales(final Traducciones instruccionesIniciales) {
		this.instruccionesIniciales = instruccionesIniciales;
	}
}
