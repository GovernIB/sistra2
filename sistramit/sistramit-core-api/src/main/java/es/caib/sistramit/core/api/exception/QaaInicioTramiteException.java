package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción si el QAA del trámite es superior al del usuario cuando se inicia
 * el trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class QaaInicioTramiteException extends ServiceRollbackException {

	/*
	 * QaaException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor.
	 */
	public QaaInicioTramiteException() {
		super("Nivell de QAA del tràmit és superior al del usuari autenticat");
	}

}
