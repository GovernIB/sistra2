package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;

import java.util.HashMap;
import java.util.Map;


public class MockFirma {

    private static Map<String, String> sesionesFirma = new HashMap<>();

    private static Map<String, FicheroFirmado> firmas = new HashMap<>();

    public static void setSesionFirma(String idAnexo, int instancia,
            String sesion) {
        sesionesFirma.put(idAnexo + "-" + instancia, sesion);
    }

    public static String getSesionFirma(String idAnexo, int instancia) {
        return sesionesFirma.get(idAnexo + "-" + instancia);
    }

    public static void setFirma(String idAnexo, int instancia,
            FicheroFirmado fic) {
        firmas.put(idAnexo + "-" + instancia, fic);
    }

    public static FicheroFirmado getFirma(String idAnexo, int instancia) {
        return firmas.get(idAnexo + "-" + instancia);
    }
}
