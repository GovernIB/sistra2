package es.caib.sistrahelp.core.service.component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrahelp.core.api.exception.ErrorJsonException;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltrosAuditoria;
import es.caib.sistramit.rest.api.util.JsonException;
import es.caib.sistramit.rest.api.util.JsonUtil;

/**
 * Implementación acceso SISTRAMIT
 *
 * @author Indra
 *
 */
@Component("sistramitApiComponent")
public final class SistramitApiComponentImpl implements SistramitApiComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public List<REventoAuditoria> obtenerAuditoriaEvento(final RFiltrosAuditoria pFiltros) {
		List<REventoAuditoria> resultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		String filtros = null;
		try {
			filtros = JsonUtil.toJson(pFiltros);
		} catch (final JsonException e) {
			throw new ErrorJsonException(e);
		}

		final Map<String, String> map = new HashMap<>();
		map.put("filtros", filtros);

		final REventoAuditoria[] listaEventos = restTemplate
				.getForObject(getUrl() + "/auditoria/evento?filtros={filtros}", REventoAuditoria[].class, map);

		if (listaEventos != null) {
			resultado = Arrays.asList(listaEventos);
		}

		return resultado;
	}

	private String getPassword() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_PWD);
	}

	private String getUser() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_USR);
	}

	private String getUrl() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL);
	}

}
