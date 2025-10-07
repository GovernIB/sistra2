package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoNivelSeguridad;
import es.caib.sistra2.commons.plugins.firmacliente.api.*;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeNivelSeguridad;
import org.apache.commons.io.FilenameUtils;

import org.fundaciobit.plugins.validatesignature.api.IValidateSignaturePlugin;
import org.fundaciobit.plugins.validatesignature.api.SignatureDetailInfo;
import org.fundaciobit.plugins.validatesignature.api.SignatureRequestedInformation;
import org.fundaciobit.plugins.validatesignature.api.ValidateSignatureRequest;
import org.fundaciobit.plugins.validatesignature.api.ValidateSignatureResponse;
import org.fundaciobit.plugins.validatesignature.api.ValidationStatus;
import org.fundaciobit.pluginsib.validatecertificate.InformacioCertificat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.exception.SesionFirmaClienteException;
import es.caib.sistramit.core.api.exception.ValidacionFirmaException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.flujo.Firmante;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
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
	public RedireccionFirma redireccionFirmaExterna(final String idEntidad, TypeNivelSeguridad nivelSeguridad, final UsuarioAutenticadoInfo usuarioAutenticado,
													final Persona firmante, final Persona representante,
													final String fileId, final byte[] fileContent, final String fileName,
													final String tipoDocumental, final String urlCallBack, final String idioma) {

		// Obtiene plugin
		final IFirmaPlugin plgFirma = getPluginFirmaExterna(idEntidad);

		// Crea sesion de firma
		final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
		infoSesionFirma.setEntidad(idEntidad);
		infoSesionFirma.setIdioma(idioma);
		infoSesionFirma.setNivelSeguridad(convertToTipoNivelSeguridad(nivelSeguridad));
		infoSesionFirma.setMetodoAutenticacion(convertToTipoMetodoAutenticacion(usuarioAutenticado.getMetodoAutenticacion()));
		if (firmante != null) {
			// Se pasa firmante para validar
			infoSesionFirma.setValidarFirmante(true);
			infoSesionFirma.setNif(firmante.getNif());
			infoSesionFirma.setNombreUsuario(firmante.getNombre());
			if (representante != null) {
				infoSesionFirma.setNifRepresentante(representante.getNif());
				infoSesionFirma.setNombreRepresentante(representante.getNombre());
			}
		} else {
			// No se pasa firmante, no se valida
			infoSesionFirma.setValidarFirmante(false);
			// Se pasa usuario autenticado
			if (usuarioAutenticado != null && usuarioAutenticado.getAutenticacion() == TypeAutenticacion.AUTENTICADO) {
				infoSesionFirma.setNif(usuarioAutenticado.getNif());
				infoSesionFirma.setNombreUsuario(usuarioAutenticado.getNombre());
			}
		}

		// Genera sesión firma
		String sf;
		try {
			sf = plgFirma.generarSesionFirma(infoSesionFirma);
		} catch (final FirmaPluginException e) {
			throw new SesionFirmaClienteException("Excepció al generar sessió firma: " + e.getMessage(), e);
		}

		// Añade fichero
		final FicheroAFirmar fichero = new FicheroAFirmar();
		fichero.setFichero(fileContent);
		if (FilenameUtils.getExtension(fileName).toLowerCase().endsWith("pdf")) {
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
		final TypeTipoDocumental tipoDoc = TypeTipoDocumental.fromString(tipoDocumental);
		if (tipoDoc == null) {
			throw new SesionFirmaClienteException("No es reconeix tipus documental: " + tipoDocumental);
		}
		fichero.setTipoDocumental(tipoDoc);
		try {
			plgFirma.anyadirFicheroAFirmar(fichero);
		} catch (final FirmaPluginException e) {
			final ListaPropiedades lp = new ListaPropiedades();
			lp.addPropiedad("fileId", fileId);
			lp.addPropiedad("idSesionFirma", sf);
			throw new SesionFirmaClienteException("Excepció al enviar fitxer a signar: " + e.getMessage(), e, lp);
		}

		// Iniciar sesion firma
		String urlRedireccion = null;
		boolean iframe;
		try {
			urlRedireccion = plgFirma.iniciarSesionFirma(sf, urlCallBack, null);
			iframe = plgFirma.isIframe();
		} catch (final FirmaPluginException e) {
			final ListaPropiedades lp = new ListaPropiedades();
			lp.addPropiedad("fileId", fileId);
			lp.addPropiedad("idSesionFirma", sf);
			throw new SesionFirmaClienteException("Excepció al iniciar sessió signatura: " + e.getMessage(), e, lp);
		}

		final RedireccionFirma res = new RedireccionFirma();
		res.setIdSesion(sf);
		res.setUrl(urlRedireccion);
		res.setIframe(iframe);
		return res;
	}

	@Override
	public FirmaClienteRespuesta recuperarResultadoFirmaExterna(final String idEntidad, final String sesionFirma,
			final String fileId) {
		final FirmaClienteRespuesta resFirma = new FirmaClienteRespuesta();
		resFirma.setSesionFirma(sesionFirma);
		resFirma.setFecha(new Date());

		// Instancia plugin
		final IFirmaPlugin plgFirma = getPluginFirmaExterna(idEntidad);

		// Verifica estado sesion firma y recupera fichero firmado
		try {
			final EstadoFirma estado = plgFirma.obtenerEstadoSesionFirma(sesionFirma);
			if (estado == null) {
				throw new SesionFirmaClienteException("No s'ha recuperat estat signatura");
			}
			switch (estado.getEstadoFirmado()) {
				case FINALIZADO_OK:
					// Recoge firma
					final FicheroFirmado fic = plgFirma.obtenerFirmaFichero(sesionFirma, fileId);
					if (fic.getEstadoFirma().getEstadoFirmado() == TypeEstadoFirmado.FINALIZADO_OK) {
						// Indica que ha finalizado correctamente
						final TypeFirmaDigital tipoFirma = TypeFirmaDigital.fromString(fic.getFirmaTipo().toString());
						if (tipoFirma == null) {
							throw new SesionFirmaClienteException(
									"Tipus signatura no reconeguda: " + fic.getFirmaTipo().toString());
						}
						// Establece datos firma
						resFirma.setFinalizada(true);
						resFirma.setFirmaContenido(fic.getFirmaFichero());
						resFirma.setFirmaTipo(tipoFirma);
						resFirma.setMetodoFirma(fic.getMetodoFirma());
						resFirma.setValida(true);
						resFirma.setVerificar(plgFirma.isVerificarFirma());
					} else {
						// Indica que ha finalizado con error
						resFirma.setDetalleError(fic.getEstadoFirma().getMensajeError());
					}
					break;

				case CANCELADO:
					// Indica que está cancelada
					resFirma.setCancelada(true);
					resFirma.setDetalleError("Signatura cancel·lada");
					break;

				case FINALIZADO_CON_ERROR:
					// Indica que ha finalizado con error
					resFirma.setDetalleError(estado.getMensajeError());
					break;

				default:
					// Indica que se recibe un estado no esperado
					resFirma.setDetalleError("Estat signatura no esperat: " + estado.toString());

		}

		} catch (final FirmaPluginException ex) {
			throw new SesionFirmaClienteException("Error accedint a plugin firma client: " + ex.getMessage(), ex);
		}

		// Cerramos sesion firma (no gestionamos excepcion)
		try {
			plgFirma.cerrarSesionFirma(sesionFirma);
		} catch (final FirmaPluginException ex) {
			LOGGER.warn("Error tancant sessió de firma client: " + ex.getMessage(), ex);
		}
		return resFirma;
	}

	@Override
	public ValidacionFirmante validarFirmante(final String idEntidad, final String idioma, final byte[] signedDocument,
			final byte[] signature, final String nifFirmantes) {
		final List<String> nifFirmantesObligatorios = new ArrayList<>();
		final List<String> nifFirmantesOpcionales = new ArrayList<>();
		final List<String> nifFirmantesRequeridos = new ArrayList<>();
		if (nifFirmantes != null) {
			nifFirmantesObligatorios.add(nifFirmantes);
		}
		final ValidacionFirmante res = validarFirmanteImpl(idEntidad, idioma, signedDocument, signature,
				nifFirmantesObligatorios, nifFirmantesOpcionales, nifFirmantesRequeridos);
		return res;
	}

	@Override
	public ValidacionFirmante validarFirmante(final String idEntidad, final String idioma, final byte[] signedDocument,
			final byte[] signature, final List<Firmante> firmantes) {
		// Desglosamos por obligatoriedad
		final List<String> nifFirmantesObligatorios = new ArrayList<>();
		final List<String> nifFirmantesOpcionales = new ArrayList<>();
		final List<String> nifFirmantesRequeridos = new ArrayList<>();
		if (firmantes != null) {
			for (final Firmante f : firmantes) {
				switch (f.getObligatorio()) {
					case OBLIGATORIO:
						nifFirmantesObligatorios.add(f.getNif());
						break;
					case OPCIONAL:
						nifFirmantesOpcionales.add(f.getNif());
						break;
					case OPCIONAL_REQUERIDO:
						nifFirmantesRequeridos.add(f.getNif());
						break;
				}
			}
		}
		// Validamos firma y firmantes requeridos
		final ValidacionFirmante res = validarFirmanteImpl(idEntidad, idioma, signedDocument, signature,
				nifFirmantesObligatorios, nifFirmantesOpcionales, nifFirmantesRequeridos);
		return res;
	}

	private ValidacionFirmante validarFirmanteImpl(final String idEntidad, final String idioma,
			final byte[] signedDocument, final byte[] signature, final List<String> nifFirmantesObligatorios,
			final List<String> nifFirmantesOpcionales, final List<String> nifFirmantesRequeridos) {

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
					"Error accedint a plugin de validació de firma en servidor: " + e.getMessage(), e);
		}

		// Verifica si ha firmado el firmante
		if (validateResponse.getValidationStatus().getStatus() != ValidationStatus.SIGNATURE_VALID) {
			firmaValida = false;
			detalleError = validateResponse.getValidationStatus().getErrorMsg();
		} else {
			final SignatureDetailInfo[] detalleFirmas = validateResponse.getSignatureDetailInfo();

			final List<String> nifFirmados = new ArrayList<String>();
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
				if (!nifFirmados.contains(nifFirma)) {
					nifFirmados.add(nifFirma);
				}
			}

			// Firmantes obligatorios deben estar todos
			firmaValida = true;
			for (final String nf : nifFirmantesObligatorios) {
				if (!nifFirmados.contains(nf)) {
					firmaValida = false;
					break;
				}
			}

			// Firmantes requeridos debe haber al menos uno
			if (firmaValida && !nifFirmantesRequeridos.isEmpty()) {
				firmaValida = false;
				for (final String nf : nifFirmantesRequeridos) {
					if (nifFirmados.contains(nf)) {
						firmaValida = true;
						break;
					}
				}
			}

			// Si la firma no es válida, indicamos texto error
			if (!firmaValida) {
				detalleError = literalesComponent.getLiteral(Literales.PASO_REGISTRAR, "firma.firmanteNoValido", idioma);
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


	/**
	 * Convierte el tipo de autenticación del cliente a tipo de autenticación del plugin.
	 * @param metodoAutenticacion Método de autenticación del cliente
	 * @return TipoMetodoAutenticacion del plugin
	 */
	private static TipoMetodoAutenticacion convertToTipoMetodoAutenticacion(TypeMetodoAutenticacion metodoAutenticacion) {
		TipoMetodoAutenticacion res = TipoMetodoAutenticacion.fromString(metodoAutenticacion.toString());
		if (res == null) {
			throw new SesionFirmaClienteException("No es reconeix el mètode d'autenticació: "
					+ metodoAutenticacion.toString());
		}
		return res;
	}


	/**
	 * Convierte el tipo de nivel de seguridad del cliente a tipo de nivel de seguridad del plugin.
	 * @param nivelSeguridad Nivel de seguridad del cliente
	 * @return TipoNivelSeguridad del plugin
	 */
	private static TipoNivelSeguridad convertToTipoNivelSeguridad(TypeNivelSeguridad nivelSeguridad) {
		TipoNivelSeguridad res = null;
		if (nivelSeguridad != null) {
			res = TipoNivelSeguridad.fromString(nivelSeguridad.toString());
			if (res == null) {
				throw new SesionFirmaClienteException("No es reconeix el nivell de seguretat: " + nivelSeguridad.toString());
			}
		}
		return res;
	}
}
