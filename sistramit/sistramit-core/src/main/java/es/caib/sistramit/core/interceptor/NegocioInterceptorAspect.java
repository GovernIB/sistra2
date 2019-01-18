package es.caib.sistramit.core.interceptor;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.DatabaseException;
import es.caib.sistramit.core.api.exception.ErrorNoControladoException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.exception.ServiceRollbackException;
import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.api.service.FlujoFormularioInternoService;
import es.caib.sistramit.core.api.service.PurgaService;
import es.caib.sistramit.core.service.component.system.AuditorEventosFlujoTramitacion;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.model.system.EventoFlujoInfo;

/**
 * Lógica de logging de las excepciones de la capa de servicios.
 *
 * Las excepciones de la capa de datos serán descendientes de
 * org.springframework.dao.DataAccessException y se encapsularan en una
 * excepción DatabaseException. El resto serán descendientes de
 * ServiceNoRollbackException o ServiceRollbackException, excepto las runtime:
 * nullpointer, etc. que se encapsularan en una excepción
 * ErrorNoControladoException.
 *
 *
 * @author Indra
 */
@Aspect
@Order(ConstantesNumero.N1)
@Service
public final class NegocioInterceptorAspect {

	/**
	 * Log del interceptor.
	 */
	private final Logger log = LoggerFactory.getLogger(NegocioInterceptorAspect.class);

	/** Componente auditoria. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;

	/** Auditor eventos flujo tramitación. */
	@Autowired
	private AuditorEventosFlujoTramitacion auditorEventosFlujoTramitacion;

	/**
	 * Intercepta invocacion a metodo.
	 *
	 * @param jp
	 *            JointPoint
	 */
	@Before("@annotation(es.caib.sistramit.core.interceptor.NegocioInterceptor)")
	public void processInvocation(final JoinPoint jp) {

		// Obtiene info flujo
		final EventoFlujoInfo infoFlujo = getInfoFlujo(jp);

		// Audita eventos flujo tramitacion
		if (isFlujoTramitacionService(jp)) {
			final List<EventoAuditoria> eventos = auditorEventosFlujoTramitacion.interceptaInvocacion(
					infoFlujo.getIdSesionTramitacion(), jp.getSignature().getName(), jp.getArgs());
			auditoriaComponent.auditarEventosAplicacion(eventos);
		}

	}

	/**
	 * Intercepta retorno de un metodo.
	 *
	 * @param jp
	 *            JointPoint
	 * @param retVal
	 *            Objeto retornado
	 */
	@AfterReturning(pointcut = "@annotation(es.caib.sistramit.core.interceptor.NegocioInterceptor)", returning = "retVal")
	public void processReturn(final JoinPoint jp, final Object retVal) {
		// Obtiene info flujo
		final EventoFlujoInfo infoFlujo = getInfoFlujo(jp);

		// Audita eventos flujo tramitacion
		if (isFlujoTramitacionService(jp)) {
			final List<EventoAuditoria> eventos = auditorEventosFlujoTramitacion.interceptaRetorno(
					infoFlujo.getIdSesionTramitacion(), jp.getSignature().getName(), jp.getArgs(), retVal);
			auditoriaComponent.auditarEventosAplicacion(eventos);
		}

		// Audita eventos purga
		if (isPurgaService(jp)) {
			final ResultadoProcesoProgramado rp = (ResultadoProcesoProgramado) retVal;
			if (rp != null) {
				final EventoAuditoria ev = new EventoAuditoria();
				ev.setTipoEvento(TypeEvento.PROCESO_PURGA);
				ev.setFecha(new Date());
				ev.setResultado(Boolean.toString(rp.isFinalizadoOk()));
				ev.setPropiedadesEvento(rp.getDetalles());
				auditoriaComponent.auditarEventoAplicacion(ev);
			}
		}

	}

	/**
	 * Intercepta excepcion y realiza auditoria.
	 *
	 * @param jp
	 *            JoinPoint
	 * @param ex
	 *            Exception
	 */
	@AfterThrowing(pointcut = "@annotation(es.caib.sistramit.core.interceptor.NegocioInterceptor)", throwing = "ex")
	public void processException(final JoinPoint jp, final Throwable ex) {

		// Obtiene info flujo
		final EventoFlujoInfo infoFlujo = getInfoFlujo(jp);

		// Audita excepcion (recupera en caso necesario si se debe encapsular la
		// excepcion original)
		final ServiceRollbackException exNew = auditaExcepcion(jp, ex, infoFlujo);

		// Si es un flujo de tramitacion y se genera una excepcion FATAL
		// marcamos el flujo como invalido
		if (isFlujoTramitacionService(jp) && infoFlujo != null) {
			if ((exNew != null && exNew.getNivel() == TypeNivelExcepcion.FATAL)
					|| (exNew == null && ex instanceof ServiceException
							&& ((ServiceException) ex).getNivel() == TypeNivelExcepcion.FATAL)) {
				((FlujoTramitacionService) jp.getTarget())
						.invalidarFlujoTramitacion(infoFlujo.getIdSesionTramitacion());
			}
		}

		// Audita eventos flujo tramitacion
		Throwable exAud = ex;
		if (exNew != null) {
			exAud = exNew;
		}
		if (isFlujoTramitacionService(jp) && exAud instanceof ServiceException) {
			final List<EventoAuditoria> eventos = auditorEventosFlujoTramitacion.interceptaExcepcion(
					infoFlujo.getIdSesionTramitacion(), jp.getSignature().getName(), jp.getArgs(),
					(ServiceException) exAud);
			auditoriaComponent.auditarEventosAplicacion(eventos);
		}

		// Comprobamos si debemos encapsular la excepcion original
		if (exNew != null) {
			throw exNew;
		}

	}

	// ----------------------------------------------------------------------------------
	// METODOS UTILIDADES
	// ----------------------------------------------------------------------------------

	/**
	 * Audita excepcion e indica si se debe generar una nueva excepcion que
	 * encapsule la original (para las excepciones de BBDD y runtime exception
	 * genera una nueva excepcion de negocio).
	 *
	 * @param jp
	 *            JointPoint
	 * @param ex
	 *            Excepcion
	 * @param flujoInfo
	 *            info flujo
	 * @return En caso necesario se indica si se debe generar una nueva excepcion
	 *         que encapsule la original.
	 */
	private ServiceRollbackException auditaExcepcion(final JoinPoint jp, final Throwable ex,
			EventoFlujoInfo flujoInfo) {

		final Logger logJP = LoggerFactory.getLogger(jp.getTarget().getClass());

		ServiceRollbackException exNew;
		// Para excepciones de BBDD encapsulamos en una excepcion de servicio y
		// auditamos
		if (ex instanceof DataAccessException) {
			final DatabaseException exBD = new DatabaseException((DataAccessException) ex);
			if (flujoInfo.isDebugEnabled()) {
				logJP.error(exBD.getMessage(), ex);
			}
			auditoriaComponent.auditarExcepcionNegocio(flujoInfo.getIdSesionTramitacion(), exBD);
			exNew = exBD;
		} else if (ex instanceof ServiceException) {
			if (flujoInfo.isDebugEnabled()) {
				logJP.error(ex.getMessage(), ex);
			}
			auditoriaComponent.auditarExcepcionNegocio(flujoInfo.getIdSesionTramitacion(), (ServiceException) ex);
			exNew = null;
		} else if (ex instanceof RuntimeException) {
			// 1. Para excepciones no controladas que no sean excepciones de
			// servicio las encapsulamos en una excepcion de servicio
			final ErrorNoControladoException exNC = new ErrorNoControladoException(ex);
			if (flujoInfo.isDebugEnabled()) {
				logJP.error(exNC.getMessage(), ex);
			}
			auditoriaComponent.auditarExcepcionNegocio(flujoInfo.getIdSesionTramitacion(), exNC);
			exNew = exNC;
		} else {
			// Resto de excepciones (checked)
			// ESTE CASO NO DEBERIA SER POSIBLE YA QUE TODAS LAS EXCEPCIONES
			// CHECKED DEBERIAN SER DE NEGOCIO.
			// EN ESTE CASO SOLO AUDITAMOS POR FICHERO
			log.warn("---- SE HA PRODUCIDO EXCEPCION CHECKED QUE NO SE HA TRATADO COMO EXCEPCION DE NEGOGIO ------");
			logJP.error(ex.getMessage(), ex);
			exNew = null;
		}

		// Indicamos si se debe generar una nueva excepcion encapsulando la
		// original
		return exNew;
	}

	/**
	 * Obtiene info flujo.
	 *
	 * @param jp
	 *            Jointpoint
	 * @return info flujo
	 */
	private EventoFlujoInfo getInfoFlujo(final JoinPoint jp) {
		// Comprobamos si es una excepcion de un flujo de tramitacion o
		// formulario
		// interno y obtenemos idsesiontramitacion y debug

		final EventoFlujoInfo res = new EventoFlujoInfo();

		// - Flujo tramitacion
		if (isFlujoTramitacionService(jp)) {
			// Operaciones previas a estar creada la sesión. El resto tendrá
			// como primer parámetro la sesión.
			final String[] operacionesPrevias = { "iniciarTramite" };
			String idSesionTramitacion = null;
			if (!ArrayUtils.contains(operacionesPrevias, jp.getSignature().getName())) {
				idSesionTramitacion = (String) jp.getArgs()[0];
			}
			// Si existe id sesion, intentamos obtener detalle flujo
			if (idSesionTramitacion != null) {
				final FlujoTramitacionInfo infoFlujo = ((FlujoTramitacionService) jp.getTarget())
						.obtenerFlujoTramitacionInfo(idSesionTramitacion);
				if (infoFlujo != null) {
					res.setIdSesionTramitacion(idSesionTramitacion);
					res.setDebugEnabled(infoFlujo.isDebug());
				}
			}
		}
		// - Gestor formulario interno
		if (jp.getTarget() instanceof FlujoFormularioInternoService) {
			// Operaciones previas a estar creada la sesión. El resto tendrá
			// como primer parámetro la sesión.
			final String[] operacionesPrevias = { "cargarSesion" };
			String idSesionFormulario = null;
			if (!ArrayUtils.contains(operacionesPrevias, jp.getSignature().getName())) {
				idSesionFormulario = (String) jp.getArgs()[0];
			}
			// Si existe id sesion, intentamos obtener detalle sesion formulario
			if (idSesionFormulario != null) {
				final SesionFormularioInfo infoFlujo = ((FlujoFormularioInternoService) jp.getTarget())
						.obtenerInformacionFormulario(idSesionFormulario);
				if (infoFlujo != null) {
					res.setIdSesionTramitacion(infoFlujo.getIdSesionTramitacion());
					res.setDebugEnabled(infoFlujo.isDebugEnabled());
				}
			}
		}
		return res;
	}

	/**
	 * Verifica si llamada es al service de flujo de tramitación.
	 *
	 * @param jp
	 *            JointPoint
	 * @return boolean
	 */
	private boolean isFlujoTramitacionService(final JoinPoint jp) {
		return jp.getTarget() instanceof FlujoTramitacionService;
	}

	/**
	 * Verifica si llamada es al service de purga.
	 *
	 * @param jp
	 *            JointPoint
	 * @return boolean
	 */
	private boolean isPurgaService(final JoinPoint jp) {
		return jp.getTarget() instanceof PurgaService;
	}

}
