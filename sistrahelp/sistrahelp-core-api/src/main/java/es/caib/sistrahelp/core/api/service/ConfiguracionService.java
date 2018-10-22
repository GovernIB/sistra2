package es.caib.sistrahelp.core.api.service;

import es.caib.sistrahelp.core.api.model.ContenidoFichero;
import es.caib.sistrahelp.core.api.model.Entidad;

public interface ConfiguracionService {

	/**
	 * Obtener datos entidad.
	 *
	 * @param idEntidad
	 *            id de la entidad
	 * @return entidad
	 */
	public Entidad obtenerDatosEntidad(final String idEntidad);

	/**
	 * Obtiene el contenido del fichero
	 *
	 * @param fichero
	 *            fichero
	 * @return contenido del fichero
	 */
	public ContenidoFichero getContentFicheroByPath(final String fichero);

}
