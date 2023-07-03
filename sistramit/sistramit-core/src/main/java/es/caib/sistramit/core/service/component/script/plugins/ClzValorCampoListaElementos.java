package es.caib.sistramit.core.service.component.script.plugins;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.service.model.script.ClzValorCampoListaElementosInt;
import es.caib.sistramit.core.service.model.script.ClzValorElementoInt;

/**
 * Clase de acceso a un valor de un campo lista elementos desde los plugins.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzValorCampoListaElementos implements ClzValorCampoListaElementosInt {

	/**
	 * Lista de elementos.
	 */
	private final List<ClzValorElementoInt> elementos = new ArrayList<ClzValorElementoInt>();

	@Override
	public ClzValorElementoInt addElemento() {
		final ClzValorElementoInt e = new ClzValorElemento();
		elementos.add(e);
		return e;
	}

	@Override
	public int getNumeroElementos() {
		return elementos.size();
	}

	/**
	 * Obtiene elemento.
	 * 
	 * @param index
	 *                  indice
	 * @return elemento
	 */
	public ClzValorElemento getElemento(final int index) {
		return (ClzValorElemento) elementos.get(index);
	}

}
