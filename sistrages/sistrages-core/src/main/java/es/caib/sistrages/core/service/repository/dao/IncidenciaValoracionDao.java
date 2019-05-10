package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.IncidenciaValoracion;

public interface IncidenciaValoracionDao {

	/**
	 * Devuelve la lista de valoraciones que tiene la entidad
	 *
	 * @param codigo
	 * @return
	 */
	List<IncidenciaValoracion> getValoraciones(Long idEntidad);

	/**
	 * Carga una valoracion
	 *
	 * @param idValoracion
	 * @return
	 */
	IncidenciaValoracion loadValoracion(Long idValoracion);

	/**
	 * Añade una valoracion a la entidad.
	 *
	 * @param idEntidad
	 * @param valoracion
	 */
	void addValoracion(Long idEntidad, IncidenciaValoracion valoracion);

	/**
	 * Actualiza una valoracion
	 *
	 * @param valoracion
	 */
	void updateValoracion(IncidenciaValoracion valoracion);

	/**
	 * Borra valoracion
	 *
	 * @param idValoracion
	 */
	void removeValoracion(Long idValoracion);

	/**
	 * Comprueba si ya existe el identificador
	 * 
	 * @param identificador
	 * @param idEntidad
	 * @param idIncidencia
	 * @return
	 */
	boolean existeIdentificador(String identificador, Long idEntidad, Long idIncidencia);

}