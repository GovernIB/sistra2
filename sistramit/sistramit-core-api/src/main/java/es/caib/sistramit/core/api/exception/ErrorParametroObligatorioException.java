package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error conversion JSON.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorParametroObligatorioException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause Causa
	 */
	public ErrorParametroObligatorioException(final Exception cause) {
		super("Error Paràmetro Obligatori: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause Causa
	 */
	public ErrorParametroObligatorioException(final String cause) {
		super("Error Paràmetro Obligatori: " + cause);
	}

}
