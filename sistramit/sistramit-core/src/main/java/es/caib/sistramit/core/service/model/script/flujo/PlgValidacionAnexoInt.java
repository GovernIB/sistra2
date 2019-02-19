package es.caib.sistramit.core.service.model.script.flujo;

import es.caib.sistramit.core.service.model.script.PluginScriptPlg;

/**
 * Plugin que ofrece utilidades para leer datos de un anexo para poder
 * validarlo.
 *
 * @author Indra
 *
 */
public interface PlgValidacionAnexoInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_ANEXO";

	/**
	 * Método para obtener el valor de un campo del pdf anexado.
	 *
	 * @param codigo
	 *            codigo campo
	 * @return Valor campo
	 */
	String getValorPdf(final String codigo);

	/**
	 * Método para obtener el valor de un campo del xml anexado.
	 *
	 * @param xpath
	 *            xpath campo
	 * @return Valor campo
	 */
	String getValorXml(final String xpath);

	/**
	 * Obtiene el contenido del fichero en Base 64.
	 *
	 * @return contenido del fichero en Base 64.
	 */
	String getContenidoB64();

}
