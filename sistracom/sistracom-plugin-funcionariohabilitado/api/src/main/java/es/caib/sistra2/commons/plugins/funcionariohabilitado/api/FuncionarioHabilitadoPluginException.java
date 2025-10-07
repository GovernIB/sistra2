package es.caib.sistra2.commons.plugins.funcionariohabilitado.api;

/**
 * Excepción en plugin digitalización.
 *
 * @author Indra
 *
 */
public class FuncionarioHabilitadoPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public FuncionarioHabilitadoPluginException() {
		super();
	}

	public FuncionarioHabilitadoPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public FuncionarioHabilitadoPluginException(final String arg0) {
		super(arg0);
	}

	public FuncionarioHabilitadoPluginException(final Throwable arg0) {
		super(arg0);
	}

}
