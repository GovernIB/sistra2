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

import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Gestor;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypeInterno;

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
	private JGestorFormularios gestorFormulario;

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

	@Column(name = "FTR_PREREG", precision = 1, scale = 0)
	private Boolean prerregistro;

	@Column(name = "FTR_TIPFOR", nullable = false, length = 1)
	private String tipoFormulario;

	@Column(name = "FTR_FEXIDE", length = 20)
	private String idFormularioExterno;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "STG_PRLFTR", joinColumns = {
			@JoinColumn(name = "FPR_CODFOR", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "FPR_CODPRL", nullable = false, updatable = false) })
	private Set<JPasoRellenar> pasosRellenar = new HashSet<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formularioTramite")
	private Set<JPasoCaptura> pasosPagos = new HashSet<>(0);

	public JFormularioTramite() {
		// Constructor vacio
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

	public static JFormularioTramite fromModel(final FormularioTramite formulario) {
		final JFormularioTramite jformularioTramite = new JFormularioTramite();
		if (formulario != null) {
			jformularioTramite.setCodigo(formulario.getId());
			jformularioTramite.setFirmarDigitalmente(formulario.isDebeFirmarse());
			if (formulario.getDescripcion() != null) {
				jformularioTramite.setDescripcion(JLiteral.fromModel(formulario.getDescripcion()));
			}
			jformularioTramite.setIdentificador(formulario.getCodigo());
			if (formulario.getFormularioGestorExterno() != null) {
				jformularioTramite.setIdFormularioExterno(formulario.getFormularioGestorInterno().getCodigo());
			}
			jformularioTramite.setObligatorio(formulario.getObligatoriedad().toString());
			jformularioTramite.setOrden(formulario.getOrden());
			jformularioTramite.setPrerregistro(formulario.isDebePrerregistrarse());
			if (formulario.getScriptDatosIniciales() != null) {
				final JScript script = JScript.fromModel(formulario.getScriptDatosIniciales());
				jformularioTramite.setScriptDatosIniciales(script);
			}
			if (formulario.getScriptFirma() != null) {
				final JScript script = JScript.fromModel(formulario.getScriptFirma());
				jformularioTramite.setScriptFirmar(script);
			}
			if (formulario.getScriptObligatoriedad() != null) {
				final JScript script = JScript.fromModel(formulario.getScriptObligatoriedad());
				jformularioTramite.setScriptObligatoriedad(script);
			}
			if (formulario.getScriptPrerregistro() != null) {
				final JScript script = JScript.fromModel(formulario.getScriptPrerregistro());
				jformularioTramite.setScriptRetorno(script);
			}
			if (formulario.getScriptRetorno() != null) {
				final JScript script = JScript.fromModel(formulario.getScriptRetorno());
				jformularioTramite.setScriptRetorno(script);
			}
			if (formulario.getFormulario() != null) {
				final JFormulario jformulario = JFormulario.fromModel(formulario.getFormulario());
				jformularioTramite.setFormulario(jformulario);
			}
			jformularioTramite.setTipo(formulario.getTipo().toString());
			jformularioTramite.setTipoFormulario(formulario.getTipoFormulario().toString());

		}
		return jformularioTramite;
	}

	public FormularioTramite toModel() {
		final FormularioTramite mformulario = new FormularioTramite();

		mformulario.setId(this.getCodigo());
		mformulario.setDebeFirmarse(this.getFirmarDigitalmente());
		if (this.getDescripcion() != null) {
			mformulario.setDescripcion(this.getDescripcion().toModel());
		}
		if (this.getFormulario() != null) {
			mformulario.setFormulario(this.getFormulario().toModel());
		}
		// this.setGestorFormulario(x);
		mformulario.setCodigo(this.getIdentificador());
		if (this.getIdFormularioExterno() != null) {
			final Gestor form = new Gestor();
			form.setId(Long.valueOf(this.getIdFormularioExterno()));
			mformulario.setFormularioGestorInterno(form);
		}
		mformulario.setObligatoriedad(TypeFormularioObligatoriedad.fromString(this.getObligatorio()));
		mformulario.setOrden(this.getOrden());
		mformulario.setDebePrerregistrarse(this.getPrerregistro());
		if (this.getScriptDatosIniciales() != null) {
			final Script script = new Script();
			script.setId(this.getScriptDatosIniciales().getCodigo());
			mformulario.setScriptDatosIniciales(script);
		}
		if (this.getScriptFirmar() != null) {
			final Script script = new Script();
			script.setId(this.getScriptFirmar().getCodigo());
			mformulario.setScriptFirma(script);
		}
		if (this.getScriptObligatoriedad() != null) {
			final Script script = new Script();
			script.setId(this.getScriptObligatoriedad().getCodigo());
			mformulario.setScriptObligatoriedad(script);
		}
		if (this.getScriptDatosIniciales() != null) {
			final Script script = new Script();
			script.setId(this.getScriptDatosIniciales().getCodigo());
			mformulario.setScriptPrerregistro(script);
		}
		if (this.getScriptRetorno() != null) {
			final Script script = new Script();
			script.setId(this.getScriptRetorno().getCodigo());
			mformulario.setScriptRetorno(script);
		}
		mformulario.setTipo(TypeFormulario.fromString(this.getTipo()));
		mformulario.setTipoFormulario(TypeInterno.fromString(this.getTipoFormulario()));

		return mformulario;
	}

}
