package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.DatosAsunto;
import es.caib.sistra2.commons.plugins.registro.api.DatosOrigen;
import es.caib.sistra2.commons.plugins.registro.api.DocumentoAsiento;
import es.caib.sistra2.commons.plugins.registro.api.Interesado;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumental;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumentoIdentificacion;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaAsiento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeInteresado;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeOrigenDocumento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeValidez;
import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistramit.core.api.exception.AccesoNoPermitidoException;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoPago;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.FirmaDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.ResultadoRegistro;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite registrar tramite en el paso Registrar.
 *
 * @author Indra
 *
 */
@Component("accionRtRegistrarTramite")
public final class AccionRegistrarTramite implements AccionPaso {

	// TODO PENDIENTE PREREGISTRO (ENTREGA PRESENCIAL). VER QUE PASA CON
	// JUSTIFICANTE PARA REGISTRO (NO NECESARIO?) Y PARA PRESENCIAL (NECESARIO?).
	// VERIFICAR SI DOCS FIRMADOS.

	/** DAO acceso BBDD. */
	@Autowired
	private FlujoPasoDao dao;
	/** Literales negocio. */
	@Autowired
	private Literales literales;
	/** Componente de registro. */
	@Autowired
	private RegistroComponent registroComponent;

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
		validacionesRegistrar(pDipa, pDpp, reintentar, pVariablesFlujo);

		// Realizamos proceso de registro
		final ResultadoRegistrar resReg = registrarTramite(pDipa, pDpp, reintentar, pVariablesFlujo,
				pDefinicionTramite);

		// Actualizamos persistencia
		actualizarPersistencia(pVariablesFlujo.getIdSesionTramitacion(), pDipa, pDpp, resReg);

		// Actualizamos detalle
		actualizarDetalleRegistrar(pDipa, pVariablesFlujo, pDpp, resReg);

		// Devolvemos respuesta indicando resultado registro
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		rp.addParametroRetorno("resultado", resReg);
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;
	}

	/**
	 * Valida si se puede registrar el trámite.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pDpp
	 *            Datos persistencia paso
	 * @param pReintentar
	 *            Indica si se debe reintentar registro
	 * @param pVariablesFlujo
	 *            Variables flujo
	 */
	private void validacionesRegistrar(final DatosInternosPasoRegistrar pDipa, final DatosPersistenciaPaso pDpp,
			final boolean pReintentar, final VariablesFlujo pVariablesFlujo) {

		final DetallePasoRegistrar detallePasoRegistrar = (DetallePasoRegistrar) pDipa.getDetallePaso();

		// El paso debe estar en un estado valido para registrar
		if (pDipa.getEstado() != TypeEstadoPaso.PENDIENTE) {
			throw new AccionPasoNoPermitidaException("El paso no esta en un estado valido para registrar");
		}

		// Si hay que reintentar debe estar en ese estado
		if (pReintentar && detallePasoRegistrar.getReintentar() == TypeSiNo.NO) {
			throw new AccionPasoNoPermitidaException(" Si hay que reintentar debe estar en ese estado");
		}
		if (!pReintentar && detallePasoRegistrar.getReintentar() == TypeSiNo.SI) {
			throw new AccionPasoNoPermitidaException(" Si hay que reintentar debe estar en ese estado");
		}

		// Validaciones registro
		if (!pReintentar) {
			// Controlamos que no haya llegado el fin de plazo
			if (pVariablesFlujo.getFechaFinPlazo() != null && pVariablesFlujo.getFechaFinPlazo().before(new Date())) {
				final String mensaje = literales.getLiteral(Literales.FLUJO, "acceso.fueraPlazo",
						pVariablesFlujo.getIdioma());
				throw new AccesoNoPermitidoException(mensaje);
			}
			// Debe ser presentado por el presentador (se podra comprobar en
			// caso de que el acceso sea autenticado)
			if (pVariablesFlujo.getNivelAutenticacion() != TypeAutenticacion.ANONIMO && !pVariablesFlujo.getUsuario()
					.getNif().equals(pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador().getNif())) {
				throw new AccionPasoNoPermitidaException("El trámite debe ser registrado por el presentador ("
						+ pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador().getNif() + ")");
			}
			// Verificar si los documentos estan firmados
			if (!detallePasoRegistrar.verificarFirmas()) {
				throw new AccionPasoNoPermitidaException("No están todos los documentos firmados");
			}

		}
	}

	/**
	 * Registra el tramite.
	 *
	 * @param pDipa
	 *            Datos internos tramite
	 * @param pDpp
	 *            Datos persistencia paso
	 * @param reintentar
	 *            reintentar
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @return Resultado registro
	 */
	private ResultadoRegistrar registrarTramite(final DatosInternosPasoRegistrar pDipa,
			final DatosPersistenciaPaso pDpp, boolean reintentar, final VariablesFlujo pVariablesFlujo,
			final DefinicionTramiteSTG pDefinicionTramite) {
		ResultadoRegistrar resReg = null;

		if (reintentar) {
			resReg = registroComponent.reintentarRegistro(pVariablesFlujo.getIdSesionTramitacion(),
					pVariablesFlujo.isDebugEnabled());
		} else {
			// Generar asiento
			final AsientoRegistral asiento = generarAsiento(pDipa, pVariablesFlujo, pDefinicionTramite);
			resReg = registroComponent.registrar(pVariablesFlujo.getIdSesionTramitacion(), asiento,
					pVariablesFlujo.isDebugEnabled());

		}

		return resReg;

	}

	/**
	 * Actualizar detalle registrar.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param pDpp
	 *            Datos persistencia
	 * @param resReg
	 *            Resultado registro
	 */
	private void actualizarDetalleRegistrar(final DatosInternosPasoRegistrar pDipa,
			final VariablesFlujo pVariablesFlujo, final DatosPersistenciaPaso pDpp, final ResultadoRegistrar resReg) {

		// Obtenemos documento de asiento para ver si es un preregistro
		final DocumentoPasoPersistencia docAsientoDpp = pDpp
				.getDocumentoPasoPersistencia(ConstantesFlujo.ID_ASIENTO_REGISTRO, ConstantesNumero.N1);
		final boolean preregistro = (docAsientoDpp.getRegistroPreregistro() == TypeSiNo.SI);

		// Actualizamos detalle
		final DetallePasoRegistrar detallePasoRegistrar = (DetallePasoRegistrar) pDipa.getDetallePaso();
		switch (resReg.getResultado()) {
		case CORRECTO:
			// Registro realizado:
			// - establecemos detalle registro
			final ResultadoRegistro rr = new ResultadoRegistro();
			rr.setPreregistro(preregistro);
			rr.setFechaRegistro(resReg.getFechaRegistro());
			rr.setNumeroRegistro(resReg.getNumeroRegistro());
			rr.setAsunto(pVariablesFlujo.getTituloTramite());
			pDipa.setResultadoRegistro(rr);
			// - resetamos info de reintentar
			detallePasoRegistrar.setRegistrar(TypeSiNo.NO);
			detallePasoRegistrar.setReintentar(TypeSiNo.NO);
			break;
		case ERROR:
			// Registro realizado con error
			// - resetamos info de reintentar
			detallePasoRegistrar.setReintentar(TypeSiNo.NO);
			break;
		case REINTENTAR:
			// Se debe reintentar registro
			// - indicamos que hay que reintentar el pago
			detallePasoRegistrar.setReintentar(TypeSiNo.SI);
			break;
		default:
			throw new TipoNoControladoException("Tipo de resultado registro no controlado: " + resReg.getResultado());
		}
	}

	/**
	 * Actualiza paso tras registrar.
	 *
	 * @param pIdSesionTramitacion
	 *            Id sesion tramitacion
	 * @param pDipa
	 *            Datos internos paso
	 * @param pDpp
	 *            Datos persistencia
	 * @param resReg
	 *            Resultado registro
	 */
	private void actualizarPersistencia(final String pIdSesionTramitacion, final DatosInternosPasoRegistrar pDipa,
			final DatosPersistenciaPaso pDpp, final ResultadoRegistrar resReg) {

		// Obtenemos documento de asientO
		final DocumentoPasoPersistencia docAsientoDpp = pDpp
				.getDocumentoPasoPersistencia(ConstantesFlujo.ID_ASIENTO_REGISTRO, ConstantesNumero.N1);

		// Actualizamos estado en funcion del resultado de registro
		// - Actualizamos persistencia
		switch (resReg.getResultado()) {
		case CORRECTO:
			// Registro realizado:
			// - updateamos estado documento asiento para indicar resultado
			// registro
			docAsientoDpp.setEstado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
			docAsientoDpp.setRegistroResultado(resReg.getResultado());
			docAsientoDpp.setRegistroNumeroRegistro(resReg.getNumeroRegistro());
			docAsientoDpp.setRegistroFechaRegistro(resReg.getFechaRegistro());
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		case ERROR:
			// Error al registrar:
			// - updateamos estado documento asiento para resetear estado y
			// permitir nuevo registro
			docAsientoDpp.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
			docAsientoDpp.setRegistroResultado(null);
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		case REINTENTAR:
			// Hay que reintentar el proceso de registro
			docAsientoDpp.setEstado(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE);
			docAsientoDpp.setRegistroResultado(TypeResultadoRegistro.REINTENTAR);
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		default:
			throw new TipoNoControladoException("Tipo de resultado registro no controlado: " + resReg.getResultado());
		}
	}

	/**
	 * Genera asiento registral.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pVariablesFlujo
	 *            variables flujo
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @return asiento registral
	 */
	private AsientoRegistral generarAsiento(DatosInternosPasoRegistrar pDipa, VariablesFlujo pVariablesFlujo,
			DefinicionTramiteSTG pDefinicionTramite) {
		final AsientoRegistral asiento = new AsientoRegistral();
		// - Datos origen
		final DatosOrigen datosOrigen = new DatosOrigen();
		datosOrigen.setCodigoEntidad(pDefinicionTramite.getDefinicionVersion().getIdEntidad());
		datosOrigen.setCodigoOficinaRegistro(pDipa.getParametrosRegistro().getDatosRegistrales().getOficina());
		datosOrigen.setLibroOficinaRegistro(pDipa.getParametrosRegistro().getDatosRegistrales().getLibro());
		datosOrigen.setTipoRegistro(TypeRegistro.REGISTRO_ENTRADA);
		// - Datos asunto
		final DatosAsunto datosAsunto = new DatosAsunto();
		datosAsunto.setFechaAsunto(new Date());
		datosAsunto.setIdiomaAsunto(pVariablesFlujo.getIdioma());
		datosAsunto.setExtractoAsunto(pVariablesFlujo.getTituloTramite());
		datosAsunto.setTipoAsunto(pDipa.getParametrosRegistro().getDatosRegistrales().getTipoAsunto());
		datosAsunto.setCodigoOrganoDestino(pVariablesFlujo.getDatosTramiteCP().getOrganoDestinoDir3());
		asiento.setDatosAsunto(datosAsunto);
		// - Interesados
		final List<Interesado> interesados = new ArrayList<>();
		if (pDipa.getParametrosRegistro().getDatosRepresentacion().getRepresentado() != null) {
			interesados.add(generarInteresado(TypeInteresado.REPRESENTANTE,
					pDipa.getParametrosRegistro().getDatosRepresentacion().getRepresentado()));
			interesados.add(generarInteresado(TypeInteresado.REPRESENTADO,
					pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador()));
		} else {
			interesados.add(generarInteresado(TypeInteresado.REPRESENTANTE,
					pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador()));
		}
		asiento.setInteresados(interesados);
		// - Documentos
		final List<DocumentoAsiento> documentosRegistro = new ArrayList<>();
		final DetallePasoRegistrar dpr = (DetallePasoRegistrar) pDipa.getDetallePaso();
		documentosRegistro.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getFormularios()));
		documentosRegistro.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getAnexos()));
		documentosRegistro.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getPagos()));
		asiento.setDocumentosRegistro(documentosRegistro);

		asiento.setDatosOrigen(datosOrigen);
		return asiento;
	}

	/**
	 * Genera documentos asiento.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pVariablesFlujo
	 *            variables flujo
	 * @param listaDocumentosRegistro
	 *            documentos registro
	 * @return documentos asiento
	 */
	private List<DocumentoAsiento> generarDocumentosRegistro(DatosInternosPasoRegistrar pDipa,
			VariablesFlujo pVariablesFlujo,
			List<es.caib.sistramit.core.api.model.flujo.DocumentoRegistro> listaDocumentosRegistro) {
		final List<DocumentoAsiento> documentosRegistro = new ArrayList<>();
		if (listaDocumentosRegistro != null) {
			for (final es.caib.sistramit.core.api.model.flujo.DocumentoRegistro dr : listaDocumentosRegistro) {
				final List<DocumentoAsiento> documentosAsientoRegistral = generarDocumentosRegistro(pDipa,
						pVariablesFlujo, dr);
				documentosRegistro.addAll(documentosAsientoRegistral);
			}
		}
		return documentosRegistro;
	}

	/**
	 * Genera documento registro.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param pDocReg
	 *            Documento registro
	 * @return
	 */
	private List<DocumentoAsiento> generarDocumentosRegistro(DatosInternosPasoRegistrar pDipa,
			VariablesFlujo pVariablesFlujo, final es.caib.sistramit.core.api.model.flujo.DocumentoRegistro pDocReg) {

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
						"Un formulario no puede tener presentación presencial: " + dd.getId());
			}

			// XML formulario
			res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, true));
			// PDF formulario
			final ReferenciaFichero refPDF = ((DatosDocumentoFormulario) dd).getPdf();
			// Si tiene firmas, generamos un documento para cada firma
			if (documentoPersistencia.getFirmas() != null && documentoPersistencia.getFirmas().size() > 0) {
				final List<FirmaDocumentoPersistencia> firmas = documentoPersistencia
						.obtenerFirmasFichero(refPDF.getId());
				for (final FirmaDocumentoPersistencia f : firmas) {
					res.add(generarDocumentoRegistro(dd, refPDF, f, false));
				}
			} else {
				// Si no tiene firmas, generamos un documento para el pdf
				res.add(generarDocumentoRegistro(dd, refPDF, null, false));
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
						res.add(generarDocumentoRegistro(dd, dd.getFichero(), f, false));
					}
				} else {
					// Si no tiene firmas, generamos un documento para el pdf
					res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, false));
				}
			}
			break;
		case PAGO:
			// XML Pago
			res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, true));
			// Justificante Pago (para pago electronico)
			if (dd.getPresentacion() == TypePresentacion.ELECTRONICA) {
				res.add(generarDocumentoRegistro(dd, ((DatosDocumentoPago) dd).getJustificantePago(), null, false));
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
	 *            documento
	 * @param refFichero
	 *            fichero del documento
	 * @param firmaDocumento
	 *            firma
	 * @param xml
	 *            si es xml
	 * @return documento registro
	 */
	private DocumentoAsiento generarDocumentoRegistro(final DatosDocumento documento,
			final ReferenciaFichero refFichero, final FirmaDocumentoPersistencia firmaDocumento, final boolean xml) {

		// TODO Ver si particularizamos titulo

		// Recuperamos fichero
		final DatosFicheroPersistencia fichero = dao.recuperarFicheroPersistencia(refFichero);
		byte[] contentFic = fichero.getContenido();
		String nombreFic = fichero.getNombre();

		// Recuperamos firma
		DatosFicheroPersistencia firmaFichero = null;
		boolean anexarFirma = false;
		if (firmaDocumento != null) {
			firmaFichero = dao.recuperarFicheroPersistencia(firmaDocumento.getFirma());
			anexarFirma = true;
		}

		// Si se anexa un PADES, directamente se anexa la firma
		if (firmaDocumento != null && firmaDocumento.getTipoFirma() == TypeFirmaDigital.PADES) {
			contentFic = firmaFichero.getContenido();
			nombreFic = firmaFichero.getNombre();
			anexarFirma = false;
		}

		final DocumentoAsiento documentoAsientoRegistral = new DocumentoAsiento();
		documentoAsientoRegistral.setTituloDoc(documento.getTitulo());
		documentoAsientoRegistral.setFechaCaptura(new Date());
		documentoAsientoRegistral.setOrigenDocumento(TypeOrigenDocumento.CIUDADANO);
		documentoAsientoRegistral.setTipoDocumental("TD14");
		documentoAsientoRegistral.setTipoDocumento(calcularTipoDocumental(documento.getTipo(), xml));
		documentoAsientoRegistral.setValidez(calcularValidez(documento.getTipo()));
		documentoAsientoRegistral.setNombreFichero(nombreFic);
		documentoAsientoRegistral.setContenidoFichero(contentFic);
		if (anexarFirma) {
			documentoAsientoRegistral.setNombreFirmaAnexada(firmaFichero.getNombre());
			documentoAsientoRegistral.setContenidoFirma(firmaFichero.getContenido());
			documentoAsientoRegistral.setModoFirma(calcularTipoFirmaAsiento(firmaDocumento.getTipoFirma()));
		} else {
			documentoAsientoRegistral.setModoFirma(TypeFirmaAsiento.SIN_FIRMA);
		}
		return documentoAsientoRegistral;
	}

	/**
	 * Calcula tipo firma digital asiento.
	 *
	 * @param typeFirmaDigital
	 *            tipo firma digital
	 * @return tipo firma asiento
	 */
	private TypeFirmaAsiento calcularTipoFirmaAsiento(TypeFirmaDigital typeFirmaDigital) {
		TypeFirmaAsiento res = null;
		switch (typeFirmaDigital) {
		case PADES:
			res = TypeFirmaAsiento.FIRMA_ATTACHED;
			break;
		case XADES_DETACHED:
			res = TypeFirmaAsiento.FIRMA_DETACHED;
			break;
		case CADES_DETACHED:
			res = TypeFirmaAsiento.FIRMA_DETACHED;
			break;
		default:
			throw new TipoNoControladoException("Tipo firma no controlado: " + typeFirmaDigital);
		}
		return res;
	}

	/**
	 * Calcula tipo documento asiento.
	 *
	 * @param tipoDocumento
	 *            tipo documento flujo
	 * @param xml
	 *            si es documento xml
	 * @return tipo documento asiento
	 */
	private TypeDocumental calcularTipoDocumental(
			es.caib.sistramit.core.api.model.flujo.types.TypeDocumento tipoDocumento, boolean xml) {
		TypeDocumental res;
		if (xml) {
			res = TypeDocumental.FICHERO_TECNICO;
		} else {
			if (tipoDocumento == es.caib.sistramit.core.api.model.flujo.types.TypeDocumento.FORMULARIO) {
				res = TypeDocumental.FORMULARIO;
			} else {
				res = TypeDocumental.ANEXO;
			}
		}
		return res;
	}

	private TypeValidez calcularValidez(es.caib.sistramit.core.api.model.flujo.types.TypeDocumento tipoDocumento) {
		TypeValidez res = null;
		switch (tipoDocumento) {
		case FORMULARIO:
			res = TypeValidez.ORIGINAL;
			break;
		case ANEXO:
			res = TypeValidez.COPIA;
			break;
		case PAGO:
			res = TypeValidez.ORIGINAL;
			break;
		default:
			throw new TipoNoControladoException("Tipo de documento no soportado para registro: " + tipoDocumento);
		}
		return res;
	}

	/**
	 * Genera datos interesado.
	 *
	 * @param tipoInteresado
	 *            tipo
	 * @param datosUsuario
	 *            datos usuario
	 * @return interesado
	 */
	private Interesado generarInteresado(TypeInteresado tipoInteresado, final DatosUsuario datosUsuario) {

		TypeDocumentoIdentificacion tipoDocumento = null;
		if (ValidacionesTipo.getInstance().esNif(datosUsuario.getNif())
				|| ValidacionesTipo.getInstance().esNie(datosUsuario.getNif())) {
			tipoDocumento = TypeDocumentoIdentificacion.NIF;
		} else if (ValidacionesTipo.getInstance().esCif(datosUsuario.getNif())) {
			tipoDocumento = TypeDocumentoIdentificacion.CIF;
		} else {
			throw new TipoNoControladoException("Tipo de identificación no controlado");
		}

		final Interesado representado = new Interesado();
		representado.setActuaComo(tipoInteresado);
		representado.setTipoDocumento(tipoDocumento);
		representado.setDocIdentificacion(datosUsuario.getNif());
		representado.setNombre(datosUsuario.getNombre());
		representado.setApellido1(datosUsuario.getApellido1());
		representado.setApellido2(datosUsuario.getApellido2());
		return representado;
	}

}
