package es.caib.sistramit.core.api.model.security;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Info usuario autenticado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class UsuarioAutenticadoInfo implements Serializable {

	/**
	 * Tipo autenticacion.
	 */
	private TypeAutenticacion autenticacion;

	/**
	 * Metodo Autenticacion.
	 */
	private TypeMetodoAutenticacion metodoAutenticacion;

	/**
	 * Código usuario.
	 */
	private String username;

	/**
	 * Nombre usuario / Razon social si es una empresa.
	 */
	private String nombre;

	/**
	 * Apellido 1 usuario.
	 */
	private String apellido1;

	/**
	 * Apellido 2 usuario.
	 */
	private String apellido2;

	/**
	 * Nif usuario.
	 */
	private String nif;

	/**
	 * Email usuario.
	 */
	private String email;

	/**
	 * Representante.
	 */
	private UsuarioAutenticadoRepresentante representante;

	/**
	 * Información sesión web.
	 */
	private SesionInfo sesionInfo;

	public TypeAutenticacion getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(final TypeAutenticacion autenticacion) {
		this.autenticacion = autenticacion;
	}

	public TypeMetodoAutenticacion getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	public void setMetodoAutenticacion(final TypeMetodoAutenticacion metodoAutenticacion) {
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

	public SesionInfo getSesionInfo() {
		return sesionInfo;
	}

	public void setSesionInfo(final SesionInfo sesionInfo) {
		this.sesionInfo = sesionInfo;
	}

	/**
	 * Método para mostrar el contenido.
	 *
	 * @return el string
	 */
	public final String print() {
		return "[autenticacion=" + autenticacion + ", nif=" + nif + ", nombre=" + nombre + ", apellido1=" + apellido1
				+ ", apellido2=" + apellido2 + ", metodoAutenticacion=" + metodoAutenticacion
				+ (representante != null ? representante.print() : "") + "]";
	}

	/**
	 * Método de acceso a representante.
	 * 
	 * @return representante
	 */
	public UsuarioAutenticadoRepresentante getRepresentante() {
		return representante;
	}

	/**
	 * Método para establecer representante.
	 * 
	 * @param representante
	 *            representante a establecer
	 */
	public void setRepresentante(final UsuarioAutenticadoRepresentante representante) {
		this.representante = representante;
	}

}
