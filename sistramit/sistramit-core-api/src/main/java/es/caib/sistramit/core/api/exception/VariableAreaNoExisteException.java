package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Error que indica que no existe variable de área.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class VariableAreaNoExisteException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor.
	 *
	 * @param pMessage
	 *                     Mensaje de error.
	 */
	public VariableAreaNoExisteException(final String pMessage) {
		super(pMessage);
	}

	public VariableAreaNoExisteException(final String pmessageSRE, final Throwable pcauseSRE) {
		super(pmessageSRE, pcauseSRE);
	}

}
