package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.model.formulario.ParametrosAperturaFormulario;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RValorListaFija;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.ValorCampoFormularioCaracteresNoPermitidosException;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.service.component.integracion.DominiosComponent;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.ClzValorCampoCompuesto;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResValoresPosibles;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeListaValores;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFormulario;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Component("valoresPosiblesFormularioHelper")
public final class ValoresPosiblesFormularioHelperImpl implements ValoresPosiblesFormularioHelper {

	/** Motor de ejecución de scritps. */
	@Autowired
	private ScriptExec scriptFormulario;

	/** Acceso a dominios. **/
	@Autowired
	private DominiosComponent dominiosComponent;

	@Override
	public ValoresPosiblesCampo calcularValoresPosiblesCampoSelector(final DatosSesionFormularioInterno pDatosSesion,
			final RComponenteSelector pCampoDef, final boolean elemento) {

		// TODO LEL SE LLAMA TB INTERNAMENTE. REFACTORIZAR A FUNCION PRIVADA.

		return calcularValoresPosibles(pDatosSesion, pCampoDef, null, elemento);
	}

	@Override
	public List<ValorIndexado> calcularValoresPosiblesCampoSelectorDinamico(
			final DatosSesionFormularioInterno pDatosSesion, final RComponenteSelector pCampoDef,
			final String textoBusqueda, final boolean elemento) {

		// Filtrado valores:
		// - Si el dominio tiene un parámetro con el texto de búsqueda lo filtra el
		// dominio
		// - Si el dominio tiene no un parámetro con el texto de búsqueda se realiza
		// filtro manual

		final ValoresPosiblesCampo vpc = calcularValoresPosibles(pDatosSesion, pCampoDef, textoBusqueda, elemento);

		final List<ValorIndexado> valoresFiltrados = new ArrayList<>();
		final boolean filtroManual = !(UtilsFormularioInterno.existeParametroDominioTextoBusqueda(pCampoDef));
		for (final ValorIndexado vi : vpc.getValores()) {
			if (!filtroManual || (StringUtils.isNotBlank(textoBusqueda)
					&& UtilsFlujo.eliminarDiacriticos(vi.getDescripcion().toLowerCase())
							.indexOf(UtilsFlujo.eliminarDiacriticos(textoBusqueda.toLowerCase())) != -1)) {
				valoresFiltrados.add(vi);
			}
		}

		return valoresFiltrados;
	}

	@Override
	public List<ValoresPosiblesCampo> calcularValoresPosiblesPaginaActual(
			final DatosSesionFormularioInterno pDatosSesion, final boolean elemento) {

		final List<ValoresPosiblesCampo> res = new ArrayList<>();

		// Obtenemos definicion pagina
		final RPaginaFormulario paginaDef = pDatosSesion.obtenerDefinicionPaginaActual(
				elemento);

		// Para los campos de tipo selector calculamos valores posibles
		for (final RComponente campoDef : UtilsFormularioInterno.devuelveListaCampos(paginaDef)) {
			final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(campoDef.getTipo());
			if (tipoCampo == TypeCampo.SELECTOR) {
				final TypeSelector tipoSelector = UtilsSTG
						.traduceTipoSelector(((RComponenteSelector) campoDef).getTipoSelector());
				if (tipoSelector != TypeSelector.DINAMICO) {
					final ValoresPosiblesCampo valsCampo = calcularValoresPosiblesCampoSelector(pDatosSesion,
							(RComponenteSelector) campoDef, elemento);
					res.add(valsCampo);
				}
			}

		}

		return res;
	}

	// --------------------------------------------------------------------------------------------------
	// Funciones utilidad
	// --------------------------------------------------------------------------------------------------

	/**
	 * Verifica carácteres permitidos.
	 *
	 * @param pCampoDef
	 *                            Definición campo
	 * @param valoresPosibles
	 *                            Valores posibles
	 */
	private void verificarCaracteresPermitidos(final RComponenteSelector pCampoDef,
			final List<ValorIndexado> valoresPosibles) {
		for (final ValorIndexado vi : valoresPosibles) {

			// Verifica si valor tiene algun caracter no permitido
			if (!UtilsFormulario.comprobarCaracteresPermitidos(vi)) {
				throw new ValorCampoFormularioCaracteresNoPermitidosException(pCampoDef.getIdentificador(),
						vi.getValor() + " - " + vi.getDescripcion());
			}

		}
	}

	/**
	 * Genera valores posibles.
	 *
	 * @param pDatosSesion
	 *                            Datos sesion
	 * @param pCampoDef
	 *                            Definición campo
	 * @param valoresPosibles
	 *                            valores posibles
	 * @return valores posibles
	 */
	private ValoresPosiblesCampo generarValoresPosibles(final DatosSesionFormularioInterno pDatosSesion,
			final RComponenteSelector pCampoDef, final List<ValorIndexado> valoresPosibles) {
		final ValoresPosiblesCampo vpc = ValoresPosiblesCampo.createNewValoresPosiblesCampo();
		vpc.setId(pCampoDef.getIdentificador());
		// Revisamos que si los valores posibles tienen codigo tengan un codigo y una
		// descripcion asociada no vacia
		if (valoresPosibles != null && !valoresPosibles.isEmpty()) {
			for (final ValorIndexado vi : valoresPosibles) {
				if (StringUtils.isNotBlank(vi.getValor()) && StringUtils.isBlank(vi.getDescripcion())) {
					throw new ErrorConfiguracionException("El camp " + pCampoDef.getIdentificador() + " del formulari "
							+ pDatosSesion.getDatosInicioSesion().getIdFormulario()
							+ " té en la seva llista de valors posibles un valor amb descripció buida");
				}
				if (StringUtils.isNotBlank(vi.getValor()) && StringUtils.isBlank(vi.getValor())) {
					throw new ErrorConfiguracionException("El camp " + pCampoDef.getIdentificador() + " del formulari "
							+ pDatosSesion.getDatosInicioSesion().getIdFormulario()
							+ " té en la seva llista de valors posibles un valor amb descripció buida");
				}
			}
		}
		vpc.setValores(valoresPosibles);
		return vpc;
	}

	/**
	 * Calcula valores posibles desde script.
	 *
	 * @param pDatosSesion
	 *                         Datos sessión
	 * @param pCampoDef
	 *                         Definición campo
	 * @param elemento
	 *                         Indica si existe elemento
	 * @return valores posibles
	 */
	private List<ValorIndexado> calcularValoresPosiblesCampoDesdeScript(final DatosSesionFormularioInterno pDatosSesion,
			final RComponenteSelector pCampoDef, final boolean elemento) {

		final List<ValorIndexado> valoresPosibles = new ArrayList<>();

		// Verificamos que exista script
		if (!UtilsSTG.existeScript(pCampoDef.getScriptListaValores())) {
			throw new ErrorConfiguracionException(
					"No s'ha indicat script pel càlcul de valors possibles pel camp " + pCampoDef.getIdentificador());
		}

		// Ejecutamos script valores posibles
		final VariablesFormulario variablesFormulario = pDatosSesion.generarVariablesFormulario(pCampoDef.getIdentificador(), elemento);
		final Map<String, String> codigosError = UtilsSTG
				.convertLiteralesToMap(pCampoDef.getScriptListaValores().getLiterales());
		final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
				TypeScriptFormulario.SCRIPT_VALORES_POSIBLES, pCampoDef.getIdentificador(),
				pCampoDef.getScriptListaValores().getScript(), variablesFormulario, codigosError,
				pDatosSesion.getDefinicionTramite());

		// Generamos lista valores
		final ResValoresPosibles rvp = (ResValoresPosibles) rs.getResultado();
		for (final ClzValorCampoCompuesto vc : rvp.getValoresPosibles()) {
			valoresPosibles.add(ValorIndexado.createNewValorIndexado(vc.getCodigo(), vc.getDescripcion()));
		}
		return valoresPosibles;
	}

	/**
	 * Calcula valores posibles desde dominio.
	 *
	 * @param pDatosSesion  Datos sessión
	 * @param pCampoDef     Definición campo
	 * @param textoBusqueda Texto búsqueda (para selectores dinámicos)
	 * @param elemento
	 * @return valores posibles
	 */
	private List<ValorIndexado> calcularValoresPosiblesCampoDesdeDominio(
			final DatosSesionFormularioInterno pDatosSesion, final RComponenteSelector pCampoDef,
			final String textoBusqueda, boolean elemento) {
		// Obtenemos parametros acceso dominio
		final String idDominio = pCampoDef.getListaDominio().getDominio();
		final String campoCodigo = pCampoDef.getListaDominio().getCampoCodigo();
		final String campoDescripcion = pCampoDef.getListaDominio().getCampoDescripcion();
		if (StringUtils.isBlank(campoCodigo) || StringUtils.isBlank(campoDescripcion)) {
			throw new ErrorConfiguracionException("No s'ha especificat camp codi o descripció pel domini " + idDominio
					+ " en selector " + pCampoDef.getIdentificador());
		}

		List<ValorCampo> camposAccesibles;
		if (elemento) {
			camposAccesibles = pDatosSesion.getDatosFormulario()
					.obtenerEdicionElementoValoresAccesiblesCampo(pCampoDef.getIdentificador());
		} else {
			camposAccesibles = pDatosSesion.getDatosFormulario()
					.obtenerValoresAccesiblesCampoPaginaFormulario(pCampoDef.getIdentificador());
		}

		ParametrosAperturaFormulario parametrosApertura = pDatosSesion.getDatosInicioSesion().getParametros();
		String idioma = pDatosSesion.getDatosInicioSesion().getIdioma();

		final ParametrosDominio parametros = UtilsFormularioInterno.obtenerParametrosDominio(pCampoDef,	textoBusqueda, camposAccesibles, parametrosApertura, idioma);

		// Accedemos a dominio
		final ValoresDominio vals = dominiosComponent.recuperarDominio(idDominio, parametros,
				pDatosSesion.getDefinicionTramite());
		// Obtenemos lista valores a partir de los valores del dominio
		final List<ValorIndexado> valoresPosibles = UtilsFormularioInterno.extraerValoresPosibles(campoCodigo,
				campoDescripcion, vals);
		return valoresPosibles;
	}

	/**
	 * Calcula valores posibles desde una lista fija.
	 *
	 * @param pCampoDef
	 *                      Definición campo
	 * @return valores posibles
	 */
	private List<ValorIndexado> calcularValoresPosiblesCampoDesdeListaFija(final RComponenteSelector pCampoDef) {
		final List<ValorIndexado> valoresPosibles = new ArrayList<>();
		for (final RValorListaFija valorFijo : pCampoDef.getListaFija()) {
			valoresPosibles
					.add(ValorIndexado.createNewValorIndexado(valorFijo.getCodigo(), valorFijo.getDescripcion()));
		}
		return valoresPosibles;
	}

	/**
	 * Calcula valores posibles.
	 *
	 * @param pDatosSesion
	 *                          Datos sesión
	 * @param pCampoDef
	 *                          Definición campo
	 * @param textoBusqueda
	 *                          Texto búsqueda
	 * @param elemento
	 *                          Indica si existe elemento
	 * @return Valores posibles
	 */
	private ValoresPosiblesCampo calcularValoresPosibles(final DatosSesionFormularioInterno pDatosSesion,
			final RComponenteSelector pCampoDef, final String textoBusqueda, final boolean elemento) {
		List<ValorIndexado> valoresPosibles = null;

		// Verificamos origen lista valores
		final TypeListaValores tipoListaValores = UtilsSTG.traduceTipoListaValores(pCampoDef.getTipoListaValores());
		if (tipoListaValores == null) {
			throw new ErrorConfiguracionException("Tipus llista valors " + pCampoDef.getTipoListaValores()
					+ " no contemplat en camp " + pCampoDef.getIdentificador());
		}

		// Calculamos lista valores
		switch (tipoListaValores) {
		case FIJA:
			valoresPosibles = calcularValoresPosiblesCampoDesdeListaFija(pCampoDef);
			break;
		case DOMINIO:
			valoresPosibles = calcularValoresPosiblesCampoDesdeDominio(pDatosSesion, pCampoDef, textoBusqueda, elemento);
			break;
		case SCRIPT:
			valoresPosibles = calcularValoresPosiblesCampoDesdeScript(pDatosSesion, pCampoDef, elemento);
			break;
		default:
			throw new TipoNoControladoException("Tipus llista valors " + tipoListaValores.toString()
					+ " no controlat per camp " + pCampoDef.getIdentificador());
		}

		// Verificamos caracteres permitidos y valores reservados
		verificarCaracteresPermitidos(pCampoDef, valoresPosibles);

		// Añadimos valores posibles.
		final ValoresPosiblesCampo vpc = generarValoresPosibles(pDatosSesion, pCampoDef, valoresPosibles);
		return vpc;
	}

}
