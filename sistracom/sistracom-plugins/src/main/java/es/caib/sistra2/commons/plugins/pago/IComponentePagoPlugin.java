package es.caib.sistra2.commons.plugins.pago;

import org.fundaciobit.plugins.IPlugin;

/**
 * Interface componente pago.
 *
 * @author Indra
 *
 */
public interface IComponentePagoPlugin extends IPlugin {

	/** Prefix. */
	public static final String AUTENTICACION_BASE_PROPERTY = IPLUGIN_BASE_PROPERTIES + "pago.";

	/**
	 * Inicia pago electrónico.
	 *
	 * @param datosPago
	 *            Datos pago.
	 * @param urlCallback
	 *            Url callback.
	 * @return Redirección al pago (identificador pago + url)
	 */
	RedireccionPago iniciarPagoElectronico(DatosPago datosPago, String urlCallback) throws PagoPluginException;

	/**
	 * Verifica estado pago contra pasarela de pago.
	 *
	 * @param identificador
	 *            identificador pago
	 * @return estado pago
	 */
	TypeEstadoPago verificarPagoElectronico(String identificador) throws PagoPluginException;

	/**
	 * Obtiene justificante de pago
	 *
	 * @param identificador
	 *            identificador pago
	 * @return Justificante de pago (nulo si la pasarela no genera justificante).
	 */
	byte[] obtenerJustificantePagoElectronico(String identificador) throws PagoPluginException;

	/**
	 * Obtiene importe tasa.
	 *
	 * @param idTasa
	 *            id tasa
	 * @return importe (en cents)
	 * @throws PagoPluginException
	 */
	int consultaTasa(String idTasa) throws PagoPluginException;

	/**
	 * Obtiene carta de pago presencial (PDF).
	 *
	 * @param datosPago
	 *            Datos pago
	 * @return carta de pago presencial
	 */
	byte[] obtenerCartaPagoPresencial(DatosPago datosPago) throws PagoPluginException;

}
