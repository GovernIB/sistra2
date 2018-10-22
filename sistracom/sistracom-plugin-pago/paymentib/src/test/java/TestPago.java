import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import es.caib.sistra2.commons.plugins.pago.api.DatosPago;
import es.caib.sistra2.commons.plugins.pago.api.EstadoPago;
import es.caib.sistra2.commons.plugins.pago.api.PagoPluginException;
import es.caib.sistra2.commons.plugins.pago.api.RedireccionPago;
import es.caib.sistra2.commons.plugins.pago.paymentib.ComponentePagoPaymentIBPlugin;

/**
 * Prueba de pago.
 *
 * @author Indra
 *
 */
public class TestPago {

	/**
	 * Para realizar pruebas. Se tiene que: <br />
	 * <ul>
	 * <li>Añadir el log4j-1.2.17.jar en la ejecución (Java aplication /
	 * Classpath)</li>
	 * <li>Ejecutar en modo debug, ya que hay que realizar acciones fuera del
	 * eclipse antes de continuar.</li>
	 * </ul>
	 *
	 * @param args
	 * @throws PagoPluginException
	 * @throws IOException
	 */
	public static void main(final String args[]) throws PagoPluginException, IOException {

		final Properties prop = new Properties();
		final String prefijo = "plugin.pago.";
		final String classname = "es.caib.sistra2.commons.plugins.pago.paymentib.ComponentePagoPaymentIBPlugin";
		prop.put("plugin.pago.url", "http://localhost:8080/paymentib/api/rest/v1/");
		prop.put("plugin.pago.usr", "api-pib");
		prop.put("plugin.pago.pwd", "1234");

		final IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName(classname, prefijo, prop);

		final ComponentePagoPaymentIBPlugin comp = (ComponentePagoPaymentIBPlugin) plg;
		final DatosPago datosPago = new DatosPago();
		datosPago.setAplicacionId("S2");
		datosPago.setConcepto("co");
		datosPago.setDetallePago("dp");
		datosPago.setEntidadId("E1");
		datosPago.setIdioma("es");
		datosPago.setImporte(100);
		datosPago.setModelo("046");
		datosPago.setOrganismoId("O1");
		datosPago.setPasarelaId("MOCK"); // "ATIB"
		datosPago.setSujetoPasivoNif("12345678Z");
		datosPago.setSujetoPasivoNombre("Usuario Prueba Prueba");
		datosPago.setTasaId("1.21.1.2.1.4");
		final RedireccionPago redireccion = comp.iniciarPagoElectronico(datosPago, "http://www.google.es");
		final String identificador = redireccion.getIdentificador();
		System.out.println("Identificador: " + redireccion.getIdentificador());
		System.out.println("URL: " + redireccion.getUrlPago());
		System.out.println(" ****** ");

		// Poner pto de interrupción y acceder a la url y realizar pago
		final EstadoPago verifica = comp.verificarPagoElectronico(identificador);
		System.out.println("Verificar: " + verifica);
		System.out.println(" ****** ");

		final int tasa = comp.consultaTasa("MOCK", "1.21.1.2.1.4");
		System.out.println("Tasa: " + tasa);
		System.out.println(" ****** ");

		final byte[] resultado = comp.obtenerJustificantePagoElectronico(identificador);
		final Path path = Paths.get("/justificantePago.pdf");
		Files.write(path, resultado);
		System.out.println(" Generado el justificantePago.pdf");
		System.out.println(" ****** ");

		final byte[] carta = comp.obtenerCartaPagoPresencial(datosPago);
		final Path pathCarta = Paths.get("/cartaPago.pdf");
		Files.write(pathCarta, carta);
		System.out.println(" Generado el cartaPago.pdf");
		System.out.println(" ****** ");
		System.out.println(" FINALIZADO OK. ");
		System.out.println(" ****** ");

	}
}
