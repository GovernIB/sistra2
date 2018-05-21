package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;

/**
 * Excepción que indica que el usuario esta intentando acceder a un trámite al
 * que no puede acceder.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class UsuarioNoPermitidoException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// UsuarioNoPermitidoException FATAL.
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor UsuarioNoPermitidoException.
	 *
	 * @param pMessage
	 *            Mensaje de error.
	 * @param pNivelAut
	 *            Nivel autenticacion
	 * @param pnif
	 *            nif
	 */
	public UsuarioNoPermitidoException(final String pMessage, final TypeAutenticacion pNivelAut, final String pnif) {
		super(pMessage);
		final ListaPropiedades props = new ListaPropiedades();
		props.addPropiedad("nivelautenticacion", pNivelAut.toString());
		if (pnif != null) {
			props.addPropiedad("nif", pnif);
		}
		this.setDetallesExcepcion(props);
	}

}
