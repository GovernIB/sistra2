package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.service.model.script.ResPagoInt;

/**
 * Datos que se pueden establecer en un pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResPago implements ResPagoInt {

	@Override
	public String getPluginId() {
		return ID;
	}

	// TODO PENDIENTE

}
