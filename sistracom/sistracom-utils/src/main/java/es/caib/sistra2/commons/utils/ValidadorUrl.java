package es.caib.sistra2.commons.utils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Validacion URLs.
 *
 * @author Indra
 *
 */
public class ValidadorUrl {

	/**
	 * Singleton.
	 */
	private final static ValidadorUrl INSTANCE = new ValidadorUrl();

	/**
	 * Constructor.
	 */
	private ValidadorUrl() {
		super();
	}

	/**
	 * Singleton.
	 *
	 * @return singleton
	 */
	public static ValidadorUrl getInstance() {
		return INSTANCE;
	}

	/**
	 * Valida si la URL pertenece a los dominios indicados.
	 *
	 * @param url
	 *            Url a validar
	 * @param domains
	 *            Lista dominios
	 * @return true si pertenece a los dominios
	 */
	public boolean validateDomains(final String url, final String[] domains) {
		boolean valid = false;
		try {
			final URI uri = new URI(url);
			for (final String d : domains) {
				if (uri.getHost().endsWith("." + d)) {
					valid = true;
					break;
				}
			}
		} catch (final URISyntaxException e) {
			valid = false;
		}
		return valid;
	}
}
