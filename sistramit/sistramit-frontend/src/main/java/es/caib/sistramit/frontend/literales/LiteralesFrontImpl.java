package es.caib.sistramit.frontend.literales;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.model.comun.ConstantesNumero;

/**
 * Componente para recuperar literales de la capa web.
 *
 * El fichero de literales están estructurado por secciones para que los
 * diferentes elementos no compartan literales.
 *
 * @author Indra
 *
 */
@Component("literalesFront")
public final class LiteralesFrontImpl implements LiteralesFront {

	/**
	 * Bundle con los literales.
	 */
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Override
	public Properties getLiteralesSeccion(final String pSeccion, final String pIdioma) {

		final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale(pIdioma),
				new UTF8Control());

		final Properties properties = new Properties();
		final Enumeration<String> keysLiterales = resourceBundle.getKeys();
		while (keysLiterales.hasMoreElements()) {
			final String key = keysLiterales.nextElement();
			final String literal = resourceBundle.getString(key);
			if (key.startsWith(pSeccion)) {

				properties.setProperty(key.substring(key.indexOf(".") + ConstantesNumero.N1, key.length()), literal);
			}
		}

		return properties;
	}

	@Override
	public String getLiteralFront(final String pElemento, final String pCodigo, final String pIdioma) {
		return getLiteralFront(pElemento, pCodigo, pIdioma, "¿¿" + pElemento + "." + pCodigo + "??");
	}

	@Override
	public String getLiteralFront(final String pElemento, final String pCodigo, final String[] pParametros,
			final String pIdioma) {
		String res = null;
		try {
			res = messageSource.getMessage(pElemento + "." + pCodigo, pParametros, new Locale(pIdioma));
		} catch (final NoSuchMessageException ex) {
			res = "¿¿" + pElemento + "." + pCodigo + "??";
		}
		return res;
	}

	@Override
	public String getLiteralFront(final String pElemento, final String pCodigo, final String pIdioma,
			final String pDefecto) {
		String res = null;
		try {
			res = messageSource.getMessage(pElemento + "." + pCodigo, null, new Locale(pIdioma));
		} catch (final NoSuchMessageException ex) {
			res = pDefecto;
		}
		return res;
	}

}
