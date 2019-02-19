package es.caib.sistrages.core.api.util;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioComponenteSimple;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioPaginaSimple;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioSimple;
import es.caib.sistrages.core.api.model.comun.TramiteSimple;
import es.caib.sistrages.core.api.model.comun.TramiteSimpleFormulario;
import es.caib.sistrages.core.api.model.comun.TramiteSimplePaso;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypePaso;
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
		plugins.add(TypePluginScript.PLUGIN_SESIONFORMULARIO);
		plugins.add(TypePluginScript.PLUGIN_DOMINIOS);
		plugins.add(TypePluginScript.PLUGIN_DATOSFORMULARIO);
		plugins.add(TypePluginScript.PLUGIN_UTILS);
		plugins.add(TypePluginScript.PLUGIN_LOG);
		plugins.add(TypePluginScript.PLUGIN_MENSAJES);
		plugins.add(TypePluginScript.PLUGIN_ERROR);
		switch (tipoScript) {
		case SCRIPT_AUTORELLENABLE:
			plugins.add(0, TypePluginScript.DATOS_VALOR);
			break;
		case SCRIPT_ESTADO:
			plugins.add(0, TypePluginScript.DATOS_ESTADO);
			break;
		case SCRIPT_VALORES_POSIBLES:
			plugins.add(0, TypePluginScript.DATOS_VALORESPOSIBLES);
			break;
		case SCRIPT_VALIDACION_CAMPO:
			plugins.add(TypePluginScript.PLUGIN_VALIDACION);
			break;
		case SCRIPT_VALIDACION_PAGINA:
			plugins.add(TypePluginScript.PLUGIN_VALIDACION);
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
		plugins.add(TypePluginScript.PLUGIN_SESIONTRAMITACION);
		plugins.add(TypePluginScript.PLUGIN_DOMINIOS);
		if (tipoScript != TypeScriptFlujo.SCRIPT_PERSONALIZACION_TRAMITE
				&& tipoScript != TypeScriptFlujo.SCRIPT_PARAMETROS_INICIALES) {
			plugins.add(TypePluginScript.PLUGIN_FORMULARIOS);
		}
		plugins.add(TypePluginScript.PLUGIN_UTILS);
		plugins.add(TypePluginScript.PLUGIN_LOG);
		plugins.add(TypePluginScript.PLUGIN_MENSAJES);
		plugins.add(TypePluginScript.PLUGIN_ERROR);
		switch (tipoScript) {
		case SCRIPT_PARAMETROS_INICIALES:
			plugins.add(0, TypePluginScript.DATOS_PARAMETROSINICIALES);
			break;
		case SCRIPT_PERSONALIZACION_TRAMITE:
			plugins.add(TypePluginScript.PLUGIN_VALIDACION);
			plugins.add(0, TypePluginScript.DATOS_PERSONALIZACION);
			break;
		case SCRIPT_DATOS_INICIALES_FORMULARIO:
			plugins.add(0, TypePluginScript.DATOS_VALORESINICIALES);
			break;
		case SCRIPT_PARAMETROS_FORMULARIO:
			plugins.add(0, TypePluginScript.DATOS_PARAMETROSFORMULARIO);
			break;
		case SCRIPT_POSTGUARDAR_FORMULARIO:
			plugins.add(TypePluginScript.PLUGIN_VALIDACION);
			plugins.add(0, TypePluginScript.DATOS_FORMULARIOS);
			break;
		case SCRIPT_LISTA_DINAMICA_ANEXOS:
			plugins.add(0, TypePluginScript.DATOS_ANEXOSDINAMICOS);
			break;
		case SCRIPT_DATOS_PAGO:
			plugins.add(TypePluginScript.PLUGIN_PAGO);
			plugins.add(0, TypePluginScript.DATOS_PAGO);
			break;
		case SCRIPT_FIRMANTES:
			plugins.add(0, TypePluginScript.DATOS_FIRMANTES);
			break;
		case SCRIPT_PARAMETROS_REGISTRO:
			plugins.add(0, TypePluginScript.DATOS_REGISTRO);
			break;
		case SCRIPT_PRESENTADOR_REGISTRO:
			plugins.add(0, TypePluginScript.DATOS_PERSONA);
			break;
		case SCRIPT_REPRESENTADO_REGISTRO:
			plugins.add(0, TypePluginScript.DATOS_PERSONA);
			break;
		case SCRIPT_PLANTILLA_INFO:
			plugins.add(0, TypePluginScript.DATOS_PLANTILLA);
			break;
		case SCRIPT_VARIABLE_FLUJO:
			plugins.add(0, TypePluginScript.DATOS_VARIABLEFLUJO);
			break;
		case SCRIPT_VALIDAR_ANEXO:
			plugins.add(TypePluginScript.PLUGIN_ANEXO);
			plugins.add(TypePluginScript.PLUGIN_VALIDACION);
			break;
		case SCRIPT_PERMITIR_REGISTRO:
			plugins.add(TypePluginScript.PLUGIN_VALIDACION);
			break;
		default:
			break;
		}
		return plugins;
	}

	/**
	 * Devuelve la lista de formularios según el paso actual, la lista de pasos y el
	 * tipo de script. Si no tiene idPasoActual es porque no es un paso sino un
	 * script del trámite (script personalización y script parámetros iniciales).
	 *
	 * El funcionamiento es el siguiente: <br />
	 * - Recorre todos los pasos del trámite. - Todo formulario que encuentra, lo
	 * añade. <br />
	 * - Si se encuentra en el paso actual, se para y no añade más.<br />
	 * - Si se encuentra el formulario actual, se para y no recorre más (tampoco
	 * recorre más pasos).<br />
	 * - El script de datos iniciales formulario es 'especial' y sólo puede añadir
	 * el formulario actual (sin importar si hay alguno antes) si estamos en el paso
	 * que toca.
	 *
	 * @param list
	 *
	 * @return
	 */
	public static List<Long> getFormulariosFlujo(final TramiteSimple tramiteSimple, final String idFormulario,
			final String idPasoActual, final TypeScriptFlujo tipoScript, final String idFormularioInterno) {

		final List<Long> formularios = new ArrayList<>();
		if (tramiteSimple != null && idPasoActual != null) {
			for (final TramiteSimplePaso paso : tramiteSimple.getPasos()) {

				if (tipoScript == TypeScriptFlujo.SCRIPT_DATOS_INICIALES_FORMULARIO && idPasoActual != null
						&& paso.getCodigo().compareTo(Long.valueOf(idPasoActual)) == 0 && idFormularioInterno != null) {
					formularios.add(Long.valueOf(idFormularioInterno));
					break;
				} else if (paso.isTipoPasoRellenar() && paso.getFormularios() != null) {
					for (final TramiteSimpleFormulario formulario : paso.getFormularios()) {
						formularios.add(formulario.getIdFormularioInterno());
						if (idFormulario != null && formulario.getCodigo().compareTo(Long.valueOf(idFormulario)) == 0) {
							break;
						}
					}
				}

				if (idPasoActual != null && paso.getCodigo().compareTo(Long.valueOf(idPasoActual)) == 0) {
					break;
				}
			}
		}
		return formularios;
	}

	/**
	 * Devuelve la lista de formularios según el paso actual, la lista de pasos y el
	 * tipo de script.
	 *
	 * @param list
	 *
	 * @return
	 */
	public static DisenyoFormularioSimple getFormulariosFormulario(final DisenyoFormulario formulario) {

		final DisenyoFormularioSimple form = new DisenyoFormularioSimple();
		if (formulario != null && formulario.getPaginas() != null) {
			final List<DisenyoFormularioPaginaSimple> paginas = new ArrayList<>();
			for (final PaginaFormulario pagina : formulario.getPaginas()) {

				final DisenyoFormularioPaginaSimple pagSimple = new DisenyoFormularioPaginaSimple();
				final List<DisenyoFormularioComponenteSimple> compSimples = new ArrayList<>();
				if (pagina.getLineas() != null) {
					for (final LineaComponentesFormulario linea : pagina.getLineas()) {

						if (linea.getComponentes() != null) {
							for (final ComponenteFormulario componente : linea.getComponentes()) {
								if (isTipoEditable(componente.getTipo())) {
									final DisenyoFormularioComponenteSimple compSimple = new DisenyoFormularioComponenteSimple();
									compSimple.setCodigo(componente.getCodigo());
									compSimple.setIdentificador(componente.getIdComponente());
									compSimple.setTipo(componente.getTipo());
									compSimples.add(compSimple);
								}
							}
						}
					}
				}
				pagSimple.setComponentes(compSimples);
				paginas.add(pagSimple);
			}
			form.setPaginas(paginas);
		}
		return form;
	}

	/**
	 * Devuelve la lista de formularios según el paso actual, la lista de pasos y el
	 * tipo de script.
	 *
	 * @param list
	 *
	 * @return
	 */
	public static DisenyoFormularioSimple getFormulariosFormulario(final DisenyoFormularioSimple formulario,
			final Long idComponente, final Long idPagina) {

		boolean salir = false;
		final DisenyoFormularioSimple form = new DisenyoFormularioSimple();
		if (formulario != null && formulario.getPaginas() != null) {
			final List<DisenyoFormularioPaginaSimple> paginas = new ArrayList<>();
			for (final DisenyoFormularioPaginaSimple pagina : formulario.getPaginas()) {

				final DisenyoFormularioPaginaSimple pagSimple = new DisenyoFormularioPaginaSimple();
				final List<DisenyoFormularioComponenteSimple> compSimples = new ArrayList<>();
				if (pagina.getComponentes() != null) {
					for (final DisenyoFormularioComponenteSimple componente : pagina.getComponentes()) {

						if (isTipoEditable(componente.getTipo())) {
							final DisenyoFormularioComponenteSimple compSimple = new DisenyoFormularioComponenteSimple();
							compSimple.setCodigo(componente.getCodigo());
							compSimple.setIdentificador(componente.getIdentificador());
							compSimple.setTipo(componente.getTipo());
							compSimples.add(compSimple);
						}

						// Si estamos en el componente, hay que salir
						if (idComponente != null && componente.getCodigo().compareTo(idComponente) == 0) {
							salir = true;
							break;
						}
					}
				}

				if (salir) {
					break;
				}
				pagSimple.setComponentes(compSimples);
				paginas.add(pagSimple);

				// Si estamos en la página que toca, hay que salir
				if (idPagina != null && pagina.getCodigo().compareTo(idPagina) == 0) {
					break;
				}
			}
			form.setPaginas(paginas);

		}
		return form;
	}

	/**
	 * Comprueba si es tipo editable
	 *
	 * @param tipo
	 * @return
	 */
	private static boolean isTipoEditable(final TypeObjetoFormulario tipo) {
		return tipo == TypeObjetoFormulario.CAMPO_TEXTO || tipo == TypeObjetoFormulario.CHECKBOX
				|| tipo == TypeObjetoFormulario.SELECTOR;
	}

	/**
	 * Devuelve un objeto tramite Simple para poner en la mochila.
	 *
	 * @param tramiteVersion
	 * @return
	 */
	public static TramiteSimple getTramiteSimple(final TramiteVersion tramiteVersion) {
		final TramiteSimple tramite = new TramiteSimple();
		if (tramiteVersion != null && tramiteVersion.getListaPasos() != null) {
			final List<TramiteSimplePaso> pasos = new ArrayList<>();
			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {

				final TramiteSimplePaso pasoSimple = new TramiteSimplePaso();
				pasoSimple.setCodigo(paso.getCodigo());
				pasoSimple.setTipo(TypePaso.fromPaso(paso));
				if (paso instanceof TramitePasoRellenar) {
					pasoSimple.setFormularios(getTramiteSimpleFormularios((TramitePasoRellenar) paso));
				}
				pasos.add(pasoSimple);

			}
			tramite.setPasos(pasos);
		}

		return tramite;
	}

	/**
	 * Devuelve la lista de formularios simplificado.
	 *
	 * @param pasoRellenar
	 * @return
	 */
	private static List<TramiteSimpleFormulario> getTramiteSimpleFormularios(final TramitePasoRellenar pasoRellenar) {

		final List<TramiteSimpleFormulario> formularios = new ArrayList<>();

		if (pasoRellenar.getFormulariosTramite() != null) {
			for (final FormularioTramite forms : pasoRellenar.getFormulariosTramite()) {
				final TramiteSimpleFormulario tramiteSimpleFormulario = new TramiteSimpleFormulario();
				tramiteSimpleFormulario.setCodigo(forms.getCodigo());
				tramiteSimpleFormulario.setIdFormularioInterno(forms.getIdFormularioInterno());
				formularios.add(tramiteSimpleFormulario);
			}
		}
		return formularios;
	}

}
