package es.caib.sistra2.commons.plugins.funcionariohabilitado.api;

import org.fundaciobit.pluginsib.core.IPlugin;

import java.util.Date;

/**
 * Interface digitalización plugin.
 *
 * @author Indra
 *
 */
public interface IFuncionarioHabilitadoPlugin extends IPlugin {

	/** Prefix. */
	public static final String FUNCIONARIOHABILITADO_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "funcionariohabilitado.";

	/**
	 * Avisar a Funcionario Habilitado de la finalización del trámite.
	 * @param idioma Idioma tramitación
	 * @param nifFH NIF del funcionario habilitado
	 * @param debug Modo debug
	 * @param fechaRegistro Fecha de registro de la solicitud
	 * @param idActuacionFH Id de la actuación en Funcionario Habilitado (pasado al iniciar tramitación)
	 * @param numeroRegistro Número de registro de la solicitud
	 * @throws FuncionarioHabilitadoPluginException Genera excepción en caso de no poder avisar
	 */
	void avisarFinalizacionTramite(String nifFH, String idActuacionFH, String numeroRegistro, Date fechaRegistro, String idioma, boolean debug) throws FuncionarioHabilitadoPluginException;

}
