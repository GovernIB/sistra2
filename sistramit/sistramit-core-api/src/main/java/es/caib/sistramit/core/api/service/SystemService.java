package es.caib.sistramit.core.api.service;

import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.flujo.Entidad;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;

/**
 * Servicio funcionalidades sistema.
 *
 * @author Indra
 *
 */
public interface SystemService {

	/**
	 * Obtiene configuración.
	 *
	 * @param propiedad
	 *                      Propiedad configuración
	 *
	 * @return configuración
	 */
	String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad);

	/**
	 * Audita error en front.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión tramitación
	 * @param error
	 *                                error frontal
	 */
	void auditarErrorFront(String idSesionTramitacion, ErrorFrontException error);

	/**
	 * Permite recuperar la lista de eventos internos parametrizada por fecha e
	 * identificador de sesión.
	 *
	 * @param fechaDesde
	 *                                Fecha inicio (opcional)
	 * @param fechaHasta
	 *                                Fecha fin (opcional)
	 * @param idSesionTramitacion
	 *                                Id sesion tramitacion
	 * @param ordenAsc
	 *                                Indica si orden ascendente de fecha evento
	 *                                (true) o descendente (false).
	 *
	 * @return Lista de eventos asociados a la sesión.
	 */
	List<EventoAuditoria> recuperarLogSesionTramitacion(String idSesionTramitacion, Date fechaDesde, Date fechaHasta,
			boolean ordenAsc);

	/**
	 * Invalida caché.
	 *
	 * @param invalidacion
	 *                         datos invalidación caché
	 */
	void invalidar(Invalidacion invalidacion);

	/**
	 * Revisa invalidaciones a procesar.
	 *
	 * @return invalidaciones realizadas
	 */
	ListaPropiedades revisarInvalidaciones();

	/**
	 * Verifica si es maestro
	 *
	 * @param instancia
	 *                      instancia
	 * @return si es maestro
	 */
	boolean verificarMaestro(String instancia);

	/**
	 * Procesa los envíos inmediatos
	 */
	void procesarEnviosInmediatos();

	/**
	 * Procesa los envíos pendientes de reintentar
	 */
	void procesarEnviosReintentos();

	/**
	 * Obtiene identificador entidad.
	 *
	 * @param identificador
	 *                          identificador entidad.
	 * @param idioma
	 *                          Idioma
	 * @return info entidad
	 */
	Entidad obtenerInfoEntidad(String identificador, String idioma);

}
