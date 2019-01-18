package es.caib.sistramit.core.service.component.script.plugins.formulario;

import java.util.List;

import javax.script.ScriptException;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;
import es.caib.sistramit.core.service.component.script.ScriptUtils;
import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.formulario.PlgDatosFormularioInt;

/**
 * Plugin de acceso a los datos de los campos del formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgDatosFormulario implements PlgDatosFormularioInt {

	/**
	 * Valores campo formulario.
	 */
	private final List<ValorCampo> valoresFormulario;

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Constructor.
	 *
	 * @param pValoresFormulario
	 *            Valores formulario
	 */
	public PlgDatosFormulario(final List<ValorCampo> pValoresFormulario) {
		super();
		valoresFormulario = pValoresFormulario;
	}

	@Override
	public String getValor(final String campo) throws ScriptException {
		String res = "";
		if (valoresFormulario != null) {
			final ValorCampo vc = getValorCampo(campo, TypeValor.SIMPLE);
			if (vc != null) {
				res = ((ValorCampoSimple) vc).getValor();
			}
		}
		return res;
	}

	@Override
	public ClzValorCampoCompuestoInt getValorCompuesto(final String campo) throws ScriptException {
		ClzValorCampoCompuestoInt res = null;
		ValorIndexado vi = null;
		if (valoresFormulario != null) {
			final ValorCampo vc = getValorCampo(campo, TypeValor.INDEXADO);
			if (vc != null) {
				final ValorCampoIndexado vci = ((ValorCampoIndexado) vc);
				vi = vci.getValor();
			}
		}
		res = ScriptUtils.crearValorCompuesto(vi);
		return res;
	}

	@Override
	public int getNumeroValoresValorMultiple(final String campo) throws ScriptException {
		int res = 0;
		final ValorCampoListaIndexados vcl = getValorCampoListaIndexados(campo);
		if (vcl != null && vcl.getValor() != null) {
			res = vcl.getValor().size();
		}
		return res;
	}

	@Override
	public ClzValorCampoCompuestoInt getValorMultiple(final String campo, final int indice) throws ScriptException {
		ClzValorCampoCompuestoInt res = null;
		final ValorCampoListaIndexados vcl = getValorCampoListaIndexados(campo);
		ValorIndexado vi = null;
		if (vcl != null) {
			vi = vcl.getValor().get(indice);
		}
		res = ScriptUtils.crearValorCompuesto(vi);
		return res;
	}

	/**
	 * Obtiene valor del campo.
	 *
	 * @param pIdCampo
	 *            Id campo
	 * @param tipoValor
	 *            Tipo valor
	 * @return Valor campo
	 * @throws ScriptException
	 *             Genera excepcion en caso de que no concuerde el tipo
	 */
	private ValorCampo getValorCampo(final String pIdCampo, final TypeValor tipoValor) throws ScriptException {
		ValorCampo res = null;
		if (this.valoresFormulario != null) {
			for (final ValorCampo vc : this.valoresFormulario) {
				if (vc.getId().equals(pIdCampo)) {
					res = vc;
					break;
				}
			}
			if (res != null && res.getTipo() != tipoValor) {
				throw new ScriptException("El campo " + pIdCampo + " no es de tipo " + tipoValor.name());
			}
		}
		return res;
	}

	/**
	 * Obtiene campo lista indexados.
	 *
	 * @param campo
	 *            Campo
	 * @return ValorCampoListaIndexados
	 * @throws ScriptException
	 *             Genera excepcion si el campo no es del tipo lista indexados
	 */
	private ValorCampoListaIndexados getValorCampoListaIndexados(final String campo) throws ScriptException {
		ValorCampoListaIndexados res = null;
		if (valoresFormulario != null) {
			final ValorCampo vc = getValorCampo(campo, TypeValor.LISTA_INDEXADOS);
			res = (ValorCampoListaIndexados) vc;
		}
		return res;
	}

}
