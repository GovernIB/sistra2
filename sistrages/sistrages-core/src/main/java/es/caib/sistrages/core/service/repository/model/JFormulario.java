package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.PaginaFormulario;

/**
 * JFormulario
 */
@Entity
@Table(name = "STG_FORMUL")
public class JFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORMUL_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORMUL_SEQ", sequenceName = "STG_FORMUL_SEQ")
	@Column(name = "FOR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOR_CABLOG")
	private JFichero logoCabecera;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "FOR_SCRPLT")
	private JScript scriptPlantilla;

	@ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "FOR_CABTXT")
	private JLiteral textoCabecera;

	@Column(name = "FOR_ACCPER", nullable = false, precision = 1, scale = 0)
	private boolean permitirAccionesPersonalizadas;

	@Column(name = "FOR_CABFOR", nullable = false, precision = 1, scale = 0)
	private boolean cabeceraFormulario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario", orphanRemoval = true, cascade = { CascadeType.ALL })
	private Set<JPaginaFormulario> paginas = new HashSet<JPaginaFormulario>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario")
	private Set<JAccionPersonalizada> accionesPersonalizadas = new HashSet<JAccionPersonalizada>(0);

	public JFormulario() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFichero getLogoCabecera() {
		return this.logoCabecera;
	}

	public void setLogoCabecera(final JFichero logoCabecera) {
		this.logoCabecera = logoCabecera;
	}

	public JScript getScriptPlantilla() {
		return this.scriptPlantilla;
	}

	public void setScriptPlantilla(final JScript scriptPlantilla) {
		this.scriptPlantilla = scriptPlantilla;
	}

	public JLiteral getTextoCabecera() {
		return this.textoCabecera;
	}

	public void setTextoCabecera(final JLiteral textoCabecera) {
		this.textoCabecera = textoCabecera;
	}

	public boolean isPermitirAccionesPersonalizadas() {
		return this.permitirAccionesPersonalizadas;
	}

	public void setPermitirAccionesPersonalizadas(final boolean permitirAccionesPersonalizadas) {
		this.permitirAccionesPersonalizadas = permitirAccionesPersonalizadas;
	}

	public boolean isCabeceraFormulario() {
		return this.cabeceraFormulario;
	}

	public void setCabeceraFormulario(final boolean cabeceraFormulario) {
		this.cabeceraFormulario = cabeceraFormulario;
	}

	public Set<JPaginaFormulario> getPaginas() {
		return this.paginas;
	}

	public void setPaginas(final Set<JPaginaFormulario> paginas) {
		this.paginas = paginas;
	}

	public Set<JAccionPersonalizada> getAccionesPersonalizadas() {
		return this.accionesPersonalizadas;
	}

	public void setAccionesPersonalizadas(final Set<JAccionPersonalizada> accionesPersonalizadas) {
		this.accionesPersonalizadas = accionesPersonalizadas;
	}

	public FormularioInterno toModel() {
		final FormularioInterno formulario = new FormularioInterno();
		formulario.setId(codigo);
		formulario.setPermitirAccionesPersonalizadas(permitirAccionesPersonalizadas);
		if (scriptPlantilla != null) {
			formulario.setScriptPlantilla(scriptPlantilla.toModel());
		}
		formulario.setCabeceraFormulario(cabeceraFormulario);
		if (textoCabecera != null) {
			formulario.setTextoCabecera(textoCabecera.toModel());
		}

		return formulario;
	}

	public static JFormulario fromModel(final FormularioInterno model) {
		JFormulario jModel = null;
		if (model != null) {
			jModel = new JFormulario();
			jModel.setCodigo(model.getId());
			jModel.setPermitirAccionesPersonalizadas(model.isPermitirAccionesPersonalizadas());
			jModel.setScriptPlantilla(JScript.fromModel(model.getScriptPlantilla()));
			jModel.setCabeceraFormulario(model.isCabeceraFormulario());
			jModel.setTextoCabecera(JLiteral.fromModel(model.getTextoCabecera()));
		}
		return jModel;
	}

	public static JFormulario mergePaginasModel(final JFormulario jFormulario, final FormularioInterno pFormInt) {
		JFormulario jModel = null;

		if (jFormulario != null && !jFormulario.getPaginas().isEmpty() && pFormInt != null
				&& !pFormInt.getPaginas().isEmpty()) {
			// Borrar paginas no pasados en modelo
			final List<JPaginaFormulario> borrar = new ArrayList<JPaginaFormulario>();
			final List<Long> listPaginas = new ArrayList<>();

			for (final PaginaFormulario pag : pFormInt.getPaginas()) {
				if (pag.getId() != null) {
					listPaginas.add(pag.getId());
				}
			}

			for (final JPaginaFormulario jPag : jFormulario.getPaginas()) {
				if (!listPaginas.contains(jPag.getCodigo())) {
					borrar.add(jPag);
				}
			}

			for (final JPaginaFormulario jPag : borrar) {
				jFormulario.getPaginas().remove(jPag);
				jPag.setFormulario(null);
			}

			// Actualizamos pagina pasados en modelo
			int orden = 0;
			for (final PaginaFormulario pag : pFormInt.getPaginas()) {
				orden++;

				if (pag.getId() == null) {
					final JPaginaFormulario jPagina = JPaginaFormulario.fromModel(pag);
					jPagina.setCodigo(pag.getId());
					jPagina.setOrden(orden);
					jPagina.setFormulario(jFormulario);
					jFormulario.getPaginas().add(jPagina);
				} else {
					for (final JPaginaFormulario jPagForm : jFormulario.getPaginas()) {
						if (jPagForm.getCodigo().equals(pag.getId())) {
							jPagForm.setOrden(orden);
							break;
						}
					}
				}

			}

			jModel = jFormulario;
		}

		return jModel;
	}

}
