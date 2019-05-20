package es.caib.sistramit.core.service.model.script.formulario;

import es.caib.sistramit.core.service.model.script.ClzDatosUsuarioInt;
import es.caib.sistramit.core.service.model.script.PluginScriptPlg;

/**
 * Plugin de acceso a los datos de la sesión de tramitación.
 *
 * @author Indra
 *
 */
public interface PlgSesionFormularioInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_SESIONFORMULARIO";

	// TODO Pendiente ver si ofrecemos datos del catalogo

	/**
	 * Devuelve id sesion tramitación.
	 *
	 * @return Id sesión
	 */
	String getIdSesionTramitacion();

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

	/**
	 * Devuelve representante usuario autenticado. Sólo para trámites que se han
	 * iniciado de forma autenticada.
	 *
	 * @return Devuelve usuario (null si no hay representante).
	 */
	ClzDatosUsuarioInt getRepresentante();

	/**
	 * Obtiene datos apertura formulario.
	 *
	 * @param idParametro
	 *            id parámetro
	 * @return parámetro
	 */
	String getParametro(final String idParametro);
}
