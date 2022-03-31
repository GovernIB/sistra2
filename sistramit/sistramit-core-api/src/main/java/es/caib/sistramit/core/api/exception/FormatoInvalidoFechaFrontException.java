package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando se intenta evaluar una fecha pasada en el formato
 * del front.
 *
 */
@SuppressWarnings("serial")
public final class FormatoInvalidoFechaFrontException extends ServiceRollbackException {

	/*
	 * FormatoInvalidoFechaFrontException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Excepción formato fecha invalido.
	 *
	 * @param fecha fecha invalida
	 * @param e     excepción generada
	 */
	public FormatoInvalidoFechaFrontException(final String fecha, final Throwable e) {
		super("Format de data invàlid: " + fecha, e);
	}

}
