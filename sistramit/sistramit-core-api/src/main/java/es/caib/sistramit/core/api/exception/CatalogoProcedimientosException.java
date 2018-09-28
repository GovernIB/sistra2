package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Error en catalogo procedimientos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class CatalogoProcedimientosException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor.
	 *
	 * @param pMessage
	 *            Mensaje de error.
	 */
	public CatalogoProcedimientosException(final String pMessage) {
		super(pMessage);
	}

	public CatalogoProcedimientosException(final String pmessageSRE, final Throwable pcauseSRE) {
		super(pmessageSRE, pcauseSRE);
	}

}
