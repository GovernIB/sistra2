package es.caib.sistramit.core.service.test.mock;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RAnexoTramiteAyuda;
import es.caib.sistrages.rest.api.interna.RAnexoTramitePresentacionElectronica;
import es.caib.sistrages.rest.api.interna.RAviso;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDestinoRegistro;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RLiteral;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistrages.rest.api.interna.RPagoTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionDebeSaber;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionPagar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.interna.RVersionTramiteControlAcceso;
import es.caib.sistrages.rest.api.interna.RVersionTramitePropiedades;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;

/**
 *
 * Clase con la definición estática de información simulada de Sistrages para
 * testing.
 *
 * @author Indra
 *
 */
public class SistragesMock {

	/** Id entidad test. */
	public final static String ID_ENTIDAD = "E1";
	/** Id tramite test. */
	public final static String ID_TRAMITE = "T1";
	/** Version tramite test. */
	public final static int VERSION_TRAMITE = 1;
	/** Id tramite CP test. */
	public final static String ID_TRAMITE_CP = "Y";
	/** Idioma test. */
	public final static String IDIOMA = "es";
	/** ID Dominio. */
	public static final String ID_DOMINIO = "DOM-JUNIT";

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

		plugin = new RPlugin();
		plugin.setTipo(TypePluginEntidad.FIRMA.toString());
		plugin.setClassname("es.caib.sistra2.commons.plugins.firmacliente.mock.ComponenteFirmaPluginMock");
		plugin.setPrefijoPropiedades("prefijo");
		plugin.setPropiedades(crearListaParametros());
		plugins.add(plugin);

		plugin = new RPlugin();
		plugin.setTipo(TypePluginEntidad.REGISTRO.toString());
		plugin.setClassname("es.caib.sistra2.commons.plugins.registro.mock.RegistroMockPlugin");
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
		plugin.setClassname("es.caib.sistra2.commons.plugins.autenticacion.mock.ComponenteAutenticacionPluginMock");
		plugin.setPropiedades(crearListaParametros());
		plugins.add(plugin);

		plugin = new RPlugin();
		plugin.setTipo(TypePluginGlobal.EMAIL.toString());
		plugin.setPrefijoPropiedades("prefijo");
		plugin.setClassname("es.caib.sistra2.commons.plugins.email.mock.EmailPluginMock");
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
		pasos.add(crearPasoPagar());
		pasos.add(crearPasoRegistrar());

		final RVersionTramite vt = new RVersionTramite();
		vt.setIdEntidad("ENTIDAD1");
		vt.setTimestamp(generateTimestamp());
		vt.setIdentificador("T1");
		vt.setVersion(1);
		vt.setIdioma("es");
		vt.setTipoFlujo("N");
		vt.setPropiedades(crearPropiedadesVT());
		vt.setControlAcceso(crearControlAcceso());
		vt.setPasos(pasos);
		vt.setDominios(crearDominios());

		return vt;
	}

	private static List<String> crearDominios() {
		final List<String> dominios = new ArrayList<>();
		dominios.add(ID_DOMINIO);
		return dominios;
	}

	public static RDominio crearDominio(final String idDominio) {
		final RDominio dom1 = new RDominio();
		dom1.setCachear(true);
		dom1.setIdentificador(idDominio);
		dom1.setTipo(RDominio.TIPO_CONSULTA_BD);
		dom1.setSql("Select 1 from dual");
		dom1.setTimestamp(generateTimestamp());
		return dom1;
	}

	private static RPasoTramitacion crearPasoPagar() {
		final RPasoTramitacionPagar p = new RPasoTramitacionPagar();
		p.setIdentificador("PT1");
		p.setTipo(TypePaso.PAGAR.toString());
		final List<RPagoTramite> pagos = new ArrayList<>();

		// Pago electronico
		final RPagoTramite pagoElectronico = new RPagoTramite();
		pagoElectronico.setDescripcion("Pago electronico");
		pagoElectronico.setIdentificador("P1");
		pagoElectronico.setObligatoriedad(TypeObligatoriedad.OBLIGATORIO.toString());
		pagoElectronico.setSimularPago(true);
		pagoElectronico.setTipo("A");
		final RScript scriptPagoElectronico = new RScript();
		scriptPagoElectronico.setScript(
				"DATOS_PAGO.setPasarela('ATIB'); DATOS_PAGO.setOrganismo('ORG1'); DATOS_PAGO.setDetallePago('046','C1','T1', 100);");
		pagoElectronico.setScriptPago(scriptPagoElectronico);
		pagos.add(pagoElectronico);

		// Pago presencial
		final RPagoTramite pagoPresencial = new RPagoTramite();
		pagoPresencial.setDescripcion("Pago presencial");
		pagoPresencial.setIdentificador("P2");
		pagoPresencial.setObligatoriedad(TypeObligatoriedad.OBLIGATORIO.toString());
		pagoPresencial.setSimularPago(true);
		pagoPresencial.setTipo("P");
		final RScript scriptPagoPresencial = new RScript();
		scriptPagoPresencial.setScript(
				"DATOS_PAGO.setPasarela('ATIB'); DATOS_PAGO.setOrganismo('ORG1'); DATOS_PAGO.setDetallePago('046','C1','T1', 100);");
		pagoPresencial.setScriptPago(scriptPagoPresencial);
		// pagos.add(pagoPresencial);

		p.setPagos(pagos);

		return p;
	}

	private static RVersionTramitePropiedades crearPropiedadesVT() {
		final RVersionTramitePropiedades p = new RVersionTramitePropiedades();
		p.setAutenticado(true);
		p.setNoAutenticado(true);
		p.setNivelQAA(3);
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
		// Anexos electronicos
		// - Anexo de 1 instancia
		fl.add(crearAnexoTramite("ANE1", 1, false));
		// - Anexo multiinstancia
		fl.add(crearAnexoTramite("ANE2", 2, false));
		// - Anexo XML con validacion
		final RAnexoTramite anexoXml = crearAnexoTramite("ANE-XML", 1, false);
		final RScript scriptValidacionXml = new RScript();
		scriptValidacionXml.setScript("var dni = PLUGIN_VALIDACIONANEXO.getValorXml('//xml/dni');\r\n"
				+ "PLUGIN_LOG.debug('VALOR XML: ' + dni);\r\n"
				+ "if (!PLUGIN_VALIDACIONES.esIgual(dni,'11111111H')) {\r\n"
				+ "	PLUGIN_ERROR.setExisteError(true);\r\n"
				+ "	PLUGIN_ERROR.setTextoMensajeError('No coincide valor XML');\r\n" + "}");
		anexoXml.getPresentacionElectronica().setScriptValidacion(scriptValidacionXml);
		fl.add(anexoXml);

		// - Anexo formulario PDF con validacion
		final RAnexoTramite anexoFormularioPDF = crearAnexoTramite("ANE-PDF", 1, false);
		final RScript scriptValidacionPDF = new RScript();
		scriptValidacionPDF.setScript("var dni = PLUGIN_VALIDACIONANEXO.getValorPdf('SUJETOPASIVONIF');\r\n"
				+ "PLUGIN_LOG.debug('VALOR PDF: ' + dni);\r\n" + "if (!PLUGIN_VALIDACIONES.esIgual(dni,'6666sq')) {\r\n"
				+ "	PLUGIN_ERROR.setExisteError(true);\r\n"
				+ "	PLUGIN_ERROR.setTextoMensajeError('No coincide valor PDF');\r\n" + "}");
		anexoFormularioPDF.getPresentacionElectronica().setScriptValidacion(scriptValidacionPDF);
		fl.add(anexoFormularioPDF);

		// Anexos presenciales (dependiente dato formulario F1.PRESENTACION)
		fl.add(crearAnexoPresencialTramite("ANEP"));
		pr.setIdentificador("AD1");
		pr.setTipo("AD");
		pr.setAnexos(fl);
		return pr;
	}

	private static RAnexoTramite crearAnexoTramite(final String identificador, final int instancias,
			final boolean firmar) {

		final RAnexoTramiteAyuda ayuda = new RAnexoTramiteAyuda();
		ayuda.setUrl("http://www.google.es");
		ayuda.setMensajeHtml("Mensaje <strong>HTML</strong>");

		final List<String> extensiones = new ArrayList<>();
		extensiones.add("pdf");
		extensiones.add("odt");
		extensiones.add("xml");

		final RAnexoTramitePresentacionElectronica presentacionElectronica = new RAnexoTramitePresentacionElectronica();
		presentacionElectronica.setTamanyoMax(1);
		presentacionElectronica.setTamanyoUnidad("MB");
		presentacionElectronica.setExtensiones(extensiones);
		presentacionElectronica.setInstancias(instancias);
		presentacionElectronica.setConvertirPDF(false);
		presentacionElectronica.setAnexarFirmado(false);
		presentacionElectronica.setFirmar(firmar);

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

	private static RAnexoTramite crearAnexoPresencialTramite(final String identificador) {

		final RAnexoTramiteAyuda ayuda = new RAnexoTramiteAyuda();
		ayuda.setUrl("http://www.google.es");
		ayuda.setMensajeHtml("Mensaje <strong>HTML</strong>");

		final RScript scriptDependencia = new RScript();
		scriptDependencia.setScript("return (PLUGIN_FORMULARIOS.getValor('F1', 'PRESENTACION') == 'p'?'s':'d');");

		final RAnexoTramite anexo = new RAnexoTramite();
		anexo.setIdentificador(identificador);
		anexo.setDescripcion(identificador);
		anexo.setAyuda(ayuda);
		anexo.setObligatoriedad("D");
		anexo.setScriptDependencia(scriptDependencia);
		anexo.setPresentacion("P");

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
		f.setFirmar(true);
		f.setFormularioInterno(crearFormularioDisenyo());

		final RScript scriptDatosIniciales = new RScript();
		scriptDatosIniciales.setScript("DATOS_INICIALES_FORMULARIO.setValorCompuesto('SEL_LISTA', 'V2','Valor 2'); "
				+ "DATOS_INICIALES_FORMULARIO.setValor('CHK_ESTADO', 'S'); "
				+ "DATOS_INICIALES_FORMULARIO.setValor('TXT_ESTADO', 'VALOR INICIAL');");
		f.setScriptDatosIniciales(scriptDatosIniciales);

		final RScript scriptPostguardar = new RScript();
		scriptPostguardar.setScript("PLUGIN_LOG.debug(PLUGIN_FORMULARIOS.getValor('F1','CAMPO1'));");
		f.setScriptPostguardar(scriptPostguardar);

		return f;
	}

	private static RFormularioInterno crearFormularioDisenyo() {
		try {
			final InputStream inputStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("test-files/formularioInterno.json");
			final StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, "UTF-8");
			final String formularioInternoJSON = writer.toString();
			final ObjectMapper mapper = new ObjectMapper();
			mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			final RFormularioInterno formularioInterno = mapper.readValue(formularioInternoJSON,
					RFormularioInterno.class);
			return formularioInterno;
		} catch (final Exception ex) {
			throw new RuntimeException("Error cargando definición formulario desde json");
		}

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

	public static RValoresDominio crearValoresDominioFD(final RDominio dominio) {
		final RValoresDominio rvalores = new RValoresDominio();
		rvalores.setCodigoError(null);
		rvalores.setError(false);
		rvalores.setDescripcionError(null);
		int fila = rvalores.addFila();
		rvalores.setValor(fila, "COD1", "VAL11");
		rvalores.setValor(fila, "COD2", "VAL12");
		fila = rvalores.addFila();
		rvalores.setValor(fila, "COD1", "VAL21");
		rvalores.setValor(fila, "COD2", "VAL22");
		return rvalores;
	}

	public static RValoresDominio crearValoresDominioLF(final RDominio dominio) {
		final RValoresDominio rvalores = new RValoresDominio();
		rvalores.setCodigoError(null);
		rvalores.setError(false);
		rvalores.setDescripcionError(null);
		final int fila = rvalores.addFila();
		rvalores.setValor(fila, "COD1", "VAL11");
		rvalores.setValor(fila, "COD2", "VAL12");
		return rvalores;
	}

}
