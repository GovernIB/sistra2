package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistramit.core.api.model.flujo.types.TypeDestino;

/**
 * Datos para realizar el registro isolated.
 */
public class RegistroIsolatedData {

    private String idSesionRegistro;

    private boolean debugEnabled;

    private TypeDestino destino;

    private String codigoEntidad;

    private String idTramite;

    private int versionTramite;

    private String codigoProcedimiento;

    private String codigoSIA;

    private boolean modoEntregaHabilitado;

    private boolean modoEntregaInmediato;

    private String idEnvioRemoto;

    private AsientoRegistral asiento;

    public TypeDestino getDestino() {
        return destino;
    }

    public void setDestino(TypeDestino destino) {
        this.destino = destino;
    }

    public String getIdSesionRegistro() {
        return idSesionRegistro;
    }

    public void setIdSesionRegistro(String idSesionRegistro) {
        this.idSesionRegistro = idSesionRegistro;
    }

    public boolean isModoEntregaHabilitado() {
        return modoEntregaHabilitado;
    }

    public void setModoEntregaHabilitado(boolean modoEntregaHabilitado) {
        this.modoEntregaHabilitado = modoEntregaHabilitado;
    }

    public String getIdEnvioRemoto() {
        return idEnvioRemoto;
    }

    public void setIdEnvioRemoto(String idEnvioRemoto) {
        this.idEnvioRemoto = idEnvioRemoto;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getCodigoProcedimiento() {
        return codigoProcedimiento;
    }

    public void setCodigoProcedimiento(String codigoProcedimiento) {
        this.codigoProcedimiento = codigoProcedimiento;
    }

    public String getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(String codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public int getVersionTramite() {
        return versionTramite;
    }

    public void setVersionTramite(int versionTramite) {
        this.versionTramite = versionTramite;
    }

    public boolean isModoEntregaInmediato() {
        return modoEntregaInmediato;
    }

    public void setModoEntregaInmediato(boolean modoEntregaInmediato) {
        this.modoEntregaInmediato = modoEntregaInmediato;
    }

    public AsientoRegistral getAsiento() {
        return asiento;
    }

    public void setAsiento(AsientoRegistral asiento) {
        this.asiento = asiento;
    }
}
