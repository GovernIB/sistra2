package es.caib.sistrahelp.core.service.component;

import java.util.List;

import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltrosAuditoria;

/**
 * Acceso a componente SISTRAGES.
 *
 * @author Indra
 *
 */
public interface SistramitApiComponent {

	/**
	 * Obtener auditoria evento.
	 *
	 * @return the lista de
	 */
	public List<REventoAuditoria> obtenerAuditoriaEvento(RFiltrosAuditoria pFiltros);

}
