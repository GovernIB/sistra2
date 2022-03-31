package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que el id sesion de trámite no existe.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TramiteNoExisteException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// TramiteNoExisteException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor TramiteNoExisteException.
	 *
	 * @param pIdSesionTramite Id sesion tramite.
	 */
	public TramiteNoExisteException(final String pIdSesionTramite) {
		super("Sessió de tramitació " + pIdSesionTramite + " no existeix en persistència");
	}

}
