package es.caib.sistramit.core.service.component.flujo.reglas;

import java.util.List;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.ApplicationContextProvider;
import es.caib.sistramit.core.service.component.flujo.ModificacionesFlujo;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Acceso al contexto de una regla de tramitación. Habilita acceso a la lógica
 * del flujo desde la regla.
 *
 * @author Indra
 *
 */
public final class ContextoReglaTramitacion {

    /** Lógica de modificación del flujo. */
    private final ModificacionesFlujo modificacionesFlujo;

    /** Datos de la sesión de tramitación. */
    private final DatosSesionTramitacion datosSesion;

    /**
     * Indica si se debe redirigir hacia el siguiente paso (solo para fase post
     * accion paso).
     */
    private boolean pasarSiguientePaso;

    /**
     * Constructor.
     *
     * @param pDatosSesion
     *            Datos de la sesión de tramitación.
     */
    public ContextoReglaTramitacion(final DatosSesionTramitacion pDatosSesion) {
        super();
        datosSesion = pDatosSesion;
        modificacionesFlujo = (ModificacionesFlujo) ApplicationContextProvider
                .getApplicationContext().getBean("modificacionesFlujo");
    }

    /**
     * Definición trámite.
     *
     * @return Definición trámite.
     */
    public DefinicionTramiteSTG getDefinicionTramite() {
        return this.datosSesion.getDefinicionTramite();
    }

    /**
     * Añade paso de tramitación.
     *
     * @param idPaso
     *            Id paso
     * @param tipo
     *            Tipo paso
     */
    public void addPaso(final String idPaso, final TypePaso tipo) {
        modificacionesFlujo.addPasoTramitacion(datosSesion, idPaso, tipo);
    }

    /**
     * Elimina paso de tramitación.
     *
     */
    public void removeUltimoPaso() {
        modificacionesFlujo.removeUltimoPasoTramitacion(datosSesion);
    }

    /**
     * Invalida paso de tramitación marcandolo para revisar.
     *
     * @param idPaso
     *            Id paso
     */
    public void invalidarPaso(final String idPaso) {
        modificacionesFlujo.invalidarPasoTramitacion(datosSesion, idPaso);
    }

    /**
     * Establece accesiblidad del paso.
     *
     * @param idPaso
     *            Id paso
     * @param accesible
     *            Indica si es accesible
     */
    public void setAccesibilidadPaso(final String idPaso,
            final boolean accesible) {
        modificacionesFlujo.establecerAccesibilidadPaso(datosSesion, idPaso,
                accesible);
    }

    /**
     * Establece si el paso es solo lectura.
     *
     * @param idPaso
     *            Id paso
     * @param soloLectura
     *            Indica si es solo lectura
     */
    public void setSoloLecturaPaso(final String idPaso,
            final boolean soloLectura) {
        modificacionesFlujo.establecerSoloLecturaPaso(datosSesion, idPaso,
                soloLectura);
    }

    /**
     * Obtiene lista de pasos.
     *
     * @return Lista de pasos
     */
    public List<DatosPaso> getListaPasos() {
        return datosSesion.getDatosTramite().getDatosPasos();
    }

    /**
     * Obtiene id paso actual.
     *
     * @return id paso actual.
     */
    public String getIdPasoActual() {
        return datosSesion.getDatosTramite().getIdPasoActual();
    }

    /**
     * Obtiene id siguiente paso.
     *
     * @return Id paso
     */
    public String getIdPasoSiguiente() {
        return datosSesion.getDatosTramite().getIdPasoSiguiente();
    }

    /**
     * Obtiene los pasos por tipo de paso.
     *
     * @param tipoPaso
     *            Tipo de paso
     *
     * @return Lista de pasos del tipo indicado
     */
    public List<DatosPaso> getPasos(final TypePaso tipoPaso) {
        return datosSesion.getDatosTramite().getDatosPasos(tipoPaso);
    }

    /**
     * Obtiene el paso por id de paso.
     *
     * @param idPaso
     *            id de paso
     *
     * @return Paso
     */
    public DatosPaso getPaso(final String idPaso) {
        return datosSesion.getDatosTramite().getDatosPaso(idPaso);
    }

    /**
     * Indica si se debe redirigir hacia el siguiente paso (solo para fase post
     * accion paso).
     *
     * @return true si debe pasar automaticamente al siguiente paso
     */
    public boolean isPasarSiguientePaso() {
        return pasarSiguientePaso;
    }

    /**
     * Indica si se debe redirigir hacia el siguiente paso (solo para fase post
     * accion paso).
     *
     * @param pPasarSiguientePaso
     *            true si debe pasar automaticamente al siguiente paso
     */
    public void setPasarSiguientePaso(final boolean pPasarSiguientePaso) {
        pasarSiguientePaso = pPasarSiguientePaso;
    }

    /**
     * Obtiene variables flujo paso.
     *
     * @param idPaso
     *            Id paso
     * @return variables flujo paso.
     */
    public VariablesFlujo obtenerVariablesFlujo(final String idPaso) {
        return modificacionesFlujo.generarVariablesFlujo(datosSesion, idPaso);
    }

}
