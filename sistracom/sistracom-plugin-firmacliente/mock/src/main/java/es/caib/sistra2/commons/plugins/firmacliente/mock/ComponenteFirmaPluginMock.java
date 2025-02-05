package es.caib.sistra2.commons.plugins.firmacliente.mock;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import es.caib.sistra2.commons.plugins.firmacliente.api.*;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

/**
 * Plugin mock componente firma.
 *
 * @author Indra
 *
 */
public class ComponenteFirmaPluginMock extends AbstractPluginProperties implements IFirmaPlugin {

	private static Map<String, InfoSesionFirma> SESIONES_FIRMA = new HashMap<String, InfoSesionFirma>();

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "mock.";

	public ComponenteFirmaPluginMock(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}
	@Override
	public String generarSesionFirma(final InfoSesionFirma infoSesionFirma) throws FirmaPluginException {
		final String idSesionFirma = "SF" + System.currentTimeMillis();
		SESIONES_FIRMA.put(idSesionFirma, infoSesionFirma);
		return idSesionFirma;
	}

	@Override
	public void anyadirFicheroAFirmar(final FicheroAFirmar ficheroAFirmar) throws FirmaPluginException {
		// Vacio
	}

	@Override
	public String iniciarSesionFirma(final String idSesionFirma, final String urlCallBack, final String paramAdic)
			throws FirmaPluginException {
		try {
			if (isIframe()) {
				// Iframe: Retornamos directamente al asistente como si se hubiese realizado la firma
				return "/sistramitfront/redirigirUrl.jsp?url=" + URLEncoder.encode(urlCallBack, "UTF-8");
			} else {
				// Ventana completa: Redirigimos a página echo para copiar url callback y retornar
				final String url = getPropiedad("url");
				return url + "?url=" + URLEncoder.encode(urlCallBack, "UTF-8");
			}
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public EstadoFirma obtenerEstadoSesionFirma(final String idSesionFirma) throws FirmaPluginException {
		EstadoFirma estado = new EstadoFirma();
		estado.setEstadoFirmado(TypeEstadoFirmado.FINALIZADO_OK);
		return estado;
	}

	@Override
	public FicheroFirmado obtenerFirmaFichero(final String idSesionFirma, final String idFicheroFirma)
			throws FirmaPluginException {

		final InfoSesionFirma sf = SESIONES_FIRMA.get(idSesionFirma);

		EstadoFirma estado = new EstadoFirma();
		estado.setEstadoFirmado(TypeEstadoFirmado.FINALIZADO_OK);

		final FicheroFirmado fichero = new FicheroFirmado();
		fichero.setEstadoFirma(estado);
		fichero.setFirmaFichero("<<contenido firma>>".getBytes());
		fichero.setMimetypeFichero("application/octet-stream");
		fichero.setNombreFichero("fichero.cades");
		fichero.setFirmaTipo(TypeFirmaDigital.CADES_DETACHED);
		return fichero;
	}

	@Override
	public void cerrarSesionFirma(final String idSesionFirma) throws FirmaPluginException {
		// Vacio
	}

	@Override
	public boolean isVerificarFirma() throws FirmaPluginException {
		return true;
	}

	@Override
	public boolean isIframe() throws FirmaPluginException {
		return new Boolean(getPropiedad("iframe"));
	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 * @throws FirmaPluginException
	 */
	private String getPropiedad(final String propiedad) throws FirmaPluginException {
		final String res = getProperty(FIRMACLIENTE_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new FirmaPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
		}
		return res;
	}
}
