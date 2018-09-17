package es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.plugins.IPlugin;
import org.fundaciobit.plugins.utils.AbstractPluginProperties;
import org.fundaciobit.plugins.utils.PluginsManager;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionProcedimientoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.ICatalogoProcedimientosPlugin;

/**
 * Plugin mock catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class CatalogoProcedimientosRolsacPlugin extends AbstractPluginProperties
		implements ICatalogoProcedimientosPlugin {

	public static void main(final String args[]) {
		final Properties prop = new Properties();
		final String prefijo = "plugins.catalogoprocedimientos";
		prop.put(prefijo + ".usr", "rolsac_rest_api");
		prop.put(prefijo + ".pwd", "rolsac_rest_api");
		prop.put(prefijo + ".url", "http://http://caibter.indra.es/rolsac/api/rest/");
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
		final RTramiteRolsac tramite = restTemplate.getForObject(url + "/tramites/" + idTramiteCP,
				RTramiteRolsac.class);

		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador("PROC1");
		dp.setDescripcion("Procedimiento 1");
		dp.setIdProcedimientoSIA("SIA1");
		dp.setOrganoResponsableDir3("RespDIR3");

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(idTramiteCP);
		dt.setDescripcion("Tramite 1");
		dt.setProcedimiento(dp);
		dt.setVigente(true);

		return dt;
	}

	@Override
	public List<DefinicionProcedimientoCP> obtenerProcedimientosTramiteSistra(final String idTramite,
			final String idioma) {
		final List<DefinicionProcedimientoCP> res = new ArrayList<>();

		final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
		dp.setIdentificador("PROC1");
		dp.setDescripcion("Procedimiento 1");
		dp.setIdProcedimientoSIA("SIA1");
		dp.setOrganoResponsableDir3("RespDIR3");

		res.add(dp);
		return res;
	}

}
