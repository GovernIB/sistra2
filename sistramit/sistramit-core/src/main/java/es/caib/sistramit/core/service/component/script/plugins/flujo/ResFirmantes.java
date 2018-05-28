package es.caib.sistramit.core.service.component.script.plugins.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.ResFirmantesInt;

/**
 *
 * Datos para establecer los firmantes de un documento.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResFirmantes implements ResFirmantesInt {

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void addFirmante(final String nif, final String nombre) throws ScriptException {
		// TODO PENDIENTE

	}

}
