package es.caib.sistramit.core.api.model.formulario;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;

/**
 * Valor de un campo con lista de elementos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValorCampoListaElementos extends ValorCampo {

	/**
	 * Valor del campo.
	 */
	private List<ValorElemento> valor = new ArrayList<>();

	/**
	 * Constructor.
	 */
	public ValorCampoListaElementos() {
		super();
		this.setTipo(TypeValor.LISTA_ELEMENTOS);
	}

	/**
	 * Obtiene valor.
	 *
	 * @return valor
	 */
	public List<ValorElemento> getValor() {
		return valor;
	}

	/**
	 * Establece valor.
	 *
	 * @param valor
	 *                  valor
	 */
	public void setValor(final List<ValorElemento> valor) {
		this.valor = valor;
	}

	public void addElemento(final ValorElemento elemento) {
		valor.add(elemento);
	}

	/**
	 * Crea valor vacío.
	 *
	 * @param pIdentificador
	 *                           Identificador
	 * @return Valor vacío.
	 */
	public static ValorCampo createValorVacio(final String pIdentificador) {
		final ValorCampoListaElementos vcl = new ValorCampoListaElementos();
		vcl.setId(pIdentificador);
		vcl.setValor(new ArrayList<ValorElemento>());
		// vcl.setValor(null);
		return vcl;
	}

	@Override
	public boolean esVacio() {
		return (valor == null || valor.isEmpty());
	}

	@Override
	public boolean esValorIgual(final ValorCampo pValorCampo) {
		boolean res = false;
		if (pValorCampo.esVacio() == this.esVacio() && pValorCampo.getTipo() == this.getTipo()) {
			if (!pValorCampo.esVacio()) {
				final ValorCampoListaElementos vco = (ValorCampoListaElementos) pValorCampo;
				res = vco.getValor().equals(this.getValor());
			} else {
				res = true;
			}
		}
		return res;
	}

	@Override
	public ValorCampo duplicar() {
		ValorCampoListaElementos res;
		if (this.esVacio()) {
			res = (ValorCampoListaElementos) createValorVacio(this.getId());
		} else {
			res = new ValorCampoListaElementos();
			res.setId(this.getId());
			for (final ValorElemento vi : this.valor) {
				res.addElemento(vi.duplicar());
			}
		}
		return res;
	}

	@Override
	public String print() {
		String res = null;
		if (!this.esVacio()) {
			final StringBuffer sb = new StringBuffer(200 * this.getValor().size());
			int i = 1;
			for (final ValorElemento ve : this.getValor()) {
				sb.append("\nElemento " + i + ":");
				if (ve.getElemento() != null) {
					for (final ValorCampo vc : ve.getElemento()) {
						sb.append("		" + vc.print());
					}
				}
				i++;
			}
			res = sb.toString();
		} else {
			res = "";
		}
		return res;
	}

	@Override
	public void reemplazaValor(final ValorCampo valorCampo) {
		if (!this.getId().equals(valorCampo.getId())) {
			throw new ErrorConfiguracionException("No coincideix id");
		}
		if (this.getTipo() != valorCampo.getTipo()) {
			throw new ErrorConfiguracionException("No coincide tipus");
		}
		final ValorCampoListaElementos valorNew = (ValorCampoListaElementos) valorCampo;
		if (valorNew.getValor() == null) {
			this.valor = null;
		} else {
			this.valor = new ArrayList<>();
			for (final ValorElemento vi : valorNew.getValor()) {
				this.valor.add(vi.duplicar());
			}
		}
	}

}
