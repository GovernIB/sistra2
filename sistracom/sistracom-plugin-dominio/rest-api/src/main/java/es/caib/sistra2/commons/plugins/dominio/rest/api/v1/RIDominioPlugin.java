package es.caib.sistra2.commons.plugins.dominio.rest.api.v1;

import java.util.List;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface email plugin.
 *
 * @author Indra
 *
 */
public interface RIDominioPlugin extends IPlugin {

	/** Prefix. */
	public static final String DOMINIO_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "dominio.";

	/**
	 * Invoca dominio remoto.
	 *
	 * @param idDominio
	 *            id dominio
	 * @param url
	 *            url
	 * @param parametros
	 *            parametros
	 * @return valores dominio
	 * @throws DominioPluginException
	 */
	public RValoresDominio invocarDominio(String idDominio, String url, List<RParametroDominio> parametros)
			throws RDominioPluginException;

}
