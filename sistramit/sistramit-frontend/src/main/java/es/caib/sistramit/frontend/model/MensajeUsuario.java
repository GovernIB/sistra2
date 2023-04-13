package es.caib.sistramit.frontend.model;

import es.caib.sistramit.frontend.model.types.TypeEstiloError;

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
	 * Texto del botón continuar.
	 */
	private String textoBoton;

	/**
	 * Estilo error.
	 */
	private TypeEstiloError estiloError;

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
	 * Constructor.
	 *
	 * @param titulo
	 * @param texto
	 * @param textoBoton
	 * @param estiloError
	 */
	public MensajeUsuario(final String titulo, final String texto, final String textoBoton,
			final TypeEstiloError estiloError) {
		super();
		this.titulo = titulo;
		this.texto = texto;
		this.textoBoton = textoBoton;
		this.estiloError = estiloError;
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

	/**
	 * Método de acceso a textoBoton.
	 *
	 * @return textoBoton
	 */
	public String getTextoBoton() {
		return textoBoton;
	}

	/**
	 * Método para establecer textoBoton.
	 *
	 * @param textoBoton
	 *                       textoBoton a establecer
	 */
	public void setTextoBoton(final String textoBoton) {
		this.textoBoton = textoBoton;
	}

	/**
	 * Método de acceso a estiloError.
	 *
	 * @return estiloError
	 */
	public TypeEstiloError getEstiloError() {
		return estiloError;
	}

	/**
	 * Método para establecer estiloError.
	 *
	 * @param estiloError
	 *                        estiloError a establecer
	 */
	public void setEstiloError(final TypeEstiloError estiloError) {
		this.estiloError = estiloError;
	}

}
