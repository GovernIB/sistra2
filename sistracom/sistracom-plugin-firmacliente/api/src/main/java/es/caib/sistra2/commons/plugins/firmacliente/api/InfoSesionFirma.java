package es.caib.sistra2.commons.plugins.firmacliente.api;

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

	/** Email. **/
	private String email;

	/** NIF representante (quien firma). **/
	private String nifRepresentante;

	/** Nombre representante (quien firma).. **/
	private String nombreRepresentante;

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

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *                    the entidad to set
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
	 *                the nif to set
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
	 *                          the nombreUsuario to set
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
	 *                      the codigoSIA to set
	 */
	public void setCodigoSIA(final String codigoSIA) {
		this.codigoSIA = codigoSIA;
	}

	/**
	 * Método de acceso a email.
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método para establecer email.
	 *
	 * @param email
	 *                  email a establecer
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Método de acceso a nifRepresentante.
	 * 
	 * @return nifRepresentante
	 */
	public String getNifRepresentante() {
		return nifRepresentante;
	}

	/**
	 * Método para establecer nifRepresentante.
	 * 
	 * @param nifRepresentante
	 *                             nifRepresentante a establecer
	 */
	public void setNifRepresentante(final String nifRepresentante) {
		this.nifRepresentante = nifRepresentante;
	}

	/**
	 * Método de acceso a nombreRepresentante.
	 * 
	 * @return nombreRepresentante
	 */
	public String getNombreRepresentante() {
		return nombreRepresentante;
	}

	/**
	 * Método para establecer nombreRepresentante.
	 * 
	 * @param nombreRepresentante
	 *                                nombreRepresentante a establecer
	 */
	public void setNombreRepresentante(final String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}
}
