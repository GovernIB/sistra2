package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando el valor posible es una palabra reservada.
 *
 */
@SuppressWarnings("serial")
public final class ValorPosiblePalabraReservadaException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor ValorCampoFormularioCaracteresNoPermitidosException.
	 *
	 * @param idCampo          idCampo
	 * @param valorNoPermitido valor no permitido
	 */
	public ValorPosiblePalabraReservadaException(final String idCampo, final String valorNoPermitido) {
		super("El valor del camp de formulari " + idCampo + " té caràcters no permesos: " + valorNoPermitido);
	}

}
