package es.caib.sistrahelp.core.service.component;

import java.util.ArrayList;
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

import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistrahelp.core.api.exception.ErrorJsonException;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.comun.ListaPropiedades;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
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
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		List<EventoAuditoriaTramitacion> resultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		String filtroBusqueda = null;
		if (pFiltroBusqueda != null) {
			try {
				filtroBusqueda = JsonUtil.toJson(pFiltroBusqueda);
			} catch (final JsonException e) {
				throw new ErrorJsonException(e);
			}
		}

		String filtroPaginacion = null;
		if (pFiltroPaginacion != null) {
			try {
				filtroPaginacion = JsonUtil.toJson(pFiltroPaginacion);
			} catch (final JsonException e) {
				throw new ErrorJsonException(e);
			}
		}

		final Map<String, String> map = new HashMap<>();
		map.put("filtroPaginacion", filtroPaginacion);
		map.put("filtroBusqueda", filtroBusqueda);

		final REventoAuditoria[] listaEventos = restTemplate.getForObject(
				getUrl() + "/auditoria/evento?filtroPaginacion={filtroPaginacion}&filtroBusqueda={filtroBusqueda}",
				REventoAuditoria[].class, map);

		if (listaEventos != null) {
			resultado = new ArrayList<>();
			for (final REventoAuditoria rEventoAuditoria : listaEventos) {
				final EventoAuditoriaTramitacion evento = new EventoAuditoriaTramitacion();
				evento.setId(rEventoAuditoria.getId());

				evento.setIdSesionTramitacion(rEventoAuditoria.getIdSesionTramitacion());
				evento.setTipoEvento(TypeEvento.fromString(rEventoAuditoria.getTipoEvento()));
				evento.setFecha(rEventoAuditoria.getFecha());
				evento.setNif(rEventoAuditoria.getNif());
				evento.setIdTramite(rEventoAuditoria.getIdTramite());
				evento.setVersionTramite(rEventoAuditoria.getVersionTramite());
				evento.setIdProcedimientoCP(rEventoAuditoria.getIdProcedimientoCP());
				evento.setIdProcedimientoSIA(rEventoAuditoria.getIdProcedimientoSIA());
				evento.setCodigoError(rEventoAuditoria.getCodigoError());
				evento.setDescripcion(rEventoAuditoria.getDescripcion());
				evento.setResultado(rEventoAuditoria.getResultado());
				evento.setTrazaError(rEventoAuditoria.getTrazaError());

				if (rEventoAuditoria.getDetalle() != null) {
					try {
						evento.setPropiedadesEvento((ListaPropiedades) JSONUtil.fromJSON(rEventoAuditoria.getDetalle(),
								ListaPropiedades.class));
					} catch (final JSONUtilException e) {
						throw new ErrorJsonException(e);
					}
				}
				resultado.add(evento);
			}

		}

		return resultado;
	}

	@Override
	public Long obtenerAuditoriaEventoCount(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		String filtroBusqueda = null;
		if (pFiltroBusqueda != null) {
			try {
				filtroBusqueda = JsonUtil.toJson(pFiltroBusqueda);
			} catch (final JsonException e) {
				throw new ErrorJsonException(e);
			}
		}

		final Map<String, String> map = new HashMap<>();
		map.put("filtroBusqueda", filtroBusqueda);

		return restTemplate.getForObject(getUrl() + "/auditoria/eventoContar?filtroBusqueda={filtroBusqueda}",
				Long.class, map);
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
