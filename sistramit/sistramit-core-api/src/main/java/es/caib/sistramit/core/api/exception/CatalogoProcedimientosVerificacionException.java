package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que hay un error en la verificacion de catalogo
 * procedimientos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class CatalogoProcedimientosVerificacionException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor CatalogoProcedimientosVerificacionException.
	 *
	 * @param pMessage Mensaje de error.
	 */
	public CatalogoProcedimientosVerificacionException(final String pMessage) {
		super(pMessage);
	}

}
