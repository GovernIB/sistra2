package es.caib.sistrahelp.core.api.service;

import java.util.List;

import es.caib.sistrahelp.core.api.model.Alerta;

/**
 * Dominio service.
 *
 * @author Indra.
 *
 */
public interface AlertaService {

	/**
	 * Obtener Alerta.
	 *
	 * @param codVa codigo del Alerta
	 * @return Alerta
	 */
	public Alerta loadAlerta(Long codAl);

	/**
	 * Obtener Alerta by nombre.
	 *
	 * @param identificador identificador del Alerta
	 * @return Alerta
	 */
	public Alerta loadAlertaByNombre(String identificador);

	/**
	 * Añade Alerta.
	 *
	 * @param va Alerta a crear.
	 *
	 */
	public Long addAlerta(Alerta al);

	/**
	 * Actualiza Alerta.
	 *
	 * @param va Alerta con los datos requeridos.
	 */
	public void updateAlerta(Alerta al);

	/**
	 * Elimina dominio.
	 *
	 * @param idAlerta the id Alerta
	 * @return true, si se realiza correctamente
	 */
	public boolean removeAlerta(Long idal);

	/**
	 * Listar Alerta.
	 *
	 * @param idVa   Id de la area
	 * @param filtro Filro aplicado al código o descripcion.
	 * @return lista de Alerta
	 */
	public List<Alerta> listAlerta(String filtro);

	/**
	 * Listar Alerta Activo
	 *
	 * @param idVa   Id de la area
	 * @param filtro Filro aplicado al código o descripcion.
	 * @return lista de Alerta
	 */
	public List<Alerta> listAlertaActivo(String filtro, boolean activo);

}