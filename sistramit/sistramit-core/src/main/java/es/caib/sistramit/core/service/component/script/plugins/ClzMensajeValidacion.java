package es.caib.sistramit.core.service.component.script.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.service.component.script.ScriptUtils;
import es.caib.sistramit.core.service.model.script.ClzMensajeValidacionInt;

/**
 * Clase que modeliza un mensaje de validación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzMensajeValidacion implements ClzMensajeValidacionInt {

	/**
	 * Codigo mensaje.
	 */
	private final String codigoMensaje;

	/**
	 * Parametros mensaje.
	 */
	private final List<String> parametrosMensaje = new ArrayList<String>();

	/**
	 * Mensajes validaciones.
	 */
	private final Map<String, String> mensajesValidacion;

	/**
	 * Constructor.
	 *
	 * @param pCodigoMensaje
	 *            Codigo mensaje error
	 * @param pMensajesValidacion
	 *            Mensajes validacion
	 *
	 */
	public ClzMensajeValidacion(final String pCodigoMensaje, final Map<String, String> pMensajesValidacion) {
		super();
		codigoMensaje = pCodigoMensaje;
		mensajesValidacion = pMensajesValidacion;
	}


	@Override
	public void addParametroMensaje(final String pParametro) {
		parametrosMensaje.add(pParametro);
	}


	@Override
	public String getMensaje() {
		return ScriptUtils.calculaMensajeError(mensajesValidacion, codigoMensaje, parametrosMensaje);
	}

}
