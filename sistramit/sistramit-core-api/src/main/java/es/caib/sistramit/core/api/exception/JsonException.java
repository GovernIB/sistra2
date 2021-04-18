package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica error al convertir a JSON.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class JsonException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// JsonException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor JsonException.
	 *
	 * @param pMessage
	 *                     Mensaje de error.
	 */
	public JsonException(final String pMessage) {
		super(pMessage);
	}

	/**
	 * Constructor JsonException.
	 *
	 * @param pMessage
	 *                     Mensaje de error.
	 * @param pExc
	 *                     Excepcion original.
	 */
	public JsonException(final String pMessage, final Throwable pExc) {
		super(pMessage, pExc);
	}

}
