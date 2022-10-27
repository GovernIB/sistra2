package es.caib.sistrages.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RLiteral;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.RLiteralScript;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistrages.rest.api.interna.RValorParametro;

public class AdapterUtils {

	private static final String LITERAL_DEFECTO = "";
	public static final String SEPARADOR_IDIOMAS = ";";
	public static final String SEPARADOR_EXTENSIONES = SEPARADOR_IDIOMAS;
	public static final String FORMATO_FECHA = "yyyyMMddHHmmss";

	private AdapterUtils() {
		throw new IllegalStateException("No se puede instanciar la clase");
	}

	public static RLiteral generarLiteral(final Literal ori) {
		final RLiteral li = new RLiteral();
		if (ori != null) {
			final List<RLiteralIdioma> literales = new ArrayList<>();
			RLiteralIdioma l = null;
			for (final Traduccion t : ori.getTraducciones()) {
				l = new RLiteralIdioma();
				l.setIdioma(t.getIdioma());
				l.setDescripcion(t.getLiteral());
				literales.add(l);
			}
			li.setLiterales(literales);
		}
		return li;
	}

	/**
	 * retorna el idioma especicficado. si se indica el idi por defecto, si no se
	 * encuentra el idioma esperado se retorna el idioma por defecto, si no retorna
	 * el primer idioma.
	 *
	 * @param ori
	 * @param idioma
	 * @param idiDefecto
	 * @return
	 */
	public static String generarLiteralIdioma(final Literal ori, final String idioma) {
		String res = LITERAL_DEFECTO;
		if (ori != null) {
			for (final Traduccion t : ori.getTraducciones()) {
				if (idioma.equals(t.getIdioma())) {
					res = t.getLiteral();
					break;
				}
			}
		}
		return res;
	}

	public static RListaParametros toListaParametrosFromPropiedad(final List<Propiedad> lp) {
		RListaParametros params = null;
		if (lp != null) {
			params = new RListaParametros();
			params.setParametros(new ArrayList<>());
			for (final Propiedad p : lp) {
				final RValorParametro vp = new RValorParametro();
				vp.setCodigo(p.getCodigo() + "");
				vp.setValor(p.getValor());
				params.getParametros().add(vp);
			}
		}
		return params;
	}

	public static List<RPlugin> crearPlugins(final List<Plugin> lpg) {
		List<RPlugin> plugins = null;
		if (lpg != null) {
			plugins = new ArrayList<>();
			for (final Plugin p : lpg) {
				RPlugin plugin;
				plugin = new RPlugin();
				plugin.setTipo(p.getTipo() == null ? null : p.getTipo().toString());
				plugin.setClassname(p.getClassname());
				plugin.setPropiedades(toListaParametrosFromPropiedad(p.getPropiedades()));
				if (StringUtils.isNotBlank(p.getPrefijoPropiedades())) {
					plugin.setPrefijoPropiedades(p.getPrefijoPropiedades().trim());
					if (!plugin.getPrefijoPropiedades().endsWith(".")) {
						plugin.setPrefijoPropiedades(plugin.getPrefijoPropiedades() + ".");
					}
				}
				plugins.add(plugin);
			}
		}
		return plugins;
	}

	public static RScript generaScript(final Script origen, final String idioma) {
		RScript res = null;
		if (origen != null) {
			res = new RScript();
			res.setScript(origen.getContenido());
			List<RLiteralScript> literales = null;
			if (origen.getMensajes() != null) {
				literales = new ArrayList<>();
				for (final LiteralScript mensaje : origen.getMensajes()) {
					final RLiteralScript rliteral = new RLiteralScript();
					rliteral.setIdentificador(mensaje.getIdentificador());
					rliteral.setLiteral(generarLiteralIdioma(mensaje.getLiteral(), idioma));
					literales.add(rliteral);
				}
			}
			res.setLiterales(literales);
		}
		return res;
	}

	public static RScript generaScript(final List<Script> origenes, final String idioma) {
		RScript res = null;
		if (origenes != null && !origenes.isEmpty()) {
			res = new RScript();
			res.setScript("");
			res.setLiterales(null);
			List<RLiteralScript> literales = new ArrayList<>();
			for(Script origen : origenes) {
				res.setScript(res.getScript()+ " " +origen.getContenido());
				if (origen.getMensajes() != null) {
					for (final LiteralScript mensaje : origen.getMensajes()) {
						final RLiteralScript rliteral = new RLiteralScript();
						rliteral.setIdentificador(mensaje.getIdentificador());
						rliteral.setLiteral(generarLiteralIdioma(mensaje.getLiteral(), idioma));
						literales.add(rliteral);
					}
				}
			}
			if (!literales.isEmpty()) {
				res.setLiterales(literales);
			}
		}
		return res;
	}

}
