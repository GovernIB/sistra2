package es.caib.sistramit.rest.api.externa.v1;

import io.swagger.annotations.ApiModelProperty;

public class RRepresentanteInfo {

	/**
	 * Nombre representante.
	 */
	@ApiModelProperty(value = "Nombre representante")
	private String nombre;

	/**
	 * Apellido 1 representante.
	 */
	@ApiModelProperty(value = "Apellido 1 representante")
	private String apellido1;

	/**
	 * Apellido 2 representante.
	 */
	@ApiModelProperty(value = "Apellido 2 representante")
	private String apellido2;

	/**
	 * Nif representante.
	 */
	@ApiModelProperty(value = "Nif representante")
	private String nif;

	/**
	 * Email representante.
	 */
	@ApiModelProperty(value = "Email representante")
	private String email;

	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido1
	 */
	public final String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1 the apellido1 to set
	 */
	public final void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return the apellido2
	 */
	public final String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2 the apellido2 to set
	 */
	public final void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the nif
	 */
	public final String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public final void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}



}
