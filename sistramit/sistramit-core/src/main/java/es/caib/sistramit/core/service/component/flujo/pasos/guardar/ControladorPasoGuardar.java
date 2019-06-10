package es.caib.sistramit.core.service.component.flujo.pasos.guardar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RIncidenciaValoracion;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistramit.core.api.exception.AccionPasoNoExisteException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosGuardarJustificante;
import es.caib.sistramit.core.api.model.flujo.DetallePasoGuardar;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.DocumentosRegistroPorTipo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ValoracionIncidencia;
import es.caib.sistramit.core.api.model.flujo.ValoracionTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoGuardar;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoJustificante;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoGuardar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Controlador para paso Guardar.
 *
 * @author Indra
 *
 */
@Component("controladorPasoGuardar")
public final class ControladorPasoGuardar extends ControladorPasoReferenciaImpl {

	/** Accion descargar documento. */
	@Autowired
	private AccionDescargarDocumento accionDescargarDocumento;

	/** Accion descargar firma documento. */
	@Autowired
	private AccionDescargarFirma accionDescargarFirma;

	/** Accion descargar justificante. */
	@Autowired
	private AccionDescargarJustificante accionDescargarJustificante;

	/** Accion valorar trámite. */
	@Autowired
	private AccionValorarTramite accionValorarTramite;

	/** Componente registro. */
	@Autowired
	private RegistroComponent registroComponent;

	@Override
	protected void actualizarDatosInternos(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final TypeFaseActualizacionDatosInternos pFaseEjecucion) {

		// Obtenemos datos internos del paso
		final DatosInternosPasoGuardar dipa = (DatosInternosPasoGuardar) pDatosPaso.internalData();

		// Creamos detalle del paso visualizable en front a partir de la definición
		regenerarDatosGuardar(dipa, pDpp, pDefinicionTramite, pVariablesFlujo);

	}

	@Override
	protected EstadoSubestadoPaso evaluarEstadoPaso(final DatosPaso pDatosPaso) {
		return new EstadoSubestadoPaso(TypeEstadoPaso.COMPLETADO, null);
	}

	@Override
	protected List<DatosDocumento> obtenerDocumentosCompletadosPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final DefinicionTramiteSTG pDefinicionTramite) {
		// Este paso no tiene documentos
		return null;
	}

	@Override
	protected RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		// Ejecutamos accion
		AccionPaso accionPaso = null;
		switch ((TypeAccionPasoGuardar) pAccionPaso) {
		case DESCARGAR_DOCUMENTO:
			accionPaso = accionDescargarDocumento;
			break;
		case DESCARGAR_FIRMA:
			accionPaso = accionDescargarFirma;
			break;
		case DESCARGAR_JUSTIFICANTE:
			accionPaso = accionDescargarJustificante;
			break;
		case VALORACION_TRAMITE:
			accionPaso = accionValorarTramite;
			break;
		default:
			throw new AccionPasoNoExisteException("No existe acción " + pAccionPaso + " en el paso guardar");
		}

		final RespuestaEjecutarAccionPaso rp = accionPaso.ejecutarAccionPaso(pDatosPaso, pDpp, pAccionPaso, pParametros,
				pDefinicionTramite, pVariablesFlujo);
		return rp;
	}

	/**
	 * Regenera datos paso.
	 *
	 * @param pDipa
	 *            Datos internos paso
	 * @param pDpp
	 *            Datos persistencia paso
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param pRequiereRegistro
	 *            indica si el trámite requiere registro
	 */
	private void regenerarDatosGuardar(final DatosInternosPasoGuardar pDipa, final DatosPersistenciaPaso pDpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Inicializamos detalle en base a la definicion
		final DetallePasoGuardar dpds = calcularDetalle(pDipa.getIdPaso(), pDefinicionTramite, pVariablesFlujo);
		pDipa.setDetallePaso(dpds);
		// Actualizamos datos internos
		// - Paso final
		pDipa.setPasoFinal(true);
	}

	/**
	 * Crea detalle del paso Guardar a partir de la definición del trámite.
	 *
	 * @param idPaso
	 *            Id paso
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @return Detalle del paso
	 */
	private DetallePasoGuardar calcularDetalle(final String idPaso, final DefinicionTramiteSTG pDefinicionTramite,
			final VariablesFlujo pVariablesFlujo) {

		// Obtiene entidad
		final RConfiguracionEntidad entidadInfo = getConfig()
				.obtenerConfiguracionEntidad(pDefinicionTramite.getDefinicionVersion().getIdEntidad());

		// Buscamos documento de justificante generado en paso registrar
		final DatosDocumentoJustificante ddj = (DatosDocumentoJustificante) pVariablesFlujo
				.getDocumento(ConstantesFlujo.ID_JUSTIFICANTE_REGISTRO, ConstantesNumero.N1);

		// Definición paso registrar
		final RPasoTramitacionRegistrar defPasoRegistrar = UtilsSTG.devuelveDefinicionPasoRegistrar(pDefinicionTramite);

		// Documentos registro
		final List<DocumentosRegistroPorTipo> docsRegPorTipo = UtilsFlujo.buscarDocumentosParaRegistrar(getDao(),
				pVariablesFlujo);
		// Revisamos descarga documentos firmados en función de tipo de firma y número
		// firmantes.
		revisarDescargaDocumentosFirmados(docsRegPorTipo);

		// Datos justificante
		final DatosGuardarJustificante justificante = new DatosGuardarJustificante();
		justificante.setAsunto(ddj.getAsunto());
		justificante.setFecha(UtilsFlujo.formateaFechaFront(ddj.getFechaRegistro()));
		justificante.setPreregistro(ddj.getPreregistro());
		justificante.setNumero(ddj.getNumeroRegistro());
		justificante.setSolicitante(ddj.getSolicitante());
		justificante.setFormularios(UtilsFlujo.obtenerDocumentosTipo(docsRegPorTipo, TypeDocumento.FORMULARIO));
		justificante.setAnexos(UtilsFlujo.obtenerDocumentosTipo(docsRegPorTipo, TypeDocumento.ANEXO));
		justificante.setPagos(UtilsFlujo.obtenerDocumentosTipo(docsRegPorTipo, TypeDocumento.PAGO));
		justificante.setDescarga(
				registroComponent.descargaJustificantes(pDefinicionTramite.getDefinicionVersion().getIdEntidad()));
		justificante.setMostrarDocumentos(TypeSiNo.fromBoolean(!entidadInfo.isRegistroOcultarDescargaDocumentos()));

		// Generamos detalle paso
		final DetallePasoGuardar dpds = new DetallePasoGuardar();
		dpds.setId(idPaso);
		dpds.setCompletado(TypeSiNo.SI);
		dpds.setInstruccionesPresentacion(defPasoRegistrar.getInstruccionesPresentacionHtml());
		dpds.setInstruccionesTramitacion(defPasoRegistrar.getInstruccionesTramitacionHtml());
		dpds.setJustificante(justificante);
		if (entidadInfo.isValorarTramite()) {
			final ValoracionTramite valoracionTramite = new ValoracionTramite();
			final List<ValoracionIncidencia> problemas = new ArrayList<>();
			for (final RIncidenciaValoracion inci : entidadInfo.getIncidenciasValoracion()) {
				final ValoracionIncidencia vi = new ValoracionIncidencia();
				vi.setId(inci.getIdentificador());
				vi.setDescripcion(UtilsSTG.obtenerLiteral(inci.getDescripcion(), pVariablesFlujo.getIdioma()));
				problemas.add(vi);
			}
			valoracionTramite.setProblemas(problemas);
			dpds.setValoracion(valoracionTramite);
		}
		return dpds;
	}

	/**
	 * Revisamos descarga documentos firmados en función de tipo de firma y número
	 * firmantes: Si la firma es PDF y hay un solo firmante, la descarga del
	 * documento será directamente la descarga de la firma. En otro caso, se
	 * descarga documento original y firmas por separado.
	 *
	 * @param docsRegPorTipo
	 *            Documentos registrados
	 */
	private void revisarDescargaDocumentosFirmados(final List<DocumentosRegistroPorTipo> docsRegPorTipo) {
		for (final DocumentosRegistroPorTipo drt : docsRegPorTipo) {
			for (final DocumentoRegistro dr : drt.getListado()) {
				if (dr.getFirmado() == TypeSiNo.SI) {
					if (dr.getFirmas().size() == ConstantesNumero.N1
							&& dr.getFirmas().get(0).getTipoFirma() == TypeFirmaDigital.PADES) {
						dr.getFirmas().get(0).setDescargable(TypeSiNo.NO);
					}
				}
			}
		}
	}

	/**
	 * Método de acceso a registroComponent.
	 *
	 * @return registroComponent
	 */
	public RegistroComponent getRegistroComponent() {
		return registroComponent;
	}

	/**
	 * Método para establecer registroComponent.
	 *
	 * @param registroComponent
	 *            registroComponent a establecer
	 */
	public void setRegistroComponent(final RegistroComponent registroComponent) {
		this.registroComponent = registroComponent;
	}

}
