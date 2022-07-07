package es.caib.sistrages.core.service.repository.dao;

import java.util.Collection;
import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeClonarAccion;
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
	 * @param pIdArea         identificador de la area
	 * @param idEntidad         identificador de la entidad
	 * @param pConfAut configuracion autenticacion
	 */
	Long add(final Long pIdArea, final Long idEntidad, ConfiguracionAutenticacion pConfAut);

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
	 * Lista de avisos del area
	 *
	 * @param ambito Ambito (Area/Global/Entidad)
	 * @param idArea identificador de la area
	 * @param idEntidad Identificador de la entidad
	 * @param filtro    filtro
	 * @param idioma    idioma
	 * @return la lista de configuracion autenticacion
	 */
	List<ConfiguracionAutenticacion> getAllByFiltro(final TypeAmbito ambito, Long idArea, final Long idEntidad, TypeIdioma idioma, String filtro);

	/**
	 * Existe configuracion autenticacion con esse identificador.
	 * @param ambito
	 * @param identificador
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param codigoConfAut
	 * @return
	 */

	boolean existeConfiguracionAutenticacion(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoConfAut);

	/**
	 * Obtiene la configuracion autenticacion por identificador y codigo.
	 * @param ambito
	 * @param identificador
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param codigoConfAut
	 * @return
	 */

	ConfiguracionAutenticacion getConfiguracionAutenticacion(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoConfAut);

	/**
	 * Devuelve una lista de tipo consulta general
	 * @param filtro
	 * @param idioma
	 * @param idEntidad
	 * @param idArea
	 * @param checkAmbitoGlobal
	 * @param checkAmbitoEntidad
	 * @param checkAmbitoArea
	 * @return
	 */
	List<ConsultaGeneral> listar(String filtro, TypeIdioma idioma, Long idEntidad, Long idArea,
			boolean checkAmbitoGlobal, boolean checkAmbitoEntidad, boolean checkAmbitoArea);

	/**
	 * Devuelve una lista de configuracion autenticacion
	 * @param ambito
	 * @param codigoEntidad
	 * @return
	 */
	List<ConfiguracionAutenticacion> listConfiguracionAutenticacionRest(TypeAmbito ambito, Long codigoEntidad);

	/**
	 * Método que clona la CA de un dominio en otro area/entidad
	 * @param dominioID
	 * @param caReemplazar
	 * @param confAut
	 * @param idEntidad
	 * @param areaID
	 * @return
	 */
	ConfiguracionAutenticacion clonar(String dominioID, TypeClonarAccion accionCA, ConfiguracionAutenticacion confAut,
			Long idEntidad, Long areaID);

}
