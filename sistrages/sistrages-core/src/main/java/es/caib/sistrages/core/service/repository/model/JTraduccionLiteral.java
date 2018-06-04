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
@Table(name = "STG_TRAIDI")
public class JTraduccionLiteral implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_TRAIDI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_TRAIDI_SEQ", sequenceName = "STG_TRAIDI_SEQ")
	@Column(name = "TRI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TRI_IDIOMA", nullable = false)
	private JIdioma idioma;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TRI_CODTRA", nullable = false)
	private JLiteral traduccion;

	@Column(name = "TRI_LITERA", nullable = false, length = 1000)
	private String literal;

	/**
	 * Constructor vacio.
	 */
	public JTraduccionLiteral() {
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

	public JLiteral getTraduccion() {
		return this.traduccion;
	}

	public void setTraduccion(final JLiteral traduccion) {
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
		traduc.setId(this.codigo);
		traduc.setIdioma(this.idioma.getIdentificador());
		traduc.setLiteral(this.literal);
		return traduc;
	}

	public static JTraduccionLiteral fromModel(final Traduccion traduc) {
		JTraduccionLiteral jModel = null;
		if (traduc != null) {
			jModel = new JTraduccionLiteral();
			jModel.setCodigo(traduc.getId());
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
	public static JTraduccionLiteral clonar(final JTraduccionLiteral origTraduccionLiteral) {
		JTraduccionLiteral jtraduccionLiteral = null;
		if (origTraduccionLiteral != null) {
			jtraduccionLiteral = new JTraduccionLiteral();
			jtraduccionLiteral.setCodigo(null);
			jtraduccionLiteral.setIdioma(origTraduccionLiteral.getIdioma());
			jtraduccionLiteral.setLiteral(origTraduccionLiteral.getLiteral());
		}
		return jtraduccionLiteral;
	}
}
