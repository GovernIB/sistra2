package es.caib.sistrages.core.api.exception;

/**
 *
 * Error al clonar un objeto del modelo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class CloneModelException extends ServiceRollbackException {

	@Override
	public boolean isNegocioException() {
		return false;
	}

	/**
	 * Constructor ErrorNoControladoException.
	 *
	 * @param cause
	 *            Causa
	 */
	public CloneModelException(final Throwable cause) {
		super("Error clonando objeto: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *            mensaje
	 */
	public CloneModelException(final String message) {
		super(message);
	}

}
