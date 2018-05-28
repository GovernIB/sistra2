package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.service.model.script.flujo.PlgVariablesFlujoInt;

/**
 * Plugin de acceso a variables de flujo generadas por los pasos de tramitación
 * (se establecen desde el script de accion en flujo personalizado).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgVariablesFlujo implements PlgVariablesFlujoInt {

	@Override
	public String getPluginId() {
		return ID;
	}

}
