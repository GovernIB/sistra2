package es.caib.sistrahelp.core.api.exception;

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

	/**
	 * Constructor ErrorNoControladoException.
	 *
	 * @param cause
	 *            Causa
	 */
	public ErrorNoControladoException(final Throwable cause) {
		super("Error no controlado en la capa de servicio: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            mensaje
	 */
	public ErrorNoControladoException(final String message) {
		super(message);
	}

}
