package es.caib.sistramit.core.service.model.script.formulario;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Establecimiento de estado en script estado.
 *
 * @author Indra
 *
 */
public interface ResEstadoCampoInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_ESTADO";

	/**
	 * Establece que el campo es de solo lectura.
	 *
	 * @param readOnly
	 *            Booleano
	 *
	 */
	void setSoloLectura(final boolean readOnly);

}
