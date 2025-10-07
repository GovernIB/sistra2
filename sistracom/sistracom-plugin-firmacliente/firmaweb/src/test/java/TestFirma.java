import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoNivelSeguridad;
import es.caib.sistra2.commons.plugins.firmacliente.api.*;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.FileUtils;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import es.caib.sistra2.commons.plugins.firmacliente.firmaweb.ComponenteFirmaSimpleWebPlugin;

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

			// Configuración del plugin de firma web
			final Properties prop = new Properties();
			prop.put("plugins.firma.url",
					"http://portafib2.fundaciobit.org/portafib/common/rest/apifirmawebsimple/v1/");
			prop.put("plugins.firma.configuracion", jsonConfig);

			// Cargamos el plugin de firma web
			final IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName(
					"es.caib.sistra2.commons.plugins.firmacliente.firmaweb.ComponenteFirmaSimpleWebPlugin",
					"plugins.firma.", prop);
			final ComponenteFirmaSimpleWebPlugin plugin = (ComponenteFirmaSimpleWebPlugin) plg;

			// Paso 1. Crear sesion  (firma con autofirma y clave firma)
			final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
			infoSesionFirma.setNivelSeguridad(TipoNivelSeguridad.SUSTANCIAL_CERTIFICADO);
			infoSesionFirma.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_CERTIFICADO);
			infoSesionFirma.setValidarFirmante(true);
			infoSesionFirma.setEntidad("12345678C");
			infoSesionFirma.setIdioma("ca");
			infoSesionFirma.setNombreUsuario("jmico");
			final String idSession = plugin.generarSesionFirma(infoSesionFirma);

			// Paso 2. Leer y subir los 2 ficheros.
			// Anyadimos docs
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

			try (InputStream is2 = ComponenteFirmaSimpleWebPlugin.class.getClassLoader()
					.getResourceAsStream("hola2.pdf")) {
				final ByteArrayOutputStream fos2 = new ByteArrayOutputStream();
				FileUtils.copy(is2, fos2);
				final FicheroAFirmar fichero2 = new FicheroAFirmar();
				fichero2.setFichero(fos2.toByteArray());
				fichero2.setMimetypeFichero("application/pdf");
				fichero2.setIdioma("ca");
				fichero2.setSignNumber(1);
				fichero2.setNombreFichero("hola2.pdf");
				fichero2.setRazon("Fichero prueba1");
				fichero2.setSignID("777");
				fichero2.setSesion(idSession);
				plugin.anyadirFicheroAFirmar(fichero2);
			}

			// Paso 3. Crear transaction y obtener url
			final String url = plugin.iniciarSesionFirma(idSession, null, null);
			System.out.println("Redirige para realizar firma a URL:" + url);


			// Paso 4. Esperamos a que usuario complete firma
			System.out.println("Una vez completada firma, pulsa tecja para continuar...");
			System.in.read();


			// Paso 5. Obtenemos el estado de la firma
			TypeEstadoFirmado estado = TypeEstadoFirmado.INICIALIZADO;
			EstadoFirma estadoFirma = plugin.obtenerEstadoSesionFirma(idSession);
			estado = estadoFirma.getEstadoFirmado();
			System.out.println("Estado de la firma: " + estado);


			// Paso 6. Obtenemos ficheros cuando finalizado
			if (estado == TypeEstadoFirmado.FINALIZADO_OK) {
				final FicheroFirmado ficheroFirmado1 = plugin.obtenerFirmaFichero(idSession, "666");
				final Path path = Paths.get("/" + ficheroFirmado1.getNombreFichero());
				Files.write(path, ficheroFirmado1.getFirmaFichero());

				final FicheroFirmado ficheroFirmado2 = plugin.obtenerFirmaFichero(idSession, "777");
				final Path path2 = Paths.get("/" + ficheroFirmado2.getNombreFichero());
				Files.write(path2, ficheroFirmado2.getFirmaFichero());
			}

			// Paso 7. Cerramos session.
			plugin.cerrarSesionFirma(idSession);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
