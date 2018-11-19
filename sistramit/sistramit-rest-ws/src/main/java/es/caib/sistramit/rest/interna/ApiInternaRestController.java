package es.caib.sistramit.rest.interna;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistramit.core.api.exception.ErrorJsonException;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.PerdidaClave;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.api.service.RestApiInternaService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.rest.api.interna.RDatosSesionPago;
import es.caib.sistramit.rest.api.interna.RDetallePagoAuditoria;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFichero;
import es.caib.sistramit.rest.api.interna.RFiltroEventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroPaginacion;
import es.caib.sistramit.rest.api.interna.RFiltroPagoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroPerdidaClave;
import es.caib.sistramit.rest.api.interna.RINEventoAuditoria;
import es.caib.sistramit.rest.api.interna.RINPagoAuditoria;
import es.caib.sistramit.rest.api.interna.RInvalidacion;
import es.caib.sistramit.rest.api.interna.ROUTEventoAuditoria;
import es.caib.sistramit.rest.api.interna.ROUTPagoAuditoria;
import es.caib.sistramit.rest.api.interna.ROUTPerdidaClave;
import es.caib.sistramit.rest.api.interna.RPagoAuditoria;
import es.caib.sistramit.rest.api.interna.RPerdidaClave;
import es.caib.sistramit.rest.api.interna.RVerificacionPago;
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
				throw new ErrorJsonException(e);
			}
		}

		// Añade invalidación
		final Invalidacion invalidacion = new Invalidacion();
		if (pars != null) {
			invalidacion.setTipo(TypeInvalidacion.fromString(pars.getTipo()));
			invalidacion.setIdentificador(pars.getIdentificador());
		}
		systemService.invalidar(invalidacion);

		return true;

	}

	@ApiOperation(value = "Auditoría de eventos", notes = "Auditoría de eventos", response = ROUTEventoAuditoria.class)
	@RequestMapping(value = "/auditoria/evento", method = RequestMethod.POST)
	public ROUTEventoAuditoria obtenerAuditoriaEvento(@RequestBody final RINEventoAuditoria pFiltros) {
		final ROUTEventoAuditoria resEvento = new ROUTEventoAuditoria();

		final FiltroPaginacion filtroPaginacion = convierteFiltroPaginacion(pFiltros.getPaginacion());
		final FiltroEventoAuditoria filtroBusqueda = convierteFiltroEventoAuditoria(pFiltros.getFiltro());

		if (filtroBusqueda != null && filtroBusqueda.isSoloContar()) {
			resEvento.setNumElementos(restApiInternaService.contarLogSesionTramitacionArea(filtroBusqueda));
		} else {
			final List<EventoAuditoriaTramitacion> listaEventos = restApiInternaService
					.recuperarLogSesionTramitacionArea(filtroBusqueda, filtroPaginacion);

			if (listaEventos != null && !listaEventos.isEmpty()) {
				resEvento.setListaEventos(new ArrayList<>());

				for (final EventoAuditoriaTramitacion eventoAuditoria : listaEventos) {
					resEvento.getListaEventos().add(convierteEventoAuditoria(eventoAuditoria));
				}

			}

		}

		return resEvento;
	}

	@ApiOperation(value = "Auditoría de trámite", notes = "Auditoría de trámite", response = ROUTPerdidaClave.class)
	@RequestMapping(value = "/auditoria/tramite", method = RequestMethod.POST)
	public ROUTPerdidaClave obtenerAuditoriaTramite(@RequestBody final RFiltroPerdidaClave pRFiltroBusqueda) {
		ROUTPerdidaClave resClavePerdida = null;

		final FiltroPerdidaClave filtro = convierteFiltroPerdidaClave(pRFiltroBusqueda);

		final OUTPerdidaClave resultadoClave = restApiInternaService.recuperarClaveTramitacionArea(filtro);

		if (resultadoClave != null) {
			resClavePerdida = new ROUTPerdidaClave();

			resClavePerdida.setResultado(resultadoClave.getResultado());

			if (resultadoClave.getResultado() == 1 && resultadoClave.getListaClaves() != null
					&& !resultadoClave.getListaClaves().isEmpty()) {
				resClavePerdida.setListaClaves(new ArrayList<>());
				for (final PerdidaClave clave : resultadoClave.getListaClaves()) {
					resClavePerdida.getListaClaves().add(conviertePerdidaClave(clave));
				}
			}

		}

		return resClavePerdida;
	}



	/**
	 * Convierte filtro auditoria busqueda.
	 *
	 * @param pRFiltro
	 *            filtro
	 * @return FiltroAuditoriaTramitacion
	 */
	private FiltroEventoAuditoria convierteFiltroEventoAuditoria(final RFiltroEventoAuditoria pRFiltro) {
		FiltroEventoAuditoria filtro = null;

		if (pRFiltro != null) {

			filtro = new FiltroEventoAuditoria();

			filtro.setListaAreas(pRFiltro.getListaAreas());
			filtro.setIdSesionTramitacion(pRFiltro.getIdSesionTramitacion());
			filtro.setNif(pRFiltro.getNif());
			filtro.setFechaDesde(pRFiltro.getFechaDesde());
			filtro.setFechaHasta(pRFiltro.getFechaHasta());

			if (pRFiltro.getEvento() != null) {
				filtro.setEvento(TypeEvento.valueOf(pRFiltro.getEvento()));
			}

			filtro.setIdTramite(pRFiltro.getIdTramite());
			filtro.setVersionTramite(pRFiltro.getVersionTramite());
			filtro.setIdProcedimientoCP(pRFiltro.getIdProcedimientoCP());
			filtro.setIdProcedimientoSIA(pRFiltro.getIdProcedimientoSIA());

			filtro.setErrorPlataforma(pRFiltro.isErrorPlataforma());
			filtro.setSoloContar(pRFiltro.isSoloContar());
		}

		return filtro;

	}

	/**
	 * Convierte filtro paginacion.
	 *
	 * @param pRFiltro
	 *            filtro
	 * @return FiltroPaginacion
	 */
	private FiltroPaginacion convierteFiltroPaginacion(final RFiltroPaginacion pRFiltro) {
		FiltroPaginacion filtro = null;

		if (pRFiltro != null) {

			filtro = new FiltroPaginacion();

			filtro.setFirst(pRFiltro.getFirst());
			filtro.setPageSize(pRFiltro.getPageSize());
		}

		return filtro;

	}

	/**
	 * Convierte evento auditoria.
	 *
	 * @param pEventoAuditoria
	 *            evento auditoria
	 * @return REventoAuditoria
	 */
	private REventoAuditoria convierteEventoAuditoria(final EventoAuditoriaTramitacion pEventoAuditoria) {
		REventoAuditoria rEvento = null;

		if (pEventoAuditoria != null) {
			rEvento = new REventoAuditoria();

			rEvento.setId(pEventoAuditoria.getId());
			rEvento.setIdSesionTramitacion(pEventoAuditoria.getIdSesionTramitacion());

			if (pEventoAuditoria.getTipoEvento() != null) {
				rEvento.setTipoEvento(pEventoAuditoria.getTipoEvento().toString());
			}

			rEvento.setFecha(pEventoAuditoria.getFecha());
			rEvento.setNif(pEventoAuditoria.getNif());
			rEvento.setIdTramite(pEventoAuditoria.getIdTramite());
			rEvento.setVersionTramite(pEventoAuditoria.getVersionTramite());
			rEvento.setIdProcedimientoCP(pEventoAuditoria.getIdProcedimientoCP());
			rEvento.setIdProcedimientoSIA(pEventoAuditoria.getIdProcedimientoSIA());
			rEvento.setCodigoError(pEventoAuditoria.getCodigoError());
			rEvento.setDescripcion(pEventoAuditoria.getDescripcion());
			rEvento.setResultado(pEventoAuditoria.getResultado());
			rEvento.setTrazaError(pEventoAuditoria.getTrazaError());
			if (pEventoAuditoria.getPropiedadesEvento() != null) {
				try {
					rEvento.setDetalle(JSONUtil.toJSON(pEventoAuditoria.getPropiedadesEvento()));
				} catch (final JSONUtilException e) {
					throw new ErrorJsonException(e);
				}
			}
		}

		return rEvento;
	}

	/**
	 * Convierte filtro tramite busqueda.
	 *
	 * @param pRFiltro
	 *            filtro
	 * @return FiltroPerdidaClave
	 */
	private FiltroPerdidaClave convierteFiltroPerdidaClave(final RFiltroPerdidaClave pRFiltro) {

		FiltroPerdidaClave filtro = null;
		if (pRFiltro != null) {

			filtro = new FiltroPerdidaClave();

			filtro.setListaAreas(pRFiltro.getListaAreas());
			filtro.setDatoFormulario(pRFiltro.getDatoFormulario());
			filtro.setFechaDesde(pRFiltro.getFechaDesde());
			filtro.setFechaHasta(pRFiltro.getFechaHasta());
			filtro.setIdTramite(pRFiltro.getIdTramite());
			filtro.setVersionTramite(pRFiltro.getVersionTramite());
			filtro.setIdProcedimientoCP(pRFiltro.getIdProcedimientoCP());
		}
		return filtro;
	}

	/**
	 * Convierte perdida clave.
	 *
	 * @param pPerdidaClave
	 *            perdida clave
	 * @return RPerdidaClave
	 */
	private RPerdidaClave conviertePerdidaClave(final PerdidaClave pPerdidaClave) {
		RPerdidaClave rClave = null;

		if (pPerdidaClave != null) {

			rClave = new RPerdidaClave();

			rClave.setClaveTramitacion(pPerdidaClave.getClaveTramitacion());
			rClave.setFecha(pPerdidaClave.getFecha());
			rClave.setIdTramite(pPerdidaClave.getIdTramite());
			rClave.setVersionTramite(pPerdidaClave.getVersionTramite());
			rClave.setIdProcedimientoCP(pPerdidaClave.getIdProcedimientoCP());
		}
		return rClave;
	}


}
