package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción lanzada si salta control concurrencia en registro.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ControlConcurrenciaRegistroException extends ServiceRollbackException {


	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.WARNING;
	}

	/** Constructor. */
	public ControlConcurrenciaRegistroException(final boolean auditarExcepcion) {

		super("Límit de concurrencia de registre aconseguit");

		this.setAuditarExcepcion(auditarExcepcion);

	}

}
