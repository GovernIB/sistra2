import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.CatalogoProcedimientosRolsacPlugin;

public class TestPluginRolsac {

	public static void main(final String[] args) throws Exception {

		final String classname = "es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.CatalogoProcedimientosRolsacPlugin";
		final String prefijoGlobal = "es.caib.sistra2.";
		final String prefijoPlugin = "pluginsib.catalogoprocedimientos.rolsac.";

		final Properties prop = new Properties();
		prop.put(prefijoGlobal + prefijoPlugin + "usr", "u83491");
		prop.put(prefijoGlobal + prefijoPlugin + "pwd", "13ch0n3s");
		prop.put(prefijoGlobal + prefijoPlugin + "url", "http://caibter.indra.es/rolsac/api/rest/v1");
		prop.put(prefijoGlobal + prefijoPlugin + "reintentos", "5");
		prop.put(prefijoGlobal + prefijoPlugin + "identificadorPlataformaSistra2", "SISTRA2");
		prop.put(prefijoGlobal + prefijoPlugin + "urlSeucaib", "http://caibter.indra.es/seucaib");

		final CatalogoProcedimientosRolsacPlugin plg = (CatalogoProcedimientosRolsacPlugin) PluginsManager
				.instancePluginByClassName(classname, prefijoGlobal, prop);

		// Sin Lopd
		// final DefinicionTramiteCP res = plg.obtenerDefinicionTramite("2737318",
		// false, "es");

		// Con lopd
		final DefinicionTramiteCP res = plg.obtenerDefinicionTramite("2760199", false, "es");

		System.out.println(res.getDescripcion());

	}

}
