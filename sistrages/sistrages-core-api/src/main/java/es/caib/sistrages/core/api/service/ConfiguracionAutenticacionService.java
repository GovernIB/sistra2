package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
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
	Long addConfiguracionAutenticacion(Long idArea, Long idEntidad, ConfiguracionAutenticacion configuracionAutenticacion);

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
	 * @param ambito Ambito
	 * @param idEntidad id Entidad
	 * @param idArea id Area
	 * @param idioma    idioma
	 * @param filtro    filtro busqueda
	 * @return la lista de Configuracion Autenticacion
	 */
	List<ConfiguracionAutenticacion> listConfiguracionAutenticacion(final TypeAmbito ambito, Long idArea, Long idEntidad, TypeIdioma idioma, String filtro);

	/**
	 * Comprueba si ya existe un identificador.
	 * @param ambito
	 * @param identificador
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param codigoConfAut
	 * @return
	 */

	boolean existeConfiguracionAutenticacion(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoConfAut);

	/**
	 * Obtiene la configuracion autenticacion
	 * @param ambito
	 * @param identificador
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param codigoConfAut
	 * @return
	 */

	ConfiguracionAutenticacion getConfiguracionAutenticacion(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoConfAut);

}
