package es.caib.sistrages.core.service.component;

import java.util.List;

import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;

/**
 * Componentes ValidadorComponent.
 *
 * @author indra
 *
 */
public interface ValidadorComponent {

	List<ErrorValidacion> comprobarVersionTramite(Long id, final String idioma);

	List<ErrorValidacion> comprobarVersionTramite(TramiteVersion tramiteVersion, final String idioma);

}