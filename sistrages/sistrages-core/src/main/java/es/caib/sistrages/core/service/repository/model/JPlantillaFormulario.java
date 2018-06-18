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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;

/**
 * JPlantillaFormulario
 */
@Entity
@Table(name = "STG_FORPLT")
public class JPlantillaFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORPLT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORPLT_SEQ", sequenceName = "STG_FORPLT_SEQ")
	@Column(name = "PLT_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLT_CODFMT", nullable = false)
	private JFormateadorFormulario formateadorFormulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLT_CODFOR", nullable = false)
	private JFormulario formulario;

	@Column(name = "PLT_DESCRI", nullable = false)
	private String descripcion;

	@Column(name = "PLT_DEFECT", nullable = false, precision = 1, scale = 0)
	private boolean porDefecto;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "plantillaFormulario")
	private Set<JPlantillaIdiomaFormulario> plantillaIdiomaFormulario = new HashSet<>(0);

	public JPlantillaFormulario() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFormateadorFormulario getFormateadorFormulario() {
		return this.formateadorFormulario;
	}

	public void setFormateadorFormulario(final JFormateadorFormulario formateadorFormulario) {
		this.formateadorFormulario = formateadorFormulario;
	}

	public JFormulario getFormulario() {
		return this.formulario;
	}

	public void setFormulario(final JFormulario formulario) {
		this.formulario = formulario;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isPorDefecto() {
		return this.porDefecto;
	}

	public void setPorDefecto(final boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	public Set<JPlantillaIdiomaFormulario> getPlantillaIdiomaFormulario() {
		return this.plantillaIdiomaFormulario;
	}

	public void setPlantillaIdiomaFormulario(final Set<JPlantillaIdiomaFormulario> plantillaIdiomaFormulario) {
		this.plantillaIdiomaFormulario = plantillaIdiomaFormulario;
	}

	public PlantillaFormulario toModel() {
		final PlantillaFormulario plantilla = new PlantillaFormulario();
		plantilla.setCodigo(codigo);
		plantilla.setDescripcion(descripcion);
		plantilla.setPorDefecto(porDefecto);

		if (formateadorFormulario != null) {
			plantilla.setIdFormateadorFormulario(formateadorFormulario.getCodigo());
		}
		return plantilla;
	}

	public static JPlantillaFormulario fromModel(final PlantillaFormulario model) {
		JPlantillaFormulario jModel = null;
		if (model != null) {
			jModel = new JPlantillaFormulario();
			if (model.getCodigo() != null) {
				jModel.setCodigo(model.getCodigo());
			}
			jModel.setDescripcion(model.getDescripcion());
			jModel.setPorDefecto(model.isPorDefecto());

			if (model.getIdFormateadorFormulario() != null) {
				jModel.setFormateadorFormulario(JFormateadorFormulario
						.fromModel(new FormateadorFormulario(model.getIdFormateadorFormulario())));
			}

		}
		return jModel;
	}

}
