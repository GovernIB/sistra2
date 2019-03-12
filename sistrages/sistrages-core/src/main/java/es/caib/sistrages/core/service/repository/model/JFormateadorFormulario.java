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
	private boolean porDefecto;

	@Column(name = "FMT_BLOCK", nullable = false, precision = 1, scale = 0)
	private boolean bloquear;

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
	 * @return the porDefecto
	 */
	public final boolean isPorDefecto() {
		return porDefecto;
	}

	/**
	 * @param porDefecto
	 *            the porDefecto to set
	 */
	public final void setPorDefecto(final boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	/**
	 * @return the bloquear
	 */
	public final boolean isBloquear() {
		return bloquear;
	}

	/**
	 * @param bloquear
	 *            the bloquear to set
	 */
	public final void setBloquear(final boolean bloquear) {
		this.bloquear = bloquear;
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
		fmt.setPorDefecto(porDefecto);
		fmt.setBloquear(bloquear);
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
			jModel.setPorDefecto(model.isPorDefecto());
			jModel.setBloquear(model.isBloquear());
		}
		return jModel;
	}

}
