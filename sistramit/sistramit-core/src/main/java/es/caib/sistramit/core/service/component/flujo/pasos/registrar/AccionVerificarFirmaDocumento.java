package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.api.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.api.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeEstadoFirmado;
import es.caib.sistramit.core.api.exception.SesionFirmaClienteException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.Firma;
import es.caib.sistramit.core.api.model.flujo.FirmaVerificacion;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.FirmaDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.FirmaClienteRespuesta;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite finalizar proceso firma documento en el paso Registrar.
 *
 * @author Indra
 *
 */
@Component("accionRtVerificarFirmaDocumento")
public final class AccionVerificarFirmaDocumento implements AccionPaso {

	/** Configuración. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Atributo dao de AccionObtenerDatosAnexo. */
	@Autowired
	private FlujoPasoDao dao;

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AccionVerificarFirmaDocumento.class);

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPasoObtenerAnexo, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Recogemos parametros
		final String idDocumento = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "idDocumento", true);
		final String instanciaStr = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "instancia", false);
		final String nifFirmante = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "firmante", false);
		final int instancia = UtilsFlujo.instanciaStrToInt(instanciaStr);

		// Datos internos paso
		final DatosInternosPasoRegistrar dipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();

		// Validaciones previas
		UtilsPasoRegistrar.getInstance().validacionesFirmaDocumento(pDatosPaso, pVariablesFlujo, idDocumento, instancia,
				nifFirmante);

		// Recuperamos firma
		final FirmaClienteRespuesta resFirma = recuperarFirma(dipa, pDefinicionTramite, idDocumento, instancia);

		// Según resultado validacion actualizamos estado paso y persistencia
		// - Actualizamos detalle
		actualizarDetalle(dipa, idDocumento, instancia, nifFirmante, resFirma, pVariablesFlujo);
		// - Actualizamos persistencia
		actualizarPersistencia(dipa, idDocumento, instancia, nifFirmante, resFirma, pVariablesFlujo);

		// Devolvemos respuesta
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		final FirmaVerificacion fv = new FirmaVerificacion();
		fv.setRealizada(TypeSiNo.fromBoolean(resFirma.isFinalizada()));
		fv.setVerificada(TypeSiNo.fromBoolean(resFirma.isValida()));
		fv.setDetalleError(resFirma.getDetalleError());
		rp.addParametroRetorno("resultado", fv);
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;

	}

	/**
	 * Actualiza persistencia.
	 *
	 * @param dipa
	 *            Datos internos paso
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @param nifFirmante
	 *            nif firmante
	 * @param resFirma
	 *            resultado firma
	 * @param pVariablesFlujo
	 *            variables flujo
	 */
	private void actualizarPersistencia(DatosInternosPasoRegistrar dipa, String idDocumento, int instancia,
			String nifFirmante, FirmaClienteRespuesta resFirma, VariablesFlujo pVariablesFlujo) {

		// Si la firma es correcta, almacenamos firma
		if (resFirma.isFinalizada() && resFirma.isValida()) {

			// Obtiene datos firmante
			final Persona firmante = UtilsPasoRegistrar.getInstance().obtieneDatosFirmante(pVariablesFlujo, idDocumento,
					instancia, nifFirmante);

			// Obtiene referencia fichero a firmar
			final ReferenciaFichero refFicheroFirmar = UtilsPasoRegistrar.getInstance()
					.obtenerReferenciaFicheroFirmar(pVariablesFlujo, idDocumento, instancia);

			// Obtenemos documento persistencia
			final DatosDocumento dd = pVariablesFlujo.getDocumento(idDocumento, instancia);
			final DocumentoPasoPersistencia dpp = dao.obtenerDocumentoPersistencia(
					pVariablesFlujo.getIdSesionTramitacion(), dd.getIdPaso(), idDocumento, instancia);

			// Insertamos fichero de firma
			final String nombreFicheroFirma = "FIRMA-" + idDocumento + "-" + instancia + "-" + nifFirmante + "."
					+ UtilsPasoRegistrar.getInstance().getExtensionFirma(resFirma.getFirmaTipo());
			final ReferenciaFichero rfp = dao.insertarFicheroPersistencia(nombreFicheroFirma,
					resFirma.getFirmaContenido(), pVariablesFlujo.getIdSesionTramitacion());

			// Actualizamos documento persistencia asociando la firma
			final FirmaDocumentoPersistencia fdp = new FirmaDocumentoPersistencia();
			fdp.setFirma(rfp);
			fdp.setNif(firmante.getNif());
			fdp.setNombre(firmante.getNombre());
			fdp.setFecha(resFirma.getFecha());
			fdp.setFichero(refFicheroFirmar);
			fdp.setTipoFirma(resFirma.getFirmaTipo());
			dpp.addFirma(fdp);
			dao.establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), dd.getIdPaso(), dpp);
		}

	}

	/**
	 * Actualiza detalle paso.
	 *
	 * @param dipa
	 *            Datos internos paso
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @param nifFirmante
	 *            nif firmante
	 * @param resFirma
	 *            resultado firma
	 * @param pVariablesFlujo
	 *            variables flujo
	 */
	private void actualizarDetalle(DatosInternosPasoRegistrar dipa, String idDocumento, int instancia,
			String nifFirmante, FirmaClienteRespuesta resFirma, VariablesFlujo pVariablesFlujo) {
		// Actualiza info firma
		final DetallePasoRegistrar dpr = (DetallePasoRegistrar) dipa.getDetallePaso();
		final DocumentoRegistro dr = dpr.buscarDocumentoRegistro(idDocumento, instancia);
		final Firma f = dr.getFirma(nifFirmante);
		if (resFirma.isFinalizada() && resFirma.isValida()) {
			f.setEstadoFirma(TypeEstadoFirma.FIRMADO);
			f.setFechaFirma(UtilsFlujo.formateaFechaFront(resFirma.getFecha()));
		} else {
			f.setEstadoFirma(TypeEstadoFirma.NO_FIRMADO);
			f.setFechaFirma(null);
		}
		// Si estan todos los documentos firmados permitimos registrar
		dpr.setRegistrar(TypeSiNo.fromBoolean(dpr.verificarFirmas()));
	}

	/**
	 * Recupera firma del componente de firma cliente.
	 *
	 * @param dipa
	 *            Datos internos paso
	 * @param pDefinicionTramite
	 *            Definción trámite
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @return Respuesta firma cliente
	 */
	private FirmaClienteRespuesta recuperarFirma(final DatosInternosPasoRegistrar dipa,
			final DefinicionTramiteSTG pDefinicionTramite, final String idDocumento, final int instancia) {

		final FirmaClienteRespuesta resFirma = new FirmaClienteRespuesta();
		resFirma.setFecha(new Date());

		// Recupera sesion de firma asociada al documento
		final String sf = dipa.recuperarSesionFirma(idDocumento, instancia);
		if (sf == null) {
			throw new SesionFirmaClienteException("No existe sesion de firma almacenada en datos paso");
		}

		// Instancia plugin
		final IFirmaPlugin plgFirma = (IFirmaPlugin) configuracionComponent.obtenerPluginEntidad(
				TypePluginEntidad.FIRMA, pDefinicionTramite.getDefinicionVersion().getIdEntidad());

		// Verifica estado sesion firma y recupera fichero firmado
		try {
			final TypeEstadoFirmado estado = plgFirma.obtenerEstadoSesionFirma(sf);
			if (estado == null) {
				throw new SesionFirmaClienteException("No se ha recuperado estado firma");
			}
			if (estado == TypeEstadoFirmado.FINALIZADO_OK) {
				// Recoge firma
				final FicheroFirmado fic = plgFirma.obtenerFirmaFichero(sf, idDocumento + "-" + instancia);
				final TypeFirmaDigital tipoFirma = TypeFirmaDigital.fromString(fic.getFirmaTipo().toString());
				if (tipoFirma == null) {
					throw new SesionFirmaClienteException("Tipo firma no reconocida: " + fic.getFirmaTipo().toString());
				}
				// Establece datos firma
				resFirma.setFinalizada(true);
				resFirma.setFirmaContenido(fic.getFirmaFichero());
				resFirma.setFirmaTipo(tipoFirma);
			} else if (estado == TypeEstadoFirmado.CANCELADO) {
				resFirma.setCancelada(true);
			}
		} catch (final FirmaPluginException ex) {
			throw new SesionFirmaClienteException("Error accediendo a plugin firma cliente: " + ex.getMessage(), ex);
		}

		// Cerramos sesion firma (no gestionamos excepcion)
		try {
			plgFirma.cerrarSesionFirma(sf);
		} catch (final FirmaPluginException ex) {
			LOGGER.warn("Error cerrando sesion de firma cliente: " + ex.getMessage(), ex);
		}

		// Validamos firma y verificar nif firmante concuerda
		if (resFirma.isFinalizada()) {
			// TODO PENDIENTE PLUGIN VALIDACION FIRMA
			resFirma.setValida(true);
		}

		// Devuelve respuesta
		return resFirma;
	}

}
