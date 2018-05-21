package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción lanzada si se llega al limite de tramitación para un trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class LimiteTramitacionException extends ServiceRollbackException {

	/*
	 * LimiteTramitacionException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param pIdTramite
	 *            Id tramite
	 * @param version
	 *            Version tramite
	 * @param limite
	 *            limite
	 * @param intervalo
	 *            intervalo
	 */
	public LimiteTramitacionException(final String pIdTramite, final int version, final long limite,
			final int intervalo) {

		super("Limite de tramitacion alcanzado");

		final ListaPropiedades detalles = new ListaPropiedades();
		detalles.addPropiedad("idTramite", pIdTramite);
		detalles.addPropiedad("version", Integer.toString(version));
		detalles.addPropiedad("limiteNumero", Long.toString(limite));
		detalles.addPropiedad("limiteIntervalo", Integer.toString(intervalo));

		this.setDetallesExcepcion(detalles);

	}

}
