package es.caib.sistrages.core.api.model.types;

/**
 * Enum para indicar el tipo de plugin de scripts.
 *
 * @author Indra
 *
 */
public enum TypePluginScript {
	/**
	 * Plugin de log.
	 */
	PLUGIN_LOG,
	/**
	 * Plugin de error.
	 */
	PLUGIN_ERROR,
	/**
	 * Plugin de dominios.
	 */
	PLUGIN_DOMINIOS,
	/**
	 * Plugin de validacion.
	 */
	PLUGIN_VALIDACION,
	/**
	 * Plugin mensajes.
	 */
	PLUGIN_MENSAJES,
	/**
	 * Plugin utils.
	 */
	PLUGIN_UTILS,
	/**
	 * Plugin anexo.
	 */
	PLUGIN_ANEXO,
	/**
	 * Plugin sesion tramitacion (exclusivo del flujo)
	 */
	PLUGIN_SESIONTRAMITACION,
	/**
	 * Plugin formularios (exclusivo dentro del flujo)
	 */
	PLUGIN_FORMULARIOS,
	/**
	 * Resultado parametros iniciales (exclusivo dentro del flujo)
	 */
	DATOS_PARAMETROSINICIALES,
	/**
	 * Resultado datos iniciales formulario (exclusivo del flujo)
	 */
	DATOS_VALORESINICIALES,
	/**
	 * Resultado personalizacion tramite (exclusivo dentro del flujo)
	 */
	DATOS_PERSONALIZACION,
	/**
	 * Resultado parametros formulario (exclusivo dentro del flujo)
	 */
	DATOS_PARAMETROSFORMULARIO,
	/**
	 * Resultado modificacion formularios (exclusivo dentro del flujo)
	 */
	DATOS_FORMULARIOS,
	/**
	 * Resultado anexos dinámicos (exclusivo dentro del flujo)
	 */
	DATOS_ANEXOSDINAMICOS,
	/**
	 * Plugin de pago (exclusivo dentro del flujo)
	 */
	PLUGIN_PAGO,
	/**
	 * Resultado del pago (exclusivo dentro del flujo)
	 */
	DATOS_PAGO,
	/**
	 * Resultado firmantes (exclusivo dentro del flujo)
	 */
	DATOS_FIRMANTES,
	/**
	 * Resultado registro (exclusivo dentro del flujo)
	 */
	DATOS_REGISTRO,
	/**
	 * Resultado persona (exclusivo dentro del flujo)
	 */
	DATOS_PERSONA,
	/**
	 * Resultado plantilla info (exclusivo dentro del flujo)
	 */
	DATOS_PLANTILLA,
	/**
	 * Resultado variable flujo (exclusivo dentro del flujo)
	 */
	DATOS_VARIABLEFLUJO,
	/**
	 * Plugin sesion formulario (exclusivo dentro de formulario)
	 */
	PLUGIN_SESIONFORMULARIO,
	/**
	 * Plugin sesion formulario (exclusivo dentro de formulario)
	 */
	PLUGIN_DATOSFORMULARIO,
	/**
	 * Resultado valor campo (exclusivo dentro de formulario)
	 */
	DATOS_VALOR,
	/**
	 * Resultado estado campo (exclusivo dentro de formulario)
	 */
	DATOS_ESTADO,
	/**
	 * Resultado valores posible (exclusivo dentro del formulario)
	 */
	DATOS_VALORESPOSIBLES;
}
