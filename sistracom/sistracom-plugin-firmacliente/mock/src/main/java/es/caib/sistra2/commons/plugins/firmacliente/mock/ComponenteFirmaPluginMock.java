package es.caib.sistra2.commons.plugins.firmacliente.mock;

import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;
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

	public ComponenteFirmaPluginMock() {
	}

	public ComponenteFirmaPluginMock(final String prefijoPropiedades, final Properties properties) {
	}

	@Override
	public String generarSesionFirma(final InfoSesionFirma infoSesionFirma) {
		return "SF" + System.currentTimeMillis();
	}

	@Override
	public void anyadirFicheroAFirmar(final FicheroAFirmar ficheroAFirmar) {
		// Vacio
	}

	@Override
	public String iniciarSesionFirma(final String idSesionFirma, final String urlCallBack, final String paramAdic) {
		return "localhost:8080/sistramit";
	}

	@Override
	public TypeEstadoFirmado obtenerEstadoSesionFirma(final String idSesionFirma) {
		return TypeEstadoFirmado.FINALIZADO_OK;
	}

	@Override
	public FicheroFirmado obtenerFirmaFichero(final String idSesionFirma, final String idFicheroFirma) {
		final FicheroFirmado fichero = new FicheroFirmado();
		final byte[] contenido = "firma cades".getBytes();
		fichero.setFirmaFichero(contenido);
		fichero.setMimetypeFichero("application/octet-stream");
		fichero.setNombreFichero("fichero.cades");
		fichero.setFirmaTipo(TypeFirmaDigital.CADES_DETACHED);
		return fichero;
	}

	@Override
	public void cerrarSesionFirma(final String idSesionFirma) {
		// Vacio
	}

}
