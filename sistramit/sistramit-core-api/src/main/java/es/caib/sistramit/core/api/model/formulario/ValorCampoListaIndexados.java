package es.caib.sistramit.core.api.model.formulario;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;

/**
 * Valor de un campo con lista de valores indexados (selección múltiple).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValorCampoListaIndexados extends ValorCampo {

	/**
	 * Valor del campo.
	 */
	private List<ValorIndexado> valor = new ArrayList<ValorIndexado>();

	/**
	 * Constructor.
	 */
	public ValorCampoListaIndexados() {
		super();
		this.setTipo(TypeValor.LISTA_INDEXADOS);
	}

	/**
	 * Método de acceso a valor.
	 *
	 * @return valor
	 */
	public List<ValorIndexado> getValor() {
		return valor;
	}

	/**
	 * Método para establecer valor.
	 *
	 * @param pValor
	 *            valor a establecer
	 */
	public void setValor(final List<ValorIndexado> pValor) {
		valor = pValor;
	}

	/**
	 * Añade valor indexado.
	 *
	 * @param codigo
	 *            Código
	 * @param descripcion
	 *            Descripción
	 */
	public void addValorIndexado(final String codigo, final String descripcion) {
		valor.add(new ValorIndexado(codigo, descripcion));
	}

	/**
	 * Crea instancia.
	 *
	 * @return Nueva instancia
	 */
	public static ValorCampoListaIndexados createNewValorCampoListaIndexados() {
		return new ValorCampoListaIndexados();
	}

	/**
	 * Crea valor vacío.
	 *
	 * @param pIdentificador
	 *            Identificador
	 * @return Valor vacío.
	 */
	public static ValorCampo createValorVacio(final String pIdentificador) {
		final ValorCampoListaIndexados vcl = new ValorCampoListaIndexados();
		vcl.setId(pIdentificador);
		vcl.setValor(null);
		return vcl;
	}

	@Override
	public boolean esVacio() {
		return (getValor() == null || getValor().size() == 0);
	}

	@Override
	public String print() {
		String res = null;
		if (!this.esVacio()) {
			final StringBuffer sb = new StringBuffer(50 * this.getValor().size());
			for (final ValorIndexado vi : this.getValor()) {
				sb.append("[ " + vi.getValor() + " - " + vi.getDescripcion() + " ] ");
			}
			res = sb.toString();
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
				final ValorCampoListaIndexados vco = (ValorCampoListaIndexados) pValorCampo;
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
			res = new ValorCampoListaIndexados();
			res.setId(this.getId());
			for (final ValorIndexado vi : this.valor) {
				((ValorCampoListaIndexados) res).addValorIndexado(vi.getValor(), vi.getDescripcion());
			}
		}
		return res;
	}

	@Override
	public void reemplazaValor(ValorCampo valorCampo) {
		if (!this.getId().equals(valorCampo.getId())) {
			throw new ErrorConfiguracionException("No coincide id");
		}
		if (this.getTipo() != valorCampo.getTipo()) {
			throw new ErrorConfiguracionException("No coincide tipo");
		}
		final ValorCampoListaIndexados valorNew = (ValorCampoListaIndexados) valorCampo;
		if (valorNew.getValor() == null) {
			this.valor = null;
		} else {
			this.valor = new ArrayList<>();
			for (final ValorIndexado vi : valorNew.getValor()) {
				this.valor.add(new ValorIndexado(vi.getValor(), vi.getDescripcion()));
			}
		}
	}

}
