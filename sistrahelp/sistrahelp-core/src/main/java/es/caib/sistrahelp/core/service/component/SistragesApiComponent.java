package es.caib.sistrahelp.core.service.component;

import java.util.List;

import es.caib.sistrages.rest.api.interna.RPermisoHelpDesk;
import es.caib.sistrahelp.core.api.model.Entidad;

/**
 * Acceso a componente SISTRAGES.
 *
 * @author Indra
 *
 */
public interface SistragesApiComponent {

	/**
	 * Obtener permisos helpdesk.
	 *
	 * @return lista de permisos
	 */
	List<RPermisoHelpDesk> obtenerPermisosHelpdesk();

	/**
	 * Obtener datos entidad.
	 *
	 * @param idEntidad
	 *            id entidad
	 * @return entidad
	 */
	Entidad obtenerDatosEntidad(String idEntidad);

}
