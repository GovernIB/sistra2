import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.fundaciobit.pluginsib.core.utils.FileUtils;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.DatosAsunto;
import es.caib.sistra2.commons.plugins.registro.api.DatosOrigen;
import es.caib.sistra2.commons.plugins.registro.api.DocumentoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.Interesado;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.TipoAsunto;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeCanal;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumental;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeFirma;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeInteresado;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeOrigenDocumento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeValidez;
import es.caib.sistra2.commons.plugins.registro.regweb3.RegistroRegweb3Plugin;

/**
 * Para hacer pruebas con el plugin de registro REGWEB3
 *
 * @author Indra
 */
public class TestRegistro {

	/**
	 * Para realizar pruebas. Se tiene que: <br />
	 * <ul>
	 * <li>Añadir el log4j-1.2.17.jar, slf4j-log4j12-1.5.8.jar y
	 * slf4j-api-1.5.8.jar en la ejecución (Java aplication / Classpath)</li>
	 * <li>Ejecutar en modo debug, ya que hay que realizar acciones fuera del
	 * eclipse antes de continuar.</li>
	 * <li>Se necesitan 2 pdfs, uno con firma y otro sin, en P:/, sino, cambiar la ruta</li>
	 * </ul>
	 *
	 * @param args
	 */
	public static void main(final String args[]) {
		try {

			final String entidad = "A04003003";
			final String oficinaRegEnt = "O00009390";
			final String libroOficinaRegEnt = "L99";
			final String tipoAsuntoRegEnt = "OT";

			final Properties prop = new Properties();
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.endpoint.entrada",
					"http://dev.caib.es/regweb3/ws/v3/RegWebRegistroEntrada");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.endpoint.info",
							"http://dev.caib.es/regweb3/ws/v3/RegWebInfo");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.log.peticionesWS", "true");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.aplicacion.codigo", "SISTRA");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.aplicacion.version", "1");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.usr", "$sistra_regweb");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.pwd", "sistra_regweb");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.insertarDocs", "true");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.insertarDocs.internos", "true");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.insertarDocs.formateados", "true");
			prop.put("es.caib.sistra2.pluginsib.registro.regweb3.wsdl.dir", "/D:/app/caib/sistra/regweb3/dev/");
			final IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName(
					"es.caib.sistra2.commons.plugins.registro.regweb3.RegistroRegweb3Plugin",
					"es.caib.sistra2.", prop);
			final RegistroRegweb3Plugin plugin = (RegistroRegweb3Plugin) plg;


			// Testeamos obtención de oficinas
			final List<OficinaRegistro> oficinas = plugin.obtenerOficinasRegistro(entidad,TypeRegistro.REGISTRO_ENTRADA);
			for (OficinaRegistro oficina : oficinas) {
				System.out.println("oficina " + oficina.getCodigo() + " : " + oficina.getNombre());
			}

			// Testeamos obtencion de libros para una oficina
			final List<LibroOficina> librosoficina = plugin.obtenerLibrosOficina(entidad, oficinas.get(4).getCodigo(), TypeRegistro.REGISTRO_ENTRADA);
			for (LibroOficina libro : librosoficina) {
				System.out.println("Libro: " + libro.getCodigo() + " : " + libro.getNombre());
			}

			// Testeamos obtencion de tipos de asunto
			final List<TipoAsunto> tiposAsunto = plugin.obtenerTiposAsunto(entidad);
			System.out.println("tiposAsunto" + tiposAsunto.get(0).getCodigo() + " : " + tiposAsunto.get(0).getNombre());

			// Paso 1. Crear Datos Origen
			final DatosOrigen datosOrigen = new DatosOrigen();
			datosOrigen.setCodigoEntidad(entidad);
			datosOrigen.setCodigoOficinaRegistro(oficinaRegEnt);
			datosOrigen.setLibroOficinaRegistro(libroOficinaRegEnt);
			datosOrigen.setTipoRegistro(TypeRegistro.REGISTRO_ENTRADA);

			// Paso 2. Crear Datos Asunto
			final DatosAsunto datosAsunto = new DatosAsunto();
			datosAsunto.setCodigoOrganoDestino("A04013511");
			datosAsunto.setTipoAsunto(tipoAsuntoRegEnt);
			datosAsunto.setFechaAsunto(new Date());
			datosAsunto.setIdiomaAsunto("es");
			datosAsunto.setExtractoAsunto("Prueba registro de entrada");

			// Paso 3. Crear Interesados
			List<Interesado> interesados = new ArrayList<Interesado>();
			Interesado interesado1 = new Interesado();
			interesado1.setTipoDocumento(TypeDocumento.NIF);
			interesado1.setActuaComo(TypeInteresado.REPRESENTANTE);
			interesado1.setNombre("Alejandro");
			interesado1.setApellido1("Macià");
			interesado1.setApellido2("Martin");
			interesado1.setDocIdentificacion("74239824X");
			interesado1.setPais(new Long(724));
			interesado1.setProvincia(new Long(7));
			interesado1.setCodigoPostal("07004");
			interesado1.setDireccion("C/ Prova");
			interesado1.setEmail("amaciam@minsait.com");
			interesado1.setMunicipio(new Long(40));
			interesado1.setTelefono("871717171");

			interesados.add(interesado1);

			Interesado interesado2 = new Interesado();
			interesado2.setTipoDocumento(TypeDocumento.NIF);
			interesado2.setActuaComo(TypeInteresado.REPRESENTADO);
			interesado2.setNombre("Usuario");
			interesado2.setApellido1("Prueba");
			interesado2.setApellido2("Prueba");
			interesado2.setDocIdentificacion("12345678Z");
			interesado2.setPais(new Long(724));
			interesado2.setProvincia(new Long(7));
			interesado2.setCodigoPostal("07008");
			interesado2.setDireccion("C/ Prova");
			interesado2.setEmail("prova@prova.com");
			interesado2.setMunicipio(new Long(40));
			interesado2.setTelefono("871727272");

			interesados.add(interesado2);

			// Paso 4. Crear Documentos
			List<DocumentoRegistro> documentosRegistro = new ArrayList<DocumentoRegistro>();

			// Anyadimos doc de tipo anexo sin firma
			try (InputStream is = RegistroRegweb3Plugin.class.getClassLoader()
					.getResourceAsStream("Doc prueba.pdf")) {
				final ByteArrayOutputStream fos = new ByteArrayOutputStream();
				FileUtils.copy(is, fos);
				DocumentoRegistro documento1 = new DocumentoRegistro();
				documento1.setContenidoFichero(fos.toByteArray());
				documento1.setModoFirma(TypeFirma.SIN_FIRMA);
				documento1.setFechaCaptura(new Date());
				documento1.setNombreFichero("Prueba.pdf");
				documento1.setOrigenDocumento(TypeOrigenDocumento.CIUDADANO);
				documento1.setTipoDocumental("TD14");
				documento1.setTipoDocumento(TypeDocumental.ANEXO);
				documento1.setTituloDoc("Prueba");
				documento1.setValidez(TypeValidez.ORIGINAL);

				documentosRegistro.add(documento1);
			}

			// Anyadimos doc de tipo anexo CON firma
			try (InputStream is2 = RegistroRegweb3Plugin.class.getClassLoader()
					.getResourceAsStream("Doc prueba_signed.pdf")) {
				final ByteArrayOutputStream fos2 = new ByteArrayOutputStream();
				FileUtils.copy(is2, fos2);
				DocumentoRegistro documento2 = new DocumentoRegistro();
				documento2.setContenidoFichero(fos2.toByteArray());
				documento2.setModoFirma(TypeFirma.FIRMA_ATTACHED);
				documento2.setContenidoFirma(fos2.toByteArray());
				documento2.setNombreFirmaAnexada("Doc prueba_signed.pdf");
				documento2.setFechaCaptura(new Date());
				documento2.setNombreFichero("Doc prueba_signed.pdf");
				documento2.setOrigenDocumento(TypeOrigenDocumento.CIUDADANO);
				documento2.setTipoDocumental("TD14");
				documento2.setTipoDocumento(TypeDocumental.ANEXO);
				documento2.setTituloDoc("Documento firmado");
				documento2.setValidez(TypeValidez.ORIGINAL);

				documentosRegistro.add(documento2);
			}

			final AsientoRegistral asiento = new AsientoRegistral();
			asiento.setDatosOrigen(datosOrigen);
			asiento.setDatosAsunto(datosAsunto);
			asiento.setInteresados(interesados);
			asiento.setDocumentosRegistro(documentosRegistro);

			final ResultadoRegistro resultadoRegistro = plugin.registroEntrada(entidad, asiento);
			System.out.println("Registro de entrada realizado correctamente");
			System.out.println("Número de registro de entrada: " + resultadoRegistro.getNumeroRegistro());
			System.out.println("Fecha de registro de entrada: " + resultadoRegistro.getFechaRegistro());

			// Obtenemos justificante de registro de entrada
			byte[] justificanteRegistro = plugin.obtenerJustificanteRegistro(entidad, resultadoRegistro.getNumeroRegistro());
			final Path path = Paths.get("/justificante_" + resultadoRegistro.getNumeroRegistro());
			Files.write(path, justificanteRegistro);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
