package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import es.caib.sistra2.commons.plugins.autenticacion.api.AutenticacionPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ArchivoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CampoLOPD;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionLOPD;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionProcedimientoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteTelematico;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RArchivoRolsac;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RInfoLOPDIntf;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RProcedimientoRolsac;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaArchivos;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaProcedimientos;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaServicios;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaSimple;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaTramites;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RServicioRolsac;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RTramiteRolsac;

/**
 * Plugin mock catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class CatalogoProcedimientosRolsacPlugin extends AbstractPluginProperties
		implements ICatalogoProcedimientosPlugin {

	/** El literal de idioma que no para de estar repetido. **/
	private static final String TAMANYO_MAXIMO = "200";
	//private static final String LITERAL_PAGE = "page";
	private static final String LITERAL_IDIOMA = "lang";
	private static final String LITERAL_ERROR_NO_CONECTAR = "No se conecta correctamente a ROLSAC";
	private static final String LITERAL_FILTRO = "filtro";
	private static final String LITERAL_FILTRO_PAGINACION = "filtroPaginacion";

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/** Id plataforma Sistra2 definido en Rolsac. */
	private static final String PROPIEDAD_IDENTIFICADOR_SISTRA2 = "identificadorPlataformaSistra2";

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "rolsac.";

	/** Literales campos LOPD. */
	private Properties literalesCamposLOPD;

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
			definicion = obtenerDefinicionTramiteServicio(idTramiteCP, idioma);
		} else {
			definicion = obtenerDefinicionTramiteProcedimiento(idTramiteCP, idioma);
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
	private DefinicionTramiteCP obtenerDefinicionTramiteServicio(final String idServicioCP, final String idioma)
			throws CatalogoPluginException {

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		//map.add(LITERAL_TAMANYO, TAMANYO_MAXIMO);
		map.add(LITERAL_FILTRO_PAGINACION, "{\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}");
		map.add(LITERAL_IDIOMA, idioma);
		map.add(LITERAL_FILTRO, "{\"plataforma\" : \"" + getIdentificadorPlafaformaSistra2() + "\"}");

		final RServicioRolsac servicioRolsac = getRServicioRolsac(idServicioCP, map)[0];

		// Crea definición trámite servicio
		final DefinicionTramiteCP dt = crearDefinicionTramiteServicio(idioma, servicioRolsac);

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
	private RServicioRolsac[] getRServicioRolsac(final String idServicioCP, final MultiValueMap<String, String> map)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Obtener tramite.
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;

		while (intentos <= intentosMax) {

			try {
				final ResponseEntity<RRespuestaServicios> responseTramite;

				if (idServicioCP == null || idServicioCP.isEmpty()) {
					responseTramite = restTemplate.postForEntity(getPropiedad("url") + "/servicios", request,
							RRespuestaServicios.class);
				} else {
					responseTramite = restTemplate.postForEntity(getPropiedad("url") + "/servicios/" + idServicioCP,
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

	private DefinicionTramiteCP obtenerDefinicionTramiteProcedimiento(final String idTramiteCP, final String idioma)
			throws CatalogoPluginException {

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(LITERAL_FILTRO_PAGINACION, "{\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}");
		map.add(LITERAL_IDIOMA, idioma);
		map.add(LITERAL_FILTRO, "{\"plataforma\" : \"" + getIdentificadorPlafaformaSistra2() + "\"}");

		// Obtener procedimiento.
		final RTramiteRolsac[] tramitesRolsac = getRTramiteRolsac(idTramiteCP, map);
		final RTramiteRolsac tramiteRolsac = tramitesRolsac[0];

		final RProcedimientoRolsac procRolsac = getRProcedimientoRolsac(
				tramiteRolsac.getLink_procedimiento().getCodigo(), idioma);

		final DefinicionTramiteCP dt = obtenerDefinicionTramiteProcedimiento(procRolsac, tramiteRolsac, idioma);
		return dt;

	}

	protected DefinicionTramiteCP obtenerDefinicionTramiteProcedimiento(final RProcedimientoRolsac procRolsac,
			final RTramiteRolsac tramiteRolsac, final String idioma) throws CatalogoPluginException {
		// Codigo DIR3 responsable procedimiento
		final String dir3organoResponsable = getCodigoDir3UA(procRolsac.getLink_unidadAdministrativa().getCodigo());
		// Codigo DIR3 destintario tramite
		final String dir3organoDestinatario = getCodigoDir3UA(tramiteRolsac.getLink_organCompetent().getCodigo());

		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador(tramiteRolsac.getLink_procedimiento().getCodigo());
		dp.setDescripcion(procRolsac.getNombre());
		dp.setIdProcedimientoSIA(procRolsac.getCodigoSIA());
		dp.setOrganoResponsableDir3(dir3organoResponsable);
		dp.setServicio(false);
		dp.setLopd(generarInfoLOPD(procRolsac, idioma));

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(String.valueOf(tramiteRolsac.getCodigo()));
		dt.setDescripcion(tramiteRolsac.getNombre());
		dt.setProcedimiento(dp);
		dt.setVigente(esVigente(procRolsac, tramiteRolsac));
		dt.setOrganoDestinoDir3(dir3organoDestinatario);
		dt.setTelematico(tramiteRolsac.isTelematico());
		dt.setPlazoInicio(tramiteRolsac.getDataInici() != null ? tramiteRolsac.getDataInici().getTime() : null);
		dt.setPlazoFin(tramiteRolsac.getDataTancament() != null ? tramiteRolsac.getDataTancament().getTime() : null);
		dt.setEmailSoporte(procRolsac.getDirElectronica());

		if (dt.isTelematico() && tramiteRolsac.getPlataforma() != null
				&& getIdentificadorPlafaformaSistra2().equals(tramiteRolsac.getPlataforma().getIdentificador())) {
			final DefinicionTramiteTelematico definicion = new DefinicionTramiteTelematico();
			definicion.setTramiteIdentificador(tramiteRolsac.getIdTraTel());
			definicion.setTramiteVersion(tramiteRolsac.getVersio());
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

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(LITERAL_FILTRO_PAGINACION, "{\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}");
		map.add(LITERAL_IDIOMA, idioma);

		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				final ResponseEntity<RRespuestaProcedimientos> responseProc = restTemplate.postForEntity(
						getPropiedad("url") + "/procedimientos/" + codigoProc, requestProc,
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
	 * Obtiene el tramite con reintentos.
	 *
	 * @param idTramiteCP
	 * @param map
	 * @return
	 * @throws CatalogoPluginException
	 */
	private RArchivoRolsac[] getRArchivoRolsac(final String referenciaArchivo) throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				final ResponseEntity<RRespuestaArchivos> responseArchivo;

				responseArchivo = restTemplate.postForEntity(getPropiedad("url") + "/archivos/" + referenciaArchivo,
						request, RRespuestaArchivos.class);

				final RArchivoRolsac[] archivosRolsac = responseArchivo.getBody().getResultado();

				if (archivosRolsac == null || archivosRolsac.length == 0) {
					throw new CatalogoPluginException("No existe archivo");
				}
				return archivosRolsac;

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
	private RTramiteRolsac[] getRTramiteRolsac(final String idTramiteCP, final MultiValueMap<String, String> map)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}
		int intentos = 1;
		while (intentos <= intentosMax) {

			try {
				final ResponseEntity<RRespuestaTramites> responseTramite;
				if (idTramiteCP == null || idTramiteCP.isEmpty()) {
					responseTramite = restTemplate.postForEntity(getPropiedad("url") + "/tramites", request,
							RRespuestaTramites.class);
				} else {
					responseTramite = restTemplate.postForEntity(getPropiedad("url") + "/tramites/" + idTramiteCP,
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
	 * @param linkUA
	 * @return
	 * @throws CatalogoPluginException
	 * @throws RestClientException
	 */
	private String getCodigoDir3UA(final String codigoDIR3) throws CatalogoPluginException {

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);

		// Llamada a Rolsac con reintentos
		int intentosMax = getPropiedadIntentos();
		if (intentosMax <= 0) {
			intentosMax = 1;
		}

		int intentos = 1;
		while (intentos <= intentosMax) {

			try {

				final ResponseEntity<RRespuestaSimple> responseCodDIR3 = restTemplate.postForEntity(
						getPropiedad("url") + "/unidades_administrativas/codigoDir3/" + codigoDIR3, requestProc,
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
		// Procedimiento público
		final boolean procedimientoPublico = procedimiento.getValidacion() == null
				|| "1".equals(procedimiento.getValidacion().toString());
		// Tramite abierto
		final boolean tramitePlazoAbierto = (tramite.getDataInici() == null || tramite.getDataInici().before(ahora))
				&& (tramite.getDataTancament() == null || tramite.getDataTancament().after(ahora));
		// Trámite publicado
		final boolean tramitePublicado = (tramite.getDataPublicacio() == null
				|| tramite.getDataPublicacio().before(ahora));
		// Vigente
		return procedimientoPublico && procedimientoNoCaducado && procedimientoPublicado && tramitePlazoAbierto
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
				&& (servicio.getFechaDespublicacion() == null || servicio.getFechaDespublicacion().after(ahora));
		// Servicio es público
		final boolean publico = servicio.getValidacion() == null || "1".equals(servicio.getValidacion().toString());
		// Vigente si público y publicado
		return publico && publicado;
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
		trams.addAll(obtenerTramitesServicios(idTramite, version, idioma));
		trams.addAll(obtenerTramitesProcedimientos(idTramite, version, idioma));

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
			final String idioma) throws CatalogoPluginException {

		final List<DefinicionTramiteCP> res = new ArrayList<>();

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(LITERAL_FILTRO_PAGINACION, "{\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}");
		map.add(LITERAL_IDIOMA, idioma);
		if (version == null) {
			map.add(LITERAL_FILTRO, "{\"codigoTramiteTelematico\":\"" + idTramite + "\", \"plataforma\" : \""
					+ getIdentificadorPlafaformaSistra2() + "\"}");
		} else {
			map.add(LITERAL_FILTRO,
					"{\"codigoTramiteTelematico\":\"" + idTramite + "\",\"versionTramiteTelematico\" : \"" + version
							+ "\", \"plataforma\" : \"" + getIdentificadorPlafaformaSistra2() + "\"}");
		}

		final RServicioRolsac[] serviciosRolsac = getRServicioRolsac("", map);

		if (serviciosRolsac != null) {
			for (final RServicioRolsac servicioRolsac : serviciosRolsac) {

				// Crea definición trámite servicio
				final DefinicionTramiteCP dt = crearDefinicionTramiteServicio(idioma, servicioRolsac);

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
			final String idioma) throws CatalogoPluginException {

		final List<DefinicionTramiteCP> res = new ArrayList<>();

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(LITERAL_FILTRO_PAGINACION, "{\"size\":\"" + TAMANYO_MAXIMO + "\", \"page\" : \"0\"}");
		map.add(LITERAL_IDIOMA, idioma);
		if (version == null) {
			map.add(LITERAL_FILTRO, "{\"codigoTramiteTelematico\":\"" + idTramite + "\", \"plataforma\" : \""
					+ getIdentificadorPlafaformaSistra2() + "\"}");
		} else {
			map.add(LITERAL_FILTRO,
					"{\"codigoTramiteTelematico\":\"" + idTramite + "\",\"versionTramiteTelematico\" : \"" + version
							+ "\", \"plataforma\" : \"" + getIdentificadorPlafaformaSistra2() + "\"}");
		}

		final RTramiteRolsac[] tramitesRolsac = getRTramiteRolsac("", map);
		if (tramitesRolsac != null) {

			final Map<String, RProcedimientoRolsac> procedimientos = new HashMap<>();

			for (final RTramiteRolsac tramiteRolsac : tramitesRolsac) {

				// Obtener procedimiento.
				final String codigoProc = tramiteRolsac.getLink_procedimiento().getCodigo();
				RProcedimientoRolsac procRolsac;
				if (procedimientos.containsKey(codigoProc)) {
					procRolsac = procedimientos.get(codigoProc);
				} else {
					procRolsac = getRProcedimientoRolsac(codigoProc, idioma);
					procedimientos.put(codigoProc, procRolsac);
				}

				// Obtiene def tramite
				final DefinicionTramiteCP dt = obtenerDefinicionTramiteProcedimiento(procRolsac, tramiteRolsac, idioma);
				res.add(dt);

			}
		}

		return res;

	}

	@Override
	public ArchivoCP descargarArchivo(final String referenciaArchivo) throws CatalogoPluginException {

		// NO IMPLEMENTAMOS DESCARGA, DIRECTAMENTE DEVOLVEMOS ENLACE A SEUCAIB
		if (true) {
			throw new CatalogoPluginException("NO IMPLEMENTADO. SE DEVUELVE MEDIANTE ENLACE A SEUCAIB.");
		}

		// Llamada a Rolsac para obtener props archivo
		// NO PARECE QUE HACE FALTA, SE PUEDE OBTENER FILENAME DIRECTAMENTE DEL SERVLET
		final RArchivoRolsac[] archivos = getRArchivoRolsac(referenciaArchivo);

		// Llamada a servlet para descargar archivo
		// "/sacws-api/arxiu/apiArxiuServlet?id=";
		// ...

		return null;

	}

	/**
	 * Genera info lopd.
	 *
	 * @param procRolsac
	 *                       procedimiento rolsac
	 * @param idioma
	 *                       idioma
	 * @return lopd
	 * @throws CatalogoPluginException
	 */
	protected DefinicionLOPD generarInfoLOPD(final RInfoLOPDIntf procRolsac, final String idioma)
			throws CatalogoPluginException {
		DefinicionLOPD lopd = null;
		if ("true".equals(getPropiedad("infoLOPD"))) {
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
					&& StringUtils.isNotBlank(procRolsac.getLopdLegitimacion().getNombre())) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("legitimacion", idioma));
				c.setDescripcion(procRolsac.getLopdLegitimacion().getNombre());
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
			if (procRolsac.getLink_lopdInfoAdicional() != null
					&& procRolsac.getLink_lopdInfoAdicional().getCodigo() != null) {
				final CampoLOPD c = new CampoLOPD();
				c.setTitulo(obtenTituloCampoLOPD("infoAdicional", idioma));
				c.setDescripcion(obtenDescripcionCampoLOPD("infoAdicional", idioma));
				// Devolvemos enlace seucaib
				// c.setReferenciaArchivo(procRolsac.getLink_lopdInfoAdicional().getCodigo());
				final String urlSeucaibDescarga = getPropiedad("urlSeucaib") + "/" + ("es".equals(idioma) ? "es" : "ca")
						+ "/arxiuServlet?id=" + procRolsac.getLink_lopdInfoAdicional().getCodigo();
				c.setEnlace(urlSeucaibDescarga);
				campos.add(c);
			}

			lopd.setCampos(campos);
		}
		return lopd;
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
			final RServicioRolsac servicioRolsac) throws CatalogoPluginException {

		// Codigo DIR3 responsable procedimiento
		final String dir3organoInstructor = getCodigoDir3UA(servicioRolsac.getLink_organoInstructor().getCodigo());
		final String dir3servicioResponsable = getCodigoDir3UA(
				servicioRolsac.getLink_servicioResponsable().getCodigo());

		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
		dp.setDescripcion(servicioRolsac.getNombre());
		dp.setIdProcedimientoSIA(servicioRolsac.getCodigoSIA());
		dp.setOrganoResponsableDir3(dir3servicioResponsable);
		dp.setServicio(true);
		dp.setLopd(generarInfoLOPD(servicioRolsac, idioma));

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
		dt.setDescripcion(servicioRolsac.getNombre());
		dt.setVigente(esVigente(servicioRolsac));
		dt.setOrganoDestinoDir3(dir3organoInstructor);
		dt.setProcedimiento(dp);
		dt.setTelematico(servicioRolsac.isTelematico());
		dt.setPlazoInicio(
				servicioRolsac.getFechaPublicacion() != null ? servicioRolsac.getFechaPublicacion().getTime() : null);
		dt.setPlazoFin(
				servicioRolsac.getFechaDespublicacion() != null ? servicioRolsac.getFechaDespublicacion().getTime()
						: null);
		dt.setEmailSoporte(servicioRolsac.getCorreo());
		if (dt.isTelematico() && servicioRolsac.getPlataforma() != null
				&& getIdentificadorPlafaformaSistra2().equals(servicioRolsac.getPlataforma().getIdentificador())) {
			final DefinicionTramiteTelematico definicion = new DefinicionTramiteTelematico();
			definicion.setTramiteIdentificador(servicioRolsac.getTramiteId());
			if (servicioRolsac.getTramiteVersion() != null) {
				definicion.setTramiteVersion(Integer.valueOf(servicioRolsac.getTramiteVersion()));
			}
			dt.setTramiteTelematico(definicion);
		}
		return dt;
	}

}

