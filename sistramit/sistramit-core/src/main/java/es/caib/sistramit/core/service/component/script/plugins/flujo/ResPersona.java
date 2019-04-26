package es.caib.sistramit.core.service.component.script.plugins.flujo;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistramit.core.service.component.script.ScriptUtils;
import es.caib.sistramit.core.service.model.script.flujo.ResPersonaInt;

/**
 * Establece datos persona.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResPersona implements ResPersonaInt {

	/**
	 * Indica si es nulo.
	 */
	private boolean nulo;

	/**
	 * Nif persona.
	 */
	private String nif;

	/**
	 * Nombre persona.
	 */
	private String nombre;

	/**
	 * Apellido 1 persona.
	 */
	private String apellido1;

	/**
	 * Apellido 2 persona.
	 */
	private String apellido2;

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void setDatosPersona(final String pNif, final String pNombre, final String pApellido1,
			final String pApellido2) throws ScriptException {

		nif = NifUtils.normalizarNif(pNif);

		ScriptUtils.validarDatosPersona(nif, pNombre, pApellido1, pApellido2);

		nombre = pNombre;
		if (!StringUtils.isBlank(pApellido1)) {
			apellido1 = pApellido1;
		}
		if (!StringUtils.isBlank(pApellido2)) {
			apellido2 = pApellido2;
		}
	}

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif persona
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre persona
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método de acceso a apellido1.
	 *
	 * @return apellido1 persona
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Método de acceso a apellido2.
	 *
	 * @return apellido2 persona
	 */
	public String getApellido2() {
		return apellido2;
	}

	@Override
	public void setNulo(final boolean pNulo) {
		this.nulo = pNulo;
	}

	/**
	 * Método de acceso a nulo.
	 * 
	 * @return nulo
	 */
	public boolean isNulo() {
		return nulo;
	}

}
