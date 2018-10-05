package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.RolsacPluginException;

/**
 * Acceso a componente Catálogo de procedimientos.
 *
 * @author Indra
 *
 */
public interface CatalogoProcedimientosComponent {

	/**
	 * Recupera configuración trámite.
	 *
	 * @param idEntidad
	 *            id entidad
	 *
	 * @param idTramiteCP
	 *            id trámite
	 * @param idioma
	 *            idioma
	 * @throws RolsacPluginException
	 */
	DefinicionTramiteCP obtenerDefinicionTramite(String idEntidad, String idTramiteCP, String idioma);

}
