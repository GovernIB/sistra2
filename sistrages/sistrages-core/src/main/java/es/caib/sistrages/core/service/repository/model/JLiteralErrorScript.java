package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
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

import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.Script;

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

	@ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "LSC_CODTRA", nullable = false)
	private JLiteral literal;

	@Column(name = "LSC_IDENTI", nullable = false, length = 20)
	private String identificador;

	public JLiteralErrorScript() {
		// Constructor vacio
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the script
	 */
	public JScript getScript() {
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 */
	public void setScript(final JScript script) {
		this.script = script;
	}

	/**
	 * @return the literal
	 */
	public JLiteral getLiteral() {
		return literal;
	}

	/**
	 * @param literal
	 *            the literal to set
	 */
	public void setLiteral(final JLiteral literal) {
		this.literal = literal;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * ToModel
	 *
	 * @return
	 */
	public LiteralScript toModel() {
		final LiteralScript literalScript = new LiteralScript();
		literalScript.setCodigo(codigo);
		if (literal != null) {
			literalScript.setLiteral(literal.toModel());
		}
		literalScript.setIdentificador(identificador);
		return literalScript;
	}

	/**
	 * FromModel
	 *
	 * @param model
	 * @param jscript
	 * @return
	 */
	public static JLiteralErrorScript fromModel(final LiteralScript model, final Script jscript) {
		JLiteralErrorScript jModel = null;
		if (model != null) {
			jModel = new JLiteralErrorScript();
			jModel.setCodigo(model.getCodigo());
			jModel.setIdentificador(model.getIdentificador());
			jModel.setScript(JScript.fromModel(jscript));
			jModel.setLiteral(JLiteral.fromModel(model.getLiteral()));
		}
		return jModel;
	}

}
