package es.caib.sistramit.rest.api.interna;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Datos almacenados en la sesión de pago.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RDatosSesionPago", description = "Datos Sesion Pago")
public class RDatosSesionPago {

	/** Identificador pasarela de pago a utilizar. */
	@ApiModelProperty(value = "Identificador pasarela")
	private String pasarelaId;

	/** Entidad. */
	@ApiModelProperty(value = "Identificador entidad")
	private String entidadId;

	/** Código organismo dentro de la entidad (opcional según pasarela pago). */
	@ApiModelProperty(value = "Codigo organismo dentro de la entidad")
	private String organismoId;

	/** Indica si pago simulado. */
	@ApiModelProperty(value = "Simulado")
	private boolean simulado;

	/** Identificador pago. */
	@ApiModelProperty(value = "Identificador Pago")
	private String identificadorPago;

	/** Presentacion pago. */
	@ApiModelProperty(value = "Presentacion pago")
	private String presentacion;

	/** Fecha pago en caso de estar pagado. */
	@ApiModelProperty(value = "Fecha pago")
	private Date fechaPago;

	/** Identificador pago pasarela en caso de estar pagado. */
	@ApiModelProperty(value = "Identificador pago pasarela")
	private String localizador;

	/** Idioma. */
	@ApiModelProperty(value = "Idioma")
	private String idioma;

	/** Sujeto pasivo. */
	@ApiModelProperty(value = "Sujeto pasivo Nombre")
	private String sujetoPasivoNombre;

	@ApiModelProperty(value = "Sujeto pasivo Nif")
	private String sujetoPasivoNif;

	/** Modelo pago. */
	@ApiModelProperty(value = "Modelo")
	private String modelo;

	/** Concepto pago. */
	@ApiModelProperty(value = "Concepto")
	private String concepto;

	/** Código tasa (opcional según pasarela pago). */
	@ApiModelProperty(value = "Codigo tasa")
	private String tasaId;

	/** Importe (en cents). */
	@ApiModelProperty(value = "Importe")
	private int importe;

	/** Detalle pago (info debug a pasar a pasarela pago). */
	@ApiModelProperty(value = "Detalle Pago")
	private String detallePago;

	/**
	 * Método para establecer identificadorPago.
	 *
	 * @param identificadorPago
	 *            identificadorPago a establecer
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
	 *            fechaPago a establecer
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
	 *            localizador a establecer
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
	 *            pasarelaId a establecer
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
	 *            organismoId a establecer
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
	 *            idioma a establecer
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
	 *            modelo a establecer
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
	 *            concepto a establecer
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
	 *            tasaId a establecer
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
	 *            importe a establecer
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
	 *            detallePago a establecer
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
	 *            simulado a establecer
	 */
	public void setSimulado(final boolean simulado) {
		this.simulado = simulado;
	}

	/**
	 * Método de acceso a presentacion.
	 *
	 * @return presentacion
	 */
	public String getPresentacion() {
		return presentacion;
	}

	/**
	 * Método para establecer presentacion.
	 *
	 * @param presentacion
	 *            presentacion a establecer
	 */
	public void setPresentacion(final String presentacion) {
		this.presentacion = presentacion;
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
	 *            entidadId a establecer
	 */
	public void setEntidadId(final String entidadId) {
		this.entidadId = entidadId;
	}

	/**
	 * Obtiene el valor de sujetoPasivoNombre.
	 *
	 * @return el valor de sujetoPasivoNombre
	 */
	public String getSujetoPasivoNombre() {
		return sujetoPasivoNombre;
	}

	/**
	 * Establece el valor de sujetoPasivoNombre.
	 *
	 * @param sujetoPasivoNombre
	 *            el nuevo valor de sujetoPasivoNombre
	 */
	public void setSujetoPasivoNombre(final String sujetoPasivoNombre) {
		this.sujetoPasivoNombre = sujetoPasivoNombre;
	}

	/**
	 * Obtiene el valor de sujetoPasivoNif.
	 *
	 * @return el valor de sujetoPasivoNif
	 */
	public String getSujetoPasivoNif() {
		return sujetoPasivoNif;
	}

	/**
	 * Establece el valor de sujetoPasivoNif.
	 *
	 * @param sujetoPasivoNif
	 *            el nuevo valor de sujetoPasivoNif
	 */
	public void setSujetoPasivoNif(final String sujetoPasivoNif) {
		this.sujetoPasivoNif = sujetoPasivoNif;
	}
}
