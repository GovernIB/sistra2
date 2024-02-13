package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistramit.core.api.exception.CampoFormularioNoExisteException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ValorCampoFormularioCaracteresNoPermitidosException;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelector;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoBuscadorDinamico;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorResetCampos;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResEstadoCampo;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResValorCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.PaginaData;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFormulario;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Component("calculoDatosFormularioHelper")
public final class CalculoDatosFormularioHelperImpl implements CalculoDatosFormularioHelper {

	/** Motor de ejecución de scritps. */
	@Autowired
	private ScriptExec scriptFormulario;

	/** Helper para calculo de valores posibles de un campo. */
	@Autowired
	private ValoresPosiblesFormularioHelper valoresPosiblesHelper;

	/** Helper para calculo de valores posibles de un campo. */
	@Autowired
	private ConfiguracionFormularioHelper configuracionFormularioHelper;

	@Override
	public ResultadoEvaluarCambioCampo calcularDatosPaginaCambioCampo(final DatosSesionFormularioInterno datosSesion,
			final String idCampo, final List<ValorCampo> valoresPagina, final boolean elemento) {

		// Debe existir un campo que se modifique
		if (idCampo == null) {
			throw new CampoFormularioNoExisteException(datosSesion.getDatosInicioSesion().getIdFormulario(), null);
		}

		// Creamos objeto resultado para indicar los cambios que se han
		// realizado
		final ResultadoEvaluarCambioCampo res = new ResultadoEvaluarCambioCampo();

		// Actualizamos datos de la pagina actual con los nuevos datos
		final PaginaData paginaActual = datosSesion.getDatosFormulario().obtenerPaginaDataActual(elemento);
		if (valoresPagina != null) {
			paginaActual.actualizarValoresPagina(valoresPagina);
		}

		// Ejecutamos scripts campos pantalla
		// - Script de validacion campo
		calcularDatosPaginaValidacionCampo(datosSesion, idCampo, elemento, res);

		// Ejecutamos resto de scripts solo si se pasa el script de validacion
		if (!UtilsFlujo.isErrorValidacion(res.getValidacion())) {
			// - Script autorrellenable
			calcularDatosPaginaAutorrellenable(datosSesion, idCampo, elemento, res);
			// - Script valores posibles para selectores
			calcularDatosPaginaValoresPosibles(datosSesion, elemento, idCampo, res);
			// - Script estado
			calcularDatosPaginaEstado(datosSesion, idCampo, elemento, res);
		}

		// Devolvemos cambios realizados
		return res;
	}

	@Override
	public void recalcularDatosPagina(final DatosSesionFormularioInterno datosSesion, final boolean elemento) {

		// Pagina actual
		final PaginaData pag = datosSesion.getDatosFormulario().obtenerPaginaDataActual(elemento);
		// Definición página actual
		final RPaginaFormulario pagDef = datosSesion.obtenerDefinicionPaginaActual( elemento);

		// Recorrer todos los campos de la pagina que tienen script autorrellenable y
		// ejecutar si son solo lectura o no tienen valor
		final List<RComponente> campos = UtilsFormularioInterno.devuelveListaCampos(pagDef);
		for (final RComponente rc : campos) {
			final RPropiedadesCampo pc = UtilsFormularioInterno.obtenerPropiedadesCampo(rc);
			final ValorCampo vc = pag.getValorCampo(rc.getIdentificador());
			// Verifica si existe script y son solo lectura o no tienen valor
			if (UtilsSTG.existeScript(pc.getScriptAutorrellenable())
					&& (pc.isSoloLectura() || (vc == null || vc.esVacio()))) {
				// Ejecutar script autorrellenable y actualiza valor campo
				ValorCampo vca = ejecutarScriptAutorrellenable(datosSesion, rc, elemento);
				// Si se ha reseteado campo, creamos valor vacio dependiendo del campo
				if (vca instanceof ValorResetCampos) {
					vca = UtilsFormularioInterno.crearValorVacio(rc);
				}
				pag.actualizarValorCampo(vca);
				// Evalua cambios a realizar tras modificar campo
				if (!vca.esValorIgual(vc)) {
					// Calcula cambios (actualiza valores modificados pagina)
					final ResultadoEvaluarCambioCampo res = new ResultadoEvaluarCambioCampo();
					calcularDatosPaginaAutorrellenable(datosSesion, rc.getIdentificador(), elemento, res);
				}
			}
		}
	}

	@Override
	public ResultadoBuscadorDinamico calcularValoresPosiblesSelectorDinamico(
			final DatosSesionFormularioInterno datosSesion, final String idCampo, final String textoCampo,
			final List<ValorCampo> valoresPagina, final boolean elemento) {

		// Recupera pagina actual
		final PaginaData paginaActual = datosSesion.getDatosFormulario().obtenerPaginaDataActual(elemento);

		// Recupera definicion campo selector
		final ConfiguracionCampo confCampo = paginaActual.getConfiguracionCampo(idCampo);
		if (confCampo == null || confCampo.getTipo() != TypeCampo.SELECTOR
				|| ((ConfiguracionCampoSelector) confCampo).getContenido() != TypeSelector.DINAMICO) {
			throw new ErrorConfiguracionException("Campo " + idCampo + " no es de tipo selector");
		}

		// Actualizamos datos de la pagina actual con los nuevos datos
		if (valoresPagina != null) {
			paginaActual.actualizarValoresPagina(valoresPagina);
		}

		// Recuperamos valores posibles
		final RPaginaFormulario pagDef = datosSesion.obtenerDefinicionPaginaActual( elemento);
		final RComponenteSelector campoDef = (RComponenteSelector) UtilsFormularioInterno
				.devuelveComponentePagina(pagDef, idCampo);
		final List<ValorIndexado> vpc = valoresPosiblesHelper.calcularValoresPosiblesCampoSelectorDinamico(datosSesion,
				campoDef, textoCampo, elemento);

		// Devuelve resultado
		final ResultadoBuscadorDinamico res = new ResultadoBuscadorDinamico();
		res.setValores(vpc);
		return res;

	}

	@Override
	public void revisarCamposAutorrellenablesElemento(final DatosSesionFormularioInterno datosSesion) {

		// Obtiene definición página formulario detalle
		final RPaginaFormulario paginaDetalleDef = datosSesion.obtenerDefinicionPaginaActual(
				true);

		// Obtiene pagina formulario detalle
		final PaginaData paginaDetalle = datosSesion.getDatosFormulario().obtenerPaginaDataActual(true);

		// Lista campos pagina detalle
		final List<RComponente> campos = UtilsFormularioInterno.devuelveListaCampos(paginaDetalleDef);

		// Revisa campos autorrellenable con dependencias del formulario principal (se
		// calcularan los marcados como solo lectura y los que no tengan valor).

		for (final RComponente wc : campos) {

			// Obtiene valor actual campo
			final ValorCampo valorCampo = paginaDetalle.getValorCampo(wc.getIdentificador());

			// Verifica si existe dependencia externa
			// - Debe existir script autorrellenable
			// - Debe estar marcado como solo lectura o estar vacio
			boolean dependenciaExterna = false;
			final RPropiedadesCampo pc = UtilsFormularioInterno.obtenerPropiedadesCampo(wc);
			if (UtilsSTG.existeScript(pc.getScriptAutorrellenable())
					&& (pc.isNoModificable() || valorCampo == null || valorCampo.esVacio())) {
				// Verificamos si tiene dependencias campos que no pertenecen al detalle
				final DependenciaCampo dc = datosSesion.getDatosFormulario().obtenerDependenciaCampo(wc.getIdentificador(),
						true);
				if (dc != null && !dc.getDependenciasAutorrellenable().isEmpty()) {
					for (final String idCampoDep : dc.getDependenciasAutorrellenable()) {
						dependenciaExterna = !UtilsFormularioInterno.existeCampo(idCampoDep, campos);
						if (dependenciaExterna) {
							break;
						}
					}
				}
			}

			// Si tiene dependencia externa, evaluamos cambio campo
			if (dependenciaExterna) {
				// Ejecutar script autorrellenable y actualiza valor campo
				ValorCampo vca = ejecutarScriptAutorrellenable(datosSesion, wc, true);
				if (vca != null) {

					// Si se ha reseteado campo, creamos valor vacio dependiendo
					// del campo
					if (vca instanceof ValorResetCampos) {
						vca = UtilsFormularioInterno.crearValorVacio(wc);
					}

					final boolean valorModificado = !vca.esValorIgual(valorCampo);
					paginaDetalle.actualizarValorCampo(vca);
					// Evalua cambios a realizar tras modificar campo
					if (valorModificado) {
						// Calcula cambios (actualiza valores modificados pagina)
						final ResultadoEvaluarCambioCampo res = new ResultadoEvaluarCambioCampo();
						calcularDatosPaginaAutorrellenable(datosSesion, wc.getIdentificador(), true, res);
					}
				}
			}

		}

	}



	// --------------------------------------------------------------------------------
	// Funciones auxiliares
	// --------------------------------------------------------------------------------

	/**
	 * Ejecuta script de validacion de un campo.
	 *
	 * @param datosSesion
	 *                        Datos sesion formulario
	 * @param idCampo
	 *                        Id campo modificado
	 * @param elemento
	 *                        Indica si es elemento
	 * @param res
	 *                        Resumen de los datos y configuracion modificada
	 */
	private void calcularDatosPaginaValidacionCampo(final DatosSesionFormularioInterno datosSesion,
			final String idCampo, final boolean elemento, final ResultadoEvaluarCambioCampo res) {

		// Definicion campo
		final RPaginaFormulario pagDef = datosSesion.obtenerDefinicionPaginaActual( elemento);
		final RComponente campoDef = UtilsFormularioInterno.devuelveComponentePagina(pagDef, idCampo);
		final RPropiedadesCampo propsDef = UtilsFormularioInterno.obtenerPropiedadesCampo(campoDef);

		// Evaluamos script de validacion
		if (UtilsSTG.existeScript(propsDef.getScriptValidacion())) {
			// Ejecutamos script
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(propsDef.getScriptValidacion().getLiterales());
			final VariablesFormulario variablesFormulario = datosSesion.generarVariablesFormulario(campoDef.getIdentificador(), elemento);
			final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
					TypeScriptFormulario.SCRIPT_VALIDACION_CAMPO, campoDef.getIdentificador(),
					propsDef.getScriptValidacion().getScript(), variablesFormulario, codigosError,
					datosSesion.getDefinicionTramite());
			res.setValidacion(rs.getMensajeValidacion());
		}

	}

	/**
	 * Ejecuta scripts de autorrellenable y actualiza datos pagina.
	 *
	 * Solo se ejecuta tras modificar un campo que tiene dependencias.
	 *
	 * @param datosSesion
	 *                        Datos sesion formulario
	 * @param idCampo
	 *                        Id campo modificado
	 * @param elemento
	 *                        Indica si es elemento
	 * @param res
	 *                        Resumen de los datos y configuracion modificada
	 */
	private void calcularDatosPaginaAutorrellenable(final DatosSesionFormularioInterno datosSesion,
			final String idCampo, final boolean elemento, final ResultadoEvaluarCambioCampo res) {

		// Definicion página
		final RPaginaFormulario pagDef = datosSesion.obtenerDefinicionPaginaActual( elemento);

		// Página actual
		final PaginaData paginaActual = datosSesion.getDatosFormulario().obtenerPaginaDataActual(elemento);

		// Creamos lista de campos modificados (inicialmente el campo
		// modificado)
		final List<String> modificados = new ArrayList<>();
		modificados.add(idCampo);

		// Evaluamos script de autorrellenable
		boolean evaluarCampo;
		boolean campoPosterior;
		campoPosterior = false;
		for (final RComponente campoDefAuto : UtilsFormularioInterno.devuelveListaCampos(pagDef)) {
			// Propiedades campo
			final RPropiedadesCampo propsAutoDef = UtilsFormularioInterno.obtenerPropiedadesCampo(campoDefAuto);
			// Comprobamos si hay que evaluar campo
			evaluarCampo = false;
			// - Si el calculo es referente al cambio de un campo solo tendra
			// sentido para campos posteriores
			if (!campoPosterior && idCampo.equals(campoDefAuto.getIdentificador())) {
				campoPosterior = true;
			} else if (campoPosterior) {
				evaluarCampo = true;
			}
			if (!evaluarCampo) {
				continue;
			}
			// Obtenemos dependencias campo
			final DependenciaCampo dependenciasCampo = datosSesion.getDatosFormulario()
					.obtenerDependenciaCampo(campoDefAuto.getIdentificador(), elemento);
			// Si hay que evaluar campo se ejecutara script auto si:
			// - tiene script auto, esta vacio y es la carga de la pagina
			// - tiene script auto y tiene dependencias con campos modificados
			boolean dependenciaAutorrellenable = false;

			if (UtilsSTG.existeScript(propsAutoDef.getScriptAutorrellenable())) {
				if (paginaActual.getValorCampo(campoDefAuto.getIdentificador()).esVacio()) {
					dependenciaAutorrellenable = true;
				} else if (UtilsFormularioInterno.existeDependencia(modificados,
						dependenciasCampo.getDependenciasAutorrellenable())) {
					dependenciaAutorrellenable = true;
				}
			}

			// En caso de ser un selector y tener dependencias con campos
			// modificados se reseteara el valor del selector
			final boolean dependenciaSelector = UtilsFormularioInterno.existeDependencia(modificados,
					dependenciasCampo.getDependenciasSelector());

			if (dependenciaAutorrellenable || dependenciaSelector) {

				// TODO (FASE 2) PARA CUANDO HAYA VARIAS PAGINAS HABRA QUE VER
				// CUANDO SE ACTUALIZA UN CAMPO NO READONLY QUE DEPENDA DE
				// CAMPOS DE ANTERIORES PANTALLAS. CAMINO MAS DIRECTO SERIA QUE
				// DESDE ESTE SCRIPT SE ACTUALIZASEN TODOS LOS CAMPOS DEL
				// FORMULARIO, SI NO ES MAS COMPLEJO. PARA CAMPOS DE OTRA PAGINA
				// SOLO SE CALCULARIA VALOR, NO HACE FALTA RECUPERAR VALORES
				// POSIBLES.

				ValorCampo vc = ValorResetCampos.createNewValorResetCampos();
				// Si dependencia autorrellenable ejecutamos script
				if (dependenciaAutorrellenable) {
					vc = ejecutarScriptAutorrellenable(datosSesion, campoDefAuto, elemento);
				}

				// En caso de que se haya reseteado el valor desde el script o
				// si no tiene dependencia autorrellenable pero es un selector,
				// reseteamos valor campo
				if (vc instanceof ValorResetCampos) {
					vc = calcularValorVacio(campoDefAuto);
				}

				// Establecemos valor en formulario
				paginaActual.actualizarValorCampo(vc);

				// Marcamos como modificado
				res.getValores().add(vc);
				modificados.add(campoDefAuto.getIdentificador());
			}
		}
	}

	/**
	 * Calcular valor vacío.
	 *
	 * @param campoDefAuto
	 *                   Campo
	 */
	private ValorCampo calcularValorVacio(final RComponente campoDefAuto) {

		final ValorCampo vc = UtilsFormularioInterno.crearValorVacio(campoDefAuto);

		// Ajustes selector único: si esta vacío se debería establecer la primera opción
		// pero no tenemos acceso a los valores posibles en este punto, con lo que lo
		// dejamos nulo

		return vc;
	}

	/**
	 * Ejecuta script autorrellenable.
	 *
	 * @param datosSesion
	 *                        Datos sesion
	 * @param campoDef
	 *                        Definicion campo
	 * @param elemento
	 *                        Indica si es elemento
	 * @return Valor campo
	 */
	private ValorCampo ejecutarScriptAutorrellenable(final DatosSesionFormularioInterno datosSesion,
			final RComponente campoDef, final boolean elemento) {
		ValorCampo vcAuto = null;
		final RPropiedadesCampo propsCampoDef = UtilsFormularioInterno.obtenerPropiedadesCampo(campoDef);
		final Map<String, String> codigosError = UtilsSTG
				.convertLiteralesToMap(propsCampoDef.getScriptAutorrellenable().getLiterales());
		final VariablesFormulario variablesFormulario = datosSesion.generarVariablesFormulario(campoDef.getIdentificador(), elemento);
		final RespuestaScript rs = scriptFormulario.executeScriptFormulario(TypeScriptFormulario.SCRIPT_AUTORELLENABLE,
				campoDef.getIdentificador(), propsCampoDef.getScriptAutorrellenable().getScript(), variablesFormulario,
				codigosError, datosSesion.getDefinicionTramite());
		final ResValorCampo rsv = (ResValorCampo) rs.getResultado();
		if (rsv.getValorCampo() != null) {
			vcAuto = rsv.getValorCampo();
			vcAuto.setId(campoDef.getIdentificador());
			if (!UtilsFormulario.comprobarCaracteresPermitidos(vcAuto)) {
				throw new ValorCampoFormularioCaracteresNoPermitidosException(campoDef.getIdentificador(),
						vcAuto.print());
			}
		}
		if (vcAuto == null) {
			throw new ErrorConfiguracionException(
					"No existeix valor calculat per campo " + campoDef.getIdentificador());
		}
		return vcAuto;
	}

	/**
	 * Ejecuta scripts de solo lectura y actualiza datos pagina.
	 *
	 * @param datosSesion
	 *                        Datos sesion formulario
	 * @param idCampo
	 *                        Id campo modificado
	 * @param elemento
	 *                        Indica si es elemento
	 * @param res
	 *                        Resumen de los datos y configuracion modificada
	 */
	private void calcularDatosPaginaEstado(final DatosSesionFormularioInterno datosSesion, final String idCampo,
			final boolean elemento, final ResultadoEvaluarCambioCampo res) {

		// Definicion pagina actual
		final RPaginaFormulario paginaDef = datosSesion.obtenerDefinicionPaginaActual( elemento);

		// Página actual
		final PaginaData paginaActual = datosSesion.getDatosFormulario().obtenerPaginaDataActual(elemento);

		// Creamos lista de campos modificados (el campo modificado más los que
		// aparecen en resultado evaluar campo)
		final List<String> camposModificados = new ArrayList<String>();
		camposModificados.add(idCampo);

		for (final ValorCampo vcm : res.getValores()) {
			camposModificados.add(vcm.getId());
		}

		// Revisa script de cambio de estado
		final List<String> estadosModificado = revisarScriptEstadoCampos(datosSesion, idCampo, camposModificados,
				paginaDef, elemento);

		// Evaluamos que configuracion de campos debemos pasar:
		// - estado modificado
		for (final RComponente campoDefAuto : UtilsFormularioInterno.devuelveListaCampos(paginaDef)) {
			if (estadosModificado.contains(campoDefAuto.getIdentificador())) {
				final ConfiguracionCampo confCampo = paginaActual
						.getConfiguracionCampo(campoDefAuto.getIdentificador());
				final ConfiguracionModificadaCampo cm = ConfiguracionModificadaCampo
						.createNewConfiguracionModificadaCampo();
				cm.setId(campoDefAuto.getIdentificador());
				cm.setSoloLectura(confCampo.getSoloLectura());
				res.getConfiguracion().add(cm);
			}
		}
	}

	/**
	 * Revisa campos a modificar.
	 *
	 * @param datosSesion
	 *                              Datos sesion
	 * @param idCampo
	 *                              id campo modificado
	 * @param camposModificados
	 *                              Campos modificados
	 * @param paginaDef
	 *                              Definicion paginas
	 * @return Lista de campos modificados
	 */
	private List<String> revisarScriptEstadoCampos(final DatosSesionFormularioInterno datosSesion, final String idCampo,
			final List<String> camposModificados, final RPaginaFormulario paginaDef, final boolean elemento) {

		// Creamos lista de campos para los que se modifica o hay que refrescar
		// su estado
		final List<String> estadosModificado = new ArrayList<>();

		// Pagina actual
		final PaginaData paginaActual = datosSesion.getDatosFormulario().obtenerPaginaDataActual(elemento);

		// Evaluamos script de dependencia
		boolean evaluarCampo;
		boolean campoPosterior;
		campoPosterior = false;
		for (final RComponente campoDefAuto : UtilsFormularioInterno.devuelveListaCampos(paginaDef)) {
			// Comprobamos si hay que evaluar campo
			evaluarCampo = false;
			// Si el calculo es referente al cambio de un campo solo tendra
			// sentido para campos posteriores
			if (!campoPosterior && idCampo.equals(campoDefAuto.getIdentificador())) {
				campoPosterior = true;
			} else if (campoPosterior) {
				evaluarCampo = true;
			}
			// Si hay que evaluar campo comprobamos si:
			// - No debe estar marcado como readonly (no tiene efecto script)
			// - tiene script estado
			// - tiene dependencias con algun campo modificado
			final DependenciaCampo dependenciasCampo = datosSesion.getDatosFormulario()
					.obtenerDependenciaCampo(campoDefAuto.getIdentificador(), elemento);
			if (evaluarCampo && (UtilsFormularioInterno.existeDependencia(camposModificados,
					dependenciasCampo.getDependenciasEstado()))) {
				// Evaluamos script estado
				final ResEstadoCampo rse = configuracionFormularioHelper.evaluarEstadoCampo(datosSesion, campoDefAuto,
						elemento);
				// Actualizamos configuracion campo
				if (rse != null) {
					boolean modifConf = false;
					final ConfiguracionCampo confCampo = paginaActual
							.getConfiguracionCampo(campoDefAuto.getIdentificador());
					if (rse.getEstadoCampo().getSoloLectura() != null) {
						modifConf = true;
						confCampo.setSoloLectura(rse.getEstadoCampo().getSoloLectura());
					}
					// Añadimos a lista de campos con estado modificado
					if (modifConf && !estadosModificado.contains(campoDefAuto.getIdentificador())) {
						estadosModificado.add(campoDefAuto.getIdentificador());
					}
				}
			}
		}
		return estadosModificado;
	}

	/**
	 * Ejecuta scripts de valores posibles para campos selectores modificados.
	 *
	 * @param datosSesion
	 *                        Datos sesion formulario
	 * @param elemento
	 *                        Indica si es elemento
	 * @param idCampo
	 *                        Id campo modificado
	 * @param res
	 *                        Resumen de los datos y configuracion modificada
	 */
	private void calcularDatosPaginaValoresPosibles(final DatosSesionFormularioInterno datosSesion,
			final boolean elemento, final String idCampo, final ResultadoEvaluarCambioCampo res) {
		// Definicion pagina actual
		final RPaginaFormulario paginaDef = datosSesion.obtenerDefinicionPaginaActual( elemento);

		// Creamos lista de campos modificados (el campo modificado más los que aparecen
		// en resumen)
		final List<String> modificados = new ArrayList<>();
		modificados.add(idCampo);
		for (final ValorCampo vcm : res.getValores()) {
			modificados.add(vcm.getId());
		}

		// Evaluamos valores posibles para los selectores que dependan de los campos
		// modificados
		boolean evaluarCampo;
		boolean campoPosterior;
		campoPosterior = false;
		for (final RComponente campoDefSel : UtilsFormularioInterno.devuelveListaCampos(paginaDef)) {
			// Comprobamos si hay que evaluar campo
			evaluarCampo = false;
			// - Solo tendra sentido para campos posteriores
			if (!campoPosterior && idCampo.equals(campoDefSel.getIdentificador())) {
				campoPosterior = true;
			} else if (campoPosterior && UtilsSTG.traduceTipoCampo(campoDefSel.getTipo()) == TypeCampo.SELECTOR
					&& UtilsSTG.traduceTipoSelector(
							((RComponenteSelector) campoDefSel).getTipoSelector()) != TypeSelector.DINAMICO) {
				evaluarCampo = true;
			}

			// Si hay que evaluar campo comprobamos si es selector basado en dominio y tiene
			// dependencias con algun campo modificado
			final DependenciaCampo dependenciasCampo = datosSesion.getDatosFormulario()
					.obtenerDependenciaCampo(campoDefSel.getIdentificador(), elemento);
			if (evaluarCampo && UtilsFormularioInterno.existeDependencia(modificados,
					dependenciasCampo.getDependenciasSelector())) {
				// Calculamos lista de valores posibles
				final ValoresPosiblesCampo vpc = valoresPosiblesHelper.calcularValoresPosiblesCampoSelector(datosSesion,
						(RComponenteSelector) campoDefSel, elemento);

				// Marcamos que se han modificado los valores posibles para el campo
				res.getValoresPosibles().add(vpc);
			}
		}
	}

}
