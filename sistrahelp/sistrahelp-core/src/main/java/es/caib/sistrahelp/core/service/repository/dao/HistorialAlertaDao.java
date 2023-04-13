package es.caib.sistrahelp.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;

/**
 * La interface DominioDao.
 */
public interface HistorialAlertaDao {

	/**
	 * Obtiene alerta byId.
	 *
	 * @param Long id alerta
	 * @return el valor de byId
	 */
	HistorialAlerta getByCodigo(final Long idHistorialAlerta);

	/**
	 * Añade alerta.
	 *
	 * @param Alerta the alerta
	 */
	Long add(final HistorialAlerta ha);

	/**
	 * Actualiza alerta.
	 *
	 * @param Alerta the alerta
	 */
	void updateHistorialAlerta(final HistorialAlerta alerta);

	/**
	 * Obtiene lista de alertas.
	 *
	 * @param String filtro
	 * @return lista de Alerta
	 */
	List<HistorialAlerta> getAllByFiltro(Date desde, Date hasta);

	/**
	 * Devuelve las alertas que tienen ese nombre.
	 *
	 * @param String nombre alertas
	 * @return
	 */
	HistorialAlerta getHistorialAlertaByAlerta(Long codigoAviso);

	/**
	 * Elimina.
	 *
	 * @param idVariableArea the id VariableArea
	 */
	boolean remove(final Long idAlerta);
}