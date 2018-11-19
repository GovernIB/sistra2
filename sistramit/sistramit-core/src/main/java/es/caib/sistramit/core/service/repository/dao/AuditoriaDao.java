package es.caib.sistramit.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;

/**
 * Interface de acceso a base de datos para servicios de auditoria.
 */
public interface AuditoriaDao {

	/**
	 * Guarda un evento en auditoría.
	 *
	 * @param evento
	 *            Evento
	 * @param idSesionTramitacion
	 *            Indica si el evento esta asociado a una sesion de tramitacion
	 *            (nulo si no lo esta)
	 */
	void add(final EventoAuditoria evento, String idSesionTramitacion);

	/**
	 * Borra log interno los eventos no asociados a una sesión hasta la fecha
	 * indicada (no incluida).
	 *
	 * @param toDate
	 *            Fecha hasta la cual se debe borrar.
	 * @return numero de errores borrados
	 */
	int purgarEventosSinSesion(final Date toDate);

	/**
	 * Borra log interno los eventos asociados una sesión hasta la fecha indicada
	 * (no incluida).
	 *
	 * @param toDate
	 *            Fecha hasta la cual se debe borrar.
	 * @return numero de errores borrados
	 */
	int purgarEventosSesion(String idSesion);

	/**
	 * Permite recuperar la lista de eventos internos parametrizada por fecha e
	 * identificador de sesión.
	 *
	 * @param idSesionTramitacion
	 *            Id sesion tramitacion (opcional)
	 * @param fechaDesde
	 *            Fecha inicio (opcional)
	 * @param fechaHasta
	 *            Fecha fin (opcional)
	 * @param ordenAsc
	 *            Indica si orden ascendente de fecha evento (true) o descendente
	 *            (false).
	 * @return Lista de eventos asociados a la sesión.
	 */
	List<EventoAuditoria> retrieve(String idSesionTramitacion, Date fechaDesde, Date fechaHasta, boolean ordenAsc);

	/**
	 * Permite recuperar la lista de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Permite recuperar la lista de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return numero de eventos
	 */
	Long countByAreas(final FiltroEventoAuditoria pFiltroBusqueda);

}
