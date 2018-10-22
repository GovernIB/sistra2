package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;

/**
 * Servicios Api Rest.
 *
 * @author Indra
 *
 */
public interface RestApiExternaService {

    String test(String echo);

    List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro);
}
