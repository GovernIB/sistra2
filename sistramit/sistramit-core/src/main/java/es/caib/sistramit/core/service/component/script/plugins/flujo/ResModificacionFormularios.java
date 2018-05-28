package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.service.model.script.ResModificacionFormulariosInt;

/**
 *
 * Modificación de los formularios en el script de post guardar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResModificacionFormularios implements ResModificacionFormulariosInt {

	@Override
	public String getPluginId() {
		return ID;
	}

	// TODO PENDIENTE
}
