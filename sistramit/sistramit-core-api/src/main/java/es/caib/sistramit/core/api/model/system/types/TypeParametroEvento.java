package es.caib.sistramit.core.api.model.system.types;

/**
 * Parámetros estándar para un evento de auditoria.
 *
 * @author Indra
 *
 */
public enum TypeParametroEvento {

	/**
	 * Autenticación.
	 */
	AUTENTICACION("AUT"),
	/**
	 * Sistema operativo.
	 */
	SISTEMA_OPERATIVO("SO"),
	/**
	 * Versión sistema operativo.
	 */
	VERSION_SISTEMA_OPERATIVO("VSO"),
	/**
	 * Navegador.
	 */
	NAVEGADOR("NA"),
	/**
	 * Versión navegador.
	 */
	VERSION_NAVEGADOR("VNA"),
	/**
	 * Para inicio tramites incorrectos: url inicio.
	 */
	URL_INICIO("URL"),
	/**
	 * Número registro (evento envio/registro).
	 */
	NUMERO_REGISTRO("NUMREG"),
	/**
	 * Valoración trámite: Código SIA procedimiento.
	 */
	VALORACION_SIA("VALSIA"),
	/**
	 * Valoración trámite: Código procedimiento del Catálogo de Servicios.
	 */
	VALORACION_PROCEDIMIENTO("VALCPPR"),
	/**
	 * Valoración trámite: Código trámite del Catálogo de Servicios.
	 */
	VALORACION_TRAMITE("VALCPTR"),
	/**
	 * Valoración trámite: Indica si es servicio (true/false).
	 */
	VALORACION_ES_SERVICIO("VALCPSR"),
	/**
	 * Valoración trámite: Código trámite/versión.
	 */
	VALORACION_TRAMITE_ELECTRONICO("VALTRVR"),
	/**
	 * Valoración trámite: puntuación.
	 */
	VALORACION_PUNTUACION("VALPUN"),
	/**
	 * Valoración trámite: lista problemas separados por ";".
	 */
	VALORACION_PROBLEMAS("VALPROB"),
	/**
	 * Valoración trámite: observaciones.
	 */
	VALORACION_OBSERVACIONES("VALOBS");

	/**
	 * Valor como string.
	 */
	private final String stringValueParametroEvento;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeParametroEvento(final String value) {
		stringValueParametroEvento = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueParametroEvento;
	}

	/**
	 * Método para From string de la clase TypeAutenticacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type autenticacion
	 */
	public static TypeParametroEvento fromString(final String text) {
		TypeParametroEvento respuesta = null;
		if (text != null) {
			for (final TypeParametroEvento b : TypeParametroEvento.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
