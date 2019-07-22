package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Parámetros apertura del formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ParametrosAperturaFormulario implements Serializable {

	/**
	 * Parámetros de apertura.
	 */
	private List<ParametroAperturaFormulario> parametros = new ArrayList<>();

	/**
	 * Añade parámetro.
	 *
	 * @param pCodigo
	 *                    Código
	 * @param pValor
	 *                    Valor
	 */
	public void addParametro(final String pCodigo, final String pValor) {
		boolean existe = false;
		for (final ParametroAperturaFormulario p : parametros) {
			if (StringUtils.equals(p.getCodigo(), pCodigo)) {
				p.setValor(pValor);
				existe = true;
				break;
			}
		}
		if (!existe) {
			parametros.add(new ParametroAperturaFormulario(pCodigo, pValor));
		}
	}

	/**
	 * Obtiene parámetros.
	 *
	 * @param idParametro
	 *                        id parametro
	 * @return Valor
	 */
	public String getParametro(final String idParametro) {
		String res = "";
		for (final ParametroAperturaFormulario p : parametros) {
			if (StringUtils.equals(p.getCodigo(), idParametro)) {
				res = p.getValor();
				break;
			}
		}
		return res;
	}

	/**
	 * Obtiene si existe parámetro.
	 *
	 * @param idParametro
	 *                        id parametro
	 * @return boolean si existe
	 */
	public boolean existeParametro(final String idParametro) {
		boolean res = false;
		for (final ParametroAperturaFormulario p : parametros) {
			if (StringUtils.equals(p.getCodigo(), idParametro)) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**
	 * Obtiene lista de los id de parametros.
	 *
	 * @return Lista de los id de parametros.
	 */
	public List<String> obtenerListaParametrosId() {
		final List<String> res = new ArrayList<>();
		for (final ParametroAperturaFormulario p : parametros) {
			res.add(p.getCodigo());
		}
		return res;
	}

	/**
	 * Método de acceso a parametros.
	 *
	 * @return parametros
	 */
	public List<ParametroAperturaFormulario> getParametros() {
		return parametros;
	}

	/**
	 * Método para establecer parametros.
	 *
	 * @param parametros
	 *                       parametros a establecer
	 */
	public void setParametros(final List<ParametroAperturaFormulario> parametros) {
		this.parametros = parametros;
	}
}
