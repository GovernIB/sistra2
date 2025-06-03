package es.caib.sistramit.core.service.component.system;

import java.util.List;

import es.caib.sistramit.core.api.model.system.rest.externo.*;

/**
 * Componente para generar rest externo.
 *
 * @author Indra
 *
 */
public interface RestApiExternaComponent {

	/**
	 * Recuperar tramites.
	 *
	 * @param pFiltro
	 *                    filtro
	 * @return lista de tramites
	 */
	public List<TramitePersistencia> recuperarTramites(FiltroTramitePersistencia pFiltro);

	/**
	 * Recuperar eventos.
	 *
	 * @param pFiltro
	 *                    filtro
	 * @return lista de eventos
	 */
	public List<Evento> recuperarEventos(FiltroEvento pFiltro);

	/**
	 * Obtener ticket acceso.
	 *
	 * @param infoTicketAcceso
	 *                    filtro
	 * @return url con ticket de acceso
	 */
	public String obtenerTicketAcceso(InfoTicketAcceso infoTicketAcceso);

	/**
	 * Obtiene lista trámites finalizados.
	 * 
	 * @param filtroBusqueda
	 *                           Filtro
	 * @return lista trámites finalizados
	 */
	public List<TramiteFinalizado> recuperarTramitesFinalizados(FiltroTramiteFinalizado filtroBusqueda);


}