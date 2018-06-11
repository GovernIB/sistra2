package es.caib.sistramit.core.api.model.system.types;

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
	 * INICIO DEL TRÁMITE (CORRECTO).
	 */
	INICIAR_TRAMITE("TR_INI"),
	/**
	 * INICIO DEL TRÁMITE (INCORRECTO).
	 */
	INICIAR_TRAMITE_INCORRECTO("TR_INK"),
	/**
	 * CARGA DEL TRÁMITE.
	 */
	CARGAR_TRAMITE("TR_CAR"),
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
	 * DEBUG SCRIPT. EVENTO GENERADO CUANDO SE GENERA UN DEBUG EN UN SCRIPT.
	 */
	DEBUG_SCRIPT("TR_SCR");

	/**
	 * Valor como string.
	 */
	private final String stringValueEvento;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
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
	 * @param text
	 *            Parámetro text
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
