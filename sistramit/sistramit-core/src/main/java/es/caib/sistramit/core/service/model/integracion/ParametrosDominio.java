package es.caib.sistramit.core.service.model.integracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Permite especificar parametros de un dominio.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ParametrosDominio implements Serializable {

	/**
	 * Parámetros.
	 */
	private final List<ParametroDominio> parametros = new ArrayList<ParametroDominio>();

	/**
	 * Añade parámetro.
	 *
	 * @param codigo
	 *                      codigo parametro.
	 * @param parametro
	 *                      valor parametro.
	 */
	public void addParametro(final String codigo, final String parametro) {
		final ParametroDominio p = new ParametroDominio();
		p.setCodigo(codigo);
		if (parametro != null) {
			p.setValor(parametro);
		}
		parametros.add(p);
	}

	/**
	 * Busca parametro con el codigo indicado.
	 *
	 * @param codigo
	 *                   codigo
	 * @return Parametro
	 */
	public ParametroDominio getParametro(final String codigo) {
		ParametroDominio res = null;
		if (parametros != null) {
			for (final ParametroDominio p : parametros) {
				if (p.getCodigo().equals(codigo)) {
					res = p;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Obtiene lista parámetros.
	 *
	 * @return el atributo parametros
	 */
	public List<ParametroDominio> getParametros() {
		return parametros;
	}

	/**
	 * Saca parametros en formato cadena para imprimir.
	 */
	public String print() {
		String res = "";
		if (parametros != null) {
			final StringBuffer sb = new StringBuffer(parametros.size() * ConstantesNumero.N50);
			for (final ParametroDominio p : parametros) {
				sb.append(p.getCodigo()).append("=").append("\"" + p.getValor() + "\"");
			}
			res = sb.toString();
		}
		return res;
	}
}
