package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import es.caib.sistra2.commons.plugins.registro.api.*;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.*;
import es.caib.sistra2.commons.utils.*;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.JsonException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.*;
import es.caib.sistramit.core.api.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.api.model.flujo.types.*;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.*;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Acción que preparara registro para registrarlo de forma isolated posteriormente.
 *
 * @author Indra
 *
 */
@Component("accionRtPrepararRegistro")
public final class AccionPrepararRegistro implements AccionPaso {

	/** DAO acceso BBDD. */
	@Autowired
	private FlujoPasoDao dao;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPasoObtenerAnexo, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Recuperamos parametros
		final TypeSiNo reintentarStr = (TypeSiNo) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "reintentar",
				false);
		boolean reintentar = false;
		if (reintentarStr == TypeSiNo.SI) {
			reintentar = true;
		}

		// Obtenemos datos internos del paso
		final DatosInternosPasoRegistrar pDipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();

		// Valida si se puede registrar el tramite
		validacionesRegistrar(pDipa, pDpp, pVariablesFlujo, pDefinicionTramite);

		// Generamos asiento registral
		AsientoRegistral asiento = null;
		if (!reintentar) {
			asiento = generarAsiento(pDipa, pVariablesFlujo, pDefinicionTramite, true);
		}

		// Obtenemos documento de asiento para obtener id sesion registro
		final DocumentoPasoPersistencia docAsientoDpp = pDpp
				.getDocumentoPasoPersistencia(ConstantesFlujo.ID_ASIENTO_REGISTRO, ConstantesNumero.N1);
		final String idSesionRegistro = docAsientoDpp.getRegistroIdSesion();

		// Preparamos info para registrar
		RegistroIsolatedData registroIsolatedData = new RegistroIsolatedData();
		registroIsolatedData.setDestino(pVariablesFlujo.getTipoDestino());
		registroIsolatedData.setIdSesionRegistro(idSesionRegistro);
		registroIsolatedData.setCodigoEntidad(pDipa.getParametrosRegistro().getDatosRegistrales().getCodigoEntidad());
		registroIsolatedData.setDebugEnabled(pVariablesFlujo.isDebugEnabled());
		registroIsolatedData.setIdTramite(pVariablesFlujo.getIdTramite());
		registroIsolatedData.setVersionTramite(pVariablesFlujo.getVersionTramite());
		registroIsolatedData.setCodigoProcedimiento(pVariablesFlujo.getDatosTramiteCP().getProcedimiento().getIdentificador());
		registroIsolatedData.setCodigoSIA(pDipa.getParametrosRegistro().getDatosRegistrales().getCodigoSIA());
		registroIsolatedData.setModoEntregaHabilitado(UtilsSTG.isModoEntregaHabilitado(pDefinicionTramite));
		registroIsolatedData.setModoEntregaInmediato(UtilsSTG.isModoEntregaInmediato(pDefinicionTramite));
		registroIsolatedData.setIdEnvioRemoto(pDipa.getParametrosRegistro().getDatosRegistrales().getIdEnvioRemoto());
        registroIsolatedData.setAsiento(asiento);

        // Devolvemos respuesta con datos para registrar
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		rp.addParametroRetorno("registroIsolatedData", registroIsolatedData);
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;
	}

	/**
	 * Valida si se puede registrar el trámite.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pDpp
	 *                               Datos persistencia paso
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 */
	private void validacionesRegistrar(final DatosInternosPasoRegistrar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo, final DefinicionTramiteSTG pDefinicionTramite) {

		final DetallePasoRegistrar detallePasoRegistrar = (DetallePasoRegistrar) pDipa.getDetallePaso();

		// El paso debe estar en un estado valido para registrar
		if (pDipa.getEstado() != TypeEstadoPaso.PENDIENTE) {
			throw new AccionPasoNoPermitidaException("La passa no està en un estat vàlid per registrar");
		}

		// Como se ha iniciado sesión registro deberá estar en estado reintentar
		if (detallePasoRegistrar.getReintentar() == TypeSiNo.NO) {
			throw new AccionPasoNoPermitidaException(" No s'ha iniciat sessió registre i no està en estat reintentar");
		}

	}

	/**
	 * Genera asiento registral.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pVariablesFlujo
	 *                               variables flujo
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pContenidoDocs
	 *                               Indica si genera o no el contenido de los
	 *                               documentos (para debug generar sin contenido)
	 * @return asiento registral
	 */
	private AsientoRegistral generarAsiento(final DatosInternosPasoRegistrar pDipa,
			final VariablesFlujo pVariablesFlujo, final DefinicionTramiteSTG pDefinicionTramite,
			final boolean pContenidoDocs) {
		final AsientoRegistral asiento = new AsientoRegistral();
		// - Datos origen
		final DatosOrigen datosOrigen = new DatosOrigen();
		datosOrigen.setCodigoEntidad(pDipa.getParametrosRegistro().getDatosRegistrales().getCodigoEntidad());
		datosOrigen.setCodigoOficinaRegistro(pDipa.getParametrosRegistro().getDatosRegistrales().getOficina());
		datosOrigen.setLibroOficinaRegistro(pDipa.getParametrosRegistro().getDatosRegistrales().getLibro());
		datosOrigen.setTipoRegistro(TypeRegistro.REGISTRO_ENTRADA);
		// - Datos asunto
		final DatosAsunto datosAsunto = new DatosAsunto();
		datosAsunto.setCodigoSiaProcedimiento(pDipa.getParametrosRegistro().getDatosRegistrales().getCodigoSIA());
		datosAsunto.setFechaAsunto(new Date());
		datosAsunto.setIdiomaAsunto(pVariablesFlujo.getIdioma());
		datosAsunto.setExtractoAsunto(pDipa.getParametrosRegistro().getDatosRegistrales().getExtracto());
		datosAsunto
				.setCodigoOrganoDestino(pDipa.getParametrosRegistro().getDatosRegistrales().getCodigoOrganoDestino());
		datosAsunto.setNumeroExpediente(pDipa.getParametrosRegistro().getDatosRegistrales().getNumeroExpediente());
		datosAsunto.setTextoExpone(pDipa.getParametrosRegistro().getDatosRegistrales().getTextoExpone());
		datosAsunto.setTextoSolicita(pDipa.getParametrosRegistro().getDatosRegistrales().getTextoSolicita());
		asiento.setDatosAsunto(datosAsunto);
		// - Interesados
		final List<Interesado> interesados = new ArrayList<>();
		if (pDipa.getParametrosRegistro().getDatosRepresentacion() != null) {
			interesados.add(generarInteresado(TypeInteresado.REPRESENTADO,
					pDipa.getParametrosRegistro().getDatosRepresentacion().getRepresentado()));
			interesados.add(generarInteresado(TypeInteresado.REPRESENTANTE,
					pDipa.getParametrosRegistro().getDatosRepresentacion().getRepresentante()));
		} else {
			interesados.add(generarInteresado(TypeInteresado.REPRESENTANTE,
					pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador()));
		}
		asiento.setInteresados(interesados);

		// TODO FH -- PENDIENTE API RW4 PARA PASAR INFO FH EN CAMPOS ESPECIFICOS.
		//  DE MOMENTO SE MAPEAN EN ESTOS CAMPOS YA EXISTENTES:
		//referenciaExterna->Codigo Funcionario Habilitado
		//numeroExpediente->Nif Funcionario Habilitado
		//observaciones->Nombre Funcionario Habilitado
		if (pVariablesFlujo.isFuncionarioHabilitado()) {
			FuncionarioHabilitado fh = pVariablesFlujo.getUsuarioAutenticado().getFuncionarioHabilitado();
			datosAsunto.setReferenciaExterna(fh.getUserName());
			datosAsunto.setNumeroExpediente(fh.getNif());
			datosAsunto.setObservaciones(fh.getNombreApellidos());

		}

		// - Documentos
		final List<DocumentoAsiento> documentosRegistro = new ArrayList<>();
		final DetallePasoRegistrar dpr = (DetallePasoRegistrar) pDipa.getDetallePaso();
		documentosRegistro
				.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getFormularios(), pContenidoDocs));
		documentosRegistro.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getAnexos(), pContenidoDocs));
		documentosRegistro.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getPagos(), pContenidoDocs));
		asiento.setDocumentosRegistro(documentosRegistro);

		asiento.setDatosOrigen(datosOrigen);
		return asiento;
	}

	/**
	 * Genera documentos asiento.
	 *
	 * @param pDipa
	 *                                    Datos internos paso
	 * @param pVariablesFlujo
	 *                                    variables flujo
	 * @param listaDocumentosRegistro
	 *                                    documentos registro
	 * @param pContenidoDocs
	 *                                    Indica si genera o no el contenido de los
	 *                                    documentos (para debug generar sin
	 *                                    contenido)
	 * @return documentos asiento
	 */
	private List<DocumentoAsiento> generarDocumentosRegistro(final DatosInternosPasoRegistrar pDipa,
			final VariablesFlujo pVariablesFlujo, final List<DocumentoRegistro> listaDocumentosRegistro,
			final boolean pContenidoDocs) {
		final List<DocumentoAsiento> documentosRegistro = new ArrayList<>();
		if (listaDocumentosRegistro != null) {
			for (final DocumentoRegistro dr : listaDocumentosRegistro) {
				final List<DocumentoAsiento> documentosAsientoRegistral = generarDocumentosRegistro(pDipa,
						pVariablesFlujo, dr, pContenidoDocs);
				documentosRegistro.addAll(documentosAsientoRegistral);
			}
		}
		return documentosRegistro;
	}

	/**
	 * Genera documento registro.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 * @param pDocReg
	 *                            Documento registro
	 * @param pContenidoDocs
	 *                            Indica si genera o no el contenido de los
	 *                            documentos (para debug generar sin contenido)
	 * @return
	 */
	private List<DocumentoAsiento> generarDocumentosRegistro(final DatosInternosPasoRegistrar pDipa,
			final VariablesFlujo pVariablesFlujo, final DocumentoRegistro pDocReg, final boolean pContenidoDocs) {

		final List<DocumentoAsiento> res = new ArrayList<>();

		// Obtenemos datos documento del flujo
		final DatosDocumento dd = pVariablesFlujo.getDocumento(pDocReg.getId(), pDocReg.getInstancia());

		// Obtiene datos persistencia del fichero
		final DocumentoPasoPersistencia documentoPersistencia = dao.obtenerDocumentoPersistencia(
				pVariablesFlujo.getIdSesionTramitacion(), dd.getIdPaso(), pDocReg.getId(), pDocReg.getInstancia());

		// En funcion del tipo de documento generamos los documentos para el asiento
		switch (dd.getTipo()) {
		case FORMULARIO:
			// Debe ser electronico
			if (dd.getPresentacion() != TypePresentacion.ELECTRONICA) {
				throw new ErrorConfiguracionException(
						"Un formulari no pot tenir presentació presencial: " + dd.getId());
			}

			// XML formulario
			res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, true, pContenidoDocs));
			// PDF formulario
			final ReferenciaFichero refPDF = ((DatosDocumentoFormulario) dd).getPdf();
			// Si tiene firmas, generamos un documento para cada firma
			if (documentoPersistencia.getFirmas() != null && documentoPersistencia.getFirmas().size() > 0) {
				final List<FirmaDocumentoPersistencia> firmas = documentoPersistencia
						.obtenerFirmasFichero(refPDF.getId());
				for (final FirmaDocumentoPersistencia f : firmas) {
					res.add(generarDocumentoRegistro(dd, refPDF, f, false, pContenidoDocs));
				}
			} else {
				// Si no tiene firmas, generamos un documento para el pdf
				res.add(generarDocumentoRegistro(dd, refPDF, null, false, pContenidoDocs));
			}
			break;
		case ANEXO:
			// Si no es presencial no se trata
			if (dd.getPresentacion() == TypePresentacion.ELECTRONICA) {
				// Si tiene firmas, generamos un documento para cada firma
				if (documentoPersistencia.getFirmas() != null && documentoPersistencia.getFirmas().size() > 0) {
					final List<FirmaDocumentoPersistencia> firmas = documentoPersistencia
							.obtenerFirmasFichero(dd.getFichero().getId());
					for (final FirmaDocumentoPersistencia f : firmas) {
						res.add(generarDocumentoRegistro(dd, dd.getFichero(), f, false, pContenidoDocs));
					}
				} else {
					// Si no tiene firmas, generamos un documento para el pdf
					res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, false, pContenidoDocs));
				}
			}
			break;
		case PAGO:
			// XML Pago
			res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, true, pContenidoDocs));
			// Justificante Pago (para pago electronico)
			if (dd.getPresentacion() == TypePresentacion.ELECTRONICA) {
				res.add(generarDocumentoRegistro(dd, ((DatosDocumentoPago) dd).getJustificantePago(), null, false,
						pContenidoDocs));
			}
			break;
		default:
			break;
		}

		return res;
	}

	/**
	 * Genera documento registro.
	 *
	 * @param documento
	 *                           documento
	 * @param refFichero
	 *                           fichero del documento
	 * @param firmaDocumento
	 *                           firma
	 * @param xml
	 *                           si es xml
	 * @param pContenidoDocs
	 *                           Indica si genera o no el contenido de los
	 *                           documentos (para debug generar sin contenido)
	 * @return documento registro
	 */
	private DocumentoAsiento generarDocumentoRegistro(final DatosDocumento documento,
			final ReferenciaFichero refFichero, final FirmaDocumentoPersistencia firmaDocumento, final boolean xml,
			final boolean pContenidoDocs) {

		// TODO Ver si particularizamos titulo
		byte[] contentFic = null;
		String contentFicStr = null;

		// Recuperamos fichero
		final DatosFicheroPersistencia fichero = dao.recuperarFicheroPersistencia(refFichero);
		contentFic = fichero.getContenido();
		contentFicStr = "fic:" + refFichero.getId();
		final String instancia = (documento.getTipo() == TypeDocumento.ANEXO
				? ((DatosDocumentoAnexo) documento).getInstancia() + ""
				: "1");
		final String nombreFic = documento.getId() + "-" + instancia + "."
				+ FilenameUtils.getExtension(fichero.getNombre());

		// Recuperamos firma
		DatosFicheroPersistencia firmaFichero = null;
		String firmaFicheroStr = null;
		boolean anexarFirma = false;
		if (firmaDocumento != null) {
			firmaFichero = dao.recuperarFicheroPersistencia(firmaDocumento.getFirma());
			firmaFicheroStr = "fic:" + firmaDocumento.getFirma().getId();
			anexarFirma = true;
		}

		// Si se anexa un PADES, directamente se anexa la firma
		if (firmaDocumento != null && firmaDocumento.getTipoFirma() == TypeFirmaDigital.PADES) {
			contentFic = firmaFichero.getContenido();
			contentFicStr = "fic:" + firmaDocumento.getFirma().getId();
			anexarFirma = false;
		}

		// Anexo que se ha anexado firmado
		final boolean anexoAnexadoFirmado = documento.getTipo() == TypeDocumento.ANEXO
				&& ((DatosDocumentoAnexo) documento).getAnexadoFirmado() == TypeSiNo.SI;

		// Esta firmado si tiene fichero firma o es un anexo anexado firmado
		final boolean firmado = (firmaDocumento != null || anexoAnexadoFirmado);

		// Creamos info documento asiento registral
		final DocumentoAsiento documentoAsientoRegistral = new DocumentoAsiento();
		documentoAsientoRegistral.setTituloDoc(documento.getTitulo());
		documentoAsientoRegistral.setFechaCaptura(new Date());
		documentoAsientoRegistral.setOrigenDocumento(TypeOrigenDocumento.CIUDADANO);
		documentoAsientoRegistral.setTipoDocumental(calcularTipoDocumental(documento));
		documentoAsientoRegistral.setTipoDocumento(calcularTipoDocumento(documento.getTipo(), xml));
		documentoAsientoRegistral.setValidez(calcularValidez(documento.getTipo(), firmado));
		documentoAsientoRegistral.setNombreFichero(nombreFic);
		documentoAsientoRegistral
				.setContenidoFichero(pContenidoDocs ? contentFic : UtilsFlujo.stringToBytes(contentFicStr));

		// - No firmado
		if (!firmado) {
			documentoAsientoRegistral.setModoFirma(TypeFirmaAsiento.SIN_FIRMA);
		}

		// - Firmado
		if (firmado) {
			es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaDigital tipoFirma;
			TypeFirmaAsiento modoFirma;
			if (anexoAnexadoFirmado) {
				// Anexo anexado firmado
				tipoFirma = es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaDigital.PADES;
				modoFirma = TypeFirmaAsiento.FIRMA_ATTACHED;
			} else {
				// Firmado desde asistente
				tipoFirma = es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaDigital
						.fromString(firmaDocumento.getTipoFirma().toString());
				modoFirma = calcularTipoFirmaAsiento(firmaDocumento.getTipoFirma());
			}
			documentoAsientoRegistral.setTipoFirma(tipoFirma);
			documentoAsientoRegistral.setModoFirma(modoFirma);
			if (anexarFirma) {
				documentoAsientoRegistral.setNombreFirmaAnexada(firmaFichero.getNombre());
				documentoAsientoRegistral.setContenidoFirma(
						pContenidoDocs ? firmaFichero.getContenido() : UtilsFlujo.stringToBytes(firmaFicheroStr));
			}
		}

		return documentoAsientoRegistral;
	}

	protected String calcularTipoDocumental(final DatosDocumento documento) {
		// TODO Ver de establecer configuración por STG
		String tipoDocumental = "TD99";
		if (documento.getTipo() == TypeDocumento.FORMULARIO) {
			tipoDocumental = "TD14";
		}
		return tipoDocumental;
	}

	/**
	 * Calcula tipo firma digital asiento.
	 *
	 * @param typeFirmaDigital
	 *                             tipo firma digital
	 * @return tipo firma asiento
	 */
	private TypeFirmaAsiento calcularTipoFirmaAsiento(final TypeFirmaDigital typeFirmaDigital) {
		TypeFirmaAsiento res = null;
		switch (typeFirmaDigital) {
		case PADES:
			res = TypeFirmaAsiento.FIRMA_ATTACHED;
			break;
		case XADES_DETACHED:
			res = TypeFirmaAsiento.FIRMA_DETACHED;
			break;
		case XADES_ENVELOPED:
			res = TypeFirmaAsiento.FIRMA_ATTACHED;
			break;
		case CADES_DETACHED:
			res = TypeFirmaAsiento.FIRMA_DETACHED;
			break;
		case CADES_ATTACHED:
			res = TypeFirmaAsiento.FIRMA_ATTACHED;
			break;
		default:
			throw new TipoNoControladoException("Tipus firma no controlat: " + typeFirmaDigital);
		}
		return res;
	}

	/**
	 * Calcula tipo documento asiento.
	 *
	 * @param tipoDocumento
	 *                          tipo documento flujo
	 * @param xml
	 *                          si es documento xml
	 * @return tipo documento asiento
	 */
	private TypeDocumental calcularTipoDocumento(
			final TypeDocumento tipoDocumento, final boolean xml) {
		TypeDocumental res;
		if (xml) {
			res = TypeDocumental.FICHERO_TECNICO;
		} else {
			if (tipoDocumento == TypeDocumento.FORMULARIO) {
				res = TypeDocumental.FORMULARIO;
			} else {
				res = TypeDocumental.ANEXO;
			}
		}
		return res;
	}

	/**
	 * Calcula tipo validez.
	 *
	 * @param tipoDocumento
	 *                          Tipo documento
	 * @param firmado
	 * @return Tipo validez
	 */
	private TypeValidez calcularValidez(final TypeDocumento tipoDocumento,
			final boolean firmado) {
		TypeValidez res = null;
		switch (tipoDocumento) {
		case FORMULARIO:
			res = TypeValidez.ORIGINAL;
			break;
		case ANEXO:
			if (firmado) {
				res = TypeValidez.ORIGINAL;
			} else {
				res = TypeValidez.COPIA;
			}
			break;
		case PAGO:
			res = TypeValidez.ORIGINAL;
			break;
		default:
			throw new TipoNoControladoException("Tipus de document no suportat per registre: " + tipoDocumento);
		}
		return res;
	}

	/**
	 * Genera datos interesado.
	 *
	 * @param tipoInteresado
	 *                            tipo
	 * @param datosInteresado
	 *                            datos usuario
	 * @return interesado
	 */
	private Interesado generarInteresado(final TypeInteresado tipoInteresado, final DatosInteresado datosInteresado) {
		TypeDocumentoIdentificacion tipoDocumento = null;
		if (NifUtils.esNie(datosInteresado.getNif())) {
			tipoDocumento = TypeDocumentoIdentificacion.ID_EXTRANJERO;
		} else if (NifUtils.esNifPersonaJuridica(datosInteresado.getNif())) {
			tipoDocumento = TypeDocumentoIdentificacion.CIF;
		} else if (NifUtils.esNifPersonaFisica(datosInteresado.getNif())) {
			tipoDocumento = TypeDocumentoIdentificacion.NIF;
		} else {
			throw new TipoNoControladoException("Tipus d'identificació no controlat para identificació: " + datosInteresado.getNif());
		}

		final Interesado interesado = new Interesado();
		interesado.setActuaComo(tipoInteresado);
		interesado.setTipoDocumento(tipoDocumento);
		interesado.setDocIdentificacion(datosInteresado.getNif());
		if (tipoDocumento == TypeDocumentoIdentificacion.CIF) {
			interesado.setRazonSocial(datosInteresado.getNombre());
		} else {
			interesado.setNombre(datosInteresado.getNombre());
			interesado.setApellido1(datosInteresado.getApellido1());
			interesado.setApellido2(datosInteresado.getApellido2());
		}

		if (datosInteresado.getContacto() != null) {
			interesado.setPais(datosInteresado.getContacto().getPais());
			interesado.setProvincia(datosInteresado.getContacto().getProvincia());
			interesado.setMunicipio(datosInteresado.getContacto().getMunicipio());
			interesado.setDireccion(datosInteresado.getContacto().getDireccion());
			interesado.setCodigoPostal(datosInteresado.getContacto().getCodigoPostal());
			interesado.setEmail(datosInteresado.getContacto().getEmail());
			interesado.setTelefono(datosInteresado.getContacto().getTelefono());
		}

		return interesado;
	}

}
