package es.caib.sistramit.core.service.model.flujo.types;

/**
 * Indica la fase de ejecución de una regla de tramitación.
 *
 * @author Indra
 *
 */
public enum TypeFaseEjecucion {
	/**
	 * Indica que se ejecutara al iniciar trámite, tras el control de
	 * personalización y control de acceso.
	 */
	PRE_INICIO,
	/**
	 * Indica que se ejecutara después de iniciar trámite.
	 */
	POST_INICIO,
	/**
	 * Indica que se ejecutara al cargar trámite, tras el control de personalización
	 * y control de acceso.
	 */
	PRE_CARGA,
	/**
	 * Indica que se ejecutara después de cargar trámite.
	 */
	POST_CARGA,
	/**
	 * Indica que se ejecutara como validación previa al ir a un paso.
	 */
	PRE_NAVEGACION,
	/**
	 * Indica que se ejecutara como validación posterior al ir a un paso.
	 */
	POST_NAVEGACION,
	/**
	 * Indica que se ejecutara como validación previa a ejecutar una acción sobre un
	 * paso.
	 */
	PRE_ACCION,
	/**
	 * Indica que se ejecutara después de ejecutar una acción sobre un paso.
	 */
	POST_ACCION,
	/**
	 * Indica que se ejecutara como validación previa a cancelar un trámite.
	 */
	PRE_CANCELAR;
}
