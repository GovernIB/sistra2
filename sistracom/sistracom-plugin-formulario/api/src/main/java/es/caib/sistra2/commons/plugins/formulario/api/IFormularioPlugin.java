package es.caib.sistra2.commons.plugins.formulario.api;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface plugin formulario externo.
 *
 * @author Indra
 *
 */
public interface IFormularioPlugin extends IPlugin {

	/** Prefix. */
	public static final String FORMULARIO_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "formulario.";

	/**
	 * Invoca a gestor formulario para abrir formulario.
	 *
	 * @param idGestorFormulario
	 *                                id gestor (para obtener securización según
	 *                                props plugin)
	 * @param urlGestorFormulario
	 *                                url gestor formulario
	 * @param user
	 *                                usuario
	 * @param pwd
	 *                                password
	 * @param datosInicio
	 *                                datos para iniciar formulario
	 * @return url redirección a formulario (debe ser url basada en ticket de un
	 *         solo uso y con timeout)
	 * @throws FormularioPluginException
	 */
	public String invocarFormulario(String idGestorFormulario, String urlGestorFormulario, String user, String pwd,
			DatosInicioFormulario datosInicio) throws FormularioPluginException;

	/**
	 * Obtiene datos retorno formulario.
	 *
	 * @param idGestorFormulario
	 *                                id gestor (para obtener securización según
	 *                                props plugin)
	 * @param urlGestorFormulario
	 *                                url gestor formulario
	 * @param ticket
	 *                                Ticket
	 * @param user
	 *                                usuario
	 * @param pwd
	 *                                password
	 * @return datos retorno formulario
	 */
	public DatosRetornoFormulario obtenerResultadoFormulario(String idGestorFormulario, String urlGestorFormulario,
			String user, String pwd, String ticket) throws FormularioPluginException;

}
