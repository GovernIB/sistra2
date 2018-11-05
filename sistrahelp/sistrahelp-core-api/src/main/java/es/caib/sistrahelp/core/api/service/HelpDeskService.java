package es.caib.sistrahelp.core.api.service;

import java.util.List;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltrosAuditoriaTramitacion;

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
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltrosAuditoriaTramitacion pFiltros);
}
