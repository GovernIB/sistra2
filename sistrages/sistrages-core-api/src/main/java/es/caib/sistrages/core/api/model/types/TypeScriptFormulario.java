package es.caib.sistrages.core.api.model.types;

/**
 * Tipo de script para formulario interno.
 *
 * @author Indra
 *
 */
public enum TypeScriptFormulario implements TypeScript {
	/**
	 * Script autorrellenable.
	 */
	SCRIPT_AUTORELLENABLE,
	/**
	 * Script estado campo (solo lectura).
	 */
	SCRIPT_ESTADO,
	/**
	 * Script de validación de campo.
	 */
	SCRIPT_VALIDACION_CAMPO,
	/**
	 * Script de validación de página.
	 */
	SCRIPT_VALIDACION_PAGINA,
	/**
	 * Script de valores posibles selector.
	 */
	SCRIPT_VALORES_POSIBLES,
	/**
	 * Script de plantilla pdf dinamica.
	 */
	SCRIPT_PLANTILLA_PDF_DINAMICA;

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeScriptFormulario fromString(final String text) {
		TypeScriptFormulario respuesta = null;
		if (text != null) {
			for (final TypeScriptFormulario b : TypeScriptFormulario.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
