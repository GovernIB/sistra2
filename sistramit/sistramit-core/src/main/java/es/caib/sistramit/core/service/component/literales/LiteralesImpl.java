package es.caib.sistramit.core.service.component.literales;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

/**
 * Componente para recuperar literales de la capa de negocio.
 *
 * El fichero de literales están estructurado por secciones para que los
 * diferentes elementos no compartan literales.
 *
 * @author Indra
 *
 */
@Component("literales")
public final class LiteralesImpl implements Literales {

	/**
	 * Bundle con los literales.
	 */
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Override
	public String getLiteral(final String pPrefijo, final String pCodigo, final String pIdioma) {
		String res = null;
		try {
			res = messageSource.getMessage(pPrefijo + "." + pCodigo, null, new Locale(pIdioma));
		} catch (final NoSuchMessageException ex) {
			res = "¿¿" + pPrefijo + "." + pCodigo + "??";
		}
		return res;
	}

	@Override
	public String getLiteral(final String pPrefijo, final String pCodigo, final String[] pParametros,
			final String pIdioma) {
		String res = null;
		try {
			res = messageSource.getMessage(pPrefijo + "." + pCodigo, pParametros, new Locale(pIdioma));
		} catch (final NoSuchMessageException ex) {
			res = "¿¿" + pPrefijo + "." + pCodigo + "??";
		}
		return res;
	}

}
