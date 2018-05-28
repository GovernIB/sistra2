package es.caib.sistramit.core.interceptor;

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

import es.caib.sistramit.core.api.exception.DatabaseException;
import es.caib.sistramit.core.api.exception.ErrorNoControladoException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.exception.ServiceRollbackException;
import es.caib.sistramit.core.api.model.comun.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.api.service.GestorFormulariosInternoService;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;

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

	@Autowired
	private AuditoriaComponent auditoriaComponent;

	/**
	 * Intercepta invocacion a metodo.
	 *
	 * @param jp
	 *            JointPoint
	 */
	@Before("@annotation(es.caib.sistramit.core.interceptor.NegocioInterceptor)")
	public void processInvocation(final JoinPoint jp) {
		// TODO PENDIENTE AUDITAR EVENTOS APLICACION
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
		// TODO PENDIENTE AUDITAR EVENTOS APLICACION
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

		// Comprobamos si es una excepcion de un flujo de tramitacion o formulario
		// interno y obtenemos idsesiontramitacion y debug
		String idSesionTramitacion = null;
		boolean debug = true;
		// - Flujo tramitacion
		if (jp.getTarget() instanceof FlujoTramitacionService) {
			// Operaciones previas a estar creada la sesión. El resto tendrá como primer
			// parámetro la sesión.
			final String[] operacionesPrevias = { "iniciarTramite" };
			if (!ArrayUtils.contains(operacionesPrevias, jp.getSignature().getName())) {
				idSesionTramitacion = (String) jp.getArgs()[0];
			}
			// Si existe id sesion, intentamos obtener detalle flujo
			if (idSesionTramitacion != null) {
				final DetalleTramite infoFlujo = ((FlujoTramitacionService) jp.getTarget())
						.obtenerFlujoTramitacionInfo(idSesionTramitacion);
				if (infoFlujo != null) {
					debug = infoFlujo.isDebug();
				} else {
					idSesionTramitacion = null;
				}
			}
		}
		// - Gestor formulario interno
		if (jp.getTarget() instanceof GestorFormulariosInternoService) {
			// TODO PENDIENTE
		}

		// Audita excepcion (recupera en caso necesario si se debe encapsular la
		// excepcion original)
		final ServiceRollbackException exNew = auditaExcepcion(jp, ex, idSesionTramitacion, debug);

		// Si es un flujo de tramitacion y se genera una excepcion FATAL
		// marcamos el flujo como invalido
		if (jp.getTarget() instanceof FlujoTramitacionService && idSesionTramitacion != null) {
			if ((exNew != null && exNew.getNivel() == TypeNivelExcepcion.FATAL)
					|| (exNew == null && ex instanceof ServiceException
							&& ((ServiceException) ex).getNivel() == TypeNivelExcepcion.FATAL)) {
				((FlujoTramitacionService) jp.getTarget()).invalidarFlujoTramitacion(idSesionTramitacion);
			}
		}

		// TODO PENDIENTE AUDITAR EVENTOS APLICACION

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
	 * @param debug
	 *            Debug
	 * @param idSesionTramitacion
	 *            idSesionTramitacion
	 * @return En caso necesario se indica si se debe generar una nueva excepcion
	 *         que encapsule la original.
	 */
	private ServiceRollbackException auditaExcepcion(final JoinPoint jp, final Throwable ex,
			final String idSesionTramitacion, final boolean debug) {

		final Logger logJP = LoggerFactory.getLogger(jp.getTarget().getClass());

		ServiceRollbackException exNew;
		// Para excepciones de BBDD encapsulamos en una excepcion de servicio y
		// auditamos
		if (ex instanceof DataAccessException) {
			final DatabaseException exBD = new DatabaseException((DataAccessException) ex);
			if (debug) {
				logJP.error(exBD.getMessage(), ex);
			}
			auditoriaComponent.auditarExcepcionNegocio(idSesionTramitacion, exBD);
			exNew = exBD;
		} else if (ex instanceof ServiceException) {
			if (debug) {
				logJP.error(ex.getMessage(), ex);
			}
			auditoriaComponent.auditarExcepcionNegocio(idSesionTramitacion, (ServiceException) ex);
			exNew = null;
		} else if (ex instanceof RuntimeException) {
			// 1. Para excepciones no controladas que no sean excepciones de
			// servicio las encapsulamos en una excepcion de servicio
			final ErrorNoControladoException exNC = new ErrorNoControladoException(ex);
			if (debug) {
				logJP.error(exNC.getMessage(), ex);
			}
			auditoriaComponent.auditarExcepcionNegocio(idSesionTramitacion, exNC);
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

}
