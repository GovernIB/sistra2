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

import es.caib.sistrages.core.api.model.GestorExternoFormularios;

/**
 * JFormularioExterno
 */
@Entity
@Table(name = "STG_GESFOR")
public class JGestorExternoFormularios implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_GESFOR_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_GESFOR_SEQ", sequenceName = "STG_GESFOR_SEQ")
	@Column(name = "GFE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "GFE_CODENT", nullable = false)
	private JEntidad entidad;

	@Column(name = "GFE_IDENTI", nullable = false, length = 20)
	private String identificador;

	@Column(name = "GFE_DESCR", nullable = false, length = 255)
	private String descripcion;

	@Column(name = "GFE_URL", nullable = false, length = 100)
	private String url;

	public JGestorExternoFormularios() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JEntidad getEntidad() {
		return this.entidad;
	}

	public void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	public GestorExternoFormularios toModel() {
		final GestorExternoFormularios formExterno = new GestorExternoFormularios();
		formExterno.setCodigo(codigo);
		formExterno.setIdentificador(this.getIdentificador());
		formExterno.setDescripcion(this.getDescripcion());
		formExterno.setUrl(this.getUrl());
		return formExterno;
	}

	public static JGestorExternoFormularios fromModel(final GestorExternoFormularios model) {
		JGestorExternoFormularios jModel = null;
		if (model != null) {
			jModel = new JGestorExternoFormularios();
			jModel.setCodigo(model.getCodigo());
			jModel.setIdentificador(model.getIdentificador());
			jModel.setDescripcion(model.getDescripcion());
			jModel.setUrl(model.getUrl());
		}
		return jModel;
	}

	/** Mergea **/
	public void merge(final GestorExternoFormularios pFormularioExterno) {
		this.setDescripcion(pFormularioExterno.getDescripcion());
		this.setUrl(pFormularioExterno.getUrl());
		this.setIdentificador(pFormularioExterno.getIdentificador());
	}

}
