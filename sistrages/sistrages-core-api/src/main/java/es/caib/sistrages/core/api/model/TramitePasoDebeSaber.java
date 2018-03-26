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
	private Literal instruccionesIniciales;

	/**
	 * @return the instruccionesInciales
	 */
	public Literal getInstruccionesIniciales() {
		return instruccionesIniciales;
	}

	/**
	 * @param instruccionesInciales
	 *            the instruccionesInciales to set
	 */
	public void setInstruccionesIniciales(final Literal instruccionesIniciales) {
		this.instruccionesIniciales = instruccionesIniciales;
	}
}
