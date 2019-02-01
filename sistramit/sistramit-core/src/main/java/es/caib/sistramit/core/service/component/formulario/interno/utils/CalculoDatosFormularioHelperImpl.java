package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistramit.core.api.exception.CampoFormularioNoExisteException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.ValorCampoFormularioCaracteresNoPermitidosException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorResetCampos;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResValorCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.PaginaFormularioData;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFormulario;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Component("calculoDatosFormularioHelper")
public final class CalculoDatosFormularioHelperImpl implements CalculoDatosFormularioHelper {

	/** Motor de ejecución de scritps. */
	@Autowired
	private ScriptExec scriptFormulario;

	@Override
	public ResultadoEvaluarCambioCampo calcularDatosPaginaCambioCampo(DatosSesionFormularioInterno datosSesion,
			String idCampo, List<ValorCampo> valoresPagina) {
		// Debe existir un campo que se modifique
		if (idCampo == null) {
			throw new CampoFormularioNoExisteException(datosSesion.getIdFormulario(), null);
		}

		// Creamos objeto resultado para indicar los cambios que se han
		// realizado
		final ResultadoEvaluarCambioCampo res = new ResultadoEvaluarCambioCampo();

		// Actualizamos datos de la pagina actual con los nuevos datos
		final PaginaFormularioData paginaActual = datosSesion.getDatosFormulario().getPaginaActualFormulario();
		if (valoresPagina != null) {
			paginaActual.setValoresCampo(valoresPagina);
		}

		// Ejecutamos scripts campos pantalla
		// - Script de validacion campo
		calcularDatosPaginaValidacionCampo(datosSesion, idCampo, res);

		// Ejecutamos resto de scripts solo si se pasa el script de validacion
		if (res.getValidacionCorrecta() == TypeSiNo.SI) {
			// - Script autorrellenable
			calcularDatosPaginaAutorrellenable(datosSesion, idCampo, res);
			// - Script valores posibles para selectores (excepto tipo ventana)
			calcularDatosPaginaValoresPosibles(datosSesion, idCampo, res);
			// - Script estado
			calcularDatosPaginaEstado(datosSesion, idCampo, res);
		}

		// Devolvemos cambios realizados
		return res;
	}

	@Override
	public void calcularCamposOcultosPagina(DatosSesionFormularioInterno datosSesion) {
		// TODO PENDIENTE IMPLEMENTAR
		throw new RuntimeException("Pendiente implementar");

	}

	// --------------------------------------------------------------------------------
	// Funciones auxiliares
	// --------------------------------------------------------------------------------

	/**
	 * Ejecuta script de validacion de un campo.
	 *
	 * @param datosSesion
	 *            Datos sesion formulario
	 * @param idCampo
	 *            Id campo modificado
	 * @param res
	 *            Resumen de los datos y configuracion modificada
	 */
	private void calcularDatosPaginaValidacionCampo(final DatosSesionFormularioInterno datosSesion,
			final String idCampo, final ResultadoEvaluarCambioCampo res) {

		// Definicion campo
		final RPaginaFormulario pagDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);
		final RComponente campoDef = UtilsFormularioInterno.devuelveComponentePagina(pagDef, idCampo);
		final RPropiedadesCampo propsDef = UtilsFormularioInterno.obtenerPropiedadesCampo(campoDef);

		// Evaluamos script de validacion
		if (UtilsSTG.existeScript(propsDef.getScriptValidacion())) {
			// Ejecutamos script
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(propsDef.getScriptValidacion().getLiterales());
			final VariablesFormulario variablesFormulario = UtilsFormularioInterno
					.generarVariablesFormulario(datosSesion, campoDef.getIdentificador());
			final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
					TypeScriptFormulario.SCRIPT_VALIDACION_CAMPO, campoDef.getIdentificador(),
					propsDef.getScriptValidacion().getScript(), variablesFormulario, codigosError,
					datosSesion.getDefinicionTramite());
			if (rs.isError()) {
				res.setValidacionCorrecta(TypeSiNo.NO);
				res.setMensaje(rs.getMensajeError());
			} else if (rs.isAviso()) {
				res.setAviso(TypeSiNo.SI);
				res.setMensajeAviso(rs.getMensajeAviso());
				res.setTipoMensajeAviso(rs.getTipoMensajeAviso());
			}
		}
	}

	/**
	 * Ejecuta scripts de autorrellenable y actualiza datos pagina.
	 *
	 * Solo se ejecuta tras modificar un campo que tiene dependencias.
	 *
	 * @param datosSesion
	 *            Datos sesion formulario
	 * @param idCampo
	 *            Id campo modificado
	 * @param res
	 *            Resumen de los datos y configuracion modificada
	 */
	private void calcularDatosPaginaAutorrellenable(final DatosSesionFormularioInterno datosSesion,
			final String idCampo, final ResultadoEvaluarCambioCampo res) {

		// Definicion página
		final RPaginaFormulario pagDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);

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
					.getDependenciaCampo(campoDefAuto.getIdentificador());
			// Si hay que evaluar campo se ejecutara script auto si:
			// - tiene script auto, esta vacio y es la carga de la pagina
			// - tiene script auto y tiene dependencias con campos modificados
			boolean dependenciaAutorrellenable = false;
			if (UtilsSTG.existeScript(propsAutoDef.getScriptAutorrellenable())) {
				if (datosSesion.getDatosFormulario().getPaginaActualFormulario()
						.getValorCampo(campoDefAuto.getIdentificador()).esVacio()) {
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
					vc = ejecutarScriptAutorrellenable(datosSesion, campoDefAuto);
				}

				// En caso de que se haya reseteado el valor desde el script o
				// si no tiene dependencia autorrellenable pero es un selector,
				// reseteamos valor campo
				if (vc != null) {
					// Si se ha reseteado campo, creamos valor vacio dependiendo
					// del campo
					if (vc instanceof ValorResetCampos) {
						vc = UtilsFormularioInterno.crearValorVacio(campoDefAuto);
					}

					// Establecemos valor en formulario
					datosSesion.getDatosFormulario().getPaginaActualFormulario().setValorCampo(vc);

					// Marcamos como modificado
					res.getValores().add(vc);
					modificados.add(campoDefAuto.getIdentificador());
				}
			}
		}
	}

	/**
	 * Ejecuta script autorrellenable.
	 *
	 * @param datosSesion
	 *            Datos sesion
	 * @param campoDef
	 *            Definicion campo
	 * @return Valor campo
	 */
	private ValorCampo ejecutarScriptAutorrellenable(final DatosSesionFormularioInterno datosSesion,
			final RComponente campoDef) {
		ValorCampo vcAuto = null;
		final RPropiedadesCampo propsCampoDef = UtilsFormularioInterno.obtenerPropiedadesCampo(campoDef);
		final Map<String, String> codigosError = UtilsSTG
				.convertLiteralesToMap(propsCampoDef.getScriptAutorrellenable().getLiterales());
		final VariablesFormulario variablesFormulario = UtilsFormularioInterno.generarVariablesFormulario(datosSesion,
				campoDef.getIdentificador());
		final RespuestaScript rs = scriptFormulario.executeScriptFormulario(TypeScriptFormulario.SCRIPT_AUTORELLENABLE,
				campoDef.getIdentificador(), propsCampoDef.getScriptAutorrellenable().getScript(), variablesFormulario,
				codigosError, datosSesion.getDefinicionTramite());
		if (rs.isError()) {
			throw new ErrorScriptException(TypeScriptFormulario.SCRIPT_AUTORELLENABLE.name(),
					datosSesion.getDatosInicioSesion().getIdSesionTramitacion(), campoDef.getIdentificador(),
					rs.getMensajeError());
		} else {
			final ResValorCampo rsv = (ResValorCampo) rs.getResultado();
			if (rsv.getValorCampo() != null) {
				vcAuto = rsv.getValorCampo();
				vcAuto.setId(campoDef.getIdentificador());
				if (!UtilsFormulario.comprobarCaracteresPermitidos(vcAuto)) {
					throw new ValorCampoFormularioCaracteresNoPermitidosException(campoDef.getIdentificador(),
							vcAuto.print());
				}
			}
		}
		return vcAuto;
	}

	private void calcularDatosPaginaEstado(DatosSesionFormularioInterno datosSesion, String idCampo,
			ResultadoEvaluarCambioCampo res) {
		// TODO PENDIENTE IMPLEMENTAR
		throw new RuntimeException("Pendiente implementar");

	}

	private void calcularDatosPaginaValoresPosibles(DatosSesionFormularioInterno datosSesion, String idCampo,
			ResultadoEvaluarCambioCampo res) {
		// TODO PENDIENTE IMPLEMENTAR
		throw new RuntimeException("Pendiente implementar");

	}

}
