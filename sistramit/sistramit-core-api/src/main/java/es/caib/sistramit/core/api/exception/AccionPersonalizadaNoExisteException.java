package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando se intenta guardar formulario y se especifica una
 * accion que no existe.
 *
 */
@SuppressWarnings("serial")
public final class AccionPersonalizadaNoExisteException extends ServiceRollbackException {

	/*
	 * AccionPersonalizadaNoExisteException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor AccionPersonalizadaNoExisteException.
	 *
	 * @param idFormulario
	 *            Parámetro id formulario
	 * @param codigoAccion
	 *            Parámetro codigo accion
	 */
	public AccionPersonalizadaNoExisteException(final String idFormulario, final String codigoAccion) {
		super("No existe codigo de accion " + codigoAccion + " para el formulario " + idFormulario);
	}

}
