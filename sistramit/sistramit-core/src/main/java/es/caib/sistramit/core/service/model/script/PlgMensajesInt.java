package es.caib.sistramit.core.service.model.script;

/**
 * Plugin que permite acceder a los mensajes de validación. Sirve para usar
 * literales multiidioma dentro de los scripts.
 *
 * @author Indra
 *
 */
public interface PlgMensajesInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_MENSAJES";

	/**
	 * Método para crear un mensaje de validación.
	 *
	 * @param codigo
	 *            codigo mensaje validación
	 * @return Mensaje validacion
	 */
	ClzMensajeInt crearMensaje(final String codigo);

}
