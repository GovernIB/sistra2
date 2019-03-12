package es.caib.sistramit.core.service.model.script;

import java.util.Date;

/**
 * Tramite CP.
 *
 *
 * @author Indra
 *
 */
public interface ClzTramiteCPInt extends PluginClass {

	/**
	 * Método de acceso a la descripción.
	 *
	 * @return Descripción
	 */
	public String getDescripcion();

	/**
	 * Método de acceso al email de soporte.
	 *
	 * @return Email
	 */
	public String getEmailSoporte();

	/**
	 * Método de acceso al identificador.
	 *
	 * @return Identificador.
	 */
	public String getIdentificador();

	/**
	 * Método de acceso al organo destino.
	 *
	 * @return DIR3 órgano
	 */
	public String getOrganoDestinoDir3();

	/**
	 * Método de acceso a la fecha de plazo fin.
	 *
	 * @return Fecha fin
	 */
	public Date getPlazoFin();

	/**
	 * Método de acceso a la fecha de plazo inicio
	 *
	 * @return Fecha inicio
	 */
	public Date getPlazoInicio();

	/**
	 * Método de acceso al procedimiento.
	 *
	 * @return Procedimiento
	 */
	public ClzProcedimientoCPInt getProcedimiento();

	/**
	 * Método de acceso a la url de información
	 *
	 * @return
	 */
	public String getUrlInformacion();

	/**
	 * Método de acceso que comprueba si el trámite es vigente.
	 *
	 * @return Es vigente
	 */
	public boolean isVigente();

}
