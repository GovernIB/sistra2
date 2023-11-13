package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2;

import es.caib.sistra2.commons.plugins.autenticacion.api.AutenticacionPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.*;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo.*;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Plugin catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class CatalogoProcedimientosRolsacPlugin extends AbstractPluginProperties
		implements ICatalogoProcedimientosPlugin {

	/** El literal de idioma que no para de estar repetido. **/
	private static final String TAMANYO_MAXIMO = "200";
	private static final String LITERAL_IDIOMA = "lang";
	private static final String LITERAL_ERROR_NO_CONECTAR = "No se conecta correctamente a ROLSAC";
	private static final String LITERAL_FILTRO = "filtro";
	private static final String LITERAL_FILTRO_PAGINACION = "filtroPaginacion";

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/** Id plataforma Sistra2 definido en Rolsac. */
	private static final String PROPIEDAD_IDENTIFICADOR_SISTRA2 = "identificadorPlataformaSistra2";

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "rolsac2.";

	/** Literales campos LOPD. */
	private Properties literalesCamposLOPD;

/**
 *  función que retorna json con las propiedades que permite el plugin.
 *  propiedades permitidas:
 *
 * 		"url": "url de acceso al api ROLSAC2"
 * 		"usr": "usuario de acceso al api de rolsac2"
 * 		"pwd": "pw de acceso al api de rolsac2"
 * 		"identificadorPlataformaSistra2": "identificado de la plataforma SISTRA2 en ROLSAC2"
 * 		"reintentos": "numero de reintentos"
 * 		"urlSeucaibArchivos": "URL de descarga de archivos, acepta estos placeholder {{IDIOMA}} y {{IDARCHIVO}}"
*/
	private String getJsonPropiedades(){
        return "{\"url\": \"url de acceso al api ROLSAC2\"" +
				",\"usr\":\"usuario de acceso al api de rolsac2\"" +
				",\"pwd\":\"pw de acceso al api de rolsac2\"" +
				",\"identificadorPlataformaSistra2\": \"identificado de la plataforma SISTRA2 en ROLSAC2\"" +
				",\"reintentos\":\"numero de reintentos\"" +
				",\"urlSeucaibArchivos\":\"URL de descarga de archivos, acepta estos placeholder {{IDIOMA}} y {{IDARCHIVO}}\"" +
				"}";
	}


	public CatalogoProcedimientosRolsacPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);

		// Literales campos LOPD
		try {
			literalesCamposLOPD = new Properties();
			literalesCamposLOPD.load(CatalogoProcedimientosRolsacPlugin.class.getResourceAsStream("lopd.properties"));
		} catch (final IOException e) {
			throw new RuntimeException("Error al cargar fichero literales LOPD: " + e.getMessage(), e);
		}
	}

	@Override
	public DefinicionTramiteCP obtenerDefinicionTramite(final String idTramiteCP, final boolean servicio,
			final String idioma) throws CatalogoPluginException {

		DefinicionTramiteCP definicion;
		if (servicio) {
			definicion = obtenerDefinicionTramiteServicio(idTramiteCP, idioma, false);
		} else {
			definicion = obtenerDefinicionTramiteProcedimiento(idTramiteCP, idioma, false);
		}
		return definicion;
	}

	/**
	 * Método que calcula la definición de trámite de un servicio.
	 *
	 * @param idServicioCP
	 *                         ID Servicio
	 * @param idioma
	 *                         Idioma (es/ca/en)
	 * @return
	 * @throws CatalogoPluginException
	 */
	private DefinicionTramiteCP obtenerDefinicionTramiteServicio(final String idServicioCP, final String idioma, final boolean modoResumen)
			throws CatalogoPluginException {

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		//map.add(LITERAL_TAMANYO, TAMANYO_MAXIMO);
		//map.add(LITERAL_FILTRO_PAGINACION, "{\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}");
		//map.add(LITERAL_IDIOMA, idioma);
		map.add(LITERAL_FILTRO, "{\"idPlataforma\" : \"" + getIdentificadorPlafaformaSistra2() + "\", \"filtroPaginacion\" : {\"page\" : \"0\"}}");

		final RServicioRolsac servicioRolsac = getRServicioRolsac(idServicioCP, map, idioma)[0];

		// Crea definición trámite servicio
		final DefinicionTramiteCP dt = crearDefinicionTramiteServicio(idioma, servicioRolsac, modoResumen);

		return dt;
	}

	/**
	 * Obtiene valor identificador S2 definido en Rolsac.
	 *
	 * @return valor identificador S2 definido en Rolsac.
	 * @throws CatalogoPluginException
	 */
	private String getIdentificadorPlafaformaSistra2() throws CatalogoPluginException {
		return getPropiedad(PROPIEDAD_IDENTIFICADOR_SISTRA2);
	}

	/**
	 * Obtiene el servicio de rolsac y realiza los intentos que haga falta.
	 *
	 * @param idServicioCP
	 * @param idioma
	 * @return
	 * @throws CatalogoPluginException
	 */
	private RServicioRolsac[] getRServicioRolsac(final String idServicioCP, final MultiValueMap<String, String> map, final String idioma)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Obtener tramite.
		//final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		final HttpEntity<String> request = new HttpEntity<>(map.getFirst(LITERAL_FILTRO), headers);
		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;

		while (intentos <= intentosMax) {

			try {
				final ResponseEntity<RRespuestaServicios> responseTramite;

				String url = getPropiedad("url");
				if (idServicioCP == null || idServicioCP.isEmpty()) {
					responseTramite = restTemplate.postForEntity(url + "/servicios?" + LITERAL_IDIOMA + "=" + idioma, request,
							RRespuestaServicios.class);
				} else {
					responseTramite = restTemplate.postForEntity(url + "/servicios/" + idServicioCP + "?" + LITERAL_IDIOMA + "=" + idioma,
							request, RRespuestaServicios.class);
				}
				final RServicioRolsac[] serviciosRolsac = responseTramite.getBody().getResultado();

				// Si se ha pasado el idServicio y está vacío o nulo, entonces dar error
				if (idServicioCP != null && !idServicioCP.isEmpty()
						&& (serviciosRolsac == null || serviciosRolsac.length == 0)) {
					throw new CatalogoPluginException("No hay servicio");
				}

				return serviciosRolsac;
			} catch (final Exception e) {
				// No hacemos nada
				log.warn(getWarning(intentos) + ": " + e.getMessage());
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);
	}

	/**
	 * Método privado que calcula la definicion de trámite de un procedimiento.
	 *
	 * @param idTramiteCP
	 *                        ID Trámite
	 * @param idioma
	 *                        Idioma (es/ca/en)
	 * @return
	 * @throws CatalogoPluginException
	 */

	private DefinicionTramiteCP obtenerDefinicionTramiteProcedimiento(final String idTramiteCP, final String idioma, final boolean modoResumen)
			throws CatalogoPluginException {

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		//map.add(LITERAL_FILTRO_PAGINACION, "{\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}");
		//map.add(LITERAL_IDIOMA, idioma);
		map.add(LITERAL_FILTRO, "{\"idPlataforma\" : \"" + getIdentificadorPlafaformaSistra2() + "\", \"filtroPaginacion\" : {\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}}");

		// Obtener procedimiento.
		final RTramiteRolsac[] tramitesRolsac = getRTramiteRolsac(idTramiteCP, map, idioma);
		final RTramiteRolsac tramiteRolsac = tramitesRolsac[0];

		final RProcedimientoRolsac procRolsac = getRProcedimientoRolsac(
				tramiteRolsac.getLink_procedimiento().getCodigo(), idioma);

		final DefinicionTramiteCP dt = obtenerDefinicionTramiteProcedimiento(procRolsac, tramiteRolsac, idioma, modoResumen);
		return dt;

	}

	protected DefinicionTramiteCP obtenerDefinicionTramiteProcedimiento(final RProcedimientoRolsac procRolsac,
			final RTramiteRolsac tramiteRolsac, final String idioma, final boolean modoResumen) throws CatalogoPluginException {

		if (modoResumen) {
			final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
			dp.setIdentificador(tramiteRolsac.getLink_procedimiento().getCodigo());
			dp.setDescripcion(procRolsac.getNombreProcedimientoWorkFlow());
			dp.setIdProcedimientoSIA(procRolsac.getCodigoSIA() != null ? procRolsac.getCodigoSIA().toString() : null);
			dp.setValidacion((procRolsac.getEstado().equals(TypeProcedimientoEstado.PUBLICADO)
									&& procRolsac.getWorkflow().equals(TypeProcedimientoWorkflow.DEFINITIVO)) ? 1 : 0);
			final DefinicionTramiteCP dt = new DefinicionTramiteCP();
			dt.setIdentificador(String.valueOf(tramiteRolsac.getCodigo()));
			dt.setDescripcion(tramiteRolsac.getNombre());
			dt.setVigente(esVigente(procRolsac, tramiteRolsac));
			dt.setProcedimiento(dp);
			return dt;
		}

		// Codigo DIR3 responsable procedimiento
		String dir3organoResponsable = "";
		if (procRolsac.getLinkUnidadAdministrativaResponsable() == null) {
			log.error("El link de unidad administrativa es nulo con el proc: " + procRolsac.getCodigo());
		} else {
			dir3organoResponsable = getCodigoDir3UA(procRolsac.getLinkUnidadAdministrativaResponsable().getCodigo());
		}

		// Codigo DIR3 destintario tramite
		String dir3organoDestinatario = "";

		if (tramiteRolsac.getLink_unidadAdministrativa() == null) {
			log.error("El link de organo competente es nulo con el proc: " + procRolsac.getCodigo());
		} else {
			dir3organoDestinatario = getCodigoDir3UA(tramiteRolsac.getLink_unidadAdministrativa().getCodigo());
		}


		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador(tramiteRolsac.getLink_procedimiento().getCodigo());
		dp.setDescripcion(procRolsac.getNombreProcedimientoWorkFlow());
		dp.setIdProcedimientoSIA(procRolsac.getCodigoSIA().toString());
		dp.setOrganoResponsableDir3(dir3organoResponsable);
		dp.setValidacion((procRolsac.getEstado().equals(TypeProcedimientoEstado.PUBLICADO)
								&& procRolsac.getWorkflow().equals(TypeProcedimientoWorkflow.DEFINITIVO)) ? 1 : 0);
		dp.setServicio(false);
		dp.setLopd(generarInfoLOPDProcedimiento(procRolsac, idioma));

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(String.valueOf(tramiteRolsac.getCodigo()));
		dt.setDescripcion(tramiteRolsac.getNombre());
		dt.setProcedimiento(dp);
		dt.setVigente(esVigente(procRolsac, tramiteRolsac));
		dt.setOrganoDestinoDir3(dir3organoDestinatario);
		dt.setTelematico(tramiteRolsac.isTramitElectronica());
		dt.setPlazoInicio(tramiteRolsac.getFechaInicio() != null ? tramiteRolsac.getFechaInicio() : null);
		dt.setPlazoFin(tramiteRolsac.getFechaCierre() != null ? tramiteRolsac.getFechaCierre() : null);
		dt.setEmailSoporte(procRolsac.getResponsableEmail());

		final RTipoTramitacion tipoTramitacionRolsac = getRTipoTramitacionRolsac(tramiteRolsac.getLink_tipoTramitacion().getCodigo(), idioma)[0];
		final RPlatTramitElectronica platTramitRolsac = getRPlatTramitElectronicaRolsac(tipoTramitacionRolsac.getLink_codPlatTramitacion().getCodigo(), idioma)[0];
		if (dt.isTelematico() && platTramitRolsac.getIdentificador() != null
				&& getIdentificadorPlafaformaSistra2().equals(platTramitRolsac.getIdentificador())) {
			final DefinicionTramiteTelematico definicion = new DefinicionTramiteTelematico();
			definicion.setTramiteIdentificador(tipoTramitacionRolsac.getTramiteId());
			definicion.setTramiteVersion(tipoTramitacionRolsac.getTramiteVersion());
			dt.setTramiteTelematico(definicion);

		}
		return dt;
	}

	/**
	 * Obtine el procedimiento rolsac con reintentos.
	 *
	 * @param codigoProc
	 * @param idioma
	 * @return
	 * @throws CatalogoPluginException
	 */
	private RProcedimientoRolsac getRProcedimientoRolsac(final String codigoProc, final String idioma)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

//		final HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);

		// Obtener tramite.
//		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//		map.add(LITERAL_FILTRO, "{\"filtroPaginacion\" : {\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}}");
		//map.add(LITERAL_IDIOMA, idioma);

//		final HttpEntity<String> requestProc = new HttpEntity<>(map.getFirst(LITERAL_FILTRO), headers);

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				String url = getPropiedad("url");

				final ResponseEntity<RRespuestaProcedimientos> responseProc = restTemplate.postForEntity(
						url + "/procedimientos/" + codigoProc + "?" + LITERAL_IDIOMA + "=" + idioma, null,
						RRespuestaProcedimientos.class);
				if (responseProc == null || responseProc.getBody() == null
						|| responseProc.getBody().getResultado() == null) {
					throw new CatalogoPluginException("El procedimiento no existe.");
				}
				final RProcedimientoRolsac[] procedimientosRolsac = responseProc.getBody().getResultado();
				if (procedimientosRolsac == null || procedimientosRolsac.length == 0) {
					throw new CatalogoPluginException("No hay procedimiento");
				}

				return procedimientosRolsac[0];
			} catch (final Exception e) {
				// No hacemos nada
				log.warn(getWarning(intentos) + ": " + e.getMessage());
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);
	}

	/**
	 * Obtine el procedimiento rolsac con reintentos.
	 *
	 * @param codigoProc
	 * @param idioma
	 * @return
	 * @throws CatalogoPluginException
	 */
	private Map<Long, RProcedimientoRolsac> getRProcedimientoRolsac(final List<Long> codigoProcs, final String idioma)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		String codigos = "";
		for(Long codProc : codigoProcs) {
			codigos += codProc+",";
		}
		if (codigos.endsWith(",")) {
			codigos = codigos.substring(0, codigos.length()-1);
		}
		map.add(LITERAL_FILTRO, "{\"codigos\":\"" + codigos + "\", \"filtroPaginacion\" : {\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}}");
		final HttpEntity<String> request = new HttpEntity<>(map.getFirst(LITERAL_FILTRO), headers);


		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				final ResponseEntity<RRespuestaProcedimientos> responseTramite;

				String url = getPropiedad("url");
					responseTramite = restTemplate.postForEntity(url + "/procedimientos?" + LITERAL_IDIOMA + "=" + idioma, request,
							RRespuestaProcedimientos.class);


				final RProcedimientoRolsac[] procedimientosRolsac = responseTramite.getBody().getResultado();
				Map<Long, RProcedimientoRolsac> respuesta = new HashMap<>();
				for(RProcedimientoRolsac proc : procedimientosRolsac ) {
					respuesta.put(proc.getCodigo(), proc);
				}
				return respuesta;

			} catch (final Exception e) {
				// No hacemos nada
				log.warn(getWarning(intentos) + ": " + e.getMessage());
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);

	}

	/**
	 * Obtiene el tramite con reintentos.
	 *
	 * @return
	 * @throws CatalogoPluginException
	 */
	private RProcedimientoDocumento[] getRProcedimientoDocumento(final String codigoWorkflow) throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

//		final HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				final ResponseEntity<RRespuestaProcedimientoDocumento> responseDocumento;
				String url = getPropiedad("url");
				responseDocumento = restTemplate.postForEntity(url + "/procedimientos/documentos/" + codigoWorkflow,
						null, RRespuestaProcedimientoDocumento.class);

				final RProcedimientoDocumento[] documentosRolsac = responseDocumento.getBody().getResultado();

				if (documentosRolsac == null || documentosRolsac.length == 0) {
					throw new CatalogoPluginException("No existe archivo");
				}
				return documentosRolsac;

			} catch (final Exception e) {
				// No hacemos nada
				log.warn(getWarning(intentos) + ": " + e.getMessage());
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);

	}

	/**
	 * Obtiene el tramite con reintentos.
	 *
	 * @param idTramiteCP
	 * @param map
	 * @return
	 * @throws CatalogoPluginException
	 */
	private RTramiteRolsac[] getRTramiteRolsac(final String idTramiteCP, final MultiValueMap<String, String> map, final String idioma)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		//final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		final HttpEntity<String> request = new HttpEntity<>(map.getFirst(LITERAL_FILTRO), headers);


		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				final ResponseEntity<RRespuestaTramites> responseTramite;

				String url = getPropiedad("url");
				if (idTramiteCP == null || idTramiteCP.isEmpty()) {
					responseTramite = restTemplate.postForEntity(url + "/tramites?" + LITERAL_IDIOMA + "=" + idioma, request,
							RRespuestaTramites.class);
				} else {
					responseTramite = restTemplate.postForEntity(url + "/tramites/" + idTramiteCP + "?" + LITERAL_IDIOMA + "=" + idioma,
							request, RRespuestaTramites.class);
				}

				final RTramiteRolsac[] tramitesRolsac = responseTramite.getBody().getResultado();

				if (idTramiteCP != null && !idTramiteCP.isEmpty()
						&& (tramitesRolsac == null || tramitesRolsac.length == 0)) {
					throw new CatalogoPluginException("No hay tramites");
				}
				return tramitesRolsac;

			} catch (final Exception e) {
				// No hacemos nada
				log.warn(getWarning(intentos) + ": " + e.getMessage());
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);

	}

	/**
	 * Obtiene el código DIR3 de la UA
	 *
	 * @return
	 * @throws CatalogoPluginException
	 * @throws RestClientException
	 */
	private String getCodigoDir3UA(final String codigoDIR3) throws CatalogoPluginException {

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));
		final HttpEntity<String> requestProc = new HttpEntity<>(map.getFirst(LITERAL_FILTRO), headers);

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}

		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				String url = getPropiedad("url");

				final ResponseEntity<RRespuestaSimple> responseCodDIR3 = restTemplate.postForEntity(
						url + "/unidades_administrativas/codigoDir3/" + codigoDIR3, requestProc,
						RRespuestaSimple.class);

				if (responseCodDIR3 == null || responseCodDIR3.getBody() == null
						|| responseCodDIR3.getBody().getResultado() == null) {
					throw new CatalogoPluginException("LA UA no existe.");
				}
				return responseCodDIR3.getBody().getResultado();

			} catch (final Exception e) {
				// No hacemos nada
				log.warn(getWarning(intentos) + ": " + e.getMessage());
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);

	}

	/**
	 * Devuelve el mensaje de warning
	 *
	 * @param intentos
	 * @return
	 */
	private String getWarning(final Integer intentos) {
		return "Error al conectar a Rolsac (intento " + intentos + ")";
	}

	/**
	 * Indica si es vigente el trámite.
	 *
	 * @param procedimiento
	 * @param tramite
	 * @return
	 */
	private boolean esVigente(final RProcedimientoRolsac procedimiento, final RTramiteRolsac tramite) {
		final Calendar ahora = Calendar.getInstance();
		// Procedimiento caducado
		final boolean procedimientoNoCaducado = (procedimiento.getFechaCaducidad() == null
				|| procedimiento.getFechaCaducidad().after(ahora));
		// Procedimiento publicado
		final boolean procedimientoPublicado = (procedimiento.getFechaPublicacion() == null
				|| procedimiento.getFechaPublicacion().before(ahora));
		// Procedimiento público (Ya no es necesario el estado, da igual si es publico/resrva/interna)
		//final boolean procedimientoPublico = procedimiento.getValidacion() == null
		//		|| "1".equals(procedimiento.getValidacion().toString());
		// Tramite abierto
		final boolean tramitePlazoAbierto = (tramite.getFechaInicio() == null || tramite.getFechaInicio().before(ahora.getTime()))
				&& (tramite.getFechaCierre() == null || tramite.getFechaCierre().after(ahora.getTime()));
		// Trámite publicado
		final boolean tramitePublicado = (tramite.getFechaPublicacion() == null
				|| tramite.getFechaPublicacion().before(ahora.getTime()));
		// Vigente
		return procedimientoNoCaducado && procedimientoPublicado && tramitePlazoAbierto
				&& tramitePublicado;
	}

	/**
	 * Indica si es vigente servicio.
	 *
	 * @param servicio
	 * @return
	 */
	private boolean esVigente(final RServicioRolsac servicio) {
		final Calendar ahora = Calendar.getInstance();
		// Servicio este publicado
		final boolean publicado = (servicio.getFechaPublicacion() == null
				|| servicio.getFechaPublicacion().before(ahora))
				&& (servicio.getFechaCaducidad() == null || servicio.getFechaCaducidad().after(ahora));
		// Servicio es público  (Ya no es necesario el estado, da igual si es publico/resrva/interna)
		//final boolean publico = servicio.getValidacion() == null || "1".equals(servicio.getValidacion().toString());
		// Vigente si público y publicado
		return  publicado;
	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 * @throws AutenticacionPluginException
	 */
	private String getPropiedad(final String propiedad) throws CatalogoPluginException {
		final String res = getProperty(
				CATALOGO_PROCEDIMIENTOS_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new CatalogoPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
		}
		return res;
	}

	/**
	 * Obtiene propiedad de intentos, por defecto 3.
	 *
	 * @return
	 */
	private Integer getPropiedadIntentos() {
		Integer intentos;
		try {
			final String nIntentos = getPropiedad("reintentos");
			intentos = Integer.valueOf(nIntentos);
		} catch (final Exception e) {
			intentos = 3;
		}
		return intentos;
	}

	@Override
	public List<DefinicionTramiteCP> obtenerTramites(final String idTramite, final Integer version, final String idioma)
			throws CatalogoPluginException {

		final List<DefinicionTramiteCP> trams = new ArrayList<>();
		trams.addAll(obtenerTramitesServicios(idTramite, version, idioma, true));
		trams.addAll(obtenerTramitesProcedimientos(idTramite, version, idioma, true));

		return trams;
	}

	/**
	 * Devuelve los trámites de los servicios
	 *
	 * @param idTramite
	 * @param version
	 * @param idioma
	 * @return
	 * @throws CatalogoPluginException
	 */
	private List<DefinicionTramiteCP> obtenerTramitesServicios(final String idTramite, final Integer version,
			final String idioma, final boolean modoResumen) throws CatalogoPluginException {

		final List<DefinicionTramiteCP> res = new ArrayList<>();

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		//map.add(LITERAL_FILTRO_PAGINACION, "{\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}");
		//map.add(LITERAL_IDIOMA, idioma);
		//String vigentes = "";
		//if (soloVigentes) {
		//	vigentes = " \"vigente\":\"1\", ";
		//}
		if (version == null) {
			map.add(LITERAL_FILTRO, "{\"idTramite\":\"" + idTramite + "\", \"idPlataforma\" : \""
					+ getIdentificadorPlafaformaSistra2() + "\", \"filtroPaginacion\" : {\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}}");
		} else {
			map.add(LITERAL_FILTRO,
					"{\"idTramite\":\"" + idTramite + "\", \"version\" : \"" + version
							+ "\", \"idPlataforma\" : \"" + getIdentificadorPlafaformaSistra2()
								+ "\", \"filtroPaginacion\" : {\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}}");
		}

		final RServicioRolsac[] serviciosRolsac = getRServicioRolsac("", map, idioma);

		if (serviciosRolsac != null) {
			for (final RServicioRolsac servicioRolsac : serviciosRolsac) {

				// Crea definición trámite servicio
				final DefinicionTramiteCP dt = crearDefinicionTramiteServicio(idioma, servicioRolsac, modoResumen);

				res.add(dt);
			}
		}

		return res;

	}

	/**
	 * Devuelve los trámites de los procedimientos.
	 *
	 * @param idTramite
	 * @param version
	 * @param idioma
	 * @return
	 * @throws CatalogoPluginException
	 */
	private List<DefinicionTramiteCP> obtenerTramitesProcedimientos(final String idTramite, final Integer version,
			final String idioma, final boolean resumen) throws CatalogoPluginException {

		final List<DefinicionTramiteCP> res = new ArrayList<>();

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		if (version == null) {
			map.add(LITERAL_FILTRO, "{\"idTramite\":\"" + idTramite + "\", \"idPlataforma\" : \""
					+ getIdentificadorPlafaformaSistra2() + "\", \"filtroPaginacion\" : {\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}}");
		} else {
			map.add(LITERAL_FILTRO,
					"{\"idTramite\":\"" + idTramite + "\",\"version\" : \"" + version
							+ "\", \"idPlataforma\" : \"" + getIdentificadorPlafaformaSistra2()
							+ "\", \"filtroPaginacion\" : {\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}}");
		}

		final RTramiteRolsac[] tramitesRolsac = getRTramiteRolsac("", map, idioma);
		if (tramitesRolsac != null) {

			List<Long> codigoProcs = getCodigoProcedimientos(tramitesRolsac);
			Map<Long, RProcedimientoRolsac> procedimientos = getRProcedimientoRolsac(codigoProcs, idioma);
			if (procedimientos != null) {
				for(RTramiteRolsac tramiteRolsac : tramitesRolsac) {
					if (procedimientos.get(Long.valueOf(tramiteRolsac.getLink_procedimiento().getCodigo())) != null) {
						final DefinicionTramiteCP dt = obtenerDefinicionTramiteProcedimiento(procedimientos.get(Long.valueOf(tramiteRolsac.getLink_procedimiento().getCodigo())), tramiteRolsac, idioma, resumen);
						res.add(dt);
					}
				}
			}
		}

		return res;

	}

	private List<Long> getCodigoProcedimientos(RTramiteRolsac[] tramitesRolsac) {
		List<Long> codigos = new ArrayList<>();
		if (tramitesRolsac != null) {
			for(RTramiteRolsac rtramite : tramitesRolsac) {
				codigos.add(Long.valueOf(rtramite.getLink_procedimiento().getCodigo()));
			}
		}
		return codigos;
	}


	/**
	 * Obtiene el tipo de tramitación con reintentos.
	 *
	 * @param codTipoTramiteCP
	 * @return
	 * @throws CatalogoPluginException
	 */
	private RTipoTramitacion[] getRTipoTramitacionRolsac(final String codTipoTramiteCP, final String idioma)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}

		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				String url = getPropiedad("url");

				final ResponseEntity<RRespuestaTipoTramitacion> responseProc = restTemplate.postForEntity(
						url + "/tipos_tramitacion/" + codTipoTramiteCP + "?" + LITERAL_IDIOMA + "=" + idioma, null,
						RRespuestaTipoTramitacion.class);
				if (responseProc == null || responseProc.getBody() == null
						|| responseProc.getBody().getResultado() == null) {
					throw new CatalogoPluginException("El tipo de tramitación no existe.");
				}

				return responseProc.getBody().getResultado();

			} catch (final Exception e) {
				// No hacemos nada
				log.warn(getWarning(intentos) + ": " + e.getMessage());
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);
	}

	/**
	 * Obtiene la plataforma de tramitación electrónica con reintentos.
	 *
	 * @param codPlatTramitCP
	 * @return
	 * @throws CatalogoPluginException
	 */
	private RPlatTramitElectronica[] getRPlatTramitElectronicaRolsac(final String codPlatTramitCP, final String idioma)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}

		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				String url = getPropiedad("url");

				final ResponseEntity<RRespuestaPlatTramitElectronica> responseProc = restTemplate.postForEntity(
						url + "/plataformas/" + codPlatTramitCP + "?" + LITERAL_IDIOMA + "=" + idioma, null,
						RRespuestaPlatTramitElectronica.class);
				if (responseProc == null || responseProc.getBody() == null
						|| responseProc.getBody().getResultado() == null) {
					throw new CatalogoPluginException("La plataforma de tramitación electrónica no existe.");
				}

				return responseProc.getBody().getResultado();

			} catch (final Exception e) {
				// No hacemos nada
				log.warn(getWarning(intentos) + ": " + e.getMessage());
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);
	}



	@Override
	public ArchivoCP descargarArchivo(final String referenciaArchivo) throws CatalogoPluginException {

		// NO IMPLEMENTAMOS DESCARGA, DIRECTAMENTE DEVOLVEMOS ENLACE A SEUCAIB
		if (true) {
			throw new CatalogoPluginException("NO IMPLEMENTADO. SE DEVUELVE MEDIANTE ENLACE A SEUCAIB.");
		}

		// Llamada a Rolsac para obtener props archivo
		// NO PARECE QUE HACE FALTA, SE PUEDE OBTENER FILENAME DIRECTAMENTE DEL SERVLET
		final RProcedimientoDocumento[] archivos = getRProcedimientoDocumento(referenciaArchivo);

		// Llamada a servlet para descargar archivo
		// "/sacws-api/arxiu/apiArxiuServlet?id=";
		// ...

		return null;

	}

	/**
	 * Genera info lopd procedimiento.
	 *
	 * @param procRolsac
	 *                       procedimiento rolsac
	 * @param idioma
	 *                       idioma
	 * @return lopd
	 * @throws CatalogoPluginException
	 */
	protected DefinicionLOPD generarInfoLOPDProcedimiento(final RProcedimientoRolsac procRolsac, final String idioma)
			throws CatalogoPluginException {
		DefinicionLOPD lopd = null;

			lopd = new DefinicionLOPD();
			// - Cabecera
			lopd.setTextoCabecera(procRolsac.getLopdCabecera());
			// - Campos
			final List<CampoLOPD> campos = new ArrayList<>();
			// - Responsable
			if (StringUtils.isNotBlank(procRolsac.getLopdResponsable())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("responsable", idioma));
				c.setDescripcion(procRolsac.getLopdResponsable());
				campos.add(c);
			}
			// - Finalidad
			if (StringUtils.isNotBlank(procRolsac.getLopdFinalidad())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("finalidad", idioma));
				c.setDescripcion(procRolsac.getLopdFinalidad());
				campos.add(c);
			}
			// - Legitimación
			if (procRolsac.getLopdLegitimacion() != null
					&& StringUtils.isNotBlank(procRolsac.getLopdLegitimacion().getDescripcion())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("legitimacion", idioma));
				c.setDescripcion(procRolsac.getLopdLegitimacion().getDescripcion());
				campos.add(c);
			}
			// - Destinatario
			if (StringUtils.isNotBlank(procRolsac.getLopdDestinatario())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("destinatario", idioma));
				c.setDescripcion(procRolsac.getLopdDestinatario());
				campos.add(c);
			}
			// - Derechos
			if (StringUtils.isNotBlank(procRolsac.getLopdDerechos())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("derechos", idioma));
				c.setDescripcion(procRolsac.getLopdDerechos());
				campos.add(c);
			}
			// - InfoAdicional (archivo)
			if (procRolsac.getLinkLopdInfoAdicional() != null
					&& procRolsac.getLinkLopdInfoAdicional().getCodigo() != null) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("infoAdicional", idioma));
				c.setDescripcion(obtenDescripcionCampoLOPD("infoAdicional", idioma));
				// Devolvemos enlace seucaib
				// c.setReferenciaArchivo(procRolsac.getLink_lopdInfoAdicional().getCodigo());
				/*final String urlSeucaibDescarga = getPropiedad("urlSeucaib") + "/" + ("es".equals(idioma) ? "es" : "ca")
						+ "/arxiuServlet?id=" + procRolsac.getLinkLopdInfoAdicional().getCodigo();*/
				c.setEnlace(getUrlDescarga(idioma,procRolsac.getLinkLopdInfoAdicional().getCodigo()));
				c.setEnlace(getUrlDescarga(idioma,procRolsac.getLinkLopdInfoAdicional().getCodigo()));
				campos.add(c);
			}

			lopd.setCampos(campos);

		return lopd;
	}

	/**
	 * Genera info lopd servicio.
	 *
	 * @param procRolsac
	 *                       procedimiento rolsac
	 * @param idioma
	 *                       idioma
	 * @return lopd
	 * @throws CatalogoPluginException
	 */
	protected DefinicionLOPD generarInfoLOPDServicio(final RServicioRolsac procRolsac, final String idioma)
			throws CatalogoPluginException {
		DefinicionLOPD lopd = null;
		if (procRolsac.isActivoLOPD()) {
			lopd = new DefinicionLOPD();
			// - Cabecera
			lopd.setTextoCabecera(procRolsac.getLopdCabecera());
			// - Campos
			final List<CampoLOPD> campos = new ArrayList<>();
			// - Responsable
			if (StringUtils.isNotBlank(procRolsac.getLopdResponsable())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("responsable", idioma));
				c.setDescripcion(procRolsac.getLopdResponsable());
				campos.add(c);
			}
			// - Finalidad
			if (StringUtils.isNotBlank(procRolsac.getLopdFinalidad())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("finalidad", idioma));
				c.setDescripcion(procRolsac.getLopdFinalidad());
				campos.add(c);
			}
			// - Legitimación
			if (procRolsac.getLopdLegitimacion() != null
					&& StringUtils.isNotBlank(procRolsac.getLopdLegitimacion().getDescripcion())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("legitimacion", idioma));
				c.setDescripcion(procRolsac.getLopdLegitimacion().getDescripcion());
				campos.add(c);
			}
			// - Destinatario
			if (StringUtils.isNotBlank(procRolsac.getLopdDestinatario())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("destinatario", idioma));
				c.setDescripcion(procRolsac.getLopdDestinatario());
				campos.add(c);
			}
			// - Derechos
			if (StringUtils.isNotBlank(procRolsac.getLopdDerechos())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("derechos", idioma));
				c.setDescripcion(procRolsac.getLopdDerechos());
				campos.add(c);
			}
			// - InfoAdicional (archivo)

			// obtenemos código de archivo
			if (procRolsac.getLinkLopdInfoAdicional() != null
					&& procRolsac.getLinkLopdInfoAdicional().getCodigo() != null) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("infoAdicional", idioma));
				c.setDescripcion(obtenDescripcionCampoLOPD("infoAdicional", idioma));
				// Devolvemos enlace seucaib
				// c.setReferenciaArchivo(procRolsac.getLink_lopdInfoAdicional().getCodigo());
				/*	final String urlSeucaibDescarga = getPropiedad("urlSeucaib") + "/" + ("es".equals(idioma) ? "es" : "ca")
						+ "/arxiuServlet?id=" + procRolsac.getLinkLopdInfoAdicional().getCodigo();*/
				c.setEnlace(getUrlDescarga(idioma,procRolsac.getLinkLopdInfoAdicional().getCodigo()));
				campos.add(c);
			}

			lopd.setCampos(campos);
		}
		return lopd;
	}

	private String getUrlDescarga(String idioma, String idFichero) throws CatalogoPluginException {
		String res="";
		res = getPropiedad("urlSeucaibArchivos").replace ("{{IDIOMA}}","es".equals(idioma) ? "es" : "ca").replace(
				"{{IDARCHIVO}}",idFichero);
		return res;
	}

	/**
	 * Obtiene titulo campo LOPD.
	 *
	 * @param campo
	 *                   campo
	 * @param idioma
	 *                   idioma
	 * @return titulo
	 */
	private String obtenTituloCampoLOPD(final String campo, final String idioma) {
		return literalesCamposLOPD.getProperty("titulo." + campo + "." + idioma);
	}

	/**
	 * Obtiene texto campo LOPD.
	 *
	 * @param campo
	 *                   campo
	 * @param idioma
	 *                   idioma
	 * @return texto
	 */
	private String obtenDescripcionCampoLOPD(final String campo, final String idioma) {
		return literalesCamposLOPD.getProperty("texto." + campo + "." + idioma);
	}

	protected DefinicionTramiteCP crearDefinicionTramiteServicio(final String idioma,
			final RServicioRolsac servicioRolsac, final boolean modoResumen) throws CatalogoPluginException {

		if (modoResumen) {
			final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
			dp.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
			dp.setDescripcion(servicioRolsac.getNombre());
			dp.setIdProcedimientoSIA(servicioRolsac.getCodigoSIA());
			dp.setServicio(true);
			//dp.setValidacion(servicioRolsac.getValidacion());
			dp.setValidacion((servicioRolsac.getEstado().equals(TypeProcedimientoEstado.PUBLICADO.toString())
									&& servicioRolsac.getWorkflow().equals(TypeProcedimientoWorkflow.DEFINITIVO.getValor())) ? 1 : 0);

			final DefinicionTramiteCP dt = new DefinicionTramiteCP();
			dt.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
			dt.setDescripcion(servicioRolsac.getNombre());
			dt.setVigente(esVigente(servicioRolsac));
			dt.setProcedimiento(dp);
			return dt;
		}

		// Codigo DIR3 responsable procedimiento
		String dir3organoInstructor = "";
		dir3organoInstructor = ""+ servicioRolsac.getUaInstructor();
		//TODO: pendiente revisar cuando los servicios tengan link.
//		if (servicioRolsac.getLink_organoInstructor() == null) {
//			log.error("El link de servicio de unidad administrativa es nulo con el serv: " + servicioRolsac.getCodigo());
//		} else {
//			dir3organoInstructor = getCodigoDir3UA(servicioRolsac.getLink_organoInstructor().getCodigo());
//		}
		String dir3servicioResponsable = "";
		dir3servicioResponsable = ""+ servicioRolsac.getUaResponsable();
		//TODO: pendiente revisar cuando los servicios tengan link.
//		if (servicioRolsac.getLink_servicioResponsable() == null) {
//			log.error("El link de servicio de servicio responsable es nulo con el serv: " + servicioRolsac.getCodigo());
//		} else {
//			dir3servicioResponsable = getCodigoDir3UA(servicioRolsac.getLink_servicioResponsable().getCodigo());
//		}

		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
		dp.setDescripcion(servicioRolsac.getNombre());
		dp.setIdProcedimientoSIA(servicioRolsac.getCodigoSIA());
		dp.setOrganoResponsableDir3(dir3servicioResponsable);
		dp.setValidacion((servicioRolsac.getEstado().equals(TypeProcedimientoEstado.PUBLICADO.toString())
								&& servicioRolsac.getWorkflow().equals(TypeProcedimientoWorkflow.DEFINITIVO.getValor())) ? 1 : 0);
		dp.setServicio(true);
		dp.setLopd(generarInfoLOPDServicio(servicioRolsac, idioma));

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
		dt.setDescripcion(servicioRolsac.getNombre());
		dt.setVigente(esVigente(servicioRolsac));
		dt.setOrganoDestinoDir3(dir3organoInstructor);
		dt.setProcedimiento(dp);
		dt.setTelematico(servicioRolsac.isTramitElectronica());
		dt.setPlazoInicio(
				servicioRolsac.getFechaPublicacion() != null ? servicioRolsac.getFechaPublicacion().getTime() : null);
		dt.setPlazoFin(
				servicioRolsac.getFechaCaducidad() != null ? servicioRolsac.getFechaCaducidad().getTime()
						: null);
		dt.setEmailSoporte(servicioRolsac.getResponsableEmail());

		final RTipoTramitacion tipoTramitacionRolsac = getRTipoTramitacionRolsac(servicioRolsac.getLink_tipoTramitacion().getCodigo(), idioma)[0];
		final RPlatTramitElectronica platTramitRolsac = getRPlatTramitElectronicaRolsac(tipoTramitacionRolsac.getLink_codPlatTramitacion().getCodigo(), idioma)[0];
		if (dt.isTelematico() && platTramitRolsac.getIdentificador() != null
				&& getIdentificadorPlafaformaSistra2().equals(platTramitRolsac.getIdentificador())) {
			final DefinicionTramiteTelematico definicion = new DefinicionTramiteTelematico();
			definicion.setTramiteIdentificador(tipoTramitacionRolsac.getTramiteId());
			if (tipoTramitacionRolsac.getTramiteVersion() != null) {
				definicion.setTramiteVersion(tipoTramitacionRolsac.getTramiteVersion());
			}
			dt.setTramiteTelematico(definicion);
		}
		return dt;
	}

}

