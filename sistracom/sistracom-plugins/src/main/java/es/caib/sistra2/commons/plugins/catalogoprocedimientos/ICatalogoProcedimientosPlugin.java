package es.caib.sistra2.commons.plugins.catalogoprocedimientos;

import java.text.ParseException;
import java.util.List;

import org.fundaciobit.plugins.IPlugin;

/**
 * Interface catálogo procedimientos.
 *
 * @author Indra
 *
 */
public interface ICatalogoProcedimientosPlugin extends IPlugin {

	/** Prefix. */
	public static final String CATALOGO_PROCEDIMIENTOS_BASE_PROPERTY = IPLUGIN_BASE_PROPERTIES
			+ "catalogoprocedimientos.";

	/**
	 * Recupera configuración trámite.
	 *
	 * @param idTramiteCP
	 *            id trámite
	 * @param idioma
	 *            idioma
	 * @throws ParseException
	 */
	DefinicionTramiteCP obtenerDefinicionTramite(String idTramiteCP, String idioma) throws RolsacPluginException;

	/**
	 * Recupera procedimientos en los que se usa un trámite de Sistra.
	 *
	 * @param idTramite
	 *            id trámite sistra
	 * @param idioma
	 *            idioma
	 * @throws RolsacPluginException
	 */
	List<DefinicionProcedimientoCP> obtenerProcedimientosTramiteSistra(String idTramite, String idioma)
			throws RolsacPluginException;

}
