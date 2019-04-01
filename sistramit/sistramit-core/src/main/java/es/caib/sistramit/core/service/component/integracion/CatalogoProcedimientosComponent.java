package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;

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
	 * @throws CatalogoPluginException
	 */
	DefinicionTramiteCP obtenerDefinicionTramite(String idEntidad, String idTramiteCP, final boolean servicioCP,
			String idioma);

}
