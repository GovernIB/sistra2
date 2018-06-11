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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.Area;

/**
 * JArea
 */
@Entity
@Table(name = "STG_AREA", uniqueConstraints = @UniqueConstraint(columnNames = "ARE_IDENTI"))
public class JArea implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_AREA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_AREA_SEQ", sequenceName = "STG_AREA_SEQ")
	@Column(name = "ARE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARE_CODENT", nullable = false)
	private JEntidad entidad;

	@Column(name = "ARE_IDENTI", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "ARE_DESCR", nullable = false)
	private String descripcion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_AREDOM", joinColumns = {
			@JoinColumn(name = "DMA_CODARE", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DMA_CODDOM", nullable = false, updatable = false) })
	private Set<JDominio> dominios = new HashSet<>(0);

	/** Constructor. **/
	public JArea() {
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

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<JDominio> getDominios() {
		return this.dominios;
	}

	public void setDominios(final Set<JDominio> dominios) {
		this.dominios = dominios;
	}

	public Area toModel() {
		final Area area = new Area();
		area.setCodigo(this.codigo);
		area.setDescripcion(this.descripcion);
		area.setIdentificador(this.identificador);
		return area;
	}

	public static JArea fromModel(final Area area) {
		final JArea jArea = new JArea();
		if (area != null) {
			jArea.setCodigo(area.getCodigo());
			jArea.setIdentificador(area.getIdentificador());
			jArea.setDescripcion(area.getDescripcion());
		}
		return jArea;
	}

}
