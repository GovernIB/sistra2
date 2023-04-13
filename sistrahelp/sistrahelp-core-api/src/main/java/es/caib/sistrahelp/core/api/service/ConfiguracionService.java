package es.caib.sistrahelp.core.api.service;

import org.apache.commons.digester.plugins.PluginException;
import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.sistrahelp.core.api.model.ContenidoFichero;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;

public interface ConfiguracionService {

	/**
	 * Obtener datos entidad.
	 *
	 * @param idEntidad id de la entidad
	 * @return entidad
	 */
	public Entidad obtenerDatosEntidad(final String idEntidad);

	/**
	 * Obtiene el contenido del fichero
	 *
	 * @param fichero fichero
	 * @return contenido del fichero
	 */
	public ContenidoFichero getContentFicheroByPath(final String fichero);

	/**
	 * Obtiene configuración.
	 *
	 * @param propiedad Propiedad configuración
	 *
	 * @return configuración
	 */
	String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad);

	IPlugin obtenerPluginGlobal(TypePluginGlobal tipoPlugin) throws PluginException;

	String obtenerPropiedadConfiguracionSistrages(TypePropiedadConfiguracion purgaPurgados);

}
