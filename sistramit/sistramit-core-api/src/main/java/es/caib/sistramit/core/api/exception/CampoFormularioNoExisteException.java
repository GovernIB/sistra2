package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepcion lanzada cuando se intenta guardar un campo de un formulario que no
 * existe.
 */
@SuppressWarnings("serial")
public final class CampoFormularioNoExisteException extends ServiceRollbackException {

	/*
	 * CampoFormularioNoExisteException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor CampoFormularioNoExisteException.
	 *
	 * @param idFormulario Parámetro id formulario
	 * @param idCampo      Parámetro id campo
	 */
	public CampoFormularioNoExisteException(final String idFormulario, final String idCampo) {
		super("Camp " + idCampo + " no existeix en formulari " + idFormulario);
	}

}
