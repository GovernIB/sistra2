package es.caib.sistra2.commons.plugins.pago.api;

/**
 * Datos pago.
 *
 * @author Indra
 *
 */
public class DatosPago {

	/** Identificador pasarela de pago a utilizar. */
	private String pasarelaId;

	/** Identicador entidad. */
	private String entidadId;

	/** Código organismo dentro de la entidad (opcional según pasarela pago). */
	private String organismoId;

	/** Detalle pago (información acerca del pago, p.e. trámite, etc.). */
	private String detallePago;

	/** Idioma. */
	private String idioma;

	/** Sujeto pasivo nif. */
	private String sujetoPasivoNif;

	/** Sujeto pasivo nombre. */
	private String sujetoPasivoNombre;

	/** Modelo pago. */
	private String modelo;

	/** Concepto pago. */
	private String concepto;

	/** Código tasa (opcional según pasarela pago). */
	private String tasaId;

	/** Importe (en cents). */
	private int importe;

	/**
	 * Indica si se filtran los métodos de pago (lista separada por ; ). Si no se
	 * establece, se mostarán los activos por defecto.
	 */
	private String metodosPago;

	/** Identificador del trámite*/
    private String idTramite;

    /** Versión del trámite*/
    private Integer versionTramite;

	/**
	 * Método de acceso a identificador.
	 *
	 * @return identificador
	 */
	public String getPasarelaId() {
		return pasarelaId;
	}

	/**
	 * Método para establecer identificador.
	 *
	 * @param identificador
	 *                          identificador a establecer
	 */
	public void setPasarelaId(final String identificador) {
		this.pasarelaId = identificador;
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
	 * Método de acceso a sujetoPasivoNif.
	 *
	 * @return sujetoPasivoNif
	 */
	public String getSujetoPasivoNif() {
		return sujetoPasivoNif;
	}

	/**
	 * Método para establecer sujetoPasivoNif.
	 *
	 * @param sujetoPasivoNif
	 *                            sujetoPasivoNif a establecer
	 */
	public void setSujetoPasivoNif(final String sujetoPasivoNif) {
		this.sujetoPasivoNif = sujetoPasivoNif;
	}

	/**
	 * Método de acceso a sujetoPasivoNombre.
	 *
	 * @return sujetoPasivoNombre
	 */
	public String getSujetoPasivoNombre() {
		return sujetoPasivoNombre;
	}

	/**
	 * Método para establecer sujetoPasivoNombre.
	 *
	 * @param sujetoPasivoNombre
	 *                               sujetoPasivoNombre a establecer
	 */
	public void setSujetoPasivoNombre(final String sujetoPasivoNombre) {
		this.sujetoPasivoNombre = sujetoPasivoNombre;
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

	/**
	 * @return the idTramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite the idTramite to set
	 */
	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the versionTramite
	 */
	public Integer getVersionTramite() {
		return versionTramite;
	}

	/**
	 * @param versionTramite the versionTramite to set
	 */
	public void setVersionTramite(final Integer versionTramite) {
		this.versionTramite = versionTramite;
	}


}
