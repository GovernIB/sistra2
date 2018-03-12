package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JFormularioTramite
 */
@Entity
@Table(name = "STG_FORTRA")
public class JFormularioTramite implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORTRA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORTRA_SEQ", sequenceName = "STG_FORTRA_SEQ")
	@Column(name = "FTR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_CODFOR")
	private JFormulario formulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_FEXGST")
	private JGestorFormularios gestorFormulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_SCRRET")
	private JScript scriptRetorno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_SCRPAR")
	private JScript scriptParametros;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_SCRINI")
	private JScript scriptDatosIniciales;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_SCRFIR")
	private JScript scriptFirmar;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_SCROBL")
	private JScript scriptObligatoriedad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_DESCRIP", nullable = false)
	private JLiterales descripcion;

	@Column(name = "FTR_IDENTI", nullable = false, length = 20)
	private String identificador;

	@Column(name = "FTR_TIPO", nullable = false, length = 1)
	private String tipo;

	@Column(name = "FTR_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	@Column(name = "FTR_OBLIGA", nullable = false, length = 1)
	private String obligatorio;

	@Column(name = "FTR_FIRDIG", precision = 1, scale = 0)
	private Boolean firmarDigitalmente;

	@Column(name = "FTR_PREREG", precision = 1, scale = 0)
	private Boolean prerregistro;

	@Column(name = "FTR_TIPFOR", nullable = false, length = 1)
	private String tipoFormulario;

	@Column(name = "FTR_FEXIDE", length = 20)
	private String idFormularioExterno;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_PRLFTR", joinColumns = {
			@JoinColumn(name = "FPR_CODFOR", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "FPR_CODPRL", nullable = false, updatable = false) })
	private Set<JPasoRellenar> pasosRellenar = new HashSet<JPasoRellenar>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formularioTramite")
	private Set<JPasoCaptura> pasosPagos = new HashSet<JPasoCaptura>(0);

	public JFormularioTramite() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JFormulario getFormulario() {
		return this.formulario;
	}

	public void setFormulario(final JFormulario formulario) {
		this.formulario = formulario;
	}

	public JGestorFormularios getGestorFormulario() {
		return this.gestorFormulario;
	}

	public void setGestorFormulario(final JGestorFormularios gestorFormulario) {
		this.gestorFormulario = gestorFormulario;
	}

	public JScript getScriptRetorno() {
		return this.scriptRetorno;
	}

	public void setScriptRetorno(final JScript scriptRetorno) {
		this.scriptRetorno = scriptRetorno;
	}

	public JScript getScriptParametros() {
		return this.scriptParametros;
	}

	public void setScriptParametros(final JScript scriptParametros) {
		this.scriptParametros = scriptParametros;
	}

	public JScript getScriptDatosIniciales() {
		return this.scriptDatosIniciales;
	}

	public void setScriptDatosIniciales(final JScript scriptDatosIniciales) {
		this.scriptDatosIniciales = scriptDatosIniciales;
	}

	public JScript getScriptFirmar() {
		return this.scriptFirmar;
	}

	public void setScriptFirmar(final JScript scriptFirmar) {
		this.scriptFirmar = scriptFirmar;
	}

	public JScript getScriptObligatoriedad() {
		return this.scriptObligatoriedad;
	}

	public void setScriptObligatoriedad(final JScript scriptObligatoriedad) {
		this.scriptObligatoriedad = scriptObligatoriedad;
	}

	public JLiterales getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final JLiterales descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public String getObligatorio() {
		return this.obligatorio;
	}

	public void setObligatorio(final String obligatorio) {
		this.obligatorio = obligatorio;
	}

	public Boolean getFirmarDigitalmente() {
		return this.firmarDigitalmente;
	}

	public void setFirmarDigitalmente(final Boolean firmarDigitalmente) {
		this.firmarDigitalmente = firmarDigitalmente;
	}

	public Boolean getPrerregistro() {
		return this.prerregistro;
	}

	public void setPrerregistro(final Boolean prerregistro) {
		this.prerregistro = prerregistro;
	}

	public String getTipoFormulario() {
		return this.tipoFormulario;
	}

	public void setTipoFormulario(final String tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public String getIdFormularioExterno() {
		return this.idFormularioExterno;
	}

	public void setIdFormularioExterno(final String idFormularioExterno) {
		this.idFormularioExterno = idFormularioExterno;
	}

	public Set<JPasoRellenar> getPasosRellenar() {
		return this.pasosRellenar;
	}

	public void setPasosRellenar(final Set<JPasoRellenar> pasosRellenar) {
		this.pasosRellenar = pasosRellenar;
	}

	public Set<JPasoCaptura> getPasosPagos() {
		return this.pasosPagos;
	}

	public void setPasosPagos(final Set<JPasoCaptura> pasosPagos) {
		this.pasosPagos = pasosPagos;
	}

}
