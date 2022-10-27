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

import es.caib.sistrages.core.api.model.SeccionReutilizable;

/**
 * JSeccionReutilizable
 */
@Entity
@Table(name = "STG_SECREU")
public class JSeccionReutilizable implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_SECREU_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_SECREU_SEQ", sequenceName = "STG_SECREU_SEQ")
	@Column(name = "SREU_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SREU_CODENT", nullable = false)
	private JEntidad entidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SREU_CODFOR", nullable = false)
	private JFormulario  formularioAsociado;

	@Column(name = "SREU_IDENTI", length = 3)
	private String identificador;

	@Column(name = "SREU_DESCR", length = 255)
	private String descripcion;

	@Column(name = "SREU_BLOQ")
	private boolean bloqueado;

	@Column(name = "SREU_ACTIVA")
	private boolean activa;

	@Column(name = "SREU_BLOQID", length = 255)
	private String bloqueadoUsuario;

	@Column(name = "SREU_RELEAS")
	private int release;

	@Column(name = "SREU_HUELLA", length = 20)
	private String huella;

	public JSeccionReutilizable() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the entidad
	 */
	public JEntidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(JEntidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the formularioAsociado
	 */
	public JFormulario getFormularioAsociado() {
		return formularioAsociado;
	}

	/**
	 * @param formularioAsociado the formularioAsociado to set
	 */
	public void setFormularioAsociado(JFormulario formularioAsociado) {
		this.formularioAsociado = formularioAsociado;
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
	public void setIdentificador(String identificador) {
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
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the bloqueado
	 */
	public boolean isBloqueado() {
		return bloqueado;
	}

	/**
	 * @param bloqueado the bloqueado to set
	 */
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	/**
	 * @return the bloqueadoUsuario
	 */
	public String getBloqueadoUsuario() {
		return bloqueadoUsuario;
	}

	/**
	 * @param bloqueadoUsuario the bloqueadoUsuario to set
	 */
	public void setBloqueadoUsuario(String bloqueadoUsuario) {
		this.bloqueadoUsuario = bloqueadoUsuario;
	}

	/**
	 * @return the release
	 */
	public int getRelease() {
		return release;
	}

	/**
	 * @param release the release to set
	 */
	public void setRelease(int release) {
		this.release = release;
	}

	/**
	 * @return the huella
	 */
	public String getHuella() {
		return huella;
	}

	/**
	 * @param huella the huella to set
	 */
	public void setHuella(String huella) {
		this.huella = huella;
	}

	/**
	 * @return the activa
	 */
	public boolean isActiva() {
		return activa;
	}

	/**
	 * @param activa the activa to set
	 */
	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public SeccionReutilizable toModel() {
		SeccionReutilizable seccion = new SeccionReutilizable();
		seccion.setCodigo(this.getCodigo());
		seccion.setIdEntidad(this.getEntidad().getCodigo());
		seccion.setIdentificadorEntidad(this.getEntidad().getIdentificador());
		seccion.setIdFormularioAsociado(this.getFormularioAsociado().getCodigo());
		seccion.setBloqueado(this.isBloqueado());
		seccion.setBloqueadoUsuario(this.getBloqueadoUsuario());
		seccion.setDescripcion(this.getDescripcion());
		seccion.setHuella(this.getHuella());
		seccion.setIdentificador(this.getIdentificador());
		seccion.setRelease(this.getRelease());
		seccion.setActivado(this.isActiva());

		return seccion;
	}

	public static JSeccionReutilizable fromModel(final SeccionReutilizable model) {
		JSeccionReutilizable jModel = null;
		if (model != null) {
			jModel = new JSeccionReutilizable();
			jModel.setCodigo(model.getCodigo());
			jModel.setIdentificador(model.getIdentificador());
			jModel.setBloqueado(model.isBloqueado());
			jModel.setBloqueadoUsuario(model.getBloqueadoUsuario());
			jModel.setDescripcion(model.getDescripcion());
			jModel.setHuella(model.getHuella());
			jModel.setRelease(model.getRelease());
			jModel.setActiva(model.isActivado());
		}
		return jModel;
	}

	public void merge(final SeccionReutilizable model) {
		this.setIdentificador(model.getIdentificador());
		this.setBloqueado(model.isBloqueado());
		this.setBloqueadoUsuario(model.getBloqueadoUsuario());
		this.setDescripcion(model.getDescripcion());
		this.setHuella(model.getHuella());
		this.setRelease(model.getRelease());
		this.setActiva(model.isActivado());
	}

}
