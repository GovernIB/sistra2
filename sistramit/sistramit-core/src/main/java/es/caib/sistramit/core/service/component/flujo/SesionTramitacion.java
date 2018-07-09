package es.caib.sistramit.core.service.component.flujo;

/**
 * Componente que permite generar una sesion. Se aisla en este componente para
 * generar en una transaccion unica la creación del id sesion que permitira
 * enlazar el log. La creacion del tramite en si se generara despues. Permite
 * generar log antes de la creación efectiva del trámite.
 *
 * @author Indra
 *
 */
public interface SesionTramitacion {

    /**
     * Genera sesion de tramitacion.
     *
     * @return Id sesion tramitacion
     */
    String generarSesionTramitacion();

}
