package es.caib.sistrages.core.api.exception;

import es.caib.sistrages.core.api.model.comun.ListaPropiedades;

@SuppressWarnings("serial")
public class NotAccessAllowedException extends ServiceRollbackException {

	public NotAccessAllowedException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public NotAccessAllowedException(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public NotAccessAllowedException(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public NotAccessAllowedException(final String messageSNRE) {
		super(messageSNRE);
	}

}
