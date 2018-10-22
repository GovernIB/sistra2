package es.caib.sistrahelp.core.api.exception;

import es.caib.sistrahelp.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica problemas con la gestión de ficheros externo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FicheroExternoException extends ServiceRollbackException {

	public FicheroExternoException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public FicheroExternoException(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public FicheroExternoException(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public FicheroExternoException(final String messageSNRE) {
		super(messageSNRE);
	}

}
