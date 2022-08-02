package es.caib.sistramit.core.service.model.script.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.flujo.DatosCalculoPago;
import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Datos que se pueden establecer en un pago.
 *
 * @author Indra
 *
 */
public interface ResPagoInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_PAGO";

	/**
	 * Establece id pasarela.
	 *
	 * @param pasarelaId
	 *                       pasarelaId
	 */
	void setPasarela(String pasarelaId);

	/**
	 * Establece id organismo (opcional, depende del plugin).
	 *
	 * @param organismoId
	 *                        organismoId
	 */
	void setOrganismo(String organismoId);

	/**
	 * Establece contribuyente.
	 *
	 * @param nif
	 *                   nif
	 * @param nombre
	 *                   nombre
	 * @throws ScriptException
	 */
	void setContribuyente(String nif, String nombre) throws ScriptException;

	/**
	 * Establece detalle pago.
	 *
	 * @param tasa
	 *                     codigo
	 * @param modelo
	 *                     modelo
	 * @param concepto
	 *                     concepto
	 * @param importe
	 *                     importe en cents
	 * @throws ScriptException
	 */
	void setDetallePago(String modelo, String concepto, String tasa, int importe) throws ScriptException;

	/**
	 * Indica si se filtran los métodos de pago . Si no se establece, se mostarán
	 * los activos por defecto.
	 * 
	 * @param metodos
	 *                    lista separada por ;
	 */
	void setMetodosPago(String metodos);

	/**
	 * Método de acceso a datosPago.
	 *
	 * @return datosPago
	 */
	DatosCalculoPago getDatosPago();

}
