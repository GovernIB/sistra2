package es.caib.sistrahelp.core.api.service;

import java.util.List;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;

/**
 * Servicio para auditoria de tramites.
 *
 * @author Indra
 *
 */
public interface HelpDeskService {

	/**
	 * Obtener auditoria evento.
	 *
	 * @return lista de eventos
	 */
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Obtener numero de elementos de auditoria evento.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return numero de elementos
	 */
	public Long countAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda);

	/**
	 * Obtener auditoria tramite.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return resultado perdida clave
	 */
	public ResultadoPerdidaClave obtenerAuditoriaTramite(FiltroPerdidaClave pFiltroBusqueda);
}
