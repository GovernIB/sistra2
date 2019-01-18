package es.caib.sistramit.core.service.component.script.plugins.formulario;

import javax.script.ScriptException;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorResetCampos;
import es.caib.sistramit.core.service.component.script.plugins.ClzValorCampoMultiple;
import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;
import es.caib.sistramit.core.service.model.script.formulario.ResValorCampoInt;

/**
 *
 * Establecimiento de valor en script autorrellenable.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResValorCampo implements ResValorCampoInt {

	/**
	 * Valor campo.
	 */
	private ValorCampo valorCampo;

	/**
	 * Constructor.
	 *
	 */
	public ResValorCampo() {
		super();
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void resetValor() {
		valorCampo = new ValorResetCampos();
	}

	@Override
	public void setValor(final String valor) throws ScriptException {
		final ValorCampoSimple vs = new ValorCampoSimple();
		vs.setValor(valor);
		valorCampo = vs;
	}

	@Override
	public void setValorCompuesto(final String codigo, final String descripcion) {
		final ValorCampoIndexado vc = new ValorCampoIndexado();
		vc.setValor(new ValorIndexado(codigo, descripcion));
		valorCampo = vc;
	}

	@Override
	public void setValorMultiple(final ClzValorCampoMultipleInt valores) {
		final ValorCampoListaIndexados vci = new ValorCampoListaIndexados();
		if (valores != null) {
			for (int i = 0; i < valores.getNumeroValores(); i++) {
				final ClzValorCampoCompuestoInt vcc = valores.getValor(i);
				vci.addValorIndexado(vcc.getCodigo(), vcc.getDescripcion());
			}
		}
		valorCampo = vci;
	}

	@Override
	public ClzValorCampoMultipleInt crearValorMultiple() {
		return new ClzValorCampoMultiple();
	}

	/**
	 * Obtiene el valor del campo establecido mediante el script.
	 *
	 * @return valorCampo
	 */
	public ValorCampo getValorCampo() {
		return valorCampo;
	}

}
