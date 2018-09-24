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
     * retorna el idioma especicficado. si se indica el idi por defecto, si no
     * se encuentra el idioma esperado se retorna el idioma por defecto, si no
     * retorna el primer idioma.
     *
     * @param ori
     * @param idioma
     * @param idiDefecto
     * @return
     */
    public static String generarLiteralIdioma(final Literal ori,
            final String idioma) {
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

    public static RListaParametros toListaParametrosFromPropiedad(
            final List<Propiedad> lp) {
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
                plugin.setTipo(
                        p.getTipo() == null ? null : p.getTipo().toString());
                plugin.setClassname(p.getClassname());
                plugin.setPropiedades(
                        toListaParametrosFromPropiedad(p.getPropiedades()));
                plugin.setPrefijoPropiedades(p.getPrefijoPropiedades());
                plugins.add(plugin);
            }
        }
        return plugins;
    }

    public static RScript generaScript(final Script origen) {
        RScript res = null;
        if (origen != null) {
            res = new RScript();
            res.setScript(origen.getContenido());
            // TODO: añadir los literales
        }
        return res;
    }

}
