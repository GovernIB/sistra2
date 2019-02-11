package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RValorListaFija;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.ValorCampoFormularioCaracteresNoPermitidosException;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
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
	public ValoresPosiblesCampo calcularValoresPosiblesCampoSelector(DatosSesionFormularioInterno pDatosSesion,
			RComponenteSelector pCampoDef) {
		List<ValorIndexado> valoresPosibles = null;

		// Verificamos origen lista valores
		final TypeListaValores tipoListaValores = UtilsSTG.traduceTipoListaValores(pCampoDef.getTipoListaValores());
		if (tipoListaValores == null) {
			throw new ErrorConfiguracionException("Tipo lista valores " + pCampoDef.getTipoListaValores()
					+ " no contemplado en campo " + pCampoDef.getIdentificador());
		}

		// Calculamos lista valores
		switch (tipoListaValores) {
		case FIJA:
			valoresPosibles = calcularValoresPosiblesCampoDesdeListaFija(pCampoDef);
			break;
		case DOMINIO:
			valoresPosibles = calcularValoresPosiblesCampoDesdeDominio(pDatosSesion, pCampoDef);
			break;
		case SCRIPT:
			valoresPosibles = calcularValoresPosiblesCampoDesdeScript(pDatosSesion, pCampoDef);
			break;
		default:
			throw new TipoNoControladoException("Tipo lista valores " + tipoListaValores.toString()
					+ " no controlado para campo " + pCampoDef.getIdentificador());
		}

		// Verificamos caracteres permitidos
		verificarCaracteresPermitidos(pCampoDef, valoresPosibles);

		// Añadimos valores posibles.
		final ValoresPosiblesCampo vpc = generarValoresPosibles(pDatosSesion, pCampoDef, valoresPosibles);
		return vpc;
	}

	@Override
	public List<ValoresPosiblesCampo> calcularValoresPosiblesPaginaActual(
			final DatosSesionFormularioInterno pDatosSesion) {

		final List<ValoresPosiblesCampo> res = new ArrayList<>();

		// Obtenemos definicion pagina
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(pDatosSesion);

		// Para los campos de tipo selector calculamos valores posibles
		for (final RComponente campoDef : UtilsFormularioInterno.devuelveListaCampos(paginaDef)) {
			final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(campoDef.getTipo());
			if (tipoCampo == TypeCampo.SELECTOR) {
				final ValoresPosiblesCampo valsCampo = calcularValoresPosiblesCampoSelector(pDatosSesion,
						(RComponenteSelector) campoDef);
				res.add(valsCampo);
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
	 *            Definición campo
	 * @param valoresPosibles
	 *            Valores posibles
	 */
	private void verificarCaracteresPermitidos(RComponenteSelector pCampoDef, List<ValorIndexado> valoresPosibles) {
		for (final ValorIndexado vi : valoresPosibles) {
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
	 *            Datos sesion
	 * @param pCampoDef
	 *            Definición campo
	 * @param valoresPosibles
	 *            valores posibles
	 * @return valores posibles
	 */
	private ValoresPosiblesCampo generarValoresPosibles(DatosSesionFormularioInterno pDatosSesion,
			RComponenteSelector pCampoDef, List<ValorIndexado> valoresPosibles) {
		final ValoresPosiblesCampo vpc = ValoresPosiblesCampo.createNewValoresPosiblesCampo();
		vpc.setId(pCampoDef.getIdentificador());
		// Revisamos que si los valores posibles tienen codigo tengan un codigo y una
		// descripcion asociada no vacia
		if (valoresPosibles != null && !valoresPosibles.isEmpty()) {
			for (final ValorIndexado vi : valoresPosibles) {
				if (StringUtils.isNotBlank(vi.getValor()) && StringUtils.isBlank(vi.getDescripcion())) {
					throw new ErrorConfiguracionException("El campo " + pCampoDef.getIdentificador()
							+ " del formulario " + pDatosSesion.getIdFormulario()
							+ " tiene en su lista de valores posibles un valor con descripcion vacia");
				}
				if (StringUtils.isNotBlank(vi.getValor()) && StringUtils.isBlank(vi.getValor())) {
					throw new ErrorConfiguracionException("El campo " + pCampoDef.getIdentificador()
							+ " del formulario " + pDatosSesion.getIdFormulario()
							+ " tiene en su lista de valores posibles un valor con código vacio");
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
	 *            Datos sessión
	 * @param pCampoDef
	 *            Definición campo
	 * @return valores posibles
	 */
	private List<ValorIndexado> calcularValoresPosiblesCampoDesdeScript(DatosSesionFormularioInterno pDatosSesion,
			RComponenteSelector pCampoDef) {

		final List<ValorIndexado> valoresPosibles = new ArrayList<>();

		// Ejecutamos script valores posibles
		final VariablesFormulario variablesFormulario = UtilsFormularioInterno.generarVariablesFormulario(pDatosSesion,
				pCampoDef.getIdentificador());
		final Map<String, String> codigosError = UtilsSTG
				.convertLiteralesToMap(pCampoDef.getScriptListaValores().getLiterales());
		final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
				TypeScriptFormulario.SCRIPT_VALORES_POSIBLES, pCampoDef.getIdentificador(),
				pCampoDef.getScriptListaValores().getScript(), variablesFormulario, codigosError,
				pDatosSesion.getDefinicionTramite());
		if (rs.isError()) {
			throw new ErrorScriptException(TypeScriptFormulario.SCRIPT_VALORES_POSIBLES.name(),
					pDatosSesion.getDatosInicioSesion().getIdSesionTramitacion(), pCampoDef.getIdentificador(),
					rs.getMensajeError());
		}

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
	 * @param pDatosSesion
	 *            Datos sessión
	 * @param pCampoDef
	 *            Definición campo
	 * @return valores posibles
	 */
	private List<ValorIndexado> calcularValoresPosiblesCampoDesdeDominio(DatosSesionFormularioInterno pDatosSesion,
			RComponenteSelector pCampoDef) {
		// Obtenemos parametros acceso dominio
		final String idDominio = pCampoDef.getListaDominio().getDominio();
		final String campoCodigo = pCampoDef.getListaDominio().getCampoCodigo();
		final String campoDescripcion = pCampoDef.getListaDominio().getCampoDescripcion();
		if (StringUtils.isBlank(campoCodigo) || StringUtils.isBlank(campoDescripcion)) {
			throw new ErrorConfiguracionException("No se ha especificado campo código o descripción para el dominio "
					+ idDominio + " en selector " + pCampoDef.getIdentificador());
		}
		final ParametrosDominio parametros = UtilsFormularioInterno.obtenerParametrosDominio(pDatosSesion, pCampoDef);
		// Accedemos a dominio
		final ValoresDominio vals = dominiosComponent.recuperarDominio(idDominio, parametros,
				pDatosSesion.getDefinicionTramite());
		// Obtenemos lista de valores del campo a partir de los valores del
		// dominio
		final List<ValorIndexado> valoresPosibles = UtilsFormularioInterno.extraerValoresPosibles(campoCodigo,
				campoDescripcion, vals);
		return valoresPosibles;
	}

	/**
	 * Calcula valores posibles desde una lista fija.
	 *
	 * @param pCampoDef
	 *            Definición campo
	 * @return valores posibles
	 */
	private List<ValorIndexado> calcularValoresPosiblesCampoDesdeListaFija(RComponenteSelector pCampoDef) {
		final List<ValorIndexado> valoresPosibles = new ArrayList<>();
		for (final RValorListaFija valorFijo : pCampoDef.getListaFija()) {
			valoresPosibles
					.add(ValorIndexado.createNewValorIndexado(valorFijo.getCodigo(), valorFijo.getDescripcion()));
		}
		return valoresPosibles;
	}

}
