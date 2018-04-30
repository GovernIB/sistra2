package es.caib.sistrages.core.api.exception;

/**
 *
 * Capturamos en una excepción de negocio la excepcion que genera spring para
 * errores de base de datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatabaseException extends ServiceRollbackException {

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause
	 *            Causa
	 */
	public DatabaseException(final Exception cause) {
		super("Error en la capa de base de datos: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause
	 *            Causa
	 */
	public DatabaseException(final String cause) {
		super("Error en la capa de base de datos: " + cause);
	}

}
