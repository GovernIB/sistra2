package es.caib.sistra2.stg.frontend.model.types;

import es.caib.sistra2.stg.frontend.model.OpcionMenu;

/**
 * Tipo para indicar las diferentes opciones del menu para role Administrador Entidad y Operador.
 *
 * @author Indra
 *
 */
public enum TypeOpcionMenuAdmOper implements OpcionMenu {

	/**
     * Tramites.
     */
    TRAMITES,
    /**
     * Configuracion entidad.
     */
    CONFIGURACION_ENTIDAD,
    /**
     * Plugins entidad.
     */
    PLUGINS_ENTIDAD,
    /**
     * Gestores formularios.
     */
    GESTORES_FORMULARIOS,
    /**
     * Gestores formularios.
     */
    DOMINIOS_ENTIDAD,
    /**
     * Gestores formularios.
     */
    FUENTES_DATOS,
    /**
     * Gestores formularios.
     */
    MENSAJES,
    /**
     * Gestores formularios.
     */
    ROLES_PERMISOS;


}
