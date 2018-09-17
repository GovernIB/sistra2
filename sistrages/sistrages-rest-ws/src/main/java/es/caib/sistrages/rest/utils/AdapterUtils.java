package es.caib.sistrages.rest.utils;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RLiteral;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistrages.rest.api.interna.RValorParametro;

public class AdapterUtils {

	private AdapterUtils() {
		throw new IllegalStateException("No se puede instanciar la clase");
	}

	private static final String literalDefecto = "??";
	private static final String literalDefectoError = "?err?";
	public static final String SEPARADOR_IDIOMAS = ";";
	public static final String SEPARADOR_EXTENSIONES = SEPARADOR_IDIOMAS;
	public static final String FORMATO_FECHA = "yyyyMMddHHmmss";

	public static RLiteral generarLiteral(final Literal ori) {
		final RLiteral li = new RLiteral();
		try {
			final List<RLiteralIdioma> literales = new ArrayList<>();
			RLiteralIdioma l = null;
			for (final Traduccion t : ori.getTraducciones()) {
				l = new RLiteralIdioma();
				l.setIdioma(t.getIdioma());
				l.setDescripcion(t.getLiteral());
				literales.add(l);
			}
			li.setLiterales(literales);
		} catch (final Exception e) {
			return null;
		}
		return li;
	}

	/**
	 * retorna el idioma especicficado. si se indica el idi por defecto, si no se
	 * encuentra el idioma esperado se retorna el idioma por defecto, si no retorna
	 * el primer idioma. si el literal "ori" es null retorna "literalDefectoError"
	 *
	 * @param ori
	 * @param idioma
	 * @param idiDefecto
	 * @return
	 */
	public static String generarLiteralIdioma(final Literal ori, final String idioma) {
		try {
			for (final Traduccion t : ori.getTraducciones()) {
				if (idioma.equals(t.getIdioma())) {
					return t.getLiteral();
				}
			}
		} catch (final Exception e) {
			return literalDefectoError; // si el literal es null por ejemplo.
		}
		return literalDefecto;
	}

	public static RListaParametros toListaParametrosFromPropiedad(final List<Propiedad> lp) {
		final RListaParametros params = new RListaParametros();
		try {
			params.setParametros(new ArrayList<>());
			for (final Propiedad p : lp) {
				final RValorParametro vp = new RValorParametro();
				vp.setCodigo(p.getCodigo() + "");
				vp.setValor(p.getValor());
				params.getParametros().add(vp);
			}
		} catch (final Exception e) {
			return null;
		}
		return params;
	}

	public static List<RPlugin> crearPlugins(final List<Plugin> lpg) {
		final List<RPlugin> plugins = new ArrayList<>();
		try {
			for (final Plugin p : lpg) {
				RPlugin plugin;
				plugin = new RPlugin();
				plugin.setTipo(p.getTipo() == null ? null : p.getTipo().toString());
				plugin.setClassname(p.getClassname());
				plugin.setPropiedades(toListaParametrosFromPropiedad(p.getPropiedades()));
				plugin.setPrefijoPropiedades(p.getPrefijoPropiedades());
				plugins.add(plugin);
			}
		} catch (final Exception e) {
			return null;
		}
		return plugins;
	}

	public static RScript generaScript(final Script origen) {

		if (origen != null) {
			final RScript s = new RScript();
			s.setScript(origen.getContenido());
			// TODO: añadir los literales
			return s;
		} else {
			return null;
		}

	}

}
