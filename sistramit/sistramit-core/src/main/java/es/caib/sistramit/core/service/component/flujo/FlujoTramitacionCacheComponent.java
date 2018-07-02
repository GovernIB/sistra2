package es.caib.sistramit.core.service.component.flujo;

public interface FlujoTramitacionCacheComponent {

    void put(String idSesionTramitacion,
            FlujoTramitacionComponent flujoTramitacionComponent);

    FlujoTramitacionComponent get(String idSesionTramitacion);

    void purgar();

}