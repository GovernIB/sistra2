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

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;

/**
 * JConfiguracionAutenticacion
 */
@Entity
@Table(name = "STG_CONFAUT")
public class JConfiguracionAutenticacion implements IModelApi {

 	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_CONFAUT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_CONFAUT_SEQ", sequenceName = "STG_CONFAUT_SEQ")
	@Column(name = "CAU_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CAU_CODARE", nullable = false)
	private JArea area;

	@Column(name = "CAU_IDENTI", nullable = false, length = 20)
	private String identificador;

	@Column(name = "CAU_DESCR", nullable = false, length = 255)
	private String descripcion;

	@Column(name = "CAU_USER", nullable = false, length = 50)
	private String usuario;

	@Column(name = "CAU_PASS", nullable = false, length = 50)
	private String password;

	public JConfiguracionAutenticacion() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}


	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the area
	 */
	public JArea getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(JArea area) {
		this.area = area;
	}

	public ConfiguracionAutenticacion toModel() {
		final ConfiguracionAutenticacion confAutenticacion = new ConfiguracionAutenticacion();
		confAutenticacion.setCodigo(codigo);
		confAutenticacion.setIdentificador(this.getIdentificador());
		confAutenticacion.setDescripcion(this.getDescripcion());
		confAutenticacion.setUsuario(this.getUsuario());
		confAutenticacion.setPassword(this.getPassword());
		return confAutenticacion;
	}

	public static JConfiguracionAutenticacion fromModel(final ConfiguracionAutenticacion model) {
		JConfiguracionAutenticacion jModel = null;
		if (model != null) {
			jModel = new JConfiguracionAutenticacion();
			jModel.setCodigo(model.getCodigo());
			jModel.setIdentificador(model.getIdentificador());
			jModel.setDescripcion(model.getDescripcion());
			jModel.setUsuario(model.getUsuario());
			jModel.setPassword(model.getPassword());
		}
		return jModel;
	}

	/** Mergea **/
	public void merge(final ConfiguracionAutenticacion pConfAut) {
		this.setDescripcion(pConfAut.getDescripcion());
		this.setIdentificador(pConfAut.getIdentificador());
		this.setUsuario(pConfAut.getUsuario());
		this.setPassword(pConfAut.getPassword());
	}

}
