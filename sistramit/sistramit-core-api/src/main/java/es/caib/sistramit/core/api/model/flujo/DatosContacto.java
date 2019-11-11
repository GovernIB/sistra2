package es.caib.sistramit.core.api.model.flujo;

/**
 * Datos de contacto.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosContacto implements ModelApi {

	/** Pais del interesado (codificación SICRES) */
	private Long pais;

	/** provincia del interesado (codificación SICRES) */
	private Long provincia;

	/** municipio del interesado (codificación SICRES) */
	private Long municipio;

	/** direccion postal del interesado */
	private String direccion;

	/** codigo postal del interesado */
	private String codigoPostal;

	/** email del interesado */
	private String email;

	/** telefono del interesado */
	private String telefono;

	/**
	 * Método de acceso a pais.
	 *
	 * @return pais
	 */
	public Long getPais() {
		return pais;
	}

	/**
	 * Método para establecer pais.
	 *
	 * @param pais
	 *                 pais a establecer
	 */
	public void setPais(final Long pais) {
		this.pais = pais;
	}

	/**
	 * Método de acceso a provincia.
	 *
	 * @return provincia
	 */
	public Long getProvincia() {
		return provincia;
	}

	/**
	 * Método para establecer provincia.
	 *
	 * @param provincia
	 *                      provincia a establecer
	 */
	public void setProvincia(final Long provincia) {
		this.provincia = provincia;
	}

	/**
	 * Método de acceso a municipio.
	 *
	 * @return municipio
	 */
	public Long getMunicipio() {
		return municipio;
	}

	/**
	 * Método para establecer municipio.
	 *
	 * @param municipio
	 *                      municipio a establecer
	 */
	public void setMunicipio(final Long municipio) {
		this.municipio = municipio;
	}

	/**
	 * Método de acceso a direccion.
	 *
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Método para establecer direccion.
	 *
	 * @param direccion
	 *                      direccion a establecer
	 */
	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Método de acceso a codigoPostal.
	 *
	 * @return codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Método para establecer codigoPostal.
	 *
	 * @param codigoPostal
	 *                         codigoPostal a establecer
	 */
	public void setCodigoPostal(final String codigoPostal) {
		this.codigoPostal = codigoPostal;
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
	 * Método de acceso a telefono.
	 *
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Método para establecer telefono.
	 *
	 * @param telefono
	 *                     telefono a establecer
	 */
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Imprime en String.
	 *
	 * @return String
	 */
	public String print() {
		return "Pais:" + getPais() + " Provincia:" + getProvincia() + "Municipio:" + getMunicipio() + " Direccion: "
				+ getDireccion() + " CP:" + getCodigoPostal() + " Email:" + getEmail() + " Teléfono: " + getTelefono();
	}

}
