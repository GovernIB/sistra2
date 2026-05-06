package es.caib.sistramit.core.service.component.system;

import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;

import java.util.List;

/**
 *
 * Permite establecer logica personalizada para auditar eventos en la invocación
 * del flujo de tramitación.
 *
 * @author Indra
 *
 */
public interface AuditorEventosFlujoTramitacion {

	/**
	 * Permite establecer logica personalizada para auditar eventos en la invocacion
	 * de un metodo.
	 *
	 * @param idSesionTramitacion
	 *                                Id sesion tramitación Instancia objeto
	 *                                invocado
	 * @param metodo
	 *                                Nombre metodo
	 * @param argumentos
	 *                                Argumentos
	 * @param debugEnabled
	 *                                Si está habilitado el debug en el trámite
	 * @return Devuelve eventos generados.
	 */
	List<EventoAuditoria> interceptaInvocacion(String idSesionTramitacion, String metodo, Object[] argumentos,
			boolean debugEnabled);

	/**
	 * Permite establecer logica personalizada para auditar eventos en el retorno de
	 * un metodo.
	 *
	 * @param idSesionTramitacion
	 *                                Id sesion tramitación Instancia objeto
	 *                                invocado
	 * @param metodo
	 *                                Nombre metodo
	 * @param argumentos
	 *                                Argumentos
	 * @param result
	 *                                Resultado del metodo
	 * @param debugEnabled
	 *                                Si está habilitado el debug en el trámite
	 * @return Devuelve eventos generados.
	 */
	List<EventoAuditoria> interceptaRetorno(String idSesionTramitacion, String metodo, Object[] argumentos,
			Object result, boolean debugEnabled);

	/**
	 * Permite establecer logica personalizada para auditar eventos si un metodo
	 * genera una excepcion.
	 *
	 * @param idSesionTramitacion Id sesion tramitación Instancia objeto
	 *                            invocado
	 * @param metodo              Nombre metodo
	 * @param argumentos          Argumentos
	 * @param excepcion           generada
	 * @param debugEnabled 							  Si está habilitado el debug en el trámite
	 * @return Devuelve eventos generados.
	 */
	List<EventoAuditoria> interceptaExcepcion(String idSesionTramitacion, String metodo, Object[] argumentos,
											  ServiceException excepcion, boolean debugEnabled);

}
