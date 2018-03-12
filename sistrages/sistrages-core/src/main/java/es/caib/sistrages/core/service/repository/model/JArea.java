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

/**
 * JArea
 */
@Entity
@Table(name = "STG_AREA", uniqueConstraints = @UniqueConstraint(columnNames = "ARE_IDENTI"))
public class JArea implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_AREA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_AREA_SEQ", sequenceName = "STG_AREA_SEQ")
	@Column(name = "ARE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARE_CODENT", nullable = false)
	private JEntidades entidad;

	@Column(name = "ARE_IDENTI", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "ARE_DESCR", nullable = false)
	private String descripcion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_AREFUE", joinColumns = {
			@JoinColumn(name = "FUA_CODAREA", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "FUA_CODFUE", nullable = false, updatable = false) })
	private Set<JFuenteDatos> fuentesDatos = new HashSet<JFuenteDatos>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_AREDOM", joinColumns = {
			@JoinColumn(name = "DMA_CODARE", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DMA_CODDOM", nullable = false, updatable = false) })
	private Set<JDominio> dominios = new HashSet<JDominio>(0);

	public JArea() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JEntidades getEntidad() {
		return this.entidad;
	}

	public void setEntidad(final JEntidades entidad) {
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

	public Set<JFuenteDatos> getFuentesDatos() {
		return this.fuentesDatos;
	}

	public void setFuentesDatos(final Set<JFuenteDatos> fuentesDatos) {
		this.fuentesDatos = fuentesDatos;
	}

	public Set<JDominio> getStgDominis() {
		return this.dominios;
	}

	public void setStgDominis(final Set<JDominio> dominios) {
		this.dominios = dominios;
	}

}
