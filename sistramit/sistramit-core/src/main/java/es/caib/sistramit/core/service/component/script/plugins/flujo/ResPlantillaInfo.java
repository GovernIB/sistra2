package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.service.model.script.ResPlantillaInfoInt;

/**
 * Resultado para establecer los datos de la plantilla para el paso de
 * información.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResPlantillaInfo implements ResPlantillaInfoInt {

	@Override
	public String getPluginId() {
		return ID;
	}

}
