package es.caib.sistramit.core.service.model.script.flujo;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Datos para establecer instrucciones en paso Debe saber.
 *
 * @author Indra
 *
 */
public interface ResInstruccionesInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_INSTRUCCIONES";

	/**
	 * Establece texto a mostrar a través de texto html.
	 * 
	 * @param textHtml
	 *                     Texto (html)
	 */
	void setTextoMensaje(String textHtml);

	/**
	 * Establece texto a mostrar a través de mensaje.
	 * 
	 * @param textHtml
	 *                     Texto (html)
	 */
	void setCodigoMensaje(String codigoMensaje);

	/**
	 * Parámetros a substuir en el mensaje que hace referencia el código de error.
	 * ({0} {1} ...).
	 *
	 * @param parametro
	 *                      Parámetro a substuir.
	 */
	void addParametroMensaje(final String parametro);

}
