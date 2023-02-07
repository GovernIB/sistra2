package es.caib.sistrages.core.api.model;

/**
 *
 * Tramite Paso Registrar.
 *
 * @author Indra
 *
 */

public class TramitePasoRegistrar extends TramitePaso {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Script destino registro. **/
	private Script scriptDestinoRegistro;

	/** Script representante. **/
	private Script scriptRepresentante;

	/** Script validar permitir registrar. **/
	private Script scriptValidarRegistrar;

	/** Script presentador. **/
	private Script scriptPresentador;

	/** Destino. **/
	private String destino;

	/** Script presentador. **/
	private EnvioRemoto envioRemoto;

	/** Instrucciones fin tramitación */
	private Literal instruccionesFinTramitacion;

	/** Instrucciones presentación **/
	private Literal instruccionesPresentacion;

	/** Código oficina registro **/
	private String codigoOficinaRegistro;

	/** Código libro registro **/
	private String codigoLibroRegistro;

	/** Indica si admite representación */
	private boolean admiteRepresentacion;

	/** Indica si valida representación */
	private boolean validaRepresentacion;

	/** Indica si se habilita subsanación. **/
	private boolean permiteSubsanar;

	/** Instrucciones subsanación */
	private Literal instruccionesSubsanacion;

	/** Indica si se avisa al finalizar trámite. **/
	private boolean avisoAlFinalizar;

	/** En caso de habilitar aviso, se indica mecanismo aviso **/
	private Script scriptAlFinalizar;

	/**
	 * @return the scriptDestinoRegistro
	 */
	public Script getScriptDestinoRegistro() {
		return scriptDestinoRegistro;
	}

	/**
	 * @param scriptDestinoRegistro the scriptDestinoRegistro to set
	 */
	public void setScriptDestinoRegistro(final Script scriptDestinoRegistro) {
		this.scriptDestinoRegistro = scriptDestinoRegistro;
	}

	/**
	 * @return the scriptRepresentante
	 */
	public Script getScriptRepresentante() {
		return scriptRepresentante;
	}

	/**
	 * @param scriptRepresentante the scriptRepresentante to set
	 */
	public void setScriptRepresentante(final Script scriptRepresentante) {
		this.scriptRepresentante = scriptRepresentante;
	}

	/**
	 * @return the scriptValidarRegistrar
	 */
	public Script getScriptValidarRegistrar() {
		return scriptValidarRegistrar;
	}

	/**
	 * @param scriptValidarRegistrar the scriptValidarRegistrar to set
	 */
	public void setScriptValidarRegistrar(final Script scriptValidarRegistrar) {
		this.scriptValidarRegistrar = scriptValidarRegistrar;
	}

	/**
	 * @return the scriptPresentador
	 */
	public Script getScriptPresentador() {
		return scriptPresentador;
	}

	/**
	 * @param scriptPresentador the scriptPresentador to set
	 */
	public void setScriptPresentador(final Script scriptPresentador) {
		this.scriptPresentador = scriptPresentador;
	}

	/**
	 * @return the instruccionesFinTramitacion
	 */
	public Literal getInstruccionesFinTramitacion() {
		return instruccionesFinTramitacion;
	}

	/**
	 * @param instruccionesFinTramitacion the instruccionesFinTramitacion to set
	 */
	public void setInstruccionesFinTramitacion(final Literal instruccionesFinTramitacion) {
		this.instruccionesFinTramitacion = instruccionesFinTramitacion;
	}

	/**
	 * @return the instruccionesPresentacion
	 */
	public Literal getInstruccionesPresentacion() {
		return instruccionesPresentacion;
	}

	/**
	 * @param instruccionesPresentacion the instruccionesPresentacion to set
	 */
	public void setInstruccionesPresentacion(final Literal instruccionesPresentacion) {
		this.instruccionesPresentacion = instruccionesPresentacion;
	}

	/**
	 * @return the codigoOficinaRegistro
	 */
	public String getCodigoOficinaRegistro() {
		return codigoOficinaRegistro;
	}

	/**
	 * @param codigoOficinaRegistro the codigoOficinaRegistro to set
	 */
	public void setCodigoOficinaRegistro(final String codigoOficinaRegistro) {
		this.codigoOficinaRegistro = codigoOficinaRegistro;
	}

	/**
	 * @return the codigoLibroRegistro
	 */
	public String getCodigoLibroRegistro() {
		return codigoLibroRegistro;
	}

	/**
	 * @param codigoLibroRegistro the codigoLibroRegistro to set
	 */
	public void setCodigoLibroRegistro(final String codigoLibroRegistro) {
		this.codigoLibroRegistro = codigoLibroRegistro;
	}

	/**
	 * @return the admiteRepresentacion
	 */
	public boolean isAdmiteRepresentacion() {
		return admiteRepresentacion;
	}

	/**
	 * @param admiteRepresentacion the admiteRepresentacion to set
	 */
	public void setAdmiteRepresentacion(final boolean admiteRepresentacion) {
		this.admiteRepresentacion = admiteRepresentacion;
	}

	/**
	 * @return the validaRepresentacion
	 */
	public boolean isValidaRepresentacion() {
		return validaRepresentacion;
	}

	/**
	 * @param validaRepresentacion the validaRepresentacion to set
	 */
	public void setValidaRepresentacion(final boolean validaRepresentacion) {
		this.validaRepresentacion = validaRepresentacion;
	}

	/**
	 * @return the permiteSubsanar
	 */
	public final boolean isPermiteSubsanar() {
		return permiteSubsanar;
	}

	/**
	 * @param permiteSubsanar the permiteSubsanar to set
	 */
	public final void setPermiteSubsanar(final boolean permiteSubsanar) {
		this.permiteSubsanar = permiteSubsanar;
	}

	/**
	 * @return the instruccionesSubsanacion
	 */
	public final Literal getInstruccionesSubsanacion() {
		return instruccionesSubsanacion;
	}

	/**
	 * @param instruccionesSubsanacion the instruccionesSubsanacion to set
	 */
	public final void setInstruccionesSubsanacion(final Literal instruccionesSubsanacion) {
		this.instruccionesSubsanacion = instruccionesSubsanacion;
	}

	/**
	 * @return the avisoAlFinalizar
	 */
	public final boolean isAvisoAlFinalizar() {
		return avisoAlFinalizar;
	}

	/**
	 * @param avisoAlFinalizar the avisoAlFinalizar to set
	 */
	public final void setAvisoAlFinalizar(final boolean avisoAlFinalizar) {
		this.avisoAlFinalizar = avisoAlFinalizar;
	}

	/**
	 * @return the scriptAlFinalizar
	 */
	public Script getScriptAlFinalizar() {
		return scriptAlFinalizar;
	}

	/**
	 * @param scriptAlFinalizar the scriptAlFinalizar to set
	 */
	public void setScriptAlFinalizar(final Script scriptAlFinalizar) {
		this.scriptAlFinalizar = scriptAlFinalizar;
	}

	/**
	 * @return the destino
	 */
	public final String getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public final void setDestino(String destino) {
		this.destino = destino;
	}

	/**
	 * @return the envioRemoto
	 */
	public final EnvioRemoto getEnvioRemoto() {
		return envioRemoto;
	}

	/**
	 * @param envioRemoto the envioRemoto to set
	 */
	public final void setEnvioRemoto(EnvioRemoto envioRemoto) {
		this.envioRemoto = envioRemoto;
	}

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     **/
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "TramitPasRegistrar. ");
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
           if (scriptDestinoRegistro != null) {
        	   texto.append(tabulacion +"\t ScriptDestiRegistre: \n");
        	   texto.append(scriptDestinoRegistro.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (scriptRepresentante != null) {
        	   texto.append(tabulacion +"\t ScriptRepresentant: \n");
        	   texto.append(scriptRepresentante.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (scriptValidarRegistrar != null) {
        	   texto.append(tabulacion +"\t ScriptValidarRegistrar: \n");
        	   texto.append(scriptValidarRegistrar.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (scriptPresentador != null) {
        	   texto.append(tabulacion +"\t ScriptPresentador: \n");
        	   texto.append(scriptPresentador.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (scriptAlFinalizar != null) {
        	   texto.append(tabulacion +"\t scriptAlFinalitzar: \n");
        	   texto.append(scriptAlFinalizar.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (envioRemoto != null) {
        	   texto.append(tabulacion +"\t envioRemoto: \n");
        	   texto.append(envioRemoto.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (instruccionesFinTramitacion != null) {
        	   texto.append(tabulacion +"\t InstruccionsFiTramitacio: \n");
        	   texto.append(instruccionesFinTramitacion.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (instruccionesPresentacion != null) {
        	   texto.append(tabulacion +"\t InstruccionsPresentacio: \n");
        	   texto.append(instruccionesPresentacion.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (instruccionesSubsanacion != null) {
        	   texto.append(tabulacion +"\t InstruccionsSubsanacio: \n");
        	   texto.append(instruccionesSubsanacion.toString(tabulacion+"\t", idioma)+ "\n");
           }
           texto.append(tabulacion +"\t Desti:" + destino + "\n");
           texto.append(tabulacion +"\t CodiOficinaRegistre:" + codigoOficinaRegistro + "\n");
           texto.append(tabulacion +"\t CodiLlibreRegistre:" + codigoLibroRegistro + "\n");
           texto.append(tabulacion +"\t DdmiteRepresentacio:" + admiteRepresentacion + "\n");
           texto.append(tabulacion +"\t ValidaRepresentacio:" + validaRepresentacion + "\n");
           texto.append(tabulacion +"\t PermetSubsanar:" + permiteSubsanar + "\n");
           texto.append(tabulacion +"\t AvisAlFinalitzar:" + avisoAlFinalizar + "\n");
           return texto.toString();
     }
}
