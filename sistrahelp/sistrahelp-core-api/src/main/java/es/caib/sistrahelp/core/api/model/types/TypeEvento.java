package es.caib.sistrahelp.core.api.model.types;

/**
 * Tipos de evento de auditoria.
 *
 * @author Indra
 *
 */
public enum TypeEvento {

	/**
	 * EVENTO PREDEFINIDO DE ERROR PARA EXCEPCIONES.
	 */
	ERROR("ERROR"),
	/**
	 * DEBUG SCRIPT. EVENTO GENERADO CUANDO SE GENERA UN DEBUG EN UN SCRIPT.
	 */
	DEBUG_SCRIPT("TR_SCR"),

	PROCESO_PURGA("TR_PUR"),
	/**
	 * INICIO DEL TRÁMITE (CORRECTO).
	 */
	INICIAR_TRAMITE("TR_INI"),
	/**
	 * CARGA DEL TRÁMITE.
	 */
	CARGAR_TRAMITE("TR_CAR"),
	/**
	 * PAGO ELECTRÓNICO.
	 */
	PAGO_ELECTRONICO("TR_PAE"),
	/**
	 * PAGO PRESENCIAL (CARTA PAGO).
	 */
	PAGO_PRESENCIAL("TR_PAP"),
	/**
	 * BORRADO DEL TRÁMITE.
	 */
	BORRAR_TRAMITE("TR_BOR"),
	/**
	 * REGISTRO TRAMITE.
	 */
	REGISTRAR_TRAMITE("TR_REG"),
	/**
	 * ENVIO TRAMITE.
	 */
	ENVIAR_TRAMITE("TR_ENV"),
	/**
	 * FIN DE TRÁMITE.
	 */
	FIN_TRAMITE("TR_FIN"),
	/**
	 * EVALUAR TRÁMITE.
	 */
	VALORACION_TRAMITE("TR_VAL"),
	/**
	 * FORMULARIO INICIO.
	 */
	FORMULARIO_INICIO("TR_FRI"),
	/**
	 * FORMULARIO FIN.
	 */
	FORMULARIO_FIN("TR_FRF"),
	/**
	 * FIRMA INICIO.
	 */
	FIRMA_INICIO("TR_SGI"),
	/**
	 * FIRMA FIN.
	 */
	FIRMA_FIN("TR_SGF"),

	/**
	 * INVALIDACIONES: PETICIÓN INVALIDACIÓN
	 */
	INV_REQ("IV_PET"),

	/**
	 * INVALIDACIONES: EJECUCIÓN INVALIDACIÓN
	 */
	INV_EJE("IV_EJE"),

	/**
	 * DEBUG FLUJO
	 */
	DEBUG_FLUJO("TR_DEB");

	/**
	 * Valor como string.
	 */
	private final String stringValueEvento;

	/**
	 * Constructor.
	 *
	 * @param value Valor como string.
	 */
	private TypeEvento(final String value) {
		stringValueEvento = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueEvento;
	}

	/**
	 * Método para From string de la clase TypeAutenticacion.
	 *
	 * @param text Parámetro text
	 * @return el type autenticacion
	 */
	public static TypeEvento fromString(final String text) {
		TypeEvento respuesta = null;
		if (text != null) {
			for (final TypeEvento b : TypeEvento.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
