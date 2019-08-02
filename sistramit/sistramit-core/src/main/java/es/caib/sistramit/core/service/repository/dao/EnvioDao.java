package es.caib.sistramit.core.service.repository.dao;

import java.util.List;

import es.caib.sistramit.core.service.model.system.Envio;

/**
 * DAO para envios
 */
public interface EnvioDao {

	/**
	 * Guarda envio.
	 *
	 * @param envio envio
	 */
	void addEnvio(Envio envio);

	/**
	 * Guarda un envio como correcto.
	 *
	 * @param idEnvio ID de envio
	 */
	void guardarCorrecto(Long idEnvio);

	/**
	 * Guarda un envio como intento erróneo.
	 *
	 * @param idEnvio ID de envio
	 * @param mensaje Mensaje de error
	 */
	void errorEnvio(Long idEnvio, String mensaje);

	/**
	 * Obtiene lista envios a enviar inmediatos.
	 *
	 * @return Lista de envios
	 */
	List<Envio> listaInmediatos();

	/**
	 * Obtiene lista envios a enviar de reintentos.
	 *
	 * @return Lista de envios
	 */
	List<Envio> listaReintentos();

}
