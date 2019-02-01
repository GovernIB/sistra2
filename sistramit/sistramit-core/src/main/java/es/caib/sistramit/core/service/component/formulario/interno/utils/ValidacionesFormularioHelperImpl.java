package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.MensajeAviso;
import es.caib.sistramit.core.service.model.formulario.interno.ResultadoValidacion;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Component("validacionesFormularioHelper")
public final class ValidacionesFormularioHelperImpl implements ValidacionesFormularioHelper {

	/** Motor de ejecución de scritps. */
	@Autowired
	private ScriptExec scriptFormulario;

	@Override
	public ResultadoValidacion validarConfiguracionCampos(DatosSesionFormularioInterno datosSesion) {
		// TODO PENDIENTE IMPLEMENTAR
		throw new RuntimeException("Pendiente implementar");
	}

	@Override
	public ResultadoValidacion validarScriptValidacionPagina(DatosSesionFormularioInterno datosSesion) {
		final ResultadoValidacion rv = new ResultadoValidacion();

		// - Definicion pagina actual
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);

		// Ejecutamos script validacion pagina
		if (UtilsSTG.existeScript(paginaDef.getScriptValidacion())) {
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(paginaDef.getScriptValidacion().getLiterales());
			final VariablesFormulario variablesFormulario = UtilsFormularioInterno
					.generarVariablesFormulario(datosSesion, null);

			// Id pagina
			// TODO No existe un id pagina, será necesario para navegación entre páginas
			final String idPagina = "P-" + datosSesion.getDatosFormulario().getPaginaActualFormulario().getIndiceDef();

			final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
					TypeScriptFormulario.SCRIPT_VALIDACION_PAGINA, idPagina,
					paginaDef.getScriptValidacion().getScript(), variablesFormulario, codigosError,
					datosSesion.getDefinicionTramite());
			if (rs.isError()) {
				rv.setError(true);
				rv.setMensajeError(rs.getMensajeError());
				rv.setAvisos(null);
			} else if (rs.isAviso()) {
				rv.getAvisos().add(new MensajeAviso(rs.getTipoMensajeAviso(), rs.getMensajeAviso()));
			}
		}

		return rv;
	}

	@Override
	public ResultadoValidacion validarScriptValidacionCampos(DatosSesionFormularioInterno datosSesion) {
		final ResultadoValidacion rv = new ResultadoValidacion();

		// - Definicion pagina actual
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);

		for (final RComponente campo : UtilsFormularioInterno.devuelveListaCampos(paginaDef)) {
			final RPropiedadesCampo propsCampo = UtilsFormularioInterno.obtenerPropiedadesCampo(campo);
			if (UtilsSTG.existeScript(propsCampo.getScriptValidacion())) {
				// Ejecutamos script validacion
				final Map<String, String> codigosError = UtilsSTG
						.convertLiteralesToMap(propsCampo.getScriptValidacion().getLiterales());
				final VariablesFormulario variablesFormulario = UtilsFormularioInterno
						.generarVariablesFormulario(datosSesion, campo.getIdentificador());
				final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
						TypeScriptFormulario.SCRIPT_VALIDACION_CAMPO, campo.getIdentificador(),
						propsCampo.getScriptValidacion().getScript(), variablesFormulario, codigosError,
						datosSesion.getDefinicionTramite());
				if (rs.isError()) {
					// Paramos al primer error de validacion
					rv.setError(true);
					rv.setMensajeError(rs.getMensajeError());
					rv.setAvisos(null);
					break;
				} else if (rs.isAviso()) {
					rv.getAvisos().add(new MensajeAviso(rs.getTipoMensajeAviso(), rs.getMensajeAviso()));
				}
			}
		}
		return rv;
	}

}
