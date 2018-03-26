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
import javax.persistence.UniqueConstraint;

/**
 * JAccionPersonalizada
 */
@Entity
@Table(name = "STG_ACCPER", uniqueConstraints = @UniqueConstraint(columnNames = { "ACP_CODFOR", "ACP_ACCION" }))
public class JAccionPersonalizada implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_ACCPER_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_ACCPER_SEQ", sequenceName = "STG_ACCPER_SEQ")
	@Column(name = "ACP_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACP_CODFOR", nullable = false)
	private JFormulario formulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACP_DESCRI", nullable = false)
	private JLiteral titulo;

	@Column(name = "ACP_ACCION", nullable = false, length = 20)
	private String accion;

	@Column(name = "ACP_VALIDA", nullable = false, precision = 1, scale = 0)
	private boolean validar;

	public JAccionPersonalizada() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFormulario getFormulario() {
		return this.formulario;
	}

	public void setFormulario(final JFormulario formulario) {
		this.formulario = formulario;
	}

	public JLiteral getTitulo() {
		return this.titulo;
	}

	public void setTitulo(final JLiteral titulo) {
		this.titulo = titulo;
	}

	public String getAccion() {
		return this.accion;
	}

	public void setAccion(final String accion) {
		this.accion = accion;
	}

	public boolean isValidar() {
		return this.validar;
	}

	public void setValidar(final boolean validar) {
		this.validar = validar;
	}

}
