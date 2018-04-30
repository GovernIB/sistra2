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
	 * Punto de entrada acceso clave.
	 */
	public static final String PUNTOENTRADA_ACCESO_CLAVE = "/asistente/accesoClave.html";
	/**
	 * Punto de entrada logout clave.
	 */
	public static final String PUNTOENTRADA_LOGOUT_CLAVE = "/asistente/iniciarLogoutClave.html";
	/**
	 * Punto de entrada para retornar desde modulo de pagos.
	 */
	public static final String PUNTOENTRADA_RETORNO_GESTOR_PAGO_EXTERNO = "/asistente/retornoGestorPagoExterno.html";
	/**
	 * Punto de entrada para retornar desde un gestor de formularios externo.
	 */
	public static final String PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_EXTERNO = "/asistente/retornoGestorFormularioExterno.html";
	/**
	 * Atributo que indica que la sesion viene del login para que el autologout no
	 * haga logout.
	 */
	public static final String AUTOLOGOUT_FROMLOGIN = "AUTOLOGOUT_FROMLOGIN";
	/**
	 * Parámetro para autenticación con ticket.
	 */
	public static final String TICKET_PARAM = "ticket";
	/**
	 * Usuario usado en pagina de login para indicar que es un ticket de un gestor
	 * de formularios externo.
	 */
	public static final String TICKET_GFE_USER = "ticketGFE";
	/**
	 * Usuario usado en pagina de login para indicar que es un ticket de un modulo
	 * de pagos.
	 */
	public static final String TICKET_PAG_USER = "ticketPAG";
	/**
	 * Usuario usado en pagina de login para indicar que es un ticket de clave.
	 */
	public static final String TICKET_CLAVE_USER = "ticketClave";
	/**
	 * Usuario usado en pagina de login para indicar que es un ticket de la carpeta
	 * ciudadana.
	 */
	public static final String TICKET_CDC_USER = "ticketCDC";

	/**
	 * Constructor.
	 */
	private ConstantesSeguridad() {
		super();
	}
}
