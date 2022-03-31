package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Indica que no existe el flujo de formulario indicado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class NoExisteFlujoFormularioException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor NoExisteFlujoFormularioException.
	 *
	 * @param idSesionFormulario idSesionFormulario
	 */
	public NoExisteFlujoFormularioException(final String idSesionFormulario) {
		super("No existeix fluxe de formulari amb id: " + idSesionFormulario);
	}

}
