package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando se intenta guardar un campo de un formulario con un
 * tipo de valor que no le corresponde.
 *
 */
@SuppressWarnings("serial")
public final class TipoValorCampoFormularioException extends ServiceRollbackException {

	/*
	 * TipoValorCampoFormularioException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor TipoValorCampoFormularioException.
	 *
	 * @param idCampo Parámetro id campo
	 */
	public TipoValorCampoFormularioException(final String idCampo) {
		super("El valor del camp de formulari " + idCampo + " no és del tipus correcte");
	}

}
