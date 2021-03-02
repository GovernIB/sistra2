package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.io.Serializable;

/**
 * Archivo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ArchivoCP implements Serializable {

	/** Titulo. */
	private String filename;

	/** Contenido. */
	private byte[] content;

	/**
	 * Método de acceso a filename.
	 * 
	 * @return filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Método para establecer filename.
	 * 
	 * @param filename
	 *                     filename a establecer
	 */
	public void setFilename(final String filename) {
		this.filename = filename;
	}

	/**
	 * Método de acceso a content.
	 * 
	 * @return content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Método para establecer content.
	 * 
	 * @param content
	 *                    content a establecer
	 */
	public void setContent(final byte[] content) {
		this.content = content;
	}

}
