package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeFormatoCaptcha;

/**
 *
 * Imagen captcha.
 *
 * @author Indra
 *
 */
public final class Captcha {

	/**
	 * Constructor
	 *
	 * @param tipo
	 * @param contenido
	 */
	public Captcha(final TypeFormatoCaptcha tipo, final byte[] contenido) {
		super();
		this.tipo = tipo;
		this.contenido = contenido;
		switch (tipo) {
		case IMAGEN:
			this.fichero = "captcha.png";
			break;
		case SONIDO:
			this.fichero = "captcha.wav";
			break;
		default:
			break;
		}
	}

	/**
	 * Constructor
	 */
	public Captcha() {
		super();
	}

	/**
	 * Tipo captcha.
	 */
	private TypeFormatoCaptcha tipo;

	/**
	 * Fichero.
	 */
	private String fichero;

	/**
	 * Datos fichero.
	 */
	private byte[] contenido;

	/**
	 * Método de acceso a fichero.
	 *
	 * @return fichero
	 */
	public String getFichero() {
		return fichero;
	}

	/**
	 * Método para establecer fichero.
	 *
	 * @param pFichero
	 *                     fichero a establecer
	 */
	public void setFichero(final String pFichero) {
		fichero = pFichero;
	}

	/**
	 * Método de acceso a contenido.
	 *
	 * @return contenido
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * Método para establecer contenido.
	 *
	 * @param pContenido
	 *                       contenido a establecer
	 */
	public void setContenido(final byte[] pContenido) {
		contenido = pContenido;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypeFormatoCaptcha getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo
	 *                 tipo a establecer
	 */
	public void setTipo(final TypeFormatoCaptcha tipo) {
		this.tipo = tipo;
	}

}
