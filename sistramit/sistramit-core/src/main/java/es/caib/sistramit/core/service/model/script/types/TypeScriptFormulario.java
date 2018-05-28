package es.caib.sistramit.core.service.model.script.types;

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
	 * Script estado campo.
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

}
