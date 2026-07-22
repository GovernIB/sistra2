package es.caib.sistramit.core.service.model.script.flujo;

import es.caib.sistramit.core.service.component.script.plugins.flujo.ClzPagoDinamico;
import es.caib.sistramit.core.service.model.script.PluginScriptRes;

import javax.script.ScriptException;

/**
 * * Datos para establecer los pagos de forma dinámica.
 *
 * @author Indra
 *
 */
public interface ResPagosDinamicosInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_PAGOSDINAMICOS";

	/**
	 * Crear pago dinámico para establecer sus propiedades.
	 *
	 * @return anexo
	 */
	ClzPagoDinamico crearPago();

	/**
	 * Añade un nuevo pago.
	 *
	 * @param pago
	 *                  pago dinámico
	 * @throws ScriptException
	 *                             Excepcion
	 */
	void addPago(final ClzPagoDinamico pago) throws ScriptException;

}
