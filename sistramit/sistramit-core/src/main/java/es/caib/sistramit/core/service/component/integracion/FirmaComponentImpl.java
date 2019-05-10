package es.caib.sistramit.core.service.component.integracion;

import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.plugins.certificate.InformacioCertificat;
import org.fundaciobit.plugins.validatesignature.api.IValidateSignaturePlugin;
import org.fundaciobit.plugins.validatesignature.api.SignatureDetailInfo;
import org.fundaciobit.plugins.validatesignature.api.SignatureRequestedInformation;
import org.fundaciobit.plugins.validatesignature.api.ValidateSignatureRequest;
import org.fundaciobit.plugins.validatesignature.api.ValidateSignatureResponse;
import org.fundaciobit.plugins.validatesignature.api.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.api.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.api.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.api.InfoSesionFirma;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeEstadoFirmado;
import es.caib.sistramit.core.api.exception.SesionFirmaClienteException;
import es.caib.sistramit.core.api.exception.ValidacionFirmaException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.service.model.integracion.FirmaClienteRespuesta;
import es.caib.sistramit.core.service.model.integracion.RedireccionFirma;
import es.caib.sistramit.core.service.model.integracion.ValidacionFirmante;

/**
 * Implementación acceso componente firma.
 *
 * @author Indra
 *
 */
@Component("firmaComponent")
public final class FirmaComponentImpl implements FirmaComponent {

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Acceso a literales. */
	@Autowired
	private Literales literalesComponent;

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FirmaComponentImpl.class);

	@Override
	public RedireccionFirma redireccionFirmaExterna(final String idEntidad, final Persona firmante, final String fileId,
			final byte[] fileContent, final String fileName, final String urlCallBack, final String idioma) {

		// Obtiene plugin
		final IFirmaPlugin plgFirma = getPluginFirmaExterna(idEntidad);

		// Crea sesion de firma
		final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
		infoSesionFirma.setEntidad(idEntidad);
		infoSesionFirma.setNif(firmante.getNif());
		infoSesionFirma.setNombreUsuario(firmante.getNombre());
		infoSesionFirma.setIdioma(idioma);
		String sf;
		try {
			sf = plgFirma.generarSesionFirma(infoSesionFirma);
		} catch (final FirmaPluginException e) {
			throw new SesionFirmaClienteException("Excepción al generar sesión firma: " + e.getMessage(), e);
		}

		// Añade fichero
		final FicheroAFirmar fichero = new FicheroAFirmar();
		fichero.setFichero(fileContent);
		if (FilenameUtils.getExtension(fileName).toLowerCase().endsWith(".pdf")) {
			fichero.setMimetypeFichero("application/pdf");
		} else {
			fichero.setMimetypeFichero("application/octet-stream");
		}
		fichero.setIdioma(idioma);
		fichero.setSignNumber(1);
		fichero.setNombreFichero(fileName);
		fichero.setRazon(fileName);
		fichero.setSignID(fileId);
		fichero.setSesion(sf);
		try {
			plgFirma.anyadirFicheroAFirmar(fichero);
		} catch (final FirmaPluginException e) {
			final ListaPropiedades lp = new ListaPropiedades();
			lp.addPropiedad("fileId", fileId);
			lp.addPropiedad("idSesionFirma", sf);
			throw new SesionFirmaClienteException("Excepción al enviar fichero a firmar", lp);
		}

		// Iniciar sesion firma
		String urlRedireccion = null;
		try {
			urlRedireccion = plgFirma.iniciarSesionFirma(sf, urlCallBack, null);
		} catch (final FirmaPluginException e) {
			final ListaPropiedades lp = new ListaPropiedades();
			lp.addPropiedad("fileId", fileId);
			lp.addPropiedad("idSesionFirma", sf);
			throw new SesionFirmaClienteException("Excepción al iniciar sesión firma", lp);
		}

		final RedireccionFirma res = new RedireccionFirma();
		res.setIdSesion(sf);
		res.setUrl(urlRedireccion);
		return res;
	}

	@Override
	public FirmaClienteRespuesta recuperarResultadoFirmaExterna(final String idEntidad, final String sesionFirma,
			final String fileId) {
		final FirmaClienteRespuesta resFirma = new FirmaClienteRespuesta();
		resFirma.setFecha(new Date());

		// Instancia plugin
		final IFirmaPlugin plgFirma = getPluginFirmaExterna(idEntidad);

		// Verifica estado sesion firma y recupera fichero firmado
		try {
			final TypeEstadoFirmado estado = plgFirma.obtenerEstadoSesionFirma(sesionFirma);
			if (estado == null) {
				throw new SesionFirmaClienteException("No se ha recuperado estado firma");
			}
			if (estado == TypeEstadoFirmado.FINALIZADO_OK) {
				// Recoge firma
				final FicheroFirmado fic = plgFirma.obtenerFirmaFichero(sesionFirma, fileId);
				final TypeFirmaDigital tipoFirma = TypeFirmaDigital.fromString(fic.getFirmaTipo().toString());
				if (tipoFirma == null) {
					throw new SesionFirmaClienteException("Tipo firma no reconocida: " + fic.getFirmaTipo().toString());
				}
				// Establece datos firma
				resFirma.setFinalizada(true);
				resFirma.setFirmaContenido(fic.getFirmaFichero());
				resFirma.setFirmaTipo(tipoFirma);
				resFirma.setValida(true);
			} else if (estado == TypeEstadoFirmado.CANCELADO) {
				resFirma.setCancelada(true);
			}
		} catch (final FirmaPluginException ex) {
			throw new SesionFirmaClienteException("Error accediendo a plugin firma cliente: " + ex.getMessage(), ex);
		}

		// Cerramos sesion firma (no gestionamos excepcion)
		try {
			plgFirma.cerrarSesionFirma(sesionFirma);
		} catch (final FirmaPluginException ex) {
			LOGGER.warn("Error cerrando sesion de firma cliente: " + ex.getMessage(), ex);
		}
		return resFirma;
	}

	@Override
	public ValidacionFirmante validarFirmante(final String idEntidad, final String idioma, final byte[] signedDocument,
			final byte[] signature, final String nifFirmante) {

		boolean firmaValida = true;
		String detalleError = null;

		// Realiza validación firma
		final ValidateSignatureResponse validateResponse;
		final IValidateSignaturePlugin plgValidaFirma = getPluginValidacionFirma(idEntidad);
		try {
			final ValidateSignatureRequest validateRequest = new ValidateSignatureRequest();
			validateRequest.setSignedDocumentData(signedDocument);
			validateRequest.setSignatureData(signature);
			validateRequest.setLanguage(idioma);
			final SignatureRequestedInformation signatureRequestedInformation = new SignatureRequestedInformation();
			signatureRequestedInformation.setReturnCertificateInfo(true);
			signatureRequestedInformation.setReturnCertificates(true);
			signatureRequestedInformation.setReturnSignatureTypeFormatProfile(true);
			signatureRequestedInformation.setValidateCertificateRevocation(true);
			signatureRequestedInformation.setReturnValidationChecks(true);
			signatureRequestedInformation.setReturnTimeStampInfo(true);
			validateRequest.setSignatureRequestedInformation(signatureRequestedInformation);
			validateResponse = plgValidaFirma.validateSignature(validateRequest);
		} catch (final Exception e) {
			throw new ValidacionFirmaException(
					"Error accediendo a plugin de validación de firma en servidor: " + e.getMessage(), e);
		}

		// Verifica si ha firmado el firmante
		if (validateResponse.getValidationStatus().getStatus() != ValidationStatus.SIGNATURE_VALID) {
			firmaValida = false;
			detalleError = validateResponse.getValidationStatus().getErrorMsg();
		} else {

			final SignatureDetailInfo[] detalleFirmas = validateResponse.getSignatureDetailInfo();
			firmaValida = false;
			detalleError = literalesComponent.getLiteral(Literales.PASO_REGISTRAR, "firma.firmanteNoValido",
					idioma);
			for (int i = 0; i < detalleFirmas.length; i++) {
				final SignatureDetailInfo detalleFirma = detalleFirmas[i];
				final InformacioCertificat datosCertificadoFirma = detalleFirma.getCertificateInfo();
				final String clasificacion = Integer.toString(datosCertificadoFirma.getClassificacio());
				String nifFirma = null;
				if ("0".equals(clasificacion) || "5".equals(clasificacion)) {
					nifFirma = datosCertificadoFirma.getNifResponsable();
				} else if ("1".equals(clasificacion) || "11".equals(clasificacion) || "12".equals(clasificacion)) {
					nifFirma = datosCertificadoFirma.getUnitatOrganitzativaNifCif();
				}
				if (StringUtils.equals(nifFirma, nifFirmante)) {
					firmaValida = true;
					detalleError = null;
					break;
				}
			}
		}

		// Devuelve resultado validación
		final ValidacionFirmante resultado = new ValidacionFirmante();
		resultado.setCorrecto(firmaValida);
		resultado.setDetalleError(detalleError);
		return resultado;
	}

	/**
	 * Obtiene plugin validación firma.
	 *
	 * @return plugin
	 */
	private IValidateSignaturePlugin getPluginValidacionFirma(final String entidad) {
		final IValidateSignaturePlugin plgValidaFirma = (IValidateSignaturePlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.VALIDACION_FIRMA_SERVIDOR, entidad);
		return plgValidaFirma;
	}

	/**
	 * Obtiene plugin firma externa.
	 *
	 * @return plugin
	 */
	private IFirmaPlugin getPluginFirmaExterna(final String entidad) {
		final IFirmaPlugin plgFirma = (IFirmaPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.FIRMA, entidad);
		return plgFirma;
	}

}
