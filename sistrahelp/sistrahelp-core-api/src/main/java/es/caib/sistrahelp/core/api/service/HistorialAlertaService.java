package es.caib.sistrahelp.core.api.service;

import java.util.Date;
import java.util.List;

import es.caib.sistrahelp.core.api.model.HistorialAlerta;

/**
 * Dominio service.
 *
 * @author Indra.
 *
 */
public interface HistorialAlertaService {

	/**
	 * Obtener Alerta.
	 *
	 * @param codVa codigo del Alerta
	 * @return Alerta
	 */
	public HistorialAlerta loadHistorialAlerta(Long codAl);

	/**
	 * Obtener Alerta by nombre.
	 *
	 * @param identificador identificador del Alerta
	 * @return Alerta
	 */
	public HistorialAlerta loadHistorialAlertaByAlerta(Long al);

	/**
	 * Añade Alerta.
	 *
	 * @param va Alerta a crear.
	 *
	 */
	public Long addHistorialAlerta(HistorialAlerta al);

	/**
	 * Actualiza Alerta.
	 *
	 * @param va Alerta con los datos requeridos.
	 */
	public void updateHistorialAlerta(HistorialAlerta al);

	/**
	 * Elimina dominio.
	 *
	 * @param idAlerta the id Alerta
	 * @return true, si se realiza correctamente
	 */
	public boolean removeHistorialAlerta(Long idal);

	/**
	 * Listar Alerta.
	 *
	 * @param idVa   Id de la area
	 * @param filtro Filro aplicado al código o descripcion.
	 * @return lista de Alerta
	 */
	public List<HistorialAlerta> listHistorialAlerta(Date desde, Date hasta);

}