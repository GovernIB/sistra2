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

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoRepresentante;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;
import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.service.RestApiExternaService;
import es.caib.sistramit.rest.api.externa.v1.REvento;
import es.caib.sistramit.rest.api.externa.v1.RFiltroEvento;
import es.caib.sistramit.rest.api.externa.v1.RFiltroTramiteFinalizado;
import es.caib.sistramit.rest.api.externa.v1.RFiltroTramitePersistencia;
import es.caib.sistramit.rest.api.externa.v1.RInfoTicketAcceso;
import es.caib.sistramit.rest.api.externa.v1.RTramiteFinalizado;
import es.caib.sistramit.rest.api.externa.v1.RTramitePersistencia;
import es.caib.sistramit.rest.api.externa.v1.RUsuarioAutenticadoInfo;
import es.caib.sistramit.rest.api.util.JsonException;
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

	@ApiOperation(value = "Recuperación trámites finalizados", notes = "Recuperación trámites finalizados", response = RTramiteFinalizado.class, responseContainer = "List")
	@RequestMapping(value = "/tramiteFinalizado", method = RequestMethod.POST)
	public List<RTramiteFinalizado> obtenerTramitesFinalizados(@RequestBody final RFiltroTramiteFinalizado pFiltro) {
		final List<RTramiteFinalizado> rListaTramites = new ArrayList<>();
		final FiltroTramiteFinalizado filtroBusqueda = convierteFiltroTramiteFinalizado(pFiltro);
		final List<TramiteFinalizado> listaTramites = restApiExternaService
				.recuperarTramitesFinalizados(filtroBusqueda);
		if (listaTramites != null && !listaTramites.isEmpty()) {
			for (final TramiteFinalizado tramite : listaTramites) {
				rListaTramites.add(convierteTramiteFinalizado(tramite));
			}
		}
		return rListaTramites;
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

				if(rUsuarioAutenticadoInfo.getRepresentanteInfo() != null) {
					final UsuarioAutenticadoRepresentante usuarioAutenticadoRepresentante = new UsuarioAutenticadoRepresentante();
					usuarioAutenticadoRepresentante.setNif(rUsuarioAutenticadoInfo.getRepresentanteInfo().getNif());
					usuarioAutenticadoRepresentante.setNombre(rUsuarioAutenticadoInfo.getRepresentanteInfo().getNombre());
					usuarioAutenticadoRepresentante.setApellido1(rUsuarioAutenticadoInfo.getRepresentanteInfo().getApellido1());
					usuarioAutenticadoRepresentante.setApellido2(rUsuarioAutenticadoInfo.getRepresentanteInfo().getApellido2());
					usuarioAutenticadoRepresentante.setEmail(rUsuarioAutenticadoInfo.getRepresentanteInfo().getEmail());
					usuarioAutenticadoInfo.setRepresentante(usuarioAutenticadoRepresentante);

				}
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
	 * @throws JsonException
	 */
	private FiltroEvento convierteFiltroEvento(final RFiltroEvento pRFiltro) {
		final List<TypeEvento> eventosNoPermitidos = TypeEvento.getEventosInternos();
		final FiltroEvento filtro = new FiltroEvento();
		if (pRFiltro != null) {
			filtro.setFecha(pRFiltro.getFecha());
			if (pRFiltro.getListaEventos() != null) {
				for (final String ev : pRFiltro.getListaEventos()) {
					final TypeEvento evento = TypeEvento.fromString(ev);
					if (evento == null) {
						throw new ErrorFrontException("No existeix event de tipus " + ev);
					}
					if (eventosNoPermitidos.contains(evento)) {
						throw new ErrorFrontException("Event no permés: " + ev);
					}
					filtro.getListaEventos().add(evento);
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
		final FiltroTramitePersistencia filtro = new FiltroTramitePersistencia();
		if (pRFiltro != null) {
			filtro.setNif(pRFiltro.getNif());
			filtro.setFechaDesde(pRFiltro.getFechaDesde());
			filtro.setFechaHasta(pRFiltro.getFechaHasta());
		}
		return filtro;
	}

	/**
	 * Convierte filtro tramite finalizado.
	 *
	 * @param pRFiltro
	 *                     filtro
	 * @return filtro tramite finalizado
	 */
	private FiltroTramiteFinalizado convierteFiltroTramiteFinalizado(final RFiltroTramiteFinalizado pRFiltro) {
		final FiltroTramiteFinalizado filtro = new FiltroTramiteFinalizado();
		if (pRFiltro != null) {
			filtro.setNif(pRFiltro.getNif());
			filtro.setFechaDesde(pRFiltro.getFechaDesde());
			filtro.setFechaHasta(pRFiltro.getFechaHasta());
			filtro.setIdSesionTramitacion(pRFiltro.getIdSesionTramitacion());
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
			rEvento.setTipoEvento(pEvento.getTipoEvento().toString());
			rEvento.setFecha(pEvento.getFecha());
			rEvento.setIdTramite(pEvento.getIdTramite());
			rEvento.setVersionTramite(pEvento.getVersionTramite());
			rEvento.setIdProcedimientoCP(pEvento.getIdProcedimientoCP());
			rEvento.setIdProcedimientoSIA(pEvento.getIdProcedimientoSIA());
			if (pEvento.getPropiedadesEvento() != null) {
				rEvento.setPropiedadesEvento(pEvento.getPropiedadesEvento().getPropiedades());
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

	/**
	 * Convierte tramite finalizado
	 *
	 * @param tramite
	 *                    tramite
	 * @return RTramiteFinalizado
	 */
	private RTramiteFinalizado convierteTramiteFinalizado(final TramiteFinalizado pTramite) {
		RTramiteFinalizado rTramite = null;

		if (pTramite != null) {
			rTramite = new RTramiteFinalizado();
			rTramite.setIdSesionTramitacion(pTramite.getIdSesionTramitacion());
			rTramite.setIdioma(pTramite.getIdioma());
			rTramite.setIdTramite(pTramite.getIdTramite());
			rTramite.setVersionTramite(pTramite.getVersionTramite());
			rTramite.setDescripcionTramite(pTramite.getDescripcionTramite());
			rTramite.setFechaFin(pTramite.getFechaFin());
			rTramite.setAutenticacion(pTramite.getAutenticacion().toString());
			rTramite.setMetodoAutenticacion(pTramite.getMetodoAutenticacion().toString());
			rTramite.setIdProcedimientoSIA(pTramite.getIdProcedimientoSIA());
			rTramite.setNif(pTramite.getNif());
			rTramite.setNombreApellidos(pTramite.getNombreApellidos());
			rTramite.setNumeroRegistro(pTramite.getNumeroRegistro());
		}

		return rTramite;
	}

}
