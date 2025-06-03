package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Datos internos paso Anexar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoAnexar extends DatosInternosPasoReferencia {

    /**
     * Sesiones digitalización (id documento - sesion digitalización).
     */
    private final Map<String, String> sesionesDigitalizacion = new HashMap<>();

    /**
     * Constructor.
     *
     * @param idSesionTramitacion
     *            Parámetro id sesion tramitacion
     * @param idPaso
     *            Parámetro id paso
     */
    public DatosInternosPasoAnexar(final String idSesionTramitacion,
            final String idPaso) {
        this.setTipo(TypePaso.ANEXAR);
        this.setIdSesionTramitacion(idSesionTramitacion);
        this.setIdPaso(idPaso);
    }

    /**
     * Guarda sesion digitalizacion asociada a un documento.
     *
     * @param idDocumento
     *            id documento
     * @param idSesionDigitalizacion
     *            sesion digitalizacion
     */
    public void guardarSesionDigitalizacion(String idDocumento, String idSesionDigitalizacion) {
        sesionesDigitalizacion.put(idDocumento, idSesionDigitalizacion);
    }

    /**
     * Recupera sesion digitalizacion asociada a un documento.
     *
     * @param idDocumento
     *            id documento
     * @return id
     */
    public String recuperarSesionDigitalizacion(String idDocumento) {
        String sesionDigi = sesionesDigitalizacion.get(idDocumento);
        if (sesionDigi != null) {
            sesionesDigitalizacion.remove(idDocumento);
        }
        return sesionDigi;
    }

}
