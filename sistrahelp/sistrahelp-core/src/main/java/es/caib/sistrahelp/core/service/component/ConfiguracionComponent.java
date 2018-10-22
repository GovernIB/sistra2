package es.caib.sistrahelp.core.service.component;

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
	 * @param propiedad
	 *            Propiedad configuración
	 *
	 * @return configuración
	 */
	String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad);

}