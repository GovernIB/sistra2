package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Indica que no existe el flujo de tramitación indicado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class NoExisteFlujoTramitacionException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor NoExisteFlujoTramitacionException.
	 *
	 * @param cause
	 *            Causa
	 */
	public NoExisteFlujoTramitacionException(final String idSesionTramitacion) {
		super("No existe flujo de tramitación con id: " + idSesionTramitacion);
	}

}
