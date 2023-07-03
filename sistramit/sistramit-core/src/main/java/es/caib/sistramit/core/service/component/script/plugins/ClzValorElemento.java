package es.caib.sistramit.core.service.component.script.plugins;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;
import es.caib.sistramit.core.service.model.script.ClzValorElementoInt;

/**
 * Clase de acceso a un valor de un elemento.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzValorElemento implements ClzValorElementoInt {

	/**
	 * Lista de valores campo.
	 */
	private final List<ValorCampo> valores = new ArrayList<ValorCampo>();

	@Override
	public void addValor(final String idCampo, final String valor) throws ScriptException {
		valores.add(new ValorCampoSimple(idCampo, valor));
	}

	@Override
	public void addValorCompuesto(final String idCampo, final String codigo, final String descripcion) {
		valores.add(new ValorCampoIndexado(idCampo, codigo, descripcion));
	}

	@Override
	public void addValorMultiple(final String idCampo, final ClzValorCampoMultipleInt valorMultiple) {
		final ValorCampoListaIndexados vli = new ValorCampoListaIndexados();
		vli.setId(idCampo);
		if (valorMultiple != null && valorMultiple.getNumeroValores() > 0) {
			for (int i = 0; i < valorMultiple.getNumeroValores(); i++) {
				final ClzValorCampoCompuestoInt v = valorMultiple.getValor(i);
				vli.addValorIndexado(v.getCodigo(), v.getDescripcion());
			}
		}
		valores.add(vli);
	}

	/**
	 * Obtiene valores campos.
	 * 
	 * @return valores campos
	 */
	public List<ValorCampo> getValores() {
		return valores;
	}

}
