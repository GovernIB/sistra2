package es.caib.sistra2.commons.plugins.pago.api;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface componente pago.
 *
 * @author Indra
 *
 */
public interface IComponentePagoPlugin extends IPlugin {

	/** Prefix. */
	public static final String PAGO_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "pago.";

	/**
	 * Inicia pago electrónico.
	 *
	 * @param datosPago
	 *            Datos pago.
	 * @param urlCallback
	 *            Url callback.
	 * @return Redirección al pago (identificador pago + url)
	 */
	RedireccionPago iniciarPagoElectronico(final DatosPago datosPago, final String urlCallback)
			throws PagoPluginException;

	/**
	 * Verifica estado pago contra pasarela de pago.
	 *
	 * @param identificador
	 *            identificador pago
	 * @return estado pago
	 */
	EstadoPago verificarPagoElectronico(final String identificador) throws PagoPluginException;

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
	 * @param idPasarela
	 *            id pasarela
	 * @param idTasa
	 *            id tasa
	 * @return importe (en cents)
	 * @throws PagoPluginException
	 */
	int consultaTasa(final String idPasarela, final String idTasa) throws PagoPluginException;

	/**
	 * Indica si pasarela permite pago presencial.
	 *
	 * @param idPasarela
	 *            id pasarela
	 * @return Indica si pasarela permite pago presencial. * @throws
	 *         PagoPluginException
	 */
	boolean permitePagoPresencial(final String idPasarela) throws PagoPluginException;

	/**
	 * Obtiene carta de pago presencial (PDF).
	 *
	 * @param datosPago
	 *            Datos pago
	 * @return carta de pago presencial
	 */
	byte[] obtenerCartaPagoPresencial(final DatosPago datosPago) throws PagoPluginException;

}
