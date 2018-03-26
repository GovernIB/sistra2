package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar los tipos de mensaje aviso.
 *
 * @author Indra
 *
 */
public enum TypeAvisoEntidad {
	/** TODOS LOS TRAMITES **/
	TODOS("TOD"),
	/** TRÁMITES ANÓNIMOS **/
	NOAUTENTICADOS("NAU"),
	/** TRÁMITES AUTENTICADOS. **/
	AUTENTICADOS("AUT"),
	/** TRÁMITES CON PAGO. **/
	PAGO("PAG"),
	/** TRÁMITES CON REGISTRO. **/
	REGISTRO("REG"),
	/** TRÁMITES CON FIRMA. **/
	FIRMA("FIR"),
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
	private TypeAvisoEntidad(final String pAmbito) {
		valor = pAmbito;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeAvisoEntidad fromString(final String text) {
		TypeAvisoEntidad respuesta = null;
		if (text != null) {
			for (final TypeAvisoEntidad b : TypeAvisoEntidad.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

	@Override
	public String toString() {
		return valor;
	}

}
