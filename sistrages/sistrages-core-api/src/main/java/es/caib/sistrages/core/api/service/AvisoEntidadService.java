package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * AvisoEntidad Service.
 */
public interface AvisoEntidadService {

	/**
	 * Obtiene el valor de Aviso Entidad.
	 *
	 * @param id el identificador
	 * @return el valor de Aviso Entidad
	 */
	AvisoEntidad getAvisoEntidad(Long id);

	/**
	 * Añade Aviso Entidad.
	 *
	 * @param idEntidad    id Entidad
	 * @param avisoEntidad el valor de Aviso Entidad
	 */
	void addAvisoEntidad(Long idEntidad, AvisoEntidad avisoEntidad);

	/**
	 * Elimina Aviso Entidad.
	 *
	 * @param id el identificador
	 * @return true, si se realiza correctamente
	 */
	boolean removeAvisoEntidad(Long id);

	/**
	 * Actualiza Aviso Entidad.
	 *
	 * @param avisoEntidad el valor de Aviso Entidad
	 */
	void updateAvisoEntidad(AvisoEntidad avisoEntidad);

	/**
	 * Lista de Aviso Entidad.
	 *
	 * @param idEntidad id Entidad
	 * @param idioma    idioma
	 * @param filtro    filtro busqueda
	 * @return la lista de Aviso Entidad
	 */
	List<AvisoEntidad> listAvisoEntidad(Long idEntidad, TypeIdioma idioma, String filtro);

	/**
	 * Obtiene el aviso entidad según el identificador del tramite
	 *
	 * @param identificadorTramite
	 * @return
	 */
	List<AvisoEntidad> getAvisoEntidadByTramite(String identificadorTramite);

}
