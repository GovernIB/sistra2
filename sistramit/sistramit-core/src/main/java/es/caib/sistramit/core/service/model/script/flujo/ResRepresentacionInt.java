package es.caib.sistramit.core.service.model.script.flujo;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Establece datos representación.
 *
 * @author Indra
 *
 */
public interface ResRepresentacionInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_REPRESENTACION";

	/**
	 * Indica si realmente existe representación (por defecto true).
	 */
	void setActivarRepresentacion(boolean activar);

	/**
	 * Crea representante para establecer los datos.
	 *
	 * @return representante
	 */
	ResPersonaInt crearRepresentante();

	/**
	 * Crea representado para establecer los datos.
	 *
	 * @return representado
	 */
	ResPersonaInt crearRepresentado();

}
