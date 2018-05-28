package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.script.ClzParametrosDominioInt;

/**
 * Permite especificar parametros de un dominio.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzParametrosDominio implements ClzParametrosDominioInt {

	/**
	 * Parámetros.
	 */
	private final ParametrosDominio parametros = new ParametrosDominio();

	@Override
	public void addParametro(final String codigo, final String parametro) {
		parametros.addParametro(codigo, parametro);
	}

	/**
	 * Obtiene lista parámetros.
	 *
	 * @return el atributo parametros
	 */
	public ParametrosDominio getParametros() {
		return parametros;
	}

}
