package es.caib.sistra2.commons.plugins.registro.api;

/**
 * Excepción en plugin registro.
 *
 * @author Indra
 *
 */
public class RegistroPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public RegistroPluginException() {
		super();
	}

	public RegistroPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public RegistroPluginException(final String arg0) {
		super(arg0);
	}

	public RegistroPluginException(final Throwable arg0) {
		super(arg0);
	}

}
