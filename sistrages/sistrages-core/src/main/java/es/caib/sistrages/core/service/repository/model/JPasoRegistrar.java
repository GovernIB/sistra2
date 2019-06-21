package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.TramitePasoRegistrar;

/**
 * JPasoRegistrar
 */
@Entity
@Table(name = "STG_PASREG")
public class JPasoRegistrar implements IModelApi {

	/** Serial Version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@Column(name = "PRG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Paso tramitacion. **/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PRG_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	/** Script destino registro */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_SCRREG")
	private JScript scriptDestinoRegistro;

	/** Script representante. */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_SCRREP")
	private JScript scriptRepresentante;

	/** Script validar registrar. **/
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_SCRVAL")
	private JScript scriptValidarRegistrar;

	/** Script presentador. **/
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_SCRPRE")
	private JScript scriptPresentador;

	/** Instrucciones fin tramitación */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_INSFIT")
	private JLiteral instruccionesFinTramitacion;

	/** Instrucciones presentación */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_INSPRE")
	private JLiteral instruccionesPresentacion;

	/** Código oficina registro */
	@Column(name = "PRG_REGOFI", length = 20)
	private String codigoOficinaRegistro;

	/** Código libro registro. **/
	@Column(name = "PRG_REGLIB", length = 20)
	private String codigoLibroRegistro;

	/** Código tipo asunto. **/
	@Column(name = "PRG_REGASU", length = 20)
	private String codigoTipoAsunto;

	/** Admite representacion. **/
	@Column(name = "PRG_REPADM", nullable = false, precision = 1, scale = 0)
	private boolean admiteRepresentacion;

	/** Valida representacion. **/
	@Column(name = "PRG_REPVAL", nullable = false, precision = 1, scale = 0)
	private boolean validaRepresentacion;

	/** Indica si se habilita subsanación. **/
	@Column(name = "PRG_SUBSAN", nullable = false, precision = 1, scale = 0)
	private boolean permiteSubsanar;

	/** Instrucciones subsanación */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_INSSUB")
	private JLiteral instruccionesSubsanacion;

	/** Indica si se habilita subsanación. **/
	@Column(name = "PRG_AVIFIN", nullable = false, precision = 1, scale = 0)
	private boolean avisoAlFinalizar;

	/** Script destino registro */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_AVISCR")
	private JScript scriptAlFinalizar;

	/**
	 * Constructor.
	 */
	public JPasoRegistrar() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the pasoTramitacion
	 */
	public JPasoTramitacion getPasoTramitacion() {
		return pasoTramitacion;
	}

	/**
	 * @param pasoTramitacion the pasoTramitacion to set
	 */
	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	/**
	 * @return the scriptDestinoRegistro
	 */
	public JScript getScriptDestinoRegistro() {
		return scriptDestinoRegistro;
	}

	/**
	 * @param scriptDestinoRegistro the scriptDestinoRegistro to set
	 */
	public void setScriptDestinoRegistro(final JScript scriptDestinoRegistro) {
		this.scriptDestinoRegistro = scriptDestinoRegistro;
	}

	/**
	 * @return the scriptRepresentante
	 */
	public JScript getScriptRepresentante() {
		return scriptRepresentante;
	}

	/**
	 * @param scriptRepresentante the scriptRepresentante to set
	 */
	public void setScriptRepresentante(final JScript scriptRepresentante) {
		this.scriptRepresentante = scriptRepresentante;
	}

	/**
	 * @return the scriptValidarRegistrar
	 */
	public JScript getScriptValidarRegistrar() {
		return scriptValidarRegistrar;
	}

	/**
	 * @param scriptValidarRegistrar the scriptValidarRegistrar to set
	 */
	public void setScriptValidarRegistrar(final JScript scriptValidarRegistrar) {
		this.scriptValidarRegistrar = scriptValidarRegistrar;
	}

	/**
	 * @return the scriptPresentador
	 */
	public JScript getScriptPresentador() {
		return scriptPresentador;
	}

	/**
	 * @param scriptPresentador the scriptPresentador to set
	 */
	public void setScriptPresentador(final JScript scriptPresentador) {
		this.scriptPresentador = scriptPresentador;
	}

	/**
	 * @return the instruccionesFinTramitacion
	 */
	public JLiteral getInstruccionesFinTramitacion() {
		return instruccionesFinTramitacion;
	}

	/**
	 * @param instruccionesFinTramitacion the instruccionesFinTramitacion to set
	 */
	public void setInstruccionesFinTramitacion(final JLiteral instruccionesFinTramitacion) {
		this.instruccionesFinTramitacion = instruccionesFinTramitacion;
	}

	/**
	 * @return the instruccionesPresentacion
	 */
	public JLiteral getInstruccionesPresentacion() {
		return instruccionesPresentacion;
	}

	/**
	 * @param instruccionesPresentacion the instruccionesPresentacion to set
	 */
	public void setInstruccionesPresentacion(final JLiteral instruccionesPresentacion) {
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
	public boolean isPermiteSubsanar() {
		return permiteSubsanar;
	}

	/**
	 * @param permiteSubsanar the permiteSubsanar to set
	 */
	public void setPermiteSubsanar(final boolean permiteSubsanar) {
		this.permiteSubsanar = permiteSubsanar;
	}

	/**
	 * @return the instruccionesSubsanacion
	 */
	public JLiteral getInstruccionesSubsanacion() {
		return instruccionesSubsanacion;
	}

	/**
	 * @param instruccionesSubsanacion the instruccionesSubsanacion to set
	 */
	public void setInstruccionesSubsanacion(final JLiteral instruccionesSubsanacion) {
		this.instruccionesSubsanacion = instruccionesSubsanacion;
	}

	/**
	 * @return the avisoAlFinalizar
	 */
	public boolean isAvisoAlFinalizar() {
		return avisoAlFinalizar;
	}

	/**
	 * @param avisoAlFinalizar the avisoAlFinalizar to set
	 */
	public void setAvisoAlFinalizar(final boolean avisoAlFinalizar) {
		this.avisoAlFinalizar = avisoAlFinalizar;
	}

	/**
	 * @return the scriptAlFinalizar
	 */
	public JScript getScriptAlFinalizar() {
		return scriptAlFinalizar;
	}

	/**
	 * @param scriptAlFinalizar the scriptAlFinalizar to set
	 */
	public void setScriptAlFinalizar(final JScript scriptAlFinalizar) {
		this.scriptAlFinalizar = scriptAlFinalizar;
	}

	/**
	 * From model.
	 *
	 * @param paso
	 * @return
	 */
	public static JPasoRegistrar fromModel(final TramitePasoRegistrar paso) {
		JPasoRegistrar jpaso = null;
		if (paso != null) {
			jpaso = new JPasoRegistrar();
			jpaso.setCodigo(paso.getCodigo());
			jpaso.setAdmiteRepresentacion(paso.isAdmiteRepresentacion());
			jpaso.setCodigoLibroRegistro(paso.getCodigoLibroRegistro());
			jpaso.setCodigoOficinaRegistro(paso.getCodigoOficinaRegistro());
			jpaso.setCodigoTipoAsunto(paso.getCodigoTipoAsunto());
			jpaso.setInstruccionesFinTramitacion(JLiteral.fromModel(paso.getInstruccionesFinTramitacion()));
			jpaso.setInstruccionesPresentacion(JLiteral.fromModel(paso.getInstruccionesPresentacion()));
			jpaso.setValidaRepresentacion(paso.isValidaRepresentacion());
			jpaso.setScriptDestinoRegistro(JScript.fromModel(paso.getScriptDestinoRegistro()));
			jpaso.setScriptPresentador(JScript.fromModel(paso.getScriptPresentador()));
			jpaso.setScriptRepresentante(JScript.fromModel(paso.getScriptRepresentante()));
			jpaso.setScriptValidarRegistrar(JScript.fromModel(paso.getScriptValidarRegistrar()));
			jpaso.setInstruccionesSubsanacion(JLiteral.fromModel(paso.getInstruccionesSubsanacion()));
			jpaso.setPermiteSubsanar(paso.isPermiteSubsanar());
			jpaso.setScriptAlFinalizar(JScript.fromModel(paso.getScriptAlFinalizar()));
			jpaso.setAvisoAlFinalizar(paso.isAvisoAlFinalizar());
		}
		return jpaso;
	}

	/**
	 * Clonar.
	 *
	 * @param origPasoRegistrar
	 * @return
	 */
	public static JPasoRegistrar clonar(final JPasoRegistrar origPasoRegistrar,
			final JPasoTramitacion jpasoTramitacion) {
		JPasoRegistrar jpasoRegistrar = null;
		if (origPasoRegistrar != null) {
			jpasoRegistrar = new JPasoRegistrar();
			jpasoRegistrar.setCodigo(null);
			jpasoRegistrar.setPasoTramitacion(jpasoTramitacion);
			jpasoRegistrar.setAdmiteRepresentacion(origPasoRegistrar.isAdmiteRepresentacion());
			jpasoRegistrar.setCodigoLibroRegistro(origPasoRegistrar.getCodigoLibroRegistro());
			jpasoRegistrar.setCodigoOficinaRegistro(origPasoRegistrar.getCodigoOficinaRegistro());
			jpasoRegistrar.setCodigoTipoAsunto(origPasoRegistrar.getCodigoTipoAsunto());
			jpasoRegistrar.setInstruccionesFinTramitacion(
					JLiteral.clonar(origPasoRegistrar.getInstruccionesFinTramitacion()));
			jpasoRegistrar
					.setInstruccionesPresentacion(JLiteral.clonar(origPasoRegistrar.getInstruccionesPresentacion()));
			jpasoRegistrar.setValidaRepresentacion(origPasoRegistrar.isValidaRepresentacion());
			jpasoRegistrar.setScriptDestinoRegistro(JScript.clonar(origPasoRegistrar.getScriptDestinoRegistro()));
			jpasoRegistrar.setScriptPresentador(JScript.clonar(origPasoRegistrar.getScriptPresentador()));
			jpasoRegistrar.setScriptRepresentante(JScript.clonar(origPasoRegistrar.getScriptRepresentante()));
			jpasoRegistrar.setScriptValidarRegistrar(JScript.clonar(origPasoRegistrar.getScriptValidarRegistrar()));
			jpasoRegistrar
					.setInstruccionesSubsanacion(JLiteral.clonar(origPasoRegistrar.getInstruccionesSubsanacion()));
			jpasoRegistrar.setPermiteSubsanar(origPasoRegistrar.isPermiteSubsanar());
			jpasoRegistrar.setScriptAlFinalizar(JScript.clonar(origPasoRegistrar.getScriptAlFinalizar()));
			jpasoRegistrar.setAvisoAlFinalizar(origPasoRegistrar.isAvisoAlFinalizar());
		}
		return jpasoRegistrar;
	}

}
