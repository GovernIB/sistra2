package es.caib.sistramit.core.api.model.system.types;

/**
 * Tipos de invalidaciones de cacheo.
 *
 * @author Indra
 *
 */
public enum TypeInvalidacion {

	/**
	 * Realiza invalidación de todas las cachés.
	 */
	COMPLETA("X"),
	/**
	 * Definición versión trámite.
	 */
	TRAMITE("T"),
	/**
	 * Dominio.
	 */
	DOMINIO("D"),
	/**
	 * Datos entidad.
	 */
	ENTIDAD("E"),
	/**
	 * Configuración global.
	 */
	CONFIGURACION("C");

	/**
	 * Valor como string.
	 */
	private final String stringValueInvalidacion;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeInvalidacion(final String value) {
		stringValueInvalidacion = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueInvalidacion;
	}

	/**
	 * Método para From string de la clase TypeAutenticacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type autenticacion
	 */
	public static TypeInvalidacion fromString(final String text) {
		TypeInvalidacion respuesta = null;
		if (text != null) {
			for (final TypeInvalidacion b : TypeInvalidacion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
