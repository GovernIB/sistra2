package es.caib.sistramit.core.service.component.script.plugins;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.service.model.script.PlgErrorInt;

/**
 * Plugin que permite establecer un error al ejecutar el script.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgError implements PlgErrorInt {

	/**
	 * Indica si existe error.
	 */
	private boolean existeError;

	/**
	 * Código error particularizado. Para establecer error por codigo mensaje.
	 */
	private String codigoMensajeError;

	/**
	 * Parámetros a substuir en el mensaje que hace referencia el código de error.
	 * ({0} {1} ...).
	 */
	private final List<String> parametrosMensajeError = new ArrayList<String>();

	/**
	 * Mensaje error particularizado. Para establecer error directamente con el
	 * texto.
	 */
	private String textoMensajeError;

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Indica si hay error.
	 *
	 * @return Indica si hay error.
	 */
	public boolean isExisteError() {
		return existeError;
	}

	@Override
	public void setExisteError(final boolean pExisteError) {
		existeError = pExisteError;
	}

	/**
	 * Obtiene codigo de error.
	 *
	 * @return Obtiene codigo de error.
	 */
	public String getCodigoMensajeError() {
		return codigoMensajeError;
	}

	@Override
	public void addParametroMensajeError(final String parametro) {
		parametrosMensajeError.add(parametro);
	}

	@Override
	public void setCodigoMensajeError(final String pCodigoMensajeError) {
		codigoMensajeError = pCodigoMensajeError;
	}

	/**
	 * Obtiene texto de error.
	 *
	 * @return Obtiene texto de error.
	 */
	public String getTextoMensajeError() {
		return textoMensajeError;
	}

	@Override
	public void setTextoMensajeError(final String pTextoMensajeError) {
		textoMensajeError = pTextoMensajeError;
	}

	/**
	 * Obtiene parametros de error.
	 *
	 * @return Obtiene parametros de error.
	 */
	public List<String> getParametrosMensajeError() {
		return parametrosMensajeError;
	}

}
