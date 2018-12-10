package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccesoNoPermitidoException;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.service.model.flujo.ResultadoRegistro;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeResultadoRegistro;
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
		rp.addParametroRetorno("resultado", resReg.getResultado());
		if (resReg.getResultado() == TypeResultadoRegistro.CORRECTO) {
			rp.addParametroRetorno("numeroRegistro", pDipa.getResultadoRegistro().getNumeroRegistro());
		}

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
			// TODO VERIFICAR SI DOCUMENTOS FIRMADOS??
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
			// TODO Pendiente
			final AsientoRegistral asiento = generarAsiento();
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
			((DetallePasoRegistrar) pDipa.getDetallePaso()).setReintentar(TypeSiNo.NO);
			break;
		case ERROR:
			// Registro realizado con error
			// - resetamos info de reintentar
			((DetallePasoRegistrar) pDipa.getDetallePaso()).setReintentar(TypeSiNo.NO);
			break;
		case REINTENTAR:
			// Se debe reintentar registro
			// - indicamos que hay que reintentar el pago
			((DetallePasoRegistrar) pDipa.getDetallePaso()).setReintentar(TypeSiNo.SI);
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
			docAsientoDpp.setRegistroResultado(resReg.getResultado());
			docAsientoDpp.setRegistroNumeroRegistro(resReg.getNumeroRegistro());
			docAsientoDpp.setRegistroFechaRegistro(resReg.getFechaRegistro());
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		case ERROR:
			// Error al registrar:
			// - updateamos estado documento asiento para resetear estado y
			// permitir nuevo registro
			docAsientoDpp.setRegistroResultado(null);
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		case REINTENTAR:
			// Hay que reintentar el proceso de registro
			docAsientoDpp.setRegistroResultado(TypeResultadoRegistro.REINTENTAR);
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		default:
			throw new TipoNoControladoException("Tipo de resultado registro no controlado: " + resReg.getResultado());
		}
	}

	private AsientoRegistral generarAsiento() {
		// TODO PENDIENTE
		throw new RuntimeException("Pendiente implementar");
	}

}
