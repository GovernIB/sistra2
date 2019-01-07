package es.caib.sistramit.core.service.component.flujo.pasos.guardar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistramit.core.api.exception.AccionPasoNoExisteException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosGuardarJustificante;
import es.caib.sistramit.core.api.model.flujo.DetallePasoGuardar;
import es.caib.sistramit.core.api.model.flujo.DocumentosRegistroPorTipo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoGuardar;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
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

	/** Accion descargar justificante. */
	@Autowired
	private AccionDescargarJustificante accionDescargarJustificante;

	@Override
	protected void actualizarDatosInternos(DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp,
			DefinicionTramiteSTG pDefinicionTramite, VariablesFlujo pVariablesFlujo,
			TypeFaseActualizacionDatosInternos pFaseEjecucion) {

		// Obtenemos datos internos del paso
		final DatosInternosPasoGuardar dipa = (DatosInternosPasoGuardar) pDatosPaso.internalData();

		// Creamos detalle del paso visualizable en front a partir de la definición
		regenerarDatosGuardar(dipa, pDpp, pDefinicionTramite, pVariablesFlujo);

	}

	@Override
	protected EstadoSubestadoPaso evaluarEstadoPaso(DatosPaso pDatosPaso) {
		return new EstadoSubestadoPaso(TypeEstadoPaso.COMPLETADO, null);
	}

	@Override
	protected List<DatosDocumento> obtenerDocumentosCompletadosPaso(DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp,
			DefinicionTramiteSTG pDefinicionTramite) {
		// Este paso no tiene documentos
		return null;
	}

	@Override
	protected RespuestaEjecutarAccionPaso ejecutarAccionPaso(DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp,
			TypeAccionPaso pAccionPaso, ParametrosAccionPaso pParametros, DefinicionTramiteSTG pDefinicionTramite,
			VariablesFlujo pVariablesFlujo) {
		// Ejecutamos accion
		AccionPaso accionPaso = null;
		switch ((TypeAccionPasoGuardar) pAccionPaso) {
		case DESCARGAR_DOCUMENTO:
			accionPaso = accionDescargarDocumento;
			break;
		case DESCARGAR_JUSTIFICANTE:
			accionPaso = accionDescargarJustificante;
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
	private void regenerarDatosGuardar(final DatosInternosPasoGuardar pDipa, DatosPersistenciaPaso pDpp,
			DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

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
	private DetallePasoGuardar calcularDetalle(final String idPaso, DefinicionTramiteSTG pDefinicionTramite,
			final VariablesFlujo pVariablesFlujo) {

		// Buscamos documento de justificante generado en paso registrar
		final DatosDocumentoJustificante ddj = (DatosDocumentoJustificante) pVariablesFlujo
				.getDocumento(ConstantesFlujo.ID_JUSTIFICANTE_REGISTRO, ConstantesNumero.N1);

		// Definición paso registrar
		final RPasoTramitacionRegistrar defPasoRegistrar = UtilsSTG.devuelveDefinicionPasoRegistrar(pDefinicionTramite);

		// Documentos registro
		final List<DocumentosRegistroPorTipo> docsRegPorTipo = UtilsFlujo.buscarDocumentosParaRegistrar(getDao(),
				pVariablesFlujo);

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

		// Generamos detalle paso
		final DetallePasoGuardar dpds = new DetallePasoGuardar();
		dpds.setId(idPaso);
		dpds.setCompletado(TypeSiNo.SI);
		dpds.setInstruccionesPresentacion(defPasoRegistrar.getInstruccionesPresentacionHtml());
		dpds.setInstruccionesTramitacion(defPasoRegistrar.getInstruccionesTramitacionHtml());
		dpds.setJustificante(justificante);
		return dpds;
	}

}
