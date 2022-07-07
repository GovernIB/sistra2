package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción si el QAA del trámite es superior al del usuario cuando se recarga
 * el trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class QaaRecargaTramiteException extends ServiceRollbackException {

	/*
	 * QaaException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param idSesionTramitacion
	 *                                idSesionTramitacion
	 */
	public QaaRecargaTramiteException(final String idSesionTramitacion) {

		super("Nivell de QAA del tràmit és superior al del usuari autenticat");

		final ListaPropiedades detallesSRE = new ListaPropiedades();
		detallesSRE.addPropiedad("idSesionTramitacion", idSesionTramitacion);
		this.setDetallesExcepcion(detallesSRE);

	}

}
