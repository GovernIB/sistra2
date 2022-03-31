package es.caib.sistrages.frontend.model.types;

import es.caib.sistrages.frontend.model.OpcionMenu;

/**
 * Tipo para indicar las diferentes opciones del menu para role
 * Superadministrador.
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
	 * Configuracion autenticacion
	 */
	CONFIGURACION_AUTENTICACION;
}
