package es.caib.sistrahelp.core.service.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
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
import es.caib.sistrahelp.core.api.model.DatosSesionPago;
import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.EventoCM;
import es.caib.sistrahelp.core.api.model.FicheroAuditoria;
import es.caib.sistrahelp.core.api.model.FicheroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaPago;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.FiltroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PagoAuditoria;
import es.caib.sistrahelp.core.api.model.PerdidaClave;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.Persona;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaDetallePago;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaPago;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaPersistencia;
import es.caib.sistrahelp.core.api.model.ResultadoErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.ResultadoEventoAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoEventoCM;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;
import es.caib.sistrahelp.core.api.model.ResultadoSoporte;
import es.caib.sistrahelp.core.api.model.Soporte;
import es.caib.sistrahelp.core.api.model.VerificacionPago;
import es.caib.sistrahelp.core.api.model.comun.ListaPropiedades;
import es.caib.sistrahelp.core.api.model.types.TypeDocumentoPersistencia;
import es.caib.sistrahelp.core.api.model.types.TypeEstadoTramite;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePresentacion;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistramit.rest.api.interna.RDetallePagoAuditoria;
import es.caib.sistramit.rest.api.interna.RErroresPorTramiteCM;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.REventoCM;
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
import es.caib.sistramit.rest.api.interna.ROUTErroresPorTramiteCM;
import es.caib.sistramit.rest.api.interna.ROUTEventoAuditoria;
import es.caib.sistramit.rest.api.interna.ROUTEventoCM;
import es.caib.sistramit.rest.api.interna.ROUTPagoAuditoria;
import es.caib.sistramit.rest.api.interna.ROUTPerdidaClave;
import es.caib.sistramit.rest.api.interna.ROUTSoporte;
import es.caib.sistramit.rest.api.interna.ROUTTramiteAuditoria;
import es.caib.sistramit.rest.api.interna.RPagoAuditoria;
import es.caib.sistramit.rest.api.interna.RPerdidaClave;
import es.caib.sistramit.rest.api.interna.RPersistenciaAuditoria;
import es.caib.sistramit.rest.api.interna.RSoporte;

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
		ROUTEventoAuditoria rResultado = null;
		List<REventoAuditoria> listaREventos = null;

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroAuditoriaBusqueda(toCapital(pFiltroBusqueda)));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTEventoAuditoria> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/evento", request, ROUTEventoAuditoria.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		} else {
			rResultado = new ROUTEventoAuditoria();
			rResultado.setListaEventos(null);
			rResultado.setNumElementos((long) 0);
		}

		if (rResultado != null) {
			resultado = new ResultadoEventoAuditoria();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				listaREventos = rResultado.getListaEventos();

				if (listaREventos != null) {
					resultado.setListaEventos(new ArrayList<>());
					// auditoria
					for (final REventoAuditoria rEventoAuditoria : listaREventos) {
						resultado.getListaEventos().add(convierteEventoAuditoria(rEventoAuditoria));
					}

				}
			}
		}

		return resultado;
	}

	@Override
	public ResultadoSoporte obtenerFormularioSoporte(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {

		ResultadoSoporte resultado = null;
		ROUTSoporte rResultado = null;
		List<RSoporte> listaREventos = null;

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroAuditoriaBusqueda(toCapital(pFiltroBusqueda)));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTSoporte> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/soporte", request, ROUTSoporte.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		} else {
			rResultado = new ROUTSoporte();
			rResultado.setListaFormularios(null);
			rResultado.setNumElementos((long) 0);
		}

		if (rResultado != null) {
			resultado = new ResultadoSoporte();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				listaREventos = rResultado.getListaFormularios();

				if (listaREventos != null) {
					resultado.setListaFormularios(new ArrayList<>());
					// auditoria
					for (final RSoporte rForm : listaREventos) {
						resultado.getListaFormularios().add(convierteSoporte(rForm));
					}

				}
			}
		}

		return resultado;
	}

	private FiltroAuditoriaTramitacion toCapital(FiltroAuditoriaTramitacion filtro) {
		if (filtro != null) {
			if (filtro.getIdSesionTramitacion() != null) {
				filtro.setIdSesionTramitacion(filtro.getIdSesionTramitacion().toUpperCase());
			}

			if (filtro.getIdProcedimientoCP() != null) {
				filtro.setIdProcedimientoCP(filtro.getIdProcedimientoCP().toUpperCase());
			}

			if (filtro.getNombre() != null) {
				filtro.setNombre(filtro.getNombre().toUpperCase());
			}

			if (filtro.getNif() != null) {
				filtro.setNif(filtro.getNif().toUpperCase());
			}

			if (filtro.getIdTramite() != null) {
				filtro.setIdTramite(filtro.getIdTramite().toUpperCase());
			}

			if (filtro.getCodSia() != null) {
				filtro.setCodSia(filtro.getCodSia().toUpperCase());
			}
		}
		return filtro;
	}

	@Override
	public ResultadoPerdidaClave obtenerClaveTramitacion(final FiltroPerdidaClave pFiltroBusqueda) {
		ResultadoPerdidaClave resultado = null;
		ROUTPerdidaClave rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final HttpEntity<RFiltroPerdidaClave> request = new HttpEntity<>(
				convierteFiltroTramiteBusqueda(pFiltroBusqueda), headers);

		final ResponseEntity<ROUTPerdidaClave> response = restTemplate.postForEntity(getUrl() + "/auditoria/clave",
				request, ROUTPerdidaClave.class);

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
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

	@Override
	public ResultadoAuditoriaPago obtenerAuditoriaPago(final FiltroAuditoriaPago pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		ResultadoAuditoriaPago resultado = null;
		ROUTPagoAuditoria rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINPagoAuditoria param = new RINPagoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroInformacionPagos(pFiltroBusqueda));

		final HttpEntity<RINPagoAuditoria> request = new HttpEntity<>(param, headers);

		final ResponseEntity<ROUTPagoAuditoria> response = restTemplate.postForEntity(getUrl() + "/auditoria/pago",
				request, ROUTPagoAuditoria.class);

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		}

		if (rResultado != null) {
			resultado = new ResultadoAuditoriaPago();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				if (rResultado.getListaPagos() != null) {
					resultado.setListaPagos(new ArrayList<>());

					for (final RPagoAuditoria rPago : rResultado.getListaPagos()) {
						resultado.getListaPagos().add(conviertePago(rPago));
					}
				}
			}

		}

		return resultado;
	}

	@Override
	public ResultadoAuditoriaDetallePago obtenerAuditoriaDetallePago(final Long pIdPago) {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final RDetallePagoAuditoria rDetallePagoAuditoria = restTemplate
				.getForObject(getUrl() + "/auditoria/pago/" + pIdPago, RDetallePagoAuditoria.class);

		return convierteDetallePago(rDetallePagoAuditoria);

	}

	@Override
	public ResultadoAuditoriaPersistencia obtenerAuditoriaPersistencia(
			final FiltroPersistenciaAuditoria pFiltroBusqueda, final FiltroPaginacion pFiltroPaginacion) {
		ResultadoAuditoriaPersistencia resultado = null;
		ROUTTramiteAuditoria rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINTramiteAuditoria param = new RINTramiteAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroPersistencia(pFiltroBusqueda));

		final HttpEntity<RINTramiteAuditoria> request = new HttpEntity<>(param, headers);

		final ResponseEntity<ROUTTramiteAuditoria> response = restTemplate
				.postForEntity(getUrl() + "/auditoria/tramite", request, ROUTTramiteAuditoria.class);

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		}

		if (rResultado != null) {
			resultado = new ResultadoAuditoriaPersistencia();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				if (rResultado.getListaPersistencia() != null) {
					resultado.setListaPersistencia(new ArrayList<>());
					Date now = new Date();
					for (final RPersistenciaAuditoria rPersistencia : rResultado.getListaPersistencia()) {
						if (rPersistencia.getFechaCaducidad() == null
								|| rPersistencia.getFechaCaducidad().getTime() - now.getTime() > 0) {
							resultado.getListaPersistencia().add(conviertePersistencia(rPersistencia));
						}
					}
				}
			}

		}

		return resultado;
	}

	@Override
	public List<FicheroPersistenciaAuditoria> obtenerAuditoriaPersistenciaFichero(final Long pIdTramite) {
		List<FicheroPersistenciaAuditoria> resultado = null;
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final RFicheroPersistenciaAuditoria[] listaRFichero = restTemplate
				.getForObject(getUrl() + "/auditoria/tramite/" + pIdTramite, RFicheroPersistenciaAuditoria[].class);

		if (listaRFichero != null) {
			resultado = new ArrayList<>();

			for (final RFicheroPersistenciaAuditoria rFichero : listaRFichero) {
				resultado.add(convierteFicheroPersistenciaAuditoria(rFichero));
			}
		}

		return resultado;

	}

	@Override
	public FicheroAuditoria obtenerAuditoriaFichero(final Long pIdFichero, final String pClave) {
		FicheroAuditoria resultado = null;
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final RFichero rFichero = restTemplate
				.getForObject(getUrl() + "/auditoria/fichero/" + pIdFichero + "/" + pClave, RFichero.class);

		if (rFichero != null) {
			resultado = convierteAuditoriaFichero(rFichero);
		}

		return resultado;
	}

	@Override
	public ResultadoEventoCM obtenerCountEventoCM(FiltroAuditoriaTramitacion pFiltroBusqueda) {
		ResultadoEventoCM resultado = null;
		ROUTEventoCM rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setFiltro(convierteFiltroAuditoriaBusqueda(toCapital(pFiltroBusqueda)));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTEventoCM> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/countEventoCM", request, ROUTEventoCM.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		}

		if (rResultado != null) {
			resultado = new ResultadoEventoCM();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				if (rResultado.getListaEventos() != null) {
					resultado.setListaEventosCM(new ArrayList<>());

					for (final REventoCM rEvCM : rResultado.getListaEventos()) {
						resultado.getListaEventosCM().add(convierteEventoCM(rEvCM));
					}
				}
			}

		}

		return resultado;
	}

	@Override
	public String urlLogoEntidad(String codDir3) {

		final RestTemplate restTemplate = new RestTemplate();
		String resultado = "";
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final HttpEntity<String> request = new HttpEntity<>(codDir3, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/urlLogoEntidad", request, String.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			resultado = response.getBody();
		}

		return resultado;
	}

	@Override
	public ResultadoErroresPorTramiteCM obtenerErroresPorTramiteCM(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		ResultadoErroresPorTramiteCM resultado = null;
		ROUTErroresPorTramiteCM rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroAuditoriaBusqueda(toCapital(pFiltroBusqueda)));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTErroresPorTramiteCM> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/erroresTramiteCM", request,
					ROUTErroresPorTramiteCM.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		}

		if (rResultado != null) {
			resultado = new ResultadoErroresPorTramiteCM();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				if (rResultado.getListaErrores() != null) {
					resultado.setListaErroresCM(new ArrayList<>());

					for (final RErroresPorTramiteCM rErrCM : rResultado.getListaErrores()) {
						resultado.getListaErroresCM().add(convierteErroresPorTramiteCM(rErrCM));
					}
				}
			}

		}

		return resultado;
	}

	@Override
	public ResultadoEventoCM obtenerErroresPorTramiteCMExpansion(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		ResultadoEventoCM resultado = null;
		ROUTEventoCM rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroAuditoriaBusqueda(toCapital(pFiltroBusqueda)));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTEventoCM> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/erroresTramiteCMRe", request,
					ROUTEventoCM.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		}

		if (rResultado != null) {
			resultado = new ResultadoEventoCM();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				if (rResultado.getListaEventos() != null) {
					resultado.setListaEventosCM(new ArrayList<>());

					for (final REventoCM rErrCM : rResultado.getListaEventos()) {
						resultado.getListaEventosCM().add(convierteEventoCM(rErrCM));
					}
				}
			}

		}

		return resultado;
	}

	@Override
	public ResultadoEventoCM obtenerTramitesPorErrorCM(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		ResultadoEventoCM resultado = null;
		ROUTEventoCM rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroAuditoriaBusqueda(toCapital(pFiltroBusqueda)));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTEventoCM> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/tramitesErrorCM", request,
					ROUTEventoCM.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		}

		if (rResultado != null) {
			resultado = new ResultadoEventoCM();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				if (rResultado.getListaEventos() != null) {
					resultado.setListaEventosCM(new ArrayList<>());

					for (final REventoCM rErrCM : rResultado.getListaEventos()) {
						resultado.getListaEventosCM().add(convierteEventoCM(rErrCM));
					}
				}
			}

		}

		return resultado;
	}

	@Override
	public ResultadoErroresPorTramiteCM obtenerTramitesPorErrorCMExpansion(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		ResultadoErroresPorTramiteCM resultado = null;
		ROUTErroresPorTramiteCM rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroAuditoriaBusqueda(toCapital(pFiltroBusqueda)));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTErroresPorTramiteCM> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/tramitesErrorCMRe", request,
					ROUTErroresPorTramiteCM.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		}

		if (rResultado != null) {
			resultado = new ResultadoErroresPorTramiteCM();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				if (rResultado.getListaErrores() != null) {
					resultado.setListaErroresCM(new ArrayList<>());

					for (final RErroresPorTramiteCM rErrCM : rResultado.getListaErrores()) {
						resultado.getListaErroresCM().add(convierteErroresPorTramiteCM(rErrCM));
					}
				}
			}

		}

		return resultado;
	}

	@Override
	public ResultadoEventoCM obtenerErroresPlataformaCM(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		ResultadoEventoCM resultado = null;
		ROUTEventoCM rResultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RINEventoAuditoria param = new RINEventoAuditoria();
		param.setPaginacion(convierteFiltroPaginacion(pFiltroPaginacion));
		param.setFiltro(convierteFiltroAuditoriaBusqueda(toCapital(pFiltroBusqueda)));

		final HttpEntity<RINEventoAuditoria> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTEventoCM> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/erroresPlataformaCM", request,
					ROUTEventoCM.class);
		} catch (Exception e) {

		}

		if (response != null && response.getStatusCodeValue() == 200) {
			rResultado = response.getBody();
		}

		if (rResultado != null) {
			resultado = new ResultadoEventoCM();

			if (pFiltroBusqueda != null && pFiltroBusqueda.isSoloContar()) {
				resultado.setNumElementos(rResultado.getNumElementos());
			} else {
				if (rResultado.getListaEventos() != null) {
					resultado.setListaEventosCM(new ArrayList<>());

					for (final REventoCM rErrCM : rResultado.getListaEventos()) {
						resultado.getListaEventosCM().add(convierteEventoCM(rErrCM));
					}
				}
			}

		}

		return resultado;
	}

	@Override
	public void updateFormularioSoporte(Soporte soporte) {
		List<RSoporte> listaREventos = null;

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo", soporte.getCodigo());
		param.put("estado", soporte.getEstado());
		param.put("comentarios", soporte.getComent());

		final HttpEntity<Map<String, Object>> request = new HttpEntity<>(param, headers);
		ResponseEntity<ROUTSoporte> response = null;
		try {
			response = restTemplate.postForEntity(getUrl() + "/auditoria/soporte/update", request, ROUTSoporte.class);
		} catch (Exception e) {

		}
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
	 * @param pFiltro filtro
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
	 * @param pFiltro filtro
	 * @return RFiltroEventoAuditoria
	 */
	private RFiltroEventoAuditoria convierteFiltroAuditoriaBusqueda(final FiltroAuditoriaTramitacion pFiltro) {
		RFiltroEventoAuditoria rFiltro = null;

		if (pFiltro != null) {
			rFiltro = new RFiltroEventoAuditoria();
			rFiltro.setListaAreas(pFiltro.getListaAreas());
			rFiltro.setIdSesionTramitacion(pFiltro.getIdSesionTramitacion());
			rFiltro.setNif(pFiltro.getNif());
			rFiltro.setNombre(pFiltro.getNombre());
			rFiltro.setTlf(pFiltro.getTlf());
			rFiltro.setEmail(pFiltro.getEmail());
			rFiltro.setFechaDesde(pFiltro.getFechaDesde());
			rFiltro.setFechaHasta(pFiltro.getFechaHasta());
			rFiltro.setCodSia(pFiltro.getCodSia());
			rFiltro.setExcepcion(pFiltro.getExcepcion());
			rFiltro.setEstado(pFiltro.getEstado());
			rFiltro.setComent(pFiltro.getComent());

			if (pFiltro.getEvento() != null) {
				rFiltro.setEvento(pFiltro.getEvento().name());
			}

			rFiltro.setIdTramite(pFiltro.getIdTramite());
			rFiltro.setVersionTramite(pFiltro.getVersionTramite());
			rFiltro.setIdProcedimientoCP(pFiltro.getIdProcedimientoCP());
			rFiltro.setIdProcedimientoSIA(pFiltro.getIdProcedimientoSIA());
			rFiltro.setErrorPlataforma(pFiltro.isErrorPlataforma());
			rFiltro.setSoloContar(pFiltro.isSoloContar());

			rFiltro.setSortField(pFiltro.getSortField());
			rFiltro.setSortOrder(pFiltro.getSortOrder());

			rFiltro.setClasificacionSeleccionada(pFiltro.getClasificacionSeleccionada());
			rFiltro.setErrorTipo(pFiltro.getErrorTipo());
		}

		return rFiltro;

	}

	/**
	 * Convierte evento auditoria.
	 *
	 * @param pREventoAuditoria evento auditoria
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
			evento.setNombre(pREventoAuditoria.getNombre());
			evento.setApellido1(pREventoAuditoria.getApellido1());
			evento.setApellido2(pREventoAuditoria.getApellido2());
			evento.setIdTramite(pREventoAuditoria.getIdTramite());
			evento.setVersionTramite(pREventoAuditoria.getVersionTramite());
			evento.setIdProcedimientoCP(pREventoAuditoria.getIdProcedimientoCP());
			evento.setIdProcedimientoSIA(pREventoAuditoria.getIdProcedimientoSIA());
			evento.setCodigoError(pREventoAuditoria.getCodigoError());
			evento.setDescripcion(pREventoAuditoria.getDescripcion());
			evento.setResultado(pREventoAuditoria.getResultado());
			evento.setTrazaError(pREventoAuditoria.getTrazaError());
			evento.setDescripcionTramite(pREventoAuditoria.getDescripcionTramite());

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
	 * Convierte evento auditoria.
	 *
	 * @param pREventoAuditoria evento auditoria
	 * @return EventoAuditoriaTramitacion
	 */
	private Soporte convierteSoporte(final RSoporte pRSoporte) {
		Soporte soporte = null;

		if (pRSoporte != null) {
			soporte = new Soporte();

			soporte.setCodigo(pRSoporte.getCodigo());
			soporte.setIdTramite(pRSoporte.getIdTramite());
			soporte.setVersion(pRSoporte.getVersion());
			soporte.setFechaCreacion(pRSoporte.getFechaCreacion());
			soporte.setSesionTramitacion(pRSoporte.getSesionTramitacion());
			soporte.setNif(pRSoporte.getNif());
			soporte.setNombre(pRSoporte.getNombre());
			soporte.setTelefono(pRSoporte.getTelefono());
			soporte.setEmail(pRSoporte.getEmail());
			soporte.setHorario(pRSoporte.getHorario());
			soporte.setTipoProblema(pRSoporte.getTipoProblema());
			soporte.setDescripcionProblema(pRSoporte.getDescripcionProblema());
			soporte.setNombreFichero(pRSoporte.getNombreFichero());
			soporte.setDatosFichero(pRSoporte.getDatosFichero());
			soporte.setEstado(pRSoporte.getEstado());
			soporte.setComent(pRSoporte.getComentario());
		}

		return soporte;
	}

	/**
	 * Convierte evento auditoria.
	 *
	 * @param pREventoAuditoria evento auditoria
	 * @return EventoAuditoriaTramitacion
	 */
	private RSoporte convierteRSoporte(final Soporte soporte) {
		RSoporte rsoporte = null;

		if (soporte != null) {
			rsoporte = new RSoporte();

			rsoporte.setCodigo(soporte.getCodigo());
			rsoporte.setIdTramite(soporte.getIdTramite());
			rsoporte.setVersion(soporte.getVersion());
			rsoporte.setFechaCreacion(soporte.getFechaCreacion());
			rsoporte.setSesionTramitacion(soporte.getSesionTramitacion());
			rsoporte.setNif(soporte.getNif());
			rsoporte.setNombre(soporte.getNombre());
			rsoporte.setTelefono(soporte.getTelefono());
			rsoporte.setEmail(soporte.getEmail());
			rsoporte.setHorario(soporte.getHorario());
			rsoporte.setTipoProblema(soporte.getTipoProblema());
			rsoporte.setDescripcionProblema(soporte.getDescripcionProblema());
			rsoporte.setNombreFichero(soporte.getNombreFichero());
			rsoporte.setDatosFichero(soporte.getDatosFichero());
			rsoporte.setEstado(soporte.getEstado());
			rsoporte.setComentario(soporte.getComent());
		}

		return rsoporte;
	}

	/**
	 * Convierte filtro tramite busqueda.
	 *
	 * @param pFiltro filtro
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
	 * @param rClave Clave
	 * @return PerdidaClave
	 */
	private PerdidaClave conviertePerdidaClave(final RPerdidaClave rClave) {
		final PerdidaClave clave = new PerdidaClave();

		clave.setIdSesionTramitacion(rClave.getIdSesionTramitacion());
		clave.setFecha(rClave.getFecha());
		clave.setIdTramite(rClave.getIdTramite());
		clave.setVersionTramite(rClave.getVersionTramite());
		clave.setIdProcedimientoCP(rClave.getIdProcedimientoCP());
		return clave;
	}

	/**
	 * Convierte filtro informacion pagos.
	 *
	 * @param pFiltro filtro
	 * @return filtro pago auditoria
	 */
	private RFiltroPagoAuditoria convierteFiltroInformacionPagos(final FiltroAuditoriaPago pFiltro) {
		RFiltroPagoAuditoria rFiltro = null;

		if (pFiltro != null) {
			rFiltro = new RFiltroPagoAuditoria();
			rFiltro.setListaAreas(pFiltro.getListaAreas());
			rFiltro.setIdSesionTramitacion(pFiltro.getIdSesionTramitacion());
			rFiltro.setNif(pFiltro.getNif());
			rFiltro.setFechaDesde(pFiltro.getFechaDesde());
			rFiltro.setFechaHasta(pFiltro.getFechaHasta());

			if (pFiltro.getTipoAcceso() != null) {
				rFiltro.setAcceso(pFiltro.getTipoAcceso().name());
			}

			rFiltro.setSoloContar(pFiltro.isSoloContar());

			rFiltro.setSortField(pFiltro.getSortField());
			rFiltro.setSortOrder(pFiltro.getSortOrder());
		}

		return rFiltro;

	}

	private PagoAuditoria conviertePago(final RPagoAuditoria pRPagoAuditoria) {
		PagoAuditoria pago = null;

		if (pRPagoAuditoria != null) {

			pago = new PagoAuditoria();

			pago.setIdSesionTramitacion(pRPagoAuditoria.getIdSesionTramitacion());
			pago.setFecha(pRPagoAuditoria.getFecha());
			pago.setIdTramite(pRPagoAuditoria.getIdTramite());
			pago.setVersionTramite(pRPagoAuditoria.getVersionTramite());
			pago.setFichero(pRPagoAuditoria.getFichero());
			pago.setFicheroClave(pRPagoAuditoria.getFicheroClave());
			pago.setCodigoPago(pRPagoAuditoria.getCodigoPago());
			pago.setEstado(pRPagoAuditoria.getEstado());
			pago.setIdentificador(pRPagoAuditoria.getIdentificador());
			pago.setPresentacion(pRPagoAuditoria.getPresentacion());
			pago.setPasarelaId(pRPagoAuditoria.getPasarelaId());
			pago.setImporte(pRPagoAuditoria.getImporte());
			pago.setTasaId(pRPagoAuditoria.getTasaId());
			pago.setLocalizador(pRPagoAuditoria.getLocalizador());
			pago.setFechaPago(pRPagoAuditoria.getFechaPago());
			pago.setPagoEstadoIncorrecto(pRPagoAuditoria.getPagoEstadoIncorrecto());
		}

		return pago;

	}

	private EventoCM convierteEventoCM(final REventoCM pREventoCM) {
		EventoCM evento = null;

		if (pREventoCM != null) {

			evento = new EventoCM();

			evento.setTipoEvento(pREventoCM.getTipoEvento());
			evento.setConcurrencias(pREventoCM.getConcurrencias());
			evento.setPorc(pREventoCM.getPorc());
		}

		return evento;

	}

	private ErroresPorTramiteCM convierteErroresPorTramiteCM(final RErroresPorTramiteCM pEventoCM) {
		ErroresPorTramiteCM rEvento = null;

		if (pEventoCM != null) {

			rEvento = new ErroresPorTramiteCM();

			rEvento.setIdTramite(pEventoCM.getIdTramite());
			rEvento.setVersion(pEventoCM.getVersion());
			rEvento.setNumeroErrores(pEventoCM.getNumeroErrores());
			rEvento.setSesionesIniciadas(pEventoCM.getSesionesIniciadas());
			rEvento.setSesionesFinalizadas(pEventoCM.getSesionesFinalizadas());
			rEvento.setSesionesInacabadas(pEventoCM.getSesionesInacabadas());
			rEvento.setPorcentage(pEventoCM.getPorcentage());
		}

		return rEvento;

	}

	private ResultadoAuditoriaDetallePago convierteDetallePago(final RDetallePagoAuditoria pRDetallePagoAuditoria) {
		ResultadoAuditoriaDetallePago detalle = null;

		if (pRDetallePagoAuditoria != null) {

			detalle = new ResultadoAuditoriaDetallePago();

			if (pRDetallePagoAuditoria.getDatos() != null) {
				final DatosSesionPago datos = new DatosSesionPago();
				datos.setPasarelaId(pRDetallePagoAuditoria.getDatos().getPasarelaId());
				datos.setEntidadId(pRDetallePagoAuditoria.getDatos().getEntidadId());
				datos.setOrganismoId(pRDetallePagoAuditoria.getDatos().getOrganismoId());
				datos.setSimulado(pRDetallePagoAuditoria.getDatos().isSimulado());
				datos.setIdentificadorPago(pRDetallePagoAuditoria.getDatos().getIdentificadorPago());
				datos.setPresentacion(TypePresentacion.fromString(pRDetallePagoAuditoria.getDatos().getPresentacion()));
				datos.setFechaPago(pRDetallePagoAuditoria.getDatos().getFechaPago());
				datos.setLocalizador(pRDetallePagoAuditoria.getDatos().getLocalizador());
				datos.setIdioma(pRDetallePagoAuditoria.getDatos().getIdioma());

				final Persona sujetoPasivo = new Persona(pRDetallePagoAuditoria.getDatos().getSujetoPasivoNif(),
						pRDetallePagoAuditoria.getDatos().getSujetoPasivoNombre());
				datos.setSujetoPasivo(sujetoPasivo);

				datos.setModelo(pRDetallePagoAuditoria.getDatos().getModelo());
				datos.setConcepto(pRDetallePagoAuditoria.getDatos().getConcepto());
				datos.setTasaId(pRDetallePagoAuditoria.getDatos().getTasaId());
				datos.setImporte(pRDetallePagoAuditoria.getDatos().getImporte());
				datos.setDetallePago(pRDetallePagoAuditoria.getDatos().getDetallePago());

				detalle.setDatos(datos);
			}

			if (pRDetallePagoAuditoria.getVerificacion() != null) {
				final VerificacionPago verificacion = new VerificacionPago();

				verificacion.setVerificado(pRDetallePagoAuditoria.getVerificacion().isVerificado());
				verificacion.setPagado(pRDetallePagoAuditoria.getVerificacion().isPagado());
				verificacion.setCodigoError(pRDetallePagoAuditoria.getVerificacion().getCodigoError());
				verificacion.setMensajeError(pRDetallePagoAuditoria.getVerificacion().getMensajeError());
				verificacion.setFechaPago(pRDetallePagoAuditoria.getVerificacion().getFechaPago());
				verificacion.setLocalizador(pRDetallePagoAuditoria.getVerificacion().getLocalizador());
				verificacion.setJustificantePDF(
						Base64.decodeBase64(pRDetallePagoAuditoria.getVerificacion().getJustificantePDF()));

				detalle.setVerificacion(verificacion);
			}

		}

		return detalle;
	}

	private PersistenciaAuditoria conviertePersistencia(final RPersistenciaAuditoria pRPersistenciaAuditoria) {
		PersistenciaAuditoria persistencia = null;

		if (pRPersistenciaAuditoria != null) {
			persistencia = new PersistenciaAuditoria();
			persistencia.setId(pRPersistenciaAuditoria.getId());
			persistencia.setIdSesionTramitacion(pRPersistenciaAuditoria.getIdSesionTramitacion());
			persistencia.setIdTramite(pRPersistenciaAuditoria.getIdTramite());
			persistencia.setVersionTramite(pRPersistenciaAuditoria.getVersionTramite());
			persistencia.setIdProcedimientoCP(pRPersistenciaAuditoria.getIdProcedimientoCP());
			persistencia.setNif(pRPersistenciaAuditoria.getNif());
			persistencia.setNombre(pRPersistenciaAuditoria.getNombre());
			persistencia.setApellido1(pRPersistenciaAuditoria.getApellido1());
			persistencia.setApellido2(pRPersistenciaAuditoria.getApellido2());
			persistencia.setFechaInicio(pRPersistenciaAuditoria.getFechaInicio());
			persistencia.setEstado(TypeEstadoTramite.fromString(pRPersistenciaAuditoria.getEstado()));
			persistencia.setCancelado(pRPersistenciaAuditoria.isCancelado());
			persistencia.setFechaCaducidad(pRPersistenciaAuditoria.getFechaCaducidad());
			persistencia.setPurgar(pRPersistenciaAuditoria.isPurgar());
			persistencia.setFechaPurgado(pRPersistenciaAuditoria.getFechaPurgado());
			persistencia.setPurgado(pRPersistenciaAuditoria.isPurgado());
			persistencia.setDescripcionTramite(pRPersistenciaAuditoria.getDescripcionTramite());
			persistencia.setFechaUltimoAcceso(pRPersistenciaAuditoria.getFechaUltimoAcceso());
			persistencia.setFechaFin(pRPersistenciaAuditoria.getFechaFin());
			persistencia.setPersistente(pRPersistenciaAuditoria.isPersistente());
			persistencia.setUrlInicio(pRPersistenciaAuditoria.getUrlInicio());
		}

		return persistencia;
	}

	private RFiltroPersistenciaAuditoria convierteFiltroPersistencia(final FiltroPersistenciaAuditoria pFiltro) {
		RFiltroPersistenciaAuditoria rFiltro = null;

		if (pFiltro != null) {
			rFiltro = new RFiltroPersistenciaAuditoria();
			rFiltro.setListaAreas(pFiltro.getListaAreas());
			rFiltro.setIdSesionTramitacion(pFiltro.getIdSesionTramitacion());
			rFiltro.setNif(pFiltro.getNif());
			rFiltro.setFechaDesde(pFiltro.getFechaDesde());
			rFiltro.setFechaHasta(pFiltro.getFechaHasta());
			rFiltro.setTipoTramitePersistencia(pFiltro.getTipoTramitePersistencia().toString());
			rFiltro.setIdTramite(pFiltro.getIdTramite());
			rFiltro.setVersionTramite(pFiltro.getVersionTramite());
			rFiltro.setIdProcedimientoCP(pFiltro.getIdProcedimientoCP());
			rFiltro.setIdProcedimientoSIA(pFiltro.getIdProcedimientoSIA());
			rFiltro.setSoloContar(pFiltro.isSoloContar());

			rFiltro.setSortField(pFiltro.getSortField());
			rFiltro.setSortOrder(pFiltro.getSortOrder());
		}

		return rFiltro;
	}

	private FicheroPersistenciaAuditoria convierteFicheroPersistenciaAuditoria(
			final RFicheroPersistenciaAuditoria pRFichero) {
		FicheroPersistenciaAuditoria fichero = null;

		if (pRFichero != null) {

			fichero = new FicheroPersistenciaAuditoria();
			fichero.setIdentificadorPaso(pRFichero.getIdentificadorPaso());
			fichero.setTipoPaso(pRFichero.getTipoPaso());
			fichero.setNombre(pRFichero.getNombre());
			fichero.setCodigo(pRFichero.getCodigo());
			fichero.setClave(pRFichero.getClave());
			fichero.setTipo(TypeDocumentoPersistencia.fromString(pRFichero.getTipo()));

		}

		return fichero;
	}

	private FicheroAuditoria convierteAuditoriaFichero(final RFichero pRFichero) {
		FicheroAuditoria fichero = null;

		if (pRFichero != null) {

			fichero = new FicheroAuditoria();

			fichero.setContenido(Base64.decodeBase64(pRFichero.getContenido()));
			fichero.setNombre(pRFichero.getNombre());
		}

		return fichero;
	}

}
