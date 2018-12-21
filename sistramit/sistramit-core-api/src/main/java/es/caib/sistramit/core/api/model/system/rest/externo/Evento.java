package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistramit.core.api.exception.ErrorJsonException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;

/**
 * La clase Evento (RestApiExternaService).
 */
@SuppressWarnings("serial")
public final class Evento implements Serializable {

	/**
	 * Crea una nueva instancia de Evento.
	 *
	 * @param idSesionTramitacion
	 *            id sesion tramitacion
	 * @param tipoEvento
	 *            tipo evento
	 * @param fecha
	 *            fecha
	 * @param nif
	 *            nif
	 * @param idTramite
	 *            id tramite
	 * @param versionTramite
	 *            version tramite
	 * @param idProcedimientoCP
	 *            id procedimiento CP
	 * @param idProcedimientoSIA
	 *            id procedimiento SIA
	 * @param codigoError
	 *            codigo error
	 * @param descripcion
	 *            descripcion
	 * @param resultado
	 *            resultado
	 * @param trazaError
	 *            traza error
	 * @param detalle
	 *            detalle
	 */
	public Evento(final String idSesionTramitacion, final String tipoEvento, final Date fecha, final String nif,
			final String idTramite, final Integer versionTramite, final String idProcedimientoCP,
			final String idProcedimientoSIA, final String codigoError, final String descripcion, final String resultado,
			final String trazaError, final String detalle) {
		super();
		this.idSesionTramitacion = idSesionTramitacion;
		this.tipoEvento = TypeEvento.fromString(tipoEvento);
		this.fecha = fecha;
		this.nif = nif;
		this.idTramite = idTramite;
		this.versionTramite = versionTramite;
		this.idProcedimientoCP = idProcedimientoCP;
		this.idProcedimientoSIA = idProcedimientoSIA;
		this.codigoError = codigoError;
		this.descripcion = descripcion;
		this.resultado = resultado;
		this.trazaError = trazaError;
		try {
			this.propiedadesEvento = (ListaPropiedades) JSONUtil.fromJSON(detalle, ListaPropiedades.class);
		} catch (final JSONUtilException e) {
			throw new ErrorJsonException(e);
		}
	}

	/**
	 * Crea una nueva instancia de Evento.
	 *
	 * @param tipoEvento
	 *            tipo evento
	 * @param fecha
	 *            fecha
	 * @param codigoError
	 *            codigo error
	 * @param descripcion
	 *            descripcion
	 * @param resultado
	 *            resultado
	 * @param trazaError
	 *            traza error
	 * @param detalle
	 *            detalle
	 */
	public Evento(final String tipoEvento, final Date fecha, final String codigoError, final String descripcion,
			final String resultado, final String trazaError, final String detalle) {
		super();
		this.tipoEvento = TypeEvento.fromString(tipoEvento);
		this.fecha = fecha;
		this.codigoError = codigoError;
		this.descripcion = descripcion;
		this.resultado = resultado;
		this.trazaError = trazaError;
		try {
			this.propiedadesEvento = (ListaPropiedades) JSONUtil.fromJSON(detalle, ListaPropiedades.class);
		} catch (final JSONUtilException e) {
			throw new ErrorJsonException(e);
		}
	}

	/**
	 * Crea una nueva instancia de Evento.
	 */
	public Evento() {
		super();
	}

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
		// fecha de EventoAplicacion
		fecha = pFechaEventoAplicacion;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public String getDescripcion() {
		// descripcion de EventoAplicacion
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
		// descripcion de EventoAplicacion
		descripcion = pDescripcion;
	}

	/**
	 * Obtiene el valor de idSesionTramitacion.
	 *
	 * @return el valor de idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		// Devuelve idSesionTramitacion de evento
		return idSesionTramitacion;
	}

	/**
	 * Obtiene el valor de propiedadesEvento.
	 *
	 * @return el valor de propiedadesEvento
	 */
	public ListaPropiedades getPropiedadesEvento() {
		// Devuelve propiedadesEvento de EventoAplicacion
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
