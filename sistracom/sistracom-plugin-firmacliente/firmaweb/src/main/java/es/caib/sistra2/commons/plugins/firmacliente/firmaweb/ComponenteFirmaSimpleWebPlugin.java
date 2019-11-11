package es.caib.sistra2.commons.plugins.firmacliente.firmaweb;

import java.util.Properties;

import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaWebSimple;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleAddFileToSignRequest;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleCommonInfo;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleFile;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleFileInfoSignature;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleGetSignatureResultRequest;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleGetTransactionStatusResponse;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleSignatureResult;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleStartTransactionRequest;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleStatus;
import org.fundaciobit.apisib.apifirmasimple.v1.jersey.ApiFirmaWebSimpleJersey;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.api.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.api.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.api.InfoSesionFirma;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeEstadoFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeFirmaDigital;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeTipoDocumental;

/**
 * Plugin mock componente firma.
 *
 * @author Indra
 *
 */
public class ComponenteFirmaSimpleWebPlugin extends AbstractPluginProperties implements IFirmaPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "firmaweb.";

	public ComponenteFirmaSimpleWebPlugin() {
	}

	/**
	 * Constructor.
	 *
	 * @param prefijoPropiedades
	 *                               prefijo props
	 * @param properties
	 *                               propiedades
	 */
	public ComponenteFirmaSimpleWebPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String generarSesionFirma(final InfoSesionFirma infoSesionFirma) throws FirmaPluginException {

		/** Crear conexion. **/
		try {
			final ApiFirmaWebSimple api = generarApi();
			final FirmaSimpleCommonInfo commonInfo = new FirmaSimpleCommonInfo(getPropiedad("profile"),
					infoSesionFirma.getIdioma(), infoSesionFirma.getNombreUsuario(), infoSesionFirma.getEntidad(),
					infoSesionFirma.getEmail());

			return api.getTransactionID(commonInfo);
		} catch (final Exception e) {
			throw new FirmaPluginException("Error generando una sesion para firmar.", e);
		}

	}

	private ApiFirmaWebSimpleJersey generarApi() throws FirmaPluginException {
		return new ApiFirmaWebSimpleJersey(getPropiedad("url"), getPropiedad("usr"), getPropiedad("pwd"));
	}

	@Override
	public void anyadirFicheroAFirmar(final FicheroAFirmar ficheroAFirmar) throws FirmaPluginException {
		ApiFirmaWebSimple api = null;
		try {

			final FirmaSimpleFile fileToSign = new FirmaSimpleFile(ficheroAFirmar.getNombreFichero(),
					ficheroAFirmar.getMimetypeFichero(), ficheroAFirmar.getFichero());

			FirmaSimpleFileInfoSignature fileInfoSignature;
			{
				final String signID = ficheroAFirmar.getSignID();
				final String name = fileToSign.getNom();
				final String reason = ficheroAFirmar.getRazon();
				final String location = ficheroAFirmar.getLocalizacion();
				final int signNumber = ficheroAFirmar.getSignNumber();
				final String languageSign = ficheroAFirmar.getIdioma();
				final Long codigoTipoDocumental = convertTypeTipoDocumental(ficheroAFirmar.getTipoDocumental());

				fileInfoSignature = new FirmaSimpleFileInfoSignature(fileToSign, signID, name, reason, location,
						signNumber, languageSign, codigoTipoDocumental);
			}

			api = generarApi();

			api.addFileToSign(new FirmaSimpleAddFileToSignRequest(ficheroAFirmar.getSesion(), fileInfoSignature));

		} catch (final Exception e) {
			throw new FirmaPluginException("Error añadiendo fichero", e);
		}

	}

	@Override
	public String iniciarSesionFirma(final String idSesionFirma, final String urlCallBack, final String paramAdic)
			throws FirmaPluginException {
		final ApiFirmaWebSimple api = generarApi();
		final FirmaSimpleStartTransactionRequest startTransactionInfo = new FirmaSimpleStartTransactionRequest(
				idSesionFirma, urlCallBack, paramAdic);

		if ("true".equalsIgnoreCase(getPropiedad("iframe"))) {
			startTransactionInfo.setView(FirmaSimpleStartTransactionRequest.VIEW_IFRAME);
		} else {
			startTransactionInfo.setView(FirmaSimpleStartTransactionRequest.VIEW_FULLSCREEN);
		}

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
		final ApiFirmaWebSimple api = generarApi();
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
		final ApiFirmaWebSimple api = generarApi();
		FirmaSimpleSignatureResult fssr;
		try {
			fssr = api.getSignatureResult(new FirmaSimpleGetSignatureResultRequest(idSesionFirma, signID));
		} catch (final Exception e) {
			throw new FirmaPluginException("Error obtenido el resultado del fichero", e);
		}

		final TypeEstadoFirmado fst = TypeEstadoFirmado.fromInt(fssr.getStatus().getStatus());

		if (fst == TypeEstadoFirmado.FINALIZADO_CON_ERROR) {
			throw new FirmaPluginException(fssr.getStatus().getErrorMessage());
		}

		final TypeFirmaDigital tipoFirma = TypeFirmaDigital.fromString(fssr.getSignedFileInfo().getEniTipoFirma());
		if (tipoFirma == null) {
			throw new FirmaPluginException(
					"No se reconoce tipo de firma " + fssr.getSignedFileInfo().getEniTipoFirma());
		}

		final FirmaSimpleFile fsf = fssr.getSignedFile();
		final FicheroFirmado fic = new FicheroFirmado();
		fic.setFirmaFichero(fsf.getData());
		fic.setMimetypeFichero(fsf.getMime());
		fic.setNombreFichero(fsf.getNom());
		fic.setFirmaTipo(tipoFirma);

		return fic;
	}

	@Override
	public void cerrarSesionFirma(final String idSesionFirma) throws FirmaPluginException {
		final ApiFirmaWebSimple api = generarApi();
		try {
			api.closeTransaction(idSesionFirma);
		} catch (final Exception e) {
			throw new FirmaPluginException("Error cerrando la sesion firma", e);
		}
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

	/**
	 * Convierte tipo documental a código.
	 *
	 * @param tipoDocumental
	 *                           Tipo documental
	 * @return código
	 * @throws FirmaPluginException
	 */
	private Long convertTypeTipoDocumental(final TypeTipoDocumental tipoDocumental) throws FirmaPluginException {

		// TODO PENDIENTE DE VER TRADUCCIÓN A CÓDIGO TIPO DOCUMENTAL DE PFIB
		Long res = null;
		switch (tipoDocumental) {
		case TD14_SOLICITUD:
			res = 14L;
			break;
		case TD99_OTROS:
			res = 99L;
			break;
		default:
			throw new FirmaPluginException("Pendiente mapeo código para tipo documental " + tipoDocumental);
		}

		return res;
	}

	@Override
	public boolean isVerificarFirma() throws FirmaPluginException {
		return new Boolean(getPropiedad("verificarFirma"));
	}

}
