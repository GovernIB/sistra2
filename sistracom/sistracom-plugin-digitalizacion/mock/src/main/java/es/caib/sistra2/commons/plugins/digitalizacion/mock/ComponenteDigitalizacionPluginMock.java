package es.caib.sistra2.commons.plugins.digitalizacion.mock;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import es.caib.sistra2.commons.pdf.UtilPDF;

import es.caib.sistra2.commons.plugins.digitalizacion.api.*;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

/**
 * Plugin mock componente digitalizaciçon.
 *
 * @author Indra
 *
 */
public class ComponenteDigitalizacionPluginMock extends AbstractPluginProperties implements IDigitalizacionPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "mock.";

	public ComponenteDigitalizacionPluginMock(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}


	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 */
	private String getPropiedadOpcional(final String propiedad) {
		final String res = getProperty(DIGITALIZACION_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		return res;
	}

	@Override
	public String generarSesionDigitalizacion(InfoSesionDigitalizacion infoSesionDigitalizacion) throws DigitalizacionPluginException {
		return System.currentTimeMillis() + "";
	}

	@Override
	public String iniciarSesionDigitalizacion(String idSesionDigitalizacion, String urlCallBack) throws DigitalizacionPluginException {
		try {
			if (isIframe()) {
				// Iframe: Retornamos directamente al asistente como si se hubiese realizado la firma
				return "/sistramitfront/redirigirUrl.jsp?url=" + URLEncoder.encode(urlCallBack, "UTF-8");
			} else {
				// Ventana completa: Redirigimos a página echo para copiar url callback y retornar
				return "https://echo.free.beeceptor.com?url=" + URLEncoder.encode(urlCallBack, "UTF-8");
			}
		} catch (final UnsupportedEncodingException e) {
			throw new DigitalizacionPluginException(e);
		}
	}

	@Override
	public ResultadoDigitalizacion obtenerResultadoDigitalizacion(String idSesionDigitalizacion) throws DigitalizacionPluginException {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
			UtilPDF.generateBlankPdf(bos);
			ResultadoDigitalizacion resultado = new ResultadoDigitalizacion();
			resultado.setDigitalizado(true);
			resultado.setNombreFichero("documento.pdf");
			resultado.setDocumento(bos.toByteArray());
			return resultado;
		} catch (Exception e) {
			throw new DigitalizacionPluginException("Error al generar PDF mock", e);
		}
	}

	@Override
	public boolean isIframe() throws DigitalizacionPluginException {
		String iframe = getPropiedadOpcional("iframe");
		if (iframe == null) {
			iframe = "true";
		}
		return new Boolean(iframe);
	}
}
