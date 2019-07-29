package es.caib.sistrages.core.api.model.migracion;

import es.caib.sistrages.core.api.model.Literal;

public class DocumSistra {

	/**
	 * 
	 */
	public DocumSistra() {
		super();
	}

	private Long codigo;
	private String identi;
	private String forfor;
	private Integer forver;
	private String tipo;
	private String obligatorio;
	private String scriptObligatorio;
	private String firmar;
	private String scriptEstablecerFirmantes;
	private String nivaut;

	private String scriptDatosIniciales;
	private String scriptParametros;
	private String scriptPostGuardar;

	private String extensiones;
	private String debeConvertirPDF;
	private String ayudaURL;
	private Integer tamanyoMaximo;
	private Integer numeroInstancia;
	private String generico;
	private String telematico;

	private String scriptPago;

	private FormulSistra formulario;

	private Literal descripcion;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public String getIdenti() {
		return identi;
	}

	public void setIdenti(final String identi) {
		this.identi = identi;
	}

	public String getForfor() {
		return forfor;
	}

	public void setForfor(final String forfor) {
		this.forfor = forfor;
	}

	public Integer getForver() {
		return forver;
	}

	public void setForver(final Integer forver) {
		this.forver = forver;
	}

	public Literal getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public FormulSistra getFormulario() {
		return formulario;
	}

	public void setFormulario(final FormulSistra formulario) {
		this.formulario = formulario;
	}

	public String getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(final String obligatorio) {
		this.obligatorio = obligatorio;
	}

	public String getScriptObligatorio() {
		return scriptObligatorio;
	}

	public void setScriptObligatorio(final String scriptObligatorio) {
		this.scriptObligatorio = scriptObligatorio;
	}

	public String getFirmar() {
		return firmar;
	}

	public void setFirmar(final String firmar) {
		this.firmar = firmar;
	}

	public String getScriptEstablecerFirmantes() {
		return scriptEstablecerFirmantes;
	}

	public void setScriptEstablecerFirmantes(final String scriptEstablecerFirmantes) {
		this.scriptEstablecerFirmantes = scriptEstablecerFirmantes;
	}

	public String getNivaut() {
		return nivaut;
	}

	public void setNivaut(final String nivaut) {
		this.nivaut = nivaut;
	}

	public String getScriptDatosIniciales() {
		return scriptDatosIniciales;
	}

	public void setScriptDatosIniciales(final String scriptDatosIniciales) {
		this.scriptDatosIniciales = scriptDatosIniciales;
	}

	public String getScriptParametros() {
		return scriptParametros;
	}

	public void setScriptParametros(final String scriptParametros) {
		this.scriptParametros = scriptParametros;
	}

	public String getScriptPostGuardar() {
		return scriptPostGuardar;
	}

	public void setScriptPostGuardar(final String scriptPostGuardar) {
		this.scriptPostGuardar = scriptPostGuardar;
	}

	public String getExtensiones() {
		return extensiones;
	}

	public void setExtensiones(final String extensiones) {
		this.extensiones = extensiones;
	}

	public String getDebeConvertirPDF() {
		return debeConvertirPDF;
	}

	public void setDebeConvertirPDF(final String debeConvertirPDF) {
		this.debeConvertirPDF = debeConvertirPDF;
	}

	public String getAyudaURL() {
		return ayudaURL;
	}

	public void setAyudaURL(final String ayudaURL) {
		this.ayudaURL = ayudaURL;
	}

	public Integer getTamanyoMaximo() {
		return tamanyoMaximo;
	}

	public void setTamanyoMaximo(final Integer tamanyoMaximo) {
		this.tamanyoMaximo = tamanyoMaximo;
	}

	public Integer getNumeroInstancia() {
		return numeroInstancia;
	}

	public void setNumeroInstancia(final Integer numeroInstancia) {
		this.numeroInstancia = numeroInstancia;
	}

	public String getGenerico() {
		return generico;
	}

	public void setGenerico(final String generico) {
		this.generico = generico;
	}

	public String getTelematico() {
		return telematico;
	}

	public void setTelematico(final String telematico) {
		this.telematico = telematico;
	}

	public String getScriptPago() {
		return scriptPago;
	}

	public void setScriptPago(String scriptPago) {
		this.scriptPago = scriptPago;
	}
}
