package es.caib.sistramit.core.service.model.script.flujo;

import es.caib.sistramit.core.service.model.script.ClzDatosUsuarioInt;
import es.caib.sistramit.core.service.model.script.PluginScriptPlg;

/**
 * Plugin de acceso a los datos de la sesión de tramitación.
 *
 * @author Indra
 *
 */
public interface PlgSesionTramitacionInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_SESIONTRAMITACION";

	// TODO Pendiente ver si ofrecemos datos del catalogo

	/**
	 * Devuelve id sesion tramitación.
	 *
	 * @return Id sesión
	 */
	String getIdSesionTramitacion();

	/**
	 * Devuelve url inicio del trámite.
	 *
	 * @return url inicio del trámite
	 */
	String getUrlInicioTramite();

	/**
	 * Devuelve parámetro inicio.
	 *
	 * @param parametro
	 *            Nombre parámetro.
	 * @return Devuelve valor parámetro. En caso de no encontrar el parámetro
	 *         devuelve cadena vacía.
	 */
	String getParametroInicio(final String parametro);

	/**
	 * Devuelve idioma de tramitación.
	 *
	 * @return idioma de tramitación: es, ca, ...
	 */
	String getIdioma();

	/**
	 * Indica si el trámite es autenticado.
	 *
	 * @return true si autenticado
	 */
	boolean isAutenticado();

	/**
	 * Devuelve método de autenticación.
	 *
	 * @return método de autenticación
	 */
	String getMetodoAutenticacion();

	/**
	 * Devuelve usuario autenticado. Sólo para trámites que se han iniciado de forma
	 * autenticada.
	 *
	 * @return Devuelve usuario (null si el trámite no se ha iniciado de forma
	 *         autenticada).
	 */
	ClzDatosUsuarioInt getUsuario();

}
