package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Aviso usuario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AvisoUsuario implements ModelApi {

	/** Avisar. */
	private TypeSiNo avisar = TypeSiNo.NO;

	/** Email. */
	private String email;

	/**
	 * Método de acceso a avisar.
	 * 
	 * @return avisar
	 */
	public TypeSiNo getAvisar() {
		return avisar;
	}

	/**
	 * Método para establecer avisar.
	 * 
	 * @param avisar
	 *                   avisar a establecer
	 */
	public void setAvisar(final TypeSiNo avisar) {
		this.avisar = avisar;
	}

	/**
	 * Método de acceso a email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método para establecer email.
	 * 
	 * @param email
	 *                  email a establecer
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

}
