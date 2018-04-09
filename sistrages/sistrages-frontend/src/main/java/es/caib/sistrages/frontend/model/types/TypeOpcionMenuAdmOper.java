package es.caib.sistrages.frontend.model.types;

import es.caib.sistrages.frontend.model.OpcionMenu;

/**
 * Tipo para indicar las diferentes opciones del menu para role Administrador
 * Entidad y Operador.
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
	// GESTORES_FORMULARIOS,
	/**
	 * Formateador formularios
	 */
	FormateadorFormulario,
	/**
	 * DOMINIOS ENTIDAD.
	 */
	DOMINIOS_ENTIDAD,
	/**
	 * FUENTES DATOS.
	 */
	FUENTES_DATOS,
	/**
	 * MENSAJES.
	 */
	MENSAJES,
	/**
	 * ROLES PERMISOS.
	 */
	ROLES_PERMISOS;

}
