package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.service.model.script.flujo.ResInstruccionesInt;

/**
 * Datos para establecer instrucciones en paso Debe saber.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResInstrucciones implements ResInstruccionesInt {

	/**
	 * Código mensaje particularizado. Para establecer mensaje por codigo mensaje.
	 */
	private String codigoMensaje;

	/**
	 * Parámetros a substuir en el mensaje que hace referencia el código de mensaje.
	 * ({0} {1} ...).
	 */
	private final List<String> parametrosMensaje = new ArrayList<String>();

	/**
	 * Mensaje particularizado (html). Para establecer mensaje directamente con el
	 * texto.
	 */
	private String textoMensaje;

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Obtiene codigo de mensaje.
	 *
	 * @return Obtiene codigo de mensaje.
	 */
	public String getCodigoMensaje() {
		return codigoMensaje;
	}

	@Override
	public void addParametroMensaje(final String parametro) {
		parametrosMensaje.add(parametro);
	}

	@Override
	public void setCodigoMensaje(final String pCodigoMensaje) {
		codigoMensaje = pCodigoMensaje;
	}

	/**
	 * Obtiene texto de mensaje.
	 *
	 * @return Obtiene texto de mensaje.
	 */
	public String getTextoMensaje() {
		return textoMensaje;
	}

	@Override
	public void setTextoMensaje(final String pTextoMensaje) {
		textoMensaje = pTextoMensaje;
	}

	/**
	 * Obtiene parametros de mensaje.
	 *
	 * @return Obtiene parametros de mensaje.
	 */
	public List<String> getParametrosMensaje() {
		return parametrosMensaje;
	}

}
