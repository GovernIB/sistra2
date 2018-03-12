package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar el tipo de un dominio global.
 *
 * @author Indra
 *
 */
public enum TypeDominio {
	/**
	 * Consulta de BBDD.
	 */
	CONSULTA_BD("B"),
	/**
	 * Lista fija de valores.
	 */
	LISTA_FIJA("L"),
	/**
	 * Consulta remota.
	 */
	CONSULTA_REMOTA("R"),
	/**
	 * Fuente de datos.
	 */
	FUENTE_DATOS("F");

	private String valor;

	TypeDominio(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypeDominio tipo) {
		return tipo.valor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum() {
		return this.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypeDominio toEnum(final String text) {
		TypeDominio respuesta = null;
		if (text != null) {
			for (final TypeDominio b : TypeDominio.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return valor;
	}

}
