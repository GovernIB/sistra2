package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionProcedimientoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteTelematico;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RProcedimientoRolsac;
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
	private static final String LITERAL_IDIOMA = "idioma";
	private static final String LITERAL_ERROR_NO_CONECTAR = "No se conecta correctamente a ROLSAC";
	private static final String LITERAL_FILTRO = "filtro";

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String IDENTIFICADOR_SISTRA2 = "SISTRA2";

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "rolsac.";

	public CatalogoProcedimientosRolsacPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
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
	 * @param idServicioCP ID Servicio
	 * @param idioma       Idioma (es/ca/en)
	 * @return
	 * @throws CatalogoPluginException
	 */
	private DefinicionTramiteCP obtenerDefinicionTramiteServicio(final String idServicioCP, final String idioma)
			throws CatalogoPluginException {

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(LITERAL_IDIOMA, idioma);

		final RServicioRolsac servicioRolsac = getRServicioRolsac(idServicioCP, map)[0];

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

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
		dt.setDescripcion(servicioRolsac.getNombre());
		dt.setVigente(esVigente(servicioRolsac));
		dt.setOrganoDestinoDir3(dir3organoInstructor);
		dt.setProcedimiento(dp);
		dt.setTelematico(servicioRolsac.isTelematico());
		if (dt.isTelematico() && servicioRolsac.getPlataforma() != null
				&& IDENTIFICADOR_SISTRA2.equals(servicioRolsac.getPlataforma().getIdentificador())) {
			final DefinicionTramiteTelematico definicion = new DefinicionTramiteTelematico();
			definicion.setTramiteIdentificador(servicioRolsac.getTramiteId());
			if (servicioRolsac.getTramiteVersion() != null) {
				definicion.setTramiteVersion(Integer.valueOf(servicioRolsac.getTramiteVersion()));
			}
			dt.setTramiteTelematico(definicion);
		}

		return dt;
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
		final int intentosMax = getPropiedadIntentos();
		int intentos = 0;
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
				log.warn(getWarning(intentos), e);
			}
			intentos++;
		}

		throw new CatalogoPluginException(LITERAL_ERROR_NO_CONECTAR);
	}

	/**
	 * Método privado que calcula la definicion de trámite de un procedimiento.
	 *
	 * @param idTramiteCP ID Trámite
	 * @param idioma      Idioma (es/ca/en)
	 * @return
	 * @throws CatalogoPluginException
	 */

	private DefinicionTramiteCP obtenerDefinicionTramiteProcedimiento(final String idTramiteCP, final String idioma)
			throws CatalogoPluginException {

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(LITERAL_IDIOMA, idioma);

		// Obtener procedimiento.
		final RTramiteRolsac[] tramitesRolsac = getRTramiteRolsac(idTramiteCP, map);
		final RTramiteRolsac tramiteRolsac = tramitesRolsac[0];

		final String codigoProc = tramiteRolsac.getLink_procedimiento().getCodigo();
		final RProcedimientoRolsac procRolsac = getRProcedimientoRolsac(codigoProc, idioma);

		// Codigo DIR3 responsable procedimiento
		final String dir3organoResponsable = getCodigoDir3UA(procRolsac.getLink_unidadAdministrativa().getCodigo());
		// Codigo DIR3 destintario tramite
		final String dir3organoDestinatario = getCodigoDir3UA(tramiteRolsac.getLink_organCompetent().getCodigo());

		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador(codigoProc);
		dp.setDescripcion(procRolsac.getNombre());
		dp.setIdProcedimientoSIA(procRolsac.getCodigoSIA());
		dp.setOrganoResponsableDir3(dir3organoResponsable);
		dp.setServicio(false);

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(String.valueOf(tramiteRolsac.getCodigo()));
		dt.setDescripcion(tramiteRolsac.getNombre());
		dt.setProcedimiento(dp);
		dt.setVigente(esVigente(procRolsac, tramiteRolsac));
		dt.setOrganoDestinoDir3(dir3organoDestinatario);
		dt.setTelematico(tramiteRolsac.isTelematico());

		if (dt.isTelematico() && tramiteRolsac.getPlataforma() != null
				&& IDENTIFICADOR_SISTRA2.equals(tramiteRolsac.getPlataforma().getIdentificador())) {
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
		map.add(LITERAL_IDIOMA, idioma);

		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);

		final int intentosMax = getPropiedadIntentos();
		int intentos = 0;
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
				log.warn(getWarning(intentos), e);
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
		final int intentosMax = getPropiedadIntentos();
		int intentos = 0;
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
				log.warn(getWarning(intentos), e);
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

		final int intentosMax = getPropiedadIntentos();
		int intentos = 0;
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
				log.warn(getWarning(intentos), e);
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
		return "Intento " + intentos + " de conectar a rolsac ";
	}

	/**
	 * Indica si es vigente el trámite. Se mira:<br />
	 * <ul>
	 * <li>Que el proc es público (validacion = 1, los otros valores 2 y 3, son
	 * privado o interno</li>
	 * <li>Que el proc tiene fechas correctas (está publicado del pasado y no está
	 * caduco)</li>
	 * <li>Que el trámite tiene fechas correctas (está publicado del pasado y no
	 * está caduco)</li>
	 * </ul>
	 *
	 * @param procedimiento
	 * @param tramite
	 * @return
	 */
	public boolean esVigente(final RProcedimientoRolsac procedimiento, final RTramiteRolsac tramite) {

		final boolean noCaducadoProc = (procedimiento.getFechaCaducidad() == null
				|| procedimiento.getFechaCaducidad().after(Calendar.getInstance()));
		final boolean noCaducado = (tramite.getDataCaducitat() == null
				|| tramite.getDataCaducitat().after(Calendar.getInstance()));
		final boolean publicadoProc = (procedimiento.getFechaPublicacion() == null
				|| procedimiento.getFechaPublicacion().before(Calendar.getInstance()));
		final boolean publicado = (tramite.getDataPublicacio() == null
				|| tramite.getDataPublicacio().before(Calendar.getInstance()));
		final boolean visible = procedimiento.getValidacion() == null
				|| "1".equals(procedimiento.getValidacion().toString());
		return visible && noCaducado && publicado && noCaducadoProc && publicadoProc;
	}

	/**
	 * Indica si es vigente el trámite. Se mira:<br />
	 * <ul>
	 * <li>Que el serv es público (validacion = 1, los otros valores 2 y 3, son
	 * privado o interno</li>
	 * <li>Que el serv tiene fechas correctas (está publicado del pasado y no está
	 * caduco)</li>
	 * </ul>
	 *
	 * @param servicio
	 * @return
	 */
	public boolean esVigente(final RServicioRolsac servicio) {

		final boolean noCaducadoServ = (servicio.getFechaDespublicacion() == null
				|| servicio.getFechaDespublicacion().after(Calendar.getInstance()));
		final boolean publicadoServ = (servicio.getFechaPublicacion() == null
				|| servicio.getFechaPublicacion().before(Calendar.getInstance()));
		final boolean visible = servicio.getValidacion() == null || "1".equals(servicio.getValidacion().toString());
		return visible && noCaducadoServ && publicadoServ;
	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad propiedad
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
		map.add(LITERAL_IDIOMA, idioma);
		if (version == null) {
			map.add(LITERAL_FILTRO, "{\"codigoTramiteTelematico\":\"" + idTramite + "\"}");
		} else {
			map.add(LITERAL_FILTRO, "{\"codigoTramiteTelematico\":\"" + idTramite
					+ "\",\"versionTramiteTelematico\" : \"" + version + "\"}");
		}

		final RServicioRolsac[] serviciosRolsac = getRServicioRolsac("", map);

		if (serviciosRolsac != null) {
			for (final RServicioRolsac servicioRolsac : serviciosRolsac) {

				// Codigo DIR3 responsable procedimiento
				final String dir3organoInstructor = getCodigoDir3UA(
						servicioRolsac.getLink_organoInstructor().getCodigo());
				final String dir3servicioResponsable = getCodigoDir3UA(
						servicioRolsac.getLink_servicioResponsable().getCodigo());

				final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
				dp.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
				dp.setDescripcion(servicioRolsac.getNombre());
				dp.setIdProcedimientoSIA(servicioRolsac.getCodigoSIA());
				dp.setOrganoResponsableDir3(dir3servicioResponsable);
				dp.setServicio(true);

				final DefinicionTramiteCP dt = new DefinicionTramiteCP();
				dt.setIdentificador(String.valueOf(servicioRolsac.getCodigo()));
				dt.setDescripcion(servicioRolsac.getNombre());
				dt.setVigente(esVigente(servicioRolsac));
				dt.setOrganoDestinoDir3(dir3organoInstructor);
				dt.setProcedimiento(dp);
				dt.setTelematico(servicioRolsac.isTelematico());
				if (dt.isTelematico() && servicioRolsac.getPlataforma() != null
						&& IDENTIFICADOR_SISTRA2.equals(servicioRolsac.getPlataforma().getIdentificador())) {
					final DefinicionTramiteTelematico definicion = new DefinicionTramiteTelematico();
					definicion.setTramiteIdentificador(servicioRolsac.getTramiteId());
					if (servicioRolsac.getTramiteVersion() != null) {
						definicion.setTramiteVersion(Integer.valueOf(servicioRolsac.getTramiteVersion()));
					}
					dt.setTramiteTelematico(definicion);

				}

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
		map.add(LITERAL_IDIOMA, idioma);
		if (version == null) {
			map.add(LITERAL_FILTRO, "{\"codigoTramiteTelematico\":\"" + idTramite + "\"}");
		} else {
			map.add(LITERAL_FILTRO, "{\"codigoTramiteTelematico\":\"" + idTramite
					+ "\",\"versionTramiteTelematico\" : \"" + version + "\"}");
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

				// Codigo DIR3 responsable procedimiento
				final String dir3organoResponsable = getCodigoDir3UA(
						procRolsac.getLink_unidadAdministrativa().getCodigo());
				// Codigo DIR3 destintario tramite
				final String dir3organoDestinatario = getCodigoDir3UA(
						tramiteRolsac.getLink_organCompetent().getCodigo());

				final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
				dp.setIdentificador(codigoProc);
				dp.setDescripcion(procRolsac.getNombre());
				dp.setIdProcedimientoSIA(procRolsac.getCodigoSIA());
				dp.setOrganoResponsableDir3(dir3organoResponsable);
				dp.setServicio(false);

				final DefinicionTramiteCP dt = new DefinicionTramiteCP();
				dt.setIdentificador(String.valueOf(tramiteRolsac.getCodigo()));
				dt.setDescripcion(tramiteRolsac.getNombre());
				dt.setProcedimiento(dp);
				dt.setVigente(esVigente(procRolsac, tramiteRolsac));
				dt.setOrganoDestinoDir3(dir3organoDestinatario);
				dt.setTelematico(tramiteRolsac.isTelematico());
				if (dt.isTelematico() && tramiteRolsac.getPlataforma() != null
						&& IDENTIFICADOR_SISTRA2.equals(tramiteRolsac.getPlataforma().getIdentificador())) {
					final DefinicionTramiteTelematico definicion = new DefinicionTramiteTelematico();
					definicion.setTramiteIdentificador(tramiteRolsac.getIdTraTel());
					if (tramiteRolsac.getVersio() != null) {
						definicion.setTramiteVersion(tramiteRolsac.getVersio());
					}
					dt.setTramiteTelematico(definicion);

				}
				res.add(dt);

			}
		}

		return res;

	}

}
