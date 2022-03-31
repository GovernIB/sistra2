package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que el flujo no es válido. Se ha generado anteriormente
 * una excepción FATAL sobre el flujo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class FlujoInvalidoException extends ServiceRollbackException {

	/*
	 * FlujoInvalidoException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor FlujoInvalidoException.
	 *
	 */
	public FlujoInvalidoException() {
		super("El fluxe no es vàlid");
	}

}
