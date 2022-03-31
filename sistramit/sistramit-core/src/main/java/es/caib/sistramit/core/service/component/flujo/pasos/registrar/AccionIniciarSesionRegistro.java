package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistramit.core.api.exception.AccesoNoPermitidoException;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que inicia sesión registro y pone estado a reintentar.
 *
 * @author Indra
 *
 */
@Component("accionRtIniciarSesionRegistro")
public final class AccionIniciarSesionRegistro implements AccionPaso {

	/** DAO acceso BBDD. */
	@Autowired
	private FlujoPasoDao dao;
	/** Componente de registro. */
	@Autowired
	private RegistroComponent registroComponent;
	/** Literales negocio. */
	@Autowired
	private Literales literales;
	/** Motor de ejecución de scritps. */
	@Autowired
	private ScriptExec scriptFlujo;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPasoObtenerAnexo, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Obtenemos datos internos del paso
		final DatosInternosPasoRegistrar pDipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();

		// Valida si se puede registrar el tramite
		validacionesRegistrar(pDipa, pDpp, pVariablesFlujo, pDefinicionTramite);

		// Iniciamos sesión registro
		final String idSesionRegistro = registroComponent.iniciarSesionRegistro(
				pDefinicionTramite.getDefinicionVersion().getIdEntidad(), pVariablesFlujo.isDebugEnabled());

		// Actualizamos persistencia
		actualizarPersistencia(pDipa, pDpp, idSesionRegistro);

		// Actualizamos detalle
		actualizarDetalleRegistrar(pDipa);

		// Devolvemos respuesta indicando id sesion registro
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		rp.addParametroRetorno("idSesionRegistro", idSesionRegistro);
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
	 * @param pReintentar
	 *                               Indica si se debe reintentar registro
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

		// No debe estar en estado reintentar
		if (detallePasoRegistrar.getReintentar() == TypeSiNo.SI) {
			throw new AccionPasoNoPermitidaException(" Registre està pendent de verificació (estat reintentar)");
		}

		// Validaciones registro

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
			throw new AccionPasoNoPermitidaException("El tràmite ha de ser registrat pel presentador ("
					+ pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador().getNif() + ")");
		}
		// Verificar si los documentos estan firmados
		if (!detallePasoRegistrar.verificarFirmas()) {
			throw new AccionPasoNoPermitidaException("No estan tots els documents signats");
		}

		// Verificamos si se permite registro
		final RPasoTramitacionRegistrar pasoRegistrar = (RPasoTramitacionRegistrar) UtilsSTG
				.devuelveDefinicionPaso(pDipa.getIdPaso(), pDefinicionTramite);

		// Validar representacion
		if (pasoRegistrar.isAdmiteRepresentacion() && pasoRegistrar.isValidaRepresentacion()) {
			// TODO PENDIENTE IMPLEMENTACION
			throw new RuntimeException("Validar representació pendent implementar");
		}

		// Script de permitir registrar
		if (UtilsSTG.existeScript(pasoRegistrar.getScriptValidar())) {
			final RespuestaScript rs = ControladorPasoRegistrarHelper.getInstance().ejecutarScriptPermitirRegistrar(
					pDipa.getIdPaso(), pDefinicionTramite, pVariablesFlujo, scriptFlujo);
		}

	}

	/**
	 * Actualizar detalle registrar.
	 *
	 * @param pDipa
	 *                  Datos internos paso
	 */
	private void actualizarDetalleRegistrar(final DatosInternosPasoRegistrar pDipa) {
		// Actualizamos detalle indicando que se debería reintentar registro
		final DetallePasoRegistrar detallePasoRegistrar = (DetallePasoRegistrar) pDipa.getDetallePaso();
		detallePasoRegistrar.setReintentar(TypeSiNo.SI);
	}

	/**
	 * Actualiza paso tras registrar.
	 *
	 * @param idSesionRegistro
	 *                             Id sesion registro
	 * @param pDipa
	 *                             Datos internos paso
	 * @param pDpp
	 *                             Datos persistencia
	 * @param resReg
	 *                             Resultado registro
	 */
	private void actualizarPersistencia(final DatosInternosPasoRegistrar pDipa, final DatosPersistenciaPaso pDpp,
			final String idSesionRegistro) {
		// Marcamos documento como incompleto, estado reintentar y apuntamos id sesion
		// registro
		final DocumentoPasoPersistencia docAsientoDpp = pDpp
				.getDocumentoPasoPersistencia(ConstantesFlujo.ID_ASIENTO_REGISTRO, ConstantesNumero.N1);
		docAsientoDpp.setEstado(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE);
		docAsientoDpp.setRegistroResultado(TypeResultadoRegistro.REINTENTAR);
		docAsientoDpp.setRegistroIdSesion(idSesionRegistro);
		dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
	}

}
