package es.caib.sistramit.core.service.model.script;

/**
 * Plugin para marcar el script con error. Este error generará una excepción de
 * script excepto en los scripts que sean de validación, en los que el mensaje
 * establecido en el plugin será el que se muestre al usuario.
 *
 * @author Indra
 *
 */
public interface PlgErrorInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_ERROR";

	/**
	 * Método para establecer existeError.
	 *
	 * @param pExisteError
	 *            existeError a establecer
	 */
	void setExisteError(final boolean pExisteError);

	/**
	 * Parámetros a substuir en el mensaje que hace referencia el código de error.
	 * ({0} {1} ...).
	 *
	 * @param parametro
	 *            Parámetro a substuir.
	 */
	void addParametroMensajeError(final String parametro);

	/**
	 * Método para establecer el mensaje de error a partir de un código de mensaje.
	 *
	 * @param pCodigoMensajeError
	 *            codigoMensajeError a establecer
	 */
	void setCodigoMensajeError(final String pCodigoMensajeError);

	/**
	 * Método para establecer directamente el mesaje de error.
	 *
	 * @param pTextoMensajeError
	 *            textoMensajeError a establecer
	 */
	void setTextoMensajeError(final String pTextoMensajeError);

}
