package es.caib.sistramit.core.service.model.script;

/**
 * Plugin para debug de scripts. Genera evento en auditoria.
 *
 * @author Indra
 *
 */
public interface PlgLogInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_LOG";

	/**
	 * Realiza debug.
	 *
	 * @param mensaje
	 *            Mensaje.
	 */
	void debug(final String mensaje);

	/**
	 * Implementa retardo para testing.
	 *
	 * @param timeout
	 *            timeout en segundos
	 */
	void retardo(final int timeout);
}
