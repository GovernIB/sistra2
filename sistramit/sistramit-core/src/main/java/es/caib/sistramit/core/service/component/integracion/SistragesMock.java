package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RAnexoTramiteAyuda;
import es.caib.sistrages.rest.api.interna.RAnexoTramitePresentacionElectronica;
import es.caib.sistrages.rest.api.interna.RAviso;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteSeccion;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDestinoRegistro;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RLineaComponentes;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RLiteral;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionDebeSaber;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.interna.RVersionTramiteControlAcceso;
import es.caib.sistrages.rest.api.interna.RVersionTramitePropiedades;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;

// TODO PASAR A TEST UNA VEZ SE INTEGRE CON STG

/**
 *
 * Clase con la definición estática de información simulada de Sistrages para
 * testing.
 *
 * @author Indra
 *
 */
public class SistragesMock {

    public static RConfiguracionGlobal crearConfiguracionGlobal() {
        final RConfiguracionGlobal configuracionGlobal = new RConfiguracionGlobal();

        configuracionGlobal.setTimestamp(generateTimestamp());

        final List<RValorParametro> parametros = new ArrayList<>();
        RValorParametro vp;
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.SISTRAMIT_URL.toString());
        vp.setValor("http://localhost:8080/sistramitfront");
        parametros.add(vp);
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS.toString());
        vp.setValor("es,ca,en");
        parametros.add(vp);
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.PLUGINS_PREFIJO.toString());
        vp.setValor("es.caib.sistra2");
        parametros.add(vp);

        final RListaParametros propiedades = new RListaParametros();
        propiedades.setParametros(parametros);
        configuracionGlobal.setPropiedades(propiedades);

        configuracionGlobal.setPlugins(crearPluginsGlobal());

        return configuracionGlobal;
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

    public static RLiteral generarLiteralUrl() {

        final RLiteral li = new RLiteral();

        final List<RLiteralIdioma> literales = new ArrayList<>();
        RLiteralIdioma l = null;

        l = new RLiteralIdioma();
        l.setIdioma("es");
        l.setDescripcion("http://www.google.es/es");
        literales.add(l);

        l = new RLiteralIdioma();
        l.setIdioma("ca");
        l.setDescripcion("http://www.google.es/ca");
        literales.add(l);

        li.setLiterales(literales);

        return li;
    }

    public static RConfiguracionEntidad crearEntidad() {

        ROpcionFormularioSoporte opc;

        final List<ROpcionFormularioSoporte> opciones = new ArrayList<>();

        opc = new ROpcionFormularioSoporte();
        opc.setCodigo(1L);
        opc.setTipo(generarLiteral());
        opc.setDescripcion(generarLiteral());
        opc.setDestinatario("R");
        opciones.add(opc);

        opc = new ROpcionFormularioSoporte();
        opc.setCodigo(2L);
        opc.setTipo(generarLiteral());
        opc.setDescripcion(generarLiteral());
        opc.setDestinatario("L");
        opc.setListaEmails("email1;email2");
        opciones.add(opc);

        final RConfiguracionEntidad e = new RConfiguracionEntidad();
        e.setTimestamp(generateTimestamp());
        e.setIdentificador("E1");
        e.setLogo("/sistramitfront/asistente/imgs/logo-goib.svg");
        // e.setCss("pathCss");
        e.setEmail("entidad@mail.es");
        e.setContactoHTML(generarLiteral());
        e.setUrlCarpeta(generarLiteralUrl());
        e.setAyudaFormulario(opciones);
        e.setAyudaTelefono("012");
        e.setAyudaUrl("url ayuda");
        e.setAvisoLegal(generarLiteral());
        e.setMapaWeb(generarLiteralUrl());
        e.setRss(generarLiteral());
        e.setUrlFacebook("http://facebook");
        e.setUrlInstagram("http://instagram");
        e.setUrlTwitter("http://twitter");
        e.setUrlYoutube("http://youtube");
        e.setPlugins(crearPluginsEntidad());
        e.setInfoLopdHTML(generarLiteral());

        return e;

    }

    private static List<RPlugin> crearPluginsEntidad() {
        final List<RPlugin> plugins = new ArrayList<>();

        RPlugin plugin;

        plugin = new RPlugin();
        plugin.setTipo(TypePluginEntidad.CATALOGO_PROCEDIMIENTOS.toString());
        plugin.setClassname(
                "es.caib.sistra2.commons.plugins.catalogoprocedimientos.mock.CatalogoProcedimientosPluginMock");
        plugin.setPrefijoPropiedades("prefijo");
        plugin.setPropiedades(crearListaParametros());
        plugins.add(plugin);

        return plugins;
    }

    private static List<RPlugin> crearPluginsGlobal() {
        final List<RPlugin> plugins = new ArrayList<>();

        RPlugin plugin;

        plugin = new RPlugin();
        plugin.setTipo(TypePluginGlobal.LOGIN.toString());
        plugin.setPrefijoPropiedades("prefijo");
        plugin.setClassname(
                "es.caib.sistra2.commons.plugins.autenticacion.mock.ComponenteAutenticacionPluginMock");
        plugin.setPropiedades(crearListaParametros());
        plugins.add(plugin);

        plugin = new RPlugin();
        plugin.setTipo(TypePluginGlobal.EMAIL.toString());
        plugin.setPrefijoPropiedades("prefijo");
        plugin.setClassname(
                "es.caib.sistra2.commons.plugins.email.mock.EmailPluginMock");
        plugin.setPropiedades(crearListaParametros());
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
        pasos.add(crearPasoAnexar());
        pasos.add(crearPasoRegistrar());

        final RVersionTramite vt = new RVersionTramite();
        vt.setTimestamp(generateTimestamp());
        vt.setIdentificador("T1");
        vt.setVersion(1);
        vt.setIdioma("es");
        vt.setTipoFlujo("N");
        vt.setPropiedades(crearPropiedadesVT());
        vt.setControlAcceso(crearControlAcceso());
        vt.setPasos(pasos);
        return vt;
    }

    private static RVersionTramitePropiedades crearPropiedadesVT() {
        final RVersionTramitePropiedades p = new RVersionTramitePropiedades();
        p.setAutenticado(true);
        p.setNoAutenticado(true);
        p.setNivelQAA(2);
        p.setPersistente(true);
        return p;
    }

    private static RVersionTramiteControlAcceso crearControlAcceso() {
        final RVersionTramiteControlAcceso c = new RVersionTramiteControlAcceso();
        c.setActivo(true);
        c.setDebug(true);
        return c;
    }

    private static RPasoTramitacionDebeSaber crearPasoDebeSaber() {
        final RPasoTramitacionDebeSaber pd = new RPasoTramitacionDebeSaber();
        pd.setIdentificador("DS1");
        pd.setTipo("DS");
        pd.setInstruccionesInicio("debe saber");
        return pd;
    }

    private static RPasoTramitacionRellenar crearPasoRellenar() {
        final RPasoTramitacionRellenar pr = new RPasoTramitacionRellenar();
        final List<RFormularioTramite> fl = new ArrayList<>();
        fl.add(crearFormularioTramite());
        pr.setIdentificador("RF1");
        pr.setTipo("RF");
        pr.setFormularios(fl);
        return pr;
    }

    private static RPasoTramitacionAnexar crearPasoAnexar() {
        final RPasoTramitacionAnexar pr = new RPasoTramitacionAnexar();
        final List<RAnexoTramite> fl = new ArrayList<>();
        fl.add(crearAnexoTramite("ANE1", 1));
        fl.add(crearAnexoTramite("ANE2", 2));
        pr.setIdentificador("AD1");
        pr.setTipo("AD");
        pr.setAnexos(fl);
        return pr;
    }

    private static RAnexoTramite crearAnexoTramite(String identificador,
            int instancias) {

        final RAnexoTramiteAyuda ayuda = new RAnexoTramiteAyuda();
        ayuda.setUrl("http://www.google.es");
        ayuda.setMensajeHtml("Mensaje <strong>HTML</strong>");

        final List<String> extensiones = new ArrayList<>();
        extensiones.add("pdf");
        extensiones.add("odt");

        final RAnexoTramitePresentacionElectronica presentacionElectronica = new RAnexoTramitePresentacionElectronica();
        presentacionElectronica.setTamanyoMax(1);
        presentacionElectronica.setTamanyoUnidad("MB");
        presentacionElectronica.setExtensiones(extensiones);
        presentacionElectronica.setInstancias(instancias);
        presentacionElectronica.setConvertirPDF(false);
        presentacionElectronica.setAnexarFirmado(false);

        final RScript scriptDependencia = new RScript();
        scriptDependencia.setScript("return 'S';");

        final RAnexoTramite anexo = new RAnexoTramite();
        anexo.setIdentificador(identificador);
        anexo.setDescripcion(identificador);
        anexo.setAyuda(ayuda);
        anexo.setObligatoriedad("D");
        anexo.setScriptDependencia(scriptDependencia);
        anexo.setPresentacion("E");
        anexo.setPresentacionElectronica(presentacionElectronica);

        return anexo;
    }

    private static RPasoTramitacionRegistrar crearPasoRegistrar() {
        final RPasoTramitacionRegistrar pr = new RPasoTramitacionRegistrar();
        pr.setIdentificador("RT1");
        pr.setTipo("RT");
        final RDestinoRegistro destino = new RDestinoRegistro();
        destino.setOficinaRegistro("OF1");
        destino.setLibroRegistro("LIB1");
        destino.setTipoAsunto("AS1");
        pr.setDestino(destino);
        return pr;
    }

    private static RFormularioTramite crearFormularioTramite() {
        RFormularioTramite f;
        f = new RFormularioTramite();
        f.setIdentificador("F1");
        f.setDescripcion("Formulario");
        f.setObligatoriedad("S");
        f.setInterno(true);
        f.setFormularioInterno(crearFormularioDisenyo());

        final RScript scriptDatosIniciales = new RScript();
        scriptDatosIniciales.setScript(
                "DATOS_INICIALES_FORMULARIO.setValor('CAMPO1', 'VALOR_INICIAL');");
        f.setScriptDatosIniciales(scriptDatosIniciales);

        final RScript scriptPostguardar = new RScript();
        scriptPostguardar.setScript(
                "PLUGIN_LOG.debug(PLUGIN_FORMULARIOS.getValor('F1','CAMPO1'));");
        f.setScriptPostguardar(scriptPostguardar);

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

        final RAvisosEntidad ra = new RAvisosEntidad();
        ra.setTimestamp(generateTimestamp());
        ra.setAvisos(lista);
        return ra;
    }

    private static RAviso generarAviso() {
        final RAviso a = new RAviso();
        a.setMensaje(generarLiteral());
        a.setTipo("LIS");
        a.setFechaInicio("20180615000000");
        a.setFechaFin("20180620000000");
        a.setBloquear(true);
        a.setListaVersiones("TRAM1-1;TRAM2-2");
        return a;
    }

    private static String generateTimestamp() {
        final Random rand = new Random();
        return System.currentTimeMillis() + "-" + rand.nextInt();
    }

}
