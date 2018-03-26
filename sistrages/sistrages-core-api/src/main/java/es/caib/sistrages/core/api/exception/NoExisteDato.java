package es.caib.sistrages.core.api.exception;

import es.caib.sistrages.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que no existe el dato.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class NoExisteDato extends ServiceRollbackException {

	public NoExisteDato(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public NoExisteDato(final String messageSNRE, final Throwable causeSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public NoExisteDato(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public NoExisteDato(final String messageSNRE) {
		super(messageSNRE);
	}

}
