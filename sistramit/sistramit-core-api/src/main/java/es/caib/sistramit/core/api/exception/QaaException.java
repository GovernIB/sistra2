package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción si el QAA del trámite es superior al del usuario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class QaaException extends ServiceRollbackException {

	/*
	 * QaaException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor QaaException.
	 *
	 * @param pMessage Mensaje de error.
	 */
	public QaaException() {
		super("Nivell de QAA del tràmit és superior al del usuari autenticat");
	}

}
