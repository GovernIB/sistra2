package es.caib.sistramit.core.api.model.security;

/**
 * Constantes de seguridad.
 *
 */
public final class ConstantesSeguridad {

	/**
	 * Punto de entrada para iniciar un tramite desde el asistente.
	 */
	public static final String PUNTOENTRADA_INICIAR_TRAMITE = "/asistente/iniciarTramite.html";
	/**
	 * Punto de entrada para cargar un tramite desde el asistente.
	 */
	public static final String PUNTOENTRADA_CARGAR_TRAMITE = "/asistente/cargarTramite.html";
	/**
	 * Punto de entrada retorno componente autenticacion.
	 */
	public static final String PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN = "/asistente/retornoAutenticacion.html";
	/**
	 * Punto de entrada logout autenticacion.
	 */
	public static final String PUNTOENTRADA_RETORNO_AUTENTICACION_LOGOUT = "/asistente/iniciarLogoutAutenticacion.html";
	/**
	 * Punto de entrada para retornar desde modulo de pagos (funciona a través de
	 * ticket).
	 */
	public static final String PUNTOENTRADA_RETORNO_GESTOR_PAGO_EXTERNO = "/asistente/retornoPagoExterno.html";
	/**
	 * Punto de entrada para retornar desde un gestor de formularios externo
	 * (funciona a través de ticket).
	 */
	public static final String PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_EXTERNO = "/asistente/retornoGestorFormularioExterno.html";
	/**
	 * Punto de entrada para retornar desde firma registro (no funciona a través de
	 * ticket).
	 */
	public static final String PUNTOENTRADA_RETORNO_FIRMA_REGISTRO = "/asistente/rt/retornoFirmaDocumento.html";

	/**
	 * Punto de entrada para retornar desde carpeta ciudadano.
	 */
	public static final String PUNTOENTRADA_RETORNO_CARPETA = "/asistente/retornoCarpetaCiudadano.html";

	/**
	 * Atributo que indica que la sesion viene del login para que el autologout no
	 * haga logout.
	 */
	public static final String AUTOLOGOUT_FROMLOGIN = "AUTOLOGOUT_FROMLOGIN";
	/**
	 * Usuario interno de acceso anonimo.
	 */
	public static final String ANONIMO_USER = "nobody";
	/**
	 * Usuario usado en pagina de login para indicar que es autenticación por ticket
	 * por Clave.
	 */
	public static final String TICKET_USER_CLAVE = "ticket-user-clave";
	/**
	 * Usuario usado en pagina de login para indicar que es autenticación por ticket
	 * por Carpeta Ciudadana.
	 */
	public static final String TICKET_USER_CARPETA = "ticket-user-carpeta";
	/**
	 * Usuario usado en pagina de login para indicar que es autenticación por ticket
	 * por Gestor Formularios.
	 */
	public static final String TICKET_USER_GF = "ticket-user-gf";
	/**
	 * Usuario usado en pagina de login para indicar que es autenticación por ticket
	 * por Pagos.
	 */
	public static final String TICKET_USER_PAGO = "ticket-user-pago";
	/**
	 * Parámetro para autenticación con ticket.
	 */
	public static final String TICKET_PARAM = "ticket";

	/**
	 * Constructor.
	 */
	private ConstantesSeguridad() {
		super();
	}
}
