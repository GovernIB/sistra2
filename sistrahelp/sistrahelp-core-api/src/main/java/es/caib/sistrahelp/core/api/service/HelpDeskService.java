package es.caib.sistrahelp.core.api.service;

import java.util.List;

import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltrosAuditoria;

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
	public List<REventoAuditoria> obtenerAuditoriaEvento(RFiltrosAuditoria pFiltros);
}
