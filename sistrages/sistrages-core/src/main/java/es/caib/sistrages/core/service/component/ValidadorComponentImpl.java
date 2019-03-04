package es.caib.sistrages.core.service.component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.service.component.literales.Literales;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;

@Component("validadorComponent")
public class ValidadorComponentImpl implements ValidadorComponent {

	@Autowired
	TramiteDao tramiteDao;

	@Autowired
	TramitePasoDao tramitePasoDao;

	@Autowired
	FormularioInternoDao formularioInternoDao;

	@Autowired
	Literales literales;

	@Override
	public List<ErrorValidacion> comprobarVersionTramite(final Long pId, final String pIdioma) {
		List<ErrorValidacion> listaErrores = null;

		if (pId != null && StringUtils.isNoneEmpty(pIdioma)) {
			final TramiteVersion tramiteVersion = tramiteDao.getTramiteVersion(pId);
			tramiteVersion.setListaPasos(tramitePasoDao.getTramitePasos(pId));
			listaErrores = comprobarVersionTramite(tramiteVersion, pIdioma);
		}

		return listaErrores;
	}

	@Override
	public List<ErrorValidacion> comprobarVersionTramite(final TramiteVersion pTramiteVersion, final String pIdioma) {
		List<ErrorValidacion> listaErrores = null;

		if (pTramiteVersion != null && StringUtils.isNoneEmpty(pIdioma)) {

			final List<String> idiomasTramiteVersion = getIdiomasSoportados(pTramiteVersion);

			// recupera pasos si no los tiene cargados
			if (pTramiteVersion.getListaPasos().isEmpty()) {
				pTramiteVersion.setListaPasos(tramitePasoDao.getTramitePasos(pTramiteVersion.getCodigo()));
			}

			// recupera los diseños de los formularios
			recuperaDisenyoFormularios(pTramiteVersion);

			listaErrores = comprobarLiterales(pTramiteVersion, idiomasTramiteVersion, pIdioma);
		}
		return listaErrores;
	}

	private List<ErrorValidacion> comprobarLiterales(final TramiteVersion pTramiteVersion,
			final List<String> pIdiomasTramiteVersion, final String pIdioma) {

		final List<ErrorValidacion> listaErrores = new ArrayList<>();

		if (pTramiteVersion != null) {

			// literales propiedades
			comprobarLiteralesPropiedades(pTramiteVersion, pIdiomasTramiteVersion, pIdioma, listaErrores);

			// literales pasos
			if (!pTramiteVersion.getListaPasos().isEmpty()) {
				for (final TramitePaso paso : pTramiteVersion.getListaPasos()) {
					// literales debe saber
					if (paso instanceof TramitePasoDebeSaber) {
						comprobarLiteralesDebeSaber(pIdiomasTramiteVersion, pIdioma, listaErrores,
								(TramitePasoDebeSaber) paso);
					} else if (paso instanceof TramitePasoRellenar) {
						comprobarLiteralesRellenarFormulario(pIdiomasTramiteVersion, pIdioma, listaErrores,
								(TramitePasoRellenar) paso);
					} else if (paso instanceof TramitePasoRegistrar) {
						comprobarLiteralesRegistrarFormulario(pIdiomasTramiteVersion, pIdioma, listaErrores,
								(TramitePasoRegistrar) paso);

					}

				}
			}

		}
		return listaErrores;

	}

	private void comprobarLiteralesPropiedades(final TramiteVersion pTramiteVersion,
			final List<String> pIdiomasTramiteVersion, final String pIdioma, final List<ErrorValidacion> listaErrores) {
		listaErrores.addAll(comprobarLiteralesScript(pTramiteVersion.getScriptPersonalizacion(),
				"propiedades.scriptPersonalizacion",
				new String[] { literales.getLiteral("validador", "propiedades", pIdioma) }, "literal.script.mensaje",
				pIdiomasTramiteVersion, pIdioma));

		listaErrores.addAll(comprobarLiteralesScript(pTramiteVersion.getScriptInicializacionTramite(),
				"propiedades.scriptInicializacionTramite",
				new String[] { literales.getLiteral("validador", "propiedades", pIdioma) }, "literal.script.mensaje",
				pIdiomasTramiteVersion, pIdioma));
	}

	private void comprobarLiteralesDebeSaber(final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<ErrorValidacion> listaErrores, final TramitePasoDebeSaber pasoDebeSaber) {
		if (literalIncompleto(pasoDebeSaber.getInstruccionesIniciales(), pIdiomasTramiteVersion)) {

			final ErrorValidacion error = errorValidacionElemento("tramitePasoDebeSaber.instruccionesiniciales",
					new String[] { literales.getLiteral("validador", "tramitePasoDebeSaber", pIdioma) },
					"literal.paso.elemento", pIdioma);
			listaErrores.add(error);
		}
	}

	private void comprobarLiteralesRellenarFormulario(final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<ErrorValidacion> listaErrores, final TramitePasoRellenar pasoRellenar) {
		if (pasoRellenar.getFormulariosTramite() != null) {
			for (final FormularioTramite formulario : pasoRellenar.getFormulariosTramite()) {

				if (literalIncompleto(formulario.getDescripcion(), pIdiomasTramiteVersion)) {
					final ErrorValidacion error = errorValidacionElemento("tramitePasoRellenar.descripcion",
							new String[] { formulario.getIdentificador() }, "literal.formulario.elemento", pIdioma);
					listaErrores.add(error);
				}

				listaErrores.addAll(comprobarLiteralesScript(formulario.getScriptObligatoriedad(),
						"tramitePasoRellenar.scriptObligatoriedad", new String[] { formulario.getIdentificador() },
						"literal.script.mensaje.formulario", pIdiomasTramiteVersion, pIdioma));

				listaErrores.addAll(comprobarLiteralesScript(formulario.getScriptFirma(),
						"tramitePasoRellenar.scriptFirma", new String[] { formulario.getIdentificador() },
						"literal.script.mensaje.formulario", pIdiomasTramiteVersion, pIdioma));

				listaErrores.addAll(comprobarLiteralesScript(formulario.getScriptDatosIniciales(),
						"tramitePasoRellenar.scriptDatosIniciales", new String[] { formulario.getIdentificador() },
						"literal.script.mensaje.formulario", pIdiomasTramiteVersion, pIdioma));

				listaErrores.addAll(comprobarLiteralesScript(formulario.getScriptParametros(),
						"tramitePasoRellenar.scriptParametros", new String[] { formulario.getIdentificador() },
						"literal.script.mensaje.formulario", pIdiomasTramiteVersion, pIdioma));

				listaErrores.addAll(comprobarLiteralesScript(formulario.getScriptRetorno(),
						"tramitePasoRellenar.scriptRetorno", new String[] { formulario.getIdentificador() },
						"literal.script.mensaje.formulario", pIdiomasTramiteVersion, pIdioma));

				// DISEÑO DEL FORMULARIO
				// propiedades del formulario
				comprobarLiteralesDisenyoFormulario(pIdiomasTramiteVersion, pIdioma, listaErrores, formulario);

			}
		}
	}

	private void comprobarLiteralesDisenyoFormulario(final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<ErrorValidacion> listaErrores, final FormularioTramite formulario) {
		if (literalIncompleto(formulario.getDisenyoFormulario().getTextoCabecera(), pIdiomasTramiteVersion)) {
			final ErrorValidacion error = errorValidacionElemento("tramitePasoRellenar.disenyoFormulario.textoCabecera",
					new String[] { formulario.getIdentificador() }, "literal.formulario.elemento", pIdioma);
			listaErrores.add(error);
		}

		if (formulario.getDisenyoFormulario().getPaginas() != null) {
			for (final PaginaFormulario paginaFormulario : formulario.getDisenyoFormulario().getPaginas()) {
				listaErrores.addAll(comprobarLiteralesScript(paginaFormulario.getScriptValidacion(),
						"tramitePasoRellenar.disenyoFormulario.scriptValidacion",
						new String[] {
								literales.getLiteral("validador", "tramitePasoRellenar.disenyoFormulario.pagina",
										pIdioma) + " " + Integer.toString(paginaFormulario.getOrden()),
								formulario.getIdentificador() },
						"literal.script.mensaje.formulario.pagina", pIdiomasTramiteVersion, pIdioma));
			}
		}

		listaErrores.addAll(comprobarLiteralesScript(formulario.getDisenyoFormulario().getScriptPlantilla(),
				"tramitePasoRellenar.disenyoFormulario.scriptPlantilla", new String[] { formulario.getIdentificador() },
				"literal.script.mensaje.formulario", pIdiomasTramiteVersion, pIdioma));

		if (formulario.getDisenyoFormulario().getPaginas() != null) {
			for (final PaginaFormulario paginaFormulario : formulario.getDisenyoFormulario().getPaginas()) {
				if (paginaFormulario.getLineas() != null) {
					for (final LineaComponentesFormulario linea : paginaFormulario.getLineas()) {
						if (linea.getComponentes() != null) {
							for (final ComponenteFormulario componente : linea.getComponentes()) {
								if (componente instanceof ComponenteFormularioSeccion) {
									final ComponenteFormularioSeccion seccion = (ComponenteFormularioSeccion) componente;

									if (literalIncompleto(seccion.getTexto(), pIdiomasTramiteVersion)) {
										final ErrorValidacion error = errorValidacionElemento(
												"tramitePasoRellenar.disenyoFormulario.texto",
												new String[] {
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.seccion",
																pIdioma) + " " + seccion.getLetra(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.formulario.disenyoFormulario.pagina.elemento", pIdioma);
										listaErrores.add(error);
									}
								} else if (componente instanceof ComponenteFormularioEtiqueta) {
									final ComponenteFormularioEtiqueta aviso = (ComponenteFormularioEtiqueta) componente;

									if (literalIncompleto(aviso.getTexto(), pIdiomasTramiteVersion)) {
										final ErrorValidacion error = errorValidacionElemento(
												"tramitePasoRellenar.disenyoFormulario.texto",
												new String[] { aviso.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.formulario.disenyoFormulario.pagina.elemento", pIdioma);
										listaErrores.add(error);
									}
								} else if (componente instanceof ComponenteFormularioCampo) {
									final ComponenteFormularioCampo campo = (ComponenteFormularioCampo) componente;

									// texto
									if (literalIncompleto(campo.getTexto(), pIdiomasTramiteVersion)) {
										final ErrorValidacion error = errorValidacionElemento(
												"tramitePasoRellenar.disenyoFormulario.texto",
												new String[] { campo.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.formulario.disenyoFormulario.pagina.elemento", pIdioma);
										listaErrores.add(error);
									}

									// ayuda online
									if (literalIncompleto(campo.getAyuda(), pIdiomasTramiteVersion)) {
										final ErrorValidacion error = errorValidacionElemento(
												"tramitePasoRellenar.disenyoFormulario.ayuda",
												new String[] { campo.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.formulario.disenyoFormulario.pagina.elemento", pIdioma);
										listaErrores.add(error);
									}

									// script autorelleno
									listaErrores.addAll(comprobarLiteralesScript(campo.getScriptAutorrellenable(),
											"tramitePasoRellenar.disenyoFormulario.scriptAutorellenable",
											new String[] { campo.getIdComponente(),
													literales.getLiteral("validador",
															"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
															+ " " + Integer.toString(paginaFormulario.getOrden()),
													formulario.getIdentificador() },
											"literal.script.mensaje.formulario.disenyoFormulario.pagina",
											pIdiomasTramiteVersion, pIdioma));

									if (componente instanceof ComponenteFormularioCampoSelector) {
										final ComponenteFormularioCampoSelector selector = (ComponenteFormularioCampoSelector) componente;
										if (TypeListaValores.SCRIPT.equals(selector.getTipoListaValores())) {
											// selector.getScriptValoresPosibles()
										}

									}

								}
							}
						}
					}
				}
			}
		}

	}

	private void comprobarLiteralesRegistrarFormulario(final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<ErrorValidacion> listaErrores, final TramitePasoRegistrar pasoRegistrar) {
		listaErrores.addAll(comprobarLiteralesScript(pasoRegistrar.getScriptDestinoRegistro(),
				"tramitePasoRegistrar.scriptDestinoRegistro",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.script.mensaje.paso", pIdiomasTramiteVersion, pIdioma));

		listaErrores.addAll(
				comprobarLiteralesScript(pasoRegistrar.getScriptPresentador(), "tramitePasoRegistrar.scriptPresentador",
						new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
						"literal.script.mensaje.paso", pIdiomasTramiteVersion, pIdioma));

		listaErrores.addAll(comprobarLiteralesScript(pasoRegistrar.getScriptRepresentante(),
				"tramitePasoRegistrar.scriptRepresentante",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.script.mensaje.paso", pIdiomasTramiteVersion, pIdioma));

		listaErrores.addAll(comprobarLiteralesScript(pasoRegistrar.getScriptValidarRegistrar(),
				"tramitePasoRegistrar.scriptValidarRegistrar",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.script.mensaje.paso", pIdiomasTramiteVersion, pIdioma));

		if (literalIncompleto(pasoRegistrar.getInstruccionesPresentacion(), pIdiomasTramiteVersion)) {
			final ErrorValidacion error = errorValidacionElemento("tramitePasoRegistrar.instruccionesPresentacion",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
					"literal.paso.elemento", pIdioma);
			listaErrores.add(error);
		}

		if (literalIncompleto(pasoRegistrar.getInstruccionesFinTramitacion(), pIdiomasTramiteVersion)) {
			final ErrorValidacion error = errorValidacionElemento("tramitePasoRegistrar.instruccionesFinTramitacion",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
					"literal.paso.elemento", pIdioma);
			listaErrores.add(error);
		}
	}

	private List<ErrorValidacion> comprobarLiteralesScript(final Script pScript, final String pLiteralScript,
			final String[] pOpciones, final String pLiteralOpciones, final List<String> pIdiomasTramiteVersion,
			final String pIdioma) {
		final List<ErrorValidacion> listaErrores = new ArrayList<>();

		if (pScript != null && pScript.getMensajes() != null && !pScript.getMensajes().isEmpty()) {
			for (final LiteralScript mensaje : pScript.getMensajes()) {
				if (literalIncompleto(mensaje.getLiteral(), pIdiomasTramiteVersion)) {

					final ErrorValidacion error = errorValidacionScript(mensaje.getIdentificador(), pLiteralScript,
							pOpciones, pLiteralOpciones, pIdioma);
					listaErrores.add(error);

				}
			}
		}
		return listaErrores;
	}

	private ErrorValidacion errorValidacionScript(final String pMensaje, final String pLiteralScript,
			final String[] pOpciones, final String pLiteralOpciones, final String pIdioma) {
		final String literalScript = literales.getLiteral("validador", pLiteralScript, pIdioma);

		final StringBuilder elemento = new StringBuilder();

		if (pOpciones != null) {
			elemento.append(pOpciones[pOpciones.length - 1] + " > ");

			for (int i = pOpciones.length - 2; i >= 0; i--) {
				elemento.append(pOpciones[i] + " > ");
			}
		}

		elemento.append(literalScript + " > " + pMensaje);

		String[] parametros = { pMensaje, literalScript };
		parametros = ArrayUtils.addAll(parametros, pOpciones);

		final String descripcion = literales.getLiteral("validador", pLiteralOpciones, parametros, pIdioma);

		return new ErrorValidacion(elemento.toString(), descripcion);
	}

	private ErrorValidacion errorValidacionElemento(final String pElemento, final String[] pOpciones,
			final String pLiteralOpciones, final String pIdioma) {
		final String literalElemento = literales.getLiteral("validador", pElemento, pIdioma);

		final StringBuilder elemento = new StringBuilder();

		if (pOpciones != null) {
			elemento.append(pOpciones[pOpciones.length - 1] + " > ");

			for (int i = pOpciones.length - 2; i >= 0; i--) {
				elemento.append(pOpciones[i] + " > ");
			}
		}

		elemento.append(literalElemento);

		String[] parametros = { literalElemento };
		parametros = ArrayUtils.addAll(parametros, pOpciones);

		final String descripcion = literales.getLiteral("validador", pLiteralOpciones, parametros, pIdioma);

		return new ErrorValidacion(elemento.toString(), descripcion);
	}

	/******* Aux Literales ********/
	// TODO: Pasar de frontend a core????
	private List<String> getIdiomasSoportados(final TramiteVersion tramiteVersion) {
		List<String> idiomas;
		if (tramiteVersion == null || tramiteVersion.getIdiomasSoportados() == null
				|| tramiteVersion.getIdiomasSoportados().isEmpty()) {
			idiomas = new ArrayList<>();
			idiomas.add(TypeIdioma.CATALAN.toString());
			idiomas.add(TypeIdioma.CASTELLANO.toString());

		} else {
			idiomas = Arrays.asList(tramiteVersion.getIdiomasSoportados().split(";"));
		}
		return idiomas;
	}

	private void recuperaDisenyoFormularios(final TramiteVersion pTramiteVersion) {
		if (!pTramiteVersion.getListaPasos().isEmpty()) {
			for (final TramitePaso paso : pTramiteVersion.getListaPasos()) {
				if (paso instanceof TramitePasoRellenar) {
					final List<FormularioTramite> formularios = ((TramitePasoRellenar) paso).getFormulariosTramite();
					if (formularios != null && !formularios.isEmpty()) {
						for (final FormularioTramite formularioTramite : formularios) {
							final DisenyoFormulario disenyoFormulario = formularioInternoDao
									.getFormularioCompletoById(formularioTramite.getIdFormularioInterno());
							if (disenyoFormulario != null) {
								formularioTramite.setDisenyoFormulario(disenyoFormulario);
							}
						}
					}
				}
			}
		}
	}

	private boolean literalIncompleto(final Literal literal, final List<String> pIdiomas) {
		if (literal != null) {
			for (final String idioma : pIdiomas) {
				if (!literal.contains(idioma) || literal.getTraduccion(idioma).isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	/**************************************/

	private List<Field> getAllFields(final Class<?> clazz) {
		final List<Field> fields = new ArrayList<>();

		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

		final Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null && superClazz != ModelApi.class) {
			fields.addAll(getAllFields(superClazz));
		}

		return fields;
	}

	private List<Class<?>> getGeneralizaciones(final Class<?> classObject) {
		final List<Class<?>> generalizacion = new ArrayList<>();

		generalizacion.add(classObject);

		final Class<?> superClass = classObject.getSuperclass();
		if (superClass != null) {
			generalizacion.addAll(getGeneralizaciones(superClass));
		}

		return generalizacion;
	}

}
