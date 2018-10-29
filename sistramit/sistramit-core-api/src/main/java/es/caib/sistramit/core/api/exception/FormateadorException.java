package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Error formateador.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class FormateadorException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor.
	 *
	 * @param pMessage
	 *            Mensaje de error.
	 */
	public FormateadorException(final String pMessage) {
		super(pMessage);
	}

	public FormateadorException(final String pmessageSRE, final Throwable pcauseSRE) {
		super(pmessageSRE, pcauseSRE);
	}

}
