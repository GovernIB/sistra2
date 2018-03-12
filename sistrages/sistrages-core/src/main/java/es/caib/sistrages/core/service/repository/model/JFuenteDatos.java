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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * JFuenteDatos
 */
@Entity
@Table(name = "STG_FUEDAT", uniqueConstraints = @UniqueConstraint(columnNames = "FUE_IDENT"))
public class JFuenteDatos implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FUEDAT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FUEDAT_SEQ", sequenceName = "STG_FUEDAT_SEQ")
	@Column(name = "FUE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@Column(name = "FUE_AMBITO", nullable = false, length = 1)
	private String ambito;

	@Column(name = "FUE_IDENT", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "FUE_DESCR", nullable = false)
	private String descripcion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_AREFUE", joinColumns = {
			@JoinColumn(name = "FUA_CODFUE", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "FUA_CODAREA", nullable = false, updatable = false) })
	private Set<JArea> areas = new HashSet<JArea>(0);

	public JFuenteDatos() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public String getAmbito() {
		return this.ambito;
	}

	public void setAmbito(final String ambito) {
		this.ambito = ambito;
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

	public Set<JArea> getAreas() {
		return this.areas;
	}

	public void setAreas(final Set<JArea> areas) {
		this.areas = areas;
	}

}
