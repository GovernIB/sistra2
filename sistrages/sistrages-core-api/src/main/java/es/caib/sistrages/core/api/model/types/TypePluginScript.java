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
	 * Plugin de avisos.
	 */
	PLUGIN_AVISOS,
	/**
	 * Plugin mensaje de validación.
	 */
	PLUGIN_MENSAJEVALIDACION,
	/**
	 * Plugin validaciones.
	 */
	PLUGIN_VALIDACIONES,
	/**
	 * Plugin validacion anexo.
	 */
	PLUGIN_VALIDACIONANEXO,
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
	RES_PARAMETROSINICIALES,
	/**
	 * Resultado datos iniciales formulario (exclusivo del flujo)
	 */
	RES_DATOSINICIALESFORMULARIO,
	/**
	 * Resultado personalizacion tramite (exclusivo dentro del flujo)
	 */
	RES_PERSONALIZACIONTRAMITE,
	/**
	 * Resultado parametros formulario (exclusivo dentro del flujo)
	 */
	RES_PARAMETROSFORMULARIO,
	/**
	 * Resultado modificacion formularios (exclusivo dentro del flujo)
	 */
	RES_MODIFICACIONFORMULARIOS,
	/**
	 * Resultado anexos dinámicos (exclusivo dentro del flujo)
	 */
	RES_ANEXOSDINAMICOS,
	/**
	 * Plugin de pago (exclusivo dentro del flujo)
	 */
	PLUGIN_PAGO,
	/**
	 * Resultado del pago (exclusivo dentro del flujo)
	 */
	RES_PAGO,
	/**
	 * Resultado firmantes (exclusivo dentro del flujo)
	 */
	RES_FIRMANTES,
	/**
	 * Resultado registro (exclusivo dentro del flujo)
	 */
	RES_REGISTRO,
	/**
	 * Resultado persona (exclusivo dentro del flujo)
	 */
	RES_PERSONA,
	/**
	 * Resultado plantilla info (exclusivo dentro del flujo)
	 */
	RES_PLANTILLAINFO,
	/**
	 * Resultado variable flujo (exclusivo dentro del flujo)
	 */
	RES_VARIABLEFLUJO,
	/**
	 * Plugin sesion formulario (exclusivo dentro de formulario)
	 */
	PLUGIN_SESIONFORMULARIO,
	/**
	 * Plugin sesion formulario (exclusivo dentro de formulario)
	 */
	PLUGIN_DATOSFORMULARIOS,
	/**
	 * Resultado valor campo (exclusivo dentro de formulario)
	 */
	RES_VALORCAMPO,
	/**
	 * Resultado estado campo (exclusivo dentro de formulario)
	 */
	RES_ESTADOCAMPO,
	/**
	 * Resultado valores posible (exclusivo dentro del formulario)
	 */
	RES_VALORESPOSIBLE;
}
