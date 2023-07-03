package es.caib.sistramit.core.service.component.script.plugins.formulario;

import java.util.List;

import javax.script.ScriptException;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaElementos;
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

	/** Valores campo formulario. */
	private final List<ValorCampo> valoresFormulario;

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Constructor.
	 *
	 * @param pValoresFormulario
	 *                               Valores formulario
	 * @param pEdicion
	 *                               edicion
	 */
	public PlgDatosFormulario(final List<ValorCampo> pValoresFormulario) {
		super();
		valoresFormulario = pValoresFormulario;
	}

	@Override
	public String getValor(final String campo) throws ScriptException {
		String res = "";
		if (valoresFormulario != null) {
			final ValorCampo vc = getValorCampo(this.valoresFormulario, campo, TypeValor.SIMPLE);
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
			final ValorCampo vc = getValorCampo(this.valoresFormulario, campo, TypeValor.INDEXADO);
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
		final ValorCampoListaIndexados vcl = getValorCampoListaIndexados(this.valoresFormulario, campo);
		if (vcl != null && vcl.getValor() != null) {
			res = vcl.getValor().size();
		}
		return res;
	}

	@Override
	public ClzValorCampoCompuestoInt getValorMultiple(final String campo, final int indice) throws ScriptException {
		ClzValorCampoCompuestoInt res = null;
		final ValorCampoListaIndexados vcl = getValorCampoListaIndexados(this.valoresFormulario, campo);
		ValorIndexado vi = null;
		if (vcl != null) {
			vi = vcl.getValor().get(indice);
		}
		res = ScriptUtils.crearValorCompuesto(vi);
		return res;
	}

	@Override
	public int getNumeroElementos(final String campoListaElementos) throws ScriptException {
		int res = 0;
		final ValorCampoListaElementos vcle = getValorCampoListaElementos(this.valoresFormulario, campoListaElementos);
		if (vcle != null && vcle.getValor() != null) {
			res = vcle.getValor().size();
		}
		return res;
	}

	@Override
	public String getValorElemento(final String campoListaElementos, final int numElemento, final String campo)
			throws ScriptException {
		String res = "";
		final ValorCampoListaElementos vcle = getValorCampoListaElementos(this.valoresFormulario, campoListaElementos);
		if (vcle != null && vcle.getValor() != null) {
			final ValorCampoSimple vc = (ValorCampoSimple) getValorCampo(vcle.getValor().get(numElemento).getElemento(),
					campo, TypeValor.SIMPLE);
			if (vc != null) {
				res = vc.getValor();
			}
		}
		return res;
	}

	@Override
	public ClzValorCampoCompuestoInt getValorElementoCompuesto(final String campoListaElementos, final int numElemento,
			final String campo) throws ScriptException {
		ClzValorCampoCompuestoInt res = null;
		ValorIndexado vi = null;
		final ValorCampoListaElementos vcle = getValorCampoListaElementos(this.valoresFormulario, campoListaElementos);
		if (vcle != null && vcle.getValor() != null) {
			final ValorCampoIndexado vc = (ValorCampoIndexado) getValorCampo(
					vcle.getValor().get(numElemento).getElemento(), campo, TypeValor.INDEXADO);
			if (vc != null) {
				vi = vc.getValor();
			}
		}
		res = ScriptUtils.crearValorCompuesto(vi);
		return res;
	}

	@Override
	public ClzValorCampoCompuestoInt getValorElementoMultiple(final String campoListaElementos, final int numElemento,
			final String campo, final int indice) throws ScriptException {
		ClzValorCampoCompuestoInt res = null;
		ValorIndexado vi = null;
		final ValorCampoListaElementos vcle = getValorCampoListaElementos(this.valoresFormulario, campoListaElementos);
		if (vcle != null && vcle.getValor() != null) {
			final ValorCampoListaIndexados vc = (ValorCampoListaIndexados) getValorCampo(
					vcle.getValor().get(numElemento).getElemento(), campo, TypeValor.LISTA_INDEXADOS);
			if (vc != null) {
				vi = vc.getValor().get(indice);
			}
		}
		res = ScriptUtils.crearValorCompuesto(vi);
		return res;
	}

	@Override
	public int getNumeroValoresValorElementoMultiple(final String campoListaElementos, final int numElemento,
			final String campo) throws ScriptException {
		int res = 0;
		final ValorCampoListaElementos vcle = getValorCampoListaElementos(this.valoresFormulario, campoListaElementos);
		if (vcle != null && vcle.getValor() != null) {
			final ValorCampoListaIndexados vc = (ValorCampoListaIndexados) getValorCampo(
					vcle.getValor().get(numElemento).getElemento(), campo, TypeValor.LISTA_INDEXADOS);
			if (vc != null) {
				res = vc.getValor().size();
			}
		}
		return res;
	}

	// -----------------------------------------------------------------
	// FUNCIONES AUXILIARES
	// -----------------------------------------------------------------

	/**
	 * Obtiene valor del campo.
	 *
	 * @param pIdCampo
	 *                      Id campo
	 * @param tipoValor
	 *                      Tipo valor
	 * @return Valor campo
	 * @throws ScriptException
	 *                             Genera excepcion en caso de que no concuerde el
	 *                             tipo
	 */
	private ValorCampo getValorCampo(final List<ValorCampo> valores, final String pIdCampo, final TypeValor tipoValor)
			throws ScriptException {
		ValorCampo res = null;
		if (valores != null) {
			for (final ValorCampo vc : valores) {
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
	 *                  Campo
	 * @return ValorCampoListaIndexados
	 * @throws ScriptException
	 *                             Genera excepcion si el campo no es del tipo lista
	 *                             indexados
	 */
	private ValorCampoListaIndexados getValorCampoListaIndexados(final List<ValorCampo> valores, final String campo)
			throws ScriptException {
		ValorCampoListaIndexados res = null;
		if (valores != null) {
			final ValorCampo vc = getValorCampo(valores, campo, TypeValor.LISTA_INDEXADOS);
			res = (ValorCampoListaIndexados) vc;
		}
		return res;
	}

	/**
	 * Obtiene campo lista elementos.
	 *
	 * @param campo
	 *                  Campo
	 * @return ValorCampoListaElementos
	 * @throws ScriptException
	 *                             Genera excepcion si el campo no es del tipo lista
	 *                             elementos
	 */
	private ValorCampoListaElementos getValorCampoListaElementos(final List<ValorCampo> valores, final String campo)
			throws ScriptException {
		ValorCampoListaElementos res = null;
		if (valores != null) {
			final ValorCampo vc = getValorCampo(valores, campo, TypeValor.LISTA_ELEMENTOS);
			res = (ValorCampoListaElementos) vc;
		}
		return res;
	}

}
