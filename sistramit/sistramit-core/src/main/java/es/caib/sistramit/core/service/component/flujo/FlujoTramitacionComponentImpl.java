package es.caib.sistramit.core.service.component.flujo;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;

@Component("flujoTramitacionComponent")
@Scope(value = "prototype")
public class FlujoTramitacionComponentImpl implements FlujoTramitacionComponent {

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistramit.core.service.component.FlujoTramitacionComponent#
	 * iniciarTramite(java.lang.String, int, java.lang.String, java.lang.String,
	 * java.lang.String, java.util.Map)
	 */
	@Override
	public String iniciarTramite(final String idTramite, final int version, final String idioma,
			final String idTramiteCatalogo, final String urlInicio, final Map<String, String> parametrosInicio) {

		final String idSesionTramitacion = GeneradorId.generarId();
		return idSesionTramitacion;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistramit.core.service.component.FlujoTramitacionComponent#
	 * obtenerDetalleTramite()
	 */
	@Override
	public DetalleTramite obtenerDetalleTramite() {
		// TODO Auto-generated method stub
		final DetalleTramite dt = new DetalleTramite();
		dt.setIdTramite("TRAM1");
		dt.setTitulo("Tramite 1");
		return dt;
	}

}
