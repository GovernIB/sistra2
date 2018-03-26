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
 * JLiteraresErrorScript
 */
@Entity
@Table(name = "STG_LITSCR", uniqueConstraints = @UniqueConstraint(columnNames = { "LSC_CODSCR", "LSC_IDENTI" }))
public class JLiteralErrorScript implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_LITSCR_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_LITSCR_SEQ", sequenceName = "STG_LITSCR_SEQ")
	@Column(name = "LSC_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LSC_CODSCR", nullable = false)
	private JScript script;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LSC_CODTRA", nullable = false)
	private JLiteral literal;

	@Column(name = "LSC_IDENTI", nullable = false, length = 20)
	private String identificador;

	public JLiteralErrorScript() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JScript getScript() {
		return this.script;
	}

	public void setScript(final JScript script) {
		this.script = script;
	}

	public JLiteral getLiteral() {
		return this.literal;
	}

	public void setLiteral(final JLiteral literal) {
		this.literal = literal;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

}
