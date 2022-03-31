package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

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

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause Causa
	 */
	public DatabaseException(final Exception cause) {
		super("Error a la capa de base de dades: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause Causa
	 */
	public DatabaseException(final String cause) {
		super("Error a la capa de base de dades: " + cause);
	}

}
