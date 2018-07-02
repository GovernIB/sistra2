package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
/**
 * Servicios Api Rest.
 *
 * @author Indra
 *
 */
public interface RestApiInternaService {

    public String test(String echo);
    public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro);
	public List<Plugin> listPlugin(TypeAmbito ambito, Long idEntidad, String filtro);

}
