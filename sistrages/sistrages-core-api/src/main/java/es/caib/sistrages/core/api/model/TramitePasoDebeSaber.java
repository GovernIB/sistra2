package es.caib.sistrages.core.api.model;

/**
 *
 * Tramite Paso Debe Saber.
 *
 * @author Indra
 *
 */

public class TramitePasoDebeSaber extends TramitePaso {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Instrucciones iniciales. **/
	private Literal instruccionesIniciales;

	/** Script debe saber **/
	private Script scriptDebeSaber;

	/**
	 * @return the instruccionesInciales
	 */
	public Literal getInstruccionesIniciales() {
		return instruccionesIniciales;
	}

	/**
	 * @param instruccionesInciales the instruccionesInciales to set
	 */
	public void setInstruccionesIniciales(final Literal instruccionesIniciales) {
		this.instruccionesIniciales = instruccionesIniciales;
	}

	/**
	 * @return the scriptDebeSaber
	 */
	public final Script getScriptDebeSaber() {
		return scriptDebeSaber;
	}

	/**
	 * @param scriptDebeSaber the scriptDebeSaber to set
	 */
	public final void setScriptDebeSaber(Script scriptDebeSaber) {
		this.scriptDebeSaber = scriptDebeSaber;
	}

}
