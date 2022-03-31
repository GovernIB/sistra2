package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;

/**
 * Valor de un campo normal.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValorCampoSimple extends ValorCampo {

	/**
	 * Valor del campo.
	 */
	private String valor;

	/**
	 * Constructor.
	 *
	 * @param pId
	 *            Id campo
	 * @param pValor
	 *            Valor campo
	 */
	public ValorCampoSimple(final String pId, final String pValor) {
		super();
		this.setTipo(TypeValor.SIMPLE);
		this.setId(pId);
		valor = pValor;
	}

	/**
	 * Constructor.
	 */
	public ValorCampoSimple() {
		super();
		this.setTipo(TypeValor.SIMPLE);
	}

	/**
	 * Método de acceso a valor.
	 *
	 * @return valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Método para establecer valor.
	 *
	 * @param pValor
	 *            valor a establecer
	 */
	public void setValor(final String pValor) {
		valor = pValor;
	}

	/**
	 * Crea instancia.
	 *
	 * @return ValorCampoSimple
	 */
	public static ValorCampoSimple createNewValorCampoSimple() {
		return new ValorCampoSimple();
	}

	/**
	 * Crea instancia.
	 *
	 * @param pId
	 *            Id campo
	 * @param pValor
	 *            Valor campo
	 * @return ValorCampoSimple
	 */
	public static ValorCampoSimple createNewValorCampoSimple(final String pId, final String pValor) {
		return new ValorCampoSimple(pId, pValor);
	}

	/**
	 * Crea valor vacio.
	 *
	 * @param idCampo
	 *            id campo
	 * @return ValorCampoIndexado vacio
	 */
	public static ValorCampoSimple createValorVacio(final String idCampo) {
		final ValorCampoSimple vcs = new ValorCampoSimple();
		vcs.setId(idCampo);
		vcs.setValor(null);
		return vcs;
	}

	@Override
	public boolean esVacio() {
		return (getValor() == null || getValor().length() == 0);
	}

	@Override
	public String print() {
		String res = null;
		if (!this.esVacio()) {
			res = this.getValor();
		} else {
			res = "";
		}
		return res;
	}

	@Override
	public boolean esValorIgual(final ValorCampo pValorCampo) {
		boolean res = false;
		if (pValorCampo.esVacio() == this.esVacio() && pValorCampo.getTipo() == this.getTipo()) {
			if (!pValorCampo.esVacio()) {
				final ValorCampoSimple vco = (ValorCampoSimple) pValorCampo;
				res = vco.getValor().equals(this.getValor());
			} else {
				res = true;
			}
		}
		return res;
	}

	@Override
	public ValorCampo duplicar() {
		ValorCampo res;
		if (this.esVacio()) {
			res = createValorVacio(this.getId());
		} else {
			res = new ValorCampoSimple(this.getId(), this.getValor());
		}
		return res;
	}

	@Override
	public void reemplazaValor(ValorCampo valorCampo) {
		if (!this.getId().equals(valorCampo.getId())) {
			throw new ErrorConfiguracionException("No coincideix id");
		}
		if (this.getTipo() != valorCampo.getTipo()) {
			throw new ErrorConfiguracionException("No coincideix tipus");
		}
		final ValorCampoSimple valorNew = (ValorCampoSimple) valorCampo;
		this.setValor(valorNew.getValor());
	}

}
