package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.text.ParseException;
import java.util.List;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface catálogo procedimientos.
 *
 * @author Indra
 *
 */
public interface ICatalogoProcedimientosPlugin extends IPlugin {

	/** Prefix. */
	public static final String CATALOGO_PROCEDIMIENTOS_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES
			+ "catalogoprocedimientos.";

	/**
	 * Recupera configuración trámite.
	 *
	 * @param idTramiteCP
	 *                        id trámite
	 * @param servicio
	 *                        indica si es un servicio
	 * @param idioma
	 *                        idioma
	 * @throws ParseException
	 */
	DefinicionTramiteCP obtenerDefinicionTramite(String idTramiteCP, boolean servicio, String idioma)
			throws CatalogoPluginException;

	/**
	 * Recupera trámites en los que se usa un trámite de Sistra.
	 *
	 * @param idTramite
	 *                      id trámite sistra
	 * @param idioma
	 *                      idioma
	 * @param soloVigentes	Indica true/false si hay que buscar todo o sólo los vigentes.
	 * @throws CatalogoPluginException
	 */
	List<DefinicionTramiteCP> obtenerTramites(String idTramite, Integer version, String idioma)
			throws CatalogoPluginException;

	/**
	 * Recupera archivo de catálogo.
	 *
	 * @param referenciaArchivo
	 *                              referencia archivo
	 * @return archivo
	 */
	ArchivoCP descargarArchivo(String referenciaArchivo) throws CatalogoPluginException;

}
