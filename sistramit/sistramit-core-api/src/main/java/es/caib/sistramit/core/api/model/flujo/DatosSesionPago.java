package es.caib.sistramit.core.api.model.flujo;

import java.util.Date;

import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;

/**
 * Datos almacenados en la sesión de pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DatosSesionPago implements ModelApi {

	/** Identificador pasarela de pago a utilizar. */
	private String pasarelaId;

	/** Entidad. */
	private String entidadId;

	/** Código organismo dentro de la entidad (opcional según pasarela pago). */
	private String organismoId;

	/** Indica si pago simulado. */
	private boolean simulado;

	/** Identificador pago. */
	private String identificadorPago;

	/** Presentacion pago. */
	private TypePresentacion presentacion;

	/** Fecha pago en caso de estar pagado. */
	private Date fechaPago;

	/** Identificador pago pasarela en caso de estar pagado. */
	private String localizador;

	/** Idioma. */
	private String idioma;

	/** Sujeto pasivo. */
	private Persona sujetoPasivo;

	/** Modelo pago. */
	private String modelo;

	/** Concepto pago. */
	private String concepto;

	/** Código tasa (opcional según pasarela pago). */
	private String tasaId;

	/** Importe (en cents). */
	private int importe;

	/** Detalle pago (info debug a pasar a pasarela pago). */
	private String detallePago;

	/**
	 * Indica si se filtran los métodos de pago (lista separada por ; ). Si no se
	 * establece, se mostarán los activos por defecto.
	 */
	private String metodosPago;

	/**
	 * Método para establecer identificadorPago.
	 *
	 * @param identificadorPago
	 *                              identificadorPago a establecer
	 */
	public void setIdentificadorPago(final String identificadorPago) {
		this.identificadorPago = identificadorPago;
	}

	/**
	 * Método de acceso a identificadorPago.
	 *
	 * @return identificadorPago
	 */
	public String getIdentificadorPago() {
		return identificadorPago;
	}

	/**
	 * Método de acceso a fechaPago.
	 *
	 * @return fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * Método para establecer fechaPago.
	 *
	 * @param fechaPago
	 *                      fechaPago a establecer
	 */
	public void setFechaPago(final Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * Método de acceso a localizador.
	 *
	 * @return localizador
	 */
	public String getLocalizador() {
		return localizador;
	}

	/**
	 * Método para establecer localizador.
	 *
	 * @param localizador
	 *                        localizador a establecer
	 */
	public void setLocalizador(final String localizador) {
		this.localizador = localizador;
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
	 * Método de acceso a organismoId.
	 *
	 * @return organismoId
	 */
	public String getOrganismoId() {
		return organismoId;
	}

	/**
	 * Método para establecer organismoId.
	 *
	 * @param organismoId
	 *                        organismoId a establecer
	 */
	public void setOrganismoId(final String organismoId) {
		this.organismoId = organismoId;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
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
	 * Método de acceso a tasaId.
	 *
	 * @return tasaId
	 */
	public String getTasaId() {
		return tasaId;
	}

	/**
	 * Método para establecer tasaId.
	 *
	 * @param tasaId
	 *                   tasaId a establecer
	 */
	public void setTasaId(final String tasaId) {
		this.tasaId = tasaId;
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
	 * Método de acceso a detallePago.
	 *
	 * @return detallePago
	 */
	public String getDetallePago() {
		return detallePago;
	}

	/**
	 * Método para establecer detallePago.
	 *
	 * @param detallePago
	 *                        detallePago a establecer
	 */
	public void setDetallePago(final String detallePago) {
		this.detallePago = detallePago;
	}

	/**
	 * Método de acceso a simulado.
	 *
	 * @return simulado
	 */
	public boolean isSimulado() {
		return simulado;
	}

	/**
	 * Método para establecer simulado.
	 *
	 * @param simulado
	 *                     simulado a establecer
	 */
	public void setSimulado(final boolean simulado) {
		this.simulado = simulado;
	}

	/**
	 * Método de acceso a presentacion.
	 *
	 * @return presentacion
	 */
	public TypePresentacion getPresentacion() {
		return presentacion;
	}

	/**
	 * Método para establecer presentacion.
	 *
	 * @param presentacion
	 *                         presentacion a establecer
	 */
	public void setPresentacion(final TypePresentacion presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * Método de acceso a sujetoPasivo.
	 *
	 * @return sujetoPasivo
	 */
	public Persona getSujetoPasivo() {
		return sujetoPasivo;
	}

	/**
	 * Método para establecer sujetoPasivo.
	 *
	 * @param sujetoPasivo
	 *                         sujetoPasivo a establecer
	 */
	public void setSujetoPasivo(final Persona sujetoPasivo) {
		this.sujetoPasivo = sujetoPasivo;
	}

	/**
	 * Método de acceso a entidadId.
	 *
	 * @return entidadId
	 */
	public String getEntidadId() {
		return entidadId;
	}

	/**
	 * Método para establecer entidadId.
	 *
	 * @param entidadId
	 *                      entidadId a establecer
	 */
	public void setEntidadId(final String entidadId) {
		this.entidadId = entidadId;
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
