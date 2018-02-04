package es.caib.sistra2.stg.frontend.model.types;

import es.caib.sistra2.stg.frontend.model.OpcionMenu;

/**
 * Tipo para indicar las diferentes opciones del menu para role Superadministrador.
 *
 * @author Indra
 *
 */
public enum TypeOpcionMenuSuperAdministrador implements OpcionMenu {

	/**
     * Entidades.
     */
    ENTIDADES,
    /**
     * Propiedades globales.
     */
    PROPIEDADES_GLOBALES,
    /**
     * Plugins globales.
     */
    PLUGINS_GLOBALES,
    /**
     * Dominios globales.
     */
    DOMINIOS_GLOBALES,
    
    /**
     * Test
     */
    TEST
    ;
}
