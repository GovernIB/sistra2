package es.caib.sistramit.frontend.model;

/**
 * Mensaje a mostrar al usuario.
 *
 * @author Indra
 *
 */
public final class MensajeUsuario {
	/**
	 * Constructor.
	 */
	public MensajeUsuario() {
	}

	/**
	 * Constructor.
	 *
	 * @param pTitulo
	 *            Título
	 * @param pTexto
	 *            Texto
	 */
	public MensajeUsuario(final String pTitulo, final String pTexto) {
		super();
		titulo = pTitulo;
		texto = pTexto;
	}

	/**
	 * Título del mensaje al usuario.
	 */
	private String titulo;

	/**
	 * Texto del mensaje al usuario.
	 */
	private String texto;

	/**
	 * Título del mensaje al usuario.
	 *
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Título del mensaje al usuario.
	 *
	 * @param pTitulo
	 *            titulo a establecer
	 */
	public void setTitulo(final String pTitulo) {
		titulo = pTitulo;
	}

	/**
	 * Texto del mensaje al usuario.
	 *
	 * @return texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Texto del mensaje al usuario.
	 *
	 * @param pTexto
	 *            texto a establecer
	 */
	public void setTexto(final String pTexto) {
		texto = pTexto;
	}

}
