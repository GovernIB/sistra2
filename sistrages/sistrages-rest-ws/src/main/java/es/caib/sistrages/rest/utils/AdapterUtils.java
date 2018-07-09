package es.caib.sistrages.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

	private final static String literalDefecto= "??";
	private final static String literalDefectoError= "?err?";
	public final static String SEPARADOR_IDIOMAS= ";";
	public static final String SEPARADOR_EXTENSIONES = SEPARADOR_IDIOMAS;



	public static RLiteral generarLiteral(Literal ori) {
		final RLiteral li = new RLiteral();
		try {
			final List<RLiteralIdioma> literales = new ArrayList<>();
			RLiteralIdioma l = null;
			for (Traduccion t : ori.getTraducciones()) {
				l = new RLiteralIdioma();
				l.setIdioma(t.getIdioma());
				l.setDescripcion(t.getLiteral());
				literales.add(l);
			}
			li.setLiterales(literales);
		} catch (Exception e) {
			return null;
		}
		return li;
	}

	/**
	 * retorna el idioma especicficado. si se indica el idi por defecto, si no se
	 * encuentra el idioma esperado se retorna el idioma por defecto, si no retorna
	 * el primer idioma
	 *
	 * @param ori
	 * @param idioma
	 * @param idiDefecto
	 * @return
	 */
	public static String generarLiteralIdioma(Literal ori, String idioma) {
		try {
			for (Traduccion t : ori.getTraducciones()) {
				if (t.getIdioma().equals(idioma)) {
					return t.getLiteral();
				}
			}
		} catch (Exception e) {
			return literalDefectoError;
		}
		return literalDefecto;
	}

	public static RListaParametros toListaParametrosFromPropiedad(List<Propiedad> lp) {
		final RListaParametros params = new RListaParametros();
		try {
			params.setParametros(new ArrayList<>());
			for (Propiedad p : lp) {
				final RValorParametro vp = new RValorParametro();
				vp.setCodigo(p.getCodigo() + "");
				vp.setValor(p.getValor());
				params.getParametros().add(vp);
			}
		} catch (Exception e) {
			return null;
		}
		return params;
	}

	public static List<RPlugin> crearPlugins(List<Plugin> lpg) {
		final List<RPlugin> plugins = new ArrayList<>();
		try {
			for (Plugin p : lpg) {
				RPlugin plugin;
				plugin = new RPlugin();
				plugin.setTipo(p.getTipo().name());
				plugin.setClassname(p.getClassname());
				plugin.setParametros(toListaParametrosFromPropiedad(p.getPropiedades()));
				plugins.add(plugin);
			}
		} catch (Exception e) {
			return null;
		}
		return plugins;
	}

	public static RScript generaScript(Script origen) {
		RScript s = new RScript();
		s.setScript(origen.getContenido());
		//TODO: añadir los literales
		return s;
	}

}
