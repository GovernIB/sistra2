package es.caib.sistra2.commons.plugins.mock.firmacliente;

import java.util.Properties;

import org.fundaciobit.plugins.utils.AbstractPluginProperties;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.ApiFirmaWebSimple;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleAddFileToSignRequest;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleCommonInfo;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleFile;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleFileInfoSignature;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleGetSignatureResultRequest;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleGetTransactionStatusResponse;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleSignatureResult;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleStartTransactionRequest;
import org.fundaciobit.pluginsib.signature.firmasimple.apifirmasimple.v1.beans.FirmaSimpleStatus;

import es.caib.sistra2.commons.plugins.autenticacion.AutenticacionPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.FicheroFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.InfoSesionFirma;
import es.caib.sistra2.commons.plugins.firmacliente.TypeEstadoFirmado;

/**
 * Plugin mock componente firma.
 *
 * @author Indra
 *
 */
public class ComponenteFirmaSimpleWebPlugin extends AbstractPluginProperties implements IFirmaPlugin {

	public ComponenteFirmaSimpleWebPlugin() {
	}

	public ComponenteFirmaSimpleWebPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String generarSesionFirma(final InfoSesionFirma infoSesionFirma) throws FirmaPluginException {

		/** Crear conexion. **/
		try {
			final ApiFirmaWebSimple api = new ApiFirmaWebSimple(getUrl(), getUsr(), getPwd());
			final FirmaSimpleCommonInfo commonInfo = new FirmaSimpleCommonInfo(infoSesionFirma.getIdioma(),
					infoSesionFirma.getNombreUsuario(), infoSesionFirma.getEntidad());
			return api.getTransactionID(commonInfo);
		} catch (final Exception e) {
			throw new FirmaPluginException("Error generando una sesion para firmar.", e);
		}

	}

	@Override
	public void ficheroAFirmar(final FicheroAFirmar ficheroAFirmar) throws FirmaPluginException {
		ApiFirmaWebSimple api = null;
		final String transactionID = null;
		try {

			final FirmaSimpleFile fileToSign = new FirmaSimpleFile(ficheroAFirmar.getNombreFichero(),
					ficheroAFirmar.getMimetypeFichero(), ficheroAFirmar.getFichero());

			FirmaSimpleFileInfoSignature fileInfoSignature;
			{
				final String signID = ficheroAFirmar.getSignID();
				final String name = fileToSign.getNom();
				final String reason = ficheroAFirmar.getRazon();
				final String location = ficheroAFirmar.getLocalizacion();
				final String signerEmail = ficheroAFirmar.getEmail();
				final int signNumber = ficheroAFirmar.getSignNumber();
				final String languageSign = ficheroAFirmar.getIdioma();

				fileInfoSignature = new FirmaSimpleFileInfoSignature(fileToSign, signID, name, reason, location,
						signerEmail, signNumber, languageSign);
			}

			api = new ApiFirmaWebSimple(getUrl(), getUsr(), getPwd());

			api.addFileToSign(new FirmaSimpleAddFileToSignRequest(ficheroAFirmar.getSesion(), fileInfoSignature));

		} catch (final Exception e) {
			throw new FirmaPluginException("Error añadiendo fichero", e);
		}

	}

	@Override
	public String iniciarSesionFirma(final String idSesionFirma, final String urlCallBack, final String paramAdic)
			throws FirmaPluginException {
		final ApiFirmaWebSimple api = new ApiFirmaWebSimple(getUrl(), getUsr(), getPwd());
		final FirmaSimpleStartTransactionRequest startTransactionInfo = new FirmaSimpleStartTransactionRequest(
				idSesionFirma, urlCallBack, paramAdic);
		String url;
		try {
			url = api.startTransaction(startTransactionInfo);
		} catch (final Exception e) {
			throw new FirmaPluginException("Error empezando la transaction", e);
		}
		return url;
	}

	@Override
	public TypeEstadoFirmado obtenerEstadoSesionFirma(final String idSesionFirma) throws FirmaPluginException {
		final ApiFirmaWebSimple api = new ApiFirmaWebSimple(getUrl(), getUsr(), getPwd());
		FirmaSimpleGetTransactionStatusResponse fullTransactionStatus;
		try {
			fullTransactionStatus = api.getTransactionStatus(idSesionFirma);
		} catch (final Exception e) {
			throw new FirmaPluginException("Error viendo el status de la transaction", e);
		}
		final FirmaSimpleStatus transactionStatus = fullTransactionStatus.getTransactionStatus();
		final int status = transactionStatus.getStatus();

		return TypeEstadoFirmado.fromInt(status);
	}

	@Override
	public FicheroFirmado obtenerFirmaFichero(final String idSesionFirma, final String signID)
			throws FirmaPluginException {
		final ApiFirmaWebSimple api = new ApiFirmaWebSimple(getUrl(), getUsr(), getPwd());
		FirmaSimpleSignatureResult fssr;
		try {
			fssr = api.getSignatureResult(new FirmaSimpleGetSignatureResultRequest(idSesionFirma, signID));
		} catch (final Exception e) {
			throw new FirmaPluginException("Error obtenido el resultado del fichero", e);
		}
		final FirmaSimpleFile fsf = fssr.getSignedFile();
		final FicheroFirmado fic = new FicheroFirmado();
		fic.setFirmaFichero(fsf.getData());
		fic.setMimetypeFichero(fsf.getMime());
		fic.setNombreFichero(fsf.getNom());
		return fic;
	}

	@Override
	public void cerrarSesionFirma(final String idSesionFirma) throws FirmaPluginException {
		final ApiFirmaWebSimple api = new ApiFirmaWebSimple(getUrl(), getUsr(), getPwd());
		try {
			api.closeTransaction(idSesionFirma);
		} catch (final Exception e) {
			throw new FirmaPluginException("Error cerrando la sesion firma", e);
		}
	}

	/**
	 * Obtiene url de propiedades.
	 *
	 * @return url propiedades
	 * @throws AutenticacionPluginException
	 */
	private String getUrl() throws FirmaPluginException {
		final String url = this.getProperty("url");
		if (url == null) {
			throw new FirmaPluginException("No se ha especificado parametro url en propiedades");
		}
		return url;
	}

	/**
	 * Obtiene usuario de propiedades.
	 *
	 * @return url propiedades
	 * @throws AutenticacionPluginException
	 */
	private String getUsr() throws FirmaPluginException {
		final String url = this.getProperty("usr");
		if (url == null) {
			throw new FirmaPluginException("No se ha especificado parametro usuario en propiedades");
		}
		return url;
	}

	/**
	 * Obtiene password de propiedades.
	 *
	 * @return url propiedades
	 * @throws AutenticacionPluginException
	 */
	private String getPwd() throws FirmaPluginException {
		final String url = this.getProperty("pwd");
		if (url == null) {
			throw new FirmaPluginException("No se ha especificado parametro password en propiedades");
		}
		return url;
	}

}
