package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ArchivoCP;
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
	 *                        id entidad
	 *
	 * @param idTramiteCP
	 *                        id trámite
	 * @param idioma
	 *                        idioma
	 * @throws CatalogoPluginException
	 */
	DefinicionTramiteCP obtenerDefinicionTramite(String idEntidad, String idTramiteCP, final boolean servicioCP,
			String idioma);

	/**
	 * Recupera archivo.
	 *
	 * @param idEntidad
	 *                              id entidad
	 * @param referenciaArchivo
	 *                              referencia archivo
	 * @return archivo
	 */
	ArchivoCP descargarArchivo(String idEntidad, String referenciaArchivo);

	/**
	 * Limpiar caché procedimientos de la entidad.
	 *
	 * @param idEntidad
	 *                      Id entidad
	 */
	void evictCatalogoProcedimientosEntidad(String idEntidad);

	/**
	 * Limpiar caché procedimientos de todas las entidades.
	 *
	 * @param idEntidad
	 *                      Id entidad
	 */
	void evictCatalogoProcedimientosEntidad();

}
