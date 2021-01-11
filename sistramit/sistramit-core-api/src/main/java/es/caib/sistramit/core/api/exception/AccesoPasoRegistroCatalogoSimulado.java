package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que no se puede acceder al paso registro con el catalogo
 * simulado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AccesoPasoRegistroCatalogoSimulado extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// ErrorConfiguracionException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor AccesoPasoRegistroCatalogoSimulado.
	 *
	 * @param pMessage
	 *                     Mensaje de error.
	 */
	public AccesoPasoRegistroCatalogoSimulado() {
		super("No se puede acceder a paso registro si se simula acceso a catalogo de servicios");
	}

}
