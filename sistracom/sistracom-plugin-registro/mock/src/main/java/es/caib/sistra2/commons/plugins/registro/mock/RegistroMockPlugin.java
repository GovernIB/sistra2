package es.caib.sistra2.commons.plugins.registro.mock;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoJustificante;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.VerificacionRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeEstadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeJustificante;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;

/**
 * Plugin mock registro.
 *
 * @author Indra
 *
 */
public class RegistroMockPlugin extends AbstractPluginProperties implements IRegistroPlugin {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistroMockPlugin.class);

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "mock.";

	/**
	 * Nombre de propiedad para indicar como se descargan los justificantes.
	 */
	public static final String PROP_JUSTIFICANTE_DESCARGA = "justificanteDESCARGA";

	/**
	 * Nombre de propiedad para indicar cuanto tiempo se espera para registrar (en
	 * segundos).
	 */
	public static final String PROP_DURACION_REGISTRO = "duracionRegistro";

	/**
	 * Si genera error al consultar justificante (true/false).
	 */
	public static final String PROP_JUSTIFICANTE_ERROR = "justificanteERROR";

	public RegistroMockPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public List<OficinaRegistro> obtenerOficinasRegistro(final String codigoEntidad, final TypeRegistro tipoRegistro)
			throws RegistroPluginException {

		final List<OficinaRegistro> res = new ArrayList<>();
		final OficinaRegistro of1 = new OficinaRegistro();
		of1.setCodigo("codofi1");
		of1.setNombre("oficina registro 1");
		res.add(of1);
		final OficinaRegistro of2 = new OficinaRegistro();
		of2.setCodigo("codofi2");
		of2.setNombre("oficina registro 2");
		res.add(of2);
		final OficinaRegistro of3 = new OficinaRegistro();
		of3.setCodigo("codofi3");
		of3.setNombre("oficina registro 3");
		res.add(of3);
		return res;

	}

	@Override
	public List<LibroOficina> obtenerLibrosOficina(final String codigoEntidad, final String codigoOficina,
			final TypeRegistro tipoRegistro) throws RegistroPluginException {
		final List<LibroOficina> res = new ArrayList<>();
		final LibroOficina lo1 = new LibroOficina();
		lo1.setCodigo("codlib." + codigoOficina + ".1");
		lo1.setNombre("libro oficina (" + codigoOficina + ") 1");
		res.add(lo1);
		final LibroOficina lo2 = new LibroOficina();
		lo2.setCodigo("codlib." + codigoOficina + ".2");
		lo2.setNombre("libro oficina (" + codigoOficina + ") 2");
		res.add(lo2);
		final LibroOficina lo3 = new LibroOficina();
		lo3.setCodigo("codlib." + codigoOficina + ".3");
		lo3.setNombre("libro oficina (" + codigoOficina + ") 3");
		res.add(lo3);
		return res;
	}

	@Override
	public ResultadoRegistro registroEntrada(final String idSesionRegistro, final AsientoRegistral asientoRegistral)
			throws RegistroPluginException {

		// Si hay que simular tiempo registro
		final String duracionRegistro = getPropiedad(PROP_DURACION_REGISTRO);
		if (duracionRegistro != null) {
			final int duracionSegundos = Integer.parseInt(duracionRegistro);
			if (duracionSegundos > 0) {
				retardo(duracionSegundos);
			}
		}

		// Devolvemos simulacion registro
		final ResultadoRegistro res = new ResultadoRegistro();
		res.setFechaRegistro(new Date());
		res.setNumeroRegistro("E-" + System.currentTimeMillis());
		return res;
	}

	@Override
	public ResultadoRegistro registroSalida(final String idSesionRegistro, final AsientoRegistral asientoRegistral)
			throws RegistroPluginException {
		final ResultadoRegistro res = new ResultadoRegistro();
		res.setFechaRegistro(new Date());
		res.setNumeroRegistro("S-" + System.currentTimeMillis());
		return res;
	}

	@Override
	public ResultadoJustificante obtenerJustificanteRegistro(final String codigoEntidad, final String numeroRegistro)
			throws RegistroPluginException {

		if ("true".equals(getPropiedad(PROP_JUSTIFICANTE_ERROR))) {
			throw new RegistroPluginException("Simulación error descarga justificante");
		}

		final ResultadoJustificante res = new ResultadoJustificante();
		res.setTipo(descargaJustificantes());

		switch (descargaJustificantes()) {
		case URL_EXTERNA:
			res.setUrl("https://www.google.es/search?q=justificante");
			break;
		case FICHERO:
			// Lee pdf mock del classpath
			byte[] content = null;
			try (final InputStream isFile = RegistroMockPlugin.class.getClassLoader()
					.getResourceAsStream("justificanteRegistroMock.pdf");) {
				content = IOUtils.toByteArray(isFile);
			} catch (final IOException e) {
				throw new RegistroPluginException("Excepcion al recuperar justificante simulado: " + e.getMessage(), e);
			}
			res.setContenido(content);
			break;
		case CARPETA_CIUDADANA:
			// Url relativa a carpeta
			res.setUrl("/search?q=justificante");
			break;
		default:
			// Tipo justificante no soportado
			throw new RegistroPluginException("Tipo justificante no soportado");
		}

		return res;
	}

	@Override
	public LibroOficina obtenerLibroOrganismo(final String codigoEntidad, final String codigoOrganismo)
			throws RegistroPluginException {
		final LibroOficina lo = new LibroOficina();
		lo.setCodigo("codlib.codofi1.1");
		lo.setNombre("libro oficina (1) 1");
		return lo;
	}

	@Override
	public TypeJustificante descargaJustificantes() throws RegistroPluginException {
		TypeJustificante tipoJustificante = TypeJustificante.fromString(getPropiedad(PROP_JUSTIFICANTE_DESCARGA));
		if (tipoJustificante == null) {
			tipoJustificante = TypeJustificante.FICHERO;
		}
		return tipoJustificante;
	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 * @throws RegistroPluginException
	 */
	private String getPropiedad(final String propiedad) throws RegistroPluginException {
		final String res = getProperty(REGISTRO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		return res;
	}

	/**
	 * Implementa retardo para testing.
	 *
	 * @param timeout
	 *                    timeout en segundos
	 */
	private void retardo(final int pTimeout) {
		LOGGER.debug("Simulando retardo de " + pTimeout + " segundos");
		final long inicio = System.currentTimeMillis();
		while (true) {
			if ((System.currentTimeMillis() - inicio) > (pTimeout * 1000L)) {
				break;
			}
		}
		LOGGER.debug("Fin simulando retardo de " + pTimeout + " segundos");
	}

	@Override
	public String iniciarSesionRegistroEntrada(final String codigoEntidad) throws RegistroPluginException {
		// Simulamos por timeout
		return System.currentTimeMillis() + "";
	}

	@Override
	public String iniciarSesionRegistroSalida(final String codigoEntidad) throws RegistroPluginException {
		// Simulamos por timeout
		return System.currentTimeMillis() + "";
	}

	@Override
	public VerificacionRegistro verificarRegistroEntrada(final String codigoEntidad, final String idSesionRegistro)
			throws RegistroPluginException {
		return verificarPorTimeout(idSesionRegistro);
	}

	@Override
	public VerificacionRegistro verificarRegistroSalida(final String codigoEntidad, final String idSesionRegistro)
			throws RegistroPluginException {
		return verificarPorTimeout(idSesionRegistro);
	}

	private VerificacionRegistro verificarPorTimeout(final String idSesionRegistro) throws RegistroPluginException {
		// TODO ESPERAR A QUE RW3 IMPLEMENTE MECANISMO DE COMPENSACION
		// De momento recibiremos como idSesionRegistro el timestamp de cuando se inició
		// el registro y lo que hacemos es esperar a que se cumpla el timeout (por si en
		// una petición anterior se completa). Si se
		// cumple el timeout y en otra petición no se ha acabado el registro lo damos
		// como no finalizado. Esto no funcionaria si se carga el trámite en otra
		// sesión.
		// Si se completa el registro dentro de la misma sesión se repintaría el paso

		final VerificacionRegistro res = new VerificacionRegistro();
		res.setEstado(TypeEstadoRegistro.NO_REALIZADO);

		final Long timestampInicioRegistro = new Long(idSesionRegistro);

		final String duracionRegistro = getPropiedad(PROP_DURACION_REGISTRO);
		if (duracionRegistro != null) {
			final int duracionSegundos = Integer.parseInt(duracionRegistro);
			final Long timeout = duracionSegundos * 1000L;
			final Date limite = new Date(timestampInicioRegistro + timeout);
			final Date ahora = new Date();
			if (ahora.before(limite)) {
				res.setEstado(TypeEstadoRegistro.EN_PROCESO);
			}
		}

		return res;
	}

}
