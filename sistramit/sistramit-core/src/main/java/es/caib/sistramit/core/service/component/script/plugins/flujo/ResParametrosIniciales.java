package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.HashMap;
import java.util.Map;

import es.caib.sistramit.core.service.model.script.ResParametrosInicialesInt;

/**
 * Datos para establecer los parámetros iniciales trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResParametrosIniciales implements ResParametrosInicialesInt {

	/**
	 * Parámetros iniciales.
	 */
	private final Map<String, String> parametros = new HashMap<>();

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void addParametro(final String codigo, final String valor) {
		parametros.put(codigo, valor);
	}

	/**
	 * Método de acceso a parámetros.
	 *
	 * @return Parámetros apertura
	 */
	public Map<String, String> getParametros() {
		return parametros;
	}

}
