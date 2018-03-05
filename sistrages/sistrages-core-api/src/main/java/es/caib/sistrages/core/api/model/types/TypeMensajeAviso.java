package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar los tipos de mensaje aviso.
 *
 * @author Indra
 *
 */
public enum TypeMensajeAviso {
	/** TODOS LOS TRAMITES **/
	TODOS("TOD"),
	/** TRÁMITES ANÓNIMOS **/
	ANONIMOS("ANO"),
	/** TRÁMITES AUTENTICADOS. **/
	AUTENTICADOS("AUT"),
	/** TRÁMITES CON PAGO. **/
	PAGO("PAG"),
	/** TRÁMITES CON REGISTRO. **/
	REGISTRO("REG"),
	/** TRÁMITES CON FIRMA. **/
	FIRMA("FIR"),
	/** TRÁMITES POR ORGANISMO. **/
	ORGANISMO("ORG"),
	/** LISTA DE TRÁMITES. **/
	LISTA("LIS");

	/**
	 * Ambito nombre;
	 */
	private String valor;

	/**
	 * Constructor.
	 *
	 * @param pAmbito
	 *            Role name
	 */
	private TypeMensajeAviso(final String pAmbito) {
		valor = pAmbito;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeMensajeAviso fromString(final String text) {
		TypeMensajeAviso respuesta = null;
		if (text != null) {
			for (final TypeMensajeAviso b : TypeMensajeAviso.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static String toString(final TypeMensajeAviso ambito) {
		String respuesta = null;
		if (ambito != null) {
			respuesta = ambito.valor;
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

	/**
	 * Label para los selectItems.
	 *
	 * @return
	 */
	public String getLabel() {
		return "typeMensajeAviso." + this.valor;
	}
}
