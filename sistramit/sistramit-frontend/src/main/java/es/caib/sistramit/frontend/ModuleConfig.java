package es.caib.sistramit.frontend;

// TODO VER COMO TRATAR LAS PROPIEDADES
// EN FICHERO PROPERTIES DEBERIA HABER URL A API SISTRAGES
// EL RESTO PROPS A VER SI EN SISTRAGES O EN PROPERTIES

/**
 * Acceso a propiedades del modulo.
 *
 * @author Indra
 */
public final class ModuleConfig {

	/** Entorno de ejecución: DES, PRE o PRO. */
	public static final String entorno = "DES";

	/** Url Sistramit. */
	public static final String urlSistramit = "http://localhost:8080/sistramitfront";

	/** Url excepcion fatal. */ // TODO Ante una excepcion fatal no controlada, hay que ver que se hace
	public static final String urlExcepcionFatal = "http://www.google.es";

	/** Ajax timeout (min). */ // TODO EN PROPERTIES O SISTRAGES ??
	public static final String ajaxTimeout = "4";

	/** Idiomas soportados. Recuperar de Sistrages. */
	public static final String idiomasSoportados = "es,ca,en";

}
