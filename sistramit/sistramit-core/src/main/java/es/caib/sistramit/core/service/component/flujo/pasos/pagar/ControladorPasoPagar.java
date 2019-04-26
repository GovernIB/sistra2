package es.caib.sistramit.core.service.component.flujo.pasos.pagar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RPagoTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionPagar;
import es.caib.sistramit.core.api.exception.AccionPasoNoExisteException;
import es.caib.sistramit.core.api.exception.ConfiguracionModificadaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.DetalleEstadoPagoIncorrecto;
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.Pago;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoPagar;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPago;
import es.caib.sistramit.core.service.model.flujo.DatosCalculoPago;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoPago;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoPagar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.EstadoMarcadores;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubEstadoPasoPagar;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubestadoPaso;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Controlador para paso Pagar.
 *
 * @author Indra
 *
 */
@Component("controladorPasoPagar")
public final class ControladorPasoPagar extends ControladorPasoReferenciaImpl {

	/** Pagos iniciados. */
	private static final String MARCADOR_PAGOS_INICIADOS = "pagosIniciados";
	/** Pagos completados. */
	private static final String MARCADOR_PAGOS_COMPLETADOS = "pagosCompletados";
	/** Pendiente asistente. */
	private static final String MARCADOR_PENDIENTE_ASISTENTE = "pendienteAsistente";

	/** Accion abrir pago. */
	@Autowired
	private AccionIniciarPago accionIniciarPago;
	/** Accion abrir pago. */
	@Autowired
	private AccionVerificarPagoPasarela accionVerificarPagoPasarela;
	/** Accion descargar justificante pago. */
	@Autowired
	private AccionDescargarJustificantePago accionDescargarJustificantePago;
	/** Accion cancelar pago iniciado. */
	@Autowired
	private AccionCancelarPagoIniciado accionCancelarPagoIniciado;
	/** Accion carta de pago presencial. */
	@Autowired
	private AccionCartaPagoPresencial accionCartaPagoPresencial;

	@Override
	protected void actualizarDatosInternos(final DatosPaso pDatosPaso, final DatosPersistenciaPaso dpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final TypeFaseActualizacionDatosInternos pFaseEjecucion) {
		// Obtenemos datos internos del paso
		final DatosInternosPasoPagar dipa = (DatosInternosPasoPagar) pDatosPaso.internalData();
		// Regenera datos del paso pagar
		regenerarDatosPasoPagar(dipa, dpp, pVariablesFlujo, pFaseEjecucion, pDefinicionTramite);
	}

	@Override
	protected EstadoSubestadoPaso evaluarEstadoPaso(final DatosPaso pDatosPaso) {
		// Obtenemos datos internos del paso
		final DatosInternosPasoPagar pDip = (DatosInternosPasoPagar) pDatosPaso.internalData();
		// Calculamos marcadores estado pagar
		final EstadoMarcadores marcadores = calcularMarcadoresEstado(pDip);
		// Establecemos estado en funcion de los marcadores
		TypeEstadoPaso estado;
		if (marcadores.getMarcador(MARCADOR_PENDIENTE_ASISTENTE)) {
			estado = TypeEstadoPaso.PENDIENTE;
		} else {
			estado = TypeEstadoPaso.COMPLETADO;
		}
		// Establecemos subestado en funcion de si se han iniciado pagos
		TypeSubestadoPaso subestado;
		if (marcadores.getMarcador(MARCADOR_PAGOS_INICIADOS)) {
			subestado = TypeSubEstadoPasoPagar.PAGO_INICIADO;
		} else if (marcadores.getMarcador(MARCADOR_PAGOS_COMPLETADOS)) {
			subestado = TypeSubEstadoPasoPagar.PAGO_COMPLETADO;
		} else {
			subestado = null;
		}
		return new EstadoSubestadoPaso(estado, subestado);
	}

	@Override
	protected List<DatosDocumento> obtenerDocumentosCompletadosPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final DefinicionTramiteSTG pDefinicionTramite) {
		// Obtenemos datos internos del paso
		final DatosInternosPasoPagar pDipa = (DatosInternosPasoPagar) pDatosPaso.internalData();
		// Buscamos los documentos completados
		final List<DatosDocumento> res = new ArrayList<>();
		for (final Pago p : ((DetallePasoPagar) pDipa.getDetallePaso()).getPagos()) {
			if (p.getRellenado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
				final DatosDocumentoPago ddp = crearDatosDocumentoPago(pDipa, pDpp, p, pDefinicionTramite);
				res.add(ddp);
			}
		}
		return res;
	}

	/**
	 * Crea los datos del documento de pago accesibles desde otros pasos.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pDpp
	 *            Datos persistencia
	 * @param pDetallePago
	 *            Detalle pago
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @return Datos documento pago
	 */
	private DatosDocumentoPago crearDatosDocumentoPago(final DatosInternosPasoPagar pDipa,
			final DatosPersistenciaPaso pDpp, final Pago pDetallePago, final DefinicionTramiteSTG pDefinicionTramite) {
		// Obtenemos documento de persistencia
		final DocumentoPasoPersistencia dpp = pDpp.getDocumentoPasoPersistencia(pDetallePago.getId(),
				ConstantesNumero.N1);
		// Establecemos datos documento pago
		final DatosDocumentoPago ddp = new DatosDocumentoPago();
		ddp.setIdPaso(pDpp.getId());
		ddp.setId(pDetallePago.getId());
		ddp.setTitulo(pDetallePago.getTitulo());
		ddp.setFichero(dpp.getFichero());
		ddp.setPresentacion(pDetallePago.getPresentacion());
		ddp.setJustificantePago(dpp.getPagoJustificantePdf());
		return ddp;
	}

	@Override
	protected RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		AccionPaso accionPaso = null;
		switch ((TypeAccionPasoPagar) pAccionPaso) {
		case INICIAR_PAGO:
			accionPaso = accionIniciarPago;
			break;
		case CANCELAR_PAGO_INICIADO:
			accionPaso = accionCancelarPagoIniciado;
			break;
		case VERIFICAR_PAGO_PASARELA:
			accionPaso = accionVerificarPagoPasarela;
			break;
		case CARTA_PAGO_PRESENCIAL:
			accionPaso = accionCartaPagoPresencial;
			break;
		case DESCARGAR_JUSTIFICANTE:
			accionPaso = accionDescargarJustificantePago;
			break;
		default:
			throw new AccionPasoNoExisteException("No existe acción " + pAccionPaso + " en el paso pagar");
		}

		final RespuestaEjecutarAccionPaso respuesta = accionPaso.ejecutarAccionPaso(pDatosPaso, pDpp, pAccionPaso,
				pParametros, pDefinicionTramite, pVariablesFlujo);
		return respuesta;
	}

	// -----------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -----------------------------------------------------------------------
	/**
	 * Calcula el detalle del paso a partir de la definición. Para cada pago
	 * almacena sus datos del pago en los datos internos del paso.
	 *
	 * @param pDipa
	 *            Datos internos del paso
	 * @param pDefinicionTramite
	 *            Parámetro definicion tramite
	 * @param pVariablesFlujo
	 *            Parámetro variables flujo
	 * @return el detalle paso pagar
	 */
	private DetallePasoPagar calcularDetalle(final DatosInternosPasoPagar pDipa,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		// Obtenemos definición del paso
		final RPasoTramitacionPagar defPaso = (RPasoTramitacionPagar) UtilsSTG.devuelveDefinicionPaso(pDipa.getIdPaso(),
				pDefinicionTramite);
		// Recorremos la lista fija de documentos (genera en los datos internos
		// del paso los datos de los pagos a realizar)
		final List<Pago> pagosFij = calcularDetalleListaFijaPagos(pDipa, pDefinicionTramite, defPaso, pVariablesFlujo);
		// Creamos detalle paso
		final DetallePasoPagar dpa = new DetallePasoPagar();
		dpa.setId(defPaso.getIdentificador());
		dpa.getPagos().addAll(pagosFij);
		dpa.setCompletado(TypeSiNo.NO);
		return dpa;
	}

	/**
	 * Calcula la lista fija de pagos.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pPasoDef
	 *            Parámetro def paso
	 * @param pVariablesFlujo
	 *            Parámetro variables flujo
	 * @return Lista de pagos fijos
	 */
	private List<Pago> calcularDetalleListaFijaPagos(final DatosInternosPasoPagar pDipa,
			final DefinicionTramiteSTG pDefinicionTramite, final RPasoTramitacionPagar pPasoDef,
			final VariablesFlujo pVariablesFlujo) {

		final List<Pago> pagos = new ArrayList<>();

		for (final RPagoTramite detalle : pPasoDef.getPagos()) {

			final Pago pago = new Pago();

			// Establecemos id y titulo
			pago.setId(detalle.getIdentificador());
			pago.setTitulo(detalle.getDescripcion());

			// Comprobamos obligatoriedad
			TypeObligatoriedad rs = TypeObligatoriedad.fromString(detalle.getObligatoriedad());
			if (rs == TypeObligatoriedad.DEPENDIENTE) {
				// Si es dependiente ejecutamos script de dependencia
				rs = UtilsFlujo.ejecutarScriptDependenciaDocumento(detalle.getScriptDependencia(),
						detalle.getIdentificador(), pVariablesFlujo, null, getScriptFlujo(), pDefinicionTramite);
				// Si queda como dependiente no hay que pagar
				if (rs == TypeObligatoriedad.DEPENDIENTE) {
					// Pasamos al siguiente pago
					continue;
				}
			}
			pago.setObligatorio(rs);

			// TODO SUBSANACION REVISAR SEGUN REQUISITOS A ESTABLECER
			// Si existe documentacion presencial, permitimos pago presencial
			pago.getPresentacionesPermitidas().add(TypePresentacion.ELECTRONICA);
			if (pVariablesFlujo.existeDocumentacionPresencial()) {
				pago.getPresentacionesPermitidas().add(TypePresentacion.PRESENCIAL);
			}

			// Calculamos los datos del pago y los almacenamos en datos internos
			// paso
			final DatosCalculoPago dp = calcularDatosPagoFijo(pDefinicionTramite, detalle, pVariablesFlujo);
			// Almacenamos datos pago en datos internos del paso
			pDipa.addDatosPago(detalle.getIdentificador(), dp);

			// Añadimos a lista de pagos fijos
			pagos.add(pago);
		}

		return pagos;

	}

	/**
	 * Calcula datos pago fijo.
	 *
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pPagoDef
	 *            Definición pago
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @return Datos del pago
	 */
	private DatosCalculoPago calcularDatosPagoFijo(final DefinicionTramiteSTG pDefinicionTramite,
			final RPagoTramite pPagoDef, final VariablesFlujo pVariablesFlujo) {

		if (pPagoDef.getScriptPago() == null) {
			throw new ErrorConfiguracionException(
					"No se ha especificado script de pago para el pago " + pPagoDef.getIdentificador());
		}

		// Ejecutamos script
		final Map<String, String> codigosError = UtilsSTG
				.convertLiteralesToMap(pPagoDef.getScriptPago().getLiterales());
		final RespuestaScript rs = getScriptFlujo().executeScriptFlujo(TypeScriptFlujo.SCRIPT_DATOS_PAGO,
				pPagoDef.getIdentificador(), pPagoDef.getScriptPago().getScript(), pVariablesFlujo, null, null,
				codigosError, pDefinicionTramite);
		final ResPago resf = (ResPago) rs.getResultado();
		final DatosCalculoPago dp = resf.getDatosPago();
		// Establecemos fecha del pago
		final Date hoy = new Date();
		dp.setFecha(UtilsFlujo.formateaFechaFront(hoy));
		// Si no se indica contribuyente, por defecto sera el iniciador
		if (dp.getContribuyente() == null) {
			// Si el acceso es anonimo no sabemos contribuyente
			if (pVariablesFlujo.getNivelAutenticacion() == TypeAutenticacion.ANONIMO) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_DATOS_PAGO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), pPagoDef.getIdentificador(),
						"No se ha especificado contribuyente y el trámite se ha iniciado de forma anónima");
			}
			// Por defecto el contribyente sera el iniciador
			dp.setContribuyente(UtilsFlujo.usuarioPersona(pVariablesFlujo.getUsuario()));
		}
		return dp;
	}

	/**
	 * Regenera datos a partir persistencia.
	 *
	 * @param pDipa
	 *            Datos internos paso pagar
	 * @param pDpp
	 *            Datos persistencia
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param pFaseEjecucion
	 *            Indica en que momento se actualizan los datos internos (inicio,
	 *            carga, revisar o después de una acción).
	 * @param pDefinicionTramite
	 *            Definicion tramite
	 */
	private void regenerarDatosPasoPagar(final DatosInternosPasoPagar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo, final TypeFaseActualizacionDatosInternos pFaseEjecucion,
			final DefinicionTramiteSTG pDefinicionTramite) {

		// Calcula detalle en base a la definicion.
		final DetallePasoPagar dpaNew = calcularDetalle(pDipa, pDefinicionTramite, pVariablesFlujo);

		// Se inicializa el detalle
		pDipa.setDetallePaso(dpaNew);

		// Regenera datos de pagos existentes en persistencia
		regenerarPagosExistentes(pDipa, pDpp, pVariablesFlujo, pFaseEjecucion);

		// Genera en persistencia los pagos nuevos
		regenerarPagosNuevos(pDipa, pDpp, pVariablesFlujo);
	}

	/**
	 * Creamos en persistencia los documentos que existen en el detalle pero que aun
	 * no existen en persistencia.
	 *
	 * @param pDipa
	 *            Datos internos paso pagar
	 * @param pDpp
	 *            Datos persistencia
	 * @param pVariablesFlujo
	 *            Variables flujo
	 */
	private void regenerarPagosNuevos(final DatosInternosPasoPagar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo) {
		// Creamos en persistencia los documentos que existen en el detalle pero
		// que aun no existen en persistencia
		for (final Pago detallePago : ((DetallePasoPagar) pDipa.getDetallePaso()).getPagos()) {
			if (pDpp.getNumeroInstanciasDocumento(detallePago.getId()) == 0) {
				final DocumentoPasoPersistencia docDpp = DocumentoPasoPersistencia.createDocumentoPersistencia();
				docDpp.setId(detallePago.getId());
				docDpp.setTipo(TypeDocumentoPersistencia.PAGO);
				docDpp.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
				docDpp.setInstancia(ConstantesNumero.N1);
				getDao().establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docDpp);
				pDpp.getDocumentos().add(docDpp);
			}
		}
	}

	/**
	 * Actualiza la información de los pagos existentes en persistencia.
	 *
	 * @param pDipa
	 *            Datos internos paso pagar
	 * @param pDpp
	 *            Datos persistencia
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param pFaseEjecucion
	 *            Indica en que momento se actualizan los datos internos (inicio,
	 *            carga, revisar o después de una acción).
	 */
	private void regenerarPagosExistentes(final DatosInternosPasoPagar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo, final TypeFaseActualizacionDatosInternos pFaseEjecucion) {

		for (final DocumentoPasoPersistencia docDpp : pDpp.getDocumentos()) {
			// Obtenemos detalle pago
			final Pago detallePago = ((DetallePasoPagar) pDipa.getDetallePaso()).getPago(docDpp.getId());
			// Si no existe en la lista de pagos o es dependiente no hay que
			// pagarlo lo borramos
			if (detallePago == null || detallePago.getObligatorio() == TypeObligatoriedad.DEPENDIENTE) {
				eliminarPago(pDipa, docDpp, pVariablesFlujo);
			} else {
				// Si se ha intentado rellenar actualizamos su informacion
				if (docDpp.getEstado() != TypeEstadoDocumento.SIN_RELLENAR) {
					actualizarDatosPagoIniciado(pDipa, docDpp, detallePago, pVariablesFlujo);
				}
			}
		}
	}

	/**
	 * Actualiza datos pago iniciado (completado o incorrecto).
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pDocumentoPersistencia
	 *            Documento persistencia
	 * @param pDetallePago
	 *            Detalle del pago
	 * @param pVariablesFlujo
	 *            Variables flujo
	 */
	private void actualizarDatosPagoIniciado(final DatosInternosPasoPagar pDipa,
			final DocumentoPasoPersistencia pDocumentoPersistencia, final Pago detallePagoPasarela,
			final VariablesFlujo pVariablesFlujo) {

		// Recuperamos datos sesion de pago y los cacheamos en
		// datos internos
		final DatosFicheroPersistencia xmlDatosSesionPago = getDao()
				.recuperarFicheroPersistencia(pDocumentoPersistencia.getFichero());
		final DatosSesionPago datosSesionPago = ControladorPasoPagarHelper.getInstance()
				.fromXML(xmlDatosSesionPago.getContenido());
		pDipa.addSesionPago(detallePagoPasarela.getId(), datosSesionPago);

		// Detalle pago
		// - Presentacion pago
		detallePagoPasarela.setPresentacion(datosSesionPago.getPresentacion());
		if (datosSesionPago.getPresentacion() != null
				&& !detallePagoPasarela.getPresentacionesPermitidas().contains(datosSesionPago.getPresentacion())) {
			throw new ErrorConfiguracionException(
					"El pago se realizado mediante una forma de presentación que no está permitida");
		}
		// - Rellenado
		detallePagoPasarela.setRellenado(pDocumentoPersistencia.getEstado());
		// - Estado incorrecto
		if (pDocumentoPersistencia.getEstado() != TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
			final String mensajeError = ControladorPasoPagarHelper.getInstance().generarMensajeEstadoIncorrecto(
					getLiterales(), pVariablesFlujo.getIdioma(), pDocumentoPersistencia.getPagoEstadoIncorrecto(),
					pDocumentoPersistencia.getPagoErrorPasarela(),
					pDocumentoPersistencia.getPagoMensajeErrorPasarela());
			detallePagoPasarela.setEstadoIncorrecto(
					new DetalleEstadoPagoIncorrecto(pDocumentoPersistencia.getPagoEstadoIncorrecto(), mensajeError,
							pDocumentoPersistencia.getPagoErrorPasarela(),
							pDocumentoPersistencia.getPagoMensajeErrorPasarela()));
		}
	}

	/**
	 * Elimina pago de persistencia.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pDocDpp
	 *            Documento persistencia
	 * @param pVariablesFlujo
	 *            Variables flujo
	 */
	private void eliminarPago(final DatosInternosPasoPagar pDipa, final DocumentoPasoPersistencia pDocDpp,
			final VariablesFlujo pVariablesFlujo) {
		// Si anteriormente el pago estaba correctamente realizado o
		// estaba iniciado, hay algo mal en el flujo
		// ya que no deberia producirse esta situacion
		if (pDocDpp.getEstado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE
				|| (pDocDpp.getEstado() == TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE)) {
			throw new ConfiguracionModificadaException(
					"Existe un pago iniciado o ya realizado que ya no hay que realizarlo: " + pDocDpp.getId());
		}

		// Borramos documento del paso y ficheros asociados
		getDao().eliminarDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), pDocDpp.getId(),
				pDocDpp.getInstancia());
		final List<ReferenciaFichero> fics = pDocDpp.obtenerReferenciasFicherosPago();
		for (final ReferenciaFichero fdp : fics) {
			getDao().eliminarFicheroPersistencia(fdp);
		}
	}

	/**
	 * Calcula marcadores estado pagar.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @return Marcadores estado
	 */
	private EstadoMarcadores calcularMarcadoresEstado(final DatosInternosPasoPagar pDipa) {
		final EstadoMarcadores marcadores = new EstadoMarcadores();

		// Recorremos anexos
		for (final Pago detallePago : ((DetallePasoPagar) pDipa.getDetallePaso()).getPagos()) {
			// Evaluamos en funcion de si se ha realizado o no
			switch (detallePago.getRellenado()) {
			case RELLENADO_CORRECTAMENTE: // Pago realizado
				// Indicamos que se ha completado el pago
				marcadores.addMarcador(MARCADOR_PAGOS_COMPLETADOS, true);
				break;
			case RELLENADO_INCORRECTAMENTE: // Pago iniciado pero no acabado
				marcadores.addMarcador(MARCADOR_PENDIENTE_ASISTENTE, true);
				// Indicamos que se ha iniciado pago
				marcadores.addMarcador(MARCADOR_PAGOS_INICIADOS, true);
				break;
			case SIN_RELLENAR:
				// Si es obligatorio comprobamos si se debe realizar a
				// traves del asistente o de la bandeja
				if (detallePago.getObligatorio() == TypeObligatoriedad.OBLIGATORIO) {
					marcadores.addMarcador(MARCADOR_PENDIENTE_ASISTENTE, true);
				}
				break;
			default:
				throw new TipoNoControladoException(
						"Tipo rellenado " + detallePago.getRellenado().name() + " no valido para pago");
			}
		}
		return marcadores;
	}

}
