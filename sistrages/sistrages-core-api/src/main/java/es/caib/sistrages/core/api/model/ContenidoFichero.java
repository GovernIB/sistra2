package es.caib.sistrages.core.api.model;

/**
 *
 * Contenido fichero.
 *
 * @author Indra
 *
 */

public class ContenidoFichero extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** File name. */
	private String filename;

	/** Content. */
	private byte[] content;

	public String getFilename() {
		return filename;
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(final byte[] content) {
		this.content = content;
	}

}
