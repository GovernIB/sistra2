package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando el valor de un campo (o valor posible) contiene
 * caracteres no permitidos.
 *
 */
@SuppressWarnings("serial")
public final class ValorCampoFormularioCaracteresNoPermitidosException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor ValorCampoFormularioCaracteresNoPermitidosException.
	 *
	 * @param idCampo
	 *            idCampo
	 * @param valorNoPermitido
	 *            valor no permitido
	 */
	public ValorCampoFormularioCaracteresNoPermitidosException(final String idCampo, final String valorNoPermitido) {
		super("El valor del campo de formulario " + idCampo + " tiene caracteres no permitidos: " + valorNoPermitido);
	}

}
