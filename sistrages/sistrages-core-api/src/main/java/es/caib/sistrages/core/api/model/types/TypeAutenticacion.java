package es.caib.sistrages.core.api.model.types;

/**
 * Indica el tipo de autenticación.
 * <ul>
 * <li>Todos (TOD)</li>
 * <li>Certificado (CER)</li>
 * <li>Clave Pin (PIN)</li>
 * <li>Clave Permanente (PER)</li>
 * </ul>
 *
 * @author Indra
 *
 */
public enum TypeAutenticacion {

	/** Propiedades del formulario **/
	CERTIFICADO("CER"), CLAVE_PIN("PIN"), CLAVE_PERMANENTE("PER");

	private String valor;

	TypeAutenticacion(final String iValor) {
		this.valor = iValor;
	}

	public static TypeAutenticacion fromString(final String iValor) {
		TypeAutenticacion retorno = null;
		for (final TypeAutenticacion tipo : TypeAutenticacion.values()) {
			if (tipo != null && tipo.toString().equals(iValor)) {
				retorno = tipo;
				break;
			}
		}

		return retorno;
	}

	@Override
	public String toString() {
		return this.valor;
	}
}
