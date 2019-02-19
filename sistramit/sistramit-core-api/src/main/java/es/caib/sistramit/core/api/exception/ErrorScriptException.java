package es.caib.sistramit.core.api.exception;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepción general para la ejecución de scritps que indica que se ha marcado
 * un error en el script. Se usará cuando no se desee generar otra excepción más
 * específica.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorScriptException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// ErrorScriptException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 *
	 * Constructor ErrorScriptException.
	 *
	 * @param scriptName
	 *            Nombre script
	 * @param idSesion
	 *            Id sesion
	 * @param idElemento
	 *            Id elemento
	 * @param pMessage
	 *            Mensaje
	 */
	public ErrorScriptException(final String scriptName, final String idSesion, final String idElemento,
			final String pMessage) {
		super(pMessage);
		final ListaPropiedades detalles = new ListaPropiedades();
		if (idSesion != null) {
			detalles.addPropiedad("idSesion", idSesion);
		}
		detalles.addPropiedad("script", scriptName);
		if (StringUtils.isNotEmpty(idElemento)) {
			detalles.addPropiedad("elemento", idElemento);
		}
		this.setDetallesExcepcion(detalles);
	}

	/**
	 *
	 * Constructor ErrorScriptException.
	 *
	 *
	 * @param pMessage
	 *            Mensaje
	 */
	public ErrorScriptException(final String pMessage) {
		super(pMessage);
	}

}
