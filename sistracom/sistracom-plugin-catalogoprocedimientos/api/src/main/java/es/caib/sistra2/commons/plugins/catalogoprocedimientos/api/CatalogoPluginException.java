package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

/**
 * Excepción en plugin catálogo.
 *
 * @author Indra
 *
 */
public class CatalogoPluginException extends Exception {

	private static final long serialVersionUID = 1L;

	public CatalogoPluginException() {
		super();
	}

	public CatalogoPluginException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public CatalogoPluginException(final String arg0) {
		super(arg0);
	}

	public CatalogoPluginException(final Throwable arg0) {
		super(arg0);
	}

}
