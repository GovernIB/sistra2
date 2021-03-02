package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.io.Serializable;

/**
 * Campo LOPD.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class CampoLOPD implements Serializable {

	/** Titulo. */
	private String titulo;

	/** Descripción. */
	private String descripcion;

	/** Referencia archivo (se deberá consultar a Catalogo para obtener archivo). */
	private String referenciaArchivo;

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
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para establecer descripcion.
	 * 
	 * @param descripcion
	 *                        descripcion a establecer
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método de acceso a referenciaArchivo.
	 * 
	 * @return referenciaArchivo
	 */
	public String getReferenciaArchivo() {
		return referenciaArchivo;
	}

	/**
	 * Método para establecer referenciaArchivo.
	 * 
	 * @param referenciaArchivo
	 *                              referenciaArchivo a establecer
	 */
	public void setReferenciaArchivo(final String referenciaArchivo) {
		this.referenciaArchivo = referenciaArchivo;
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
