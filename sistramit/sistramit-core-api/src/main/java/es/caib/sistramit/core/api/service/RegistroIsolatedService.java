package es.caib.sistramit.core.api.service;

import es.caib.sistramit.core.api.model.flujo.RegistroIsolatedData;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;

/**
 * Service para realizar el registro de forma isolated (fuera de transacción)
 */
public interface RegistroIsolatedService {

    ResultadoRegistrar registrar(String idSesionTramitacion, RegistroIsolatedData registroIsolated, boolean reintentar);

}
