package es.caib.sistramit.core.service.component.system;

import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionAutenticacion;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Componente para acceder a configuracion.
 *
 * @author Indra
 *
 */
public interface ConfiguracionComponent {

	/**
	 * Obtiene configuración.
	 *
	 * @param propiedad
	 *                      Propiedad configuración
	 *
	 * @return configuración
	 */
	String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad);

	/**
	 * Obtiene configuración.
	 *
	 * @param propiedad
	 *                       Propiedad configuración
	 * @param forceLocal
	 *                       si fuerza solo a buscar en el properties local y no
	 *                       buscar en la configuración global del STG
	 * @return configuración
	 */
	String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad, boolean forceLocal);

	/**
	 * Recupera definición versión de trámite.
	 *
	 * @param idTramite
	 *                      id trámite
	 * @param version
	 *                      versión
	 * @param idioma
	 *                      idioma
	 * @return Definición versión trámite
	 */
	DefinicionTramiteSTG recuperarDefinicionTramite(String idTramite, int version, String idioma);

	/**
	 * Recupera definición versión de trámite.
	 *
	 * @param idDominio
	 *                      id Dominio
	 * @return Definición dominio
	 */
	RDominio recuperarDefinicionDominio(String idDominio);

	/**
	 * Recupera configuracion entidad.
	 *
	 * @param idEntidad
	 * @return configuracion entidad.
	 */
	RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad);

	/**
	 * Recupera avisos entidad.
	 *
	 * @param idEntidad
	 * @return configuracion entidad.
	 */
	RAvisosEntidad obtenerAvisosEntidad(String idEntidad);

	/**
	 * Obtiene tipo plugin global.
	 *
	 * @param tipoPlugin
	 *                       tipo plugin
	 * @return Plugin
	 */
	IPlugin obtenerPluginGlobal(TypePluginGlobal tipoPlugin);

	/**
	 * Obtiene tipo plugin entidad.
	 *
	 * @param tipoPlugin
	 *                       tipo plugin
	 * @return Plugin
	 */
	IPlugin obtenerPluginEntidad(TypePluginEntidad tipoPlugin, String idEntidad);

	/**
	 * Obtiene url recursos.
	 *
	 * @return configuración
	 */
	String obtenerUrlResources();

	/**
	 * Obtiene configuración autenticación.
	 *
	 * @param idConfAut
	 *                      id configuración autenticación.
	 * @param idEntidad
	 *                      id entidad (nulo si global).
	 * @return configuración autenticación
	 */
	RConfiguracionAutenticacion obtenerConfiguracionAutenticacion(String idConfAut, String idEntidad);

}