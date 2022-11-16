package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Literal Script.
 */

public class LiteralScript extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Identificador. */
	private String identificador;

	/** Literal. */
	private Literal literal;

	/**
	 * Método de acceso a codigo.
	 *
	 * @return codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 *
	 * @param codigo
	 *            codigo a establecer
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método de acceso a identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer identificador.
	 *
	 * @param identificador
	 *            identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * Método de acceso a literal.
	 *
	 * @return literal
	 */
	public Literal getLiteral() {
		return literal;
	}

	/**
	 * Método para establecer literal.
	 *
	 * @param literal
	 *            literal a establecer
	 */
	public void setLiteral(final Literal literal) {
		this.literal = literal;
	}

	/**
	 * Clonar.
	 *
	 * @param mensaje
	 * @return
	 */
	public void limpiarIds() {
		this.setCodigo(null);
		if (this.literal != null) {
			this.literal.setCodigo(null);
			for (final Traduccion trad : this.getLiteral().getTraducciones()) {
				trad.setCodigo(null);
			}
		}
	}


	/**
	 * Clonar.
	 *
	 * @param mensaje
	 * @return
	 */
	public static LiteralScript clonar(final LiteralScript iLiteralScript) {
		LiteralScript literalScript = null;
		if (iLiteralScript != null) {
			literalScript = new LiteralScript();
			literalScript.setIdentificador(iLiteralScript.getIdentificador());
			iLiteralScript.getLiteral().setCodigo(null);
			final Literal literal = new Literal();
			final List<Traduccion> traducciones = new ArrayList<>();
			for (final Traduccion trad : iLiteralScript.getLiteral().getTraducciones()) {
				final Traduccion traduccion = new Traduccion();
				traduccion.setIdioma(trad.getIdioma());
				traduccion.setLiteral(trad.getLiteral());
				traducciones.add(traduccion);
			}
			literal.setTraducciones(traducciones);
			literalScript.setLiteral(literal);
		}
		return literalScript;
	}

}
