package es.caib.sistramit.core.api.model.flujo;

/**
 * Campo LOPD.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class LopdCampo implements ModelApi {

	/** Titulo. */
	private String titulo;

	/** Descripción. */
	private String texto;

	/** Enlace (url de acceso). */
	private String enlace;

	/**
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param titulo
	 *                   titulo a establecer
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Método de acceso a descripcion.
	 *
	 * @return descripcion
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Método para establecer descripcion.
	 *
	 * @param descripcion
	 *                        descripcion a establecer
	 */
	public void setTexto(final String descripcion) {
		this.texto = descripcion;
	}

	/**
	 * Método de acceso a enlace.
	 *
	 * @return enlace
	 */
	public String getEnlace() {
		return enlace;
	}

	/**
	 * Método para establecer enlace.
	 *
	 * @param enlace
	 *                   enlace a establecer
	 */
	public void setEnlace(final String enlace) {
		this.enlace = enlace;
	}

}
