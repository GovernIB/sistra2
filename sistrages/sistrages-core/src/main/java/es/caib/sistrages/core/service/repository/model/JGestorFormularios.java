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
import javax.persistence.UniqueConstraint;

/**
 * JGestorFormularios
 */
@Entity
@Table(name = "STG_GESFOR", uniqueConstraints = @UniqueConstraint(columnNames = "GFE_IDENTI"))
public class JGestorFormularios implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_GESFOR_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_GESFOR_SEQ", sequenceName = "STG_GESFOR_SEQ")
	@Column(name = "GFE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GFE_CODENT", nullable = false)
	private JEntidad entidad;

	@Column(name = "GFE_IDENTI", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "GFE_DESCR", nullable = false)
	private String descripcion;

	@Column(name = "GFE_URL", nullable = false, length = 100)
	private String urlGestorFormularios;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gestorFormulario")
	private Set<JFormularioTramite> formulariosTramite = new HashSet<JFormularioTramite>(0);

	public JGestorFormularios() {
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

	public String getUrlGestorFormularios() {
		return this.urlGestorFormularios;
	}

	public void setUrlGestorFormularios(final String urlGestorFormularios) {
		this.urlGestorFormularios = urlGestorFormularios;
	}

	public Set<JFormularioTramite> getFormulariosTramite() {
		return this.formulariosTramite;
	}

	public void setFormulariosTramite(final Set<JFormularioTramite> formulariosTramite) {
		this.formulariosTramite = formulariosTramite;
	}

}
