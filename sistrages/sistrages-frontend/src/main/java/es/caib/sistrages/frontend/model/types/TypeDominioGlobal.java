package es.caib.sistrages.frontend.model.types;

/**
 * Tipo para indicar el tipo de un dominio global.
 *
 * @author Indra
 *
 */
public enum TypeDominioGlobal {
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
	CONSULTA_REMOTA("R");

	private String valor;

	TypeDominioGlobal(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum(final TypeDominioGlobal tipo) {
		return tipo.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypeDominioGlobal toEnum(final String tipo) {
		TypeDominioGlobal retorno;
		if (tipo == null) {
			retorno = null;
		} else {
			switch (tipo) {
			case "B":
				retorno = TypeDominioGlobal.CONSULTA_BD;
				break;
			case "L":
				retorno = TypeDominioGlobal.LISTA_FIJA;
				break;
			case "R":
				retorno = TypeDominioGlobal.CONSULTA_REMOTA;
				break;
			default:
				retorno = null;
				break;
			}
		}
		return retorno;
	}

}
