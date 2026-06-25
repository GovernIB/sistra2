import es.caib.sistra2.commons.plugins.digitalizacion.api.InfoPersonaDigitalizacion;
import es.caib.sistra2.commons.plugins.digitalizacion.api.InfoSesionDigitalizacion;
import es.caib.sistra2.commons.plugins.digitalizacion.api.ResultadoDigitalizacion;
import es.caib.sistra2.commons.plugins.digitalizacion.digitalib.ComponenteDigitalibPlugin;
import org.apache.commons.io.IOUtils;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws Exception {

        final String classname = "es.caib.sistra2.commons.plugins.digitalizacion.digitalib.ComponenteDigitalibPlugin";
        final String prefijoGlobal = "es.caib.sistra2.";
        final String prefijoPlugin = "pluginsib.digitalizacion.digitalib.";

        final Properties prop = new Properties();
        prop.put(prefijoGlobal + prefijoPlugin + "debug", "true");
        prop.put(prefijoGlobal + prefijoPlugin + "iframe", "false");
        prop.put(prefijoGlobal + prefijoPlugin + "profileCode", "APP_ESCAN_FIRSER");
        prop.put(prefijoGlobal + prefijoPlugin + "endpoint", "https://dev.caib.es/digitalibapi/interna");
        prop.put(prefijoGlobal + prefijoPlugin + "username", "$sistra2_digitalib_dev");
        prop.put(prefijoGlobal + prefijoPlugin + "password", "sistra2_digitalib_dev");

        final ComponenteDigitalibPlugin plg = (ComponenteDigitalibPlugin) PluginsManager
                .instancePluginByClassName(classname, prefijoGlobal, prop);


        InfoSesionDigitalizacion infoSesion = new InfoSesionDigitalizacion();
        infoSesion.setNombreDocumento("documento1");
        InfoPersonaDigitalizacion ciudadano = new InfoPersonaDigitalizacion();
        ciudadano.setNombre("nombre");
        ciudadano.setApellido1("apellido1");
        ciudadano.setApellido2("apellido2");
        ciudadano.setNif("11111111H");
        infoSesion.setCiudadano(ciudadano);
        infoSesion.setCodigoDIR3("codigoDIR3");
        infoSesion.setEntidad("entidad");
        infoSesion.setIdioma("es");

        InfoPersonaDigitalizacion funcionarioHabilitado = new InfoPersonaDigitalizacion();
        funcionarioHabilitado.setNombre("Victor");
        funcionarioHabilitado.setApellido1("apellido1");
        funcionarioHabilitado.setApellido2("apellido2");
        funcionarioHabilitado.setNif("00000000T");
        funcionarioHabilitado.setUserName("u0001");
        infoSesion.setFuncionarioHabilitado(funcionarioHabilitado);


        String idSesion = plg.generarSesionDigitalizacion(infoSesion);
        System.out.println("idSesion: " + idSesion);

        String urlDIB = plg.iniciarSesionDigitalizacion(idSesion, "https://google.com");
        System.out.println("urlDIB: " + urlDIB);

        System.out.println("Realice escaneo y pulse enter para continuar");
        Scanner lectura = new Scanner (System.in);
        lectura.nextLine();

        ResultadoDigitalizacion result = plg.obtenerResultadoDigitalizacion(idSesion);
        System.out.println("result: digitalizado = " + result.isDigitalizado() + ", nombreFichero = " + result.getNombreFichero() + ", mensajeError = " + result.getMensajeError());

        if (result.isDigitalizado()) {
            File tempFile = File.createTempFile("DIB", ".pdf");
            ByteArrayInputStream bis = new ByteArrayInputStream(result.getDocumento());
            FileOutputStream fos = new FileOutputStream(tempFile);
            IOUtils.copy(bis, fos);
            fos.close();
            bis.close();
            System.out.println("Fichero guardado en: " + tempFile.getAbsolutePath());
        }

    }

}
