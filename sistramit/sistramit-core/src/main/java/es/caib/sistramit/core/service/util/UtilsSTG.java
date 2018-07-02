package es.caib.sistramit.core.service.util;

import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Utilidades para manejar modelo de STG.
 *
 * @author Indra
 *
 */
public final class UtilsSTG {

    /**
     * Constructor.
     */
    private UtilsSTG() {
        super();
    }

    /**
     * Verifica si esta habilitado el debug.
     *
     * @param definicionTramite
     *            Definicion trámite.
     * @return debug habilitado
     */
    public static boolean isDebugEnabled(
            DefinicionTramiteSTG definicionTramite) {
        return definicionTramite.getDefinicionVersion().getControlAcceso()
                .isDebug();
    }

}
