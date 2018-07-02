package es.caib.sistramit.core.service.component.system;

import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;

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