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

	/** Instrucciones fin tramitación */
	private Literal instruccionesFinTramitacion;

	/** Instrucciones presentación **/
	private Literal instruccionesPresentacion;

	/** Código oficina registro **/
	private String codigoOficinaRegistro;

	/** Código libro registro **/
	private String codigoLibroRegistro;

	/** Código tipo asunto **/
	private String codigoTipoAsunto;

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
	 * @return the codigoTipoAsunto
	 */
	public String getCodigoTipoAsunto() {
		return codigoTipoAsunto;
	}

	/**
	 * @param codigoTipoAsunto the codigoTipoAsunto to set
	 */
	public void setCodigoTipoAsunto(final String codigoTipoAsunto) {
		this.codigoTipoAsunto = codigoTipoAsunto;
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
	public void setScriptAlFinalizar(Script scriptAlFinalizar) {
		this.scriptAlFinalizar = scriptAlFinalizar;
	}

}
