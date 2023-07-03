package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.caib.sistramit.core.service.model.formulario.ParametrosAperturaFormulario;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteCampoOculto;
import es.caib.sistrages.rest.api.interna.RComponenteCheckbox;
import es.caib.sistrages.rest.api.interna.RComponenteListaElementos;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RLineaComponentes;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RParametroDominio;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.ValorCampoFormularioNoValidoException;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
import es.caib.sistramit.core.api.model.formulario.ListaElementosColumna;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaElementos;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.PaginaData;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeListaValores;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeParametroDominio;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
import es.caib.sistramit.core.service.model.script.formulario.PlgDatosFormularioInt;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

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
	 * Devuelve lista de campos formulario.
	 *
	 * @param defFormulario
	 *                      Definición formulario
	 * @return Lista campos
	 */
	public static List<RComponente> devuelveListaCampos(final RFormularioInterno defFormulario) {
		final List<RComponente> campos = new ArrayList<>();
		for (final RPaginaFormulario defPagina : defFormulario.getPaginas()) {
			campos.addAll(devuelveListaCampos(defPagina));
		}
		return campos;
	}

	/**
	 * Devuelve lista de campos de un tipo.
	 *
	 * @param pagForDef
	 *                      Definición página.
	 * @param tipoCampo
	 *                      Tipo campo
	 * @return Lista ids campos de ese tipo
	 */
	public static List<String> devuelveCamposTipo(final RPaginaFormulario pagForDef, final TypeCampo tipoCampo) {

		// TODO LEL CAMBIAR ESTOS DEVUELVE POR OBTENER?? O POR BUSCAR??

		final List<String> camposTipo = new ArrayList<>();
		final List<RComponente> camposPagFor = UtilsFormularioInterno.devuelveListaCampos(pagForDef);
		for (final RComponente c : camposPagFor) {
			if (UtilsSTG.traduceTipoCampo(c.getTipo()) == tipoCampo) {
				camposTipo.add(c.getIdentificador());
			}
		}
		return camposTipo;
	}

	/**
	 * Devuelve lista de campos de la página.
	 *
	 * @param defPagina
	 *                      Definición página
	 * @return Lista campos
	 */
	public static List<RComponente> devuelveListaCampos(final RPaginaFormulario defPagina) {
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
	 * Obtiene lista ids campos captcha de la página.
	 *
	 * @param defPagina
	 *                      Definición página
	 * @return Lista ids campos captcha de la página
	 */
	public static List<String> devuelveListaCamposCaptcha(final RPaginaFormulario defPagina) {
		final List<String> res = new ArrayList<>();
		final List<RComponente> camposPag = devuelveListaCampos(defPagina);
		for (final RComponente c : camposPag) {
			final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(c.getTipo());
			if (tipoCampo == TypeCampo.CAPTCHA) {
				res.add(c.getIdentificador());
			}
		}
		return res;
	}

	/**
	 * Devuelve componente la página.
	 *
	 * @param idComponente
	 *                         idComponente
	 * @return Campo
	 */
	public static RComponente devuelveComponentePagina(final RPaginaFormulario defPagina, final String idComponente) {
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
	 * @param pCampoDef Definición campo
	 * @return boolean
	 */
	public static boolean esCampo(final RComponente pCampoDef) {
		final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(pCampoDef.getTipo());
		return (tipoCampo != null);
	}

	/**
	 * Obtiene propiedades campo.
	 *
	 * @param pCampoDef
	 *                      definición campo
	 * @return propiedades campo
	 */
	public static RPropiedadesCampo obtenerPropiedadesCampo(final RComponente pCampoDef) {
		RPropiedadesCampo res = null;
		final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(pCampoDef.getTipo());
		if (tipoCampo == null) {
			throw new ErrorConfiguracionException("Component " + pCampoDef.getIdentificador() + " no és un camp");
		}
		switch (tipoCampo) {
		case TEXTO:
			res = ((RComponenteTextbox) pCampoDef).getPropiedadesCampo();
			break;
		case SELECTOR:
			res = ((RComponenteSelector) pCampoDef).getPropiedadesCampo();
			break;
		case VERIFICACION:
			res = ((RComponenteCheckbox) pCampoDef).getPropiedadesCampo();
			break;
		case OCULTO:
			res = ((RComponenteCampoOculto) pCampoDef).getPropiedadesCampo();
			break;
		case CAPTCHA:
			// No tiene props especificas, retornamos propiedades por defecto
			res = new RPropiedadesCampo();
			break;
		case LISTA_ELEMENTOS:
			res = ((RComponenteListaElementos) pCampoDef).getPropiedadesCampo();
			break;
		default:
			throw new TipoNoControladoException("Tipus campo no controlat: " + tipoCampo);
		}
		return res;
	}

	/**
	 * Crea valor vacío según tipo de campo.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return Valor campo vacio
	 */
	public static ValorCampo crearValorVacio(final RComponente pCampoDef) {
		final TypeValor tipoValor = obtenerTipoValorCampo(pCampoDef);
		ValorCampo res;
		if (UtilsSTG.traduceTipoCampo(pCampoDef.getTipo()) == TypeCampo.VERIFICACION) {
			// Si es check, valor vacío es no seleccionado
			res = new ValorCampoSimple(pCampoDef.getIdentificador(),
					((RComponenteCheckbox) pCampoDef).getValorNoChecked());
		} else {
			// Creamos valor vacío
			res = UtilsFormulario.crearValorVacio(pCampoDef.getIdentificador(), tipoValor);
		}
		return res;
	}

	/**
	 * Busca dependencias con otros campos.
	 *
	 * @param pCampoDef
	 *                      Definición campo
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
	 * Obtiene definición página a partir identificador.
	 *
	 * @param defFormulario
	 *                         Definición formulario
	 * @param identificadorPagina
	 * 	 *                         Id página
	 * @return definición página
	 */
	public static RPaginaFormulario obtenerDefinicionPaginaFormulario(final RFormularioInterno defFormulario,
			final String identificadorPagina) {
		RPaginaFormulario res = null;
		if (defFormulario != null && StringUtils.isNotBlank(identificadorPagina)) {
			for (final RPaginaFormulario paginaDef : defFormulario.getPaginas()) {
				if (identificadorPagina.equals(paginaDef.getIdentificador())) {
					res = paginaDef;
					break;
				}
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException("No existeix pàgina amb id: " + identificadorPagina);
		}
		return res;
	}


	/**
	 * Obtiene definición página a partir identificador.
	 *
	 * @param defFormulario
	 *                         Definición formulario
	 * @param identificadorPagina  Id pagina (si es nulo, se devuelve la siguiente página será la primera página).
	 * @return definición página siguiente a id página
	 */
	public static RPaginaFormulario obtenerDefinicionSiguientePaginaFormulario(final RFormularioInterno defFormulario,
																	  final String identificadorPagina) {
		RPaginaFormulario res = null;
		boolean siguiente = false;
		if (defFormulario != null) {
			if (StringUtils.isBlank(identificadorPagina)) {
				// Si id pagina = null, coge la primera pagina
				siguiente = true;
			}
			for (final RPaginaFormulario paginaDef : defFormulario.getPaginas()) {
				if (siguiente) {
					res = paginaDef;
					break;
				} else if (identificadorPagina.equals(paginaDef.getIdentificador())) {
					siguiente = true;
				}
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException("No existeix pàgina següent a: " + identificadorPagina);
		}
		return res;
	}

	/**
	 * Obtiene indice definición página.
	 * @param defFormulario Definición formulario
	 * @param identificadorPagina identificador página
	 * @return indice definición página.
	 */
	public static int obtenerIndiceDefinicionPaginaFormulario(final RFormularioInterno defFormulario,
													final String identificadorPagina) {
		Integer res = null;
		if (StringUtils.isNotBlank(identificadorPagina)) {
			int index = 0;
			for (final RPaginaFormulario paginaDef : defFormulario.getPaginas()) {
				index++;
				if (identificadorPagina.equals(paginaDef.getIdentificador())) {
					res = index;
					break;
				}
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException("No existeix pàgina amb id: " + identificadorPagina);
		}
		return res;
	}


	/**
	 * Verifica si existe filtrado por texto busqueda.
	 *
	 * @param pCampoDef
	 *
	 * @return boolean
	 */
	public static boolean existeParametroDominioTextoBusqueda(final RComponenteSelector pCampoDef) {
		boolean res = false;
		if (UtilsSTG.traduceTipoListaValores(pCampoDef.getTipoListaValores()) == TypeListaValores.DOMINIO) {
			final List<RParametroDominio> parametrosDef = pCampoDef.getListaDominio().getParametros();
			if (parametrosDef != null) {
				for (final RParametroDominio parDef : parametrosDef) {
					final TypeParametroDominio tipoParametro = UtilsSTG.traduceTipoParametroDominio(parDef.getTipo());
					if (tipoParametro == TypeParametroDominio.BUSQUEDA) {
						res = true;
					}
				}
			}
		}
		return res;
	}


	/**
	 * Extrae los valores posibles a partir de los valores del dominio.
	 *
	 * @param pCampoCodigo
	 *                              Campo código
	 * @param pCampoDescripcion
	 *                              Campo descripción
	 * @param pValoresDominio
	 *                              Valores dominio
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
	 *                                  Id campo
	 * @param valorCampo
	 *                                  Valor serializado campo
	 * @return ValorCampo
	 */
	public static ValorCampo deserializarValorCampo(final String idCampo, final String valorCampo) {
		// El valor serializado no puede estar vacio
		if (StringUtils.isBlank(valorCampo)) {
			throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
		}

		// Deserializamos valor
		final ValorCampo vc = (ValorCampo) UtilsFlujo.jsonToJava(valorCampo, ValorCampo.class);

		// Verificamos que concuerda id campo con el valor campo
		if (vc == null || idCampo == null || !idCampo.equals(vc.getId())) {
			throw new ValorCampoFormularioNoValidoException(idCampo, valorCampo);
		}

		return vc;
	}

	/**
	 * Comprueba si algún campo modificado se encuentra en la lista de campos
	 * dependientes.
	 *
	 * @param pModificados
	 *                               Lista de campos modificados
	 * @param pDependenciasCampo
	 *                               Lista de campos dependientes
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
	 *                         Lista de valores
	 * @param idCampo
	 *                         Id campo
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
	 * Busca valor elemento.
	 *
	 * @param idCampoListaElementos
	 *                                   id campo lista elementos
	 * @param indiceElemento
	 *                                   indice elemento
	 * @param valoresPaginaPrincipal
	 *                                   valores pagina
	 * @return valor elemento
	 */
	public static List<ValorCampo> buscarValorElemento(final String idCampoListaElementos, final Integer indiceElemento,
			final List<ValorCampo> valoresPaginaPrincipal) {
		List<ValorCampo> ve = new ArrayList<ValorCampo>();
		if (indiceElemento != null) {
			final ValorCampoListaElementos vcle = (ValorCampoListaElementos) buscarValorCampo(valoresPaginaPrincipal,
					idCampoListaElementos);
			if (vcle.getValor() != null && vcle.getValor().get(indiceElemento) != null
					&& vcle.getValor().get(indiceElemento).getElemento() != null) {
				ve = vcle.getValor().get(indiceElemento).getElemento();
			}
		}
		return ve;
	}

	/**
	 * Busca configuración campo.
	 *
	 * @param configuracionesCampo
	 *                                 Lista de valores
	 * @param idCampo
	 *                                 Id campo
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
	 *                                 Lista de valores
	 * @param idCampo
	 *                                 Id campo
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
	 *                            valores posibles
	 * @param idCampo
	 *                            idcampo
	 * @return valores posibles campo
	 */
	public static ValoresPosiblesCampo buscarValoresPosibles(final List<ValoresPosiblesCampo> valoresPosibles,
			final String idCampo) {
		ValoresPosiblesCampo res = null;
		for (final ValoresPosiblesCampo cc : valoresPosibles) {
			if (cc.getId().equals(idCampo)) {
				res = cc;
				break;
			}
		}
		return res;
	}

	/**
	 * Crea PaginaFormulario.
	 *
	 * @param pagData
	 *                       datos página
	 * @return PaginaFormulario
	 */
	public static PaginaFormulario convertToPaginaFormulario(final PaginaData pagData) {
		// Establece datos página creando una copia para evitar modificación
		final PaginaFormulario p = new PaginaFormulario(pagData.getIdFormulario());
		p.setIdFormulario(pagData.getIdFormulario());
		p.setIdPagina(pagData.getIdentificador());
		p.setMostrarTitulo(pagData.getMostrarTitulo());
		p.setTitulo(pagData.getTitulo());
		for (final ConfiguracionCampo cc : pagData.getConfiguracion()) {
			p.getConfiguracion().add(SerializationUtils.clone(cc));
		}
		for (final ValorCampo vc : pagData.getValores()) {
			// No pasamos valores captcha
			if (p.getConfiguracion(vc.getId()).getTipo() != TypeCampo.CAPTCHA) {
				p.getValores().add(SerializationUtils.clone(vc));
			}
		}
		return p;
	}


	/**
	 * Obtiene columnas a mostrar en tabla lista elementos.
	 *
	 * @param pCampoDef
	 *                      Definición campo
	 * @return columnas a mostrar
	 */
	public static List<ListaElementosColumna> obtenerColumnasMostrarListaElementos(
			final RComponenteListaElementos pCampoDef) {
		final List<ListaElementosColumna> columnas = new ArrayList<ListaElementosColumna>();
		final List<RComponente> campos = UtilsFormularioInterno.devuelveListaCampos(pCampoDef.getPaginaElemento());
		for (final RComponente c : campos) {
			final RPropiedadesCampo propsGenerales = UtilsFormularioInterno.obtenerPropiedadesCampo(c);
			if (propsGenerales.isListaElementosMostrar()) {
				final ListaElementosColumna lec = new ListaElementosColumna();
				lec.setId(c.getIdentificador());
				lec.setDescripcion(c.getEtiqueta());
				lec.setAncho(propsGenerales.getListaElementosAnchura());
				columnas.add(lec);
			}
		}
		return columnas;
	}

	/**
	 * OBtiene valor campo.
	 *
	 * @param identificador
	 *                          id
	 * @param xml
	 *                          xml
	 * @return valor
	 */
	public static ValorCampo obtenerValorCampo(final String identificador, final XmlFormulario xml) {
		ValorCampo res = null;

		// Obtiene valor campo
		if (xml.getValores() != null && identificador != null) {
			for (final ValorCampo valor : xml.getValores()) {
				if (valor.getId() != null && identificador.equals(valor.getId())) {
					res = valor;
				}
			}
		}
		return res;
	}

	/**
	 * Obtiene definición página elemento.
	 *
	 * @param definicionFormulario
	 *                                  Definición formulario
	 * @param idPagina
	 *                                  id Pagina
	 * @param idCampoListaElementos
	 *                                  id campo lista elementos
	 * @return definición página elemento.
	 */
	public static RPaginaFormulario obtenerDefinicionPaginaElemento(final RFormularioInterno definicionFormulario,
			final String idPagina, final String idCampoListaElementos) {
		RPaginaFormulario rpf = null;
		final RPaginaFormulario pf = obtenerDefinicionPaginaFormulario(definicionFormulario, idPagina);
		final List<RComponente> listaCampos = devuelveListaCampos(pf);
		for (final RComponente rc : listaCampos) {
			if (rc.getIdentificador().equals(idCampoListaElementos)) {
				final RComponenteListaElementos rcl = (RComponenteListaElementos) rc;
				rpf = rcl.getPaginaElemento();
				break;
			}
		}
		return rpf;
	}

	/**
	 * Obtiene parametros dominio.
	 *
	 * @param pCampoDef
	 *                          Definicion campo selector
	 * @param textoBusqueda
	 *                          Texto búsqueda (para selectores dinámicos)
	 * @return parametros
	 */
	public static ParametrosDominio obtenerParametrosDominio(final RComponenteSelector pCampoDef, final String textoBusqueda,
															 final List<ValorCampo> camposAccesibles,
															 final ParametrosAperturaFormulario parametrosAperturaFormulario,
															 final String idioma) {


		final ParametrosDominio parametros = new ParametrosDominio();

		final List<RParametroDominio> parametrosDef = pCampoDef.getListaDominio().getParametros();
		if (parametrosDef != null) {
			for (final RParametroDominio parDef : parametrosDef) {

				final TypeParametroDominio tipoParametro = UtilsSTG.traduceTipoParametroDominio(parDef.getTipo());
				if (tipoParametro == null) {
					throw new ErrorConfiguracionException("Tipus paràmetre " + parDef.getTipo()
							+ " no suportat en camp " + pCampoDef.getIdentificador());
				}

				switch (tipoParametro) {
					case CAMPO:
						final ValorCampo vc = UtilsFormularioInterno.buscarValorCampo(camposAccesibles, parDef.getValor());
						parametros.addParametro(parDef.getIdentificador(), valorCampoParametroDominio(vc));
						break;
					case CONSTANTE:
						parametros.addParametro(parDef.getIdentificador(), parDef.getValor());
						break;
					case PARAMETRO:
						if (!parametrosAperturaFormulario.existeParametro(parDef.getValor())) {
							throw new TipoNoControladoException(
									"No existeix paràmetre apertura formulari usat com per paràmetre domini: "
											+ parDef.getValor());
						}
						final String paramForm = parametrosAperturaFormulario
								.getParametro(parDef.getValor());
						parametros.addParametro(parDef.getIdentificador(), paramForm);
						break;
					case SESION:
						if (StringUtils.equalsIgnoreCase("idioma", parDef.getValor())) {
							parametros.addParametro(parDef.getIdentificador(), idioma);
						} else {
							throw new TipoNoControladoException(
									"Propietat per paràmetre domini de tipus sessió no controlat: " + parDef.getValor());
						}
						break;
					case BUSQUEDA:
						if (UtilsSTG.traduceTipoSelector(pCampoDef.getTipoSelector()) != TypeSelector.DINAMICO) {
							throw new TipoNoControladoException(
									"Paràmetre text de cerca només permès per a selector dinàmic");
						}
						parametros.addParametro(parDef.getIdentificador(), textoBusqueda);
						break;
					default:
						throw new TipoNoControladoException("Tipus de paràmetre domini no controlat: " + parDef.getTipo());
				}
			}
		}
		return parametros;
	}



	// ---------------------------------------------------------------------------------------------------
	// Funciones auxiliares
	// ---------------------------------------------------------------------------------------------------

	/**
	 * Busca dependencias con otros campos dentro de un script.
	 *
	 * @param rScript
	 *                   Script SCript
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
	 *                      Definición campo
	 * @return Tipo valor
	 */
	private static TypeValor obtenerTipoValorCampo(final RComponente pCampoDef) {
		TypeValor tipoValor = null;
		final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(pCampoDef.getTipo());
		switch (tipoCampo) {
		case TEXTO:
			tipoValor = TypeValor.SIMPLE;
			break;
		case OCULTO:
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
		case LISTA_ELEMENTOS:
			tipoValor = TypeValor.LISTA_ELEMENTOS;
			break;
		case CAPTCHA:
			tipoValor = TypeValor.SIMPLE;
			break;
		default:
			throw new TipoNoControladoException("Tipus de camp " + tipoCampo + " no controlat");
		}
		return tipoValor;
	}

	/**
	 * Busca dependencias con otros campos dentro de la configuración de un
	 * selector.
	 *
	 * @param pCampoDef
	 *                      Definición campo selector
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
	 * Comprueba si existe campo en la lista.
	 *
	 * @param idCampoDep
	 *                       id campo
	 * @param campos
	 *                       lista campos
	 * @return true si existe
	 */
	public static boolean existeCampo(final String idCampoDep, final List<RComponente> campos) {
		boolean res = false;
		if (campos != null) {
			for (final RComponente rc : campos) {
				res = rc.getIdentificador().equals(idCampoDep);
				if (res) {
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Obtiene el valor campo para ser pasaso a un dominio. Los valores simples será
	 * el valor, para los indexados sera el código, y para la lista de indexados se
	 * pasará la lista de códigos separadas por coma. En caso de que el valor del
	 * campo sea nulo se devolverá la cadena vacía.
	 *
	 * @param valorCampo
	 *                       Valor campo
	 *
	 * @return Valor campo para pasarlo como dominio.
	 */
	private static String valorCampoParametroDominio(final ValorCampo valorCampo) {
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
					throw new TipoNoControladoException("Tipus de camp no controlat: " + valorCampo.getTipo());
			}
		}
		if (res == null) {
			res = "";
		}
		return res;
	}


}
