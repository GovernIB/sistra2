import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoNivelSeguridad;
import es.caib.sistra2.commons.plugins.firmacliente.api.*;
import es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.ComponenteFirmaSimpleWebPlugin;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.utils.FileUtils;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Para hacer pruebas con la firma
 *
 * @author Indra
 */
public class TestFirma {

	/**
	 * Para realizar pruebas. Se tiene que: <br />
	 * <ul>
	 * <li>Añadir el log4j-1.2.17.jar en la ejecución (Java aplication /
	 * Classpath)</li>
	 * <li>Ejecutar en modo debug, ya que hay que realizar acciones fuera del
	 * eclipse antes de continuar.</li>
	 * <li>Se necesitan 2 pdfs cualesquiera en P:/, sino, cambiar la ruta</li>
	 * </ul>
	 *
	 * @param args
	 */
	public static void main(final String args[]) {
		try {

			// Generamos la configuración del plugin de firma web en formato JSON.
			String jsonConfig = ConfiguradorPluginFirmaWeb.generateConfigJSON(true);

			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.USER_FA_CF, "$sistra2_fa_cf_portafib_dev");
			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.PWD_FA_CF, "sistra2_fa_cf_portafib_dev");

			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.USER_FA_AF, "$sistra2_fa_af_portafib_dev");
			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.PWD_FA_AF, "sistra2_fa_af_portafib_dev");

			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.USER_AF, "$sistra2_af_portafib_dev");
			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.PWD_AF, "sistra2_af_portafib_dev");

			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.USER_CF, "$sistra2_cf_portafib_dev");
			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.PWD_CF, "sistra2_cf_portafib_dev");

			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.USER_FA, "$sistra2_fa_portafib_dev");
			jsonConfig = StringUtils.replace(jsonConfig, ConfiguradorPluginFirmaWeb.PWD_FA, "sistra2_fa_portafib_dev");


			System.out.println(jsonConfig);
			if (true) return;

			// Configuración del plugin de firma web
			final String classname = "es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.ComponenteFirmaSimpleWebPlugin";
			final String prefijoGlobal = "es.caib.sistra2.";
			final String prefijoPlugin = "pluginsib.firmacliente.pfibinterna.";

			final Properties prop = new Properties();
			prop.put(prefijoGlobal + prefijoPlugin + "url", "https://dev.caib.es/portafibapi/interna");
			prop.put(prefijoGlobal + prefijoPlugin + "configuracion", jsonConfig);
			prop.put(prefijoGlobal + prefijoPlugin + "iframe", "false");

			final ComponenteFirmaSimpleWebPlugin plugin = (ComponenteFirmaSimpleWebPlugin) PluginsManager
					.instancePluginByClassName(classname, prefijoGlobal, prop);


			// Paso 1. Crear sesion  (firma con autofirma y clave firma)
			final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
			infoSesionFirma.setNivelSeguridad(TipoNivelSeguridad.SUSTANCIAL_CERTIFICADO);
			infoSesionFirma.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_CERTIFICADO);
			infoSesionFirma.setValidarFirmante(true);
			infoSesionFirma.setEntidad("12345678C");
			infoSesionFirma.setIdioma("ca");
			infoSesionFirma.setNif("33456299Q");
			infoSesionFirma.setNombreUsuario("Rafael Sanz Villanueva");
			final String idSession = plugin.generarSesionFirma(infoSesionFirma);

			// Paso 2. Subir fichero a firmar
			try (InputStream is = ComponenteFirmaSimpleWebPlugin.class.getClassLoader()
					.getResourceAsStream("hola.pdf")) {
				final ByteArrayOutputStream fos = new ByteArrayOutputStream();
				FileUtils.copy(is, fos);
				final FicheroAFirmar fichero = new FicheroAFirmar();
				fichero.setFichero(fos.toByteArray());
				fichero.setMimetypeFichero("application/pdf");
				fichero.setIdioma("ca");
				fichero.setSignNumber(1);
				fichero.setNombreFichero("hola.pdf");
				fichero.setRazon("Fichero prueba1");
				fichero.setSignID("666");
				fichero.setSesion(idSession);
				fichero.setTipoDocumental(TypeTipoDocumental.TD99_OTROS);
				plugin.anyadirFicheroAFirmar(fichero);
			}

			// Paso 3. Crear transaction y obtener url
			final String url = plugin.iniciarSesionFirma(idSession, "https://echo.free.beeceptor.com", null);
			System.out.println("Redirige para realizar firma a URL:" + url);


			// Paso 4. Esperamos a que usuario complete firma
			System.out.println("Una vez completada firma, pulsa tecla para continuar...");
			System.in.read();


			// Paso 5. Obtenemos el estado de la firma
			EstadoFirma estadoFirma = plugin.obtenerEstadoSesionFirma(idSession);
			TypeEstadoFirmado estado = estadoFirma.getEstadoFirmado();
			System.out.println("Estado de la firma: " + estado);


			// Paso 6. Obtenemos ficheros cuando finalizado
			if (estado == TypeEstadoFirmado.FINALIZADO_OK) {
				final FicheroFirmado ficheroFirmado1 = plugin.obtenerFirmaFichero(idSession, "666");
				if (ficheroFirmado1.getEstadoFirma().getEstadoFirmado() == TypeEstadoFirmado.FINALIZADO_OK) {
					final Path path = Paths.get("/" + ficheroFirmado1.getNombreFichero());
					Files.write(path, ficheroFirmado1.getFirmaFichero());
					System.out.println("Almacenando firma en: " + path.toAbsolutePath().toString());
				} else {
					System.out.println("Fichero firmado con error: " + ficheroFirmado1.getEstadoFirma().getCodigoError() + " - " + ficheroFirmado1.getEstadoFirma().getMensajeError());
				}
			}

			// Paso 7. Cerramos session.
			plugin.cerrarSesionFirma(idSession);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}



}
