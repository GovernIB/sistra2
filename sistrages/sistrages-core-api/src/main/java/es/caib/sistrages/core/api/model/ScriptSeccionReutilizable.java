package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeScriptSeccionReutilizable;

/**
 * La clase Script.
 */

public class ScriptSeccionReutilizable extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Script **/
	private Script script;

	/** Seccion reutilizable **/
	private SeccionReutilizable seccionReutilizable;

	/** Id seccion reutilizable **/
	private Long idSeccionReutilizable;

	/** Tipo Script **/
	private TypeScriptSeccionReutilizable tipoScript;

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the script
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(Script script) {
		this.script = script;
	}

	/**
	 * @return the seccionReutilizable
	 */
	public SeccionReutilizable getSeccionReutilizable() {
		return seccionReutilizable;
	}

	/**
	 * @param seccionReutilizable the seccionReutilizable to set
	 */
	public void setSeccionReutilizable(SeccionReutilizable seccionReutilizable) {
		this.seccionReutilizable = seccionReutilizable;
	}

	/**
	 * @return the tipoScript
	 */
	public TypeScriptSeccionReutilizable getTipoScript() {
		return tipoScript;
	}

	/**
	 * @param tipoScript the tipoScript to set
	 */
	public void setTipoScript(TypeScriptSeccionReutilizable tipoScript) {
		this.tipoScript = tipoScript;
	}

	/**
	 * @return the idSeccionReutilizable
	 */
	public Long getIdSeccionReutilizable() {
		return idSeccionReutilizable;
	}

	/**
	 * @param idSeccionReutilizable the idSeccionReutilizable to set
	 */
	public void setIdSeccionReutilizable(Long idSeccionReutilizable) {
		this.idSeccionReutilizable = idSeccionReutilizable;
	}

	public static ScriptSeccionReutilizable createInstance(Long idSeccion,
			TypeScriptSeccionReutilizable tipo) {
		ScriptSeccionReutilizable script = new ScriptSeccionReutilizable();
		script.setIdSeccionReutilizable(idSeccion);
		script.setTipoScript(tipo);
		script.setScript(Script.createInstance());
		return script;
	}


}
