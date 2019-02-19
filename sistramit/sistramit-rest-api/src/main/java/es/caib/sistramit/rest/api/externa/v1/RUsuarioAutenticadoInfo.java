package es.caib.sistramit.rest.api.externa.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Info usuario autenticado.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RUsuarioAutenticadoInfo", description = "Info usuario autenticado")
public final class RUsuarioAutenticadoInfo {


	/**
	 * Tipo autenticacion.
	 */
	@ApiModelProperty(value = "Tipo autenticación: Autenticado (c) / Anónimo (a).")
	private String autenticacion;

	/**
	 * Metodo Autenticacion.
	 */
	@ApiModelProperty(value = "Metodo Autenticacion")
	private String metodoAutenticacion;

	/**
	 * Código usuario.
	 */
	@ApiModelProperty(value = "Código usuario")
	private String username;

	/**
	 * Nombre usuario / Razon social si es una empresa.
	 */
	@ApiModelProperty(value = "Nombre usuario / Razon social si es una empresa")
	private String nombre;

	/**
	 * Apellido 1 usuario.
	 */
	@ApiModelProperty(value = "Apellido 1 usuario")
	private String apellido1;

	/**
	 * Apellido 2 usuario.
	 */
	@ApiModelProperty(value = "Apellido 2 usuario")
	private String apellido2;

	/**
	 * Nif usuario.
	 */
	@ApiModelProperty(value = "Nif usuario")
	private String nif;

	/**
	 * Email usuario.
	 */
	@ApiModelProperty(value = "Email usuario")
	private String email;

	public String getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(final String autenticacion) {
		this.autenticacion = autenticacion;
	}

	public String getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	public void setMetodoAutenticacion(final String metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(final String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(final String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
