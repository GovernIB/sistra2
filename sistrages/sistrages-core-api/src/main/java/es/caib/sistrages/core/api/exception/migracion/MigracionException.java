package es.caib.sistrages.core.api.exception.migracion;

import es.caib.sistrages.core.api.exception.ServiceRollbackException;
import es.caib.sistrages.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que faltan datos.
 * 
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class MigracionException extends ServiceRollbackException {

	public MigracionException(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public MigracionException(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public MigracionException(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public MigracionException(final String messageSNRE) {
		super(messageSNRE);
	}

}
