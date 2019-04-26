package es.caib.sistramit.core.service.component.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccesoNoPermitidoException;
import es.caib.sistramit.core.api.exception.EngineScriptException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.RegistroNoPermitidoException;
import es.caib.sistramit.core.api.exception.ValidacionAnexoException;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.formulario.MensajeValidacion;
import es.caib.sistramit.core.service.component.integracion.DominiosComponent;
import es.caib.sistramit.core.service.component.integracion.PagoComponent;
import es.caib.sistramit.core.service.component.script.plugins.PlgDominios;
import es.caib.sistramit.core.service.component.script.plugins.PlgError;
import es.caib.sistramit.core.service.component.script.plugins.PlgLog;
import es.caib.sistramit.core.service.component.script.plugins.PlgMensajes;
import es.caib.sistramit.core.service.component.script.plugins.PlgUtils;
import es.caib.sistramit.core.service.component.script.plugins.PlgValidacion;
import es.caib.sistramit.core.service.component.script.plugins.flujo.PlgFormularios;
import es.caib.sistramit.core.service.component.script.plugins.flujo.PlgPago;
import es.caib.sistramit.core.service.component.script.plugins.flujo.PlgSesionTramitacion;
import es.caib.sistramit.core.service.component.script.plugins.flujo.PlgValidacionAnexo;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResAnexosDinamicos;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResDatosInicialesFormulario;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResFirmantes;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResModificacionFormularios;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPago;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResParametrosFormulario;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResParametrosIniciales;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPersona;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPersonalizacionTramite;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPlantillaInfo;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResRegistro;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResVariableFlujo;
import es.caib.sistramit.core.service.component.script.plugins.formulario.PlgDatosFormulario;
import es.caib.sistramit.core.service.component.script.plugins.formulario.PlgSesionFormulario;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResEstadoCampo;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResValorCampo;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResValoresPosibles;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.PlgErrorInt;
import es.caib.sistramit.core.service.model.script.PlgValidacionInt;
import es.caib.sistramit.core.service.model.script.PluginScript;
import es.caib.sistramit.core.service.model.script.flujo.ResAnexosDinamicosInt;
import es.caib.sistramit.core.service.model.script.flujo.ResDatosInicialesFormularioInt;
import es.caib.sistramit.core.service.model.script.flujo.ResFirmantesInt;
import es.caib.sistramit.core.service.model.script.flujo.ResModificacionFormulariosInt;
import es.caib.sistramit.core.service.model.script.flujo.ResPagoInt;
import es.caib.sistramit.core.service.model.script.flujo.ResParametrosFormularioInt;
import es.caib.sistramit.core.service.model.script.flujo.ResParametrosInicialesInt;
import es.caib.sistramit.core.service.model.script.flujo.ResPersonaInt;
import es.caib.sistramit.core.service.model.script.flujo.ResPersonalizacionTramiteInt;
import es.caib.sistramit.core.service.model.script.flujo.ResPlantillaInfoInt;
import es.caib.sistramit.core.service.model.script.flujo.ResRegistroInt;
import es.caib.sistramit.core.service.model.script.flujo.ResVariableFlujoInt;
import es.caib.sistramit.core.service.model.script.formulario.ResEstadoCampoInt;
import es.caib.sistramit.core.service.model.script.formulario.ResValorCampoInt;
import es.caib.sistramit.core.service.model.script.types.TypeScript;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFormulario;

/**
 * Implementación del componente de ejecución de scripts.
 *
 * @author Indra
 *
 */
@Component("scriptExec")
public final class ScriptExecImpl implements ScriptExec {

	/**
	 * Script Engine manager.
	 */
	private final ScriptEngineManager engineManager;

	/** Pago component para plugin pago (calculo tasa). */
	@Autowired
	private PagoComponent pagoComponent;

	/** Dominios component. */
	@Autowired
	private DominiosComponent dominiosComponent;

	/**
	 * Constante para indicar que no devuelve nada el script. Si se establece este
	 * valor en getPluginResultadoFlujo se devolverá null como resultado del script.
	 */
	private final static String SCRIPT_RETURN_NONE = "SCRIPT_RETURN_NONE";

	/**
	 * Constructor.
	 */
	public ScriptExecImpl() {
		super();

		// Instanciamos scrip engine manager
		engineManager = new ScriptEngineManager();

	}

	@Override
	public RespuestaScript executeScriptFlujo(final TypeScriptFlujo pTipoScript, final String pIdElemento,
			final String pScript, final VariablesFlujo pVariablesFlujo, final Map<String, Object> pVariablesScript,
			final List<DatosDocumento> pDocumentosPaso, final Map<String, String> codigosError,
			final DefinicionTramiteSTG pDefinicionTramite) {

		// Generamos plugins
		final List<PluginScript> plugins = generarPluginsFlujo(pTipoScript, pIdElemento, pVariablesFlujo,
				pVariablesScript, pDocumentosPaso, pDefinicionTramite, codigosError);

		// Ejecutamos script
		final String idSesion = pVariablesFlujo.getIdSesionTramitacion();
		final Object result = ejecutarScript(pTipoScript, idSesion, pIdElemento, pScript, plugins);

		// Retornamos resultado
		return generarRespuestaScriptFlujo(pTipoScript, codigosError, plugins, result, pIdElemento);
	}

	@Override
	public RespuestaScript executeScriptFormulario(final TypeScriptFormulario pTipoScript, final String pIdElemento,
			final String pScript, final VariablesFormulario pVariablesFormulario,
			final Map<String, String> pCodigosError, final DefinicionTramiteSTG pDefinicionTramite) {

		// Generamos plugins
		final List<PluginScript> plugins = generarPluginsFormulario(pTipoScript, pIdElemento, pVariablesFormulario,
				pDefinicionTramite, pCodigosError);

		// Ejecutamos script
		final String idSesion = pVariablesFormulario.getIdSesionTramitacion();
		final Object result = ejecutarScript(pTipoScript, idSesion, pIdElemento, pScript, plugins);

		// Retornamos resultado
		return generarRespuestaScriptFormulario(pTipoScript, pCodigosError, plugins, result);
	}

	// -------------------------------------------------------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -------------------------------------------------------------------------------------------------------------------
	/**
	 * Ejecutar script.
	 *
	 * @param pTipoScript
	 *            Tipo script
	 * @param idSesion
	 *            Id sesion
	 * @param pIdElemento
	 *            Id elemento
	 * @param pScript
	 *            Script
	 * @param plugins
	 *            Plugins
	 * @return Resultado script
	 */
	private Object ejecutarScript(final TypeScript pTipoScript, final String idSesion, final String pIdElemento,
			final String pScript, final List<PluginScript> plugins) {
		Object result = null;
		try {
			// Preparamos scripts para que valgan los returns
			final StringBuilder sb = new StringBuilder(pScript.length() + ConstantesNumero.N60);
			sb.append("function ejecutarScript() { ").append(pScript).append("\n}; ejecutarScript();");
			final ScriptEngine jsEngine = engineManager.getEngineByName("JavaScript");
			for (final PluginScript plg : plugins) {
				jsEngine.put(plg.getPluginId(), plg);
			}
			result = jsEngine.eval(sb.toString());
		} catch (final ScriptException ex) {
			throw new EngineScriptException(pTipoScript.name(), idSesion, pIdElemento, ex.getLineNumber(),
					"Error ejecutando script: " + ex.getMessage(), ex);
		}
		return result;
	}

	/**
	 * Genera respuesta a partir del resultado del script.
	 *
	 * @param pTipoScript
	 *            Tipo de script
	 * @param codigosError
	 *            Códigos de error
	 * @param plugins
	 *            Plugins
	 * @param result
	 *            Resultado script
	 * @param pIdElemento
	 *            Id elemento
	 * @return Respuesta del script
	 */
	private RespuestaScript generarRespuestaScriptFlujo(final TypeScriptFlujo pTipoScript,
			final Map<String, String> codigosError, final List<PluginScript> plugins, final Object result,
			final String pIdElemento) {
		final RespuestaScript respuestaScript = new RespuestaScript();
		// - Evaluamos si se ha marcado con error el script
		final PlgError plgError = (PlgError) getPlugin(plugins, PlgErrorInt.ID);
		if (plgError != null && plgError.isExisteError()) {
			String msgError = "Script marcado con error (no se ha establecido mensaje error)";
			if (StringUtils.isNotBlank(plgError.getCodigoMensajeError())) {
				msgError = ScriptUtils.calculaMensajeError(codigosError, plgError.getCodigoMensajeError(),
						plgError.getParametrosMensajeError());
			} else if (StringUtils.isNotBlank(plgError.getTextoMensajeError())) {
				msgError = plgError.getTextoMensajeError();
			}

			// Verificamos si generamos excepcion específica
			switch (pTipoScript) {
			case SCRIPT_PERSONALIZACION_TRAMITE:
				throw new AccesoNoPermitidoException(msgError);
			case SCRIPT_VALIDAR_ANEXO:
				throw new ValidacionAnexoException(msgError, pIdElemento);
			case SCRIPT_PERMITIR_REGISTRO:
				throw new RegistroNoPermitidoException(msgError);
			default:
				// TODO Generar excepcion con los detalles (script, idsesion,...)
				throw new ErrorScriptException(msgError);
			}

		}
		// - En funcion del plugin vemos si debemos retornar el objeto o bien un
		// plugin de tipo resultado
		final Object resScr = obtenerResultadoScript(pTipoScript, plugins, result);
		respuestaScript.setResultado(resScr);

		// - Si el script es de validacion actualizamos info de aviso
		respuestaScript.setMensajeValidacion(generarMensajeAviso(plugins, codigosError));

		return respuestaScript;
	}

	/**
	 * Genera mensaje aviso a partir del plugin de aviso.
	 *
	 * @param plugins
	 *            Plugins
	 * @param codigosError
	 *            Codigos de error
	 * @return mensaje
	 */
	private MensajeValidacion generarMensajeAviso(final List<PluginScript> plugins,
			final Map<String, String> codigosError) {
		MensajeValidacion mensaje = null;
		final PlgValidacion plgAviso = (PlgValidacion) getPlugin(plugins, PlgValidacionInt.ID);
		if (plgAviso != null && plgAviso.isExisteAviso()) {
			final String textoMensajeAviso = calcularMensaje(plgAviso.getCodigoMensajeAviso(),
					plgAviso.getParametrosMensajeAviso(), plgAviso.getTextoMensajeAviso(), codigosError);
			mensaje = new MensajeValidacion(plgAviso.getCampo(), plgAviso.getTipoAviso(), textoMensajeAviso);
		}
		return mensaje;
	}

	/**
	 * Obtiene resultado segun el tipo de script.
	 *
	 * @param pTipoScript
	 *            Tipo script
	 * @param plugins
	 *            plugins
	 * @param result
	 *            respuesta script
	 * @return resultado script
	 */
	private Object obtenerResultadoScript(final TypeScriptFlujo pTipoScript, final List<PluginScript> plugins,
			final Object result) {
		Object res = null;
		// Obtenemos si el script devuelve resultado a traves de un plugin
		// resultado
		final String idPlgRes = getPluginResultadoFlujo(pTipoScript, result);
		// Si es asi obtenemos plugin resultado
		if (idPlgRes != null) {
			res = getPlugin(plugins, idPlgRes);
		} else {
			// Si no, devolvemos como cadena en caso de que exista valor
			// retornos
			if (result != null) {
				res = result.toString();
			}
		}
		return res;
	}

	/**
	 * Obtiene si el script devuelve el resultado a traves de un plugin de tipo
	 * resultado.
	 *
	 * @param pTipoScript
	 *            Tipo scripto flujo
	 * @param result
	 * @return Id plugin resultado. Nulo si no devuelve plugin tipo resultado.
	 */
	private String getPluginResultadoFlujo(final TypeScriptFlujo pTipoScript, final Object result) {
		String res = null;
		switch (pTipoScript) {
		case SCRIPT_PARAMETROS_INICIALES:
			res = ResParametrosInicialesInt.ID;
			break;
		case SCRIPT_PERSONALIZACION_TRAMITE:
			res = ResPersonalizacionTramiteInt.ID;
			break;
		case SCRIPT_DATOS_PAGO:
			res = ResPagoInt.ID;
			break;
		case SCRIPT_DATOS_INICIALES_FORMULARIO:
			res = ResDatosInicialesFormularioInt.ID;
			break;
		case SCRIPT_POSTGUARDAR_FORMULARIO:
			res = ResModificacionFormulariosInt.ID;
			break;
		case SCRIPT_FIRMANTES:
			res = ResFirmantesInt.ID;
			break;
		case SCRIPT_PARAMETROS_REGISTRO:
			res = ResRegistroInt.ID;
			break;
		case SCRIPT_PRESENTADOR_REGISTRO:
			res = ResPersonaInt.ID;
			break;
		case SCRIPT_REPRESENTADO_REGISTRO:
			res = ResPersonaInt.ID;
			break;
		case SCRIPT_LISTA_DINAMICA_ANEXOS:
			res = ResAnexosDinamicosInt.ID;
			break;
		case SCRIPT_PLANTILLA_INFO:
			res = ResPlantillaInfoInt.ID;
			break;
		case SCRIPT_PARAMETROS_FORMULARIO:
			res = ResParametrosFormularioInt.ID;
			break;
		case SCRIPT_VARIABLE_FLUJO:
			res = ResVariableFlujoInt.ID;
			break;
		default:
			res = null;
		}
		return res;
	}

	/**
	 * Genera los plugins accesibles desde el script.
	 *
	 * @param pTipoScript
	 *            Tipo de script
	 * @param pIdElemento
	 *            Id elemento
	 * @param pVariablesFlujo
	 *            Variables de flujo
	 * @param pVariablesScript
	 *            Variables script
	 * @param pDocumentosPaso
	 *            Documentos completados del paso actual
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pCodigosError
	 *            Mensajes de error
	 * @return Lista de plugins
	 */
	private List<PluginScript> generarPluginsFlujo(final TypeScriptFlujo pTipoScript, final String pIdElemento,
			final VariablesFlujo pVariablesFlujo, final Map<String, Object> pVariablesScript,
			final List<DatosDocumento> pDocumentosPaso, final DefinicionTramiteSTG pDefinicionTramite,
			final Map<String, String> pCodigosError) {

		final List<PluginScript> plugins = new ArrayList<>();

		// Añadimos plugins generales
		// - Plugin validaciones
		plugins.add(new PlgUtils(pVariablesFlujo.isDebugEnabled()));
		// - Plugin error
		plugins.add(new PlgError());
		// - Plugin mensajes validacion
		plugins.add(new PlgMensajes(pCodigosError));
		// - Plugin log
		plugins.add(new PlgLog(pVariablesFlujo.getIdSesionTramitacion(), pTipoScript, pIdElemento,
				pVariablesFlujo.isDebugEnabled()));
		// - Plugin dominios
		plugins.add(new PlgDominios(pDefinicionTramite, dominiosComponent));

		// Añadimos plugins flujo
		// - Plugin sesion
		plugins.add(new PlgSesionTramitacion(pVariablesFlujo));
		// - Plugin formularios
		plugins.add(crearPluginFormularios(pVariablesFlujo, pDocumentosPaso));
		// - Plugin variables flujo (personalizado)
		// TODO Pendiente variables flujo (personalizado)

		// Creamos plugins especificos segun tipo script
		switch (pTipoScript) {
		case SCRIPT_PARAMETROS_INICIALES:
			plugins.add(new ResParametrosIniciales());
			break;
		case SCRIPT_PERSONALIZACION_TRAMITE:
			plugins.add(new ResPersonalizacionTramite());
			break;
		case SCRIPT_DATOS_INICIALES_FORMULARIO:
			plugins.add(new ResDatosInicialesFormulario());
			break;
		case SCRIPT_PARAMETROS_FORMULARIO:
			plugins.add(new ResParametrosFormulario());
			break;
		case SCRIPT_POSTGUARDAR_FORMULARIO:
			plugins.add(new ResModificacionFormularios());
			break;
		case SCRIPT_LISTA_DINAMICA_ANEXOS:
			plugins.add(new ResAnexosDinamicos());
			break;
		case SCRIPT_DATOS_PAGO:
			plugins.add(new PlgPago(pagoComponent, pDefinicionTramite.getDefinicionVersion().getIdEntidad()));
			plugins.add(new ResPago());
			break;
		case SCRIPT_FIRMANTES:
			plugins.add(new ResFirmantes());
			break;
		case SCRIPT_PARAMETROS_REGISTRO:
			plugins.add(new ResRegistro());
			break;
		case SCRIPT_PRESENTADOR_REGISTRO:
			plugins.add(new ResPersona());
			break;
		case SCRIPT_REPRESENTADO_REGISTRO:
			plugins.add(new ResPersona());
			break;
		case SCRIPT_PLANTILLA_INFO:
			plugins.add(new ResPlantillaInfo());
			break;
		case SCRIPT_VARIABLE_FLUJO:
			plugins.add(new ResVariableFlujo());
			break;
		case SCRIPT_VALIDAR_ANEXO:
			final String nomFichero = (String) pVariablesScript.get("nombreFichero");
			final byte[] datosFichero = (byte[]) pVariablesScript.get("datosFichero");
			plugins.add(new PlgValidacionAnexo(nomFichero, datosFichero));
			break;
		default:
			break;
		}

		// Devolvemos lista de plugins accesibles desde el script
		return plugins;
	}

	/**
	 * Prepara plugins disponibles para el script de formulario.
	 *
	 * @param pTipoScript
	 *            Tipo script
	 * @param pIdElemento
	 *            Id elemento
	 * @param pVariablesFormulario
	 *            Variables formulario
	 * @param pDefinicionTramite
	 *            Definicion tramite
	 * @param pCodigosError
	 *            Mensajes error
	 * @return Lista de plugins
	 */
	private List<PluginScript> generarPluginsFormulario(final TypeScriptFormulario pTipoScript,
			final String pIdElemento, final VariablesFormulario pVariablesFormulario,
			final DefinicionTramiteSTG pDefinicionTramite, final Map<String, String> pCodigosError) {

		final List<PluginScript> plugins = new ArrayList<>();

		// Añadimos plugins generales
		// - Plugin validaciones
		plugins.add(new PlgUtils(pVariablesFormulario.isDebugEnabled()));
		// - Plugin error
		plugins.add(new PlgError());
		// - Plugin mensajes validacion
		plugins.add(new PlgMensajes(pCodigosError));
		// - Plugin log
		plugins.add(new PlgLog(pVariablesFormulario.getIdSesionTramitacion(), pTipoScript, pIdElemento,
				pVariablesFormulario.isDebugEnabled()));
		// - Plugin dominios
		plugins.add(new PlgDominios(pDefinicionTramite, dominiosComponent));

		// Añadimos plugins formulario

		// Generamos plugins resultantes de las vbles de flujo
		// - Plugin sesion
		plugins.add(new PlgSesionFormulario(pVariablesFormulario));
		// - Parametros formulario
		plugins.add(new PlgDatosFormulario(pVariablesFormulario.getValoresCampo()));

		// Creamos plugins especificos segun tipo script
		switch (pTipoScript) {
		case SCRIPT_AUTORELLENABLE:
			plugins.add(new ResValorCampo());
			break;
		case SCRIPT_ESTADO:
			plugins.add(new ResEstadoCampo());
			break;
		case SCRIPT_VALORES_POSIBLES:
			plugins.add(new ResValoresPosibles());
			break;
		case SCRIPT_VALIDACION_CAMPO:
			plugins.add(new PlgValidacion(pIdElemento));
			break;
		case SCRIPT_VALIDACION_PAGINA:
			plugins.add(new PlgValidacion());
			break;
		default:
			break;
		}

		// Devolvemos lista de plugins accesibles desde el script
		return plugins;
	}

	/**
	 * Obtiene plugin de la lista.
	 *
	 * @param plugins
	 *            Lista plugins
	 * @param name
	 *            Nombre plugin
	 * @return Plugin
	 */
	private PluginScript getPlugin(final List<PluginScript> plugins, final String name) {
		PluginScript res = null;
		if (!name.equals(SCRIPT_RETURN_NONE)) {
			for (final PluginScript p : plugins) {
				if (p.getPluginId().equals(name)) {
					res = p;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Calucula mensaje a mostrar.
	 *
	 * @param codigoMensajeError
	 *            Codigo mensaje
	 * @param parametrosMensajeError
	 *            Parametros mensaje
	 * @param textoMensajeErrorParticularizado
	 *            Texto particularizado
	 * @param pCodigosError
	 *            Codigos de error
	 * @return mensaje
	 */
	private String calcularMensaje(final String codigoMensajeError, final List<String> parametrosMensajeError,
			final String textoMensajeErrorParticularizado, final Map<String, String> pCodigosError) {
		String textoMensajeError;
		if (StringUtils.isNotBlank(codigoMensajeError)) {
			textoMensajeError = ScriptUtils.calculaMensajeError(pCodigosError, codigoMensajeError,
					parametrosMensajeError);
		} else if (StringUtils.isNotBlank(textoMensajeErrorParticularizado)) {
			textoMensajeError = textoMensajeErrorParticularizado;
		} else {
			textoMensajeError = "";
		}
		return textoMensajeError;
	}

	/**
	 * Crea plugin de acceso a formularios.
	 *
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param pDocumentosPaso
	 *            Documentos rellenados en paso actual
	 * @return Plugin de formularios
	 */
	private PlgFormularios crearPluginFormularios(final VariablesFlujo pVariablesFlujo,
			final List<DatosDocumento> pDocumentosPaso) {
		final List<DatosDocumentoFormulario> datosForms = new ArrayList<DatosDocumentoFormulario>();
		// - Añadimos datos formularios rellenados en pasos anteriores
		for (final DatosDocumento ddp : pVariablesFlujo.getDocumentos()) {
			if (ddp.getTipo() == TypeDocumento.FORMULARIO) {
				datosForms.add((DatosDocumentoFormulario) ddp);
			}
		}
		// - Añadimos datos formularios rellenados en paso actual
		if (pDocumentosPaso != null) {
			for (final DatosDocumento ddp : pDocumentosPaso) {
				if (ddp.getTipo() == TypeDocumento.FORMULARIO) {
					datosForms.add((DatosDocumentoFormulario) ddp);
				}
			}
		}
		final PlgFormularios plgFormularios = new PlgFormularios(datosForms);
		return plgFormularios;
	}

	/**
	 * Método para Generar respuesta script formulario de la clase ScriptExecImpl.
	 *
	 * @param pTipoScript
	 *            Tipo de script
	 * @param pCodigosError
	 *            Códigos de error
	 * @param pPlugins
	 *            Plugins
	 * @param pResult
	 *            Resultado script
	 * @return Respuesta del script
	 */
	private RespuestaScript generarRespuestaScriptFormulario(final TypeScriptFormulario pTipoScript,
			final Map<String, String> pCodigosError, final List<PluginScript> pPlugins, final Object pResult) {

		final RespuestaScript respuestaScript = new RespuestaScript();
		// - Evaluamos si se ha marcado con error el script
		final PlgError plgError = (PlgError) getPlugin(pPlugins, PlgErrorInt.ID);
		if (plgError != null && plgError.isExisteError()) {
			final String textoMensajeError = calcularMensaje(plgError.getCodigoMensajeError(),
					plgError.getParametrosMensajeError(), plgError.getTextoMensajeError(), pCodigosError);
			// TODO Generar excepcion con los detalles (script, idsesion,...)
			throw new ErrorScriptException(textoMensajeError);
		}

		// - En funcion del plugin vemos si debemos retornar el objeto o bien un
		// plugin de tipo resultado
		switch (pTipoScript) {
		case SCRIPT_AUTORELLENABLE:
			respuestaScript.setResultado(getPlugin(pPlugins, ResValorCampoInt.ID));
			break;
		case SCRIPT_ESTADO:
			respuestaScript.setResultado(getPlugin(pPlugins, ResEstadoCampoInt.ID));
			break;
		case SCRIPT_VALORES_POSIBLES:
			respuestaScript.setResultado(getPlugin(pPlugins, ResValoresPosibles.ID));
			break;
		default:
			if (pResult != null) {
				respuestaScript.setResultado(pResult.toString());
			}
			break;
		}

		// - Si el script es de validacion actualizamos info de aviso
		respuestaScript.setMensajeValidacion(generarMensajeAviso(pPlugins, pCodigosError));

		return respuestaScript;
	}

}
