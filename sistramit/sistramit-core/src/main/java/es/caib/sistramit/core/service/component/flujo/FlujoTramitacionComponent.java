package es.caib.sistramit.core.service.component.flujo;

import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;

public interface FlujoTramitacionComponent {

	String iniciarTramite(String idTramite, int version, String idioma, String idTramiteCatalogo, String urlInicio,
			Map<String, String> parametrosInicio);

	ResultadoIrAPaso cargarTramite(String idSesionTramitacion);

	DetalleTramite obtenerDetalleTramite();

	void invalidarFlujoTramicacion();

	ResultadoIrAPaso recargarTramite();

	ResultadoIrAPaso irAPaso(String idPaso);

	ResultadoAccionPaso accionPaso(String idPaso, TypeAccionPaso accionPaso, ParametrosAccionPaso parametros);

	void cancelarTramite();

}