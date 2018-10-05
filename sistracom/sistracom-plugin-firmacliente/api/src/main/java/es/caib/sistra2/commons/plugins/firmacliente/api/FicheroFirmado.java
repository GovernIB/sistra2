package es.caib.sistra2.commons.plugins.firmacliente.api;

/**
 * Estado de la sesión de firma.
 *
 * @author Indra
 *
 */
public class FicheroFirmado {

	/** Nombre del fichero firmado **/
	private String nombreFichero;

	/** MimeType del fichero firmado */
	private String mimetypeFichero;

	/** Array de bytes con la firma del documento */
	private byte[] firmaFichero;

	/**
	 * @return the nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}

	/**
	 * @param nombreFichero
	 *            the nombreFichero to set
	 */
	public void setNombreFichero(final String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/**
	 * @return the mimetypeFichero
	 */
	public String getMimetypeFichero() {
		return mimetypeFichero;
	}

	/**
	 * @param mimetypeFichero
	 *            the mimetypeFichero to set
	 */
	public void setMimetypeFichero(final String mimetypeFichero) {
		this.mimetypeFichero = mimetypeFichero;
	}

	/**
	 * @return the firmaFichero
	 */
	public byte[] getFirmaFichero() {
		return firmaFichero;
	}

	/**
	 * @param firmaFichero
	 *            the firmaFichero to set
	 */
	public void setFirmaFichero(final byte[] firmaFichero) {
		this.firmaFichero = firmaFichero;
	}

}
