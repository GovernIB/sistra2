package es.caib.sistramit.core.service.component.system;

import es.caib.sistramit.core.service.model.system.FlujoTramitacionCacheIntf;

public interface FlujoTramitacionCacheComponent {

	void put(String idSesionTramitacion, FlujoTramitacionCacheIntf flujoTramitacionComponent);

	FlujoTramitacionCacheIntf get(String idSesionTramitacion);

	void purgarFlujosTramitacion();

	void remove(String idSesionTramitacion);

}