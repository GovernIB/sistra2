package es.caib.sistra2.commons.plugins.firmacliente.api;

/**
 * Excepción al conectar con plugin de firma.
 *
 * @author Indra
 *
 */
public class FirmaPluginConnectException extends Exception {

	private static final long serialVersionUID = 1L;

	public FirmaPluginConnectException() {
		super();
	}

	public FirmaPluginConnectException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public FirmaPluginConnectException(final String arg0) {
		super(arg0);
	}

	public FirmaPluginConnectException(final Throwable arg0) {
		super(arg0);
	}

}
