package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna;

import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoNivelSeguridad;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuración del plugin de firma web para un método y nivel de seguridad.
 * En función de esta configuración se determinará el perfil de firma a utilizar para ese nivel de firma.
 */
public class PluginFirmaWebPerfilFirmaConfig {

    /** Perfil de firma. */
    private String perfil;

    /** Usuario perfil firma. */
    private String usuario;

    /** Password perfil firma. */
    private String password;

    public String getPerfil() {
        return perfil;
    }

    public PluginFirmaWebPerfilFirmaConfig() {
    }

    public PluginFirmaWebPerfilFirmaConfig(String perfil, String usuario, String password) {
        this.perfil = perfil;
        this.usuario = usuario;
        this.password = password;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
