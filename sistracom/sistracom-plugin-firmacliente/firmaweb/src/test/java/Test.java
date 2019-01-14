import java.util.Properties;

import es.caib.sistra2.commons.plugins.firmacliente.api.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.api.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.api.InfoSesionFirma;
import es.caib.sistra2.commons.plugins.firmacliente.firmaweb.ComponenteFirmaSimpleWebPlugin;

public class Test {

    public static void main(String[] args) throws FirmaPluginException {

        final Properties properties = new Properties();
        properties.put(IFirmaPlugin.FIRMACLIENTE_BASE_PROPERTY + "url",
                "http://portafib2.fundaciobit.org/portafib/common/rest/apifirmawebsimple/v1/");
        properties.put(IFirmaPlugin.FIRMACLIENTE_BASE_PROPERTY + "usr",
                "ibsalut_sistra2");
        properties.put(IFirmaPlugin.FIRMACLIENTE_BASE_PROPERTY + "pwd",
                "ibsalut_sistra2");

        final ComponenteFirmaSimpleWebPlugin plg = new ComponenteFirmaSimpleWebPlugin(
                IFirmaPlugin.FIRMACLIENTE_BASE_PROPERTY, properties);

        final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
        infoSesionFirma.setEntidad("12345678C");
        infoSesionFirma.setIdioma("ca");
        infoSesionFirma.setNombreUsuario("jmico");
        final String sf = plg.generarSesionFirma(infoSesionFirma);

        System.out.println(sf);

    }

}
