package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Capturamos en una excepción de negocio las excepcion runtime no controladas
 * (no heredan de ServicioRollbackException).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorNoControladoException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor ErrorNoControladoException.
	 *
	 * @param cause Causa
	 */
	public ErrorNoControladoException(final Throwable cause) {
		super("Error no controlat a la capa de servei: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message mensaje
	 */
	public ErrorNoControladoException(final String message) {
		super(message);
	}

}
