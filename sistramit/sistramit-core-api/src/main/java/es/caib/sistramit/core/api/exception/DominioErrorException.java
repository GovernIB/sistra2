package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Error que indica que no se ha podido recuperar un dominio.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DominioErrorException extends ServiceRollbackException {

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
	public DominioErrorException(final String pMessage) {
		super(pMessage);
	}

	public DominioErrorException(final String pmessageSRE, final Throwable pcauseSRE) {
		super(pmessageSRE, pcauseSRE);
	}

}
