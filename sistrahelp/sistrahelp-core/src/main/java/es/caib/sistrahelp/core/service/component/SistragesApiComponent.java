package es.caib.sistrahelp.core.service.component;

import java.util.List;

import org.apache.commons.digester.plugins.PluginException;
import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RPermisoHelpDesk;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;

/**
 * Acceso a componente SISTRAGES.
 *
 * @author Indra
 *
 */
public interface SistragesApiComponent {

	/**
	 * Obtener permisos de helpdesk.
	 *
	 * @return lista de permisos
	 */
	List<RPermisoHelpDesk> obtenerPermisosHelpdesk();

	/**
	 * Obtener datos de la entidad.
	 *
	 * @param idEntidad identificador de la entidad
	 * @return entidad
	 */
	Entidad obtenerDatosEntidad(String idEntidad);

	/**
	 * Obtiene tipo plugin global.
	 *
	 * @param tipoPlugin tipo plugin
	 * @return Plugin
	 * @throws PluginException
	 */
	IPlugin obtenerPluginGlobal(TypePluginGlobal tipoPlugin) throws PluginException;

	RConfiguracionGlobal obtenerConfiguracionGlobal();

	Entidad obtenerDatosEntidadByArea(String idArea);

	List<String> obtenerTramitesPorArea(String idArea);

	List<Integer> obtenerVersionTramite(String identificador, String tramite);

	/**
	 * Obtiene el logo
	 * @param codDir3
	 * @return
	 */
	byte[] urlLogoEntidad(String codDir3);

}
