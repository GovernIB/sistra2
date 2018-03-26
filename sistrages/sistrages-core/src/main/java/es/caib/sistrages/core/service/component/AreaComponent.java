package es.caib.sistrages.core.service.component;

import es.caib.sistrages.core.api.model.Area;

/**
 * Componentes area.
 *
 * @author indra
 *
 */
public interface AreaComponent {

	/**
	 * Añade area a entidad.
	 * 
	 * @param idEntidad
	 *            Id entidad
	 * @param area
	 *            Datos area
	 */
	void add(final Long idEntidad, Area area);

}