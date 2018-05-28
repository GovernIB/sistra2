package es.caib.sistramit.core.service.model.script;

import java.io.Serializable;

/**
 * Plugin usado desde script.
 *
 * @author Indra
 *
 */
public interface PluginScript extends Serializable {

	/**
	 * Retorna el nombre del plugin. Será accesible desde el js a través de este
	 * nombre.
	 *
	 * @return el atributo plugin id
	 */
	String getPluginId();

}
