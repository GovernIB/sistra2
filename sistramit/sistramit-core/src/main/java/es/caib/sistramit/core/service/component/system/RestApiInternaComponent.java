package es.caib.sistramit.core.service.component.system;

import java.util.List;

import es.caib.sistramit.core.api.model.system.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.PagoAuditoria;

/**
 * Componente para generar auditoria.
 *
 * @author Indra
 *
 */
public interface RestApiInternaComponent {

	/**
	 * Recuperar clave tramitacion area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return OUTPerdidaClave
	 */
	public OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda);

}