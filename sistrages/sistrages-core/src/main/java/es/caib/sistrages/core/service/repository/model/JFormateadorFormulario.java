package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JFormateadorFormulario
 */
@Entity
@Table(name = "STG_FORFMT")
public class JFormateadorFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORFMT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORFMT_SEQ", sequenceName = "STG_FORFMT_SEQ")
	@Column(name = "FMT_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@Column(name = "FMT_CLASS", nullable = false, length = 500)
	private String classname;

	@Column(name = "FMT_DESCRI", nullable = false)
	private String descripcion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formateadorFormulario")
	private Set<JPlantillaFormulario> plantillasFormulario = new HashSet<JPlantillaFormulario>(0);

	public JFormateadorFormulario() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(final String classname) {
		this.classname = classname;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<JPlantillaFormulario> getPlantillasFormulario() {
		return this.plantillasFormulario;
	}

	public void setPlantillasFormulario(final Set<JPlantillaFormulario> plantillasFormulario) {
		this.plantillasFormulario = plantillasFormulario;
	}

}
