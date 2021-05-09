package es.caib.sistramit.core.api.service;

import java.util.List;

import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;

/**
 * Servicio funcionalidades Rest Api Externa.
 *
 * @author Indra
 *
 */
public interface RestApiExternaService {

	/**
	 * Recuperar tramites en persistencia.
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
	 * @param filtro
	 *                   filtro
	 * @return url con ticket de acceso
	 */
	public String obtenerTicketAcceso(InfoTicketAcceso pInfoTicketAcceso);

	/**
	 * Obtiene lista trámites finalizados.
	 *
	 * @param filtroBusqueda
	 *                           Filtro
	 * @return lista trámites finalizados
	 */
	public List<TramiteFinalizado> recuperarTramitesFinalizados(FiltroTramiteFinalizado filtroBusqueda);

}
