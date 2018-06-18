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

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;

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

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "FOR_SCRPLT")
	private JScript scriptPlantilla;

	@ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "FOR_CABTXT")
	private JLiteral textoCabecera;

	@Column(name = "FOR_ACCPER", nullable = false, precision = 1, scale = 0)
	private boolean permitirAccionesPersonalizadas;

	@Column(name = "FOR_CABFOR", nullable = false, precision = 1, scale = 0)
	private boolean mostrarCabecera;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<JPaginaFormulario> paginas = new HashSet<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<JPlantillaFormulario> plantillas = new HashSet<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario")
	private Set<JAccionPersonalizada> accionesPersonalizadas = new HashSet<>(0);

	public JFormulario() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
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

	public boolean isMostrarCabecera() {
		return this.mostrarCabecera;
	}

	public void setMostrarCabecera(final boolean cabeceraFormulario) {
		this.mostrarCabecera = cabeceraFormulario;
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

	public Set<JPlantillaFormulario> getPlantillas() {
		return plantillas;
	}

	public void setPlantillas(final Set<JPlantillaFormulario> plantillas) {
		this.plantillas = plantillas;
	}

	public FormularioInterno toModel() {
		final FormularioInterno formulario = new FormularioInterno();
		formulario.setId(codigo);
		formulario.setPermitirAccionesPersonalizadas(permitirAccionesPersonalizadas);
		if (scriptPlantilla != null) {
			formulario.setScriptPlantilla(scriptPlantilla.toModel());
		}
		formulario.setMostrarCabecera(mostrarCabecera);
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
			jModel.setMostrarCabecera(model.isMostrarCabecera());
			jModel.setTextoCabecera(JLiteral.fromModel(model.getTextoCabecera()));
		}
		return jModel;
	}

	public static JFormulario createDefault(final JLiteral pJTextoCabecera) {
		final JFormulario jForm = new JFormulario();

		jForm.setMostrarCabecera(true);
		jForm.setTextoCabecera(pJTextoCabecera);
		jForm.setPermitirAccionesPersonalizadas(false);

		return jForm;
	}

	public static JFormulario mergePaginasModel(final JFormulario jFormulario, final FormularioInterno pFormInt) {

		if (jFormulario != null && !jFormulario.getPaginas().isEmpty() && pFormInt != null
				&& !pFormInt.getPaginas().isEmpty()) {
			// Borrar paginas no pasados en modelo
			final List<JPaginaFormulario> borrar = new ArrayList<>();
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

		}

		return jFormulario;
	}

	public static JFormulario mergePlantillasModel(final JFormulario jFormulario, final FormularioInterno pFormInt) {

		if (jFormulario != null && pFormInt != null && !pFormInt.getPlantillas().isEmpty()) {
			// Borrar paginas no pasados en modelo
			final List<JPlantillaFormulario> borrar = new ArrayList<>();
			final List<Long> listPlantillas = new ArrayList<>();

			for (final PlantillaFormulario pag : pFormInt.getPlantillas()) {
				if (pag.getCodigo() != null) {
					listPlantillas.add(pag.getCodigo());
				}
			}

			for (final JPlantillaFormulario jPlantilla : jFormulario.getPlantillas()) {
				if (!listPlantillas.contains(jPlantilla.getCodigo())) {
					borrar.add(jPlantilla);
				}
			}

			for (final JPlantillaFormulario jPlantilla : borrar) {
				jFormulario.getPlantillas().remove(jPlantilla);
				jPlantilla.setFormulario(null);
			}

			// Actualizamos pagina pasados en modelo
			for (final PlantillaFormulario plantilla : pFormInt.getPlantillas()) {
				if (plantilla.getCodigo() == null || plantilla.getCodigo() < 0) {
					final JPlantillaFormulario jPlantilla = JPlantillaFormulario.fromModel(plantilla);
					jPlantilla.setCodigo(null);
					jPlantilla.setFormulario(jFormulario);
					jFormulario.getPlantillas().add(jPlantilla);
				} else {
					for (final JPlantillaFormulario jPlantilla : jFormulario.getPlantillas()) {
						if (jPlantilla.getCodigo().equals(plantilla.getCodigo())) {
							// TODO:completar
							jPlantilla.setDescripcion(plantilla.getDescripcion());
							jPlantilla.setPorDefecto(plantilla.isPorDefecto());

							if (!jPlantilla.getFormateadorFormulario().getCodigo()
									.equals(plantilla.getIdFormateadorFormulario()))
								jPlantilla.setFormateadorFormulario(JFormateadorFormulario
										.fromModel(new FormateadorFormulario(plantilla.getIdFormateadorFormulario())));
							break;
						}
					}
				}

			}

		}

		return jFormulario;
	}

	/**
	 * Clonar
	 *
	 * @param formulario
	 * @return
	 */
	public static JFormulario clonar(final JFormulario formulario) {
		// TODO Auto-generated method stub
		return null;
	}

}
