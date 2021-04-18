package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioGestor;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;

/**
 * JFormularioTramite
 */
@Entity
@Table(name = "STG_FORTRA")
public class JFormularioTramite implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORTRA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORTRA_SEQ", sequenceName = "STG_FORTRA_SEQ")
	@Column(name = "FTR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_CODFOR")
	private JFormulario formulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FTR_FEXGST")
	private JGestorExternoFormularios formularioExterno;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FTR_SCRRET")
	private JScript scriptRetorno;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FTR_SCRPAR")
	private JScript scriptParametros;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FTR_SCRINI")
	private JScript scriptDatosIniciales;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FTR_SCRFIR")
	private JScript scriptFirmar;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FTR_SCROBL")
	private JScript scriptObligatoriedad;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "FTR_DESCRIP", nullable = false)
	private JLiteral descripcion;

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

	@Column(name = "FTR_TIPFOR", nullable = false, length = 1)
	private String tipoFormulario;

	@Column(name = "FTR_FEXIDE", length = 20)
	private String idFormularioExterno;

	/** Pertenece al id del elemento anteriormente clonado. No se debe guardar!. */
	@Transient
	private Long codigoClonado;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "STG_PRLFTR", joinColumns = {
			@JoinColumn(name = "FPR_CODFOR", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "FPR_CODPRL", nullable = false, updatable = false) })
	private Set<JPasoRellenar> pasosRellenar = new HashSet<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formularioTramite")
	private Set<JPasoCaptura> pasosCaptura = new HashSet<>(0);

	/** Construactor. **/
	public JFormularioTramite() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFormulario getFormulario() {
		return this.formulario;
	}

	public void setFormulario(final JFormulario formulario) {
		this.formulario = formulario;
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

	public JLiteral getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final JLiteral descripcion) {
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

	/**
	 * @return the formularioExterno
	 */
	public JGestorExternoFormularios getFormularioExterno() {
		return formularioExterno;
	}

	/**
	 * @param formularioExterno the formularioExterno to set
	 */
	public void setFormularioExterno(final JGestorExternoFormularios formularioExterno) {
		this.formularioExterno = formularioExterno;
	}

	public Set<JPasoRellenar> getPasosRellenar() {
		return this.pasosRellenar;
	}

	public void setPasosRellenar(final Set<JPasoRellenar> pasosRellenar) {
		this.pasosRellenar = pasosRellenar;
	}

	public Set<JPasoCaptura> getPasosCaptura() {
		return this.pasosCaptura;
	}

	public void setPasosCaptura(final Set<JPasoCaptura> pasosPagos) {
		this.pasosCaptura = pasosPagos;
	}

	/**
	 * @return the codigoClonado
	 */
	public Long getCodigoClonado() {
		return codigoClonado;
	}

	/**
	 * @param codigoClonado the codigoClonado to set
	 */
	public void setCodigoClonado(final Long codigoClonado) {
		this.codigoClonado = codigoClonado;
	}

	/**
	 * fromModel
	 *
	 * @param formulario
	 * @return
	 */
	public static JFormularioTramite fromModel(final FormularioTramite formulario) {
		final JFormularioTramite jformularioTramite = new JFormularioTramite();
		if (formulario != null) {
			jformularioTramite.setCodigo(formulario.getCodigo());
			jformularioTramite.setFirmarDigitalmente(formulario.isDebeFirmarse());
			jformularioTramite.setDescripcion(JLiteral.fromModel(formulario.getDescripcion()));
			jformularioTramite.setIdentificador(formulario.getIdentificador());
			if (formulario.getFormularioGestorExterno() != null) {
				jformularioTramite.setIdFormularioExterno(formulario.getIdFormularioExterno());
				jformularioTramite.setFormularioExterno(
						JGestorExternoFormularios.fromModel(formulario.getFormularioGestorExterno()));
			}
			jformularioTramite.setObligatorio(formulario.getObligatoriedad().toString());
			jformularioTramite.setOrden(formulario.getOrden());

			jformularioTramite.setScriptDatosIniciales(JScript.fromModel(formulario.getScriptDatosIniciales()));
			jformularioTramite.setScriptFirmar(JScript.fromModel(formulario.getScriptFirma()));
			jformularioTramite.setScriptObligatoriedad(JScript.fromModel(formulario.getScriptObligatoriedad()));
			jformularioTramite.setScriptParametros(JScript.fromModel(formulario.getScriptParametros()));
			jformularioTramite.setScriptRetorno(JScript.fromModel(formulario.getScriptRetorno()));

			jformularioTramite.setTipo(formulario.getTipo().toString());
			jformularioTramite.setTipoFormulario(formulario.getTipoFormulario().toString());

		}
		return jformularioTramite;
	}

	/**
	 * toModel.
	 *
	 * @return
	 */
	public FormularioTramite toModel() {
		final FormularioTramite mformulario = new FormularioTramite();

		mformulario.setCodigo(this.getCodigo());
		mformulario.setDebeFirmarse(this.getFirmarDigitalmente());
		if (this.getDescripcion() != null) {
			mformulario.setDescripcion(this.getDescripcion().toModel());
		}
		if (this.getFormulario() != null) {
			mformulario.setIdFormularioInterno(this.getFormulario().getCodigo());
		}
		mformulario.setIdentificador(this.getIdentificador());
		mformulario.setIdFormularioExterno(this.getIdFormularioExterno());

		if (this.getFormularioExterno() != null) {
			final GestorExternoFormularios form = this.getFormularioExterno().toModel();
			mformulario.setFormularioGestorExterno(form);
		}
		mformulario.setObligatoriedad(TypeFormularioObligatoriedad.fromString(this.getObligatorio()));
		mformulario.setOrden(this.getOrden());

		if (this.getScriptDatosIniciales() != null) {
			mformulario.setScriptDatosIniciales(this.getScriptDatosIniciales().toModel());
		}
		if (this.getScriptFirmar() != null) {
			mformulario.setScriptFirma(this.getScriptFirmar().toModel());
		}
		if (this.getScriptObligatoriedad() != null) {
			mformulario.setScriptObligatoriedad(this.getScriptObligatoriedad().toModel());
		}
		if (this.getScriptParametros() != null) {
			mformulario.setScriptParametros(this.getScriptParametros().toModel());
		}
		if (this.getScriptRetorno() != null) {
			mformulario.setScriptRetorno(this.getScriptRetorno().toModel());
		}
		mformulario.setTipo(TypeFormulario.fromString(this.getTipo()));
		mformulario.setTipoFormulario(TypeFormularioGestor.fromString(this.getTipoFormulario()));

		return mformulario;
	}

	/**
	 * Clonar.
	 *
	 * @param formularioTramite
	 * @return
	 */
	public static JFormularioTramite clonar(final JFormularioTramite formularioTramite) {
		JFormularioTramite jformularioTramite = null;
		if (formularioTramite != null) {
			jformularioTramite = new JFormularioTramite();
			jformularioTramite.setCodigo(null);
			jformularioTramite.setCodigoClonado(formularioTramite.getCodigo());
			jformularioTramite.setFirmarDigitalmente(formularioTramite.getFirmarDigitalmente());
			jformularioTramite.setDescripcion(JLiteral.clonar(formularioTramite.getDescripcion()));
			jformularioTramite.setIdentificador(formularioTramite.getIdentificador());
			jformularioTramite.setIdFormularioExterno(formularioTramite.getIdFormularioExterno());

			jformularioTramite.setObligatorio(formularioTramite.getObligatorio());
			jformularioTramite.setOrden(formularioTramite.getOrden());

			jformularioTramite.setScriptDatosIniciales(JScript.clonar(formularioTramite.getScriptDatosIniciales()));
			jformularioTramite.setScriptFirmar(JScript.clonar(formularioTramite.getScriptFirmar()));
			jformularioTramite.setScriptObligatoriedad(JScript.clonar(formularioTramite.getScriptObligatoriedad()));
			jformularioTramite.setScriptParametros(JScript.clonar(formularioTramite.getScriptParametros()));
			jformularioTramite.setScriptRetorno(JScript.clonar(formularioTramite.getScriptRetorno()));
			jformularioTramite.setFormulario(null);
			jformularioTramite.setIdFormularioExterno(formularioTramite.getIdFormularioExterno());
			jformularioTramite.setFormularioExterno(formularioTramite.getFormularioExterno());

			jformularioTramite.setTipo(formularioTramite.getTipo());
			jformularioTramite.setTipoFormulario(formularioTramite.getTipoFormulario());
		}
		return jformularioTramite;
	}

}
