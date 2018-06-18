package es.caib.sistrages.rest.api.util;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.rest.api.Aviso;
import es.caib.sistrages.rest.api.AvisosEntidad;
import es.caib.sistrages.rest.api.Componente;
import es.caib.sistrages.rest.api.ComponenteSeccion;
import es.caib.sistrages.rest.api.ComponenteSelector;
import es.caib.sistrages.rest.api.ComponenteTextbox;
import es.caib.sistrages.rest.api.ConfiguracionGlobal;
import es.caib.sistrages.rest.api.ConfiguracionEntidad;
import es.caib.sistrages.rest.api.FormularioInterno;
import es.caib.sistrages.rest.api.FormularioTramite;
import es.caib.sistrages.rest.api.LineaComponentes;
import es.caib.sistrages.rest.api.ListaParametros;
import es.caib.sistrages.rest.api.Literal;
import es.caib.sistrages.rest.api.LiteralIdioma;
import es.caib.sistrages.rest.api.OpcionFormularioSoporte;
import es.caib.sistrages.rest.api.PaginaFormulario;
import es.caib.sistrages.rest.api.PasoTramitacion;
import es.caib.sistrages.rest.api.PasoTramitacionDebeSaber;
import es.caib.sistrages.rest.api.PasoTramitacionRellenar;
import es.caib.sistrages.rest.api.Plugin;
import es.caib.sistrages.rest.api.ValorParametro;
import es.caib.sistrages.rest.api.ValoresDominio;
import es.caib.sistrages.rest.api.VersionTramite;

public class XTestJson {

    public static void main(final String[] args) throws Exception {

        final VersionTramite vt = crearVersionTramite();

        final String json = JsonUtil.toJson(vt);
        System.out.println(json);

        final VersionTramite vt2 = (VersionTramite) JsonUtil.fromJson(json,
                VersionTramite.class);

        System.out.println(vt2.getIdentificador());
    }

    public static ConfiguracionGlobal crearConfiguracionGlobal() {
        final ConfiguracionGlobal c = new ConfiguracionGlobal();
        c.setPropiedades(crearListaParametros());
        c.setPlugins(crearPlugins());
        return c;
    }

    public static Literal generarLiteral() {

        final Literal li = new Literal();

        final List<LiteralIdioma> literales = new ArrayList<>();
        LiteralIdioma l = null;

        l = new LiteralIdioma();
        l.setIdioma("es");
        l.setDescripcion("literal es");
        literales.add(l);

        l = new LiteralIdioma();
        l.setIdioma("ca");
        l.setDescripcion("literal ca");
        literales.add(l);

        li.setLiterales(literales);

        return li;
    }

    public static ConfiguracionEntidad crearEntidad() {

        OpcionFormularioSoporte opc;

        final List<OpcionFormularioSoporte> opciones = new ArrayList<>();

        opc = new OpcionFormularioSoporte();
        opc.setTipo(generarLiteral());
        opc.setDescripcion(generarLiteral());
        opc.setDestinatario("R");
        opciones.add(opc);

        opc = new OpcionFormularioSoporte();
        opc.setTipo(generarLiteral());
        opc.setDescripcion(generarLiteral());
        opc.setDestinatario("L");
        opc.setListaEmails("email1;email2");
        opciones.add(opc);

        final ConfiguracionEntidad e = new ConfiguracionEntidad();

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

    private static List<Plugin> crearPlugins() {
        final List<Plugin> plugins = new ArrayList<>();

        Plugin plugin;

        plugin = new Plugin();
        plugin.setTipo("Tipo1");
        plugin.setClassname("Clase1");
        plugin.setParametros(crearListaParametros());
        plugins.add(plugin);

        plugin = new Plugin();
        plugin.setTipo("Tipo2");
        plugin.setClassname("Clase2");
        plugin.setParametros(crearListaParametros());
        plugins.add(plugin);

        return plugins;
    }

    public static ListaParametros crearListaParametros() {
        final ListaParametros params = new ListaParametros();
        params.setParametros(new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            final ValorParametro p = new ValorParametro();
            p.setCodigo("P" + i);
            p.setValor("V" + i);
            params.getParametros().add(p);
        }
        return params;
    }

    public static ValoresDominio crearValoresDominio() {
        final ValoresDominio vd = new ValoresDominio();
        for (int i = 0; i < 5; i++) {
            vd.addFila();
            for (int j = 0; j < 3; j++) {
                vd.setValor(i + 1, "COL" + j, "VAL" + (i + 1) + "-" + j);
            }
        }
        return vd;
    }

    public static VersionTramite crearVersionTramite() {
        final List<PasoTramitacion> pasos = new ArrayList<>();
        pasos.add(crearPasoDebeSaber());
        pasos.add(crearPasoRellenar());

        final VersionTramite vt = new VersionTramite();
        vt.setIdentificador("T1");
        vt.setVersion(1);
        vt.setPasos(pasos);
        return vt;
    }

    private static PasoTramitacionDebeSaber crearPasoDebeSaber() {
        final PasoTramitacionDebeSaber pd = new PasoTramitacionDebeSaber();
        pd.setIdentificador("P" + System.currentTimeMillis());
        pd.setTipo("DS");
        pd.setInstruccionesInicio("debe saber");
        return pd;
    }

    private static PasoTramitacionRellenar crearPasoRellenar() {
        final PasoTramitacionRellenar pr = new PasoTramitacionRellenar();
        final List<FormularioTramite> fl = new ArrayList<>();
        fl.add(crearFormularioTramite());
        fl.add(crearFormularioTramite());
        pr.setIdentificador("P" + System.currentTimeMillis());
        pr.setTipo("RF");
        pr.setFormularios(fl);
        return pr;
    }

    private static FormularioTramite crearFormularioTramite() {
        FormularioTramite f;
        f = new FormularioTramite();
        f.setIdentificador("F" + System.currentTimeMillis());
        f.setDescripcion("Formulario");
        f.setFormularioInterno(crearFormularioDisenyo());
        return f;
    }

    private static FormularioInterno crearFormularioDisenyo() {
        final List<PaginaFormulario> paginas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            paginas.add(crearPaginaFormulario());
        }

        final FormularioInterno fd = new FormularioInterno();
        fd.setPaginas(paginas);
        return fd;
    }

    private static PaginaFormulario crearPaginaFormulario() {
        PaginaFormulario p;
        p = new PaginaFormulario();
        p.setHtmlB64("HTML PAGINA");
        final List<LineaComponentes> lineas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final LineaComponentes li = new LineaComponentes();
            li.setComponentes(createLineaComponentes());
            lineas.add(li);
        }
        p.setLineas(lineas);
        return p;
    }

    private static List<Componente> createLineaComponentes() {
        final List<Componente> lc = new ArrayList<>();

        final ComponenteSeccion ce = new ComponenteSeccion();
        ce.setIdentificador("ID" + System.currentTimeMillis());
        ce.setTipo("ETQ");
        ce.setEtiqueta("Etiqueta");

        lc.add(ce);

        final ComponenteTextbox ct = new ComponenteTextbox();
        ct.setIdentificador("ID" + System.currentTimeMillis());
        ct.setTipo("ETQ");
        ct.setEtiqueta("Etiqueta");

        lc.add(ct);

        final ComponenteSelector cs = new ComponenteSelector();
        cs.setIdentificador("ID" + System.currentTimeMillis());
        cs.setTipo("SEL");
        cs.setEtiqueta("Selector");

        lc.add(cs);
        return lc;
    }

    public static AvisosEntidad crearAvisos() {
        final List<Aviso> lista = new ArrayList<>();
        lista.add(generarAviso());
        lista.add(generarAviso());
        lista.add(generarAviso());

        final AvisosEntidad res = new AvisosEntidad();
        res.setAvisos(lista);
        return res;
    }

    private static Aviso generarAviso() {
        final Aviso a = new Aviso();
        a.setMensaje(generarLiteral());
        a.setTipo("L");
        a.setFechaInicio("20180615000000");
        a.setFechaFin("20180620000000");
        a.setBloquear(true);
        a.setListaVersiones("TRAM1-1;TRAM2-2");
        return a;
    }

}
