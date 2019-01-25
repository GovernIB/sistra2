package es.caib.sistrages.core.api.util;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.types.TypePluginScript;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;

/**
 * Util de scripts.
 *
 * @author Indra
 *
 */
public class UtilScripts {

	/** Indica si es de typeScript tipo formulario. **/
	public static final String TIPO_FORMULARIO = "FORM";
	/** Indica si es de typeScript tipo flujo. **/
	public static final String TIPO_FLUJO = "FLUJ";

	/**
	 * Constructor vacio
	 */
	private UtilScripts() {
		// Vacio
	}

	/**
	 * Devuelve los tipos de plugins para script de tipo formulario.
	 *
	 * @param tipoScript
	 * @return
	 */
	public static List<TypePluginScript> getPluginsScript(final TypeScriptFormulario tipoScript) {
		final List<TypePluginScript> plugins = new ArrayList<>();
		plugins.add(TypePluginScript.PLUGIN_VALIDACIONES);
		plugins.add(TypePluginScript.PLUGIN_ERROR);
		plugins.add(TypePluginScript.PLUGIN_MENSAJEVALIDACION);
		plugins.add(TypePluginScript.PLUGIN_LOG);
		plugins.add(TypePluginScript.PLUGIN_DOMINIOS);
		plugins.add(TypePluginScript.PLUGIN_SESIONFORMULARIO);
		plugins.add(TypePluginScript.PLUGIN_DATOSFORMULARIOS);
		switch (tipoScript) {
		case SCRIPT_AUTORELLENABLE:
			plugins.add(TypePluginScript.RES_VALORCAMPO);
			break;
		case SCRIPT_ESTADO:
			plugins.add(TypePluginScript.RES_ESTADOCAMPO);
			break;
		case SCRIPT_VALORES_POSIBLES:
			plugins.add(TypePluginScript.RES_VALORESPOSIBLE);
			break;
		case SCRIPT_VALIDACION_CAMPO:
			plugins.add(TypePluginScript.PLUGIN_AVISOS);
			break;
		case SCRIPT_VALIDACION_PAGINA:
			plugins.add(TypePluginScript.PLUGIN_AVISOS);
			break;
		default:
			break;
		}

		return plugins;
	}

	/**
	 * Devuelve los tipos de plugin para script de tipo flujo.
	 *
	 * @param tipoScript
	 * @return
	 */
	public static List<TypePluginScript> getPluginsScript(final TypeScriptFlujo tipoScript) {
		final List<TypePluginScript> plugins = new ArrayList<>();
		plugins.add(TypePluginScript.PLUGIN_VALIDACIONES);
		plugins.add(TypePluginScript.PLUGIN_ERROR);
		plugins.add(TypePluginScript.PLUGIN_MENSAJEVALIDACION);
		plugins.add(TypePluginScript.PLUGIN_LOG);
		plugins.add(TypePluginScript.PLUGIN_DOMINIOS);
		plugins.add(TypePluginScript.PLUGIN_SESIONTRAMITACION);
		plugins.add(TypePluginScript.PLUGIN_FORMULARIOS);
		switch (tipoScript) {
		case SCRIPT_PARAMETROS_INICIALES:
			plugins.add(TypePluginScript.RES_PARAMETROSINICIALES);
			break;
		case SCRIPT_PERSONALIZACION_TRAMITE:
			plugins.add(TypePluginScript.PLUGIN_AVISOS);
			plugins.add(TypePluginScript.RES_PERSONALIZACIONTRAMITE);
			break;
		case SCRIPT_DATOS_INICIALES_FORMULARIO:
			plugins.add(TypePluginScript.RES_DATOSINICIALESFORMULARIO);
			break;
		case SCRIPT_PARAMETROS_FORMULARIO:
			plugins.add(TypePluginScript.RES_PARAMETROSFORMULARIO);
			break;
		case SCRIPT_POSTGUARDAR_FORMULARIO:
			plugins.add(TypePluginScript.PLUGIN_AVISOS);
			plugins.add(TypePluginScript.RES_MODIFICACIONFORMULARIOS);
			break;
		case SCRIPT_LISTA_DINAMICA_ANEXOS:
			plugins.add(TypePluginScript.RES_ANEXOSDINAMICOS);
			break;
		case SCRIPT_DATOS_PAGO:
			plugins.add(TypePluginScript.PLUGIN_PAGO);
			plugins.add(TypePluginScript.RES_PAGO);
			break;
		case SCRIPT_FIRMANTES:
			plugins.add(TypePluginScript.RES_FIRMANTES);
			break;
		case SCRIPT_PARAMETROS_REGISTRO:
			plugins.add(TypePluginScript.RES_REGISTRO);
			break;
		case SCRIPT_PRESENTADOR_REGISTRO:
			plugins.add(TypePluginScript.RES_PERSONA);
			break;
		case SCRIPT_REPRESENTADO_REGISTRO:
			plugins.add(TypePluginScript.RES_PERSONA);
			break;
		case SCRIPT_PLANTILLA_INFO:
			plugins.add(TypePluginScript.RES_PLANTILLAINFO);
			break;
		case SCRIPT_VARIABLE_FLUJO:
			plugins.add(TypePluginScript.RES_VARIABLEFLUJO);
			break;
		case SCRIPT_VALIDAR_ANEXO:
			plugins.add(TypePluginScript.PLUGIN_VALIDACIONANEXO);
			plugins.add(TypePluginScript.PLUGIN_AVISOS);
			break;
		case SCRIPT_PERMITIR_REGISTRO:
			plugins.add(TypePluginScript.PLUGIN_AVISOS);
			break;
		default:
			break;
		}
		return plugins;
	}

	/**
	 * Devuelve la lista de formularios según el paso actual, la lista de pasos y el
	 * tipo de script.
	 *
	 * @param list
	 *
	 * @return
	 */
	public static List<Long> getFormularios(final List<TramitePaso> pasos) {
		final List<Long> formularios = new ArrayList<>();
		if (pasos != null) {
			for (final TramitePaso paso : pasos) {
				if (paso instanceof TramitePasoRellenar) {
					final TramitePasoRellenar pasoRellenar = (TramitePasoRellenar) paso;
					if (pasoRellenar.getFormulariosTramite() != null) {
						for (final FormularioTramite formulario : pasoRellenar.getFormulariosTramite()) {
							if (formulario != null && formulario.getCodigo() != null) {
								formularios.add(formulario.getIdFormularioInterno());
							}
						}
					}
				}
			}
		}
		return formularios;
	}
}
