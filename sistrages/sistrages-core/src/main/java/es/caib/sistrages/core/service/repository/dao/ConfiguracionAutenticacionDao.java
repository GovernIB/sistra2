package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * La interface ConfiguracionAutenticacionDao.
 */
public interface ConfiguracionAutenticacionDao {

	/**
	 * Obtiene el configuracion autenticacion.
	 *
	 * @param pId identificador de configuracion autenticacion
	 * @return el configuracion autenticacion
	 */
	ConfiguracionAutenticacion getById(Long pId);

	/**
	 * Añade el configuracion autenticacion.
	 *
	 * @param removeByArea         identificador de la area
	 * @param pConfAut configuracion autenticacion
	 */
	Long add(final Long pIdArea, ConfiguracionAutenticacion pConfAut);

	/**
	 * Elimina el configuracion autenticacion.
	 *
	 * @param pId el identificador de configuracion autenticacion
	 */
	void remove(Long pId);

	/**
	 * Elimina el configuracion autenticacion.
	 *
	 * @param pIdArea identificador del area
	 */
	void removeByArea(Long pIdArea);

	/**
	 * Actualiza el configuracion autenticacion.
	 *
	 * @param pConfiguracionAutenticacion el configuracion autenticacion
	 */
	void update(ConfiguracionAutenticacion pConfiguracionAutenticacion);

	/**
	 * Lista de avisos de area
	 *
	 * @param pIdArea identificador de la area
	 * @return la lista configuracion autenticacion del area
	 */
	List<ConfiguracionAutenticacion> getAll(Long pIdArea);

	/**
	 * Lista de avisos del area
	 *
	 * @param pIdArea identificador de la area
	 * @param pFiltro    filtro
	 * @param pIdioma    idioma
	 * @return la lista de configuracion autenticacion
	 */
	List<ConfiguracionAutenticacion> getAllByFiltro(Long pIdArea, TypeIdioma pIdioma, String pFiltro);

	/**
	 * Existe configuracion autenticacion con esse identificador.
	 *
	 * @param identificador
	 * @return
	 */
	boolean existeConfiguracionAutenticacion(String identificador, final Long idCodigo);

	/**
	 * Obtiene la configuracion autenticacion por identificador y codigo.
	 * @param identificador
	 * @param codigo
	 * @return
	 */
	ConfiguracionAutenticacion getConfiguracionAutenticacion(String identificador, Long codigo);

}
