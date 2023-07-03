package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

/**
 * Lista elementos: columna.
 */
@SuppressWarnings("serial")
public class ListaElementosColumna implements Serializable {

	/** Id campo. */
	private String id;

	/** Descripción campo. */
	private String descripcion;

	/** Ancho columna. */
	private int ancho;

	/**
	 * Obtiene id.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece id.
	 * 
	 * @param id
	 *               id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Obtiene descripcion.
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece descripcion.
	 * 
	 * @param descripcion
	 *                        descripcion
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene ancho.
	 * 
	 * @return ancho
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * Establece ancho.
	 * 
	 * @param ancho
	 *                  ancho
	 */
	public void setAncho(final int ancho) {
		this.ancho = ancho;
	}

}
