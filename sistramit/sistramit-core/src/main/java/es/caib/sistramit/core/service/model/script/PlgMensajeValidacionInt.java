package es.caib.sistramit.core.service.model.script;

/**
 * Plugin que permite acceder a los mensajes de validación. Sirve para usar
 * literales multiidioma dentro de los scripts.
 *
 * @author Indra
 *
 */
public interface PlgMensajeValidacionInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_MENSAJEVALIDACION";

	/**
	 * Método para crear un mensaje de validación.
	 *
	 * @param codigo
	 *            codigo mensaje validación
	 * @return Mensaje validacion
	 */
	ClzMensajeValidacionInt crearMensajeValidacion(final String codigo);

}
