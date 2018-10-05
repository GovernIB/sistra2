package es.caib.sistra2.commons.plugins.firmacliente.api;

/**
 * Excepción en plugin firma.
 *
 * @author Indra
 *
 */
public class FirmaPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public FirmaPluginException() {
		super();
	}

	public FirmaPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public FirmaPluginException(final String arg0) {
		super(arg0);
	}

	public FirmaPluginException(final Throwable arg0) {
		super(arg0);
	}

}
