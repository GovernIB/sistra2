package es.caib.sistrages.core.api.exception;

import es.caib.sistrages.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que no esta bien configurado la parte de procesos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ProcesoConfiguracionException extends ServiceRollbackException {

	public ProcesoConfiguracionException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public ProcesoConfiguracionException(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public ProcesoConfiguracionException(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public ProcesoConfiguracionException(final String messageSNRE) {
		super(messageSNRE);
	}

}
