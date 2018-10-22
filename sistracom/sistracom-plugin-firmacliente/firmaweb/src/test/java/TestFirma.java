import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.FileUtils;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.api.InfoSesionFirma;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeEstadoFirmado;
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
	 * @throws PagoPluginException
	 */
	public static void main(final String args[]) {
		try {
			final Properties prop = new Properties();
			prop.put("plugins.firma.url",
					"http://portafib2.fundaciobit.org/portafib/common/rest/apifirmawebsimple/v1/");
			prop.put("plugins.firma.usr", "ibsalut_sistra2");
			prop.put("plugins.firma.pwd", "ibsalut_sistra2");
			final IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName(
					"es.caib.sistra2.commons.plugins.firmacliente.firmaweb.ComponenteFirmaSimpleWebPlugin",
					"plugins.firma.", prop);
			final ComponenteFirmaSimpleWebPlugin plugin = (ComponenteFirmaSimpleWebPlugin) plg;

			// Paso 1. Crear sesion
			final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
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
				plugin.ficheroAFirmar(fichero);
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
				plugin.ficheroAFirmar(fichero2);
			}

			// Paso 3. Crear transaction y obtener url
			final String url = plugin.iniciarSesionFirma(idSession, null, null);
			System.out.println("URL:" + url);

			// Paso 3.5 Esperando a que se finalice el firmado (4 seg durmiendo)
			TypeEstadoFirmado estado = TypeEstadoFirmado.INICIALIZADO;
			while (estado != TypeEstadoFirmado.FINALIZADO_OK) {
				estado = plugin.obtenerEstadoSesionFirma(idSession);
				System.out.println("estado: " + estado);
				Thread.sleep(4000l);
			}

			// Paso 4. Obtenemos ficheros cuando finalizado
			if (estado == TypeEstadoFirmado.FINALIZADO_OK) {
				final FicheroFirmado ficheroFirmado1 = plugin.obtenerFirmaFichero(idSession, "666");
				final Path path = Paths.get("/" + ficheroFirmado1.getNombreFichero());
				Files.write(path, ficheroFirmado1.getFirmaFichero());

				final FicheroFirmado ficheroFirmado2 = plugin.obtenerFirmaFichero(idSession, "777");
				final Path path2 = Paths.get("/" + ficheroFirmado2.getNombreFichero());
				Files.write(path2, ficheroFirmado2.getFirmaFichero());
			}

			// Paso 5. Cerramos session.
			plugin.cerrarSesionFirma(idSession);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
