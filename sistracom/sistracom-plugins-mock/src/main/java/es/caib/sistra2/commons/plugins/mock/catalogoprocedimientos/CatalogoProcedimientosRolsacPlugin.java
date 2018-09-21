package es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.plugins.IPlugin;
import org.fundaciobit.plugins.utils.AbstractPluginProperties;
import org.fundaciobit.plugins.utils.PluginsManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionProcedimientoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.ICatalogoProcedimientosPlugin;
import es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.modelo.RProcedimientoRolsac;
import es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.modelo.RRespuestaProcedimiento;
import es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.modelo.RRespuestaProcedimientos;
import es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.modelo.RRespuestaTramite;
import es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.modelo.RTramiteRolsac;

/**
 * Plugin mock catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class CatalogoProcedimientosRolsacPlugin extends AbstractPluginProperties
		implements ICatalogoProcedimientosPlugin {

	public static void main(final String args[]) throws ParseException {
		final Properties prop = new Properties();
		final String prefijo = "plugins.catalogoprocedimientos";
		prop.put(prefijo + ".usr", "rolsac_rest_api");
		prop.put(prefijo + ".pwd", "rolsac_rest_api");
		prop.put(prefijo + ".url", "http://caibter.indra.es/rolsac/api/rest/");
		final String className = "es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.CatalogoProcedimientosRolsacPlugin";
		final IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName(className, prefijo, prop);
		if (plg instanceof CatalogoProcedimientosRolsacPlugin) {
			final CatalogoProcedimientosRolsacPlugin plugin = (CatalogoProcedimientosRolsacPlugin) plg;
			final String idTramite = "1319201";
			final String idioma = "ca";
			plugin.obtenerDefinicionTramite(idTramite, idioma);
			plugin.obtenerProcedimientosTramiteSistra(idTramite, idioma);
		}
	}

	private final String prefijo;
	private final Properties properties;
	private String usr;
	private String pwd;
	private String url;

	public CatalogoProcedimientosRolsacPlugin(final String prefijoPropiedades, final Properties properties) {
		this.prefijo = prefijoPropiedades;
		this.properties = properties;
		if (this.properties != null) {
			if (this.properties.get("plugins.catalogoprocedimientos.usr") != null) {
				usr = this.properties.get("plugins.catalogoprocedimientos.usr").toString();
			}
			if (this.properties.get("plugins.catalogoprocedimientos.pwd") != null) {
				pwd = this.properties.get("plugins.catalogoprocedimientos.pwd").toString();
			}
			if (this.properties.get("plugins.catalogoprocedimientos.url") != null) {
				url = this.properties.get("plugins.catalogoprocedimientos.url").toString();
			}
		}
	}

	@Override
	public DefinicionTramiteCP obtenerDefinicionTramite(final String idTramiteCP, final String idioma) {

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(usr, pwd));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("idioma", idioma);
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaTramite> responseTramite = restTemplate
				.postForEntity(this.url + "/tramites/" + idTramiteCP, request, RRespuestaTramite.class);
		final RTramiteRolsac tramiteRolsac = responseTramite.getBody().getResultado();

		// Obtener procedimiento.
		final String codigoProc = tramiteRolsac.getLink_procedimiento().getCodigo();
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaProcedimiento> responseProc = restTemplate
				.postForEntity(this.url + "/procedimientos/" + codigoProc, requestProc, RRespuestaProcedimiento.class);
		final RProcedimientoRolsac procRolsac = responseProc.getBody().getResultado();

		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador(codigoProc);
		dp.setDescripcion(procRolsac.getNombre());
		dp.setIdProcedimientoSIA(procRolsac.getCodigoSIA());
		dp.setOrganoResponsableDir3(procRolsac.getResponsable());

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(idTramiteCP);
		dt.setDescripcion(tramiteRolsac.getNombre());
		dt.setProcedimiento(dp);
		dt.setVigente(esPublico(procRolsac, tramiteRolsac));

		return dt;

	}

	@Override
	public List<DefinicionProcedimientoCP> obtenerProcedimientosTramiteSistra(final String idTramite,
			final String idioma) {

		final List<DefinicionProcedimientoCP> res = new ArrayList<>();

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(usr, pwd));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("idioma", idioma);
		map.add("filtro", "{\"codigoTramiteTelematico\":\"" + idTramite + "\"}");

		// Obtener procedimiento.
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<RRespuestaProcedimientos> responseProc = restTemplate
				.postForEntity(this.url + "/procedimientos/", requestProc, RRespuestaProcedimientos.class);
		final RProcedimientoRolsac[] procedimientosRolsac = responseProc.getBody().getResultado();

		if (procedimientosRolsac != null) {

			for (final RProcedimientoRolsac procedimiento : procedimientosRolsac) {
				final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
				dp.setIdentificador(String.valueOf(procedimiento.getCodigo()));
				dp.setDescripcion(procedimiento.getNombre());
				dp.setIdProcedimientoSIA(procedimiento.getCodigoSIA());
				dp.setOrganoResponsableDir3(procedimiento.getResponsable());
				res.add(dp);
			}
		}

		return res;

	}

	public boolean esPublico(final RProcedimientoRolsac procedimiento, final RTramiteRolsac tramite) {
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
				e.printStackTrace();
				fechaCaducidad = null;
			}
		}
		if (tramite.getDataPublicacio() == null || tramite.getDataPublicacio().isEmpty()) {
			fechaPublicacion = null;
		} else {
			try {
				fechaPublicacion = sdf.parse(tramite.getDataPublicacio());
			} catch (final Exception e) {
				e.printStackTrace();
				fechaPublicacion = null;
			}
		}
		final boolean noCaducado = (fechaCaducidad == null || fechaCaducidad.after(now));
		final boolean publicado = (fechaPublicacion == null || fechaPublicacion.before(now));
		final boolean visible = procedimiento != null
				&& (procedimiento.getValidacion() == null || "1".equals(procedimiento.getValidacion().toString()));
		return visible && noCaducado && publicado;
	}

}
