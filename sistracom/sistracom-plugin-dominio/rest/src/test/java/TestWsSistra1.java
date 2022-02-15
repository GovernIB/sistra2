import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.DominioPluginRest;
import es.caib.sistra2.commons.plugins.dominio.rest.cxf.ParametrosDominio;

public class TestWsSistra1 {

	public static void main(final String[] args) throws Exception {

		final Properties properties = new Properties();
		properties.setProperty("pluginsib.dominio.ws.logCallsSistra1", "true");
		final DominioPluginRest plg = new DominioPluginRest("pluginsib.dominio.ws.", properties);

		ParametroDominio param1 = new ParametroDominio();
		param1.setCodigo("nif");
		param1.setValor("00000000T");

		final List<ParametroDominio> parametros = new ArrayList<>();
		parametros.add(param1);

		final ValoresDominio vd = plg.invocarDominio("CICONIF",
				"[SISTRA1]https://proves.caib.es/udit/integracion/sistra/services/SistraFacade", parametros, "$sistra_udit", "sistra_udit",
				10L);

		System.out.println(vd.getNumeroFilas());

		final Properties properties1 = new Properties();
		properties1.setProperty("pluginsib.dominio.ws.logCallsSistra1", "true");

		ParametroDominio param2 = new ParametroDominio();
		param2.setCodigo("idioma");
		param2.setValor("es");

		final List<ParametroDominio> parametros2 = new ArrayList<>();
		parametros.add(param2);

		final ValoresDominio vd2 = plg.invocarDominio("SC_FC_PES",
				"[SISTRA1][SOAP-ACTION=http://sap.com/xi/WebService/soap1.1]https://geminis.caib.es:52001/sap/xi/engine?type=entry&amp;version=3.0&amp;Sender.Service=RHAPSODY_HERMES_SYSTRA&amp;Interface=urn:es:caib:sistra:ws:v2:model:SistraFacade%5ESI_Comunicaciones_Systra_SAP", parametros, "IBS_RHOTIC", "interfaz",
				10L);

		System.out.println(vd.getNumeroFilas());

	}

}
