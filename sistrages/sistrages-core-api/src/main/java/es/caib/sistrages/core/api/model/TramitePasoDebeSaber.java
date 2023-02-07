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

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "TramitPasCalSaber. ");
           texto.append(tabulacion +"\t Codi:" + getCodigo() + "\n");
           texto.append(tabulacion +"\t Ordre:" + getOrden() + "\n");
           texto.append(tabulacion +"\t PasFinal:" + isPasoFinal() + "\n");
           texto.append(tabulacion +"\t IdPasoRelacio:" + getIdPasoRelacion() + "\n");
           texto.append(tabulacion +"\t IdPasoTramitacio:" + getIdPasoTramitacion() + "\n");
           if (getDescripcion() != null) {
        	   texto.append(tabulacion +"\t Descripció: \n");
        	   texto.append(getDescripcion().toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (getScriptNavegacion() != null) {
        	   texto.append(tabulacion +"\t ScriptNavegacio: \n");
        	   texto.append(getScriptNavegacion().toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (getScriptVariables() != null) {
        	   texto.append(tabulacion +"\t ScriptVariables: \n");
        	   texto.append(getScriptVariables().toString(tabulacion+"\t", idioma)+ "\n");
           }

           if (getInstruccionesIniciales() != null) {
        	   texto.append(tabulacion +"\t IinstruccionsInicials: \n");
        	   texto.append(getInstruccionesIniciales().toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (getScriptDebeSaber() != null) {
        	   texto.append(tabulacion +"\t ScriptDebeSaber: \n");
        	   texto.append(getScriptDebeSaber().toString(tabulacion+"\t", idioma)+ "\n");
           }
           return texto.toString();
     }

}
