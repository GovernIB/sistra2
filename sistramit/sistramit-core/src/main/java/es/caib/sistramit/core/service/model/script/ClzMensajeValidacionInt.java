package es.caib.sistramit.core.service.model.script;

/**
 * Clase que modeliza un mensaje de validación.
 *
 * @author Indra
 *
 */
public interface ClzMensajeValidacionInt extends PluginClass {

	/**
	 * Parámetros a substuir en el mensaje que hace referencia el código de error.
	 * ({0} {1} ...).
	 *
	 * @param parametro
	 *            Parámetro a substuir.
	 */
	void addParametroMensaje(final String parametro);

	/**
	 * Devuelve el mensaje.
	 *
	 * @return Mensaje
	 */
	String getMensaje();

}
