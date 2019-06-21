package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.service.model.script.flujo.ResAvisoInt;

/**
 * Datos que se pueden establecer en un aviso.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResAviso implements ResAvisoInt {

	/** Email. */
	private String email;

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void setEmail(final String pEmail) {
		this.email = pEmail;
	}

	/**
	 * Método de acceso a email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

}
