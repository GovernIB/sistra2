package es.caib.sistramit.core.service.model.script;

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
	 * Método para establecer oficina.
	 *
	 * @param oficina
	 *            oficina a establecer
	 */
	void setOficina(String oficina);

	/**
	 * Método para establecer libro.
	 *
	 * @param libro
	 *            libro a establecer
	 */
	void setLibro(String libro);

	/**
	 * Método para establecer tipoAsunto.
	 *
	 * @param tipoAsunto
	 *            tipoAsunto a establecer
	 */
	void setTipoAsunto(String tipoAsunto);

	/**
	 * Método para establecer numeroExpediente.
	 *
	 * @param numeroExpediente
	 *            numeroExpediente a establecer
	 */
	void setNumeroExpediente(String numeroExpediente);

}
