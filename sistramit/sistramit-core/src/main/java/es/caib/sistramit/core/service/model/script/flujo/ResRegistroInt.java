package es.caib.sistramit.core.service.model.script.flujo;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 *
 * Datos que se pueden establecer dinámicamente al registrar.
 *
 * @author Indra
 *
 */
public interface ResRegistroInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_REGISTRO";

	/**
	 * Establece código entidad.
	 *
	 * @param codigoEntidad
	 *                          código entidad
	 */
	void setCodigoEntidad(String codigoEntidad);

	/**
	 * Método para establecer codigoOrganoDestino.
	 *
	 * @param codigoOrganoDestino
	 *                                codigoOrganoDestino a establecer
	 */
	void setCodigoOrganoDestino(String codigoOrganoDestino);

	/**
	 * Método para establecer oficina.
	 *
	 * @param oficina
	 *                    oficina a establecer
	 */
	void setOficina(String oficina);

	/**
	 * Método para establecer libro.
	 *
	 * @param libro
	 *                  libro a establecer
	 */
	void setLibro(String libro);

	/**
	 * Método para establecer numeroExpediente.
	 *
	 * @param numeroExpediente
	 *                             numeroExpediente a establecer
	 */
	void setNumeroExpediente(String numeroExpediente);

	/**
	 * Método para establecer textoExpone.
	 *
	 * @param textoExpone
	 *                        textoExpone a establecer
	 */
	void setExpone(String textoExpone);

	/**
	 * Método para establecer textoSolicita.
	 *
	 * @param textoSolicita
	 *                          textoSolicita a establecer
	 */
	void setSolicita(String textoSolicita);

	/**
	 * Método para establecer extracto.
	 *
	 * @param extracto
	 *                     extracto a establecer
	 */
	void setExtracto(String extracto);

}
