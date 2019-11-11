package es.caib.sistramit.core.service.model.script.types;

/**
 * Tipo de script para flujo.
 *
 * @author Indra
 *
 */
public enum TypeScriptFlujo implements TypeScript {
	/**
	 * Script de parametros iniciales del trámite.
	 */
	SCRIPT_PARAMETROS_INICIALES,
	/**
	 * Script de personalización de trámite.
	 */
	SCRIPT_PERSONALIZACION_TRAMITE,
	/**
	 * Script de cálculo de dependencia de documento.
	 */
	SCRIPT_DEPENDENCIA_DOCUMENTO,
	/**
	 * Script de definición de firmantes.
	 */
	SCRIPT_FIRMANTES,
	/**
	 * Script de datos iniciales de un formulario.
	 */
	SCRIPT_DATOS_INICIALES_FORMULARIO,
	/**
	 * Script de parámetros de apertura de un formulario.
	 */
	SCRIPT_PARAMETROS_FORMULARIO,
	/**
	 * Script de post guardar de un formulario.
	 */
	SCRIPT_POSTGUARDAR_FORMULARIO,
	/**
	 * Script de validación de un anexo. Tiene como variables específicas:
	 * nombreFichero y datosFichero, en las que se pasa el anexo a validar.
	 */
	SCRIPT_VALIDAR_ANEXO,
	/**
	 * Script para indicar lista dinámica de anexos.
	 */
	SCRIPT_LISTA_DINAMICA_ANEXOS,
	/**
	 * Script para indicar los datos de un pago.
	 */
	SCRIPT_DATOS_PAGO,
	/**
	 * Script para indicar dinámicamente los parámetros de registro.
	 */
	SCRIPT_PARAMETROS_REGISTRO,
	/**
	 * Script para indicar quién debe presentar el registro.
	 */
	SCRIPT_PRESENTADOR_REGISTRO,
	/**
	 * Script para indicar si se actua en nombre de un representado.
	 */
	SCRIPT_REPRESENTACION_REGISTRO,
	/**
	 * Script para establecer una validación de si se permite registrar.
	 */
	SCRIPT_PERMITIR_REGISTRO,
	/**
	 * Script para establecer avisos.
	 */
	SCRIPT_AVISO,
	/**
	 * Script para indicar la navegación entre pasos de un trámite personalizado.
	 */
	SCRIPT_NAVEGACION_PASO,
	/**
	 * Script para indicar los valores a mostrar en la plantilla del paso de
	 * información para trámite personalizado.
	 */
	SCRIPT_PLANTILLA_INFO,
	/**
	 * Script para añadir una variable en el flujo para trámite personalizado.
	 */
	SCRIPT_VARIABLE_FLUJO;

}
