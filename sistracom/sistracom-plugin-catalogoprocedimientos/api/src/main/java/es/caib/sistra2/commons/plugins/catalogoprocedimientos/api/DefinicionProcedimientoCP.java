package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.io.Serializable;

/**
 * Datos procedimiento proveniente del Catálogo de Procedimientos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DefinicionProcedimientoCP implements Serializable {

	/** Código procedimiento CP. */
	private String identificador;

	/** Código procedimiento SIA. */
	private String idProcedimientoSIA;

	/** Descripción. */
	private String descripcion;

	/** Código DIR3 órgano responsable. */
	private String organoResponsableDir3;

	/** Indica si el procedimiento es un servicio. */
	private boolean servicio;

	/** Información LOPD. */
	private DefinicionLOPD lopd;

	/** Validacion **/
	private Integer validacion;

	/**
	 * Método de acceso a identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer identificador.
	 *
	 * @param identificador
	 *                          identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * Método de acceso a idProcedimientoSIA.
	 *
	 * @return idProcedimientoSIA
	 */
	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	/**
	 * Método para establecer idProcedimientoSIA.
	 *
	 * @param idProcedimientoSIA
	 *                               idProcedimientoSIA a establecer
	 */
	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	/**
	 * Método de acceso a descripcion.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para establecer descripcion.
	 *
	 * @param descripcion
	 *                        descripcion a establecer
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método de acceso a organoResponsableDir3.
	 *
	 * @return organoResponsableDir3
	 */
	public String getOrganoResponsableDir3() {
		return organoResponsableDir3;
	}

	/**
	 * Método para establecer organoResponsableDir3.
	 *
	 * @param organoResponsableDir3
	 *                                  organoResponsableDir3 a establecer
	 */
	public void setOrganoResponsableDir3(final String organoResponsableDir3) {
		this.organoResponsableDir3 = organoResponsableDir3;
	}

	/**
	 * Método de acceso a servicio.
	 *
	 * @return servicio
	 */
	public boolean isServicio() {
		return servicio;
	}

	/**
	 * Método de acceso a servicio.
	 *
	 * @return servicio
	 */
	public boolean isProcedimiento() {
		return !servicio;
	}

	/**
	 * Método para establecer servicio.
	 *
	 * @param servicio
	 *                     servicio a establecer
	 */
	public void setServicio(final boolean servicio) {
		this.servicio = servicio;
	}

	/**
	 * Método de acceso a lopd.
	 *
	 * @return lopd
	 */
	public DefinicionLOPD getLopd() {
		return lopd;
	}

	/**
	 * Método para establecer lopd.
	 *
	 * @param lopd
	 *                 lopd a establecer
	 */
	public void setLopd(final DefinicionLOPD lopd) {
		this.lopd = lopd;
	}

	/**
	 * @return the validacion
	 */
	public Integer getValidacion() {
		return validacion;
	}

	/**
	 * @param validacion the validacion to set
	 */
	public void setValidacion(Integer validacion) {
		this.validacion = validacion;
	}

	/** Segun ROLSAC1 **/
	public final Integer PUBLICA = 1;
	public final Integer INTERNA = 2;
	public final Integer RESERVA = 3;
	/** BAJA YA NO EXISTE **/
	public final Integer BAJA = 4;

	public boolean isValidacionPublica() {
		return this.validacion != null && this.validacion.compareTo(PUBLICA) == 0;
	}

	public boolean isValidacionInterna() {
		return this.validacion != null && this.validacion.compareTo(INTERNA) == 0;
	}

	public boolean isValidacionReserva() {
		return this.validacion != null && this.validacion.compareTo(RESERVA) == 0;
	}
}
