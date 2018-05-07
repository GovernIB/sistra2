package es.caib.sistramit.core.service.component;

import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;

public interface FlujoTramitacionComponent {

	String iniciarTramite(String idTramite, int version, String idioma, String idProcedimiento, String urlInicio,
			Map<String, String> parametrosInicio);

	DetalleTramite obtenerDetalleTramite();

}