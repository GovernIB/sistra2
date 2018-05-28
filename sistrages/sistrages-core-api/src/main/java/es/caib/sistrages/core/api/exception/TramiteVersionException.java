package es.caib.sistrages.core.api.exception;

import es.caib.sistrages.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que faltan datos.
 * 
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TramiteVersionException extends ServiceRollbackException {

	public TramiteVersionException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public TramiteVersionException(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public TramiteVersionException(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public TramiteVersionException(final String messageSNRE) {
		super(messageSNRE);
	}

}
