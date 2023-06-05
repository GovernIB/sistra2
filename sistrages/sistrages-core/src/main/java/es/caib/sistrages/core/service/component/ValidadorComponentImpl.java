package es.caib.sistrages.core.service.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.plugins.documentconverter.openoffice.OpenOfficeDocumentConverterPlugin;
import org.fundaciobit.pluginsib.documentconverter.IDocumentConverterPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.caib.sistrages.core.api.exception.VariableAreaNoExisteException;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorListaFija;
import es.caib.sistrages.core.api.model.VariableArea;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeErrorValidacion;
import es.caib.sistrages.core.api.model.types.TypeFormularioGestor;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;
import es.caib.sistrages.core.api.model.types.TypeTamanyo;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.core.api.util.UtilScripts;
import es.caib.sistrages.core.service.component.literales.Literales;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;
import es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.SeccionReutilizableDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;
import es.caib.sistrages.core.service.repository.dao.VariableAreaDao;

@Component("validadorComponent")
public class ValidadorComponentImpl implements ValidadorComponent {

	private static final Pattern PLUGINDOMI_INVOCAR_PATTERN = Pattern.compile("PLUGIN_DOMINIOS.invocarDominio\\(\\'("
			+ ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + "*?)\\'");

	@Autowired
	EntidadDao entidadDao;

	@Autowired
	AreaDao areaDao;

	@Autowired
	TramiteDao tramiteDao;

	@Autowired
	SeccionReutilizableDao seccionReutilizableDao;

	@Autowired
	private ConfiguracionGlobalService cfService;

	@Autowired
	TramitePasoDao tramitePasoDao;

	@Autowired
	FormularioInternoDao formularioInternoDao;

	@Autowired
	FormateadorFormularioDao formateadorFormularioDao;

	@Autowired
	DominioDao dominioDao;

	@Autowired
	VariableAreaDao vaDao;

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
			if (pTramiteVersion.getListaPasos() == null || pTramiteVersion.getListaPasos().isEmpty()) {
				pTramiteVersion.setListaPasos(tramitePasoDao.getTramitePasos(pTramiteVersion.getCodigo()));
			}

			// recupera dominios si no los tiene cargados
			if (pTramiteVersion.getListaDominios() == null || pTramiteVersion.getListaDominios().isEmpty()) {
				final List<Long> listaDominiosId = new ArrayList<>();
				pTramiteVersion
						.setListaAuxDominios(tramiteDao.getDominioSimpleByTramiteId(pTramiteVersion.getCodigo()));
				for (final Dominio dominioSimple : pTramiteVersion.getListaAuxDominios()) {
					listaDominiosId.add(dominioSimple.getCodigo());
				}
				pTramiteVersion.setListaDominios(listaDominiosId);
			}

			// recupera los diseños de los formularios
			recuperaDisenyoFormularios(pTramiteVersion);

			listaErrores = comprobarVersionTramite(pTramiteVersion, idiomasTramiteVersion, pIdioma);
		}
		return listaErrores;
	}

	@Override
	public List<ErrorValidacion> comprobarScript(final Script pScript, final List<Dominio> pListaDominios,
			final List<String> pIdiomasTramiteVersion, final String pIdioma) {
		final List<ErrorValidacion> listaErrores = new ArrayList<>();
		final List<Dominio> dominiosNoUsados = new ArrayList<>();
		if (pScript != null) {
			comprobarScript(pScript, "script", null, "script.literal.script.mensaje", "script.compilar.script",
					"script.dominio.script", "script.comentario", pListaDominios, pIdiomasTramiteVersion, pIdioma,
					dominiosNoUsados, listaErrores, null);
		}
		return listaErrores;
	}

	/**
	 * Valida que no tenga errores una version de un tramite.
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 */
	private List<ErrorValidacion> comprobarVersionTramite(final TramiteVersion pTramiteVersion,
			final List<String> pIdiomasTramiteVersion, final String pIdioma) {

		final List<ErrorValidacion> listaErrores = new ArrayList<>();
		final List<Dominio> pListaDominiosNoUsados = new ArrayList<>(pTramiteVersion.getListaAuxDominios());

		if (pTramiteVersion != null) {

			// propiedades
			comprobarPropiedades(pTramiteVersion, pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
					listaErrores);

			// formularios
			if (!tieneFormularios(pTramiteVersion)) {

				final ErrorValidacion error = new ErrorValidacion(pTramiteVersion.getIdString(),
						literales.getLiteral("validador", "formulario.vacio", pIdioma));
				// tipificamos el error
				error.setTipo(TypeErrorValidacion.TRAMITE);
				error.setItem(pTramiteVersion);

				listaErrores.add(error);
			}

			// pasos
			if (!pTramiteVersion.getListaPasos().isEmpty()) {
				for (final TramitePaso paso : pTramiteVersion.getListaPasos()) {
					// literales debe saber
					if (paso instanceof TramitePasoDebeSaber) {
						comprobarDebeSaber(pTramiteVersion, pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
								listaErrores, (TramitePasoDebeSaber) paso);
					} else if (paso instanceof TramitePasoRellenar) {
						comprobarRellenarFormulario(pTramiteVersion, pIdiomasTramiteVersion, pIdioma,
								pListaDominiosNoUsados, listaErrores, (TramitePasoRellenar) paso);
					} else if (paso instanceof TramitePasoAnexar) {
						comprobarAnexar(pTramiteVersion, pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
								listaErrores, (TramitePasoAnexar) paso);
					} else if (paso instanceof TramitePasoTasa) {
						comprobarTasa(pTramiteVersion, pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
								listaErrores, (TramitePasoTasa) paso);
					} else if (paso instanceof TramitePasoRegistrar) {
						comprobarRegistrarFormulario(pTramiteVersion, pIdiomasTramiteVersion, pIdioma,
								pListaDominiosNoUsados, listaErrores, (TramitePasoRegistrar) paso);

					}

				}
			}

			for (Dominio dominio : pListaDominiosNoUsados) {
				final ErrorValidacion errorVal = new ErrorValidacion();
				errorVal.setDescripcion(literales.getLiteral("validador", "dominio.nousado", pIdioma));
				errorVal.setElemento(dominio.getIdentificadorCompuesto());
				errorVal.setTipo(TypeErrorValidacion.DOMINIOS_ELIMINAR);
				errorVal.setItem(dominio);
				listaErrores.add(errorVal);
			}

			for (Dominio dom : pTramiteVersion.getListaAuxDominios()) {
				comprobarVariablesArea(dom, listaErrores, pIdioma);
			}

		}
		return listaErrores;

	}

	private void comprobarVariablesArea(Object obj, List<ErrorValidacion> listaErrores, final String pIdioma) {
		if (obj instanceof Dominio) {
			Dominio dom = (Dominio) obj;
			if (dom.getAmbito().equals(TypeAmbito.AREA) && TypeDominio.CONSULTA_REMOTA.equals(dom.getTipo())
					&& dom.getUrl().matches("\\{@@[A-Z0-9]*@@\\}")) {
				VariableArea va = vaDao.getVariableAreaByIdentificador(
						dom.getUrl().replaceAll("\\{@@", "").replaceAll("@@\\}", ""), dom.getArea().getCodigo());
				if (va == null) {
					final ErrorValidacion errorVal = new ErrorValidacion();
					errorVal.setDescripcion(literales.getLiteral("validador", "variableArea.noExiste", pIdioma) + ": "
							+ dom.getUrl().replaceAll("\\{@@", "").replaceAll("@@\\}", ""));
					errorVal.setElemento(literales.getLiteral("validador", "dominio", pIdioma) + ": "
							+ dom.getIdentificadorCompuesto());
					errorVal.setTipo(TypeErrorValidacion.VARIABLE_AREA_NO_EXISTE);
					errorVal.setItem(dom);
					listaErrores.add(errorVal);
				}
			}
		} else if (obj instanceof FormularioTramite) {
			FormularioTramite gfe = (FormularioTramite) obj;
			if (TypeFormularioGestor.EXTERNO.equals(gfe.getTipoFormulario()) && gfe.getFormularioGestorExterno() != null
					&& gfe.getFormularioGestorExterno().getUrl().matches(".*\\{@@[A-Za-z0-9\\_\\-]{1,}@@\\}.*")) {
				VariableArea va = vaDao
						.getVariableAreaByIdentificador(
								gfe.getFormularioGestorExterno().getUrl().replaceAll("\\{@@", "").replaceAll("@@\\}",
										""),
								areaDao.getAreaByIdentificador(
										gfe.getFormularioGestorExterno().getIdentificadorCompuesto().substring(0,
												gfe.getFormularioGestorExterno().getIdentificadorCompuesto()
														.indexOf('.')),
										gfe.getFormularioGestorExterno().getAreaIdentificador()).getCodigo());
				if (va == null) {
					final ErrorValidacion errorVal = new ErrorValidacion();
					errorVal.setDescripcion(literales.getLiteral("validador", "variableArea.noExiste", pIdioma) + ": "
							+ gfe.getFormularioGestorExterno().getUrl().replaceAll("\\{@@", "").replaceAll("@@\\}",
									""));
					errorVal.setElemento(literales.getLiteral("validador", "fExterno", pIdioma) + ": "
							+ gfe.getFormularioGestorExterno().getIdentificadorCompuesto());
					errorVal.setTipo(TypeErrorValidacion.VARIABLE_AREA_NO_EXISTE);
					errorVal.setItem(gfe.getFormularioGestorExterno());
					listaErrores.add(errorVal);
				}
			}
		} else if (obj instanceof EnvioRemoto) {
			EnvioRemoto er = (EnvioRemoto) obj;
			if (er.getAmbito().equals(TypeAmbito.AREA) && er.getUrl().matches(".*\\{@@[A-Za-z0-9\\_\\-]{1,}@@\\}.*")) {
				VariableArea va = vaDao.getVariableAreaByIdentificador(
						er.getUrl().replaceAll("\\{@@", "").replaceAll("@@\\}", ""), er.getArea().getCodigo());
				if (va == null) {
					final ErrorValidacion errorVal = new ErrorValidacion();
					errorVal.setDescripcion(literales.getLiteral("validador", "variableArea.noExiste", pIdioma) + ": "
							+ er.getUrl().replaceAll("\\{@@", "").replaceAll("@@\\}", ""));
					errorVal.setElemento(literales.getLiteral("validador", "envioRemoto", pIdioma) + ": "
							+ er.getIdentificadorCompuesto());
					errorVal.setTipo(TypeErrorValidacion.VARIABLE_AREA_NO_EXISTE);
					errorVal.setItem(er);
					listaErrores.add(errorVal);
				}
			}
		}
	}

	/**
	 * Valida el apartado de propiedades.
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 */
	private void comprobarPropiedades(final TramiteVersion pTramiteVersion, final List<String> pIdiomasTramiteVersion,
			final String pIdioma, final List<Dominio> pListaDominiosNoUsados,
			final List<ErrorValidacion> listaErrores) {

		final ObjectNode params = JsonNodeFactory.instance.objectNode();
		params.put("TRAMITEVERSION", String.valueOf(pTramiteVersion.getCodigo()));

		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_PERSONALIZACION_TRAMITE));

		comprobarScript(pTramiteVersion.getScriptPersonalizacion(), "propiedades.scriptPersonalizacion",
				new String[] { literales.getLiteral("validador", "propiedades", pIdioma) }, "literal.script.mensaje",
				"compilar.script", "dominio.script", "script.comentario", pTramiteVersion.getListaAuxDominios(),
				pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados, listaErrores, UtilJSON.toJSON(params));

		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_PARAMETROS_INICIALES));

		comprobarScript(pTramiteVersion.getScriptInicializacionTramite(), "propiedades.scriptInicializacionTramite",
				new String[] { literales.getLiteral("validador", "propiedades", pIdioma) }, "literal.script.mensaje",
				"compilar.script", "dominio.script", "script.comentario", pTramiteVersion.getListaAuxDominios(),
				pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados, listaErrores, UtilJSON.toJSON(params));
	}

	/**
	 * valida el paso debe saber.
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 * @param pasoDebeSaber paso debe saber
	 */
	private void comprobarDebeSaber(final TramiteVersion pTramiteVersion, final List<String> pIdiomasTramiteVersion,
			final String pIdioma, final List<Dominio> pListaDominiosNoUsados, final List<ErrorValidacion> listaErrores,
			final TramitePasoDebeSaber pasoDebeSaber) {

		comprobarLiteralHTML(pasoDebeSaber.getInstruccionesIniciales(), "tramitePasoDebeSaber.instruccionesiniciales",
				new String[] { literales.getLiteral("validador", "tramitePasoDebeSaber", pIdioma) },
				"literal.paso.elemento", pIdiomasTramiteVersion, pIdioma, listaErrores);

		final ObjectNode params = JsonNodeFactory.instance.objectNode();
		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_DEBE_SABER));

		comprobarScript(pasoDebeSaber.getScriptDebeSaber(), "tramitePasoDebeSaber.scriptInstrucciones",
				new String[] { literales.getLiteral("validador", "tramitePasoDebeSaber", pIdioma) },
				"literal.script.mensaje.paso", "compilar.script.paso", "dominio.script.paso", "script.comentario",
				pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
				listaErrores, UtilJSON.toJSON(params));

	}

	private void comprobarRellenarFormulario(final TramiteVersion pTramiteVersion,
			final List<String> pIdiomasTramiteVersion, final String pIdioma, final List<Dominio> pListaDominiosNoUsados,
			final List<ErrorValidacion> listaErrores, final TramitePasoRellenar pasoRellenar) {

		final ObjectNode params = JsonNodeFactory.instance.objectNode();

		if (pasoRellenar.getFormulariosTramite() != null) {
			for (final FormularioTramite formulario : pasoRellenar.getFormulariosTramite()) {

				comprobarLiteral(formulario.getDescripcion(), "tramitePasoRellenar.descripcion",
						new String[] { formulario.getIdentificador() }, "literal.formulario.elemento",
						pIdiomasTramiteVersion, pIdioma, listaErrores);

				params.put("TRAMITEVERSION", String.valueOf(pTramiteVersion.getCodigo()));
				params.put("TRAMITEPASO", String.valueOf(pasoRellenar.getCodigo()));

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO));

				comprobarScript(formulario.getScriptObligatoriedad(), "tramitePasoRellenar.scriptObligatoriedad",
						new String[] { formulario.getIdentificador() }, "literal.script.mensaje.formulario",
						"compilar.script.formulario", "dominio.script.formulario", "script.comentario",
						pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
						listaErrores, UtilJSON.toJSON(params));

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_FIRMANTES));

				comprobarScript(formulario.getScriptFirma(), "tramitePasoRellenar.scriptFirma",
						new String[] { formulario.getIdentificador() }, "literal.script.mensaje.formulario",
						"compilar.script.formulario", "dominio.script.formulario", "script.comentario",
						pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
						listaErrores, UtilJSON.toJSON(params));

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_DATOS_INICIALES_FORMULARIO));

				comprobarScript(formulario.getScriptDatosIniciales(), "tramitePasoRellenar.scriptDatosIniciales",
						new String[] { formulario.getIdentificador() }, "literal.script.mensaje.formulario",
						"compilar.script.formulario", "dominio.script.formulario", "script.comentario",
						pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
						listaErrores, UtilJSON.toJSON(params));

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_PARAMETROS_FORMULARIO));

				comprobarScript(formulario.getScriptParametros(), "tramitePasoRellenar.scriptParametros",
						new String[] { formulario.getIdentificador() }, "literal.script.mensaje.formulario",
						"compilar.script.formulario", "dominio.script.formulario", "script.comentario",
						pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
						listaErrores, UtilJSON.toJSON(params));

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO));

				comprobarScript(formulario.getScriptRetorno(), "tramitePasoRellenar.scriptRetorno",
						new String[] { formulario.getIdentificador() }, "literal.script.mensaje.formulario",
						"compilar.script.formulario", "dominio.script.formulario", "script.comentario",
						pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
						listaErrores, UtilJSON.toJSON(params));

				// DISEÑO DEL FORMULARIO
				// propiedades del formulario

				comprobarVariablesArea(formulario, listaErrores, pIdioma);

				if (formulario.getDisenyoFormulario() != null) {
					comprobarDisenyoFormulario(pTramiteVersion, pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
							listaErrores, formulario);
				}
			}
		}
	}

	/**
	 * Valida las variables de área de un formulario
	 */

	/**
	 * Valida disenyo formulario.
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 * @param formulario formulario
	 */
	private void comprobarDisenyoFormulario(final TramiteVersion pTramiteVersion,
			final List<String> pIdiomasTramiteVersion, final String pIdioma, final List<Dominio> pListaDominiosNoUsados,
			final List<ErrorValidacion> listaErrores, final FormularioTramite formulario) {
		final ObjectNode params = JsonNodeFactory.instance.objectNode();
		params.put("TRAMITEVERSION", String.valueOf(pTramiteVersion.getCodigo()));
		params.put("FORMULARIO_ACTUAL", String.valueOf(formulario.getCodigo()));
		params.put("FORM_INTERNO_ACTUAL", String.valueOf(formulario.getIdFormularioInterno()));

		comprobarLiteral(formulario.getDisenyoFormulario().getTextoCabecera(),
				"tramitePasoRellenar.disenyoFormulario.textoCabecera", new String[] { formulario.getIdentificador() },
				"literal.formulario.elemento", pIdiomasTramiteVersion, pIdioma, listaErrores);

		List<DisenyoFormulario> disenyos = new ArrayList<>();
		disenyos.add(formulario.getDisenyoFormulario());
		List<ScriptSeccionReutilizable> scripts = new ArrayList<>();
		List<SeccionReutilizable> seccionesReutilizables = tramiteDao
				.getSeccionesReutilizableByTramite(pTramiteVersion.getCodigo());
		if (seccionesReutilizables != null && !seccionesReutilizables.isEmpty()) {
			for (SeccionReutilizable seccion : seccionesReutilizables) {
				scripts.addAll(seccionReutilizableDao.getScriptsByIdSeccionReutilizable(seccion.getCodigo()));
				disenyos.add(formularioInternoDao.getFormularioCompletoById(seccion.getIdFormularioAsociado(), true));
			}
		}

		for (ScriptSeccionReutilizable script : scripts) {
			comprobarScript(script.getScript(), "tramitePasoRellenar.disenyoFormulario.scriptValidacion",
					new String[] {
							literales.getLiteral("validador", "tramitePasoRellenar.disenyoFormulario.pagina", pIdioma),
							formulario.getIdentificador() },
					"literal.script.mensaje.formulario.pagina", "compilar.script.formulario.pagina",
					"dominio.script.formulario.pagina", "script.comentario", pTramiteVersion.getListaAuxDominios(),
					pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados, listaErrores, UtilJSON.toJSON(params));
		}

		for (DisenyoFormulario disenyo : disenyos) {
			if (disenyo.getPaginas() != null) {

				params.put("TIPO_SCRIPT_FORMULARIO", UtilJSON.toJSON(TypeScriptFormulario.SCRIPT_VALIDACION_PAGINA));

				for (final PaginaFormulario paginaFormulario : disenyo.getPaginas()) {

					params.put("PAGINA", String.valueOf(paginaFormulario.getCodigo()));

					comprobarScript(paginaFormulario.getScriptValidacion(),
							"tramitePasoRellenar.disenyoFormulario.scriptValidacion",
							new String[] {
									literales.getLiteral("validador", "tramitePasoRellenar.disenyoFormulario.pagina",
											pIdioma) + " " + Integer.toString(paginaFormulario.getOrden()),
									formulario.getIdentificador() },
							"literal.script.mensaje.formulario.pagina", "compilar.script.formulario.pagina",
							"dominio.script.formulario.pagina", "script.comentario",
							pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma,
							pListaDominiosNoUsados, listaErrores, UtilJSON.toJSON(params));

					comprobarScript(paginaFormulario.getScriptNavegacion(),
							"tramitePasoRellenar.disenyoFormulario.scriptNavegacion",
							new String[] {
									literales.getLiteral("validador", "tramitePasoRellenar.disenyoFormulario.pagina",
											pIdioma) + " " + Integer.toString(paginaFormulario.getOrden()),
									formulario.getIdentificador() },
							"literal.script.mensaje.formulario.pagina", "compilar.script.formulario.pagina",
							"dominio.script.formulario.pagina", "script.comentario",
							pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma,
							pListaDominiosNoUsados, listaErrores, UtilJSON.toJSON(params));
				}

			}
		}

		// tiene que haber al menos un formateador
		final Long idEntidad = tramiteDao.getById(pTramiteVersion.getIdTramite()).getIdEntidad();
		final FormateadorFormulario formateador = formateadorFormularioDao.getFormateadorPorDefecto(idEntidad, null);

		if (formateador == null && (formulario.getDisenyoFormulario().getPlantillas() == null
				|| formulario.getDisenyoFormulario().getPlantillas().isEmpty())) {

			final ErrorValidacion error = errorValidacion("tramitePasoRellenar.disenyoFormulario.listaPlantilla",
					new String[] { formulario.getIdentificador() },
					"tramitePasoRellenar.disenyoFormulario.formulario.listaPlantilla", pIdioma);

			// tipificamos el error
			error.setTipo(TypeErrorValidacion.FORMATEADOR);

			params.put("ID", String.valueOf(formulario.getIdFormularioInterno()));
			error.setParams(UtilJSON.toJSON(params));

			listaErrores.add(error);
		} else if (formulario.getDisenyoFormulario().getPlantillas() != null
				&& !formulario.getDisenyoFormulario().getPlantillas().isEmpty()) {
			for (final PlantillaFormulario plantilla : formulario.getDisenyoFormulario().getPlantillas()) {

				if (plantillaIncompleta(plantilla, pIdiomasTramiteVersion)) {
					final ErrorValidacion error = errorValidacion(plantilla.getIdentificador(),
							"tramitePasoRellenar.disenyoFormulario.listaPlantilla",
							new String[] { formulario.getIdentificador() },
							"tramitePasoRellenar.disenyoFormulario.formulario.listaPlantilla.idioma", pIdioma);

					// tipificamos el error
					error.setTipo(TypeErrorValidacion.FORMATEADOR);

					params.put("ID", String.valueOf(formulario.getIdFormularioInterno()));
					error.setParams(UtilJSON.toJSON(params));

					listaErrores.add(error);
				}
			}
		}

		params.put("TIPO_SCRIPT_FORMULARIO", UtilJSON.toJSON(TypeScriptFormulario.SCRIPT_PLANTILLA_PDF_DINAMICA));

		comprobarScript(formulario.getDisenyoFormulario().getScriptPlantilla(),
				"tramitePasoRellenar.disenyoFormulario.scriptPlantilla", new String[] { formulario.getIdentificador() },
				"literal.script.mensaje.formulario", "compilar.script.formulario", "dominio.script.formulario",
				"script.comentario", pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma,
				pListaDominiosNoUsados, listaErrores, UtilJSON.toJSON(params));

		for (DisenyoFormulario disenyo : disenyos) {
			if (disenyo.getPaginas() != null) {
				for (final PaginaFormulario paginaFormulario : disenyo.getPaginas()) {
					if (paginaFormulario.getLineas() != null) {
						for (final LineaComponentesFormulario linea : paginaFormulario.getLineas()) {
							if (linea.getComponentes() != null) {
								for (final ComponenteFormulario componente : linea.getComponentes()) {
									params.put("COMPONENTE", String.valueOf(componente.getCodigo()));

									if (componente instanceof ComponenteFormularioSeccion) {
										final ComponenteFormularioSeccion seccion = (ComponenteFormularioSeccion) componente;

										comprobarLiteral(seccion.getTexto(),
												"tramitePasoRellenar.disenyoFormulario.texto",
												new String[] {
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.seccion",
																pIdioma) + " " + seccion.getLetra(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.formulario.disenyoFormulario.pagina.elemento",
												pIdiomasTramiteVersion, pIdioma, listaErrores);

									} else if (componente instanceof ComponenteFormularioEtiqueta) {
										final ComponenteFormularioEtiqueta aviso = (ComponenteFormularioEtiqueta) componente;

										comprobarLiteral(aviso.getTexto(),
												"tramitePasoRellenar.disenyoFormulario.texto",
												new String[] { aviso.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.formulario.disenyoFormulario.pagina.elemento",
												pIdiomasTramiteVersion, pIdioma, listaErrores);

									} else if (componente instanceof ComponenteFormularioCampo) {
										final ComponenteFormularioCampo campo = (ComponenteFormularioCampo) componente;

										// texto
										comprobarLiteral(campo.getTexto(),
												"tramitePasoRellenar.disenyoFormulario.texto",
												new String[] { campo.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.formulario.disenyoFormulario.pagina.elemento",
												pIdiomasTramiteVersion, pIdioma, listaErrores);

										// ayuda online
										comprobarLiteral(campo.getAyuda(),
												"tramitePasoRellenar.disenyoFormulario.ayuda",
												new String[] { campo.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.formulario.disenyoFormulario.pagina.elemento",
												pIdiomasTramiteVersion, pIdioma, listaErrores);

										// script autorelleno
										params.put("TIPO_SCRIPT_FORMULARIO",
												UtilJSON.toJSON(TypeScriptFormulario.SCRIPT_AUTORELLENABLE));

										comprobarScript(campo.getScriptAutorrellenable(),
												"tramitePasoRellenar.disenyoFormulario.scriptAutorellenable",
												new String[] { campo.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.script.mensaje.formulario.disenyoFormulario.pagina",
												"compilar.script.formulario.disenyoFormulario.pagina",
												"dominio.script.formulario.disenyoFormulario.pagina",
												"script.comentario", pTramiteVersion.getListaAuxDominios(),
												pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados, listaErrores,
												UtilJSON.toJSON(params));

										// script soloLectura
										params.put("TIPO_SCRIPT_FORMULARIO",
												UtilJSON.toJSON(TypeScriptFormulario.SCRIPT_ESTADO));

										comprobarScript(campo.getScriptSoloLectura(),
												"tramitePasoRellenar.disenyoFormulario.scriptSoloLectura",
												new String[] { campo.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.script.mensaje.formulario.disenyoFormulario.pagina",
												"compilar.script.formulario.disenyoFormulario.pagina",
												"dominio.script.formulario.disenyoFormulario.pagina",
												"script.comentario", pTramiteVersion.getListaAuxDominios(),
												pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados, listaErrores,
												UtilJSON.toJSON(params));

										// script Validacion
										params.put("TIPO_SCRIPT_FORMULARIO",
												UtilJSON.toJSON(TypeScriptFormulario.SCRIPT_VALIDACION_CAMPO));

										comprobarScript(campo.getScriptValidacion(),
												"tramitePasoRellenar.disenyoFormulario.scriptValidacion",
												new String[] { campo.getIdComponente(),
														literales.getLiteral("validador",
																"tramitePasoRellenar.disenyoFormulario.pagina", pIdioma)
																+ " " + Integer.toString(paginaFormulario.getOrden()),
														formulario.getIdentificador() },
												"literal.script.mensaje.formulario.disenyoFormulario.pagina",
												"compilar.script.formulario.disenyoFormulario.pagina",
												"dominio.script.formulario.disenyoFormulario.pagina",
												"script.comentario", pTramiteVersion.getListaAuxDominios(),
												pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados, listaErrores,
												UtilJSON.toJSON(params));

										if (componente instanceof ComponenteFormularioCampoSelector) {
											final ComponenteFormularioCampoSelector selector = (ComponenteFormularioCampoSelector) componente;
											if (TypeListaValores.FIJA.equals(selector.getTipoListaValores())) {

												comprobarLiteralesListaFija(selector.getListaValorListaFija(),
														"tramitePasoRellenar.disenyoFormulario.listaValoresFija",
														new String[] { campo.getIdComponente(),
																literales.getLiteral("validador",
																		"tramitePasoRellenar.disenyoFormulario.pagina",
																		pIdioma) + " "
																		+ Integer.toString(paginaFormulario.getOrden()),
																formulario.getIdentificador() },
														"literal.listavalores.selector.formulario.disenyoFormulario.pagina",
														pIdiomasTramiteVersion, pIdioma, listaErrores);

											} else if (TypeListaValores.SCRIPT.equals(selector.getTipoListaValores())) {
												// selector.getScriptValoresPosibles()
												params.put("TIPO_SCRIPT_FORMULARIO",
														UtilJSON.toJSON(TypeScriptFormulario.SCRIPT_VALORES_POSIBLES));

												comprobarScript(selector.getScriptValoresPosibles(),
														"tramitePasoRellenar.disenyoFormulario.scriptValoresPosibles",
														new String[] { campo.getIdComponente(),
																literales.getLiteral("validador",
																		"tramitePasoRellenar.disenyoFormulario.pagina",
																		pIdioma) + " "
																		+ Integer.toString(paginaFormulario.getOrden()),
																formulario.getIdentificador() },
														"literal.script.mensaje.formulario.disenyoFormulario.pagina",
														"compilar.script.formulario.disenyoFormulario.pagina",
														"dominio.script.formulario.disenyoFormulario.pagina",
														"script.comentario", pTramiteVersion.getListaAuxDominios(),
														pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
														listaErrores, UtilJSON.toJSON(params));

											} else if (TypeListaValores.DOMINIO
													.equals(selector.getTipoListaValores())) {

												if (selector.getCodDominio() != null) {
													pListaDominiosNoUsados.removeIf(
															e -> e.getCodigo().equals(selector.getCodDominio()));
												}

												if (selector.getCodDominio() != null
														&& (pTramiteVersion.getListaDominios() == null
																|| !pTramiteVersion.getListaDominios()
																		.contains(selector.getCodDominio()))) {
													final ErrorValidacion error = errorValidacion(
															getIdentificadorDominio(selector.getCodDominio()),
															"tramitePasoRellenar.disenyoFormulario.dominio",
															new String[] { campo.getIdComponente(),
																	literales.getLiteral("validador",
																			"tramitePasoRellenar.disenyoFormulario.pagina",
																			pIdioma)
																			+ " "
																			+ Integer.toString(
																					paginaFormulario.getOrden()),
																	formulario.getIdentificador() },
															"dominio.formulario.disenyoFormulario.pagina.selector",
															pIdioma);

													// tipificamos el error
													error.setTipo(TypeErrorValidacion.DOMINIOS_ANYADIR);

													final Dominio dom = new Dominio();
													dom.setCodigo(selector.getCodDominio());
													error.setItem(dom);

													listaErrores.add(error);
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
		}

	}

	/**
	 * Valida el paso anexar.
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 * @param pasoAnexar paso anexar
	 */
	private void comprobarAnexar(final TramiteVersion pTramiteVersion, final List<String> pIdiomasTramiteVersion,
			final String pIdioma, final List<Dominio> pListaDominiosNoUsados, final List<ErrorValidacion> listaErrores,
			final TramitePasoAnexar pasoAnexar) {

		final ObjectNode params = JsonNodeFactory.instance.objectNode();

		params.put("TRAMITEVERSION", String.valueOf(pTramiteVersion.getCodigo()));
		params.put("TRAMITEPASO", String.valueOf(pasoAnexar.getCodigo()));

		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_LISTA_DINAMICA_ANEXOS));

		comprobarScript(pasoAnexar.getScriptAnexosDinamicos(), "tramitePasoAnexar.listaDinamicaAnexos",
				new String[] { literales.getLiteral("validador", "tramitePasoAnexar", pIdioma) },
				"literal.script.mensaje.paso", "compilar.script.paso", "dominio.script.paso", "script.comentario",
				pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
				listaErrores, UtilJSON.toJSON(params));

		if (pasoAnexar.getDocumentos() != null && !pasoAnexar.getDocumentos().isEmpty()) {
			for (final Documento documento : pasoAnexar.getDocumentos()) {

				// Comprueba si hay documentos no convertibles a pdf
				if (documento.isDebeConvertirPDF()) {

					final IDocumentConverterPlugin pluginConverterPDF = new OpenOfficeDocumentConverterPlugin();

					String[] extensiones = documento.getExtensiones().split(";");
					for (String extension : extensiones) {
						if (!pluginConverterPDF.isFileExtensionSupported(extension)) {
							String[] paramLiteral = { extension };
							final ErrorValidacion error = new ErrorValidacion(documento.getIdentificador(),
									literales.getLiteral("validador", "documento.extension", paramLiteral, pIdioma));
							error.setTipo(TypeErrorValidacion.DOCUMENTO_NO_CONVERTIBLE_PDF);

							listaErrores.add(error);
						}
					}
				}

				// Comprueba si se excede el tamanyo max definido en las
				String tamanyoMaxIndiv = cfService.listConfiguracionGlobal("sistramit.anexos.tamanyoMaximoIndividual")
						.get(0).getValor();
				Integer tamanyoMax = Integer.parseInt(tamanyoMaxIndiv.substring(0, tamanyoMaxIndiv.length() - 2));
				TypeTamanyo tipoTamanyo = TypeTamanyo
						.fromString(tamanyoMaxIndiv.substring(tamanyoMaxIndiv.length() - 2, tamanyoMaxIndiv.length()));
				if (documento.getTipoTamanyo().equals(tipoTamanyo)) {
					if (tamanyoMax < documento.getTamanyoMaximo()) {
						String[] paramsT = new String[1];
						paramsT[0] = tamanyoMaxIndiv;
						final ErrorValidacion error = new ErrorValidacion(documento.getIdentificador(),
								literales.getLiteral("validador", "documento.tamanyoMax", paramsT, pIdioma));
						error.setTipo(TypeErrorValidacion.DOCUMENTO_TAMANYO_SUPERIOR);
						error.setItem(documento);
						listaErrores.add(error);
					}
				} else {
					if (documento.getTipoTamanyo().equals(TypeTamanyo.MEGABYTES)) {
						if (tamanyoMax < (documento.getTamanyoMaximo() * 1024)) {
							String[] paramsT = new String[1];
							paramsT[0] = tamanyoMaxIndiv;
							final ErrorValidacion error = new ErrorValidacion(documento.getIdentificador(),
									literales.getLiteral("validador", "documento.tamanyoMax", paramsT, pIdioma));
							error.setTipo(TypeErrorValidacion.DOCUMENTO_TAMANYO_SUPERIOR);
							error.setItem(documento);
							listaErrores.add(error);

						}
					} else {
						if ((tamanyoMax * 1024) < documento.getTamanyoMaximo()) {
							String[] paramsT = new String[1];
							paramsT[0] = tamanyoMaxIndiv;
							final ErrorValidacion error = new ErrorValidacion(documento.getIdentificador(),
									literales.getLiteral("validador", "documento.tamanyoMax", paramsT, pIdioma));
							error.setTipo(TypeErrorValidacion.DOCUMENTO_TAMANYO_SUPERIOR);
							error.setItem(documento);
							listaErrores.add(error);

						}
					}
				}

				comprobarLiteral(documento.getDescripcion(), "tramitePasoAnexar.descripcion",
						new String[] { documento.getIdentificador() }, "literal.anexo.elemento", pIdiomasTramiteVersion,
						pIdioma, listaErrores);

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO));

				comprobarScript(documento.getScriptObligatoriedad(), "tramitePasoAnexar.scriptObligatoriedad",
						new String[] { documento.getIdentificador() }, "literal.script.mensaje.anexo",
						"compilar.script.anexo", "dominio.script.anexo", "script.comentario",
						pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
						listaErrores, UtilJSON.toJSON(params));

				comprobarLiteral(documento.getAyudaTexto(), "tramitePasoAnexar.mensajeHTML",
						new String[] { documento.getIdentificador() }, "literal.anexo.elemento", pIdiomasTramiteVersion,
						pIdioma, listaErrores);

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_FIRMANTES));

				comprobarScript(documento.getScriptFirmarDigitalmente(), "tramitePasoAnexar.scriptFirmarDigitalmente",
						new String[] { documento.getIdentificador() }, "literal.script.mensaje.anexo",
						"compilar.script.anexo", "dominio.script.anexo", "script.comentario",
						pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
						listaErrores, UtilJSON.toJSON(params));

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_VALIDAR_ANEXO));

				comprobarScript(documento.getScriptValidacion(), "tramitePasoAnexar.scripValidacion",
						new String[] { documento.getIdentificador() }, "literal.script.mensaje.anexo",
						"compilar.script.anexo", "dominio.script.anexo", "script.comentario",
						pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
						listaErrores, UtilJSON.toJSON(params));
			}
		}

	}

	/**
	 * Valida el paso tasa.
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 * @param pasoTasa paso tasa
	 */
	private void comprobarTasa(final TramiteVersion pTramiteVersion, final List<String> pIdiomasTramiteVersion,
			final String pIdioma, final List<Dominio> pListaDominiosNoUsados, final List<ErrorValidacion> listaErrores,
			final TramitePasoTasa pasoTasa) {

		final ObjectNode params = JsonNodeFactory.instance.objectNode();
		params.put("TRAMITEVERSION", String.valueOf(pTramiteVersion.getCodigo()));
		params.put("TRAMITEPASO", String.valueOf(pasoTasa.getCodigo()));

		if (pasoTasa.getTasas() != null) {
			for (final Tasa tasa : pasoTasa.getTasas()) {

				comprobarLiteral(tasa.getDescripcion(), "tramitePasoTasa.descripcion",
						new String[] { tasa.getIdentificador() }, "literal.tasa.elemento", pIdiomasTramiteVersion,
						pIdioma, listaErrores);

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO));

				comprobarScript(tasa.getScriptObligatoriedad(), "tramitePasoTasa.scriptObligatoriedad",
						new String[] { tasa.getIdentificador() }, "literal.script.mensaje.tasa", "compilar.script.tasa",
						"dominio.script.tasa", "script.comentario", pTramiteVersion.getListaAuxDominios(),
						pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados, listaErrores, UtilJSON.toJSON(params));

				params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_DATOS_PAGO));

				comprobarScript(tasa.getScriptPago(), "tramitePasoTasa.scriptPago",
						new String[] { tasa.getIdentificador() }, "literal.script.mensaje.tasa", "compilar.script.tasa",
						"dominio.script.tasa", "script.comentario", pTramiteVersion.getListaAuxDominios(),
						pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados, listaErrores, UtilJSON.toJSON(params));

			}
		}

	}

	/**
	 * Valida el paso registrar formulario.
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 * @param pasoRegistrar paso registrar
	 */
	private void comprobarRegistrarFormulario(final TramiteVersion pTramiteVersion,
			final List<String> pIdiomasTramiteVersion, final String pIdioma, final List<Dominio> pListaDominiosNoUsados,
			final List<ErrorValidacion> listaErrores, final TramitePasoRegistrar pasoRegistrar) {

		final Long idEntidad = tramiteDao.getById(pTramiteVersion.getIdTramite()).getIdEntidad();
		final Entidad entidad = entidadDao.getById(idEntidad);

		final ObjectNode params = JsonNodeFactory.instance.objectNode();
		params.put("TRAMITEVERSION", String.valueOf(pTramiteVersion.getCodigo()));
		params.put("TRAMITEPASO", String.valueOf(pasoRegistrar.getCodigo()));

		if (pasoRegistrar.getEnvioRemoto() == null && StringUtils.isEmpty(pasoRegistrar.getCodigoOficinaRegistro())
				&& !entidad.isRegistroCentralizado()) {
			final ErrorValidacion error = errorValidacion("tramitePasoRegistrar.oficinaRegistro",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
					"vacio.registrar", pIdioma);

			// tipificamos el error
			error.setTipo(TypeErrorValidacion.DATOS_REGISTRO);
			error.setItem(pasoRegistrar);
			listaErrores.add(error);
		}

		if (pasoRegistrar.getEnvioRemoto() == null && StringUtils.isEmpty(pasoRegistrar.getCodigoLibroRegistro())
				&& !entidad.isRegistroCentralizado()) {
			final ErrorValidacion error = errorValidacion("tramitePasoRegistrar.libroRegistro",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
					"vacio.registrar", pIdioma);

			// tipificamos el error
			error.setTipo(TypeErrorValidacion.DATOS_REGISTRO);
			error.setItem(pasoRegistrar);
			listaErrores.add(error);
		}

		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_PARAMETROS_REGISTRO));

		comprobarScript(pasoRegistrar.getScriptDestinoRegistro(), "tramitePasoRegistrar.scriptDestinoRegistro",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.script.mensaje.paso", "compilar.script.paso", "dominio.script.paso", "script.comentario",
				pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
				listaErrores, UtilJSON.toJSON(params));

		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_PRESENTADOR_REGISTRO));

		comprobarScript(pasoRegistrar.getScriptPresentador(), "tramitePasoRegistrar.scriptPresentador",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.script.mensaje.paso", "compilar.script.paso", "dominio.script.paso", "script.comentario",
				pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
				listaErrores, UtilJSON.toJSON(params));

		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_REPRESENTACION_REGISTRO));

		comprobarScript(pasoRegistrar.getScriptRepresentante(), "tramitePasoRegistrar.scriptRepresentante",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.script.mensaje.paso", "compilar.script.paso", "dominio.script.paso", "script.comentario",
				pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
				listaErrores, UtilJSON.toJSON(params));

		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_PERMITIR_REGISTRO));

		comprobarScript(pasoRegistrar.getScriptValidarRegistrar(), "tramitePasoRegistrar.scriptValidarRegistrar",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.script.mensaje.paso", "compilar.script.paso", "dominio.script.paso", "script.comentario",
				pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
				listaErrores, UtilJSON.toJSON(params));

		params.put("TIPO_SCRIPT_FLUJO", UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_AVISO));

		comprobarScript(pasoRegistrar.getScriptAlFinalizar(), "tramitePasoRegistrar.scriptAlFinalizar",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.script.mensaje.paso", "compilar.script.paso", "dominio.script.paso", "script.comentario",
				pTramiteVersion.getListaAuxDominios(), pIdiomasTramiteVersion, pIdioma, pListaDominiosNoUsados,
				listaErrores, UtilJSON.toJSON(params));

		comprobarLiteralHTML(pasoRegistrar.getInstruccionesFinTramitacion(),
				"tramitePasoRegistrar.instruccionesFinTramitacion",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.paso.elemento", pIdiomasTramiteVersion, pIdioma, listaErrores);

		comprobarLiteralHTML(pasoRegistrar.getInstruccionesPresentacion(),
				"tramitePasoRegistrar.instruccionesPresentacion",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.paso.elemento", pIdiomasTramiteVersion, pIdioma, listaErrores);

		comprobarLiteralHTML(pasoRegistrar.getInstruccionesSubsanacion(),
				"tramitePasoRegistrar.instruccionesSubsanacion",
				new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) },
				"literal.paso.elemento", pIdiomasTramiteVersion, pIdioma, listaErrores);

		comprobarVariablesArea(pasoRegistrar.getEnvioRemoto(), listaErrores, pIdioma);

	}

	/**
	 * Valida un script.
	 *
	 * @param pScript                   script a validar
	 * @param pLiteralScript            literal del script
	 * @param pOpciones                 localizacion del script
	 * @param pLiteralOpcionesLiterales literal error
	 * @param pLiteralOpcionesCompilar  literal error compilar
	 * @param pLiteralOpcionesDominios  literal error dominios
	 * @param pListaDominios            lista dominios de version
	 * @param pIdiomasTramiteVersion    idiomas de tramite version
	 * @param pIdioma                   idioma
	 * @param listaErrores              lista de errores de validacion
	 */
	private void comprobarScript(final Script pScript, final String pLiteralScript, final String[] pOpciones,
			final String pLiteralOpcionesLiterales, final String pLiteralOpcionesCompilar,
			final String pLiteralOpcionesDominios, final String pLiteralOpcionesCometariosFinal,
			List<Dominio> pListaDominios, final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<Dominio> pListaDominiosNoUsados, final List<ErrorValidacion> listaErrores, final String params) {
		if (pScript != null) {
			comprobarLiteralesScript(pScript, pLiteralScript, pOpciones, pLiteralOpcionesLiterales,
					pIdiomasTramiteVersion, pIdioma, listaErrores);

			comprobarCompilarScript(pScript, pLiteralScript, pOpciones, pLiteralOpcionesCompilar, pIdioma, listaErrores,
					params);

			comprobarDominiosScript(pScript, pListaDominios, pLiteralScript, pOpciones, pLiteralOpcionesDominios,
					pIdioma, pListaDominiosNoUsados, listaErrores);

			comprobarComentariosScript(pScript, pLiteralScript, pOpciones, pLiteralOpcionesCometariosFinal, pIdioma,
					listaErrores, params);
		}
	}

	private void comprobarComentariosScript(Script pScript, String pLiteralScript, String[] pOpciones,
			String pLiteralOpcionesCometariosFinal, String pIdioma, List<ErrorValidacion> listaErrores, String params) {
		if (pScript.getContenido() != null) {
			final boolean comentariosWarning = UtilScripts.buscarComentariosScript(pScript.getContenido());
			if (comentariosWarning) {
				final ErrorValidacion error = errorValidacion(pLiteralScript, pOpciones,
						pLiteralOpcionesCometariosFinal, pIdioma);
//				error.setDescripcion(literales.getLiteral("validador", "script.comentario", pIdioma));
//				if (pScript.getCodigo() != null) {
//					error.setElemento(pScript.getCodigo().toString());
//				}
				error.setTipo(TypeErrorValidacion.SCRIPTS);
				error.setItem(pScript);
				error.setParams(params);
				listaErrores.add(error);
			}
		}

	}

	/**
	 * Valida un literal.
	 *
	 * @param pLiteral               literal
	 * @param pTextoLiteral          texto literal
	 * @param pOpciones              opciones
	 * @param pLiteralOpciones       literal opciones
	 * @param pIdiomasTramiteVersion idiomas tramite version
	 * @param pIdioma                idioma
	 * @param listaErrores           lista errores
	 */
	private void comprobarLiteralAux(final Literal pLiteral, final String pTextoLiteral, final String[] pOpciones,
			final String pLiteralOpciones, final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<ErrorValidacion> listaErrores, final TypeErrorValidacion pTipoError) {
		if (literalIncompleto(pLiteral, pIdiomasTramiteVersion)) {
			final ErrorValidacion error = errorValidacion(pTextoLiteral, pOpciones, pLiteralOpciones, pIdioma);

			// tipificamos el error
			error.setTipo(pTipoError);
			error.setItem(pLiteral);

			listaErrores.add(error);
		}
	}

	private void comprobarLiteral(final Literal pLiteral, final String pTextoLiteral, final String[] pOpciones,
			final String pLiteralOpciones, final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<ErrorValidacion> listaErrores) {

		comprobarLiteralAux(pLiteral, pTextoLiteral, pOpciones, pLiteralOpciones, pIdiomasTramiteVersion, pIdioma,
				listaErrores, TypeErrorValidacion.LITERALES);

	}

	private void comprobarLiteralHTML(final Literal pLiteral, final String pTextoLiteral, final String[] pOpciones,
			final String pLiteralOpciones, final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<ErrorValidacion> listaErrores) {
		comprobarLiteralAux(pLiteral, pTextoLiteral, pOpciones, pLiteralOpciones, pIdiomasTramiteVersion, pIdioma,
				listaErrores, TypeErrorValidacion.LITERALES_HTML);
	}

	/**
	 * Valida los literales de un script.
	 *
	 * @param pScript                script
	 * @param pLiteralScript         literal script
	 * @param pOpciones              opciones
	 * @param pLiteralOpciones       literal opciones
	 * @param pIdiomasTramiteVersion idiomas tramite version
	 * @param pIdioma                idioma
	 * @param listaErrores           lista errores
	 */
	private void comprobarLiteralesScript(final Script pScript, final String pLiteralScript, final String[] pOpciones,
			final String pLiteralOpciones, final List<String> pIdiomasTramiteVersion, final String pIdioma,
			final List<ErrorValidacion> listaErrores) {

		if (pScript != null && pScript.getMensajes() != null && !pScript.getMensajes().isEmpty()) {
			for (final LiteralScript mensaje : pScript.getMensajes()) {
				if (literalIncompleto(mensaje.getLiteral(), pIdiomasTramiteVersion)) {

					final ErrorValidacion error = errorValidacion(mensaje.getIdentificador(), pLiteralScript, pOpciones,
							pLiteralOpciones, pIdioma);
					// tipificamos el error
					error.setTipo(TypeErrorValidacion.LITERALES);
					// literal afectado
					error.setItem(mensaje.getLiteral());
					listaErrores.add(error);

				}
			}
		}

	}

	/**
	 * Construye Error validacion.
	 *
	 * @param pElemento        elemento
	 * @param pLiteral         literal
	 * @param pOpciones        opciones
	 * @param pLiteralOpciones literal opciones
	 * @param pIdioma          idioma
	 * @return error validacion
	 */
	private ErrorValidacion errorValidacion(final String pElemento, final String pLiteral, final String[] pOpciones,
			final String pLiteralOpciones, final String pIdioma) {
		return errorValidacion(pElemento, pLiteral, pOpciones, pLiteralOpciones, pIdioma, true);
	}

	/**
	 * Construye Error validacion.
	 *
	 * @param pElemento        elemento
	 * @param pLiteral         literal
	 * @param pOpciones        opciones
	 * @param pLiteralOpciones literal opciones
	 * @param pIdioma          idioma
	 * @param pMostrarElemento mostrar elemento
	 * @return error validacion
	 */
	private ErrorValidacion errorValidacion(final String pElemento, final String pLiteral, final String[] pOpciones,
			final String pLiteralOpciones, final String pIdioma, final boolean pMostrarElemento) {
		final String literal = literales.getLiteral("validador", pLiteral, pIdioma);

		final StringBuilder elemento = new StringBuilder();

		if (pOpciones != null) {
			elemento.append(pOpciones[pOpciones.length - 1] + " > ");

			for (int i = pOpciones.length - 2; i >= 0; i--) {
				elemento.append(pOpciones[i] + " > ");
			}
		}

		if (pMostrarElemento) {
			elemento.append(literal + " > " + pElemento);
		} else {
			elemento.append(literal);
		}

		String[] parametros = { pElemento, literal };
		parametros = ArrayUtils.addAll(parametros, pOpciones);

		final String descripcion = literales.getLiteral("validador", pLiteralOpciones, parametros, pIdioma);

		return new ErrorValidacion(elemento.toString(), descripcion);
	}

	/**
	 * Construye Error validacion.
	 *
	 * @param pLiteral         literal
	 * @param pOpciones        opciones
	 * @param pLiteralOpciones literal opciones
	 * @param pIdioma          idioma
	 * @return error validacion
	 */
	private ErrorValidacion errorValidacion(final String pLiteral, final String[] pOpciones,
			final String pLiteralOpciones, final String pIdioma) {
		final String literalElemento = literales.getLiteral("validador", pLiteral, pIdioma);

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

	/**
	 * Valida los literales de una lista fija.
	 *
	 * @param listaFija              lista fija
	 * @param pLiteralListaFija      literal lista fija
	 * @param pOpciones              opciones
	 * @param pLiteralOpciones       literal opciones
	 * @param pIdiomasTramiteVersion idiomas tramite version
	 * @param pIdioma                idioma
	 * @param listaErrores           lista errores
	 */
	private void comprobarLiteralesListaFija(final List<ValorListaFija> listaFija, final String pLiteralListaFija,
			final String[] pOpciones, final String pLiteralOpciones, final List<String> pIdiomasTramiteVersion,
			final String pIdioma, final List<ErrorValidacion> listaErrores) {

		if (listaFija != null && !listaFija.isEmpty()) {
			for (final ValorListaFija valorListaFija : listaFija) {
				if (literalIncompleto(valorListaFija.getDescripcion(), pIdiomasTramiteVersion)) {

					final ErrorValidacion error = errorValidacion(valorListaFija.getValor(), pLiteralListaFija,
							pOpciones, pLiteralOpciones, pIdioma);
					// tipificamos el error
					error.setTipo(TypeErrorValidacion.LITERALES);
					error.setItem(valorListaFija.getDescripcion());

					listaErrores.add(error);

				}
			}
		}

	}

	/**
	 * Valida si compila correctamente un script.
	 *
	 * @param pScript          script
	 * @param pLiteralScript   literal script
	 * @param pOpciones        opciones
	 * @param pLiteralOpciones literal opciones
	 * @param pIdioma          idioma
	 * @param listaErrores     lista errores
	 */
	private void comprobarCompilarScript(final Script pScript, final String pLiteralScript, final String[] pOpciones,
			final String pLiteralOpciones, final String pIdioma, final List<ErrorValidacion> listaErrores,
			final String params) {

		if (pScript != null && pScript.getContenido() != null) {
			final String errorCompilacion = compilarScript(pScript.getContenido());
			if (StringUtils.isNoneEmpty(errorCompilacion)) {
				final ErrorValidacion error = errorValidacion(errorCompilacion, pLiteralScript, pOpciones,
						pLiteralOpciones, pIdioma, false);

				// tipificamos el error
				error.setTipo(TypeErrorValidacion.SCRIPTS);

				// guardamos item afectado
				error.setItem(pScript);
				// parametros apertura scriptdialog
				error.setParams(params);

				listaErrores.add(error);
			}
		}

	}

	/**
	 * Compilar script.
	 *
	 * @param script script
	 * @return resultado compilacion
	 */
	private String compilarScript(final String script) {
		final StringBuilder error = new StringBuilder();
		try {
			final ScriptEngineManager engineManager = new ScriptEngineManager();
			final StringBuilder sb = new StringBuilder();
			sb.append("function ejecutarScript() { ").append(script).append("\n}; ejecutarScript();");
			final ScriptEngine jsEngine = engineManager.getEngineByName("JavaScript");
			final Compilable compilingEngine = (Compilable) jsEngine;
			compilingEngine.compile(sb.toString());
		} catch (final ScriptException e) {
			// Gestionar excepcion: sale el num de linea y columna dnd hay error
			// log.debug("Error script " + e.getLineNumber() + " - " + e.getColumnNumber() +
			// " : " + e.getMessage());
			// e.printStackTrace();
			if (e.getLineNumber() >= 0) {
				error.append(" línea " + e.getLineNumber());
				if (e.getColumnNumber() >= 0) {
					error.append(" línea " + e.getColumnNumber());
				}
				error.append(" : ");
			}
			error.append(e.getMessage());
		}
		return error.toString();
	}

	/**
	 * Valida los dominios invocados en un script.
	 *
	 * @param pScript          script
	 * @param pListaDominios   lista dominios
	 * @param pLiteralScript   literal script
	 * @param pOpciones        opciones
	 * @param pLiteralOpciones literal opciones
	 * @param pIdioma          idioma
	 * @param listaErrores     lista errores
	 */
	private void comprobarDominiosScript(final Script pScript, final List<Dominio> pListaDominios,
			final String pLiteralScript, final String[] pOpciones, final String pLiteralOpciones, final String pIdioma,
			final List<Dominio> pListaDominiosNoUsados, final List<ErrorValidacion> listaErrores) {
		if (pScript != null && pScript.getContenido() != null) {

			final List<String> listaDom = buscarInvocacionesDominios(pScript.getContenido());

			if (!listaDom.isEmpty()) {
				if (pListaDominiosNoUsados != null && !pListaDominiosNoUsados.isEmpty()) {
					for (final String dominioNombre : listaDom) {
						pListaDominiosNoUsados.removeIf(e -> e.getIdentificadorCompuesto().equals(dominioNombre));
					}
				}
				for (final Dominio dominio : pListaDominios) {
					listaDom.removeIf(

							e -> e.equals(dominio.getIdentificadorCompuesto()));

					if (listaDom.isEmpty())

					{
						break;
					}
				}

				if (!listaDom.isEmpty()) {
					for (final String dominio : listaDom) {
						if (!dominio.contains(":")) {
							// Debería contener la tipica estructura de GLOBAL:ID , ID_ENTIDAD:ID O
							// ID_ENTIDAD:ID_AREA#ID
							final ErrorValidacion error = errorValidacion(dominio, pLiteralScript, pOpciones,
									"dominio.script.identificadorNoCompuesto", pIdioma, false);

							// tipificamos el error
							error.setTipo(TypeErrorValidacion.SCRIPT_DOMINIO_ID_NO_COMPUESTO);

							final Dominio dom = new Dominio();
							dom.setIdentificador(dominio);
							error.setItem(dom);

							listaErrores.add(error);
						} else {
							// Sino, es que no está añadido
							final ErrorValidacion error = errorValidacion(dominio, pLiteralScript, pOpciones,
									pLiteralOpciones, pIdioma, false);

							// tipificamos el error
							error.setTipo(TypeErrorValidacion.DOMINIOS_ANYADIR);

							final Dominio dom = new Dominio();
							dom.setIdentificador(dominio);
							error.setItem(dom);

							listaErrores.add(error);
						}
					}
				}

			}
		}
	}

	/**
	 * Obtiene el valor de idiomas soportados para tramite version.
	 *
	 * @param tramiteVersion tramite version
	 * @return idiomas soportados
	 */
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

	/**
	 * Recupera disenyo formularios.
	 *
	 * @param pTramiteVersion tramite version
	 */
	private void recuperaDisenyoFormularios(final TramiteVersion pTramiteVersion) {
		if (!pTramiteVersion.getListaPasos().isEmpty()) {
			for (final TramitePaso paso : pTramiteVersion.getListaPasos()) {
				if (paso instanceof TramitePasoRellenar) {
					final List<FormularioTramite> formularios = ((TramitePasoRellenar) paso).getFormulariosTramite();
					if (formularios != null && !formularios.isEmpty()) {
						for (final FormularioTramite formularioTramite : formularios) {
							if (formularioTramite.getIdFormularioInterno() != null) {
								final DisenyoFormulario disenyoFormulario = formularioInternoDao
										.getFormularioCompletoById(formularioTramite.getIdFormularioInterno(), false);
								if (disenyoFormulario != null) {
									formularioTramite.setDisenyoFormulario(disenyoFormulario);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Comprueba si hay formularios.
	 *
	 * @param pTramiteVersion tramite version
	 */
	private boolean tieneFormularios(final TramiteVersion pTramiteVersion) {
		if (!pTramiteVersion.getListaPasos().isEmpty()) {
			for (final TramitePaso paso : pTramiteVersion.getListaPasos()) {
				if (paso instanceof TramitePasoRellenar) {
					final List<FormularioTramite> formularios = ((TramitePasoRellenar) paso).getFormulariosTramite();
					if (formularios != null && !formularios.isEmpty()) {
						return true;

					}
				}
			}
		}
		return false;
	}

	/**
	 * Comprueba si el literal está incompleto.
	 *
	 * @param literal  literal
	 * @param pIdiomas idiomas a comprobar
	 * @return true, si esta incompleto
	 */
	private boolean literalIncompleto(final Literal literal, final List<String> pIdiomas) {
		if (literal != null) {
			if (literal.getTraducciones() != null) {
				for (final String idioma : pIdiomas) {
					if (!literal.contains(idioma) || literal.getTraduccion(idioma).isEmpty()) {
						return true;
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Buscar las invocaciones de dominios en un script.
	 *
	 * @param script script
	 * @return lista de dominios invocados
	 */
	@Override
	public List<String> buscarInvocacionesDominios(final String script) {
		final List<String> dominios = new ArrayList<>();
		if (StringUtils.isNotBlank(script)) {
			buscarOcurrencias(dominios, PLUGINDOMI_INVOCAR_PATTERN, script);
		}
		return dominios;
	}

	/**
	 * Buscar ocurrencias en un script.
	 *
	 * @param deps    deps
	 * @param pattern pattern
	 * @param script  script
	 */
	private void buscarOcurrencias(final List<String> deps, final Pattern pattern, final String script) {

		// Eliminamos los comentarios
		String scriptSearch = eliminarBloqueComentarios(script);

		// Quitamos todos los espacios en blanco y saltos de línea
		scriptSearch = scriptSearch.replaceAll("\\r*\\n", "");
		scriptSearch = scriptSearch.replaceAll("\\s", "");

		final Matcher matcher = pattern.matcher(scriptSearch);
		extraerIdsFormularios(deps, matcher);
	}

	/**
	 * Elimina de un script el bloque de comentarios.
	 *
	 * @param script script
	 * @return script
	 */
	private String eliminarBloqueComentarios(final String script) {
		String scriptSearch = script;
		// Eliminamos bloques comentarios
		int indx = scriptSearch.indexOf("/*");
		while (indx > -1) {
			final int indxFin = scriptSearch.indexOf("*/", indx);
			if (indxFin > -1) {
				final String nuevaInicio = scriptSearch.substring(0, indx);
				final String nuevaFin = scriptSearch.substring(indxFin + "*/".length(), scriptSearch.length());
				scriptSearch = nuevaInicio + nuevaFin;
			} else {
				final String nuevaInicio = scriptSearch.substring(0, indx);
				scriptSearch = nuevaInicio;
			}
			indx = scriptSearch.indexOf("/*");

		}

		scriptSearch = scriptSearch.replaceAll("(?://.*)|(/\\*(?:.|[\\n\\r])*?\\*/)", "");

		return scriptSearch;

	}

	/**
	 * Extraer id.
	 *
	 * @param deps    deps
	 * @param matcher expresio
	 */
	public void extraerIdsFormularios(final List<String> deps, final Matcher matcher) {
		while (matcher.find()) {
			final String sentencia = matcher.group();
			final String[] params = sentencia.split("\\(");
			final String cadInsertar = params[1].replaceAll("['|\"]", "");
			if (!deps.contains(cadInsertar)) {
				deps.add(cadInsertar);
			}
		}
	}

	/**
	 * Obtiene el valor del identificador del dominio.
	 *
	 * @param pCodDominio cod. dominio
	 * @return el valor del identificador
	 */
	public String getIdentificadorDominio(final Long pCodDominio) {
		String resultado = null;

		if (pCodDominio != null) {
			final Dominio dominio = dominioDao.getByCodigo(pCodDominio);
			resultado = dominio.getIdentificador();
		}

		return resultado;
	}

	/**
	 * Comprueba si la plantilla esta incompleta.
	 *
	 * @param plantilla plantilla
	 * @param pIdiomas  idiomas de la version
	 * @return true, si esta incompleta
	 */
	private boolean plantillaIncompleta(final PlantillaFormulario plantilla, final List<String> pIdiomas) {
		if (plantilla != null) {

			final List<String> listaPlantillaIdiomas = new ArrayList<>();
			for (final PlantillaIdiomaFormulario plantillaIdioma : plantilla.getPlantillasIdiomaFormulario()) {
				listaPlantillaIdiomas.add(plantillaIdioma.getIdioma());
			}

			for (final String idioma : pIdiomas) {

				if (!listaPlantillaIdiomas.contains(idioma) || listaPlantillaIdiomas.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<ErrorValidacion> checkDominioNoUtilizado(Long idDominio, Long idTramiteVersion, final String pIdioma) {
		List<ErrorValidacion> listaErrores = new ArrayList<>();
		Dominio dominio = dominioDao.getByCodigo(idDominio);
		String identificador = dominio.getIdentificadorCompuesto();
		TramiteVersion tramiteVersion = tramiteDao.getTramiteVersion(idTramiteVersion);
		// recupera pasos si no los tiene cargados
		if (tramiteVersion.getListaPasos() == null || tramiteVersion.getListaPasos().isEmpty()) {
			tramiteVersion.setListaPasos(tramitePasoDao.getTramitePasos(tramiteVersion.getCodigo()));
		}

		if (UtilScripts.isDominioUtilizado(tramiteVersion.getScriptPersonalizacion(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) dominio,
					"propiedades.scriptPersonalizacion",
					new String[] { literales.getLiteral("validador", "propiedades", pIdioma) }, pIdioma, false, "");
			listaErrores.add(error);
		}

		if (UtilScripts.isDominioUtilizado(tramiteVersion.getScriptInicializacionTramite(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) dominio,
					"propiedades.scriptInicializacionTramite",
					new String[] { literales.getLiteral("validador", "propiedades", pIdioma) }, pIdioma, false, "");
			listaErrores.add(error);
		}

		if (!tramiteVersion.getListaPasos().isEmpty()) {
			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {
				// literales debe saber
				if (paso instanceof TramitePasoDebeSaber) {
					checkDominioNoUtilizadoDebeSaber(identificador, listaErrores, pIdioma, (TramitePasoDebeSaber) paso);
				} else if (paso instanceof TramitePasoRellenar) {
					checkDominioNoUtilizadoPasoRellenar(tramiteVersion, identificador, listaErrores, pIdioma,
							(TramitePasoRellenar) paso, dominio.getCodigo());
				} else if (paso instanceof TramitePasoAnexar) {
					checkDominioNoUtilizadoAnexar(identificador, listaErrores, pIdioma, (TramitePasoAnexar) paso);
				} else if (paso instanceof TramitePasoTasa) {
					checkDominioNoUtilizadoTasa(identificador, listaErrores, pIdioma, (TramitePasoTasa) paso);
				} else if (paso instanceof TramitePasoRegistrar) {
					checkDominioNoUtilizadoRegistrar(identificador, listaErrores, pIdioma, (TramitePasoRegistrar) paso);
				}
			}
		}

		return listaErrores;
	}

	/**
	 * Checkea si un dominio es utilizado en el paso rellenar.
	 *
	 * @param pTramiteVersion
	 * @param identificador
	 * @param listaErrores
	 * @param pIdioma
	 * @param pasoRellenar
	 * @param idDominio
	 */
	private void checkDominioNoUtilizadoPasoRellenar(final TramiteVersion pTramiteVersion, String identificador,
			List<ErrorValidacion> listaErrores, final String pIdioma, final TramitePasoRellenar pasoRellenar,
			final Long idDominio) {

		if (pasoRellenar.getFormulariosTramite() != null) {

			for (final FormularioTramite formulario : pasoRellenar.getFormulariosTramite()) {
				if (UtilScripts.isDominioUtilizado(formulario.getScriptObligatoriedad(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) formulario,
							"tramitePasoRellenar.scriptObligatoriedad", new String[] { formulario.getIdentificador() },
							pIdioma, false, "");
					listaErrores.add(error);
				}

				if (UtilScripts.isDominioUtilizado(formulario.getScriptFirma(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) formulario,
							"tramitePasoRellenar.scriptFirma", new String[] { formulario.getIdentificador() }, pIdioma,
							false, "");
					listaErrores.add(error);
				}

				if (UtilScripts.isDominioUtilizado(formulario.getScriptDatosIniciales(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) formulario,
							"tramitePasoRellenar.scriptDatosIniciales", new String[] { formulario.getIdentificador() },
							pIdioma, false, "");
					listaErrores.add(error);
				}

				if (UtilScripts.isDominioUtilizado(formulario.getScriptParametros(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) formulario,
							"tramitePasoRellenar.scriptParametros", new String[] { formulario.getIdentificador() },
							pIdioma, false, "");
					listaErrores.add(error);
				}

				if (UtilScripts.isDominioUtilizado(formulario.getScriptRetorno(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) formulario,
							"tramitePasoRellenar.scriptRetorno", new String[] { formulario.getIdentificador() },
							pIdioma, false, "");
					listaErrores.add(error);
				}

				// DISEÑO DEL FORMULARIO
				// propiedades del formulario
				if (formulario.getIdFormularioInterno() != null) {
					final DisenyoFormulario disenyoFormulario = formularioInternoDao
							.getFormularioCompletoById(formulario.getIdFormularioInterno(), false);
					if (disenyoFormulario != null) {
						formulario.setDisenyoFormulario(disenyoFormulario);
						checkDominioNoUtilizadoDisenyoFormulario(pTramiteVersion, identificador, listaErrores, pIdioma,
								formulario, idDominio);
					}
				}
			}
		}
	}

	/**
	 * Checkea si un dominio es utilizado en un diseño de formulario.
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 * @param formulario formulario
	 */
	private void checkDominioNoUtilizadoDisenyoFormulario(final TramiteVersion pTramiteVersion,
			final String identificador, List<ErrorValidacion> listaErrores, final String pIdioma,
			final FormularioTramite formulario, final Long idDominio) {

		List<DisenyoFormulario> disenyos = new ArrayList<>();
		disenyos.add(formulario.getDisenyoFormulario());
		List<SeccionReutilizable> seccionesReutilizables = tramiteDao
				.getSeccionesReutilizableByTramite(pTramiteVersion.getCodigo());
		if (seccionesReutilizables != null && !seccionesReutilizables.isEmpty()) {
			for (SeccionReutilizable seccion : seccionesReutilizables) {
				disenyos.add(formularioInternoDao.getFormularioCompletoById(seccion.getIdFormularioAsociado(), true));
			}
		}

		for (DisenyoFormulario disenyo : disenyos) {
			if (disenyo.getPaginas() != null) {

				for (final PaginaFormulario paginaFormulario : disenyo.getPaginas()) {

					if (UtilScripts.isDominioUtilizado(paginaFormulario.getScriptValidacion(), identificador)) {
						final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) formulario,
								"tramitePasoRellenar.disenyoFormulario.scriptValidacion",
								new String[] { paginaFormulario.getIdentificador() }, pIdioma, false, "");
						listaErrores.add(error);
					}

					if (UtilScripts.isDominioUtilizado(paginaFormulario.getScriptNavegacion(), identificador)) {
						final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) formulario,
								"tramitePasoRellenar.disenyoFormulario.scriptNavegacion",
								new String[] { paginaFormulario.getIdentificador() }, pIdioma, false, "");
						listaErrores.add(error);
					}
				}
			}

			if (UtilScripts.isDominioUtilizado(disenyo.getScriptPlantilla(), identificador)) {
				final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) formulario,
						"tramitePasoRellenar.disenyoFormulario.scriptPlantilla",
						new String[] { formulario.getIdentificador() }, pIdioma, false, "");
				listaErrores.add(error);
			}

			if (disenyo.getPaginas() != null) {
				for (final PaginaFormulario paginaFormulario : disenyo.getPaginas()) {
					if (paginaFormulario.getLineas() != null) {
						for (final LineaComponentesFormulario linea : paginaFormulario.getLineas()) {
							if (linea.getComponentes() != null) {
								for (final ComponenteFormulario componente : linea.getComponentes()) {

									if (componente instanceof ComponenteFormularioCampo) {
										final ComponenteFormularioCampo campo = (ComponenteFormularioCampo) componente;

										if (UtilScripts.isDominioUtilizado(campo.getScriptAutorrellenable(),
												identificador)) {
											final ErrorValidacion error = getInstanceErrorValidacion(
													(ModelApi) formulario,
													"tramitePasoRellenar.disenyoFormulario.scriptAutorellenable",
													new String[] { campo.getIdComponente(),
															literales.getLiteral("validador",
																	"tramitePasoRellenar.disenyoFormulario.pagina",
																	pIdioma) + " "
																	+ Integer.toString(paginaFormulario.getOrden()),
															formulario.getIdentificador() },
													pIdioma, false, "");
											listaErrores.add(error);
										}

										if (UtilScripts.isDominioUtilizado(campo.getScriptSoloLectura(),
												identificador)) {
											final ErrorValidacion error = getInstanceErrorValidacion(
													(ModelApi) formulario,
													"tramitePasoRellenar.disenyoFormulario.scriptSoloLectura",
													new String[] { campo.getIdComponente(),
															literales.getLiteral("validador",
																	"tramitePasoRellenar.disenyoFormulario.pagina",
																	pIdioma) + " "
																	+ Integer.toString(paginaFormulario.getOrden()),
															formulario.getIdentificador() },
													pIdioma, false, "");
											listaErrores.add(error);
										}

										if (UtilScripts.isDominioUtilizado(campo.getScriptValidacion(),
												identificador)) {
											final ErrorValidacion error = getInstanceErrorValidacion(
													(ModelApi) formulario,
													"tramitePasoRellenar.disenyoFormulario.scriptValidacion",
													new String[] { campo.getIdComponente(),
															literales.getLiteral("validador",
																	"tramitePasoRellenar.disenyoFormulario.pagina",
																	pIdioma) + " "
																	+ Integer.toString(paginaFormulario.getOrden()),
															formulario.getIdentificador() },
													pIdioma, false, "");
											listaErrores.add(error);
										}

										if (componente instanceof ComponenteFormularioCampoSelector) {
											final ComponenteFormularioCampoSelector selector = (ComponenteFormularioCampoSelector) componente;
											if (TypeListaValores.SCRIPT.equals(selector.getTipoListaValores())) {

												if (UtilScripts.isDominioUtilizado(selector.getScriptValoresPosibles(),
														identificador)) {
													final ErrorValidacion error = getInstanceErrorValidacion(
															(ModelApi) formulario,
															"tramitePasoRellenar.disenyoFormulario.scriptValoresPosibles",
															new String[] { campo.getIdComponente(),
																	literales.getLiteral("validador",
																			"tramitePasoRellenar.disenyoFormulario.pagina",
																			pIdioma)
																			+ " "
																			+ Integer.toString(
																					paginaFormulario.getOrden()),
																	formulario.getIdentificador() },
															pIdioma, false, "");
													listaErrores.add(error);
												}

											} else if (TypeListaValores.DOMINIO.equals(selector.getTipoListaValores())
													&& selector.getCodDominio() != null
													&& selector.getCodDominio().compareTo(idDominio) == 0) {
												final ErrorValidacion error = getInstanceErrorValidacion(
														(ModelApi) formulario,
														"tramitePasoRellenar.disenyoFormulario.dominio",
														new String[] { campo.getIdComponente(),
																literales.getLiteral("validador",
																		"tramitePasoRellenar.disenyoFormulario.pagina",
																		pIdioma) + " "
																		+ Integer.toString(paginaFormulario.getOrden()),
																formulario.getIdentificador() },
														pIdioma, false, "");
												listaErrores.add(error);
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
	}

	/**
	 * Checkea si un dominio es utilizado en el paso anexar..
	 *
	 * @param pTramiteVersion        tramiteversion
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 * @param pasoAnexar paso anexar
	 */
	private void checkDominioNoUtilizadoAnexar(String identificador, List<ErrorValidacion> listaErrores,
			final String pIdioma, final TramitePasoAnexar pasoAnexar) {

		if (UtilScripts.isDominioUtilizado(pasoAnexar.getScriptAnexosDinamicos(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoAnexar,
					"tramitePasoAnexar.listaDinamicaAnexos",
					new String[] { literales.getLiteral("validador", "tramitePasoAnexar", pIdioma) }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (pasoAnexar.getDocumentos() != null && !pasoAnexar.getDocumentos().isEmpty()) {
			for (final Documento documento : pasoAnexar.getDocumentos()) {

				if (UtilScripts.isDominioUtilizado(documento.getScriptObligatoriedad(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoAnexar,
							"tramitePasoAnexar.scriptObligatoriedad", new String[] { documento.getIdentificador() },
							pIdioma, false, "");
					listaErrores.add(error);
				}

				if (UtilScripts.isDominioUtilizado(documento.getScriptFirmarDigitalmente(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoAnexar,
							"tramitePasoAnexar.scriptFirmarDigitalmente", new String[] { documento.getIdentificador() },
							pIdioma, false, "");
					listaErrores.add(error);
				}

				if (UtilScripts.isDominioUtilizado(documento.getScriptValidacion(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoAnexar,
							"tramitePasoAnexar.scripValidacion", new String[] { documento.getIdentificador() }, pIdioma,
							false, "");
					listaErrores.add(error);
				}
			}
		}

	}

	/**
	 * Checkea si un dominio es utilizado en el paso tasa
	 *
	 * @param listaErrores
	 * @param pIdioma
	 * @param pasoTasa
	 */
	private void checkDominioNoUtilizadoTasa(String identificador, List<ErrorValidacion> listaErrores,
			final String pIdioma, final TramitePasoTasa pasoTasa) {

		if (UtilScripts.isDominioUtilizado(pasoTasa.getScriptNavegacion(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoTasa,
					"tramitePasoTasa.scripValidacion", new String[] { pasoTasa.getIdPasoTramitacion() }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (UtilScripts.isDominioUtilizado(pasoTasa.getScriptVariables(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoTasa,
					"tramitePasoTasa.scriptVariables", new String[] { pasoTasa.getIdPasoTramitacion() }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (pasoTasa.getTasas() != null) {
			for (final Tasa tasa : pasoTasa.getTasas()) {

				if (UtilScripts.isDominioUtilizado(tasa.getScriptObligatoriedad(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoTasa,
							"tramitePasoTasa.scriptObligatoriedad", new String[] { tasa.getIdentificador() }, pIdioma,
							false, "");
					listaErrores.add(error);
				}

				if (UtilScripts.isDominioUtilizado(tasa.getScriptPago(), identificador)) {
					final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoTasa,
							"tramitePasoTasa.scriptPago", new String[] { tasa.getIdentificador() }, pIdioma, false, "");
					listaErrores.add(error);
				}
			}
		}

	}

	private void checkDominioNoUtilizadoDebeSaber(String identificador, List<ErrorValidacion> listaErrores,
			final String pIdioma, final TramitePasoDebeSaber pasoDS) {

		if (UtilScripts.isDominioUtilizado(pasoDS.getScriptNavegacion(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoDS,
					"tramitePasoDebeSaber.scriptInstrucciones", new String[] { pasoDS.getIdPasoTramitacion() }, pIdioma,
					false, "");
			listaErrores.add(error);
		}

	}

	/**
	 * Checkea si un dominio es utilizado en el paso registrar.
	 *
	 * @param identificador
	 * @param listaErrores
	 * @param pIdioma
	 * @param pasoRegistrar
	 */
	private void checkDominioNoUtilizadoRegistrar(String identificador, List<ErrorValidacion> listaErrores,
			final String pIdioma, final TramitePasoRegistrar pasoRegistrar) {

		if (UtilScripts.isDominioUtilizado(pasoRegistrar.getScriptAlFinalizar(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoRegistrar,
					"tramitePasoRegistrar.scriptAlFinalizar",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (UtilScripts.isDominioUtilizado(pasoRegistrar.getScriptDestinoRegistro(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoRegistrar,
					"tramitePasoRegistrar.scriptDestinoRegistro",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (UtilScripts.isDominioUtilizado(pasoRegistrar.getScriptNavegacion(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoRegistrar,
					"tramitePasoRegistrar.scriptNavegacion",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (UtilScripts.isDominioUtilizado(pasoRegistrar.getScriptPresentador(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoRegistrar,
					"tramitePasoRegistrar.scriptPresentador",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (UtilScripts.isDominioUtilizado(pasoRegistrar.getScriptRepresentante(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoRegistrar,
					"tramitePasoRegistrar.scriptRepresentante",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (UtilScripts.isDominioUtilizado(pasoRegistrar.getScriptValidarRegistrar(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoRegistrar,
					"tramitePasoRegistrar.scriptValidarRegistrar",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

		if (UtilScripts.isDominioUtilizado(pasoRegistrar.getScriptVariables(), identificador)) {
			final ErrorValidacion error = getInstanceErrorValidacion((ModelApi) pasoRegistrar,
					"tramitePasoRegistrar.scriptVariables",
					new String[] { literales.getLiteral("validador", "tramitePasoRegistrar", pIdioma) }, pIdioma, false,
					"");
			listaErrores.add(error);
		}

	}

	/**
	 * Devuelve el error validacion con lo básico.
	 *
	 * @param item
	 * @param pLiteral
	 * @param pOpciones
	 * @param pIdioma
	 * @param pMostrarElemento
	 * @param pElemento
	 * @return
	 */
	public ErrorValidacion getInstanceErrorValidacion(ModelApi item, final String pLiteral, final String[] pOpciones,
			final String pIdioma, final boolean pMostrarElemento, final String pElemento) {
		ErrorValidacion error = new ErrorValidacion();
		final String literal = literales.getLiteral("validador", pLiteral, pIdioma);
		final StringBuilder elemento = new StringBuilder();

		if (pOpciones != null) {
			elemento.append(pOpciones[pOpciones.length - 1] + " > ");

			for (int i = pOpciones.length - 2; i >= 0; i--) {
				elemento.append(pOpciones[i] + " > ");
			}
		}

		if (pMostrarElemento) {
			elemento.append(literal + " > " + pElemento);
		} else {
			elemento.append(literal);
		}
		error.setItem(item);
		error.setElemento(elemento.toString());
		error.setTipo(TypeErrorValidacion.DOMINIOS_ELIMINAR);
		return error;
	}

	/**************************************/

}
