package es.caib.sistra2.commons.plugins.dominio.rest;

/**
 * Excepción en plugin dominio.
 *
 * @author Indra
 *
 */
public class RDominioPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public RDominioPluginException() {
		super();
	}

	public RDominioPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public RDominioPluginException(final String arg0) {
		super(arg0);
	}

	public RDominioPluginException(final Throwable arg0) {
		super(arg0);
	}

}
