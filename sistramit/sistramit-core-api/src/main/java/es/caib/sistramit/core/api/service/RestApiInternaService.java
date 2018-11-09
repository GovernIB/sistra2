package es.caib.sistramit.core.api.service;

import java.util.List;

import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;

/**
 * Servicio funcionalidades Rest Api Interna.
 *
 * @author Indra
 *
 */
public interface RestApiInternaService {

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroAuditoriaTramitacion pFiltroBusqueda);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltro
	 *            filtro busqueda
	 * @return Numero de eventos
	 */
	Long recuperarLogSesionTramitacionAreaCount(final FiltroAuditoriaTramitacion pFiltroBusqueda);

}
