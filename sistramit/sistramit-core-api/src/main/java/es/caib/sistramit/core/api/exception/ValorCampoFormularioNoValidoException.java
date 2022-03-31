package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando se intenta deserializar un valor de campo y no tiene
 * un formato correcto.
 *
 */
@SuppressWarnings("serial")
public final class ValorCampoFormularioNoValidoException extends ServiceRollbackException {

	/*
	 * ValorCampoFormularioNoValidoException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor ValorCampoFormularioNoValidoException.
	 *
	 * @param idCampo          idCampo
	 * @param valorSerializado valorSerializado
	 */
	public ValorCampoFormularioNoValidoException(final String idCampo, final String valorSerializado) {
		super("El valor serialitzat del camp de formulari " + idCampo + " no és correcte: " + valorSerializado);
	}

}
