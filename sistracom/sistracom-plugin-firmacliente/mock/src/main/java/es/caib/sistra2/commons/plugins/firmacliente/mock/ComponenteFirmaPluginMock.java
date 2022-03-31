package es.caib.sistra2.commons.plugins.firmacliente.mock;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.api.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.api.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.api.InfoSesionFirma;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeEstadoFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeFirmaDigital;

/**
 * Plugin mock componente firma.
 *
 * @author Indra
 *
 */
public class ComponenteFirmaPluginMock extends AbstractPluginProperties implements IFirmaPlugin {

	private static Map<String, InfoSesionFirma> SESIONES_FIRMA = new HashMap<String, InfoSesionFirma>();

	public ComponenteFirmaPluginMock() {
	}

	public ComponenteFirmaPluginMock(final String prefijoPropiedades, final Properties properties) {
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

		// Retornamos directamente al asistente como si se hubiese realizado la firma
		try {
			return "/sistramitfront/redirigirUrl.jsp?url=" + URLEncoder.encode(urlCallBack, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeEstadoFirmado obtenerEstadoSesionFirma(final String idSesionFirma) throws FirmaPluginException {
		return TypeEstadoFirmado.FINALIZADO_OK;
	}

	@Override
	public FicheroFirmado obtenerFirmaFichero(final String idSesionFirma, final String idFicheroFirma)
			throws FirmaPluginException {

		final InfoSesionFirma sf = SESIONES_FIRMA.get(idSesionFirma);

		final FicheroFirmado fichero = new FicheroFirmado();
		// Devuelve nif como firma para luego al validar firma se tenga el nif
		final byte[] contenido = sf.getNif().getBytes();
		fichero.setFirmaFichero(contenido);
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

}
