package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeAccionFormularioNormalizado;

/**
 * Acción formulario normalizada.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AccionFormularioNormalizada implements AccionFormulario {

	/** Accion formulario. */
	private final TypeAccionFormularioNormalizado tipo;

	/**
	 * Constructor.
	 * 
	 * @param tipo
	 *            tipo
	 */
	public AccionFormularioNormalizada(TypeAccionFormularioNormalizado tipo) {
		super();
		this.tipo = tipo;
	}

	/**
	 * Método de acceso a tipo.
	 * 
	 * @return tipo
	 */
	public TypeAccionFormularioNormalizado getTipo() {
		return tipo;
	}

}
