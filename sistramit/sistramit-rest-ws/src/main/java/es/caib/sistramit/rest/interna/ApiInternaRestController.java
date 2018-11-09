package es.caib.sistramit.rest.interna;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistramit.core.api.exception.ErrorJsonException;
import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.api.service.RestApiInternaService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroPaginacion;
import es.caib.sistramit.rest.api.interna.RInvalidacion;
import es.caib.sistramit.rest.api.util.JsonException;
import es.caib.sistramit.rest.api.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Operaciones requeridas desde el resto de módulos de Sistra2. No requieren
 * versionado.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/interna")
@Api(value = "interna", produces = "application/json")
public class ApiInternaRestController {

	/** Service system. */
	@Autowired
	private SystemService systemService;

	@Autowired
	private RestApiInternaService restApiInternaService;

	/**
	 * Invalidación.
	 *
	 */
	@ApiOperation(value = "Invalidación caché", notes = "Invalidación caché")
	@RequestMapping(value = "/invalidacion", method = RequestMethod.POST)
	public boolean invalidacion(
			@ApiParam("{\"tipo\":\"tipo\",\"identificador\":\"id\"}") @RequestParam(name = "invalidacion") final String invalidacionJSON) {

		// Parseamos parametro enviado por POST
		RInvalidacion pars = null;
		if (StringUtils.isNotBlank(invalidacionJSON)) {
			try {
				pars = (RInvalidacion) JsonUtil.fromJson(invalidacionJSON, RInvalidacion.class);
			} catch (final JsonException e) {
				throw new RuntimeException(e);
			}
		}

		// Añade invalidación
		final Invalidacion invalidacion = new Invalidacion();
		invalidacion.setTipo(TypeInvalidacion.fromString(pars.getTipo()));
		invalidacion.setIdentificador(pars.getIdentificador());
		systemService.invalidar(invalidacion);

		return true;

	}

	@ApiOperation(value = "Auditoría de eventos", notes = "Auditoría de eventos", response = REventoAuditoria.class, responseContainer = "List")
	@RequestMapping(value = "/auditoria/evento", method = RequestMethod.GET)
	public List<REventoAuditoria> obtenerAuditoriaEvento(
			@RequestParam(name = "filtroPaginacion") final String pFiltroPaginacion,
			@RequestParam(name = "filtroBusqueda") final String pFiltroBusqueda) {
		List<REventoAuditoria> resListaEventos = null;

		final FiltroPaginacion filtroPaginacion = convierteFiltroPaginacion(pFiltroPaginacion);
		final FiltroAuditoriaTramitacion filtroBusqueda = convierteFiltroAuditoriaBusqueda(pFiltroBusqueda);

		final List<EventoAuditoriaTramitacion> listaEventos = restApiInternaService
				.recuperarLogSesionTramitacionArea(filtroBusqueda, filtroPaginacion);

		if (listaEventos != null && !listaEventos.isEmpty()) {
			resListaEventos = new ArrayList<>();

			for (final EventoAuditoriaTramitacion eventoAuditoria : listaEventos) {
				final REventoAuditoria nuevo = new REventoAuditoria();
				nuevo.setId(eventoAuditoria.getId());
				nuevo.setIdSesionTramitacion(eventoAuditoria.getIdSesionTramitacion());
				nuevo.setTipoEvento(eventoAuditoria.getTipoEvento().toString());
				nuevo.setFecha(eventoAuditoria.getFecha());
				nuevo.setNif(eventoAuditoria.getNif());
				nuevo.setIdTramite(eventoAuditoria.getIdTramite());
				nuevo.setVersionTramite(eventoAuditoria.getVersionTramite());
				nuevo.setIdProcedimientoCP(eventoAuditoria.getIdProcedimientoCP());
				nuevo.setIdProcedimientoSIA(eventoAuditoria.getIdProcedimientoSIA());
				nuevo.setCodigoError(eventoAuditoria.getCodigoError());
				nuevo.setDescripcion(eventoAuditoria.getDescripcion());
				nuevo.setResultado(eventoAuditoria.getResultado());
				nuevo.setTrazaError(eventoAuditoria.getTrazaError());

				if (eventoAuditoria.getPropiedadesEvento() != null) {
					try {
						nuevo.setDetalle(JSONUtil.toJSON(eventoAuditoria.getPropiedadesEvento()));
					} catch (final JSONUtilException e) {
						throw new ErrorJsonException(e);
					}
				}
				resListaEventos.add(nuevo);
			}

		}

		return resListaEventos;
	}

	@ApiOperation(value = "Auditoría de eventos número total", notes = "Auditoría de eventos número total", response = Long.class)
	@RequestMapping(value = "/auditoria/eventoContar", method = RequestMethod.GET)
	public Long obtenerAuditoriaEventoCount(@RequestParam(name = "filtroBusqueda") final String pFiltroBusqueda) {

		final FiltroAuditoriaTramitacion filtro = convierteFiltroAuditoriaBusqueda(pFiltroBusqueda);

		return restApiInternaService.recuperarLogSesionTramitacionAreaCount(filtro);
	}

	/**
	 * Convierte los filtros de busqueda de auditoria de String al tipo
	 * correspondiente.
	 *
	 * @param pFiltro
	 *            filtros de busqueda
	 * @return filtro auditoria tramitacion
	 */
	private FiltroAuditoriaTramitacion convierteFiltroAuditoriaBusqueda(final String pFiltro) {
		RFiltroAuditoria rFiltro = null;
		try {
			rFiltro = (RFiltroAuditoria) JsonUtil.fromJson(pFiltro, RFiltroAuditoria.class);
		} catch (final JsonException e) {
			throw new ErrorJsonException(e);
		}

		FiltroAuditoriaTramitacion filtro = null;
		if (rFiltro != null) {
			filtro = new FiltroAuditoriaTramitacion();
			if (rFiltro.getEvento() != null) {
				filtro.setEvento(TypeEvento.valueOf(rFiltro.getEvento()));
			}
			filtro.setFechaDesde(rFiltro.getFechaDesde());
			filtro.setFechaHasta(rFiltro.getFechaHasta());
			filtro.setIdSesionTramitacion(rFiltro.getIdSesionTramitacion());
			filtro.setListaAreas(rFiltro.getListaAreas());
			filtro.setNif(rFiltro.getNif());
			filtro.setIdTramite(rFiltro.getIdTramite());
			filtro.setVersionTramite(rFiltro.getVersionTramite());
			filtro.setIdProcedimientoCP(rFiltro.getIdProcedimientoCP());
			filtro.setIdProcedimientoSIA(rFiltro.getIdProcedimientoSIA());

		}

		return filtro;

	}

	/**
	 * Convierte los filtros de paginacion de String al tipo correspondiente.
	 *
	 * @param pFiltro
	 *            filtro
	 * @return filtro de paginacion
	 */
	private FiltroPaginacion convierteFiltroPaginacion(final String pFiltro) {
		RFiltroPaginacion rFiltro = null;
		try {
			rFiltro = (RFiltroPaginacion) JsonUtil.fromJson(pFiltro, RFiltroPaginacion.class);
		} catch (final JsonException e) {
			throw new ErrorJsonException(e);
		}

		FiltroPaginacion filtro = null;
		if (rFiltro != null) {
			filtro = new FiltroPaginacion();

			filtro.setFirst(rFiltro.getFirst());
			filtro.setPageSize(rFiltro.getPageSize());
		}

		return filtro;

	}

}
