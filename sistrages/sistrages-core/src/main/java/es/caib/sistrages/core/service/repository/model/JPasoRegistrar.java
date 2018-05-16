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

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PRG_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_SCRREG")
	private JScript scriptDestinoRegistro;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_SCRREP")
	private JScript scriptRepresentante;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_SCRVAL")
	private JScript scriptValidarRegistrar;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_SCRPRE")
	private JScript scriptPresentador;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_INSFIT")
	private JLiteral instruccionesFinTramitacion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRG_INSPRE")
	private JLiteral instruccionesPresentacion;

	@Column(name = "PRG_REGOFI", length = 20)
	private String codigoOficinaRegistro;

	@Column(name = "PRG_REGLIB", length = 20)
	private String codigoLibroRegistro;

	@Column(name = "PRG_REGASU", length = 20)
	private String codigoTipoAsunto;

	@Column(name = "PRG_REPADM", nullable = false, precision = 1, scale = 0)
	private boolean admiteRepresentacion;

	@Column(name = "PRG_REPVAL", nullable = false, precision = 1, scale = 0)
	private boolean validaRepresentacion;

	public JPasoRegistrar() {
		// Constructor vacio
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
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
	 * @param pasoTramitacion
	 *            the pasoTramitacion to set
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
	 * @param scriptDestinoRegistro
	 *            the scriptDestinoRegistro to set
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
	 * @param scriptRepresentante
	 *            the scriptRepresentante to set
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
	 * @param scriptValidarRegistrar
	 *            the scriptValidarRegistrar to set
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
	 * @param scriptPresentador
	 *            the scriptPresentador to set
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
	 * @param instruccionesFinTramitacion
	 *            the instruccionesFinTramitacion to set
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
	 * @param instruccionesPresentacion
	 *            the instruccionesPresentacion to set
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
	 * @param codigoOficinaRegistro
	 *            the codigoOficinaRegistro to set
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
	 * @param codigoLibroRegistro
	 *            the codigoLibroRegistro to set
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
	 * @param codigoTipoAsunto
	 *            the codigoTipoAsunto to set
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
	 * @param admiteRepresentacion
	 *            the admiteRepresentacion to set
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
	 * @param validaRepresentacion
	 *            the validaRepresentacion to set
	 */
	public void setValidaRepresentacion(final boolean validaRepresentacion) {
		this.validaRepresentacion = validaRepresentacion;
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
			if (paso.getInstruccionesFinTramitacion() != null) {
				jpaso.setInstruccionesFinTramitacion(JLiteral.fromModel(paso.getInstruccionesFinTramitacion()));
			}
			if (paso.getInstruccionesPresentacion() != null) {
				jpaso.setInstruccionesPresentacion(JLiteral.fromModel(paso.getInstruccionesPresentacion()));
			}
			jpaso.setValidaRepresentacion(paso.isValidaRepresentacion());
			if (paso.getScriptDestinoRegistro() != null) {
				jpaso.setScriptDestinoRegistro(JScript.fromModel(paso.getScriptDestinoRegistro()));
			}
			if (paso.getScriptPresentador() != null) {
				jpaso.setScriptPresentador(JScript.fromModel(paso.getScriptPresentador()));
			}
			if (paso.getScriptRepresentante() != null) {
				jpaso.setScriptRepresentante(JScript.fromModel(paso.getScriptRepresentante()));
			}
			if (paso.getScriptValidarRegistrar() != null) {
				jpaso.setScriptValidarRegistrar(JScript.fromModel(paso.getScriptValidarRegistrar()));
			}
		}
		return jpaso;
	}

}
