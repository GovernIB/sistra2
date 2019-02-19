package es.caib.sistramit.core.service.component.script.plugins;

import java.util.Map;

import es.caib.sistramit.core.service.model.script.ClzMensajeInt;
import es.caib.sistramit.core.service.model.script.PlgMensajesInt;

/**
 * Plugin que permite acceder a los mensajes de validación. Sirve para usar
 * literales multiidioma dentro de los scripts.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgMensajes implements PlgMensajesInt {

	/**
	 * Mensajes validaciones.
	 */
	private final Map<String, String> mensajesValidacion;

	/**
	 * Constructor.
	 *
	 * @param pMensajesValidacion
	 *            Mensajes validacion
	 */
	public PlgMensajes(final Map<String, String> pMensajesValidacion) {
		super();
		mensajesValidacion = pMensajesValidacion;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public ClzMensajeInt crearMensaje(final String pCodigo) {
		final ClzMensajeInt m = new ClzMensaje(pCodigo, mensajesValidacion);
		return m;
	}

}
