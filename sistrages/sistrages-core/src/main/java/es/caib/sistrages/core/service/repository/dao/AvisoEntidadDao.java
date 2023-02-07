package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * La interface AvisoEntidadDao.
 */
public interface AvisoEntidadDao {

	/**
	 * Obtiene el aviso de entidad.
	 *
	 * @param pId identificador de aviso de entidad
	 * @return el aviso de entidad
	 */
	AvisoEntidad getById(Long pId);

	/**
	 * Añade el aviso de entidad.
	 *
	 * @param pIdEntidad    identificador de la entidad
	 * @param pAvisoEntidad aviso de entidad
	 */
	void add(final Long pIdEntidad, AvisoEntidad pAvisoEntidad);

	/**
	 * Elimina el aviso de entidad.
	 *
	 * @param pId el identificador de aviso de entidad
	 */
	void remove(Long pId);

	/**
	 * Elimina el aviso de entidad.
	 *
	 * @param pIdEntidad identificador de la entidad
	 */
	void removeByEntidad(Long pIdEntidad);

	/**
	 * Actualiza el aviso de entidad.
	 *
	 * @param pAvisoEntidad el aviso de entidad
	 */
	void update(AvisoEntidad pAvisoEntidad);

	/**
	 * Lista de avisos de entidad
	 *
	 * @param pIdEntidad identificador de la entidad
	 * @return la lista de avisos de entidad
	 */
	List<AvisoEntidad> getAll(Long pIdEntidad);

	/**
	 * Lista de avisos de entidad
	 *
	 * @param pIdEntidad identificador de la entidad
	 * @param pFiltro    filtro
	 * @param pIdioma    idioma
	 * @return la lista de Aviso Entidad
	 */
	List<AvisoEntidad> getAllByFiltro(Long pIdEntidad, TypeIdioma pIdioma, String pFiltro);

	/**
	 * Obtiene el aviso entidad asociado a un tramite según su identificador.
	 *
	 * @param identificadorTramite Identificador del tramite.
	 * @return El aviso de entidad
	 */
	List<AvisoEntidad> getAvisoEntidadByTramite(String identificadorTramite);

	/**
	 * Lista de avisos de entidad
	 *
	 * @param codDir3 codigo Dir3
	 * @return la lista de avisos de entidad
	 */
	List<AvisoEntidad> getAll(String codDir3);

	/**
	 * Borra referencias de mensajes de activación hacia versiones de trámite.
	 *
	 * @param idTramiteVersion
	 * @param numVersion
	 */
	void removeMensajes(Long idTramiteVersion, int numVersion);

}
