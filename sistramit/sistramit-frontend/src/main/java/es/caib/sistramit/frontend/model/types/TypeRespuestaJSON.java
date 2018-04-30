package es.caib.sistramit.frontend.model.types;

/**
 * Tipos de respuesta JSON.
 *
 * @author Indra
 *
 */
public enum TypeRespuestaJSON {

	/**
	 * SUCCESS: Operación correcta. En caso de que exista un mensaje será de tipo
	 * informativo. Sigue cargando la pagina.
	 */
	SUCCESS,
	/**
	 * WARNING: Operación correcta pero con avisos. Se mostraró un mensaje será de
	 * tipo alerta. Sigue cargando la pagina.
	 */
	WARNING,
	/**
	 * ERROR: Se ha producido un error. Se mostrará mensaje de error y se cancela la
	 * carga de la pagina.
	 */
	ERROR,
	/**
	 * FATAL: Se ha producido un error y se desconectará de la aplicación. Se
	 * mostrará mensaje de error
	 */
	FATAL;

}
