package es.caib.sistramit.core.service.model.script;

/**
 * Datos usuario.
 *
 *
 * @author Indra
 *
 */
public interface ClzDatosUsuarioInt extends PluginClass {

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif Nif
	 */
	String getNif();

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre
	 */
	String getNombre();

	/**
	 * Método de acceso a apellido1.
	 *
	 * @return apellido1
	 */
	String getApellido1();

	/**
	 * Método de acceso a apellido2.
	 *
	 * @return apellido2
	 */
	String getApellido2();

	/**
	 * Obtiene nombre y apellidos.
	 *
	 * @return Nombre y apellidos en formato: Nombre Apellido1 Apellido2
	 */
	String getNombreApellidos();

	/**
	 * Obtiene apellidos, nombre.
	 *
	 * @return Nombre y apellidos en formato: Apellido1 Apellido2, Nombre
	 */
	String getApellidosNombre();

	/**
	 * Obtiene apellidos.
	 *
	 * @return Apellidos en formato: Apellido1 Apellido2
	 */
	String getApellidos();

	/**
	 * Obtiene email.
	 *
	 * @return Email
	 */
	String getEmail();

}
