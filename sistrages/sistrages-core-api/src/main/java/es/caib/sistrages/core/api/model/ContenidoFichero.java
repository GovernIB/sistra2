package es.caib.sistrages.core.api.model;

/**
 *
 * Contenido fichero.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ContenidoFichero extends ModelApi {

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
