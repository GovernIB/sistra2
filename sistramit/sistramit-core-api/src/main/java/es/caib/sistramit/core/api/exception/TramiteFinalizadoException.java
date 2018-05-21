package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que el trámite esta finalizado y no se puede acceder.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TramiteFinalizadoException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// TramiteFinalizadoException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor TramiteFinalizadoException.
	 *
	 * @param pIdSesionTramite
	 *            Id sesion tramite.
	 */
	public TramiteFinalizadoException(final String pIdSesionTramite) {
		super("Tramite " + pIdSesionTramite + " finalizado");
	}

}
