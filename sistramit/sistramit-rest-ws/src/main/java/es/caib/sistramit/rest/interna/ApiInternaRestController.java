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
import es.caib.sistramit.core.api.model.system.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.PerdidaClave;
import es.caib.sistramit.core.api.model.system.PersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeTramitePersistencia;
import es.caib.sistramit.core.api.service.RestApiInternaService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.rest.api.interna.RDatosSesionPago;
import es.caib.sistramit.rest.api.interna.RDetallePagoAuditoria;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFichero;
import es.caib.sistramit.rest.api.interna.RFicheroPersistenciaAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroEventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroPaginacion;
import es.caib.sistramit.rest.api.interna.RFiltroPagoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltroPerdidaClave;
import es.caib.sistramit.rest.api.interna.RFiltroPersistenciaAuditoria;
import es.caib.sistramit.rest.api.interna.RINEventoAuditoria;
import es.caib.sistramit.rest.api.interna.RINPagoAuditoria;
import es.caib.sistramit.rest.api.interna.RINTramiteAuditoria;
import es.caib.sistramit.rest.api.interna.RInvalidacion;
import es.caib.sistramit.rest.api.interna.ROUTEventoAuditoria;
import es.caib.sistramit.rest.api.interna.ROUTPagoAuditoria;
import es.caib.sistramit.rest.api.interna.ROUTPerdidaClave;
import es.caib.sistramit.rest.api.interna.ROUTTramiteAuditoria;
import es.caib.sistramit.rest.api.interna.RPagoAuditoria;
import es.caib.sistramit.rest.api.interna.RPerdidaClave;
import es.caib.sistramit.rest.api.interna.RPersistenciaAuditoria;
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

	@ApiOperation(value = "Auditoría de perdida de clave", notes = "Auditoría de perdida de clave", response = ROUTPerdidaClave.class)
	@RequestMapping(value = "/auditoria/clave", method = RequestMethod.POST)
	public ROUTPerdidaClave obtenerClaveTramitacion(@RequestBody final RFiltroPerdidaClave pRFiltroBusqueda) {
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

	@ApiOperation(value = "Recuperar fichero", notes = "Solicita recuperación de un fichero de una sesión de tramitación", response = RFichero.class)
	@RequestMapping(value = "/auditoria/fichero/{idFichero}/{clave}", method = RequestMethod.GET)
	public RFichero obtenerAuditoriaFichero(@PathVariable("idFichero") final Long pIdFichero,
			@PathVariable("clave") final String pClave) {
		RFichero rFichero = null;
		final FicheroAuditoria fichero = restApiInternaService.recuperarFichero(pIdFichero, pClave);

		if (fichero != null) {
			rFichero = new RFichero();
			rFichero.setNombre(fichero.getNombre());
			rFichero.setContenido(Base64.encodeBase64String(fichero.getContenido()));
		}

		return rFichero;

	}

	@ApiOperation(value = "Auditoría de pago", notes = "Auditoría de pago", response = ROUTPagoAuditoria.class)
	@RequestMapping(value = "/auditoria/pago", method = RequestMethod.POST)
	public ROUTPagoAuditoria obtenerAuditoriaPago(@RequestBody final RINPagoAuditoria pFiltros) {
		final ROUTPagoAuditoria resPago = new ROUTPagoAuditoria();

		final FiltroPaginacion filtroPaginacion = convierteFiltroPaginacion(pFiltros.getPaginacion());
		final FiltroPagoAuditoria filtroBusqueda = convierteFiltroPagoAuditoriaBusqueda(pFiltros.getFiltro());

		if (filtroBusqueda != null && filtroBusqueda.isSoloContar()) {
			resPago.setNumElementos(restApiInternaService.contarPagosArea(filtroBusqueda));
		} else {
			final List<PagoAuditoria> listaPagos = restApiInternaService.recuperarPagosArea(filtroBusqueda,
					filtroPaginacion);

			if (listaPagos != null && !listaPagos.isEmpty()) {
				resPago.setListaPagos(new ArrayList<>());

				for (final PagoAuditoria pagoAuditoria : listaPagos) {
					resPago.getListaPagos().add(conviertePagoAuditoria(pagoAuditoria));
				}

			}

		}

		return resPago;
	}

	@ApiOperation(value = "Recuperar detalle pago", notes = "Solicita detalle de un pago.", response = RDetallePagoAuditoria.class)
	@RequestMapping(value = "/auditoria/pago/{id}", method = RequestMethod.GET)
	public RDetallePagoAuditoria obtenerAuditoriaDetallePago(@PathVariable("id") final Long pIdPago) {
		final DetallePagoAuditoria detallePago = restApiInternaService.recuperarDetallePago(pIdPago);
		return convierteDetallePago(detallePago);
	}

	@ApiOperation(value = "Auditoría de tramite", notes = "Auditoría de tramite", response = ROUTTramiteAuditoria.class)
	@RequestMapping(value = "/auditoria/tramite", method = RequestMethod.POST)
	public ROUTTramiteAuditoria obtenerAuditoriaTramite(@RequestBody final RINTramiteAuditoria pFiltros) {
		final ROUTTramiteAuditoria resTramite = new ROUTTramiteAuditoria();

		final FiltroPaginacion filtroPaginacion = convierteFiltroPaginacion(pFiltros.getPaginacion());
		final FiltroPersistenciaAuditoria filtroBusqueda = convierteFiltroPersistenciaAuditoria(pFiltros.getFiltro());

		if (filtroBusqueda != null && filtroBusqueda.isSoloContar()) {
			resTramite.setNumElementos(restApiInternaService.contarPersistenciaArea(filtroBusqueda));
		} else {
			final List<PersistenciaAuditoria> listaPersistencia = restApiInternaService
					.recuperarPersistenciaArea(filtroBusqueda, filtroPaginacion);

			if (listaPersistencia != null && !listaPersistencia.isEmpty()) {
				resTramite.setListaPersistencia(new ArrayList<>());
				for (final PersistenciaAuditoria persistenciaAuditoria : listaPersistencia) {
					resTramite.getListaPersistencia().add(conviertePersistenciaAuditoria(persistenciaAuditoria));
				}

			}

		}

		return resTramite;
	}

	@ApiOperation(value = "Recuperar ficheros de tramite", notes = "Recuperar ficheros de tramite.", response = RFicheroPersistenciaAuditoria.class, responseContainer = "List")
	@RequestMapping(value = "/auditoria/tramite/{id}", method = RequestMethod.GET)
	public List<RFicheroPersistenciaAuditoria> obtenerAuditoriaTramiteFicheros(
			@PathVariable("id") final Long pIdTramite) {
		List<RFicheroPersistenciaAuditoria> resultado = null;
		final List<FicheroPersistenciaAuditoria> listaFicheros = restApiInternaService
				.recuperarPersistenciaFicheros(pIdTramite);

		if (listaFicheros != null && !listaFicheros.isEmpty()) {
			resultado = new ArrayList<>();

			for (final FicheroPersistenciaAuditoria fichero : listaFicheros) {
				resultado.add(convierteFicheroPersistenciaAuditoria(fichero));
			}
		}

		return resultado;
	}

	private RDetallePagoAuditoria convierteDetallePago(final DetallePagoAuditoria pDetallePago) {
		RDetallePagoAuditoria rDetalle = null;

		if (pDetallePago != null) {

			rDetalle = new RDetallePagoAuditoria();

			if (pDetallePago.getDatos() != null) {
				final RDatosSesionPago rDatos = new RDatosSesionPago();
				rDatos.setPasarelaId(pDetallePago.getDatos().getPasarelaId());
				rDatos.setEntidadId(pDetallePago.getDatos().getEntidadId());
				rDatos.setOrganismoId(pDetallePago.getDatos().getOrganismoId());
				rDatos.setSimulado(pDetallePago.getDatos().isSimulado());
				rDatos.setIdentificadorPago(pDetallePago.getDatos().getIdentificadorPago());
				rDatos.setPresentacion(pDetallePago.getDatos().getPresentacion().toString());
				rDatos.setFechaPago(pDetallePago.getDatos().getFechaPago());
				rDatos.setLocalizador(pDetallePago.getDatos().getLocalizador());
				rDatos.setIdioma(pDetallePago.getDatos().getIdioma());
				rDatos.setSujetoPasivoNif(pDetallePago.getDatos().getSujetoPasivo().getNif());
				rDatos.setSujetoPasivoNombre(pDetallePago.getDatos().getSujetoPasivo().getNombre());
				rDatos.setModelo(pDetallePago.getDatos().getModelo());
				rDatos.setConcepto(pDetallePago.getDatos().getConcepto());
				rDatos.setTasaId(pDetallePago.getDatos().getTasaId());
				rDatos.setImporte(pDetallePago.getDatos().getImporte());
				rDatos.setDetallePago(pDetallePago.getDatos().getDetallePago());

				rDetalle.setDatos(rDatos);
			}

			if (pDetallePago.getVerificacion() != null) {
				final RVerificacionPago rVerificacion = new RVerificacionPago();

				rVerificacion.setVerificado(pDetallePago.getVerificacion().isVerificado());
				rVerificacion.setPagado(pDetallePago.getVerificacion().isPagado());
				rVerificacion.setCodigoError(pDetallePago.getVerificacion().getCodigoError());
				rVerificacion.setMensajeError(pDetallePago.getVerificacion().getMensajeError());
				rVerificacion.setFechaPago(pDetallePago.getVerificacion().getFechaPago());
				rVerificacion.setLocalizador(pDetallePago.getVerificacion().getLocalizador());
				rVerificacion.setJustificantePDF(
						Base64.encodeBase64String(pDetallePago.getVerificacion().getJustificantePDF()));

				rDetalle.setVerificacion(rVerificacion);
			}

		}

		return rDetalle;
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

			rClave.setIdSesionTramitacion(pPerdidaClave.getIdSesionTramitacion());
			rClave.setFecha(pPerdidaClave.getFecha());
			rClave.setIdTramite(pPerdidaClave.getIdTramite());
			rClave.setVersionTramite(pPerdidaClave.getVersionTramite());
			rClave.setIdProcedimientoCP(pPerdidaClave.getIdProcedimientoCP());
		}
		return rClave;
	}

	/**
	 * Convierte filtro auditoria busqueda.
	 *
	 * @param pRFiltro
	 *            filtro
	 * @return FiltroAuditoriaTramitacion
	 */
	private FiltroPagoAuditoria convierteFiltroPagoAuditoriaBusqueda(final RFiltroPagoAuditoria pRFiltro) {
		FiltroPagoAuditoria filtro = null;

		if (pRFiltro != null) {

			filtro = new FiltroPagoAuditoria();

			filtro.setListaAreas(pRFiltro.getListaAreas());
			filtro.setIdSesionTramitacion(pRFiltro.getIdSesionTramitacion());
			filtro.setNif(pRFiltro.getNif());
			filtro.setFechaDesde(pRFiltro.getFechaDesde());
			filtro.setFechaHasta(pRFiltro.getFechaHasta());

			if (pRFiltro.getAcceso() != null) {
				filtro.setAcceso(TypeAutenticacion.valueOf(pRFiltro.getAcceso()));
			}

			filtro.setSoloContar(pRFiltro.isSoloContar());
		}

		return filtro;

	}

	private RPagoAuditoria conviertePagoAuditoria(final PagoAuditoria pPagoAuditoria) {
		RPagoAuditoria rPago = null;

		if (pPagoAuditoria != null) {

			rPago = new RPagoAuditoria();

			rPago.setIdSesionTramitacion(pPagoAuditoria.getIdSesionTramitacion());
			rPago.setFecha(pPagoAuditoria.getFecha());
			rPago.setIdTramite(pPagoAuditoria.getIdTramite());
			rPago.setVersionTramite(pPagoAuditoria.getVersionTramite());
			rPago.setFichero(pPagoAuditoria.getFichero());
			rPago.setFicheroClave(pPagoAuditoria.getFicheroClave());
			rPago.setCodigoPago(pPagoAuditoria.getCodigoPago());
			rPago.setEstado(pPagoAuditoria.getEstado());
			rPago.setIdentificador(pPagoAuditoria.getIdentificador());
			rPago.setPresentacion(pPagoAuditoria.getPresentacion());
			rPago.setPasarelaId(pPagoAuditoria.getPasarelaId());
			rPago.setImporte(pPagoAuditoria.getImporte());
			rPago.setTasaId(pPagoAuditoria.getTasaId());
			rPago.setLocalizador(pPagoAuditoria.getLocalizador());
			rPago.setFechaPago(pPagoAuditoria.getFechaPago());
			rPago.setPagoEstadoIncorrecto(pPagoAuditoria.getPagoEstadoIncorrecto());
		}

		return rPago;
	}

	/**
	 * Convierte filtro auditoria busqueda.
	 *
	 * @param pRFiltro
	 *            filtro
	 * @return FiltroAuditoriaTramitacion
	 */
	private FiltroPersistenciaAuditoria convierteFiltroPersistenciaAuditoria(
			final RFiltroPersistenciaAuditoria pRFiltro) {
		FiltroPersistenciaAuditoria filtro = null;

		if (pRFiltro != null) {

			filtro = new FiltroPersistenciaAuditoria();

			filtro.setListaAreas(pRFiltro.getListaAreas());
			filtro.setIdSesionTramitacion(pRFiltro.getIdSesionTramitacion());
			filtro.setNif(pRFiltro.getNif());
			filtro.setFechaDesde(pRFiltro.getFechaDesde());
			filtro.setFechaHasta(pRFiltro.getFechaHasta());

			if (pRFiltro.getTipoTramitePersistencia() != null) {
				filtro.setTipoTramitePersistencia(
						TypeTramitePersistencia.fromString(pRFiltro.getTipoTramitePersistencia()));
			}

			filtro.setIdTramite(pRFiltro.getIdTramite());
			filtro.setVersionTramite(pRFiltro.getVersionTramite());
			filtro.setIdProcedimientoCP(pRFiltro.getIdProcedimientoCP());
			filtro.setIdProcedimientoSIA(pRFiltro.getIdProcedimientoSIA());

			filtro.setSoloContar(pRFiltro.isSoloContar());
		}

		return filtro;

	}

	private RPersistenciaAuditoria conviertePersistenciaAuditoria(final PersistenciaAuditoria pPersistenciaAuditoria) {
		RPersistenciaAuditoria rPersistencia = null;

		if (pPersistenciaAuditoria != null) {

			rPersistencia = new RPersistenciaAuditoria();

			rPersistencia.setId(pPersistenciaAuditoria.getId());
			rPersistencia.setIdSesionTramitacion(pPersistenciaAuditoria.getIdSesionTramitacion());
			rPersistencia.setIdTramite(pPersistenciaAuditoria.getIdTramite());
			rPersistencia.setVersionTramite(pPersistenciaAuditoria.getVersionTramite());
			rPersistencia.setIdProcedimientoCP(pPersistenciaAuditoria.getIdProcedimientoCP());
			rPersistencia.setNif(pPersistenciaAuditoria.getNif());
			rPersistencia.setNombre(pPersistenciaAuditoria.getNombre());
			rPersistencia.setApellido1(pPersistenciaAuditoria.getApellido1());
			rPersistencia.setApellido2(pPersistenciaAuditoria.getApellido2());
			rPersistencia.setFechaInicio(pPersistenciaAuditoria.getFechaInicio());
			rPersistencia.setEstado(pPersistenciaAuditoria.getEstado().toString());
			rPersistencia.setCancelado(pPersistenciaAuditoria.isCancelado());
			rPersistencia.setFechaCaducidad(pPersistenciaAuditoria.getFechaCaducidad());
			rPersistencia.setPurgar(pPersistenciaAuditoria.isPurgar());
			rPersistencia.setFechaPurgado(pPersistenciaAuditoria.getFechaPurgado());
			rPersistencia.setPurgado(pPersistenciaAuditoria.isPurgado());
			rPersistencia.setDescripcionTramite(pPersistenciaAuditoria.getDescripcionTramite());
			rPersistencia.setFechaUltimoAcceso(pPersistenciaAuditoria.getFechaUltimoAcceso());
			rPersistencia.setFechaFin(pPersistenciaAuditoria.getFechaFin());
			rPersistencia.setPersistente(pPersistenciaAuditoria.isPersistente());
			rPersistencia.setUrlInicio(pPersistenciaAuditoria.getUrlInicio());
		}

		return rPersistencia;
	}

	private RFicheroPersistenciaAuditoria convierteFicheroPersistenciaAuditoria(
			final FicheroPersistenciaAuditoria pFichero) {
		RFicheroPersistenciaAuditoria rFichero = null;

		if (pFichero != null) {

			rFichero = new RFicheroPersistenciaAuditoria();
			rFichero.setIdentificadorPaso(pFichero.getIdentificadorPaso());
			rFichero.setTipoPaso(pFichero.getTipoPaso());
			rFichero.setNombre(pFichero.getNombre());
			rFichero.setCodigo(pFichero.getCodigo());
			rFichero.setClave(pFichero.getClave());
			rFichero.setTipo(pFichero.getTipo());

		}

		return rFichero;
	}

}
