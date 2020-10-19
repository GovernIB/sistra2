package es.caib.sistra2.commons.plugins.formulario.api;

import java.io.Serializable;

/**
 * Información usuario autenticado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class UsuarioInfo implements Serializable {

	/**
	 * Nif usuario.
	 */
	private String nif;

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

}
