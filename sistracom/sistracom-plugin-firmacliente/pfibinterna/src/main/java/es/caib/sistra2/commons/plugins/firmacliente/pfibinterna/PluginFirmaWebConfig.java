package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuración del plugin de firma web para un método y nivel de seguridad.
 * En función de esta configuración se determinará el perfil de firma a utilizar para ese nivel de firma.
 */
public class PluginFirmaWebConfig {

    /** Configuración de métodos y niveles de seguridad. */
    private List<PluginFirmaWebMetodoNivelConfig> configAutNivel = new ArrayList<>();

    /** Perfil para FH. */
    private PluginFirmaWebPerfilFirmaConfig perfilFirmaFH;

    public List<PluginFirmaWebMetodoNivelConfig> getConfigAutNivel() {
        return configAutNivel;
    }

    public void setConfigAutNivel(List<PluginFirmaWebMetodoNivelConfig> configAutNivel) {
        this.configAutNivel = configAutNivel;
    }

    public PluginFirmaWebPerfilFirmaConfig getPerfilFirmaFH() {
        return perfilFirmaFH;
    }

    public void setPerfilFirmaFH(PluginFirmaWebPerfilFirmaConfig perfilFirmaFH) {
        this.perfilFirmaFH = perfilFirmaFH;
    }
}
