package es.caib.sistrages.core.api.model.migracion;

import java.util.Date;
import java.util.List;

import es.caib.sistrages.core.api.model.Literal;

public class TraverSistra {

	private Long trvCodigo;

	private Long trvCodtra;
	private Long trvVersio;
	private Long trvCodetn;
	private String trvDesver;
	private Long trvUniadm;
	private Date trvInipla;
	private Date trvFinpla;
	private String trvDestin;
	private String trvFirma;
	private String trvRegofi;
	private String trvRegast;
	private String trvConejb;
	private String trvBloque;
	private String trvBlousu;
	private String trvIdisop;
	private String trvReduci;
	private String trvConrem;
	private String trvConurl;
	private String trvConaut;
	private String trvConusu;
	private String trvConpwd;
	private String trvTagcar;
	private Date trvFeccar;
	private String trvPrenva;
	private String trvRedfin;
	private String trvAnodef;
	private String trvOrgdes;
	private String trvContip;
	private String trvConwsv;
	private String trvRegaut;
	private String trvDebug;

	private List<DocumSistra> listaDocumSistra;
	private String scriptPersonalizacion;
	private boolean autenticado;
	private boolean noAutenticado;
	private Literal instruccionesIniciales;
	private String scriptRepresentante;
	private String scriptPresentador;
	private String scriptValidarRegistrar;
	private Literal instruccionesTramitacion;
	private Literal instruccionesEntregaPresencial;

	;

	public TraverSistra() {
		super();
	}

	public Long getTrvCodigo() {
		return trvCodigo;
	}

	public void setTrvCodigo(final Long trvCodigo) {
		this.trvCodigo = trvCodigo;
	}

	public Long getTrvCodtra() {
		return trvCodtra;
	}

	public void setTrvCodtra(final Long trvCodtra) {
		this.trvCodtra = trvCodtra;
	}

	public Long getTrvVersio() {
		return trvVersio;
	}

	public void setTrvVersio(final Long trvVersio) {
		this.trvVersio = trvVersio;
	}

	public Long getTrvCodetn() {
		return trvCodetn;
	}

	public void setTrvCodetn(final Long trvCodetn) {
		this.trvCodetn = trvCodetn;
	}

	public String getTrvDesver() {
		return trvDesver;
	}

	public void setTrvDesver(final String trvDesver) {
		this.trvDesver = trvDesver;
	}

	public Long getTrvUniadm() {
		return trvUniadm;
	}

	public void setTrvUniadm(final Long trvUniadm) {
		this.trvUniadm = trvUniadm;
	}

	public Date getTrvInipla() {
		return trvInipla;
	}

	public void setTrvInipla(final Date trvInipla) {
		this.trvInipla = trvInipla;
	}

	public Date getTrvFinpla() {
		return trvFinpla;
	}

	public void setTrvFinpla(final Date trvFinpla) {
		this.trvFinpla = trvFinpla;
	}

	public String getTrvDestin() {
		return trvDestin;
	}

	public void setTrvDestin(final String trvDestin) {
		this.trvDestin = trvDestin;
	}

	public String getTrvFirma() {
		return trvFirma;
	}

	public void setTrvFirma(final String trvFirma) {
		this.trvFirma = trvFirma;
	}

	public String getTrvRegofi() {
		return trvRegofi;
	}

	public void setTrvRegofi(final String trvRegofi) {
		this.trvRegofi = trvRegofi;
	}

	public String getTrvRegast() {
		return trvRegast;
	}

	public void setTrvRegast(final String trvRegast) {
		this.trvRegast = trvRegast;
	}

	public String getTrvConejb() {
		return trvConejb;
	}

	public void setTrvConejb(final String trvConejb) {
		this.trvConejb = trvConejb;
	}

	public String getTrvBloque() {
		return trvBloque;
	}

	public void setTrvBloque(final String trvBloque) {
		this.trvBloque = trvBloque;
	}

	public String getTrvBlousu() {
		return trvBlousu;
	}

	public void setTrvBlousu(final String trvBlousu) {
		this.trvBlousu = trvBlousu;
	}

	public String getTrvIdisop() {
		return trvIdisop;
	}

	public void setTrvIdisop(final String trvIdisop) {
		this.trvIdisop = trvIdisop;
	}

	public String getTrvReduci() {
		return trvReduci;
	}

	public void setTrvReduci(final String trvReduci) {
		this.trvReduci = trvReduci;
	}

	public String getTrvConrem() {
		return trvConrem;
	}

	public void setTrvConrem(final String trvConrem) {
		this.trvConrem = trvConrem;
	}

	public String getTrvConurl() {
		return trvConurl;
	}

	public void setTrvConurl(final String trvConurl) {
		this.trvConurl = trvConurl;
	}

	public String getTrvConaut() {
		return trvConaut;
	}

	public void setTrvConaut(final String trvConaut) {
		this.trvConaut = trvConaut;
	}

	public String getTrvConusu() {
		return trvConusu;
	}

	public void setTrvConusu(final String trvConusu) {
		this.trvConusu = trvConusu;
	}

	public String getTrvConpwd() {
		return trvConpwd;
	}

	public void setTrvConpwd(final String trvConpwd) {
		this.trvConpwd = trvConpwd;
	}

	public String getTrvTagcar() {
		return trvTagcar;
	}

	public void setTrvTagcar(final String trvTagcar) {
		this.trvTagcar = trvTagcar;
	}

	public Date getTrvFeccar() {
		return trvFeccar;
	}

	public void setTrvFeccar(final Date trvFeccar) {
		this.trvFeccar = trvFeccar;
	}

	public String getTrvPrenva() {
		return trvPrenva;
	}

	public void setTrvPrenva(final String trvPrenva) {
		this.trvPrenva = trvPrenva;
	}

	public String getTrvRedfin() {
		return trvRedfin;
	}

	public void setTrvRedfin(final String trvRedfin) {
		this.trvRedfin = trvRedfin;
	}

	public String getTrvAnodef() {
		return trvAnodef;
	}

	public void setTrvAnodef(final String trvAnodef) {
		this.trvAnodef = trvAnodef;
	}

	public String getTrvOrgdes() {
		return trvOrgdes;
	}

	public void setTrvOrgdes(final String trvOrgdes) {
		this.trvOrgdes = trvOrgdes;
	}

	public String getTrvContip() {
		return trvContip;
	}

	public void setTrvContip(final String trvContip) {
		this.trvContip = trvContip;
	}

	public String getTrvConwsv() {
		return trvConwsv;
	}

	public void setTrvConwsv(final String trvConwsv) {
		this.trvConwsv = trvConwsv;
	}

	public String getTrvRegaut() {
		return trvRegaut;
	}

	public void setTrvRegaut(final String trvRegaut) {
		this.trvRegaut = trvRegaut;
	}

	public String getTrvDebug() {
		return trvDebug;
	}

	public void setTrvDebug(final String trvDebug) {
		this.trvDebug = trvDebug;
	}

	public List<DocumSistra> getListaDocumSistra() {
		return listaDocumSistra;
	}

	public void setListaDocumSistra(final List<DocumSistra> listaDocumSistra) {
		this.listaDocumSistra = listaDocumSistra;
	}

	public String getScriptPersonalizacion() {
		return scriptPersonalizacion;
	}

	public void setScriptPersonalizacion(final String scriptPersonalizacion) {
		this.scriptPersonalizacion = scriptPersonalizacion;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(final boolean autenticado) {
		this.autenticado = autenticado;
	}

	public boolean isNoAutenticado() {
		return noAutenticado;
	}

	public void setNoAutenticado(final boolean noAutenticado) {
		this.noAutenticado = noAutenticado;
	}

	public Literal getInstruccionesIniciales() {
		return instruccionesIniciales;
	}

	public void setInstruccionesIniciales(final Literal instruccionesIniciales) {
		this.instruccionesIniciales = instruccionesIniciales;
	}

	public String getScriptRepresentante() {
		return scriptRepresentante;
	}

	public void setScriptRepresentante(final String scriptRepresentante) {
		this.scriptRepresentante = scriptRepresentante;
	}

	public String getScriptPresentador() {
		return scriptPresentador;
	}

	public void setScriptPresentador(final String scriptPresentador) {
		this.scriptPresentador = scriptPresentador;
	}

	public String getScriptValidarRegistrar() {
		return scriptValidarRegistrar;
	}

	public void setScriptValidarRegistrar(final String scriptValidarRegistrar) {
		this.scriptValidarRegistrar = scriptValidarRegistrar;
	}

	public Literal getInstruccionesTramitacion() {
		return instruccionesTramitacion;
	}

	public void setInstruccionesTramitacion(final Literal instruccionesTramitacion) {
		this.instruccionesTramitacion = instruccionesTramitacion;
	}

	public Literal getInstruccionesEntregaPresencial() {
		return instruccionesEntregaPresencial;
	}

	public void setInstruccionesEntregaPresencial(final Literal instruccionesEntregaPresencial) {
		this.instruccionesEntregaPresencial = instruccionesEntregaPresencial;
	}

}
