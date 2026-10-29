import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoNivelSeguridad;
import es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.PluginFirmaWebConfig;
import es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.PluginFirmaWebMetodoNivelConfig;
import es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.PluginFirmaWebPerfilFirmaConfig;
import es.caib.sistra2.commons.utils.JSONUtil;

import java.util.List;

public class ConfiguradorPluginFirmaWeb {

    // ------- [ CONFIGURACION CON PLACEHOLDER ] -------------------------

    // Perfiles de firma
    public static final String PROFILE_FH = "FIRMASISTRA";
    public static final String PROFILE_NOVERIF = "FIRMASISTRA_noverifNIF";
    public static final String PROFILE_VERIF = "FIRMASISTRA";

    // Usuarios según métodos
    public static final String USER_FH = "${config.es.caib.sistra2.fh.portafib.usr}";
    public static final String USER_FA_CF = "${config.es.caib.sistra2.fa_cf.portafib.usr}";
    public static final String USER_FA_AF = "${config.es.caib.sistra2.fa_af.portafib.usr}";
    public static final String USER_AF = "${config.es.caib.sistra2.af.portafib.usr}";
    public static final String USER_CF = "${config.es.caib.sistra2.cf.portafib.usr}";
    public static final String USER_FA = "${config.es.caib.sistra2.fa.portafib.usr}";

    // Pwds de los usuarios con placeholder
    public static final String PWD_FH= "${config.es.caib.sistra2.fh.portafib.pwd}";
    public static final String PWD_FA_CF = "${config.es.caib.sistra2.fa_cf.portafib.pwd}";
    public static final String PWD_FA_AF = "${config.es.caib.sistra2.fa_af.portafib.pwd}";
    public static final String PWD_AF = "${config.es.caib.sistra2.af.portafib.pwd}";
    public static final String PWD_CF = "${config.es.caib.sistra2.cf.portafib.pwd}";
    public static final String PWD_FA = "${config.es.caib.sistra2.fa.portafib.pwd}";

    // Main para generar la configuración del plugin de firma web en formato JSON.
    public static void main(String[] args) throws Exception{

        // Genera la configuración en formato JSON
        String configJson = generateConfigJSON(true);
        System.out.println(configJson);
        System.out.println("------------------------------------------------------------------------------------");


        // Convierte desde JSON a objeto
        PluginFirmaWebConfig config2 = (PluginFirmaWebConfig) JSONUtil.fromJSON(configJson, PluginFirmaWebConfig.class);
        String configJson2 = JSONUtil.toJSON(config2, true);
        // System.out.println(configJson2);
        // System.out.println("------------------------------------------------------------------------------------");

        // Verifica si son iguales
        System.out.println(configJson2.equals(configJson) ? "OK: JSONs iguales" : "ERROR: JSONs diferentes");

    }

    /**
     * Genera la configuración del plugin de firma web en formato JSON.
     *
     * @return Configuración en formato JSON.
     */
    public static String generateConfigJSON(boolean prettyPrint) throws Exception {

        PluginFirmaWebConfig config = new PluginFirmaWebConfig();

        // Configuración para fh
        config.setPerfilFirmaFH(new PluginFirmaWebPerfilFirmaConfig(PROFILE_FH, USER_FH, PWD_FH));

        // Configuración por nivel aut y nivel seguridad
        List<PluginFirmaWebMetodoNivelConfig> configAutNivel = config.getConfigAutNivel();
        // Configuración para CLAVE MOVIL
        configAutNivel.add(generarConfigClaveMovil());
        // Configuración para CLAVE PERMANENTE con Bajo/Sustancial
        configAutNivel.add(generarConfigClavePermanenteBajoSustancial());
        // Configuración para CLAVE PERMANENTE con Sustancial firma con certificado
        configAutNivel.add(generarConfigClavePermanenteSustancialCert());
        // Configuración para CERTIFICADO con bajo y sustancial
        configAutNivel.add(generarConfigCertificadoBajoSustancial());
        // Configuración para CERTIFICADO con sustancial certificado y alto
        configAutNivel.add(generarConfigCertificadoSustancialCertAlto());

        // Genera JSON
        String configJson = JSONUtil.toJSON(config, prettyPrint);

        // Retorna el JSON de configuración
        return configJson;
    }

    private static PluginFirmaWebMetodoNivelConfig generarConfigCertificadoSustancialCertAlto() {
        PluginFirmaWebMetodoNivelConfig certSustancialCertAltoCfg = new PluginFirmaWebMetodoNivelConfig();
        // - Metodo: CLAVE_MOVIL
        certSustancialCertAltoCfg.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_CERTIFICADO);
        // - Nivel: BAJO y SUSTANCIAL
        certSustancialCertAltoCfg.getNivelesSeguridad().add(TipoNivelSeguridad.SUSTANCIAL_CERTIFICADO);
        certSustancialCertAltoCfg.getNivelesSeguridad().add(TipoNivelSeguridad.ALTO);
        // - Perfil sin verificación
        certSustancialCertAltoCfg.setPerfilFirmaSinVerificacion(new PluginFirmaWebPerfilFirmaConfig(PROFILE_NOVERIF, USER_FA_AF, PWD_FA_AF));
        // - Perfil con verificación
        certSustancialCertAltoCfg.setPerfilFirmaConVerificacion(new PluginFirmaWebPerfilFirmaConfig(PROFILE_VERIF, USER_AF, PWD_AF));
        return certSustancialCertAltoCfg;
    }

    private static PluginFirmaWebMetodoNivelConfig generarConfigCertificadoBajoSustancial() {
        PluginFirmaWebMetodoNivelConfig certBajoSustancialCfg = new PluginFirmaWebMetodoNivelConfig();
        // - Metodo: CLAVE_MOVIL
        certBajoSustancialCfg.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_CERTIFICADO);
        // - Nivel: BAJO y SUSTANCIAL
        certBajoSustancialCfg.getNivelesSeguridad().add(TipoNivelSeguridad.BAJO);
        certBajoSustancialCfg.getNivelesSeguridad().add(TipoNivelSeguridad.SUSTANCIAL);
        // - Perfil sin verificación
        certBajoSustancialCfg.setPerfilFirmaSinVerificacion(new PluginFirmaWebPerfilFirmaConfig(PROFILE_NOVERIF, USER_FA_AF, PWD_FA_AF));
        // - Perfil con verificación
        certBajoSustancialCfg.setPerfilFirmaConVerificacion(null);
        return certBajoSustancialCfg;
    }

    private static PluginFirmaWebMetodoNivelConfig generarConfigClavePermanenteSustancialCert() {
        PluginFirmaWebMetodoNivelConfig clavePermanenteCertCfg = new PluginFirmaWebMetodoNivelConfig();
        // - Metodo: CLAVE_MOVIL
        clavePermanenteCertCfg.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_PERMANENTE);
        // - Nivel: SUSTANCIAL CERTIFICADO
        clavePermanenteCertCfg.getNivelesSeguridad().add(TipoNivelSeguridad.SUSTANCIAL_CERTIFICADO);
        // - Perfil sin verificación
        clavePermanenteCertCfg.setPerfilFirmaSinVerificacion(new PluginFirmaWebPerfilFirmaConfig(PROFILE_NOVERIF, USER_FA_CF, PWD_FA_CF));
        // - Perfil con verificación
        clavePermanenteCertCfg.setPerfilFirmaConVerificacion(new PluginFirmaWebPerfilFirmaConfig(PROFILE_VERIF, USER_CF, PWD_CF));
        return clavePermanenteCertCfg;
    }

    private static PluginFirmaWebMetodoNivelConfig generarConfigClavePermanenteBajoSustancial() {
        PluginFirmaWebMetodoNivelConfig clavePermanenteCfg = new PluginFirmaWebMetodoNivelConfig();
        // - Metodo: CLAVE_MOVIL
        clavePermanenteCfg.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_PERMANENTE);
        // - Nivel: BAJO y SUSTANCIAL
        clavePermanenteCfg.getNivelesSeguridad().add(TipoNivelSeguridad.BAJO);
        clavePermanenteCfg.getNivelesSeguridad().add(TipoNivelSeguridad.SUSTANCIAL);
        // - Perfil sin verificación
        clavePermanenteCfg.setPerfilFirmaSinVerificacion(new PluginFirmaWebPerfilFirmaConfig(PROFILE_NOVERIF, USER_FA_CF, PWD_FA_CF));
        // - Perfil con verificación
        clavePermanenteCfg.setPerfilFirmaConVerificacion(null);
        return clavePermanenteCfg;
    }

    private static PluginFirmaWebMetodoNivelConfig generarConfigClaveMovil() {
        PluginFirmaWebMetodoNivelConfig claveMovilCfg = new PluginFirmaWebMetodoNivelConfig();
        // - Metodo: CLAVE_MOVIL
        claveMovilCfg.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_MOVIL);
        // - Nivel: BAJO y SUSTANCIAL
        claveMovilCfg.getNivelesSeguridad().add(TipoNivelSeguridad.BAJO);
        claveMovilCfg.getNivelesSeguridad().add(TipoNivelSeguridad.SUSTANCIAL);
        // - Perfil sin verificación
        claveMovilCfg.setPerfilFirmaSinVerificacion(new PluginFirmaWebPerfilFirmaConfig(PROFILE_NOVERIF, USER_FA, PWD_FA));
        // - Perfil con verificación
        claveMovilCfg.setPerfilFirmaConVerificacion(null);
        return claveMovilCfg;
    }



}
