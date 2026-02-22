package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna;

import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoNivelSeguridad;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuración del plugin de firma web para un método y nivel de seguridad.
 * En función de esta configuración se determinará el perfil de firma a utilizar para ese nivel de firma.
 */
public class PluginFirmaWebMetodoNivelConfig {

    /** Método de autenticación. */
    private TipoMetodoAutenticacion metodoAutenticacion;

    /** Niveles de seguridad. */
    private List<TipoNivelSeguridad> nivelesSeguridad = new ArrayList<>();

    /** Perfil firma con verificación. */
    private PluginFirmaWebPerfilFirmaConfig perfilFirmaConVerificacion;

    /** Perfil firma sin verificación. */
    private PluginFirmaWebPerfilFirmaConfig perfilFirmaSinVerificacion;

    public TipoMetodoAutenticacion getMetodoAutenticacion() {
        return metodoAutenticacion;
    }

    public void setMetodoAutenticacion(TipoMetodoAutenticacion metodoAutenticacion) {
        this.metodoAutenticacion = metodoAutenticacion;
    }

    public List<TipoNivelSeguridad> getNivelesSeguridad() {
        return nivelesSeguridad;
    }

    public void setNivelesSeguridad(List<TipoNivelSeguridad> nivelesSeguridad) {
        this.nivelesSeguridad = nivelesSeguridad;
    }

    public PluginFirmaWebPerfilFirmaConfig getPerfilFirmaConVerificacion() {
        return perfilFirmaConVerificacion;
    }

    public void setPerfilFirmaConVerificacion(PluginFirmaWebPerfilFirmaConfig perfilFirmaConVerificacion) {
        this.perfilFirmaConVerificacion = perfilFirmaConVerificacion;
    }

    public PluginFirmaWebPerfilFirmaConfig getPerfilFirmaSinVerificacion() {
        return perfilFirmaSinVerificacion;
    }

    public void setPerfilFirmaSinVerificacion(PluginFirmaWebPerfilFirmaConfig perfilFirmaSinVerificacion) {
        this.perfilFirmaSinVerificacion = perfilFirmaSinVerificacion;
    }

}
