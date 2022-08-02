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
	@ApiModelProperty(value = "Tipo autenticación: Autenticado (c) / Anónimo (a).", required = true)
	private String autenticacion;

	/**
	 * Metodo Autenticacion.
	 */
	@ApiModelProperty(value = "Metodo Autenticacion (ANONIMO / CLAVE_CERTIFICADO / CLAVE_PIN / CLAVE_PERMANENTE)", required = true)
	private String metodoAutenticacion;

	/**
	 * Código usuario.
	 */
	@ApiModelProperty(value = "Código usuario", required = true)
	private String username;

	/**
	 * Nombre usuario / Razon social si es una empresa.
	 */
	@ApiModelProperty(value = "Nombre usuario / Razon social si es una empresa", required = true)
	private String nombre;

	/**
	 * Apellido 1 usuario.
	 */
	@ApiModelProperty(value = "Apellido 1 usuario", required = true)
	private String apellido1;

	/**
	 * Apellido 2 usuario.
	 */
	@ApiModelProperty(value = "Apellido 2 usuario")
	private String apellido2;

	/**
	 * Nif usuario.
	 */
	@ApiModelProperty(value = "Nif usuario", required = true)
	private String nif;

	/**
	 * Email usuario.
	 */
	@ApiModelProperty(value = "Email usuario")
	private String email;

	/**
	 * QAA (Bajo:1 / Medio: 2 / Alto: 3).
	 */
	@ApiModelProperty(value = "QAA (Bajo:1 / Medio: 2 / Alto: 3)", required = true)
	private String qaa;

	/**
	 * Información de representante
	 */
	@ApiModelProperty(value = "Información de representante")
	private RRepresentanteInfo representanteInfo;

	/**
	 * Método de acceso a autenticacion.
	 *
	 * @return autenticacion
	 */
	public String getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 *
	 * @param autenticacion
	 *                          autenticacion a establecer
	 */
	public void setAutenticacion(final String autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 *
	 * @return metodoAutenticacion
	 */
	public String getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 *
	 * @param metodoAutenticacion
	 *                                metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final String metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}

	/**
	 * Método de acceso a username.
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Método para establecer username.
	 *
	 * @param username
	 *                     username a establecer
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre.
	 *
	 * @param nombre
	 *                   nombre a establecer
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método de acceso a apellido1.
	 *
	 * @return apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Método para establecer apellido1.
	 *
	 * @param apellido1
	 *                      apellido1 a establecer
	 */
	public void setApellido1(final String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * Método de acceso a apellido2.
	 *
	 * @return apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Método para establecer apellido2.
	 *
	 * @param apellido2
	 *                      apellido2 a establecer
	 */
	public void setApellido2(final String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param nif
	 *                nif a establecer
	 */
	public void setNif(final String nif) {
		this.nif = nif;
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
	 * Método de acceso a qaa.
	 *
	 * @return qaa
	 */
	public String getQaa() {
		return qaa;
	}

	/**
	 * Método para establecer qaa.
	 *
	 * @param qaa
	 *                qaa a establecer
	 */
	public void setQaa(final String qaa) {
		this.qaa = qaa;
	}

	/**
	 * @return the representanteInfo
	 */
	public final RRepresentanteInfo getRepresentanteInfo() {
		return representanteInfo;
	}

	/**
	 * @param representanteInfo the representanteInfo to set
	 */
	public final void setRepresentanteInfo(RRepresentanteInfo representanteInfo) {
		this.representanteInfo = representanteInfo;
	}



}
