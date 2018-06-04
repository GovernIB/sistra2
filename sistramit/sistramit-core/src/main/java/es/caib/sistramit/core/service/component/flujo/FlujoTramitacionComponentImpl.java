package es.caib.sistramit.core.service.component.flujo;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;

@Component("flujoTramitacionComponent")
@Scope(value = "prototype")
public class FlujoTramitacionComponentImpl implements FlujoTramitacionComponent {

	/** Id sesion tramitacion. */
	private String idSesionTramitacion;

	/**
	 * Indica si el flujo es invalido. Se marcará como inválido al generarse una
	 * excepción de tipo FATAL.
	 */
	private boolean flujoInvalido;

	@Override
	public String iniciarTramite(final String idTramite, final int version, final String idioma,
			final String idTramiteCatalogo, final String urlInicio, final Map<String, String> parametrosInicio) {

		// TODO PENDIENTE

		idSesionTramitacion = GeneradorId.generarId();
		return idSesionTramitacion;
	}

	@Override
	public DetalleTramite obtenerDetalleTramite() {
		// TODO PENDIENTE
		final DetalleTramite dt = new DetalleTramite();
		dt.setIdTramite("TRAM1");
		dt.setTitulo("Tramite 1");
		return dt;
	}

	@Override
	public void invalidarFlujoTramicacion() {
		flujoInvalido = true;
	}

	@Override
	public ResultadoIrAPaso cargarTramite(String idSesionTramitacion) {
		// TODO PENDIENTE
		return null;
	}

	@Override
	public ResultadoIrAPaso recargarTramite() {
		// TODO PENDIENTE
		return null;
	}

	@Override
	public ResultadoIrAPaso irAPaso(String idPaso) {
		// TODO PENDIENTE
		return null;
	}

	@Override
	public ResultadoAccionPaso accionPaso(String idPaso, TypeAccionPaso accionPaso, ParametrosAccionPaso parametros) {
		// TODO PENDIENTE
		return null;
	}

	@Override
	public void cancelarTramite() {
		// TODO PENDIENTE
	}

}
