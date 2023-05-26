package es.caib.sistrahelp.frontend.model.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tipo para indicar el estado de una incidencia.
 *
 * @author Indra
 *
 */
public enum TypeEstadoIncidencia {
	/**
	 * PENDIENTE.
	 */
	PENDIENTE("P"),
	/**
	 * REVISION.
	 */
	REVISION("R"),
	/**
	 * FINALIZADA.
	 */
	FINALIZADA("F");
	/**
	 * Valor como string.
	 */
	private final String stringValueEvento;

	/**
	 * Constructor.
	 *
	 * @param value Valor como string.
	 */
	private TypeEstadoIncidencia(final String value) {
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
	public static TypeEstadoIncidencia fromString(final String text) {
		TypeEstadoIncidencia respuesta = null;
		if (text != null) {
			for (final TypeEstadoIncidencia b : TypeEstadoIncidencia.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
