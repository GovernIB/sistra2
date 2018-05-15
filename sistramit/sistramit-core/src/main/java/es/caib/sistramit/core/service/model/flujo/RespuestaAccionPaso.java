package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Respuesta del controlador del paso al ejecutar una acción.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RespuestaAccionPaso implements Serializable {

	/**
	 * Parámetros de retorno específicos de la acción.
	 */
	private final Map<String, Object> parametrosRetorno = new HashMap<>();

	/**
	 * Añade un parámetro de retorno a la respuesta.
	 *
	 * @param idParametro
	 *            Id parámetro retorno
	 * @param parametro
	 *            Valor parámetro
	 *
	 */
	public void addParametroRetorno(final String idParametro, final Object parametro) {
		parametrosRetorno.put(idParametro, parametro);
	}

	/**
	 * Obtiene un parámetro de retorno de la respuesta.
	 *
	 * @param idParametro
	 *            Id parámetro retorno
	 * @return Valor parámetro
	 *
	 */
	public Object getParametroRetorno(final String idParametro) {
		return parametrosRetorno.get(idParametro);
	}

	/**
	 * Método de acceso a parametrosRetorno.
	 *
	 * @return parametrosRetorno
	 */
	public Map<String, Object> getParametrosRetorno() {
		return parametrosRetorno;
	}

}
