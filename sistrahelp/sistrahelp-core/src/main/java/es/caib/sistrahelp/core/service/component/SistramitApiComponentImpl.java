package es.caib.sistrahelp.core.service.component;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistrahelp.core.api.exception.ErrorJsonException;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.PerdidaClave;
import es.caib.sistrahelp.core.api.model.ResultadoEventoAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;
import es.caib.sistrahelp.core.api.model.comun.ListaPropiedades;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroEventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroPaginacion;
import es.caib.sistramit.rest.api.interna.RFiltroPerdidaClave;
import es.caib.sistramit.rest.api.interna.RINEventoAuditoria;
import es.caib.sistramit.rest.api.interna.ROUTEventoAuditoria;
import es.caib.sistramit.rest.api.interna.ROUTPerdidaClave;
import es.caib.sistramit.rest.api.interna.RPerdidaClave;

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
	public ResultadoEventoAuditoria obtenerAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {

		ResultadoEventoAuditoria resultado = null;
		List<REventoAuditoria> listaREventos = null;

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroAuditoriaBusqueda(pFiltroBusqueda));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);

		final ResponseEntity<ROUTEventoAuditoria> response = restTemplate.postForEntity(getUrl() + "/auditoria/evento",
				request, ROUTEventoAuditoria.class);

		switch (response.getStatusCodeValue()) {
		case 200:
			resultado = new ResultadoEventoAuditoria();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(response.getBody().getNumElementos());
			} else {
				listaREventos = response.getBody().getListaEventos();

				if (listaREventos != null) {
					resultado.setListaEventos(new ArrayList<>());
					for (final REventoAuditoria rEventoAuditoria : listaREventos) {
						resultado.getListaEventos().add(convierteEventoAuditoria(rEventoAuditoria));
					}

				}
			}
			break;
		}

		return resultado;
	}

	@Override
	public ResultadoPerdidaClave obtenerAuditoriaTramite(final FiltroPerdidaClave pFiltroBusqueda) {
		ResultadoPerdidaClave resultado = null;
		ROUTPerdidaClave rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final HttpEntity<RFiltroPerdidaClave> request = new HttpEntity<>(
				convierteFiltroTramiteBusqueda(pFiltroBusqueda), headers);

		final ResponseEntity<ROUTPerdidaClave> response = restTemplate.postForEntity(getUrl() + "/auditoria/tramite",
				request, ROUTPerdidaClave.class);

		switch (response.getStatusCodeValue()) {
		case 200:
			rResultado = response.getBody();
			break;
		}

		if (rResultado != null) {
			resultado = new ResultadoPerdidaClave();
			resultado.setResultado(rResultado.getResultado());

			if (rResultado.getListaClaves() != null) {
				resultado.setListaClaves(new ArrayList<>());

				for (final RPerdidaClave rClave : rResultado.getListaClaves()) {
					resultado.getListaClaves().add(conviertePerdidaClave(rClave));
				}
			}

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

	/**
	 * Convierte filtro paginacion.
	 *
	 * @param pFiltro
	 *            filtro
	 * @return RFiltroPaginacion
	 */
	private RFiltroPaginacion convierteFiltroPaginacion(final FiltroPaginacion pFiltro) {
		RFiltroPaginacion rFiltro = null;

		if (pFiltro != null) {
			rFiltro = new RFiltroPaginacion();

			rFiltro.setFirst(pFiltro.getFirst());
			rFiltro.setPageSize(pFiltro.getPageSize());
		}

		return rFiltro;

	}

	/**
	 * Convierte filtro auditoria busqueda.
	 *
	 * @param pFiltro
	 *            filtro
	 * @return RFiltroEventoAuditoria
	 */
	private RFiltroEventoAuditoria convierteFiltroAuditoriaBusqueda(final FiltroAuditoriaTramitacion pFiltro) {
		RFiltroEventoAuditoria rFiltro = null;

		if (pFiltro != null) {
			rFiltro = new RFiltroEventoAuditoria();
			rFiltro.setListaAreas(pFiltro.getListaAreas());
			rFiltro.setIdSesionTramitacion(pFiltro.getIdSesionTramitacion());
			rFiltro.setNif(pFiltro.getNif());
			rFiltro.setFechaDesde(pFiltro.getFechaDesde());
			rFiltro.setFechaHasta(pFiltro.getFechaHasta());

			if (pFiltro.getEvento() != null) {
				rFiltro.setEvento(pFiltro.getEvento().name());
			}

			rFiltro.setIdTramite(pFiltro.getIdTramite());
			rFiltro.setVersionTramite(pFiltro.getVersionTramite());
			rFiltro.setIdProcedimientoCP(pFiltro.getIdProcedimientoCP());
			rFiltro.setIdProcedimientoSIA(pFiltro.getIdProcedimientoSIA());
			rFiltro.setErrorPlataforma(pFiltro.isErrorPlataforma());
			rFiltro.setSoloContar(pFiltro.isSoloContar());
		}

		return rFiltro;

	}

	/**
	 * Convierte evento auditoria.
	 *
	 * @param pREventoAuditoria
	 *            evento auditoria
	 * @return EventoAuditoriaTramitacion
	 */
	private EventoAuditoriaTramitacion convierteEventoAuditoria(final REventoAuditoria pREventoAuditoria) {
		EventoAuditoriaTramitacion evento = null;

		if (pREventoAuditoria != null) {
			evento = new EventoAuditoriaTramitacion();
			evento.setId(pREventoAuditoria.getId());
			evento.setIdSesionTramitacion(pREventoAuditoria.getIdSesionTramitacion());
			evento.setTipoEvento(TypeEvento.fromString(pREventoAuditoria.getTipoEvento()));
			evento.setFecha(pREventoAuditoria.getFecha());
			evento.setNif(pREventoAuditoria.getNif());
			evento.setIdTramite(pREventoAuditoria.getIdTramite());
			evento.setVersionTramite(pREventoAuditoria.getVersionTramite());
			evento.setIdProcedimientoCP(pREventoAuditoria.getIdProcedimientoCP());
			evento.setIdProcedimientoSIA(pREventoAuditoria.getIdProcedimientoSIA());
			evento.setCodigoError(pREventoAuditoria.getCodigoError());
			evento.setDescripcion(pREventoAuditoria.getDescripcion());
			evento.setResultado(pREventoAuditoria.getResultado());
			evento.setTrazaError(pREventoAuditoria.getTrazaError());

			if (pREventoAuditoria.getDetalle() != null) {
				try {
					evento.setPropiedadesEvento((ListaPropiedades) JSONUtil.fromJSON(pREventoAuditoria.getDetalle(),
							ListaPropiedades.class));
				} catch (final JSONUtilException e) {
					throw new ErrorJsonException(e);
				}
			}
		}

		return evento;
	}

	/**
	 * Convierte filtro tramite busqueda.
	 *
	 * @param pFiltro
	 *            filtro
	 * @return RFiltroPerdidaClave
	 */
	private RFiltroPerdidaClave convierteFiltroTramiteBusqueda(final FiltroPerdidaClave pFiltro) {
		RFiltroPerdidaClave rFiltro = null;

		if (pFiltro != null) {
			rFiltro = new RFiltroPerdidaClave();
			rFiltro.setListaAreas(pFiltro.getListaAreas());
			rFiltro.setDatoFormulario(pFiltro.getDatoFormulario());
			rFiltro.setFechaDesde(pFiltro.getFechaDesde());
			rFiltro.setFechaHasta(pFiltro.getFechaHasta());
			rFiltro.setIdTramite(pFiltro.getIdTramite());
			rFiltro.setVersionTramite(pFiltro.getVersionTramite());
			rFiltro.setIdProcedimientoCP(pFiltro.getIdProcedimientoCP());
		}

		return rFiltro;

	}

	/**
	 * Convierte perdida clave.
	 *
	 * @param rClave
	 *            Clave
	 * @return PerdidaClave
	 */
	private PerdidaClave conviertePerdidaClave(final RPerdidaClave rClave) {
		final PerdidaClave clave = new PerdidaClave();

		clave.setClaveTramitacion(rClave.getClaveTramitacion());
		clave.setFecha(rClave.getFecha());
		clave.setIdTramite(rClave.getIdTramite());
		clave.setVersionTramite(rClave.getVersionTramite());
		clave.setIdProcedimientoCP(rClave.getIdProcedimientoCP());
		return clave;
	}
}
