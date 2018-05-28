package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistramit.core.service.model.script.PlgValidacionesInt;

/**
 * Plugin que permite realizar diversas validaciones.
 *
 * @author Indra
 */
@SuppressWarnings("serial")
public final class PlgValidaciones implements PlgValidacionesInt {

	/**
	 * Indica si esta habilitado debug.
	 */
	private final boolean debugEnabled;

	/**
	 * Constructor.
	 *
	 * @param pDebugEnabled
	 *            Debug enabled
	 */
	public PlgValidaciones(final boolean pDebugEnabled) {
		super();
		debugEnabled = pDebugEnabled;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	// TODO PENDIENTE
}
