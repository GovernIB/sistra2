package es.caib.sistra2.commons.plugins.digitalizacion.api;

/**
 * Excepción en plugin digitalización.
 *
 * @author Indra
 *
 */
public class DigitalizacionPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public DigitalizacionPluginException() {
		super();
	}

	public DigitalizacionPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public DigitalizacionPluginException(final String arg0) {
		super(arg0);
	}

	public DigitalizacionPluginException(final Throwable arg0) {
		super(arg0);
	}

}
