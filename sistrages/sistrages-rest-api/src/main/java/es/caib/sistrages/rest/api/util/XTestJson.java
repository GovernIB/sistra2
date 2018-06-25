package es.caib.sistrages.rest.api.util;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.rest.api.interna.RAviso;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteSeccion;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RLineaComponentes;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RLiteral;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionDebeSaber;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;

public class XTestJson {

    public static void main(final String[] args) throws Exception {

        final RVersionTramite vt = crearVersionTramite();

        final String json = JsonUtil.toJson(vt);
        System.out.println(json);

        final RVersionTramite vt2 = (RVersionTramite) JsonUtil.fromJson(json,
                RVersionTramite.class);

        System.out.println(vt2.getIdentificador());
    }

    public static RConfiguracionGlobal crearConfiguracionGlobal() {
        final RConfiguracionGlobal c = new RConfiguracionGlobal();
        c.setPropiedades(crearListaParametros());
        c.setPlugins(crearPlugins());
        return c;
    }

    public static RLiteral generarLiteral() {

        final RLiteral li = new RLiteral();

        final List<RLiteralIdioma> literales = new ArrayList<>();
        RLiteralIdioma l = null;

        l = new RLiteralIdioma();
        l.setIdioma("es");
        l.setDescripcion("literal es");
        literales.add(l);

        l = new RLiteralIdioma();
        l.setIdioma("ca");
        l.setDescripcion("literal ca");
        literales.add(l);

        li.setLiterales(literales);

        return li;
    }

    public static RConfiguracionEntidad crearEntidad() {

        ROpcionFormularioSoporte opc;

        final List<ROpcionFormularioSoporte> opciones = new ArrayList<>();

        opc = new ROpcionFormularioSoporte();
        opc.setTipo(generarLiteral());
        opc.setDescripcion(generarLiteral());
        opc.setDestinatario("R");
        opciones.add(opc);

        opc = new ROpcionFormularioSoporte();
        opc.setTipo(generarLiteral());
        opc.setDescripcion(generarLiteral());
        opc.setDestinatario("L");
        opc.setListaEmails("email1;email2");
        opciones.add(opc);

        final RConfiguracionEntidad e = new RConfiguracionEntidad();

        e.setIdentificador("E1");
        e.setLogo("pathlogo");
        e.setCss("pathCss");
        e.setEmail("entidad@mail.es");
        e.setContactoHTML(generarLiteral());
        e.setUrlCarpeta(generarLiteral());
        e.setAyudaFormulario(opciones);
        e.setAyudaTelefono("012");
        e.setAyudaUrl("url ayuda");
        e.setPlugins(crearPlugins());

        return e;

    }

    private static List<RPlugin> crearPlugins() {
        final List<RPlugin> plugins = new ArrayList<>();

        RPlugin plugin;

        plugin = new RPlugin();
        plugin.setTipo("Tipo1");
        plugin.setClassname("Clase1");
        plugin.setParametros(crearListaParametros());
        plugins.add(plugin);

        plugin = new RPlugin();
        plugin.setTipo("Tipo2");
        plugin.setClassname("Clase2");
        plugin.setParametros(crearListaParametros());
        plugins.add(plugin);

        return plugins;
    }

    public static RListaParametros crearListaParametros() {
        final RListaParametros params = new RListaParametros();
        params.setParametros(new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            final RValorParametro p = new RValorParametro();
            p.setCodigo("P" + i);
            p.setValor("V" + i);
            params.getParametros().add(p);
        }
        return params;
    }

    public static RValoresDominio crearValoresDominio() {
        final RValoresDominio vd = new RValoresDominio();
        for (int i = 0; i < 5; i++) {
            vd.addFila();
            for (int j = 0; j < 3; j++) {
                vd.setValor(i + 1, "COL" + j, "VAL" + (i + 1) + "-" + j);
            }
        }
        return vd;
    }

    public static RVersionTramite crearVersionTramite() {
        final List<RPasoTramitacion> pasos = new ArrayList<>();
        pasos.add(crearPasoDebeSaber());
        pasos.add(crearPasoRellenar());

        final RVersionTramite vt = new RVersionTramite();
        vt.setIdentificador("T1");
        vt.setVersion(1);
        vt.setPasos(pasos);
        return vt;
    }

    private static RPasoTramitacionDebeSaber crearPasoDebeSaber() {
        final RPasoTramitacionDebeSaber pd = new RPasoTramitacionDebeSaber();
        pd.setIdentificador("P" + System.currentTimeMillis());
        pd.setTipo("DS");
        pd.setInstruccionesInicio("debe saber");
        return pd;
    }

    private static RPasoTramitacionRellenar crearPasoRellenar() {
        final RPasoTramitacionRellenar pr = new RPasoTramitacionRellenar();
        final List<RFormularioTramite> fl = new ArrayList<>();
        fl.add(crearFormularioTramite());
        fl.add(crearFormularioTramite());
        pr.setIdentificador("P" + System.currentTimeMillis());
        pr.setTipo("RF");
        pr.setFormularios(fl);
        return pr;
    }

    private static RFormularioTramite crearFormularioTramite() {
        RFormularioTramite f;
        f = new RFormularioTramite();
        f.setIdentificador("F" + System.currentTimeMillis());
        f.setDescripcion("Formulario");
        f.setFormularioInterno(crearFormularioDisenyo());
        return f;
    }

    private static RFormularioInterno crearFormularioDisenyo() {
        final List<RPaginaFormulario> paginas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            paginas.add(crearPaginaFormulario());
        }

        final RFormularioInterno fd = new RFormularioInterno();
        fd.setPaginas(paginas);
        return fd;
    }

    private static RPaginaFormulario crearPaginaFormulario() {
        RPaginaFormulario p;
        p = new RPaginaFormulario();
        p.setHtmlB64("HTML PAGINA");
        final List<RLineaComponentes> lineas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final RLineaComponentes li = new RLineaComponentes();
            li.setComponentes(createLineaComponentes());
            lineas.add(li);
        }
        p.setLineas(lineas);
        return p;
    }

    private static List<RComponente> createLineaComponentes() {
        final List<RComponente> lc = new ArrayList<>();

        final RComponenteSeccion ce = new RComponenteSeccion();
        ce.setIdentificador("ID" + System.currentTimeMillis());
        ce.setTipo("ETQ");
        ce.setEtiqueta("Etiqueta");

        lc.add(ce);

        final RComponenteTextbox ct = new RComponenteTextbox();
        ct.setIdentificador("ID" + System.currentTimeMillis());
        ct.setTipo("ETQ");
        ct.setEtiqueta("Etiqueta");

        lc.add(ct);

        final RComponenteSelector cs = new RComponenteSelector();
        cs.setIdentificador("ID" + System.currentTimeMillis());
        cs.setTipo("SEL");
        cs.setEtiqueta("Selector");

        lc.add(cs);
        return lc;
    }

    public static RAvisosEntidad crearAvisos() {
        final List<RAviso> lista = new ArrayList<>();
        lista.add(generarAviso());
        lista.add(generarAviso());
        lista.add(generarAviso());

        final RAvisosEntidad res = new RAvisosEntidad();
        res.setAvisos(lista);
        return res;
    }

    private static RAviso generarAviso() {
        final RAviso a = new RAviso();
        a.setMensaje(generarLiteral());
        a.setTipo("L");
        a.setFechaInicio("20180615000000");
        a.setFechaFin("20180620000000");
        a.setBloquear(true);
        a.setListaVersiones("TRAM1-1;TRAM2-2");
        return a;
    }

}
