package es.caib.sistramit.core.service.component.flujo.pasos.pagar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.Pago;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.PagoComponent;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoPagar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que permite generar carta de pago presencial en el paso Pagar.
 *
 * @author Indra
 *
 */
@Component("accionPtCartaPagoPresencial")
public final class AccionCartaPagoPresencial implements AccionPaso {

	/** Atributo dao. */
	@Autowired
	private FlujoPasoDao dao;

	/** Atributo pago component. */
	@Autowired
	private PagoComponent pagoExternoComponent;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Recogemos parametros
		final String idPago = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "idPago", true);

		// Obtenemos datos internos del paso
		final DatosInternosPasoPagar pDipa = (DatosInternosPasoPagar) pDatosPaso.internalData();

		// Obtenemos info de detalle para el pago
		final Pago pago = ((DetallePasoPagar) pDipa.getDetallePaso()).getPago(idPago);

		// Validaciones previas a iniciar pago
		validacionesPago(pDipa, pago);

		// Generar carta de pago
		final DatosSesionPago sesionPago = ControladorPasoPagarHelper.getInstance().crearSesionPagoPasarela(
				TypePresentacion.PRESENCIAL, pVariablesFlujo.getIdSesionTramitacion(), pDipa, idPago,
				pVariablesFlujo.getIdioma(), pDefinicionTramite);
		final byte[] cartaPago = pagoExternoComponent.obtenerCartaPagoPresencial(sesionPago,
				UtilsSTG.isDebugEnabled(pDefinicionTramite));

		// Marcamos pago como realizado
		// - Actualizamos detalle
		actualizarDetallePago(pDipa, idPago, pVariablesFlujo);
		// - Actualizamos persistencia
		actualizarPersistencia(pDipa, pDpp, idPago, sesionPago, pVariablesFlujo.getIdSesionTramitacion(), cartaPago);

		// Cacheamos datos de sesion de pago en datos internos paso
		pDipa.addSesionPago(idPago, sesionPago);

		// Devolvemos respuesta (no se devuelve nada, el pdf se descarga con la
		// accion de descargar justificante)
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rp.addParametroRetorno("sesionPago", sesionPago);
		rep.setRespuestaAccionPaso(rp);
		return rep;
	}

	/**
	 * Realiza validaciones previas al pago.
	 *
	 * @param pDipa
	 *                  Datos interno paso pago
	 * @param pPago
	 *                  Datos pago
	 */
	private void validacionesPago(final DatosInternosPasoPagar pDipa, final Pago pPago) {

		// - El pago debe estar en estado pendiente de realizar
		if (pPago.getRellenado() != TypeEstadoDocumento.SIN_RELLENAR) {
			throw new AccionPasoNoPermitidaException("El pago ya ha sido iniciado");
		}

		// - Verificamos si se permite presentacion electronica
		if (!pPago.getPresentacionesPermitidas().contains(TypePresentacion.PRESENCIAL)) {
			throw new ErrorConfiguracionException(
					"El pago se realizado mediante una forma de presentación que no está permitida");
		}

	}

	/**
	 * Actualiza detalle de pago.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param idPago
	 *                            idPago
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 */
	private void actualizarDetallePago(final DatosInternosPasoPagar pDipa, final String idPago,
			final VariablesFlujo pVariablesFlujo) {
		// - Marco este documento como rellenado correctamente
		final DetallePasoPagar detallePasoPagar = (DetallePasoPagar) pDipa.getDetallePaso();
		final Pago detallePago = detallePasoPagar.getPago(idPago);
		detallePago.setRellenado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
		detallePago.setPresentacion(TypePresentacion.PRESENCIAL);
		detallePago.setEstadoIncorrecto(null);
	}

	/**
	 * Actualiza persistencia.
	 *
	 * @param pDipa
	 *                                 Datos internos paso
	 * @param pDpp
	 *                                 Datos persistencia
	 * @param pSesionPago
	 *                                 Sesion pago
	 * @param pIdSesionTramitacion
	 *                                 Id sesion tramitacion
	 * @param cartaPago
	 *                                 carta pago
	 */
	private void actualizarPersistencia(final DatosInternosPasoPagar pDipa, final DatosPersistenciaPaso pDpp,
			final String idPago, final DatosSesionPago pSesionPago, final String pIdSesionTramitacion,
			final byte[] cartaPago) {
		final DocumentoPasoPersistencia documentoPersistencia = pDpp.getDocumentoPasoPersistencia(idPago,
				ConstantesNumero.N1);
		// - Marcamos este documento como pagado
		documentoPersistencia.setEstado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
		documentoPersistencia.setPagoEstadoIncorrecto(null);
		documentoPersistencia.setPagoErrorPasarela(null);
		documentoPersistencia.setPagoMensajeErrorPasarela(null);
		documentoPersistencia.setPagoNifSujetoPasivo(pSesionPago.getSujetoPasivo().getNif());
		documentoPersistencia.setPagoIdentificador(pSesionPago.getIdentificadorPago());

		// - Marcamos para borrar los ficheros anteriores
		final List<ReferenciaFichero> ficsPersistenciaBorrar = new ArrayList<>();
		ficsPersistenciaBorrar.addAll(documentoPersistencia.obtenerReferenciasFicherosPago());
		// - Insertamos nuevos ficheros: xml y pdf carta pago
		final byte[] xmlPago = ControladorPasoPagarHelper.getInstance().toXML(pSesionPago);
		final ReferenciaFichero referenciaDocumentoXML = dao.insertarFicheroPersistencia(idPago + ".xml", xmlPago,
				pIdSesionTramitacion);
		final ReferenciaFichero referenciaCartaPago = dao.insertarFicheroPersistencia(idPago + ".pdf", cartaPago,
				pIdSesionTramitacion);

		// - Actualizamos en persistencia el documento
		documentoPersistencia.setFichero(referenciaDocumentoXML);
		documentoPersistencia.setPagoJustificantePdf(referenciaCartaPago);
		dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), documentoPersistencia);

		// - Eliminamos ficheros anteriores
		for (final ReferenciaFichero refFic : ficsPersistenciaBorrar) {
			dao.eliminarFicheroPersistencia(refFic);
		}
	}

}
