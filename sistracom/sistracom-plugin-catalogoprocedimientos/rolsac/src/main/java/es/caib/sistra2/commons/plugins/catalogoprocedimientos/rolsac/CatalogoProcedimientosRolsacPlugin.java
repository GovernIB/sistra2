package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
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
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RLink;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RProcedimientoRolsac;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaProcedimientos;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaSimple;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaTramites;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RTramiteRolsac;

/**
 * Plugin mock catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class CatalogoProcedimientosRolsacPlugin extends AbstractPluginProperties
		implements ICatalogoProcedimientosPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "rolsac.";

	public CatalogoProcedimientosRolsacPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public DefinicionTramiteCP obtenerDefinicionTramite(final String idTramiteCP, boolean servicio, final String idioma)
			throws CatalogoPluginException {

		// TODO PENDIENTE DE DISTINGUIR SI ES SERVICIO

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("idioma", idioma);
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaTramites> responseTramite = restTemplate
				.postForEntity(getPropiedad("url") + "/tramites/" + idTramiteCP, request, RRespuestaTramites.class);
		if (responseTramite == null || responseTramite.getBody() == null
				|| responseTramite.getBody().getResultado() == null) {
			throw new CatalogoPluginException("El tramite no existe.");
		}
		final RTramiteRolsac[] tramitesRolsac = responseTramite.getBody().getResultado();

		if (tramitesRolsac == null || tramitesRolsac.length == 0) {
			throw new CatalogoPluginException("No hay tramite");
		}

		// Obtener procedimiento.
		final RTramiteRolsac tramiteRolsac = tramitesRolsac[0];

		final String codigoProc = tramiteRolsac.getLink_procedimiento().getCodigo();
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaProcedimientos> responseProc = restTemplate.postForEntity(
				getPropiedad("url") + "/procedimientos/" + codigoProc, requestProc, RRespuestaProcedimientos.class);
		if (responseProc == null || responseProc.getBody() == null || responseProc.getBody().getResultado() == null) {
			throw new CatalogoPluginException("El procedimiento no existe.");
		}
		final RProcedimientoRolsac[] procedimientosRolsac = responseProc.getBody().getResultado();
		if (procedimientosRolsac == null || procedimientosRolsac.length == 0) {
			throw new CatalogoPluginException("No hay procedimiento");
		}
		final RProcedimientoRolsac procRolsac = procedimientosRolsac[0];

		// Codigo DIR3 responsable procedimiento
		final String dir3organoResponsable = getCodigoDir3UA(procRolsac.getLink_unidadAdministrativa());
		// Codigo DIR3 destintario tramite
		final String dir3organoDestinatario = getCodigoDir3UA(tramiteRolsac.getLink_organCompetent());

		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador(codigoProc);
		dp.setDescripcion(procRolsac.getNombre());
		dp.setIdProcedimientoSIA(procRolsac.getCodigoSIA());
		dp.setOrganoResponsableDir3(dir3organoResponsable);

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(idTramiteCP);
		dt.setDescripcion(tramiteRolsac.getNombre());
		dt.setProcedimiento(dp);
		dt.setVigente(esVigente(procRolsac, tramiteRolsac));
		dt.setOrganoDestinoDir3(dir3organoDestinatario);

		return dt;

	}

	/**
	 * Obtiene el código DIR3 de la UA
	 *
	 * @param linkUA
	 * @return
	 * @throws CatalogoPluginException
	 * @throws RestClientException
	 */
	private String getCodigoDir3UA(final RLink linkUA) throws CatalogoPluginException {

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaSimple> responseCodDIR3 = restTemplate.postForEntity(
				getPropiedad("url") + "/unidades_administrativas/codigoDir3/" + linkUA.getCodigo(), requestProc,
				RRespuestaSimple.class);

		if (responseCodDIR3 == null || responseCodDIR3.getBody() == null
				|| responseCodDIR3.getBody().getResultado() == null) {
			throw new CatalogoPluginException("LA UA no existe.");
		}
		return responseCodDIR3.getBody().getResultado();
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
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *            propiedad
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

	@Override
	public List<DefinicionTramiteCP> obtenerTramites(final String idTramite, final Integer version, final String idioma)
			throws CatalogoPluginException {

		// TODO PENDIENTE DE RECUPERAR TAMBIEN LOS SERVICIOS

		final List<DefinicionTramiteCP> res = new ArrayList<>();

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("idioma", idioma);
		if (version == null) {
			map.add("filtro", "{\"codigoTramiteTelematico\":\"" + idTramite + "\"}");
		} else {
			map.add("filtro", "{\"codigoTramiteTelematico\":\"" + idTramite + "\",\"versionTramiteTelematico\" : \""
					+ version + "\"}");
		}
		// Obtener procedimiento.
		final HttpEntity<MultiValueMap<String, String>> requestTram = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaTramites> responseTram = restTemplate
				.postForEntity(getPropiedad("url") + "/tramites/", requestTram, RRespuestaTramites.class);

		final RTramiteRolsac[] tramitesRolsac = responseTram.getBody().getResultado();

		if (tramitesRolsac != null) {

			final Map<String, RProcedimientoRolsac> procedimientos = new HashMap<>();

			for (final RTramiteRolsac tramiteRolsac : tramitesRolsac) {

				// Obtener procedimiento.
				final String codigoProc = tramiteRolsac.getLink_procedimiento().getCodigo();
				RProcedimientoRolsac procRolsac;
				if (procedimientos.containsKey(codigoProc)) {
					procRolsac = procedimientos.get(codigoProc);
				} else {
					final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
					final ResponseEntity<RRespuestaProcedimientos> responseProc = restTemplate.postForEntity(
							getPropiedad("url") + "/procedimientos/" + codigoProc, requestProc,
							RRespuestaProcedimientos.class);
					if (responseProc == null || responseProc.getBody() == null
							|| responseProc.getBody().getResultado() == null) {
						throw new CatalogoPluginException("El procedimiento no existe.");
					}

					procRolsac = responseProc.getBody().getResultado()[0];
					procedimientos.put(codigoProc, procRolsac);
				}

				// Codigo DIR3 responsable procedimiento
				final String dir3organoResponsable = getCodigoDir3UA(procRolsac.getLink_unidadAdministrativa());
				// Codigo DIR3 destintario tramite
				final String dir3organoDestinatario = getCodigoDir3UA(tramiteRolsac.getLink_organCompetent());

				final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
				dp.setIdentificador(codigoProc);
				dp.setDescripcion(procRolsac.getNombre());
				dp.setIdProcedimientoSIA(procRolsac.getCodigoSIA());
				dp.setOrganoResponsableDir3(dir3organoResponsable);

				final DefinicionTramiteCP dt = new DefinicionTramiteCP();
				dt.setIdentificador(String.valueOf(tramiteRolsac.getCodigo()));
				dt.setDescripcion(tramiteRolsac.getNombre());
				dt.setProcedimiento(dp);
				dt.setVigente(esVigente(procRolsac, tramiteRolsac));
				dt.setOrganoDestinoDir3(dir3organoDestinatario);
				res.add(dt);

			}
		}

		return res;

	}

}
