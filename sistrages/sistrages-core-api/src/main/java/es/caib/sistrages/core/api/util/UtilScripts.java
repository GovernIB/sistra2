package es.caib.sistrages.core.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Script;
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
import es.caib.sistrages.core.api.model.types.TypeScriptSeccionReutilizable;

/**
 * Util de scripts.
 *
 * @author Indra
 *
 */
public class UtilScripts {

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
	public static List<TypePluginScript> getPluginsScript(final TypeScriptSeccionReutilizable tipoScript) {
		final List<TypePluginScript> plugins = new ArrayList<>();
		plugins.add(TypePluginScript.PLUGIN_SESIONTRAMITACION);;
		plugins.add(TypePluginScript.PLUGIN_DOMINIOS);
		plugins.add(TypePluginScript.PLUGIN_FORMULARIOS);
		plugins.add(TypePluginScript.PLUGIN_UTILS);
		plugins.add(TypePluginScript.PLUGIN_LOG);
		plugins.add(TypePluginScript.PLUGIN_MENSAJES);
		plugins.add(TypePluginScript.PLUGIN_ERROR);
		switch (tipoScript) {
		case CARGA_DATOS_INICIAL:
			plugins.add(0, TypePluginScript.DATOS_VALORESINICIALES);
			break;
		default:
			break;
		}

		return plugins;
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
			plugins.add(0, TypePluginScript.DATOS_PERSONALIZACION);
			break;
		case SCRIPT_DEBE_SABER:
			plugins.add(0, TypePluginScript.DATOS_INSTRUCCIONES);
			break;
		case SCRIPT_DATOS_INICIALES_FORMULARIO:
			plugins.add(0, TypePluginScript.DATOS_VALORESINICIALES);
			break;
		case SCRIPT_PARAMETROS_FORMULARIO:
			plugins.add(0, TypePluginScript.DATOS_PARAMETROSFORMULARIO);
			break;
		case SCRIPT_POSTGUARDAR_FORMULARIO:
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
		case SCRIPT_REPRESENTACION_REGISTRO:
			plugins.add(0, TypePluginScript.DATOS_REPRESENTACION);
			break;
		case SCRIPT_PLANTILLA_INFO:
			plugins.add(0, TypePluginScript.DATOS_PLANTILLA);
			break;
		case SCRIPT_VARIABLE_FLUJO:
			plugins.add(0, TypePluginScript.DATOS_VARIABLEFLUJO);
			break;
		case SCRIPT_VALIDAR_ANEXO:
			plugins.add(TypePluginScript.PLUGIN_ANEXO);
			break;
		case SCRIPT_AVISO:
			plugins.add(0, TypePluginScript.DATOS_AVISO);
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
						final boolean mismoFormulario = idFormulario != null
								&& formulario.getCodigo().compareTo(Long.valueOf(idFormulario)) == 0;
						// Si es tipo script dependencia documento, solo salen los formularios
						// anteriores al actual (el cual, no se debe agregar)
						if (tipoScript == TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO && mismoFormulario) {
							break;
						}

						formularios.add(formulario.getIdFormularioInterno());
						if (mismoFormulario) {
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
	 * Obtiene el valor de tramiteSimpleFormularios.
	 *
	 * @param pasoRellenar the paso rellenar
	 * @return el valor de tramiteSimpleFormularios
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

	/**
	 * Buscar comentarios script.
	 *
	 * @param script the script
	 * @return true, if successful
	 */
	public static boolean buscarComentariosScript(String script) {
		boolean warn = false;
		boolean existe = comentarioSimple(script);
		if (existe) {
			return true;
		}
		String contenidoScript = añadirFinComentario(script);
		Pattern regex = Pattern.compile("\\/\\*(.*?)(PLUGIN_DATOSFORMULARIO\\.get)(.*?)\\*\\/");
		// Quitamos todos los espacios en blanco y saltos de línea
		String scriptSearch = contenidoScript.replaceAll("\\r*\\n", "");
		scriptSearch = scriptSearch.replaceAll("\\s", "");
		final Matcher matcher = regex.matcher(scriptSearch);

		if (matcher.find()) {
			warn = true;
		}

		return warn;
	}

	/**
	 * Comentario simple.
	 *
	 * @param script the script
	 * @return true, if successful
	 */
	private static boolean comentarioSimple(String script) {
		boolean existe = false;
		Pattern regex = Pattern.compile("\\/\\/(.*?)(PLUGIN_DATOSFORMULARIO\\.get)(.*?)$");

		String[] arrayScript = script.split("\n");
		for (int x = 0; x < arrayScript.length; x++) {
			final Matcher matcher = regex.matcher(arrayScript[x]);
			if (matcher.find()) {
				return true;
			}

		}

		return existe;
	}

	/**
	 * Añadir fin comentario.
	 *
	 * @param script the script
	 * @return the string
	 */
	private static String añadirFinComentario(String script) {
		int iniComent = 0;
		int finComent = 0;
		String contenidoScript = script;
		final List<String> deps = new ArrayList<>();

		Pattern regex = Pattern.compile("\\/\\*");
		Pattern regex1 = Pattern.compile("\\*\\/");

		final Matcher matcher = regex.matcher(script);
		final Matcher matcher1 = regex1.matcher(script);

		while (matcher.find()) {
			iniComent++;
		}
		while (matcher1.find()) {
			finComent++;
		}

		if (iniComent > finComent) {
			contenidoScript = script + "*/";
		}

		return contenidoScript;

	}

//	public static String extraerComentariosOld(String texto) {
//		if (texto == null || texto.isEmpty()) {
//			return texto;
//		}
//
//		if (!texto.contains("//") && !texto.contains("/*")) {
//			return "";
//		}
//		//Calculamos las posiciones de comentario.
//		StringBuilder comentario = new StringBuilder();
//
//		//ComentarioActivo indica que está abierto '/*' y tenemos que buscar el cierre '*/'
//		boolean comentarioActivo = false;
//		int posDblBarra = -1;
//		int posBarraAst = -1;
//		for (String text : texto.split("\n")) {
//
//			//Vemos si hay comentarios en el texto
//			if (comentarioActivo) {
//				posBarraAst = text.indexOf("*/");
//			} else {
//				posBarraAst = text.indexOf("/*");
//			}
//			posDblBarra = text.indexOf("//");
//
//
//
//			if (comentarioActivo) {
//				if (posBarraAst == -1) {
//					comentario.append( text );
//					continue;
//				}
//
//				//Si se cierra el comentario, puede que este la doble barra después también. Algo asi como:
//				// ESTO_ES_UN_COMENTARIO_Y_VOY_A_CERRARLO */ AHORA IRÍA CÓDIGO Y VUELVO A PONER COMENTARIO // ESTO_ES_TEXTO_COMENTADO
//				comentario.append(texto.substring(0,posBarraAst));
//				if (posDblBarra > posBarraAst) {
//					comentario.append( texto.substring(posDblBarra) );
//				}
//
//				//Deja de estar activo el modo comentario
//				comentarioActivo = false;
//			} else {
//
//				if (posDblBarra >= 0 || posBarraAst >= 0) {
//					if (posBarraAst != -1) {
//						if (posDblBarra == -1 || posBarraAst < posDblBarra) {
//							comentario.append( text.substring(posBarraAst) );
//							comentarioActivo = true;
//						} else {
//							comentario.append( text.substring(posDblBarra) );
//						}
//					} else {
//						comentario.append( text.substring(posDblBarra) );
//					}
//				}
//			}
//
//
//
//		}
//
//		return comentario.toString();
//	}

	public static String extraerComentarios(String texto) {
		if (texto == null || texto.isEmpty()) {
			return texto;
		}

		if (!texto.contains("//") && !texto.contains("/*")) {
			return "";
		}
		// Calculamos las posiciones de comentario.
		StringBuilder comentario = new StringBuilder();

		ClaseAyudaScripts claseScripts = ClaseAyudaScripts.getInstance();
		claseScripts.setComentarioActivo(false);
		// Lo hacemos por lineas ya que uno de los tipos de comentarios, solo va por
		// lineas '//'
		for (String text : texto.split("\n")) {

			claseScripts.reiniciar();
			claseScripts = claseScripts.getComentario(text, claseScripts);
			comentario.append(claseScripts.getComentario());
		}

		return comentario.toString();
	}

	public static String extraerContenido(String texto) {
		if (texto == null || texto.isEmpty()) {
			return texto;
		}

		if (!texto.contains("//") && !texto.contains("/*")) {
			return texto;
		}
		// Calculamos las posiciones de comentario.
		StringBuilder contenido = new StringBuilder();

		// ComentarioActivo indica que está abierto '/*' y tenemos que buscar el cierre
		// '*/'
		ClaseAyudaScripts claseScripts = ClaseAyudaScripts.getInstance();
		claseScripts.setComentarioActivo(false);
		for (String text : texto.split("\n")) {

			claseScripts.reiniciar();
			claseScripts = claseScripts.getTexto(text, claseScripts);
			contenido.append(claseScripts.getTexto());

		}

		return contenido.toString();
	}

	/**
	 * Método para ver si un identificador es utilizado dentro del script y es
	 * invocado.
	 *
	 * @param script
	 * @param identificador
	 * @return
	 */
	public static boolean isDominioUtilizado(Script script, String identificador) {
		if (script == null || script.getContenido() == null || script.getContenido().isEmpty()) {
			return false;
		}

		final String contenido = UtilScripts.extraerContenido(script.getContenido());
		Matcher matcher = Pattern.compile("PLUGIN_DOMINIO.invocarDominio\\(\\'"+identificador+"\\'\\)")
				.matcher(contenido);
		return matcher.find();
	}

	/**
	 * Clase de ayuda para poder
	 *
	 * @author Indra
	 *
	 */
	public static class ClaseAyudaScripts {

		/** Los atributos de comentario y texto **/
		private StringBuilder comentario = new StringBuilder();
		private StringBuilder texto = new StringBuilder();
		// ComentarioActivo indica que está abierto '/*' y tenemos que buscar el cierre
		// '*/'
		private boolean comentarioActivo;

		/** Obtener una instancia de la clase **/
		public static ClaseAyudaScripts getInstance() {
			return new ClaseAyudaScripts();
		}

		/** Constructor basico **/
		public ClaseAyudaScripts() {
			this.texto = new StringBuilder();
			this.comentario = new StringBuilder();
		}

		/** Reinicia los textos almacenados **/
		public void reiniciar() {
			this.texto = new StringBuilder();
			this.comentario = new StringBuilder();
		}

		/**
		 * Método recursivo.
		 *
		 * @param text
		 * @param claseAyuda
		 **/
		public ClaseAyudaScripts getComentario(String text, ClaseAyudaScripts claseAyuda) {

			if (text == null || text.isEmpty()) {
				return claseAyuda;
			}

			if (claseAyuda.isComentarioActivo()) {
				int posAstBarra = text.indexOf("*/");
				// Si aun no ha encontrado el cierre, sigue en comentario
				if (posAstBarra == -1) {
					claseAyuda.setComentario(text);
					return claseAyuda;
				} else {
					String subtexto = text.substring(0, posAstBarra + 2);
					claseAyuda.setComentario(subtexto);
					String textoSiguiente = text.substring(posAstBarra + 2);
					claseAyuda.setComentarioActivo(false);
					return getComentario(textoSiguiente, claseAyuda);
				}
			} else {
				int posBarraAst = text.indexOf("/*");
				int posDblBarra = text.indexOf("//");
				if (posBarraAst == -1 && posDblBarra == -1) {
					return claseAyuda;
				} else {
					// Es decir, que /* esta antes que //
					if (posDblBarra == -1 || (posBarraAst != -1 && posBarraAst < posDblBarra)) {
						claseAyuda.setComentarioActivo(true);
						claseAyuda.setTexto(text.substring(0, posBarraAst));
						return getComentario(text.substring(posBarraAst), claseAyuda);
					} else {
						claseAyuda.setComentario(text.substring(posDblBarra));
						return claseAyuda;
					}
				}
			}
		}

		/**
		 * Método recursivo.
		 *
		 * @param text
		 * @param claseAyuda
		 **/
		public ClaseAyudaScripts getTexto(String text, ClaseAyudaScripts claseAyuda) {

			if (text == null || text.isEmpty()) {
				return claseAyuda;
			}

			if (claseAyuda.isComentarioActivo()) {
				int posAstBarra = text.indexOf("*/");
				// Si aun no ha encontrado el cierre, sigue en comentario
				if (posAstBarra == -1) {
					return claseAyuda;
				} else {
					String textoSiguiente = text.substring(posAstBarra + 2);
					claseAyuda.setComentarioActivo(false);
					return getTexto(textoSiguiente, claseAyuda);
				}
			} else {
				int posBarraAst = text.indexOf("/*");
				int posDblBarra = text.indexOf("//");
				if (posBarraAst == -1 && posDblBarra == -1) {
					claseAyuda.setTexto(text);
					return claseAyuda;
				} else {
					// Es decir, que /* esta antes que //
					if (posDblBarra == -1 || (posBarraAst != -1 && posBarraAst < posDblBarra)) {
						claseAyuda.setComentarioActivo(true);
						claseAyuda.setTexto(text.substring(0, posBarraAst));
						return getTexto(text.substring(posBarraAst + 2), claseAyuda);
					} else {
						claseAyuda.setTexto(text.substring(0, posDblBarra));
						return claseAyuda;
					}
				}
			}
		}

		/**
		 * @return the texto
		 */
		public String getTexto() {
			return texto.toString();
		}

		/**
		 * @param texto the texto to set
		 */
		public void setTexto(String texto) {
			this.texto.append(texto);
		}

		/**
		 * @return the comentarioActivo
		 */
		public boolean isComentarioActivo() {
			return comentarioActivo;
		}

		/**
		 * @param comentarioActivo the comentarioActivo to set
		 */
		public void setComentarioActivo(boolean comentarioActivo) {
			this.comentarioActivo = comentarioActivo;
		}

		/**
		 * @return the comentario
		 */
		public String getComentario() {
			return comentario.toString();
		}

		/**
		 * @param comentario the comentario to set
		 */
		public void setComentario(String comentario) {
			this.comentario.append(comentario);
		}
	}

}
