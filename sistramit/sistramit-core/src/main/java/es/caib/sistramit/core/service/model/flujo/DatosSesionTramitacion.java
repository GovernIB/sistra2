package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 *
 * Datos de la sesión de tramitación que se mantienen en memoria (id, estado
 * pasos, definición trámite, etc.).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosSesionTramitacion implements Serializable {

    /**
     * Definicion del trámite en el GTT.
     */
    private final DefinicionTramiteSTG definicionTramite;

    /**
     * Datos de la instancia del trámite.
     */
    private final DatosTramite datosTramite = new DatosTramite();

    /**
     * Constructor.
     *
     * @param pDefinicionTramite
     *            Parámetro definicion tramite
     */
    public DatosSesionTramitacion(
            final DefinicionTramiteSTG pDefinicionTramite) {
        super();
        definicionTramite = pDefinicionTramite;
    }

    /**
     * Método de acceso a definicionTramite.
     *
     * @return definicionTramite
     */
    public DefinicionTramiteSTG getDefinicionTramite() {
        return definicionTramite;
    }

    /**
     * Método de acceso a datosTramite.
     *
     * @return datosTramite
     */
    public DatosTramite getDatosTramite() {
        return datosTramite;
    }

}
