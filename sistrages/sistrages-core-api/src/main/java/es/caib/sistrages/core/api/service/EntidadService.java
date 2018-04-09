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

	/**
	 * Obtener entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @return entidad
	 */
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
	 * Actualización entidad por parte del administrador de entidad.
	 *
	 * @param entidad
	 *            Entidad con los datos requeridos por el administrador de entidad.
	 */
	public void updateEntidadAdministradorEntidad(final Entidad entidad);

	/**
	 * Elimina entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @return true, si se ha realizado con &eacute;xito
	 */
	public boolean removeEntidad(Long idEntidad);

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
