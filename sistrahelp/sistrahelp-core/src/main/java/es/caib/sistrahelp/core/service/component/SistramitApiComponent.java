package es.caib.sistrahelp.core.service.component;

import java.util.List;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltrosAuditoriaTramitacion;

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
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltrosAuditoriaTramitacion pFiltros);

}
