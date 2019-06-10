package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.FormateadorFormulario;

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

	@Column(name = "FMT_IDENTI", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "FMT_CLASS", nullable = false, length = 500)
	private String classname;

	@Column(name = "FMT_DESCRI", nullable = false)
	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "FMT_CODENT", nullable = false)
	private JEntidad entidad;

	@Column(name = "FMT_DEFECT", nullable = false, precision = 1, scale = 0)
	private boolean generico;

	@Column(name = "FMT_BLOCK", nullable = false, precision = 1, scale = 0)
	private boolean desactivarPersonalizacion;

	public JEntidad getEntidad() {
		return entidad;
	}

	public void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
	}

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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the generico
	 */
	public boolean isGenerico() {
		return generico;
	}

	/**
	 * @param generico
	 *            the generico to set
	 */
	public void setGenerico(final boolean generico) {
		this.generico = generico;
	}

	/**
	 * @return the desactivarPersonalizacion
	 */
	public boolean isDesactivarPersonalizacion() {
		return desactivarPersonalizacion;
	}

	/**
	 * @param desactivarPersonalizacion
	 *            the desactivarPersonalizacion to set
	 */
	public void setDesactivarPersonalizacion(final boolean desactivarPersonalizacion) {
		this.desactivarPersonalizacion = desactivarPersonalizacion;
	}

	/**
	 * ToModel.
	 *
	 * @return FormateadorFormulario
	 */
	public FormateadorFormulario toModel() {
		final FormateadorFormulario fmt = new FormateadorFormulario();
		fmt.setCodigo(codigo);
		fmt.setIdentificador(identificador);
		fmt.setClassname(classname);
		fmt.setDescripcion(descripcion);
		fmt.setGenerico(generico);
		fmt.setDesactivarPersonalizacion(desactivarPersonalizacion);
		return fmt;
	}

	/**
	 * FromModel
	 *
	 * @param model
	 * @return JFormateadorFormulario
	 */
	public static JFormateadorFormulario fromModel(final FormateadorFormulario model) {
		JFormateadorFormulario jModel = null;
		if (model != null) {
			jModel = new JFormateadorFormulario();
			jModel.setCodigo(model.getCodigo());
			jModel.setIdentificador(model.getIdentificador());
			jModel.setClassname(model.getClassname());
			jModel.setDescripcion(model.getDescripcion());
			jModel.setGenerico(model.isGenerico());
			jModel.setDesactivarPersonalizacion(model.isDesactivarPersonalizacion());
		}
		return jModel;
	}

}
