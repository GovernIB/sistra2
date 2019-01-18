package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.types.TypeAccionFormulario;
import es.caib.sistramit.core.api.model.formulario.types.TypeAccionFormularioNormalizado;
import es.caib.sistramit.core.api.model.formulario.types.TypeAccionFormularioPersonalizado;

/**
 * Acción formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AccionFormulario implements Serializable {

	/** Accion formulario. */
	private TypeAccionFormulario tipo;

	/** Valor. */
	private String valor;

	/**
	 * Validar formulario. Indica si debe validar los datos del formulario al pulsar
	 * el boton de acción, ya que existen acciones que no tendrán en cuenta los
	 * datos del formulario.
	 */
	private TypeSiNo validar = TypeSiNo.SI;

	/** Constructor. */
	public AccionFormulario() {
		super();
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param pTipo
	 *            accion
	 */
	public AccionFormulario(TypeAccionFormularioNormalizado pTipo) {
		super();
		this.tipo = pTipo;
		this.valor = pTipo.toString();
		if (this.tipo.toString().equals(TypeAccionFormularioNormalizado.SIGUIENTE.toString())
				|| this.tipo.toString().equals(TypeAccionFormularioNormalizado.FINALIZAR.toString())) {
			this.validar = TypeSiNo.SI;
		} else {
			this.validar = TypeSiNo.NO;
		}
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param pTipo
	 *            accion
	 * @param valorAccionPersonalizada
	 *            En caso de accion personalizada indica valor
	 */
	public AccionFormulario(TypeAccionFormularioPersonalizado pTipo, String valorAccionPersonalizada,
			TypeSiNo validar) {
		super();
		this.tipo = pTipo;
		this.valor = valorAccionPersonalizada;
		this.validar = validar;
	}

	/**
	 * Método de acceso a validar.
	 *
	 * @return validar
	 */
	public TypeSiNo getValidar() {
		return validar;
	}

	/**
	 * Método de acceso a tipo.
	 * 
	 * @return tipo
	 */
	public TypeAccionFormulario getTipo() {
		return tipo;
	}

	/**
	 * Método de acceso a valor.
	 * 
	 * @return valor
	 */
	public String getValor() {
		return valor;
	}

}
