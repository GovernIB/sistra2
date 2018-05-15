package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Contenido de un fichero.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ContenidoFichero implements Serializable {

	/**
	 * Nombre fichero con extensión.
	 */
	private String nombre;

	/**
	 * Contenido fichero.
	 */
	private byte[] contenido;

	/**
	 * Constructor.
	 */
	public ContenidoFichero() {
		super();
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre.
	 *
	 * @param pNombre
	 *            nombre a establecer
	 */
	public void setNombre(final String pNombre) {
		nombre = pNombre;
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
	 *            contenido a establecer
	 */
	public void setContenido(final byte[] pContenido) {
		contenido = pContenido;
	}

	/**
	 * Constructor
	 *
	 * @param pNombre
	 * @param pContenido
	 */
	public ContenidoFichero(final String pNombre, final byte[] pContenido) {
		super();
		nombre = pNombre;
		contenido = pContenido;
	}

}
