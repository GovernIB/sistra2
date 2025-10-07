package es.caib.sistra2.commons.plugins.firmacliente.firmaweb;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoNivelSeguridad;
import es.caib.sistra2.commons.plugins.firmacliente.api.*;
import es.caib.sistra2.commons.utils.JSONUtil;
import org.apache.commons.codec.binary.Base64;
import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaWebSimple;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.*;
import org.fundaciobit.apisib.apifirmasimple.v1.jersey.ApiFirmaWebSimpleJersey;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

/**
 * Plugin mock componente firma.
 *
 * @author Indra
 *
 */
public class ComponenteFirmaSimpleWebPlugin extends AbstractPluginProperties implements IFirmaPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "firmaweb.";

	/** Constructor por defecto. */
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

			/** Perfil firma a usar en función de configuración. */
			PluginFirmaWebPerfilFirmaConfig perfilFirma = obtenerPerfilFirma(infoSesionFirma.getMetodoAutenticacion(), infoSesionFirma.getNivelSeguridad(), infoSesionFirma.isValidarFirmante());

			final ApiFirmaWebSimple api = generarApi(perfilFirma.getUsuario(), perfilFirma.getPassword());

			final String email = infoSesionFirma.getEmail();
			final String idioma = infoSesionFirma.getIdioma();

			// En caso de firma con certificado representación se pasa nif empresa en
			// parámetro organizationId (representante en administrationId / username)
			String administrationId = null;
			String username = null;
			String organizationId = null;

			if (infoSesionFirma.isValidarFirmante()) {
				// Si es representante, pasamos nif empresa en organizationId
				if (infoSesionFirma.getNifRepresentante() != null) {
					organizationId = infoSesionFirma.getNif();
					administrationId = infoSesionFirma.getNifRepresentante();
					username = infoSesionFirma.getNombreRepresentante();
				} else {
					// Pasamos firmante indicado
					administrationId = infoSesionFirma.getNif();
					username = infoSesionFirma.getNombreUsuario();
				}
			} else {
				// Si no hay que validar, vendrá el usuario autenticado
				administrationId = infoSesionFirma.getNif();
				username = infoSesionFirma.getNombreUsuario();
			}

			// Obtenemos transaction id
			final FirmaSimpleCommonInfo commonInfo = new FirmaSimpleCommonInfo(perfilFirma.getPerfil(), idioma, username,
					administrationId, organizationId, email);
			String transactionID = api.getTransactionID(commonInfo);

			// Retornamos id sesión firma: perfil(B64) + transactionID
			String perfilFirmaB64 = JSONUtil.toJSON(perfilFirma, false);
			perfilFirmaB64 = new String(Base64.encodeBase64(perfilFirmaB64.getBytes(StandardCharsets.UTF_8)));
			String sesionID = perfilFirmaB64 + "." + transactionID;
			return sesionID;
		} catch (final Exception e) {
			throw new FirmaPluginException("Error generando una sesion para firmar.", e);
		}
	}

	@Override
	public void anyadirFicheroAFirmar(final FicheroAFirmar ficheroAFirmar) throws FirmaPluginException {

		// Perfil de firma y transaction id
		PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(ficheroAFirmar.getSesion());
		String transactionId = obtenerTransactionIdFromSesionId(ficheroAFirmar.getSesion());

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

			api = generarApi(perfilFirma.getUsuario(), perfilFirma.getPassword());

			api.addFileToSign(new FirmaSimpleAddFileToSignRequest(transactionId, fileInfoSignature));

		} catch (final Exception e) {
			throw new FirmaPluginException("Error añadiendo fichero", e);
		}

	}

	@Override
	public String iniciarSesionFirma(final String idSesionFirma, final String urlCallBack, final String paramAdic)
			throws FirmaPluginException {

		// Perfil de firma y transaction id
		PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(idSesionFirma);
		String transactionId = obtenerTransactionIdFromSesionId(idSesionFirma);

		final ApiFirmaWebSimple api = generarApi(perfilFirma.getUsuario(), perfilFirma.getPassword());
		final FirmaSimpleStartTransactionRequest startTransactionInfo = new FirmaSimpleStartTransactionRequest(
				transactionId, urlCallBack, paramAdic);

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
	public EstadoFirma obtenerEstadoSesionFirma(final String idSesionFirma) throws FirmaPluginException {

		// Perfil de firma y transaction id
		PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(idSesionFirma);
		String transactionId = obtenerTransactionIdFromSesionId(idSesionFirma);

		final ApiFirmaWebSimple api = generarApi(perfilFirma.getUsuario(), perfilFirma.getPassword());
		FirmaSimpleGetTransactionStatusResponse fullTransactionStatus;
		try {
			fullTransactionStatus = api.getTransactionStatus(transactionId);
		} catch (final Exception e) {
			throw new FirmaPluginException("Error viendo el status de la transaction", e);
		}
		final FirmaSimpleStatus transactionStatus = fullTransactionStatus.getTransactionStatus();
		final int status = transactionStatus.getStatus();
		final String errorMessage = transactionStatus.getErrorMessage();

		EstadoFirma estado = new EstadoFirma();
		estado.setEstadoFirmado(TypeEstadoFirmado.fromInt(status));
		estado.setMensajeError(errorMessage);
		return estado;
	}

	@Override
	public FicheroFirmado obtenerFirmaFichero(final String idSesionFirma, final String signID)
			throws FirmaPluginException {

		// Perfil de firma y transaction id
		PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(idSesionFirma);
		String transactionId = obtenerTransactionIdFromSesionId(idSesionFirma);

		final ApiFirmaWebSimple api = generarApi(perfilFirma.getUsuario(), perfilFirma.getPassword());
		FirmaSimpleSignatureResult fssr;
		try {
			fssr = api.getSignatureResult(new FirmaSimpleGetSignatureResultRequest(transactionId, signID));
		} catch (final Exception e) {
			throw new FirmaPluginException("Error obtenido el resultado del fichero", e);
		}

		FicheroFirmado fic = null;

		final TypeEstadoFirmado fst = TypeEstadoFirmado.fromInt(fssr.getStatus().getStatus());

		if (fst == TypeEstadoFirmado.FINALIZADO_CON_ERROR) {
			EstadoFirma estadoFirma = new EstadoFirma();
			estadoFirma.setEstadoFirmado(TypeEstadoFirmado.FINALIZADO_CON_ERROR);
			estadoFirma.setMensajeError(fssr.getStatus().getErrorMessage());
			fic = new FicheroFirmado();
			fic.setEstadoFirma(estadoFirma);
		} else {
			// Tipo firma
			final TypeFirmaDigital tipoFirma = TypeFirmaDigital.fromString(fssr.getSignedFileInfo().getEniTipoFirma());
			if (tipoFirma == null) {
				throw new FirmaPluginException(
						"No se reconoce tipo de firma " + fssr.getSignedFileInfo().getEniTipoFirma());
			}
			// Metodo firma
			String metodoFirma = null;
			if (fssr.getSignedFileInfo().getSignerInfo().getAdditionalInformation() != null) {
				String pluginName = null;
				String pluginDesc = null;
				 for (FirmaSimpleKeyValue fkv : fssr.getSignedFileInfo().getSignerInfo().getAdditionalInformation()) {
					if (fkv.getKey().equals("SignaturePlugin.Name.Internal")) {
						pluginName = fkv.getValue();
					}
					 if (fkv.getKey().equals("SignaturePlugin.Name.Public")) {
						 pluginDesc = fkv.getValue();
					 }
				 }
				if (pluginName != null || pluginDesc != null) {
					metodoFirma = pluginName + " - " + pluginDesc;
				}
			}


			EstadoFirma estadoFirma = new EstadoFirma();
			estadoFirma.setEstadoFirmado(TypeEstadoFirmado.FINALIZADO_OK);

			final FirmaSimpleFile fsf = fssr.getSignedFile();
			fic = new FicheroFirmado();
			fic.setEstadoFirma(estadoFirma);
			fic.setFirmaFichero(fsf.getData());
			fic.setMimetypeFichero(fsf.getMime());
			fic.setNombreFichero(fsf.getNom());
			fic.setFirmaTipo(tipoFirma);
			fic.setMetodoFirma(metodoFirma);
		}

		return fic;
	}

	@Override
	public void cerrarSesionFirma(final String idSesionFirma) throws FirmaPluginException {

		// Perfil de firma y transaction id
		PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(idSesionFirma);
		String transactionId = obtenerTransactionIdFromSesionId(idSesionFirma);

		final ApiFirmaWebSimple api = generarApi(perfilFirma.getUsuario(), perfilFirma.getPassword());
		try {
			api.closeTransaction(transactionId);
		} catch (final Exception e) {
			throw new FirmaPluginException("Error cerrando la sesion firma", e);
		}
	}

	@Override
	public boolean isVerificarFirma() throws FirmaPluginException {
		return new Boolean(getPropiedad("verificarFirma"));
	}

	@Override
	public boolean isIframe() throws FirmaPluginException {
		return new Boolean(getPropiedad("iframe"));
	}


	// -------------------------------------------------------------------------------------------------------------
	// Métodos privados
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Obtiene el perfil de firma según el método de autenticación y nivel de
	 * seguridad.
	 *
	 * @param metodoAutenticacion
	 * @param nivelSeguridad
	 * @param validarFirmante
	 * @return perfil de firma
	 */
	private PluginFirmaWebPerfilFirmaConfig obtenerPerfilFirma(TipoMetodoAutenticacion metodoAutenticacion,
															   TipoNivelSeguridad nivelSeguridad, boolean validarFirmante) throws FirmaPluginException {

		PluginFirmaWebPerfilFirmaConfig perfilFirma = null;
		List<PluginFirmaWebMetodoNivelConfig> config;

		// Parseamos JSON configuración
		try {
			String configJson = getPropiedad("configuracion");
			config = (List) JSONUtil.fromListJSON(configJson, PluginFirmaWebMetodoNivelConfig.class);
		} catch (Exception e) {
			throw new FirmaPluginException("Error leyendo propiedad configuracion de perfiles de firma", e);
		}

		// Obtenemos perfil de firma según método autenticación y nivel seguridad
		for (PluginFirmaWebMetodoNivelConfig cfg : config) {
			if (cfg.getMetodoAutenticacion() == metodoAutenticacion && cfg.getNivelesSeguridad().contains(nivelSeguridad)) {
				perfilFirma = (validarFirmante? cfg.getPerfilFirmaConVerificacion() : cfg.getPerfilFirmaSinVerificacion());
				break;
			}
		}

		// Si no se ha encontrado perfil de firma, lanzamos excepción
		if (perfilFirma == null) {
			throw new FirmaPluginException("No se ha encontrado perfil de firma para método de autenticación "
					+ metodoAutenticacion + " y nivel de seguridad " + nivelSeguridad);
		}

		// Retornamos perfil de firma
		return perfilFirma;
	}

	/**
	 * Genera la API de firma web simple.
	 *
	 * @param usuario
	 * @param password
	 * @return API de firma web simple
	 * @throws FirmaPluginException
	 */
	private ApiFirmaWebSimpleJersey generarApi(String usuario, String password) throws FirmaPluginException {
		return new ApiFirmaWebSimpleJersey(getPropiedad("url"), usuario, password);
	}

	/**
	 * Obtiene propiedad (obligatoria).
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
	 * Obtiene propiedad (opcional)
	 .
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 */
	private String getPropiedadOpcional(final String propiedad) {
		final String res = getProperty(FIRMACLIENTE_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
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
		// Quitamos el TD y nos quedamos con el numero
		// TD99 --> 99
		Long res = null;
		if (tipoDocumental != null) {
			try {
				res = Long.parseLong(tipoDocumental.toString().substring(2));
			} catch (final NumberFormatException nfe) {
				throw new FirmaPluginException("Error mapeo código para tipo documental " + tipoDocumental);
			}
		}
		return res;
	}

	/**
	 * Obtiene perfil de firma a partir del id de sesión.
	 * @param sessionId id de sesión
	 * @return perfil de firma
	 * @throws FirmaPluginException
	 */
	private PluginFirmaWebPerfilFirmaConfig obtienerPerfilFirmaFromSesionId(String sessionId) throws FirmaPluginException {
		try {
			String perfilFirmaB64 = sessionId.substring(0, sessionId.indexOf('.'));
			String perfilFirmaJson = new String(Base64.decodeBase64(perfilFirmaB64.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
			return (PluginFirmaWebPerfilFirmaConfig) JSONUtil.fromJSON(perfilFirmaJson, PluginFirmaWebPerfilFirmaConfig.class);
		} catch (Exception e) {
			throw new FirmaPluginException("No se ha podido obtener perfil de firma del sesion id: " + sessionId, e);
		}
	}

	/**
	 * Obtiene transaction id a partir del id de sesión
	 * @param sessionId id de sesión
	 * @return transaction id
	 */
	private String obtenerTransactionIdFromSesionId(String sessionId) {
		return sessionId.substring(sessionId.indexOf('.') + 1);
	}

}
