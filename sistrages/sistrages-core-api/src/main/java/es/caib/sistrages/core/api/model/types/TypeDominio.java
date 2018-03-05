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
	public TypeDominio toEnum(final String tipo) {
		TypeDominio retorno;
		if (tipo == null) {
			retorno = null;
		} else {
			switch (tipo) {
			case "B":
				retorno = TypeDominio.CONSULTA_BD;
				break;
			case "L":
				retorno = TypeDominio.LISTA_FIJA;
				break;
			case "R":
				retorno = TypeDominio.CONSULTA_REMOTA;
				break;
			case "F":
				retorno = TypeDominio.FUENTE_DATOS;
				break;
			default:
				retorno = null;
				break;
			}
		}
		return retorno;
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
