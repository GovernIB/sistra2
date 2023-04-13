package es.caib.sistrahelp.core.service.repository.dao;

import java.util.List;

import es.caib.sistrahelp.core.api.model.Alerta;

/**
 * La interface DominioDao.
 */
public interface AlertaDao {

	/**
	 * Obtiene alerta byId.
	 *
	 * @param Long id alerta
	 * @return el valor de byId
	 */
	Alerta getByCodigo(final Long idAlerta);

	/**
	 * Añade alerta.
	 *
	 * @param Alerta the alerta
	 */
	Long add(final Alerta alerta);

	/**
	 * Actualiza alerta.
	 *
	 * @param Alerta the alerta
	 */
	void updateAlerta(final Alerta alerta);

	/**
	 * Obtiene lista de alertas.
	 *
	 * @param String filtro
	 * @return lista de Alerta
	 */
	List<Alerta> getAllByFiltro(String filtro);

	/**
	 * Devuelve las alertas que tienen ese nombre.
	 *
	 * @param String nombre alertas
	 * @return
	 */
	Alerta getAlertaByNombre(String nombreAlerta);

	/**
	 * Elimina.
	 *
	 * @param idVariableArea the id VariableArea
	 */
	boolean remove(final Long idAlerta);

	List<Alerta> listarAlertaActivo(final String filtro, final boolean activo);

}