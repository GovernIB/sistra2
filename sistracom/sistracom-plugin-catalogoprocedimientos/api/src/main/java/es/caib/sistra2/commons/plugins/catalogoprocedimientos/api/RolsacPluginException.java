package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

/**
 * Excepción en plugin rolsac.
 *
 * @author Indra
 *
 */
public class RolsacPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public RolsacPluginException() {
		super();
	}

	public RolsacPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public RolsacPluginException(final String arg0) {
		super(arg0);
	}

	public RolsacPluginException(final Throwable arg0) {
		super(arg0);
	}

}
