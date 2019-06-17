package es.caib.sistrages.core.api.exception;

import es.caib.sistrages.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que ha habido algún error en la importacion.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ImportacionError extends ServiceRollbackException {

	public ImportacionError(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public ImportacionError(final String messageSNRE, final Throwable causeSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public ImportacionError(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public ImportacionError(final String messageSNRE) {
		super(messageSNRE);
	}

}
