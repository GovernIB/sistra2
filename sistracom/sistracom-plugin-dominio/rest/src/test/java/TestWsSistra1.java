import java.util.Properties;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.DominioPluginRest;

public class TestWsSistra1 {

	public static void main(final String[] args) throws Exception {

		final Properties properties = new Properties();
		properties.setProperty("pluginsib.dominio.ws.logCallsSistra1", "true");
		final DominioPluginRest plg = new DominioPluginRest("pluginsib.dominio.ws.", properties);

		final ValoresDominio vd = plg.invocarDominio("PINBALADMIN_ENTITATS",
				"[SISTRA1]http://pinbaladmin.fundaciobit.org/pinbaladmin/ws/dominisistra/entitats", null, null, null,
				10L);

		System.out.println(vd.getNumeroFilas());

	}

}
