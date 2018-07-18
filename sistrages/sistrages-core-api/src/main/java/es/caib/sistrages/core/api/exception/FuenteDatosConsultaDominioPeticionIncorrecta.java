package es.caib.sistrages.core.api.exception;

import es.caib.sistrages.core.api.model.comun.ListaPropiedades;

/**
 * Excepcion que indica que la peticion es incorrecta
 * 
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FuenteDatosConsultaDominioPeticionIncorrecta extends ServiceRollbackException {

	public FuenteDatosConsultaDominioPeticionIncorrecta(final String messageSNRE, final ListaPropiedades detallesSNRE) {
		super(messageSNRE, detallesSNRE);
	}

	public FuenteDatosConsultaDominioPeticionIncorrecta(final String messageSNRE, final Throwable causeSNRE,
			final ListaPropiedades detallesSNRE) {
		super(messageSNRE, causeSNRE, detallesSNRE);
	}

	public FuenteDatosConsultaDominioPeticionIncorrecta(final String messageSNRE, final Throwable causeSNRE) {
		super(messageSNRE, causeSNRE);
	}

	public FuenteDatosConsultaDominioPeticionIncorrecta(final String messageSNRE) {
		super(messageSNRE);
	}

}
