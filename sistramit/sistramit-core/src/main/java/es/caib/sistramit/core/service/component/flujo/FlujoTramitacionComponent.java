package es.caib.sistramit.core.service.component.flujo;

import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;

public interface FlujoTramitacionComponent {

	String iniciarTramite(String idTramite, int version, String idioma, String idTramiteCatalogo, String urlInicio,
			Map<String, String> parametrosInicio);

	DetalleTramite obtenerDetalleTramite();

}