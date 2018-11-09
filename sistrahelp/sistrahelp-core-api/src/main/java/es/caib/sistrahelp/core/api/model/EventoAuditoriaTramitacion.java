package es.caib.sistrahelp.core.api.model;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistrahelp.core.api.model.comun.ListaPropiedades;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;

/**
 * La clase EventoAuditoriaTramitacion.
 */
@SuppressWarnings("serial")
public final class EventoAuditoriaTramitacion implements Serializable {

	/**
	 * Crea una nueva instancia de EventoAuditoriaTramitacion.
	 */
	public EventoAuditoriaTramitacion() {
		super();
	}

	/**
	 * id.
	 */
	private Long id;

	/**
	 * id sesion tramitacion.
	 */
	private String idSesionTramitacion;

	/**
	 * tipo evento.
	 */
	private TypeEvento tipoEvento;

	/**
	 * fecha.
	 */
	private Date fecha;

	/**
	 * descripcion.
	 */
	private String descripcion;

	/**
	 * resultado.
	 */
	private String resultado;

	/**
	 * propiedades evento.
	 */
	private ListaPropiedades propiedadesEvento;

	/**
	 * codigo error.
	 */
	private String codigoError;

	/**
	 * traza error.
	 */
	private String trazaError;

	/**
	 * nif.
	 */
	private String nif;

	/**
	 * id tramite.
	 */
	private String idTramite;

	/**
	 * version tramite.
	 */
	private Integer versionTramite;

	/**
	 * id procedimiento CP.
	 */
	private String idProcedimientoCP;

	/**
	 * id procedimiento SIA.
	 */
	private String idProcedimientoSIA;

	/**
	 * Obtiene el valor de tipoEvento.
	 *
	 * @return el valor de tipoEvento
	 */
	public TypeEvento getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * Establece el valor de tipoEvento.
	 *
	 * @param pTipoEvento
	 *            el nuevo valor de tipoEvento
	 */
	public void setTipoEvento(final TypeEvento pTipoEvento) {
		tipoEvento = pTipoEvento;
	}

	/**
	 * Obtiene el valor de fecha.
	 *
	 * @return el valor de fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor de fecha.
	 *
	 * @param pFechaEventoAplicacion
	 *            el nuevo valor de fecha
	 */
	public void setFecha(final Date pFechaEventoAplicacion) {
		fecha = pFechaEventoAplicacion;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Obtiene el valor de resultado.
	 *
	 * @return el valor de resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param pDescripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final String pDescripcion) {
		descripcion = pDescripcion;
	}

	/**
	 * Obtiene el valor de idSesionTramitacion.
	 *
	 * @return el valor de idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Obtiene el valor de propiedadesEvento.
	 *
	 * @return el valor de propiedadesEvento
	 */
	public ListaPropiedades getPropiedadesEvento() {
		return propiedadesEvento;
	}

	/**
	 * Establece el valor de resultado.
	 *
	 * @param pResultado
	 *            el nuevo valor de resultado
	 */
	public void setResultado(final String pResultado) {
		resultado = pResultado;
	}

	/**
	 * Establece el valor de propiedadesEvento.
	 *
	 * @param pPropiedadesEvento
	 *            el nuevo valor de propiedadesEvento
	 */
	public void setPropiedadesEvento(final ListaPropiedades pPropiedadesEvento) {
		propiedadesEvento = pPropiedadesEvento;
	}

	/**
	 * Establece el valor de idSesionTramitacion.
	 *
	 * @param pIdSesionTramitacion
	 *            el nuevo valor de idSesionTramitacion
	 */
	public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
		idSesionTramitacion = pIdSesionTramitacion;
	}

	/**
	 * Obtiene el valor de trazaError.
	 *
	 * @return el valor de trazaError
	 */
	public String getTrazaError() {
		return trazaError;
	}

	/**
	 * Establece el valor de trazaError.
	 *
	 * @param trazaError
	 *            el nuevo valor de trazaError
	 */
	public void setTrazaError(final String trazaError) {
		this.trazaError = trazaError;
	}

	/**
	 * Obtiene el valor de codigoError.
	 *
	 * @return el valor de codigoError
	 */
	public String getCodigoError() {
		return codigoError;
	}

	/**
	 * Establece el valor de codigoError.
	 *
	 * @param codigoError
	 *            el nuevo valor de codigoError
	 */
	public void setCodigoError(final String codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de nif.
	 *
	 * @return el valor de nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Establece el valor de nif.
	 *
	 * @param nif
	 *            el nuevo valor de nif
	 */
	public void setNif(final String nif) {
		this.nif = nif;
	}

	/**
	 * Obtiene el valor de idTramite.
	 *
	 * @return el valor de idTramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * Establece el valor de idTramite.
	 *
	 * @param idTramite
	 *            el nuevo valor de idTramite
	 */
	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * Obtiene el valor de versionTramite.
	 *
	 * @return el valor de versionTramite
	 */
	public Integer getVersionTramite() {
		return versionTramite;
	}

	/**
	 * Establece el valor de versionTramite.
	 *
	 * @param version
	 *            el nuevo valor de versionTramite
	 */
	public void setVersionTramite(final Integer version) {
		this.versionTramite = version;
	}

	/**
	 * Obtiene el valor de idProcedimientoCP.
	 *
	 * @return el valor de idProcedimientoCP
	 */
	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	/**
	 * Establece el valor de idProcedimientoCP.
	 *
	 * @param codigoProcedimiento
	 *            el nuevo valor de idProcedimientoCP
	 */
	public void setIdProcedimientoCP(final String codigoProcedimiento) {
		this.idProcedimientoCP = codigoProcedimiento;
	}

	/**
	 * Obtiene el valor de idProcedimientoSIA.
	 *
	 * @return el valor de idProcedimientoSIA
	 */
	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	/**
	 * Establece el valor de idProcedimientoSIA.
	 *
	 * @param codigoSia
	 *            el nuevo valor de idProcedimientoSIA
	 */
	public void setIdProcedimientoSIA(final String codigoSia) {
		this.idProcedimientoSIA = codigoSia;
	}

}
