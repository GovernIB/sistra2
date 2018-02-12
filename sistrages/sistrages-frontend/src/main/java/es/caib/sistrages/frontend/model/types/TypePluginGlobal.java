package es.caib.sistrages.frontend.model.types;

/**
 * Enum para indicar el tipo de plugin global.
 *
 * @author Indra
 *
 */
public enum TypePluginGlobal {
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
	DOMINIO_REMOTA("D"),
	/**
	 * Plugin de firma.
	 */
	FIRMA("F");

	private String valor;

	TypePluginGlobal(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum(final TypePluginGlobal tipo) {
		return tipo.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypePluginGlobal toEnum(final String tipo) {
		TypePluginGlobal retorno;
		if (tipo == null) {
			retorno = null;
		} else {
			switch (tipo) {
			case "L":
				retorno = TypePluginGlobal.LOGIN;
				break;
			case "R":
				retorno = TypePluginGlobal.REPRESENTACION;
				break;
			case "D":
				retorno = TypePluginGlobal.DOMINIO_REMOTA;
				break;
			case "F":
				retorno = TypePluginGlobal.FIRMA;
				break;
			default:
				retorno = null;
				break;
			}
		}
		return retorno;
	}

}
