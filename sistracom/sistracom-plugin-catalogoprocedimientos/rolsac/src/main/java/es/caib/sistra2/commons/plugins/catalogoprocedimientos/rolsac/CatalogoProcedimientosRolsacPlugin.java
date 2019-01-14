package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaProcedimiento;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaProcedimientos;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaSimple;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo.RRespuestaTramite;
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
	public DefinicionTramiteCP obtenerDefinicionTramite(final String idTramiteCP, final String idioma)
			throws CatalogoPluginException {

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("idioma", idioma);
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaTramite> responseTramite = restTemplate
				.postForEntity(getPropiedad("url") + "/tramites/" + idTramiteCP, request, RRespuestaTramite.class);
		if (responseTramite == null || responseTramite.getBody() == null
				|| responseTramite.getBody().getResultado() == null) {
			throw new CatalogoPluginException("El tramite no existe.");
		}
		final RTramiteRolsac tramiteRolsac = responseTramite.getBody().getResultado();

		// Obtener procedimiento.
		final String codigoProc = tramiteRolsac.getLink_procedimiento().getCodigo();
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaProcedimiento> responseProc = restTemplate.postForEntity(
				getPropiedad("url") + "/procedimientos/" + codigoProc, requestProc, RRespuestaProcedimiento.class);
		if (responseProc == null || responseProc.getBody() == null || responseProc.getBody().getResultado() == null) {
			throw new CatalogoPluginException("El procedimiento no existe.");
		}
		final RProcedimientoRolsac procRolsac = responseProc.getBody().getResultado();

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
		dt.setVigente(esPublico(procRolsac, tramiteRolsac));
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

	@Override
	public List<DefinicionProcedimientoCP> obtenerProcedimientosTramiteSistra(final String idTramite,
			final String idioma) throws CatalogoPluginException {

		final List<DefinicionProcedimientoCP> res = new ArrayList<>();

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("idioma", idioma);
		map.add("filtro", "{\"codigoTramiteTelematico\":\"" + idTramite + "\"}");

		// Obtener procedimiento.
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaProcedimientos> responseProc = restTemplate
				.postForEntity(getPropiedad("url") + "/procedimientos/", requestProc, RRespuestaProcedimientos.class);

		if (responseProc == null || responseProc.getBody() == null || responseProc.getBody().getResultado() == null) {
			throw new CatalogoPluginException("Los procedimientos no existen");
		}
		final RProcedimientoRolsac[] procedimientosRolsac = responseProc.getBody().getResultado();

		if (procedimientosRolsac != null) {

			for (final RProcedimientoRolsac procedimiento : procedimientosRolsac) {

				final String organoResponsable = getCodigoDir3UA(procedimiento.getLink_unidadAdministrativa());

				final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
				dp.setIdentificador(String.valueOf(procedimiento.getCodigo()));
				dp.setDescripcion(procedimiento.getNombre());
				dp.setIdProcedimientoSIA(procedimiento.getCodigoSIA());
				dp.setOrganoResponsableDir3(organoResponsable);
				res.add(dp);
			}
		}

		return res;

	}

	public boolean esPublico(final RProcedimientoRolsac procedimiento, final RTramiteRolsac tramite)
			throws CatalogoPluginException {
		final Date now = new Date();
		Date fechaCaducidad;
		Date fechaPublicacion;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		if (tramite.getDataCaducitat() == null || tramite.getDataCaducitat().isEmpty()) {
			fechaCaducidad = null;
		} else {
			try {
				fechaCaducidad = sdf.parse(tramite.getDataCaducitat());
			} catch (final Exception e) {
				throw new CatalogoPluginException("La fecha de caducidad tiene un formato erroneo", e);
			}
		}
		if (tramite.getDataPublicacio() == null || tramite.getDataPublicacio().isEmpty()) {
			fechaPublicacion = null;
		} else {
			try {
				fechaPublicacion = sdf.parse(tramite.getDataPublicacio());
			} catch (final Exception e) {
				throw new CatalogoPluginException("La fecha de caducidad tiene un formato erroneo", e);
			}
		}
		final boolean noCaducado = (fechaCaducidad == null || fechaCaducidad.after(now));
		final boolean publicado = (fechaPublicacion == null || fechaPublicacion.before(now));
		final boolean visible = procedimiento != null
				&& (procedimiento.getValidacion() == null || "1".equals(procedimiento.getValidacion().toString()));
		return visible && noCaducado && publicado;
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

}
