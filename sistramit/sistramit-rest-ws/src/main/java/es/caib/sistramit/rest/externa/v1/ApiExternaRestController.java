package es.caib.sistramit.rest.externa.v1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistramit.core.api.exception.ErrorJsonException;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;
import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.service.RestApiExternaService;
import es.caib.sistramit.rest.api.externa.v1.REvento;
import es.caib.sistramit.rest.api.externa.v1.RFiltroEvento;
import es.caib.sistramit.rest.api.externa.v1.RFiltroTramitePersistencia;
import es.caib.sistramit.rest.api.externa.v1.RInfoTicketAcceso;
import es.caib.sistramit.rest.api.externa.v1.RTramitePersistencia;
import es.caib.sistramit.rest.api.externa.v1.RUsuarioAutenticadoInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Operaciones requeridas desde el resto de módulos de Sistra2. No requieren
 * versionado.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/externa/v1")
@Api(value = "externa/v1", produces = "application/json")
public class ApiExternaRestController {

	@Autowired
	private RestApiExternaService restApiExternaService;

	@ApiOperation(value = "Recuperación de eventos", notes = "Solicita recuperación eventos", response = REvento.class, responseContainer = "List")
	@RequestMapping(value = "/evento", method = RequestMethod.POST)
	public List<REvento> obtenerEventos(@RequestBody final RFiltroEvento pFiltro) {
		final List<REvento> rListaEventos = new ArrayList<>();

		final FiltroEvento filtroBusqueda = convierteFiltroEvento(pFiltro);

		final List<Evento> listaEventos = restApiExternaService.recuperarEventos(filtroBusqueda);

		if (listaEventos != null && !listaEventos.isEmpty()) {
			for (final Evento evento : listaEventos) {
				rListaEventos.add(convierteEventos(evento));
			}
		}

		return rListaEventos;
	}

	@ApiOperation(value = "Recuperación trámites en persistencia", notes = "Recuperación trámites en persistencia", response = RTramitePersistencia.class, responseContainer = "List")
	@RequestMapping(value = "/tramite", method = RequestMethod.POST)
	public List<RTramitePersistencia> obtenerTramitesPersistencia(
			@RequestBody final RFiltroTramitePersistencia pFiltro) {
		final List<RTramitePersistencia> rListaTramites = new ArrayList<>();

		final FiltroTramitePersistencia filtroBusqueda = convierteFiltroTramitePersistencia(pFiltro);

		final List<TramitePersistencia> listaTramites = restApiExternaService.recuperarTramites(filtroBusqueda);

		if (listaTramites != null && !listaTramites.isEmpty()) {
			for (final TramitePersistencia tramite : listaTramites) {
				rListaTramites.add(convierteTramitePersistencia(tramite));
			}
		}

		return rListaTramites;
	}

	@ApiOperation(value = "Recupera info trámite en persistencia", notes = "Recupera info trámite en persistencia", response = String.class)
	@RequestMapping(value = "/tramite/{idSesionTramitacion}", method = RequestMethod.GET)
	public RTramitePersistencia obtenerTramitePersistencia(
			@PathVariable("idSesionTramitacion") final String idSesionTramitacion,
			final HttpServletResponse servletResponse) {

		final FiltroTramitePersistencia filtroBusqueda = new FiltroTramitePersistencia();
		filtroBusqueda.setIdSesionTramitacion(idSesionTramitacion);
		final List<TramitePersistencia> listaTramites = restApiExternaService.recuperarTramites(filtroBusqueda);

		RTramitePersistencia res = null;
		if (listaTramites != null && !listaTramites.isEmpty()) {
			res = convierteTramitePersistencia(listaTramites.get(0));
			return res;
		} else {
			servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	@ApiOperation(value = "Obtener ticket de acceso", notes = "Obtener ticket de acceso", response = String.class)
	@RequestMapping(value = "/ticketAcceso", method = RequestMethod.POST)
	public String obtenerTicketAcceso(@RequestBody final RInfoTicketAcceso infoTicket) {
		String ticket = null;

		final InfoTicketAcceso infoTicketAcceso = convierteInfoTicketAcceso(infoTicket);

		ticket = restApiExternaService.obtenerTicketAcceso(infoTicketAcceso);

		return ticket;
	}

	/**
	 * Convierte filtro ticket acceso.
	 *
	 * @param pRInfo
	 *                   filtro
	 * @return info ticket acceso
	 */
	private InfoTicketAcceso convierteInfoTicketAcceso(final RInfoTicketAcceso pRInfo) {
		InfoTicketAcceso info = null;

		if (pRInfo != null) {
			info = new InfoTicketAcceso();

			info.setIdSesionTramitacion(pRInfo.getIdSesionTramitacion());
			info.setUrlCallbackError(pRInfo.getUrlCallbackError());

			final RUsuarioAutenticadoInfo rUsuarioAutenticadoInfo = pRInfo.getUsuarioAutenticadoInfo();
			if (rUsuarioAutenticadoInfo != null) {
				final UsuarioAutenticadoInfo usuarioAutenticadoInfo = new UsuarioAutenticadoInfo();
				usuarioAutenticadoInfo.setApellido1(rUsuarioAutenticadoInfo.getApellido1());
				usuarioAutenticadoInfo.setApellido2(rUsuarioAutenticadoInfo.getApellido2());
				usuarioAutenticadoInfo
						.setAutenticacion(TypeAutenticacion.fromString(rUsuarioAutenticadoInfo.getAutenticacion()));
				usuarioAutenticadoInfo.setQaa(TypeQAA.fromString(rUsuarioAutenticadoInfo.getQaa()));
				usuarioAutenticadoInfo.setEmail(rUsuarioAutenticadoInfo.getEmail());
				usuarioAutenticadoInfo.setMetodoAutenticacion(
						TypeMetodoAutenticacion.fromString(rUsuarioAutenticadoInfo.getMetodoAutenticacion()));
				usuarioAutenticadoInfo.setNif(rUsuarioAutenticadoInfo.getNif());
				usuarioAutenticadoInfo.setNombre(rUsuarioAutenticadoInfo.getNombre());
				usuarioAutenticadoInfo.setUsername(rUsuarioAutenticadoInfo.getUsername());
				info.setUsuarioAutenticadoInfo(usuarioAutenticadoInfo);
			}
		}

		return info;
	}

	/**
	 * Convierte filtro evento.
	 *
	 * @param pRFiltro
	 *                     filtro
	 * @return filtro evento
	 */
	private FiltroEvento convierteFiltroEvento(final RFiltroEvento pRFiltro) {
		FiltroEvento filtro = null;

		if (pRFiltro != null) {
			filtro = new FiltroEvento();

			filtro.setFecha(pRFiltro.getFecha());

			if (pRFiltro.getListaEventos() != null) {
				filtro.setListaEventos(new ArrayList<>());
				for (final String evento : pRFiltro.getListaEventos()) {
					if (TypeEvento.fromString(evento) != null) {
						filtro.getListaEventos().add(TypeEvento.fromString(evento));
					}
				}
			}
		}

		return filtro;

	}

	/**
	 * Convierte filtro tramite persistencia.
	 *
	 * @param pRFiltro
	 *                     filtro
	 * @return filtro tramite persistencia
	 */
	private FiltroTramitePersistencia convierteFiltroTramitePersistencia(final RFiltroTramitePersistencia pRFiltro) {
		FiltroTramitePersistencia filtro = null;

		if (pRFiltro != null) {

			filtro = new FiltroTramitePersistencia();

			filtro.setNif(pRFiltro.getNif());
			filtro.setFechaDesde(pRFiltro.getFechaDesde());
			filtro.setFechaHasta(pRFiltro.getFechaHasta());

		}

		return filtro;

	}

	/**
	 * Convierte eventos.
	 *
	 * @param pEvento
	 *                    the evento
	 * @return the r evento
	 */
	private REvento convierteEventos(final Evento pEvento) {

		REvento rEvento = null;

		if (pEvento != null) {

			rEvento = new REvento();

			rEvento.setIdSesionTramitacion(pEvento.getIdSesionTramitacion());

			if (pEvento.getTipoEvento() != null) {
				rEvento.setTipoEvento(pEvento.getTipoEvento().toString());
			}

			rEvento.setFecha(pEvento.getFecha());
			rEvento.setNif(pEvento.getNif());
			rEvento.setIdTramite(pEvento.getIdTramite());
			rEvento.setVersionTramite(pEvento.getVersionTramite());
			rEvento.setIdProcedimientoCP(pEvento.getIdProcedimientoCP());
			rEvento.setIdProcedimientoSIA(pEvento.getIdProcedimientoSIA());
			rEvento.setCodigoError(pEvento.getCodigoError());
			rEvento.setDescripcion(pEvento.getDescripcion());
			rEvento.setResultado(pEvento.getResultado());
			rEvento.setTrazaError(pEvento.getTrazaError());
			if (pEvento.getPropiedadesEvento() != null) {
				try {
					rEvento.setDetalle(JSONUtil.toJSON(pEvento.getPropiedadesEvento()));
				} catch (final JSONUtilException e) {
					throw new ErrorJsonException(e);
				}
			}
		}

		return rEvento;
	}

	/**
	 * Convierte tramite persistencia.
	 *
	 * @param pTramite
	 *                     tramite
	 * @return RTramitePersistencia
	 */
	private RTramitePersistencia convierteTramitePersistencia(final TramitePersistencia pTramite) {
		RTramitePersistencia rTramite = null;

		if (pTramite != null) {

			rTramite = new RTramitePersistencia();

			rTramite.setIdSesionTramitacion(pTramite.getIdSesionTramitacion());
			rTramite.setIdioma(pTramite.getIdioma());
			rTramite.setIdTramite(pTramite.getIdTramite());
			rTramite.setVersionTramite(pTramite.getVersionTramite());
			rTramite.setDescripcionTramite(pTramite.getDescripcionTramite());
			rTramite.setFechaInicio(pTramite.getFechaInicio());
			rTramite.setFechaUltimoAcceso(pTramite.getFechaUltimoAcceso());
		}

		return rTramite;
	}

}
