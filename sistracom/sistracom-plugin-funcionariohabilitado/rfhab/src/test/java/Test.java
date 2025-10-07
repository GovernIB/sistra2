import es.caib.sistra2.commons.plugins.funcionariohabilitado.rfhab.ComponenteRFHabPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import java.util.Properties;

public class Test {

    public static void main(String[] args) throws Exception {

        final String classname = "es.caib.sistra2.commons.plugins.funcionariohabilitado.rfhab.ComponenteRFHabPlugin";
        final String prefijoGlobal = "es.caib.sistra2.";
        final String prefijoPlugin = ComponenteRFHabPlugin.FUNCIONARIOHABILITADO_BASE_PROPERTY + ComponenteRFHabPlugin.IMPLEMENTATION_BASE_PROPERTY;

        // 'https://governdigital.fundaciobit.org/rfhabapi/interna/secure/activitat/registre?language=ca&funcionari=15647325N&tipus=2&data=2025-08-31T06%3A15%3A00%2B00%3A00&registre=23&idactuaciotramitfh=e0f7d461-350a-449f-984e-2403e70d8031' \

        final Properties prop = new Properties();
        prop.put(prefijoGlobal + prefijoPlugin + "url", "https://governdigital.fundaciobit.org/rfhabapi/interna/secure/activitat/registre");
        prop.put(prefijoGlobal + prefijoPlugin + "username", "testapp");
        prop.put(prefijoGlobal + prefijoPlugin + "password", "testapp");

        final ComponenteRFHabPlugin plg = (ComponenteRFHabPlugin) PluginsManager
                .instancePluginByClassName(classname, prefijoGlobal, prop);
        plg.avisarFinalizacionTramite("15647325N", "f17894cc-3fca-46e1-9c68-ccef879e79ba", "123", new java.util.Date(), "ca", true);

        System.out.println("Avisado");



    }

}
