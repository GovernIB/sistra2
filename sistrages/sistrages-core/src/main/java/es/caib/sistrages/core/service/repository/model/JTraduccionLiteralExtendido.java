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

import es.caib.sistrages.core.api.model.Traduccion;

/**
 * JTraduccionLiterales
 */
@Entity
@Table(name = "STG_TREIDI")
public class JTraduccionLiteralExtendido implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_TREIDI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_TREIDI_SEQ", sequenceName = "STG_TREIDI_SEQ")
	@Column(name = "TEI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEI_IDIOMA", nullable = false)
	private JIdioma idioma;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEI_CODTRA", nullable = false)
	private JLiteralExtendido traduccion;

	@Column(name = "TEI_LITERA", nullable = false, length = 4000)
	private String literal;

	/**
	 * Constructor vacio.
	 */
	public JTraduccionLiteralExtendido() {
		// Constructor vacio.
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JIdioma getIdioma() {
		return this.idioma;
	}

	public void setIdioma(final JIdioma idioma) {
		this.idioma = idioma;
	}

	public JLiteralExtendido getTraduccion() {
		return this.traduccion;
	}

	public void setTraduccion(final JLiteralExtendido traduccion) {
		this.traduccion = traduccion;
	}

	public String getLiteral() {
		return this.literal;
	}

	public void setLiteral(final String literal) {
		this.literal = literal;
	}

	public Traduccion toModel() {
		final Traduccion traduc = new Traduccion();
		traduc.setCodigo(this.codigo);
		traduc.setIdioma(this.idioma.getIdentificador());
		traduc.setLiteral(this.literal);
		return traduc;
	}

	public static JTraduccionLiteralExtendido fromModel(final Traduccion traduc) {
		JTraduccionLiteralExtendido jModel = null;
		if (traduc != null) {
			jModel = new JTraduccionLiteralExtendido();
			jModel.setCodigo(traduc.getCodigo());
			jModel.setIdioma(new JIdioma(traduc.getIdioma()));
			jModel.setLiteral(traduc.getLiteral());
		}
		return jModel;
	}

	/**
	 * Clonar.
	 *
	 * @return
	 */
	public static JTraduccionLiteralExtendido clonar(final JTraduccionLiteralExtendido origTraduccionLiteral) {
		JTraduccionLiteralExtendido jtraduccionLiteral = null;
		if (origTraduccionLiteral != null) {
			jtraduccionLiteral = new JTraduccionLiteralExtendido();
			jtraduccionLiteral.setCodigo(null);
			jtraduccionLiteral.setIdioma(origTraduccionLiteral.getIdioma());
			jtraduccionLiteral.setLiteral(origTraduccionLiteral.getLiteral());
		}
		return jtraduccionLiteral;
	}
}
