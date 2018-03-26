package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * Entidad service.
 *
 * @author Indra.
 *
 */
public interface EntidadService {

	public Entidad loadEntidad(Long idEntidad);

	/**
	 * Añade entidad por parte del superadministrador.
	 *
	 * @param entidad
	 *            Entidad con los datos requeridos por superadministrador.
	 */
	public void addEntidad(Entidad entidad);

	/**
	 * Actualización entidad por parte del superadministrador.
	 *
	 * @param entidad
	 *            Entidad con los datos requeridos por superadministrador.
	 */
	public void updateEntidadSuperAdministrador(Entidad entidad);

	/**
	 * Borrar entidad.
	 * 
	 * @param idEntidad
	 *            idEntidad
	 */
	public void removeEntidad(Long idEntidad);

	/**
	 * Listar entidades.
	 * 
	 * @param typeIdioma
	 *            Idioma
	 * @param filtro
	 *            filtro
	 * @return entidades
	 */
	public List<Entidad> listEntidad(TypeIdioma typeIdioma, String filtro);

}
