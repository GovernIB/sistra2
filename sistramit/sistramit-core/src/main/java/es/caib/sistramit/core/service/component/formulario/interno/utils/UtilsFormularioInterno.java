package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteCheckbox;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RLineaComponentes;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RParametroDominio;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.ValorCampoFormularioNoValidoException;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTexto;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeListaValores;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeParametroDominio;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
import es.caib.sistramit.core.service.model.script.formulario.PlgDatosFormularioInt;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

// TODO Ver si separamos UtilsFormulario entre generales e interno

/**
 * Clase de utilidades para formularios.
 *
 * @author Indra
 *
 */
public class UtilsFormularioInterno {

	/** Pattern que identifica els noms de variables. */
	private static final Pattern PLUGINFORM_PATTERN = Pattern
			.compile(PlgDatosFormularioInt.ID + "\\.get\\w*\\(['|\"](\\w*-?\\w*)*");

	/**
	 * Comprueba si es un campo oculto (tipo texto y subtipo oculto).
	 *
	 * @param configuracionCampo
	 *            Configuracion campo
	 * @return Indica si es campo oculto
	 */
	public static boolean esCampoOculto(final ConfiguracionCampo configuracionCampo) {
		return configuracionCampo.getTipo() == TypeCampo.TEXTO
				&& ((ConfiguracionCampoTexto) configuracionCampo).getContenido() == TypeTexto.OCULTO;
	}

	/**
	 * Devuelve lista de campos formulario.
	 *
	 * @param defPagina
	 *            Definición página
	 * @return Lista campos
	 */
	public static List<RComponente> devuelveListaCampos(RFormularioInterno defFormulario) {
		final List<RComponente> campos = new ArrayList<>();
		for (final RPaginaFormulario defPagina : defFormulario.getPaginas()) {
			campos.addAll(devuelveListaCampos(defPagina));
		}
		return campos;
	}

	/**
	 * Devuelve lista de campos de la página.
	 *
	 * @param defPagina
	 *            Definición página
	 * @return Lista campos
	 */
	public static List<RComponente> devuelveListaCampos(RPaginaFormulario defPagina) {
		final List<RComponente> campos = new ArrayList<>();
		for (final RLineaComponentes linea : defPagina.getLineas()) {
			for (final RComponente c : linea.getComponentes()) {
				if (esCampo(c)) {
					campos.add(c);
				}
			}
		}
		return campos;
	}

	/**
	 * Devuelve componente la página.
	 *
	 * @param idComponente
	 *            idComponente
	 * @return Campo
	 */
	public static RComponente devuelveComponentePagina(RPaginaFormulario defPagina, String idComponente) {
		RComponente res = null;
		for (final RLineaComponentes linea : defPagina.getLineas()) {
			for (final RComponente c : linea.getComponentes()) {
				if (c.getIdentificador().equals(idComponente)) {
					res = c;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Verifica si el componente es un campo de datos.
	 *
	 * @param componente
	 * @return boolean
	 */
	public static boolean esCampo(final RComponente pCampoDef) {
		final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(pCampoDef.getTipo());
		return (tipoCampo != null);
	}

	/**
	 * Crea valor vacío según tipo de campo.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return Valor campo vacio
	 */
	public static ValorCampo crearValorVacio(final RComponente pCampoDef) {
		final TypeValor tipoValor = obtenerTipoValorCampo(pCampoDef);
		final ValorCampo res = UtilsFormulario.crearValorVacio(pCampoDef.getIdentificador(), tipoValor);
		return res;
	}

	/**
	 * Busca dependencias con otros campos.
	 *
	 * @param pCampoDef
	 *            Definición campo
	 * @return Lista de dependencias con otros campos
	 */
	public static DependenciaCampo buscarDependenciasCampo(final RComponente pCampoDef) {

		final DependenciaCampo res = new DependenciaCampo(pCampoDef.getIdentificador());

		final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(pCampoDef.getTipo());
		final RPropiedadesCampo propsCampo = obtenerPropiedadesCampo(pCampoDef);

		// Si es un selector, buscamos si referencia algun campo
		if (tipoCampo == TypeCampo.SELECTOR) {
			res.addDependenciasSelector(buscarDependenciasSelector((RComponenteSelector) pCampoDef));
		}
		// Script autorrellenable
		if (UtilsSTG.existeScript(propsCampo.getScriptAutorrellenable())) {
			res.addDependenciasAutorrellenable(buscarDependenciasScript(propsCampo.getScriptAutorrellenable()));
		}
		// Script obligatoriedad
		if (UtilsSTG.existeScript(propsCampo.getScriptEstado())) {
			res.addDependenciasEstado(buscarDependenciasScript(propsCampo.getScriptEstado()));
		}

		// Devolvemos dependencias
		return res;
	}

	/**
	 * Obtiene propiedades generales campo datos.
	 *
	 * @param pCampoDef
	 *            Campo
	 * @return
	 */
	public static RPropiedadesCampo obtenerPropiedadesCampo(RComponente pCampoDef) {
		final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(pCampoDef.getTipo());
		RPropiedadesCampo propsCampo = null;
		switch (tipoCampo) {
		case TEXTO:
			propsCampo = ((RComponenteTextbox) pCampoDef).getPropiedadesCampo();
			break;
		case SELECTOR:
			propsCampo = ((RComponenteSelector) pCampoDef).getPropiedadesCampo();
			break;
		case VERIFICACION:
			propsCampo = ((RComponenteCheckbox) pCampoDef).getPropiedadesCampo();
			break;
		default:
			throw new TipoNoControladoException("Tipo de campo " + tipoCampo + " no controlado");
		}
		return propsCampo;
	}

	/**
	 * Obtiene definición página actual.
	 *
	 * @param pDatosSesion
	 *            Datos sesión
	 * @return definición página
	 */
	public static RPaginaFormulario obtenerDefinicionPaginaActual(DatosSesionFormularioInterno pDatosSesion) {
		final RFormularioTramite defFormulario = UtilsSTG.devuelveDefinicionFormulario(
				pDatosSesion.getDatosInicioSesion().getIdPaso(), pDatosSesion.getDatosInicioSesion().getIdFormulario(),
				pDatosSesion.getDefinicionTramite());
		final RPaginaFormulario paginaDef = defFormulario.getFormularioInterno().getPaginas()
				.get(pDatosSesion.getDatosFormulario().getPaginaActualFormulario().getIndiceDef());
		return paginaDef;
	}

	/**
	 * Genera variables accesibles desde el script.
	 *
	 * @param pDatosSesion
	 *            Datos sesion formulario
	 * @param pIdCampo
	 *            Si el script es de un campo se indicara el id campo. Si es de la
	 *            pagina sera null.
	 * @return Variables accesibles desde el script.
	 */
	public static VariablesFormulario generarVariablesFormulario(final DatosSesionFormularioInterno pDatosSesion,
			final String pIdCampo) {
		final VariablesFormulario res = new VariablesFormulario();
		res.setIdSesionTramitacion(pDatosSesion.getDatosInicioSesion().getIdSesionTramitacion());
		res.setIdioma(pDatosSesion.getDatosInicioSesion().getIdioma());
		res.setParametrosApertura(pDatosSesion.getDatosInicioSesion().getParametros());
		res.setNivelAutenticacion(pDatosSesion.getDatosInicioSesion().getInfoAutenticacion().getAutenticacion());
		res.setMetodoAutenticacion(pDatosSesion.getDatosInicioSesion().getInfoAutenticacion().getMetodoAutenticacion());
		res.setUsuario(new DatosUsuario(pDatosSesion.getDatosInicioSesion().getInfoAutenticacion().getNif(),
				pDatosSesion.getDatosInicioSesion().getInfoAutenticacion().getNombre(),
				pDatosSesion.getDatosInicioSesion().getInfoAutenticacion().getApellido1(),
				pDatosSesion.getDatosInicioSesion().getInfoAutenticacion().getApellido2()));
		res.setDebugEnabled(pDatosSesion.isDebugEnabled());
		if (pIdCampo != null) {
			res.setValoresCampo(pDatosSesion.getDatosFormulario().getValoresAccesiblesCampo(pIdCampo));
		} else {
			res.setValoresCampo(pDatosSesion.getDatosFormulario().getValoresAccesiblesPaginaActual());
		}
		return res;
	}

	/**
	 * Obtiene parametros dominio.
	 *
	 * @param pDatosSesion
	 *            Datos sesion formulario
	 * @param pCampoDef
	 *            Definicion campo selector
	 * @return parametros
	 */
	public static ParametrosDominio obtenerParametrosDominio(DatosSesionFormularioInterno pDatosSesion,
			RComponenteSelector pCampoDef) {

		final ParametrosDominio parametros = new ParametrosDominio();

		final List<RParametroDominio> parametrosDef = pCampoDef.getListaDominio().getParametros();
		if (parametrosDef != null) {
			for (final RParametroDominio parDef : parametrosDef) {

				final TypeParametroDominio tipoParametro = UtilsSTG.traduceTipoParametroDominio(parDef.getTipo());
				if (tipoParametro == null) {
					throw new ErrorConfiguracionException("Tipo parámetro " + tipoParametro + " no soportado en campo "
							+ pCampoDef.getIdentificador());
				}

				switch (tipoParametro) {
				case CAMPO:
					final List<ValorCampo> camposAccesibles = pDatosSesion.getDatosFormulario()
							.getValoresAccesiblesCampo(pCampoDef.getIdentificador());
					final ValorCampo vc = UtilsFormularioInterno.buscarValorCampo(camposAccesibles, parDef.getValor());
					parametros.addParametro(parDef.getIdentificador(), valorCampoParametroDominio(vc));
					break;
				case CONSTANTE:
					parametros.addParametro(parDef.getIdentificador(), parDef.getValor());
					break;
				case PARAMETRO:
					final String paramForm = pDatosSesion.getDatosInicioSesion().getParametros()
							.getParametro(parDef.getValor());
					parametros.addParametro(parDef.getIdentificador(), paramForm);
					break;
				default:
					throw new TipoNoControladoException("Tipo de parametro dominio no controlado: " + parDef.getTipo());
				}
			}
		}
		return parametros;
	}

	/**
	 * Obtiene el valor campo para ser pasaso a un dominio. Los valores simples será
	 * el valor, para los indexados sera el código, y para la lista de indexados se
	 * pasará la lista de códigos separadas por coma. En caso de que el valor del
	 * campo sea nulo se devolverá la cadena vacía.
	 *
	 * @param valorCampo
	 *            Valor campo
	 *
	 * @return Valor campo para pasarlo como dominio.
	 */
	public static String valorCampoParametroDominio(final ValorCampo valorCampo) {
		String res = null;
		if (valorCampo != null) {
			switch (valorCampo.getTipo()) {
			case SIMPLE:
				final ValorCampoSimple vcs = (ValorCampoSimple) valorCampo;
				res = vcs.getValor();
				break;
			case INDEXADO:
				final ValorCampoIndexado vci = (ValorCampoIndexado) valorCampo;
				if (vci.getValor() != null) {
					res = vci.getValor().getValor();
				}
				break;
			case LISTA_INDEXADOS:
				final ValorCampoListaIndexados vcl = (ValorCampoListaIndexados) valorCampo;
				if (vcl.getValor() != null && !vcl.getValor().isEmpty()) {
					final StringBuffer sb = new StringBuffer(vcl.getValor().size() * 100);
					for (int i = 0; i < vcl.getValor().size(); i++) {
						if (i > 0) {
							sb.append(",");
						}
						sb.append(vcl.getValor().get(i).getValor());
					}
					res = sb.toString();
				}
				break;
			default:
				throw new TipoNoControladoException("Tipo de campo no controlado: " + valorCampo.getTipo());
			}
		}
		if (res == null) {
			res = "";
		}
		return res;
	}

	/**
	 * Extrae los valores posibles a partir de los valores del dominio.
	 *
	 * @param pCampoCodigo
	 *            Campo código
	 * @param pCampoDescripcion
	 *            Campo descripción
	 * @param pValoresDominio
	 *            Valores dominio
	 * @return el list
	 */
	public static List<ValorIndexado> extraerValoresPosibles(final String pCampoCodigo, final String pCampoDescripcion,
			final ValoresDominio pValoresDominio) {
		final List<ValorIndexado> res = new ArrayList<>();
		if (pValoresDominio != null) {
			for (int i = ConstantesNumero.N1; i <= pValoresDominio.getNumeroFilas(); i++) {
				final String codigo = pValoresDominio.getValor(i, pCampoCodigo);
				final String desc = pValoresDominio.getValor(i, pCampoDescripcion);
				res.add(ValorIndexado.createNewValorIndexado(codigo, desc));
			}
		}
		return res;
	}

	/**
	 * Deserializa valor de un campo.
	 *
	 * @param idCampo
	 *            Id campo
	 * @param valorSerializadoCampo
	 *            Valor serializado campo
	 * @return ValorCampo
	 */
	public static ValorCampo deserializarValorCampo(final String idCampo, final String valorCampo) {
		// Prefijo para serializar valores
		final String prefix = "#-@";

		// El valor serializado no puede estar vacio
		if (StringUtils.isBlank(valorCampo)) {
			throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
		}

		// Descomponemos en lista de strings
		final String[] values = valorCampo.split(prefix);
		if (values.length < ConstantesNumero.N1) {
			throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
		}

		// Deserializamos segun el tipo
		ValorCampo res = null;
		final TypeValor tipoValor = TypeValor.fromString(values[0]);
		if (tipoValor == null) {
			throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
		}
		switch (tipoValor) {
		case SIMPLE:
			res = deserializarValorCampoSimple(idCampo, valorCampo, values);
			break;
		case INDEXADO:
			res = deserializarValorCampoIndexado(idCampo, valorCampo, values);
			break;
		case LISTA_INDEXADOS:
			res = deserializarValorCampoListaIndexados(idCampo, valorCampo, values);
			break;
		default:
			throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
		}

		return res;
	}

	/**
	 * Comprueba si algún campo modificado se encuentra en la lista de campos
	 * dependientes.
	 *
	 * @param pModificados
	 *            Lista de campos modificados
	 * @param pDependenciasCampo
	 *            Lista de campos dependientes
	 * @return boolean
	 */
	public static boolean existeDependencia(final List<String> pModificados, final List<String> pDependenciasCampo) {
		boolean res = false;
		for (final String idCampoDep : pModificados) {
			if (pDependenciasCampo.contains(idCampoDep)) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**
	 * Busca valor campo.
	 *
	 * @param valoresCampo
	 *            Lista de valores
	 * @param idCampo
	 *            Id campo
	 * @return Valor campo (null si no lo encuentra)
	 */
	public static ValorCampo buscarValorCampo(final List<ValorCampo> valoresCampo, final String idCampo) {
		ValorCampo res = null;
		for (final ValorCampo vc : valoresCampo) {
			if (vc.getId().equals(idCampo)) {
				res = vc;
				break;
			}
		}
		return res;
	}

	/**
	 * Busca configuración campo.
	 *
	 * @param configuracionesCampo
	 *            Lista de valores
	 * @param idCampo
	 *            Id campo
	 * @return Valor campo (null si no lo encuentra)
	 */
	public static ConfiguracionCampo buscarConfiguracionCampo(final List<ConfiguracionCampo> configuracionesCampo,
			final String idCampo) {
		ConfiguracionCampo res = null;
		for (final ConfiguracionCampo cc : configuracionesCampo) {
			if (cc.getId().equals(idCampo)) {
				res = cc;
				break;
			}
		}
		return res;
	}

	/**
	 * Busca configuración campo.
	 *
	 * @param configuracionesCampo
	 *            Lista de valores
	 * @param idCampo
	 *            Id campo
	 * @return Valor campo (null si no lo encuentra)
	 */
	public static ConfiguracionModificadaCampo buscarConfiguracionModificadaCampo(
			final List<ConfiguracionModificadaCampo> configuracionesCampo, final String idCampo) {
		ConfiguracionModificadaCampo res = null;
		for (final ConfiguracionModificadaCampo cc : configuracionesCampo) {
			if (cc.getId().equals(idCampo)) {
				res = cc;
				break;
			}
		}
		return res;
	}

	/**
	 * Busca valores posibles asociados al campo.
	 *
	 * @param valoresPosibles
	 *            valores posibles
	 * @param idCampo
	 *            idcampo
	 * @return valores posibles campo
	 */
	public static ValoresPosiblesCampo buscarValoresPosibles(List<ValoresPosiblesCampo> valoresPosibles,
			String idCampo) {
		ValoresPosiblesCampo res = null;
		for (final ValoresPosiblesCampo cc : valoresPosibles) {
			if (cc.getId().equals(idCampo)) {
				res = cc;
				break;
			}
		}
		return res;
	}

	// ---------------------------------------------------------------------------------------------------
	// Funciones auxiliares
	// ---------------------------------------------------------------------------------------------------

	/**
	 * Busca dependencias con otros campos dentro de un script.
	 *
	 * @param script
	 *            Script SCript
	 * @return Lista de dependencias con otros campos
	 */
	private static List<String> buscarDependenciasScript(final RScript rScript) {
		final List<String> deps = new ArrayList<String>();
		final String script = rScript.getScript();
		// Quitamos todos los espacios en blanco y saltos de línea
		String scriptSearch = script.replaceAll("\\r*\\n", "");
		scriptSearch = scriptSearch.replaceAll("\\s", "");
		// Buscamos PLUGIN_FORMULARIOS y se queda con el primer parametro (idcampo)
		final Matcher matcher = PLUGINFORM_PATTERN.matcher(scriptSearch);
		while (matcher.find()) {
			final String sentencia = matcher.group();
			final String[] params = sentencia.split("\\(");
			deps.add(params[ConstantesNumero.N1].replaceAll("['|\"]", ""));
		}
		return deps;
	}

	/**
	 * Obtiene tipo de valor de un campo.
	 *
	 * @param pCampoDef
	 *            Definición campo
	 * @return Tipo valor
	 */
	private static TypeValor obtenerTipoValorCampo(final RComponente pCampoDef) {
		TypeValor tipoValor = null;
		final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(pCampoDef.getTipo());
		switch (tipoCampo) {
		case TEXTO:
			tipoValor = TypeValor.SIMPLE;
			break;
		case SELECTOR:
			final TypeSelector tipoSelector = UtilsSTG
					.traduceTipoSelector(((RComponenteSelector) pCampoDef).getTipoSelector());
			if (tipoSelector == TypeSelector.MULTIPLE) {
				tipoValor = TypeValor.LISTA_INDEXADOS;
			} else {
				tipoValor = TypeValor.INDEXADO;
			}
			break;
		case VERIFICACION:
			tipoValor = TypeValor.SIMPLE;
			break;
		default:
			throw new TipoNoControladoException("Tipo de campo " + tipoCampo + " no controlado");
		}
		return tipoValor;
	}

	/**
	 * Busca dependencias con otros campos dentro de la configuración de un
	 * selector.
	 *
	 * @param pCampoDef
	 *            Definición campo selector
	 * @return Lista de dependencias con otros campos
	 */
	private static List<String> buscarDependenciasSelector(final RComponenteSelector pCampoDef) {

		final List<String> deps = new ArrayList<>();

		final TypeListaValores tipoListaValores = TypeListaValores.fromString(pCampoDef.getTipoListaValores());

		// Si es de tipo dominio, buscamos si tiene parametros referenciados a campos
		if (tipoListaValores == TypeListaValores.DOMINIO) {
			if (pCampoDef.getListaDominio().getParametros() != null) {
				for (final RParametroDominio param : pCampoDef.getListaDominio().getParametros()) {
					if (TypeParametroDominio.fromString(param.getTipo()) == TypeParametroDominio.CAMPO) {
						deps.add(param.getValor());
					}
				}
			}
		}

		// Si es de tipo script, busca dependencias script
		if (tipoListaValores == TypeListaValores.SCRIPT && UtilsSTG.existeScript(pCampoDef.getScriptListaValores())) {
			deps.addAll(buscarDependenciasScript(pCampoDef.getScriptListaValores()));
		}

		return deps;

	}

	/**
	 * Deserializa valor de campo simple.
	 *
	 * @param idCampo
	 *            Id campo
	 * @param valorCampo
	 *            Valor campo simple serializado
	 * @param values
	 *            Lista valores deserializada
	 * @return Valor campo simple
	 */
	private static ValorCampo deserializarValorCampoSimple(final String idCampo, final String valorCampo,
			final String[] values) {
		ValorCampo res = null;
		switch (values.length) {
		case ConstantesNumero.N1: // Valor vacio
			res = ValorCampoSimple.createValorVacio(idCampo);
			break;
		case ConstantesNumero.N2: // Valor
			res = new ValorCampoSimple(idCampo, values[ConstantesNumero.N1]);
			break;
		default: // Formato incorrecto
			throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
		}
		return res;
	}

	/**
	 * Deserializa valor de campo indexado.
	 *
	 * @param idCampo
	 *            Id campo
	 * @param valorCampo
	 *            Valor campo indexado serializado
	 * @param values
	 *            Lista valores deserializada
	 * @return Valor campo indexado
	 */
	private static ValorCampo deserializarValorCampoIndexado(final String idCampo, final String valorCampo,
			final String[] values) {
		ValorCampo res = null;
		switch (values.length) {
		case ConstantesNumero.N1: // Valor vacio
			res = ValorCampoIndexado.createValorVacio(idCampo);
			break;
		case ConstantesNumero.N2: // Valor vacio
			if ("".equals(values[ConstantesNumero.N1])) {
				res = ValorCampoIndexado.createValorVacio(idCampo);
			} else {
				throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
			}
			break;
		case ConstantesNumero.N3: // Valor
			if ("".equals(values[ConstantesNumero.N1])) {
				res = ValorCampoIndexado.createValorVacio(idCampo);
			} else {
				res = new ValorCampoIndexado(idCampo, values[ConstantesNumero.N1], values[ConstantesNumero.N2]);
			}
			break;
		default: // Formato incorrecto
			throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
		}
		return res;
	}

	/**
	 * Deserializa valor de campo lista indexados.
	 *
	 * @param idCampo
	 *            Id campo
	 * @param valorCampo
	 *            Valor campo lista indexados serializado
	 * @param values
	 *            Lista valores deserializada
	 * @return Valor campo lista indexado
	 */
	private static ValorCampo deserializarValorCampoListaIndexados(final String idCampo, final String valorCampo,
			final String[] values) {
		ValorCampo res = null;
		switch (values.length) {
		case ConstantesNumero.N1: // Valor vacio
			res = ValorCampoListaIndexados.createValorVacio(idCampo);
			break;
		case ConstantesNumero.N2: // Valor vacio
			if ("".equals(values[ConstantesNumero.N1])) {
				res = ValorCampoListaIndexados.createValorVacio(idCampo);
			} else {
				throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
			}
			break;
		default:
			// El numero de elementos debe ser impar
			if (values.length % ConstantesNumero.N2 == 0) {
				throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
			}
			// Establecemos valor campo lista (cada dos valores montamos un
			// valor)
			final ValorCampoListaIndexados vcli = new ValorCampoListaIndexados();
			vcli.setId(idCampo);
			for (int i = ConstantesNumero.N1; i <= (values.length - ConstantesNumero.N2); i = i + ConstantesNumero.N2) {
				vcli.addValorIndexado(values[i], values[i + ConstantesNumero.N1]);
			}
			res = vcli;
		}
		return res;
	}

}
