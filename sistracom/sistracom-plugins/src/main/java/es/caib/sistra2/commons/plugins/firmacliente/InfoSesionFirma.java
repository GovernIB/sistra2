package es.caib.sistra2.commons.plugins.firmacliente;

/**
 * Datos relativos a la sesión de firma.
 *
 * @author Indra
 *
 */
public class InfoSesionFirma {

	/** Idioma. **/
	private String idioma;

	/** Código de la entidad. **/
	private String entidad;

	/** NIF. **/
	private String nif;

	/** Nombre usuario. **/
	private String nombreUsuario;

	/** Código SIA del procedimento. **/
	private String codigoSIA;

	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma
	 *            the idioma to set
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(final String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * @param nif
	 *            the nif to set
	 */
	public void setNif(final String nif) {
		this.nif = nif;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            the nombreUsuario to set
	 */
	public void setNombreUsuario(final String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the codigoSIA
	 */
	public String getCodigoSIA() {
		return codigoSIA;
	}

	/**
	 * @param codigoSIA
	 *            the codigoSIA to set
	 */
	public void setCodigoSIA(final String codigoSIA) {
		this.codigoSIA = codigoSIA;
	}
}
