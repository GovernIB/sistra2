package es.caib.sistramit.core.service.model.script;

import javax.script.ScriptException;

/**
 * Datos que se pueden personalizar al cargar un trámite.
 *
 * @author Indra
 *
 */
public interface ResPersonalizacionTramiteInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_PERSONALIZACION";

	/**
	 * Método para establecer tituloTramite.
	 *
	 * @param pTituloTramite
	 *            tituloTramite a establecer
	 * @throws ScriptException
	 *             Exception
	 */
	void setTituloTramite(final String pTituloTramite) throws ScriptException;

	/**
	 * Método para establecer plazoInicio.
	 *
	 * @param pPlazoInicio
	 *            plazoInicio a establecer
	 * @throws ScriptException
	 *             Exception
	 */
	void setPlazoInicio(final String pPlazoInicio) throws ScriptException;

	/**
	 * Método para establecer plazoFin.
	 *
	 * @param pPlazoFin
	 *            plazoFin a establecer
	 * @throws ScriptException
	 *             Exception
	 */
	void setPlazoFin(final String pPlazoFin) throws ScriptException;

}
