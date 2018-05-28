package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.HashMap;
import java.util.Map;

import es.caib.sistramit.core.service.model.script.flujo.ResParametrosFormularioInt;

/**
 * Datos para establecer los parámetros de apertura de un formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResParametrosFormulario implements ResParametrosFormularioInt {

	/**
	 * Parámetros de apertura.
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
