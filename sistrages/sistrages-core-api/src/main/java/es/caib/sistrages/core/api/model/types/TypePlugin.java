package es.caib.sistrages.core.api.model.types;

/**
 * Enum para indicar el tipo de plugin global.
 *
 * @author Indra
 *
 */
public enum TypePlugin {
	/**
	 * Plugin de login
	 */
	LOGIN("L"),
	/**
	 * Plugin de representacion
	 */
	REPRESENTACION("R"),
	/**
	 * Plugin de dominio remoto
	 */
	DOMINIO_REMOTO("D"),
	/**
	 * Plugin de firma.
	 */
	FIRMA("F");

	private String valor;

	TypePlugin(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum(final TypePlugin tipo) {
		return tipo.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypePlugin toEnum(final String tipo) {
		TypePlugin retorno;
		if (tipo == null) {
			retorno = null;
		} else {
			switch (tipo) {
			case "L":
				retorno = TypePlugin.LOGIN;
				break;
			case "R":
				retorno = TypePlugin.REPRESENTACION;
				break;
			case "D":
				retorno = TypePlugin.DOMINIO_REMOTO;
				break;
			case "F":
				retorno = TypePlugin.FIRMA;
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
