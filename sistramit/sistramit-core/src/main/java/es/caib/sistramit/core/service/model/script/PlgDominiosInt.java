package es.caib.sistramit.core.service.model.script;

/**
 * Plugin de acceso a dominios.
 *
 * @author Indra
 *
 */
public interface PlgDominiosInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_DOMINIOS";

	/**
	 * Crea parámetros para alimentarlo y pasarlos después al dominio.
	 *
	 * @return Parámetros
	 */
	ClzParametrosDominioInt crearParametros();

	/**
	 * Realiza invocación al dominio.
	 *
	 * @param idDominio
	 *            Id dominio
	 * @return Valores dominio
	 */
	ClzValoresDominioInt invocarDominio(final String idDominio);

	/**
	 * Realiza invocación al dominio pasándole parámetros.
	 *
	 * @param idDominio
	 *            Id dominio
	 * @param parametros
	 *            Parámetros
	 * @return Valores dominio
	 */
	ClzValoresDominioInt invocarDominio(final String idDominio, final ClzParametrosDominioInt parametros);

}
