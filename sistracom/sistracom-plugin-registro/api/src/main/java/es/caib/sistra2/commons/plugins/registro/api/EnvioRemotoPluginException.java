package es.caib.sistra2.commons.plugins.registro.api;

/**
 * Excepción en plugin envío.
 *
 * @author Indra
 *
 */
public class EnvioRemotoPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public EnvioRemotoPluginException() {
		super();
	}

	public EnvioRemotoPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public EnvioRemotoPluginException(final String arg0) {
		super(arg0);
	}

	public EnvioRemotoPluginException(final Throwable arg0) {
		super(arg0);
	}

}
