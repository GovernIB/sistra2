package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * ConfiguracionAutenticacion Service.
 */
public interface ConfiguracionAutenticacionService {

	/**
	 * Obtiene el valor de Configuracion Autenticacion.
	 *
	 * @param id el identificador
	 * @return el valor de Configuracion Autenticacion
	 */
	ConfiguracionAutenticacion getConfiguracionAutenticacion(Long id);

	/**
	 * Añade Configuracion Autenticacion.
	 *
	 * @param idArea                id Area
	 * @param configuracionAutenticacion el valor de Configuracion Autenticacion
	 */
	Long addConfiguracionAutenticacion(Long idArea, ConfiguracionAutenticacion configuracionAutenticacion);

	/**
	 * Elimina Configuracion Autenticacion.
	 *
	 * @param id el identificador
	 * @return true, si se realiza correctamente
	 */
	boolean removeConfiguracionAutenticacion(Long id);

	/**
	 * Actualiza Configuracion Autenticacion.
	 *
	 * @param configuracionAutenticacion el valor de Configuracion Autenticacion
	 */
	void updateConfiguracionAutenticacion(ConfiguracionAutenticacion configuracionAutenticacion);

	/**
	 * Lista de Configuracion Autenticacion.
	 *
	 * @param idArea id Area
	 * @param idioma    idioma
	 * @param filtro    filtro busqueda
	 * @return la lista de Configuracion Autenticacion
	 */
	List<ConfiguracionAutenticacion> listConfiguracionAutenticacion(Long idArea, TypeIdioma idioma, String filtro);

	/**
	 * Comprueba si ya existe un identificador.
	 *
	 * @param identificador
	 * @para idCodigo
	 * @return
	 */
	boolean existeConfiguracionAutenticacion(String identificador, Long idCodigo);

	/**
	 * Obtiene la configuracion autenticacion
	 * @param identificador
	 * @param codigo
	 * @return
	 */
	ConfiguracionAutenticacion getConfiguracionAutenticacion(String identificador, Long codigo);

}
