package es.caib.sistramit.core.service.component.script.plugins;

import java.util.Map;

import es.caib.sistramit.core.service.model.script.ClzMensajeValidacionInt;
import es.caib.sistramit.core.service.model.script.PlgMensajeValidacionInt;

/**
 * Plugin que permite acceder a los mensajes de validación. Sirve para usar
 * literales multiidioma dentro de los scripts.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgMensajesValidacion implements PlgMensajeValidacionInt {

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
	public PlgMensajesValidacion(final Map<String, String> pMensajesValidacion) {
		super();
		mensajesValidacion = pMensajesValidacion;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public ClzMensajeValidacionInt crearMensajeValidacion(final String pCodigo) {
		final ClzMensajeValidacionInt m = new ClzMensajeValidacion(pCodigo, mensajesValidacion);
		return m;
	}

}
