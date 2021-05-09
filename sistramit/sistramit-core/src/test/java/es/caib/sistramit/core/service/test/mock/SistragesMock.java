package es.caib.sistramit.core.service.test.mock;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.caib.sistrages.rest.api.interna.RAviso;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RIncidenciaValoracion;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RLiteral;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistrages.rest.api.interna.RPlantillaFormulario;
import es.caib.sistrages.rest.api.interna.RPlantillaIdioma;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.model.integracion.types.TypeCache;

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
	public final static String ID_ENTIDAD = "A04003003";
	/** Id tramite test. */
	public final static String ID_TRAMITE = "TRAM_JUNIT";
	/** Version tramite test. */
	public final static int VERSION_TRAMITE = 1;
	/** Id tramite CP test. */
	public final static String ID_TRAMITE_CP = "TC1";
	/** Idioma test. */
	public final static String IDIOMA = "es";
	/** ID Dominio. */
	public static final String ID_DOMINIO = "DOM-JUNIT";
	/** ID Dominio CACHE EXPLICITO. */
	public static final String ID_DOMINIO_CACHE_EXPLICITO = "DOM-CACHE-EXPLICITO";
	/** ID Dominio CACHE IMPLICITO. */
	public static final String ID_DOMINIO_CACHE_IMPLICITO = "DOM-CACHE-IMPLICITO";
	/** ID Dominio CACHE NO. */
	public static final String ID_DOMINIO_CACHE_NO = "DOM-CACHE-NO";

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
		vp = new RValorParametro();
		vp.setCodigo(TypePropiedadConfiguracion.ANEXOS_TAMANYO_TOTAL.toString());
		vp.setValor("10");
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

		final List<RIncidenciaValoracion> incidenciasValoracion = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			final RIncidenciaValoracion inci = new RIncidenciaValoracion();
			inci.setIdentificador("P" + i);
			inci.setDescripcion(generarLiteral());
			incidenciasValoracion.add(inci);
		}

		final RConfiguracionEntidad e = new RConfiguracionEntidad();
		e.setTimestamp(generateTimestamp());
		e.setIdentificador("E1");
		e.setLogo("/sistramitfront/asistente/imgs/logo-goib.svg");
		// e.setCss("pathCss");
		e.setEmail("entidad@mail.es");
		e.setContactoHTML(generarLiteral());
		e.setUrlCarpeta(generarLiteralUrl());
		e.setUrlSede(generarLiteralUrl());
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
		e.setValorarTramite(true);
		e.setIncidenciasValoracion(incidenciasValoracion);

		// Registro centralizado
		e.setRegistroCentralizado(true);
		e.setOficinaRegistroCentralizado("OF1");

		// Formateadores
		final List<RPlantillaIdioma> plantillasDefecto = new ArrayList<>();
		final RPlantillaFormulario plantilla = new RPlantillaFormulario();
		plantilla.setClaseFormateador(
				"es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl.FormateadorGenerico");
		plantilla.setDefecto(true);
		final RPlantillaIdioma plantillaIdioma = new RPlantillaIdioma();
		plantillaIdioma.setIdioma("es");
		plantillaIdioma.setPlantilla(plantilla);
		plantillasDefecto.add(plantillaIdioma);
		e.setPlantillasDefecto(plantillasDefecto);

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

		plugin = new RPlugin();
		plugin.setTipo(TypePluginEntidad.VALIDACION_FIRMA_SERVIDOR.toString());
		plugin.setClassname("es.caib.sistra2.commons.plugins.validacionfirma.mock.ValidacionFirmaPluginMock");
		plugin.setPrefijoPropiedades("prefijo");
		plugin.setPropiedades(crearListaParametros());
		plugins.add(plugin);

		plugin = new RPlugin();
		plugin.setTipo(TypePluginEntidad.FORMULARIOS_EXTERNOS.toString());
		plugin.setClassname("es.caib.sistra2.commons.plugins.formulario.mock.FormularioPluginMock");
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

		try {
			final InputStream inputStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("test-files/versionTramite.json");
			final StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, "UTF-8");
			final String json = writer.toString();
			final ObjectMapper mapper = new ObjectMapper();
			mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			final RVersionTramite defTramite = mapper.readValue(json, RVersionTramite.class);
			defTramite.setIdEntidad(ID_ENTIDAD);
			defTramite.setTimestamp("" + System.currentTimeMillis());
			return defTramite;
		} catch (final Exception ex) {
			throw new RuntimeException("Error cargando definición trámite desde json", ex);
		}
	}

	public static RDominio crearDominio(final String idDominio) {
		final RDominio dom1 = new RDominio();
		dom1.setIdentificador(idDominio);
		dom1.setTimestamp(generateTimestamp());
		dom1.setTipo(RDominio.TIPO_LISTA_LISTA);

		// Configuracion cache
		dom1.setTipoCache(TypeCache.CACHE_EXPLICITA.toString());
		if (SistragesMock.ID_DOMINIO_CACHE_IMPLICITO.equals(idDominio)) {
			dom1.setTipoCache(TypeCache.CACHE_IMPLICITA.toString());
		}
		if (SistragesMock.ID_DOMINIO_CACHE_NO.equals(idDominio)) {
			dom1.setTipoCache(TypeCache.CACHE_NO.toString());
		}

		return dom1;
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
