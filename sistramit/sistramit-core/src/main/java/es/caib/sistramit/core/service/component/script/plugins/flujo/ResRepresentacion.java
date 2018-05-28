package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.service.model.script.flujo.ResRepresentacionInt;

/**
 *
 * Datos que se pueden establecer dinámicamente los parametros de
 * representacion.
 *
 * En caso de habilitar este script no tendrá efecto las opciones indicadas en
 * las propiedades de registro.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResRepresentacion implements ResRepresentacionInt {

	@Override
	public String getPluginId() {
		return ID;
	}

	// TODO PENDIENTE
}
