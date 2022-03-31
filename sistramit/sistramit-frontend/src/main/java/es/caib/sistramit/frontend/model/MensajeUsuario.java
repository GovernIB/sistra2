package es.caib.sistramit.frontend.model;

/**
 * Mensaje a mostrar al usuario.
 *
 * @author Indra
 *
 */
public final class MensajeUsuario {

	/**
	 * Título del mensaje al usuario.
	 */
	private String titulo;

	/**
	 * Texto del mensaje al usuario.
	 */
	private String texto;

	/**
	 * Texto debug.
	 */
	private String debug;

	/**
	 * Constructor.
	 */
	public MensajeUsuario() {
	}

	/**
	 * Constructor.
	 *
	 * @param pTitulo
	 *                    Título
	 * @param pTexto
	 *                    Texto
	 */
	public MensajeUsuario(final String pTitulo, final String pTexto) {
		super();
		titulo = pTitulo;
		texto = pTexto;
	}

	/**
	 * Constructor.
	 *
	 * @param pTitulo
	 *                    Título
	 * @param pTexto
	 *                    Texto
	 */
	public MensajeUsuario(final String pTitulo, final String pTexto, final String pDebug) {
		super();
		titulo = pTitulo;
		texto = pTexto;
		debug = pDebug;
	}

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
	 *                    titulo a establecer
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
	 *                   texto a establecer
	 */
	public void setTexto(final String pTexto) {
		texto = pTexto;
	}

	/**
	 * Método de acceso a debug.
	 * 
	 * @return debug
	 */
	public String getDebug() {
		return debug;
	}

	/**
	 * Método para establecer debug.
	 * 
	 * @param debug
	 *                  debug a establecer
	 */
	public void setDebug(final String debug) {
		this.debug = debug;
	}

}
