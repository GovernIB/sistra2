package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistramit.core.api.exception.AccionPasoNoExisteException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.AvisoUsuario;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.DocumentosRegistroPorTipo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResRegistro;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoJustificante;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPresentacion;
import es.caib.sistramit.core.service.model.flujo.DatosRegistrales;
import es.caib.sistramit.core.service.model.flujo.DatosRepresentacion;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.EstadoMarcadores;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.ParametrosRegistro;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.ResultadoRegistro;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubEstadoPasoRegistrar;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Controlador para paso Registrar.
 *
 * @author Indra
 *
 */
@Component("controladorPasoRegistrar")
public final class ControladorPasoRegistrar extends ControladorPasoReferenciaImpl {

	/** Accion descargar documento. */
	@Autowired
	private AccionDescargarDocumento accionDescargarDocumento;
	/** Accion iniciar firma documento. */
	@Autowired
	private AccionIniciarFirmaDocumento accionIniciarFirmarDocumento;
	/** Accion verificar firma documento. */
	@Autowired
	private AccionVerificarFirmaDocumento accionVerificarFirmarDocumento;
	/** Accion descargar firma documento. */
	@Autowired
	private AccionDescargarFirma accionDescargarFirma;
	/** Accion registrar tramite. */
	@Autowired
	private AccionRegistrarTramite accionRegistrarTramite;
	/** Componente registro. */
	@Autowired
	private RegistroComponent registroComponent;

	@Override
	protected void actualizarDatosInternos(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final TypeFaseActualizacionDatosInternos pFaseEjecucion) {

		// Obtenemos datos internos del paso
		final DatosInternosPasoRegistrar dipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();

		// Regenera datos a partir de persistencia
		regenerarDatosRegistrar(dipa, pDpp, pDefinicionTramite, pVariablesFlujo);

	}

	@Override
	protected EstadoSubestadoPaso evaluarEstadoPaso(final DatosPaso pDatosPaso) {
		// Obtenemos datos internos del paso
		final DatosInternosPasoRegistrar pDipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();

		// Obtenemos marcadores estado
		final EstadoMarcadores marcadores = obtenerMarcadoresEstado(pDipa);

		// Establecemos estado en funcion de los marcadores
		TypeEstadoPaso estado;
		if (marcadores.getMarcador("pendienteAsistente")) {
			estado = TypeEstadoPaso.PENDIENTE;
		} else {
			estado = TypeEstadoPaso.COMPLETADO;
		}

		// Establecemos subestado
		TypeSubEstadoPasoRegistrar subestado;
		if (((DetallePasoRegistrar) pDipa.getDetallePaso()).getReintentar() == TypeSiNo.SI) {
			subestado = TypeSubEstadoPasoRegistrar.REINTENTAR_REGISTRO;
		} else {
			subestado = null;
		}

		// Devolvemos estado y subestado
		return new EstadoSubestadoPaso(estado, subestado);
	}

	@Override
	protected List<DatosDocumento> obtenerDocumentosCompletadosPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final DefinicionTramiteSTG pDefinicionTramite) {
		// Obtenemos datos internos del paso
		final DatosInternosPasoRegistrar dipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();
		// Devolvemos datos justificante
		final List<DatosDocumento> res = new ArrayList<>();
		final DatosDocumentoJustificante ddj = obtenerDatosDocumentoJustificante(dipa);
		res.add(ddj);
		return res;
	}

	@Override
	protected RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		// Ejecutamos accion
		AccionPaso accionPaso = null;
		switch ((TypeAccionPasoRegistrar) pAccionPaso) {
		case DESCARGAR_DOCUMENTO:
			accionPaso = accionDescargarDocumento;
			break;
		case INICIAR_FIRMA_DOCUMENTO:
			accionPaso = accionIniciarFirmarDocumento;
			break;
		case VERIFICAR_FIRMA_DOCUMENTO:
			accionPaso = accionVerificarFirmarDocumento;
			break;
		case DESCARGAR_FIRMA:
			accionPaso = accionDescargarFirma;
			break;
		case REGISTRAR_TRAMITE:
			accionPaso = accionRegistrarTramite;
			break;
		default:
			throw new AccionPasoNoExisteException("No existe acción " + pAccionPaso + " en el paso registrar");
		}

		final RespuestaEjecutarAccionPaso rp = accionPaso.ejecutarAccionPaso(pDatosPaso, pDpp, pAccionPaso, pParametros,
				pDefinicionTramite, pVariablesFlujo);
		return rp;
	}

	// --------------------- FUNCIONES PRIVADAS --------------------------------

	/**
	 * Regenera datos registrar a partir de los datos de persistencia.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pDpp
	 *                               Datos persistencia
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 */
	private void regenerarDatosRegistrar(final DatosInternosPasoRegistrar pDipa, final DatosPersistenciaPaso pDpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Calculamos parametros registro y los establecemos en los datos internos
		final ParametrosRegistro paramRegistro = calculoParametrosRegistro(pDipa.getIdPaso(), pDefinicionTramite,
				pVariablesFlujo);
		pDipa.setParametrosRegistro(paramRegistro);

		// Inicializamos detalle en funcion de la definicion
		final DetallePasoRegistrar dpaNew = calcularDetalle(pDipa.getIdPaso(), pDefinicionTramite, pVariablesFlujo,
				paramRegistro);
		pDipa.setDetallePaso(dpaNew);

		// Actualizamos detalle a partir persistencia
		// - Regeneramos el documento de asiento
		final DocumentoPasoPersistencia docDppAsiento = regenerarAsiento(pDipa, pDpp, pVariablesFlujo);
		// - Regeneramos justificante
		final DocumentoPasoPersistencia docDppJustificante = regenerarJustificante(pDipa, pDpp, pVariablesFlujo);

		// Si el paso esta finalizado regeneramos datos registro
		if (docDppAsiento.getRegistroResultado() == TypeResultadoRegistro.CORRECTO) {
			final ResultadoRegistro rr = new ResultadoRegistro();
			rr.setPreregistro(docDppAsiento.getRegistroPreregistro() == TypeSiNo.SI);
			rr.setFechaRegistro(docDppAsiento.getRegistroFechaRegistro());
			rr.setNumeroRegistro(docDppAsiento.getRegistroNumeroRegistro());
			rr.setAsunto(pVariablesFlujo.getTituloTramite());
			pDipa.setResultadoRegistro(rr);
		} else if (docDppAsiento.getRegistroResultado() == TypeResultadoRegistro.REINTENTAR) {
			dpaNew.setReintentar(TypeSiNo.SI);
		}

	}

	/**
	 * Regenera informacion del asiento.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pDpp
	 *                            Datos persistencia paso
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 * @return Documento Paso de Asiento
	 */
	private DocumentoPasoPersistencia regenerarAsiento(final DatosInternosPasoRegistrar pDipa,
			final DatosPersistenciaPaso pDpp, final VariablesFlujo pVariablesFlujo) {

		DocumentoPasoPersistencia docDppAsiento = pDpp.getDocumentoPasoPersistencia(ConstantesFlujo.ID_ASIENTO_REGISTRO,
				ConstantesNumero.N1);

		// Si no existe asiento lo creamos
		if (docDppAsiento == null) {
			docDppAsiento = DocumentoPasoPersistencia.createDocumentoPersistencia();
			docDppAsiento.setId(ConstantesFlujo.ID_ASIENTO_REGISTRO);
			docDppAsiento.setTipo(TypeDocumentoPersistencia.REGISTRO);
			docDppAsiento.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
			docDppAsiento.setInstancia(ConstantesNumero.N1);
			getDao().establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(),
					docDppAsiento);
			pDpp.getDocumentos().add(docDppAsiento);
		}

		return docDppAsiento;
	}

	/**
	 * Regenera informacion justificante.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pDpp
	 *                            Datos persistencia
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 * @return Documento Paso de Justificante
	 */
	private DocumentoPasoPersistencia regenerarJustificante(final DatosInternosPasoRegistrar pDipa,
			final DatosPersistenciaPaso pDpp, final VariablesFlujo pVariablesFlujo) {
		DocumentoPasoPersistencia docDppJustificante = pDpp
				.getDocumentoPasoPersistencia(ConstantesFlujo.ID_JUSTIFICANTE_REGISTRO, ConstantesNumero.N1);
		// Si no existe justificante lo creamos
		if (docDppJustificante == null) {
			docDppJustificante = DocumentoPasoPersistencia.createDocumentoPersistencia();
			docDppJustificante.setId(ConstantesFlujo.ID_JUSTIFICANTE_REGISTRO);
			docDppJustificante.setTipo(TypeDocumentoPersistencia.JUSTIFICANTE);
			docDppJustificante.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
			docDppJustificante.setInstancia(ConstantesNumero.N1);
			getDao().establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(),
					docDppJustificante);
			pDpp.getDocumentos().add(docDppJustificante);
		}

		return docDppJustificante;
	}

	/**
	 * Método para Calcular detalle de la clase ControladorPasoRegistrarImpl.
	 *
	 * @param pIdPaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param pParamRegistro
	 *                               Parámetros registro
	 * @return el detalle paso registrar
	 */
	private DetallePasoRegistrar calcularDetalle(final String pIdPaso, final DefinicionTramiteSTG pDefinicionTramite,
			final VariablesFlujo pVariablesFlujo, final ParametrosRegistro pParamRegistro) {

		// Obtenemos definicion paso
		final RPasoTramitacionRegistrar defPaso = (RPasoTramitacionRegistrar) UtilsSTG.devuelveDefinicionPaso(pIdPaso,
				pDefinicionTramite);

		// Calculamos lista de documentos a registrar
		final List<DocumentosRegistroPorTipo> docsRegPorTipo = UtilsFlujo.buscarDocumentosParaRegistrar(getDao(),
				pVariablesFlujo);

		// Calculamos aviso finalizar
		final AvisoUsuario avisoFinalizar = ControladorPasoRegistrarHelper.getInstance()
				.ejecutarScriptAvisoFinalizar(pIdPaso, pDefinicionTramite, pVariablesFlujo, getScriptFlujo());

		// Creamos detalle paso
		final DetallePasoRegistrar dpr = new DetallePasoRegistrar();
		dpr.setCompletado(TypeSiNo.NO);
		dpr.setId(pIdPaso);
		dpr.setInstruccionesEntregaPresencial(defPaso.getInstruccionesPresentacionHtml());
		dpr.setFormularios(UtilsFlujo.obtenerDocumentosTipo(docsRegPorTipo, TypeDocumento.FORMULARIO));
		dpr.setAnexos(UtilsFlujo.obtenerDocumentosTipo(docsRegPorTipo, TypeDocumento.ANEXO));
		dpr.setPagos(UtilsFlujo.obtenerDocumentosTipo(docsRegPorTipo, TypeDocumento.PAGO));
		dpr.setPresentador(UtilsFlujo.usuarioPersona(pParamRegistro.getDatosPresentacion().getPresentador()));
		dpr.setRepresentado(UtilsFlujo.usuarioPersona(pParamRegistro.getDatosRepresentacion().getRepresentado()));
		dpr.setRegistrar(TypeSiNo.fromBoolean(dpr.verificarFirmas()));
		dpr.setAvisoFinalizar(avisoFinalizar);
		return dpr;
	}

	/**
	 * Calcula parámetros del paso de registro.
	 *
	 * @param pIdpaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pVariablesFlujo
	 *                               Variables de flujo
	 * @return Parámetros calculados para paso registro
	 */
	private ParametrosRegistro calculoParametrosRegistro(final String pIdpaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		// Calculamos registro destino
		final DatosRegistrales datosRegistrales = calcularDatosRegistrales(pIdpaso, pDefinicionTramite,
				pVariablesFlujo);
		// Calculamos parametros presentacion
		final DatosPresentacion datosPresentacion = calcularDatosPresentacion(pIdpaso, pDefinicionTramite,
				pVariablesFlujo);
		// Calculamos parametros representacion
		final DatosRepresentacion datosRepresentacion = calcularDatosRepresentacion(pIdpaso, pDefinicionTramite,
				pVariablesFlujo);
		// Devolvemos parametros registro
		final ParametrosRegistro res = new ParametrosRegistro();
		res.setDatosRegistrales(datosRegistrales);
		res.setDatosPresentacion(datosPresentacion);
		res.setDatosRepresentacion(datosRepresentacion);
		return res;
	}

	/**
	 * Calcula parametros de representacion.
	 *
	 * @param pIdpaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @return Parametros representacion
	 */
	private DatosRepresentacion calcularDatosRepresentacion(final String pIdpaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		// Ejecutamos script calculo representado
		final DatosUsuario representado = ControladorPasoRegistrarHelper.getInstance()
				.ejecutarScriptRepresentado(pIdpaso, pDefinicionTramite, pVariablesFlujo, getScriptFlujo());

		final DatosRepresentacion dr = new DatosRepresentacion();
		dr.setRepresentado(representado);
		return dr;
	}

	/**
	 * Calcula parametros de presentacion.
	 *
	 * @param pIdpaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @return Parametros presentacion
	 */
	private DatosPresentacion calcularDatosPresentacion(final String pIdpaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		// Calculamos presentador
		final DatosUsuario presentador = ControladorPasoRegistrarHelper.getInstance().ejecutarScriptPresentador(pIdpaso,
				pDefinicionTramite, pVariablesFlujo, getScriptFlujo());

		final DatosPresentacion datosPresentacion = new DatosPresentacion();
		datosPresentacion.setPresentador(presentador);
		return datosPresentacion;
	}

	/**
	 * Cálcula destino del trámite teniendo en cuenta el script de parámetros
	 * dinámicos.
	 *
	 * @param pIdPaso
	 *                               Parámetro id paso
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @return datos registrales
	 */
	private DatosRegistrales calcularDatosRegistrales(final String pIdPaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		final RPasoTramitacionRegistrar pasoRegistrar = (RPasoTramitacionRegistrar) UtilsSTG
				.devuelveDefinicionPaso(pIdPaso, pDefinicionTramite);

		// Verificamos si se establece datos centralizados a nivel de entidad
		final RConfiguracionEntidad entidadInfo = getConfig()
				.obtenerConfiguracionEntidad(pDefinicionTramite.getDefinicionVersion().getIdEntidad());

		// Evaluamos script de parametros dinamicos si existe
		final ResRegistro resRegistro = ControladorPasoRegistrarHelper.getInstance()
				.ejecutarScriptParametrosRegistro(pIdPaso, pDefinicionTramite, pVariablesFlujo, getScriptFlujo());

		// Establecemos datos registro destino
		final DatosRegistrales datosRegistrales = new DatosRegistrales();

		// - Organo destino
		if (StringUtils.isNotBlank(resRegistro.getCodigoOrganoDestino())) {
			datosRegistrales.setCodigoOrganoDestino(resRegistro.getCodigoOrganoDestino());
		} else {
			datosRegistrales.setCodigoOrganoDestino(pVariablesFlujo.getDatosTramiteCP().getOrganoDestinoDir3());
		}

		// - Oficina registro
		if (StringUtils.isNotBlank(resRegistro.getOficina())) {
			// Oficina viene por script
			datosRegistrales.setOficina(resRegistro.getOficina());
		} else {
			// Verificamos si oficina se fija por entidad o a nivel de versión de trámite
			if (entidadInfo.isRegistroCentralizado()) {
				datosRegistrales.setOficina(entidadInfo.getOficinaRegistroCentralizado());
			} else {
				datosRegistrales.setOficina(pasoRegistrar.getDestino().getOficinaRegistro());
			}
		}

		// - Libro
		if (StringUtils.isNotBlank(resRegistro.getLibro())) {
			// Libro se establece por script
			datosRegistrales.setLibro(resRegistro.getLibro());
		} else {
			// Verificamos si libro se calcula automáticamente o a nivel de versión de
			// trámite
			if (entidadInfo.isRegistroCentralizado()) {
				final String libro = registroComponent.obtenerLibroOrganismo(entidadInfo.getIdentificador(),
						datosRegistrales.getCodigoOrganoDestino(), pVariablesFlujo.isDebugEnabled());
				datosRegistrales.setLibro(libro);
			} else {
				datosRegistrales.setLibro(pasoRegistrar.getDestino().getLibroRegistro());
			}
		}

		// - Tipo asunto
		if (StringUtils.isNotBlank(resRegistro.getTipoAsunto())) {
			datosRegistrales.setTipoAsunto(resRegistro.getTipoAsunto());
		} else {
			// TODO PENDIENTE VER SI TIPO ASUNTO SE CENTRALIZA
			datosRegistrales.setTipoAsunto(pasoRegistrar.getDestino().getTipoAsunto());
		}

		// - N. expediente
		datosRegistrales.setNumeroExpediente(resRegistro.getNumeroExpediente());

		// - Expone / Solicita
		datosRegistrales.setTextoExpone(resRegistro.getExpone());
		datosRegistrales.setTextoSolicita(resRegistro.getSolicita());

		// - Extracto
		if (StringUtils.isNotBlank(resRegistro.getExtracto())) {
			datosRegistrales.setExtracto(resRegistro.getExtracto());
		}

		return datosRegistrales;
	}

	/**
	 * Obtiene marcadores estado.
	 *
	 * @param pDipa
	 *                  Datos internos paso registrar
	 * @return Marcadores estado
	 */
	private EstadoMarcadores obtenerMarcadoresEstado(final DatosInternosPasoRegistrar pDipa) {
		final EstadoMarcadores marcadores = new EstadoMarcadores();
		// Comprobamos si aun no se ha registrado
		if (pDipa.getResultadoRegistro() == null) {
			// En caso contrario, pasa al asistente
			marcadores.addMarcador("pendienteAsistente", true);
		}
		return marcadores;
	}

	/**
	 * Obtiene datos documento justificante.
	 *
	 * @param pDipa
	 *                  Datos interno paso
	 * @return datos documento justificante
	 */
	private DatosDocumentoJustificante obtenerDatosDocumentoJustificante(final DatosInternosPasoRegistrar pDipa) {
		final DatosDocumentoJustificante ddj = new DatosDocumentoJustificante();
		ddj.setIdPaso(pDipa.getIdPaso());
		ddj.setId(ConstantesFlujo.ID_JUSTIFICANTE_REGISTRO);
		ddj.setTitulo(pDipa.getResultadoRegistro().getAsunto());
		ddj.setSolicitante(new Persona(pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador().getNif(),
				pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador().getNombreApellidos()));
		ddj.setAsunto(pDipa.getResultadoRegistro().getAsunto());
		ddj.setNumeroRegistro(pDipa.getResultadoRegistro().getNumeroRegistro());
		ddj.setFechaRegistro(pDipa.getResultadoRegistro().getFechaRegistro());
		if (pDipa.getResultadoRegistro().isPreregistro()) {
			ddj.setPreregistro(TypeSiNo.SI);
		}

		// TODO Ver cuando fichero justificante es necesario ¿prereregistro?
		// ddj.setFichero(pDipa.getResultadoRegistro().getReferenciaJustificante());

		return ddj;
	}

}
