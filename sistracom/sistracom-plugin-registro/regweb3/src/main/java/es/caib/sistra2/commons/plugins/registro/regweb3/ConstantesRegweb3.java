package es.caib.sistra2.commons.plugins.registro.regweb3;

/**
 * Constantes Regweb3.
 * @author Indra
 *
 */
public class ConstantesRegweb3 {

	/** Constante para indicar registro entrada. */
	public static final Long REGISTRO_ENTRADA = 1L;
	/** Constante para indicar registro salida. */
	public static final Long REGISTRO_SALIDA = 2L;

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

	/** Documetacion fisica: Acompa�a documentaci�n f�sica (u otros soportes) requerida.*/
	public final static String DOC_FISICA_REQUERIDA = "01";


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

	/** Validez documento: Copia (documento adjunto es una copia del original sin estar cotejada por ning�n organismo oficial y por tanto, sin validez jur�dica).*/
	public final static String VALIDEZ_DOCUMENTO_COPIA = "01";
	/** Validez documento: Copia compulsada (documento adjunto es una copia del original y cotejada por un organismo oficial, y por tanto, con validez jur�dica).*/
	public final static String VALIDEZ_DOCUMENTO_COPIA_COMPULSADA = "02";
	/** Validez documento: Copia original (documento adjunto es una copia del documento pero con exactamente la misma validez jur�dica que el original).*/
	public final static String VALIDEZ_DOCUMENTO_COPIA_ORIGINAL = "03";
	/** Validez documento: Copia original (documento adjunto es el original electr�nico).*/
	public final static String VALIDEZ_DOCUMENTO_ORIGINAL = "04";

}
