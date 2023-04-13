package es.caib.sistrahelp.core.service.component;

import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;

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
	 * @param propiedad Propiedad configuración
	 *
	 * @return configuración
	 */
	String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad);

	String obtenerPropiedadConfiguracionSistrages(TypePropiedadConfiguracion propiedad);

}