package es.caib.sistra2.commons.plugins.formulario.api;

/**
 * Excepción en plugin formulario.
 *
 * @author Indra
 *
 */
public class FormularioPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public FormularioPluginException() {
		super();
	}

	public FormularioPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public FormularioPluginException(final String arg0) {
		super(arg0);
	}

	public FormularioPluginException(final Throwable arg0) {
		super(arg0);
	}

}
