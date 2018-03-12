package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JPasoRegistrar
 */
@Entity
@Table(name = "STG_PASREG")
public class JPasoRegistrar implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PRG_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRG_SCRREG")
	private JScript scriptDestinoRegistro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRG_SCRREP")
	private JScript scriptRepresentante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRG_SCRVAL")
	private JScript scriptValidarRegistrar;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRG_SCRPRE")
	private JScript scriptPresentador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRG_INSFIT")
	private JLiterales instruccionesFinTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRG_INSPRE")
	private JLiterales instruccionesPresentacion;

	@Column(name = "PRG_REGOFI", length = 20)
	private String codigoOficinaRegistro;

	@Column(name = "PRG_REGLIB", length = 20)
	private String codigoLibroRegistro;

	@Column(name = "PRG_REGASU", length = 20)
	private String codigoTipoAsunto;

	@Column(name = "PRG_REGORG", length = 20)
	private String codigoOrganoDestino;

	@Column(name = "PRG_FIRMAR", nullable = false, precision = 1, scale = 0)
	private boolean firmar;

	@Column(name = "PRG_REPADM", nullable = false, precision = 1, scale = 0)
	private boolean admiteRepresentacion;

	@Column(name = "PRG_REPVAL", nullable = false, precision = 1, scale = 0)
	private boolean validaRepresentacion;

	public JPasoRegistrar() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JPasoTramitacion getPasoTramitacion() {
		return this.pasoTramitacion;
	}

	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	public JScript getScriptDestinoRegistro() {
		return this.scriptDestinoRegistro;
	}

	public void setScriptDestinoRegistro(final JScript scriptDestinoRegistro) {
		this.scriptDestinoRegistro = scriptDestinoRegistro;
	}

	public JScript getScriptRepresentante() {
		return this.scriptRepresentante;
	}

	public void setScriptRepresentante(final JScript scriptRepresentante) {
		this.scriptRepresentante = scriptRepresentante;
	}

	public JScript getScriptValidarRegistrar() {
		return this.scriptValidarRegistrar;
	}

	public void setScriptValidarRegistrar(final JScript scriptValidarRegistrar) {
		this.scriptValidarRegistrar = scriptValidarRegistrar;
	}

	public JScript getScriptPresentador() {
		return this.scriptPresentador;
	}

	public void setScriptPresentador(final JScript scriptPresentador) {
		this.scriptPresentador = scriptPresentador;
	}

	public JLiterales getInstruccionesFinTramitacion() {
		return this.instruccionesFinTramitacion;
	}

	public void setInstruccionesFinTramitacion(final JLiterales instruccionesFinTramitacion) {
		this.instruccionesFinTramitacion = instruccionesFinTramitacion;
	}

	public JLiterales getInstruccionesPresentacion() {
		return this.instruccionesPresentacion;
	}

	public void setInstruccionesPresentacion(final JLiterales instruccionesPresentacion) {
		this.instruccionesPresentacion = instruccionesPresentacion;
	}

	public String getCodigoOficinaRegistro() {
		return this.codigoOficinaRegistro;
	}

	public void setCodigoOficinaRegistro(final String codigoOficinaRegistro) {
		this.codigoOficinaRegistro = codigoOficinaRegistro;
	}

	public String getCodigoLibroRegistro() {
		return this.codigoLibroRegistro;
	}

	public void setCodigoLibroRegistro(final String codigoLibroRegistro) {
		this.codigoLibroRegistro = codigoLibroRegistro;
	}

	public String getCodigoTipoAsunto() {
		return this.codigoTipoAsunto;
	}

	public void setCodigoTipoAsunto(final String codigoTipoAsunto) {
		this.codigoTipoAsunto = codigoTipoAsunto;
	}

	public String getCodigoOrganoDestino() {
		return this.codigoOrganoDestino;
	}

	public void setCodigoOrganoDestino(final String codigoOrganoDestino) {
		this.codigoOrganoDestino = codigoOrganoDestino;
	}

	public boolean isFirmar() {
		return this.firmar;
	}

	public void setFirmar(final boolean firmar) {
		this.firmar = firmar;
	}

	public boolean isAdmiteRepresentacion() {
		return this.admiteRepresentacion;
	}

	public void setAdmiteRepresentacion(final boolean admiteRepresentacion) {
		this.admiteRepresentacion = admiteRepresentacion;
	}

	public boolean isValidaRepresentacion() {
		return this.validaRepresentacion;
	}

	public void setValidaRepresentacion(final boolean validaRepresentacion) {
		this.validaRepresentacion = validaRepresentacion;
	}

}
