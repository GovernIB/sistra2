package es.caib.sistra2.commons.plugins.formulario.mock;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.formulario.api.DatosInicioFormulario;
import es.caib.sistra2.commons.plugins.formulario.api.DatosRetornoFormulario;
import es.caib.sistra2.commons.plugins.formulario.api.FormularioPluginException;
import es.caib.sistra2.commons.plugins.formulario.api.IFormularioPlugin;

/**
 * Plugin formulario mock.
 *
 * @author Indra
 *
 */
public class FormularioPluginMock extends AbstractPluginProperties implements IFormularioPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "mock.";

	/** Map estatico para almacenar sesiones y simular. */
	private static Map<String, DatosInicioFormulario> sesiones = new HashMap<>();

	public FormularioPluginMock(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String invocarFormulario(final String idGestorFormulario, final String urlGestor, final String user,
			final String pass, final DatosInicioFormulario datosInicio) throws FormularioPluginException {
		// Generamos ticket ficticio y almacenamos en map
		final String ticket = datosInicio.getIdSesionFormulario() + ":" + System.currentTimeMillis();
		sesiones.put(ticket, datosInicio);
		// Retornamos url abrir formulario directamente al callback
		final String urlFormulario = datosInicio.getUrlCallback() + "?ticket=" + ticket;
		return urlFormulario;
	}

	@Override
	public DatosRetornoFormulario obtenerResultadoFormulario(final String idGestorFormulario, final String urlGestor,
			final String user, final String pass, final String ticket) throws FormularioPluginException {
		// Obtenemos sesion
		final DatosInicioFormulario dif = sesiones.get(ticket);
		// Retornamos datos mock
		final DatosRetornoFormulario dr = new DatosRetornoFormulario();
		dr.setIdSesionFormulario(dif.getIdSesionFormulario());
		dr.setXml(readFileFromClasspath("formularioMock.xml"));
		dr.setPdf(readFileFromClasspath("formularioMock.pdf"));
		return dr;
	}

	/**
	 * Lee archivo de classpath.
	 *
	 * @param fileName
	 *                     Nombre fichero
	 * @return contenido
	 * @throws FormularioPluginException
	 */
	private byte[] readFileFromClasspath(final String fileName) throws FormularioPluginException {
		try (final InputStream isFile = FormularioPluginMock.class.getClassLoader().getResourceAsStream(fileName);) {
			final byte[] content = IOUtils.toByteArray(isFile);
			return content;
		} catch (final IOException e) {
			throw new FormularioPluginException("Excepcion al recuperar justificante simulado: " + e.getMessage(), e);
		}
	}

}
