package es.caib.sistramit.core.service.component.flujo;

import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;

/**
 * Interfaz de un controlador de flujo de tramitación.
 *
 * Contiene la lógica de un flujo de tramitación. Los datos del propio flujo
 * (definición trámite, estado pasos, etc.) se pasan como parámetro de cada
 * método. La lógica de los pasos esta en un controlador específico para el
 * paso.
 *
 * @author Indra
 *
 */
public interface ControladorFlujoTramitacion {

    /**
     *
     * Crea un nuevo trámite.
     *
     * @param datosSesion
     *            Datos de la sesión de tramitación con la información
     *            inicializada.
     * @return Indica paso actual
     */
    ResultadoIrAPaso iniciarTramite(final DatosSesionTramitacion datosSesion);

    /**
     * Carga un trámite existente.
     *
     * @param datosSesion
     *            Datos de la sesión de tramitación con la información
     *            inicializada.
     * @return Indica paso actual
     */
    ResultadoIrAPaso cargarTramite(final DatosSesionTramitacion datosSesion);

    /**
     * Va al paso indicado.
     *
     * @param datosSesion
     *            Datos de la sesión de tramitación
     * @param idPaso
     *            Identificador de paso al que se quiere ir.
     * @return Identificador del paso actual.
     */
    ResultadoIrAPaso irAPaso(final DatosSesionTramitacion datosSesion,
            String idPaso);

    /**
     * Realiza la acción indicada en el paso.
     *
     * @param datosSesion
     *            Datos de la sesión de tramitación
     * @param idPaso
     *            Identificador del paso.
     * @param accionPaso
     *            Acción a realizar en el paso (depende del paso y de la
     *            acción).
     * @param pParametros
     *            Parámetros del paso (depende del paso y de la acción).
     * @return Map con parámetros de retorno (depende del paso y de la acción).
     */
    ResultadoAccionPaso accionPaso(final DatosSesionTramitacion datosSesion,
            String idPaso, final TypeAccionPaso accionPaso,
            final ParametrosAccionPaso pParametros);

    /**
     * Obtiene detalle del estado actual del trámite para ser interpretado por
     * el front.
     *
     * @param datosSesion
     *            Datos de la sesión de tramitación
     * @return Detalle del trámite
     */
    DetalleTramite detalleTramite(final DatosSesionTramitacion datosSesion);

    /**
     * Obtiene detalle paso actual del trámite para ser interpretado por el
     * front.
     *
     * @param datosSesion
     *            Datos de la sesión de tramitación
     * @return Detalle del trámite
     */
    DetallePasos detallePasos(final DatosSesionTramitacion datosSesion);

    /**
     * Cancela trámite provocando su eliminación.
     *
     * @param datosSesion
     *            Datos de la sesión de tramitación
     */
    void cancelarTramite(final DatosSesionTramitacion datosSesion);

}
