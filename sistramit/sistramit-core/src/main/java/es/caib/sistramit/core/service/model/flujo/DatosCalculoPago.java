package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.Persona;

/**
 * Datos calculados para realizar un pago via pasarela de pagos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosCalculoPago implements Serializable {

	/**
	 * Pasarela a usar.
	 */
	private String pasarelaId;

	/**
	 * Contribuyente. Por defecto el usuario que realiza el trámite.
	 */
	private Persona contribuyente;

	/**
	 * Fecha devengo (dd/mm/yyyy hh:mi:ss).
	 */
	private String fecha;

	/**
	 * Modelo de pago.
	 */
	private String modelo;

	/**
	 * Concepto del pago.
	 */
	private String concepto;

	/**
	 * Importe (en cents).
	 */
	private int importe;

	/**
	 * Organismo (depende pasarela).
	 */
	private String organismo;

	/**
	 * Tasa.
	 */
	private String tasa;

	/**
	 * Método de acceso a contribuyente.
	 *
	 * @return contribuyente
	 */
	public Persona getContribuyente() {
		return contribuyente;
	}

	/**
	 * Indica si se filtran los métodos de pago (lista separada por ; ). Si no se
	 * establece, se mostarán los activos por defecto.
	 */
	private String metodosPago;

	/**
	 * Método para establecer contribuyente.
	 *
	 * @param contribuyente
	 *                          contribuyente a establecer
	 */
	public void setContribuyente(final Persona contribuyente) {
		this.contribuyente = contribuyente;
	}

	/**
	 * Método de acceso a fecha.
	 *
	 * @return fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Método para establecer fecha.
	 *
	 * @param fecha
	 *                  fecha a establecer
	 */
	public void setFecha(final String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Método de acceso a modelo.
	 *
	 * @return modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * Método para establecer modelo.
	 *
	 * @param modelo
	 *                   modelo a establecer
	 */
	public void setModelo(final String modelo) {
		this.modelo = modelo;
	}

	/**
	 * Método de acceso a concepto.
	 *
	 * @return concepto
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * Método para establecer concepto.
	 *
	 * @param concepto
	 *                     concepto a establecer
	 */
	public void setConcepto(final String concepto) {
		this.concepto = concepto;
	}

	/**
	 * Método de acceso a importe.
	 *
	 * @return importe
	 */
	public int getImporte() {
		return importe;
	}

	/**
	 * Método para establecer importe.
	 *
	 * @param importe
	 *                    importe a establecer
	 */
	public void setImporte(final int importe) {
		this.importe = importe;
	}

	/**
	 * Método de acceso a organismo.
	 *
	 * @return organismo
	 */
	public String getOrganismo() {
		return organismo;
	}

	/**
	 * Método para establecer organismo.
	 *
	 * @param organismo
	 *                      organismo a establecer
	 */
	public void setOrganismo(final String organismo) {
		this.organismo = organismo;
	}

	/**
	 * Método de acceso a pasarelaId.
	 *
	 * @return pasarelaId
	 */
	public String getPasarelaId() {
		return pasarelaId;
	}

	/**
	 * Método para establecer pasarelaId.
	 *
	 * @param pasarelaId
	 *                       pasarelaId a establecer
	 */
	public void setPasarelaId(final String pasarelaId) {
		this.pasarelaId = pasarelaId;
	}

	/**
	 * Método de acceso a tasa.
	 *
	 * @return tasa
	 */
	public String getTasa() {
		return tasa;
	}

	/**
	 * Método para establecer tasa.
	 *
	 * @param tasa
	 *                 tasa a establecer
	 */
	public void setTasa(final String tasa) {
		this.tasa = tasa;
	}

	/**
	 * Método de acceso a metodosPago.
	 * 
	 * @return metodosPago
	 */
	public String getMetodosPago() {
		return metodosPago;
	}

	/**
	 * Método para establecer metodosPago.
	 * 
	 * @param metodosPago
	 *                        metodosPago a establecer
	 */
	public void setMetodosPago(final String metodosPago) {
		this.metodosPago = metodosPago;
	}
}
