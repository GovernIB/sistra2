package es.caib.sistramit.core.api.model.system.types;

/**
 * Propiedades configuración.
 *
 * @author Indra
 *
 */
public enum TypePropiedadConfiguracion {

	// TODO REVISAR NOMBRES PROPIEDADES

	/** Entorno. */
	ENTORNO("entorno"),
	/** Versión. */
	VERSION("sistra2.version"),
	/** Ficheros externos. */
	PATH_FICHEROS_EXTERNOS("ficherosExternos.path"),

	/** STG Url. */
	SISTRAGES_URL("sistrages.url"),
	/** STG Url. */
	SISTRAGES_USR("sistrages.usr"),
	/** STG Url. */
	SISTRAGES_PWD("sistrages.pwd"),

	/** Url asistente. */
	SISTRAMIT_URL("sistramit.url"),
	/** Idiomas soportados. */
	IDIOMAS_SOPORTADOS("sistramit.idiomas"),

	/** Anexo formulario soporte: extensiones. */
	ANEXO_SOPORTE_EXTENSIONES("sistramit.anexoSoporte.extensiones"),
	/** Anexo formulario soporte: tamaño. */
	ANEXO_SOPORTE_TAMANYO("sistramit.anexoSoporte.tamanyo"),

	/**
	 * Realiza chequeo de si los datos de persistencia del paso se corresponden con
	 * los almacenados en BBDD (true/false). Una vez depurada la aplicacion se puede
	 * deshabilitar
	 */
	CHECK_PERSISTENCIA_POSTACCION("sistramit.checkPersistenciaPostAccion"),
	/** Timeout sesión web (minutos). */
	TIMEOUT_SESION_WEB("sesionweb.timeout"),
	/**
	 * Timeout autenticación tickets (segundos): tiempo permitido desde la
	 * generación del ticket hasta que se consume.
	 */
	TIMEOUT_TICKET("sistramit.ticket.timeout"),
	/** Prefijo plugin. */
	PLUGINS_PREFIJO("plugins.prefix"),

	/** Max. número a recuperar. */
	PERDIDA_CLAVE_NUMMAXTRAMITES("sistrahelp.perdidaClave.numMaxTramites"),
	/**
	 * Propiedades en la purga.
	 */
	/** Tiempos de purgado. **/
	PURGA_ERRORES_INTERNOS("sistramit.purga.erroresInternos"),
	/** Tiempo (minutos) tras el cual seran borradas las sesiones de formularios */
	PURGA_SESION_FORMULARIOS_FINALIZADOS("sistramit.purga.formulario.fin"),
	/**
	 * Tiempo (minutos) tras el cual seran borradas las sesiones de formularios, si
	 * la sesion de formulario no esta finalizada
	 */
	PURGA_SESION_FORMULARIOS_NO_FINALIZADOS("sistramit.purga.formulario.nofin"),
	/** Tiempo (minutos) tras el cual seran borradas los pagos */
	PURGA_PAGOS_FINALIZADOS("sistramit.purga.pago.fin"),
	/**
	 * Tiempo (minutos) tras el cual seran borradas los pagos, si la sesion de
	 * formulario no esta finalizada
	 */
	PURGA_PAGOS_NO_FINALIZADOS("sistramit.purga.pago.nofin"),
	/**
	 * Tiempo (horas) tras su fecha de finalizacion tras el cual un tramite
	 * finalizado sera purgado.
	 */
	PURGA_FINALIZADOS("sistramit.purga.finalizado"),
	/**
	 * Tiempo (horas) tras su fecha de ultimo acceso tras el cual seran purgados los
	 * tramites no persistentes no finalizados.
	 */
	PURGA_NO_PERSISTENTES("sistramit.purga.noPersistente"),
	/**
	 * Tiempo (horas) tras su fecha de caducidad tras el cual seran purgados los
	 * tramites persistentes caducados.
	 */
	PURGA_CADUCADOS("sistramit.purga.caducados"),
	/**
	 * Tiempo (horas) tras que las invalidaciones de tramites y dominios se purgan,
	 * debe ser mayor que el TimeToLive (o si existe el TimeToIdle) de las caches de
	 * tramites y dominios.
	 */
	PURGA_INVALIDACIONES("sistramit.purga.invalidaciones"),
	/**
	 * Dias despues de la ultima modificacion del tramite tras los cuales se borrara
	 * tramite pendiente de purgar por pago realizado.
	 */
	PURGA_PENDIENTE_PURGA_PAGO("sistramit.purga.pendientePurgaPago"),
	/**
	 * Tiempo (dias) tras su fecha de purgado tras el cual seran definitivamente
	 * borrados los purgados.
	 **/
	PURGA_PURGADOS("sistramit.purga.purgados");
	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param valueStr
	 *            Valor como string.
	 */
	private TypePropiedadConfiguracion(final String valueStr) {
		stringValue = valueStr;
	}

	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypePropiedadConfiguracion fromString(final String text) {
		TypePropiedadConfiguracion respuesta = null;
		if (text != null) {
			for (final TypePropiedadConfiguracion b : TypePropiedadConfiguracion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
