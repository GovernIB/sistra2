package es.caib.sistra2.commons.plugins.firmacliente.api;

/**
 * Estado de la sesión de firma.
 *
 * @author Indra
 *
 */
public class FicheroAFirmar {

	/** Identificador de la sesión de firma **/
	private String sesion;

	/** Nombre del fichero **/
	private String nombreFichero;

	/** MimeType del fichero */
	private String mimetypeFichero;

	/** Array de bytes del documento */
	private byte[] fichero;

	/** Codigo sign ID. **/
	private String signID;

	/** Sign number. Sólo acepta el valor 1. **/
	private int signNumber = 1;

	/** Razon. **/
	private String razon;

	/** Localizacion. **/
	private String localizacion;

	/** Idioma. **/
	private String idioma;

	/**
	 * @return the nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}

	/**
	 * @param nombreFichero
	 *                          the nombreFichero to set
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
	 *                            the mimetypeFichero to set
	 */
	public void setMimetypeFichero(final String mimetypeFichero) {
		this.mimetypeFichero = mimetypeFichero;
	}

	/**
	 * @return the sesion
	 */
	public String getSesion() {
		return sesion;
	}

	/**
	 * @param sesion
	 *                   the sesion to set
	 */
	public void setSesion(final String sesion) {
		this.sesion = sesion;
	}

	/**
	 * @return the fichero
	 */
	public byte[] getFichero() {
		return fichero;
	}

	/**
	 * @param fichero
	 *                    the fichero to set
	 */
	public void setFichero(final byte[] fichero) {
		this.fichero = fichero;
	}

	/**
	 * @return the signID
	 */
	public String getSignID() {
		return signID;
	}

	/**
	 * @param signID
	 *                   the signID to set
	 */
	public void setSignID(final String signID) {
		this.signID = signID;
	}

	/**
	 * @return the signNumber
	 */
	public int getSignNumber() {
		return signNumber;
	}

	/**
	 * @param signNumber
	 *                       the signNumber to set
	 */
	public void setSignNumber(final int signNumber) {
		this.signNumber = signNumber;
	}

	/**
	 * @return the razon
	 */
	public String getRazon() {
		return razon;
	}

	/**
	 * @param razon
	 *                  the razon to set
	 */
	public void setRazon(final String razon) {
		this.razon = razon;
	}

	/**
	 * @return the localizacion
	 */
	public String getLocalizacion() {
		return localizacion;
	}

	/**
	 * @param localizacion
	 *                         the localizacion to set
	 */
	public void setLocalizacion(final String localizacion) {
		this.localizacion = localizacion;
	}

	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma
	 *                   the idioma to set
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

}
