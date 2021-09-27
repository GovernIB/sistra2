package es.caib.sistra2.commons.plugins.registro.regweb3;

/**
 * Constantes Regweb3.
 *
 * @author Indra
 *
 */
public class ConstantesRegweb3 {

	/** Nombre de propiedad de endpoint de info */
	public final static String PROP_ENDPOINT_INFO = "endpoint.info";
	/** Nombre de propiedad de endpoint de salida */
	public final static String PROP_ENDPOINT_ASIENTO = "endpoint.asiento";
	/** Nombre de propiedad de identificador de aplicacion */
	public final static String PROP_APLICACION_CODIGO = "aplicacion.codigo";
	/** Nombre de propiedad de version de aplicacion */
	public final static String PROP_APLICACION_VERSION = "aplicacion.version";
	/** Nombre de propiedad de usuario conexion */
	public final static String PROP_USUARIO = "usr";
	/** Nombre de propiedad de password del usuario de conexion */
	public final static String PROP_PASSWORD = "pwd";
	/** Nombre de propiedad de que indica si se han de enviar documentos */
	public final static String PROP_INSERTADOCS = "insertarDocs";
	/** Nombre de propiedad que indica si se han de enviar documentos internos */
	public final static String PROP_INSERTADOCS_INT = "insertarDocs.internos";
	/**
	 * Nombre de propiedad de que indica si se han de enviar documentos formateados
	 */
	public final static String PROP_INSERTADOCS_FOR = "insertarDocs.formateados";
	/** Nombre de propiedad que indica la ruta en la que se encuentran los WSDL */
	public final static String PROP_WSDL_DIR = "wsdl.dir";
	/** Nombre de propiedad de password del usuario de conexion */
	public final static String PROP_LOG_PETICIONES = "log.peticionesWS";
	/** Nombre de propiedad que indica timeout registro (segundos) */
	public final static String PROP_TIMEOUT = "timeout";
	/**
	 * Nombre de propiedad para indicar si los justificantes se generan como CSV
	 * (true/false).
	 */
	public final static String PROP_JUSTIFICANTE_DESCARGA = "justificanteDESCARGA";
	/** Nombre de propiedad que indica ruta */
	public final static String PROP_JUSTITICANTE_URL = "justificanteURL";

	/** Constante para indicar registro entrada. */
	public static final Long REGISTRO_ENTRADA = 1L;
	/** Constante para indicar registro salida. */
	public static final Long REGISTRO_SALIDA = 2L;

	/**
	 * Constante para indicar la operacion notificacion en caso de registro de
	 * salida.
	 */
	public static final Long OPERACION_NOTIFICACION = 1L;
	/**
	 * Constante para indicar la operacion comunicacion en caso de registro de
	 * salida
	 */
	public static final Long OPERACION_COMUNICACION = 2L;
	/**
	 * Constante para indicar la operacion asiento normal en caso de registro de
	 * salida.
	 */
	public static final Long OPERACION_NORMAL = null;

	/** Tipo interesado Persona f�sica. */
	public final static String TIPO_INTERESADO_PERSONA_FISICA = "2";
	/** Tipo interesado Persona juridica. */
	public final static String TIPO_INTERESADO_PERSONA_JURIDICA = "3";
	/** Tipo interesado Administraci�n. */
	public final static String TIPO_INTERESADO_PERSONA_ADMINISTRACION = "1";

	/** Tipo documento identificacion: NIF f�sica. */
	public final static String TIPO_DOCID_NIF = "N";
	/** Tipo documento identificacion: NIF persona jur�dica. */
	public final static String TIPO_DOCID_CIF = "C";
	/** Tipo documento identificacion: NIE. */
	public final static String TIPO_DOCID_NIE = "E";
	/** Tipo documento identificacion: Pasaporte. */
	public final static String TIPO_DOCID_PSP = "P";

	/** Tipo documento: formulario. */
	public final static String TIPO_DOCUMENTO_FORMULARIO = "01";
	/** Tipo documento: anexo. */
	public final static String TIPO_DOCUMENTO_ANEXO = "02";
	/** Tipo documento: fichero tecnico intermedio. */
	public final static String TIPO_DOCUMENTO_FICHERO_TECNICO = "03";

	/** Tipo documental: Comunicaci�n. */
	public final static String TIPO_DOCUMENTAL_COMUNICACION = "TD06";
	/** Tipo documental: Notificaci�n. */
	public final static String TIPO_DOCUMENTAL_NOTIFICACION = "TD07";
	/** Tipo documental: Acuse de recibo. */
	public final static String TIPO_DOCUMENTAL_ACUSE_RECIBO = "TD09";
	/** Tipo documental: Informe. */
	public final static String TIPO_DOCUMENTAL_INFORME = "TD13";
	/** Tipo documental: Solicitud. */
	public final static String TIPO_DOCUMENTAL_SOLICITUD = "TD14";
	/** Tipo documental: Otros. */
	public final static String TIPO_DOCUMENTAL_OTROS = "TD99";

	/** Origen documento: ciudadano. */
	public final static Integer ORIGEN_DOCUMENTO_CIUDADANO = new Integer(0);
	/** Origen documento: administracion. * */
	public final static Integer ORIGEN_DOCUMENTO_ADMINISTRACION = new Integer(1);

	/** Modo firma: sin firma. */
	public final static Integer MODO_FIRMA_SIN_FIRMA = new Integer(0);
	/** Modo firma: attached. */
	public final static Integer MODO_FIRMA_ATTACHED = new Integer(1);
	/** Modo firma: detached. */
	public final static Integer MODO_FIRMA_DETACHED = new Integer(2);

	/**
	 * Validez documento: Copia (documento adjunto es una copia del original sin
	 * estar cotejada por ning�n organismo oficial y por tanto, sin validez
	 * jur�dica).
	 */
	public final static String VALIDEZ_DOCUMENTO_COPIA = "01";
	/**
	 * Validez documento: Copia compulsada (documento adjunto es una copia del
	 * original y cotejada por un organismo oficial, y por tanto, con validez
	 * jur�dica).
	 */
	public final static String VALIDEZ_DOCUMENTO_COPIA_COMPULSADA = "02";
	/**
	 * Validez documento: Copia original (documento adjunto es una copia del
	 * documento pero con exactamente la misma validez jur�dica que el original).
	 */
	public final static String VALIDEZ_DOCUMENTO_COPIA_ORIGINAL = "03";
	/**
	 * Validez documento: Copia original (documento adjunto es el original
	 * electr�nico).
	 */
	public final static String VALIDEZ_DOCUMENTO_ORIGINAL = "04";

	/** Idiomas */
	/** Constante para indicar idioma catalán. */
	public static final Long IDIOMA_CATALAN = 1L;
	/** Constante para indicar idioma castellano. */
	public static final Long IDIOMA_CASTELLANO = 2L;
	/** Constante para indicar idioma inglés. */
	public static final Long IDIOMA_INGLES = 5L;
	/** Constante para indicar idioma otros. */
	public static final Long IDIOMA_OTROS = 6L;

	/** Tipo de documentacion fisica */
	/** Constante para indicar documentación adjunta en soporte papel. */
	public static final Long TIPO_DOCFIS_PAPEL = 1L;
	/**
	 * Constante para indicar documentación adjunta digitalizada y
	 * complementariamente en papel.
	 */
	public static final Long TIPO_DOCFIS_MIXTA = 2L;
	/** Constante para indicar documentación adjunta digitalizada. */
	public static final Long TIPO_DOCFIS_DIGTL = 3L;

	/** Tamanyo max. resumen. */
	public static final int MAX_SIZE_ASUNTO_RESUMEN = 240;
	/** Tamanyo max. observaciones interesado. */
	public static final int MAX_SIZE_INTERESADO_OBSERVACIONES = 160;
	/** Tamanyo max. nombre fichero anexo. */
	public static final int MAX_SIZE_ANEXO_FILENAME = 80;
	/** Tamanyo max. titulo fichero anexo. */
	public static final int MAX_SIZE_ANEXO_TITULO = 200;

}
