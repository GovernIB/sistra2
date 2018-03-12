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

/**
 * JTraduccionLiterales
 */
@Entity
@Table(name = "STG_TRAIDI")
public class JTraduccionLiterales implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_TRAIDI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_TRAIDI_SEQ", sequenceName = "STG_TRAIDI_SEQ")
	@Column(name = "TRI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRI_IDIOMA", nullable = false)
	private JIdioma idioma;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRI_CODTRA", nullable = false)
	private JLiterales traduccion;

	@Column(name = "TRI_LITERA", nullable = false, length = 1000)
	private String literal;

	public JTraduccionLiterales() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JIdioma getIdioma() {
		return this.idioma;
	}

	public void setIdioma(final JIdioma idioma) {
		this.idioma = idioma;
	}

	public JLiterales getTraduccion() {
		return this.traduccion;
	}

	public void setTraduccion(final JLiterales traduccion) {
		this.traduccion = traduccion;
	}

	public String getLiteral() {
		return this.literal;
	}

	public void setLiteral(final String literal) {
		this.literal = literal;
	}

}
