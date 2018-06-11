package es.caib.sistramit.core.api.model.system;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;

/**
 * Evento de auditoria.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EventoAuditoria implements Serializable {

    /**
     * Inidica si esta asociado a una sesion de tramitacion.
     */
    private String idSesionTramitacion;

    /**
     * Tipo evento aplicacion.
     */
    private TypeEvento tipoEvento;

    /**
     * Fecha evento aplicacion.
     */
    private Date fecha;

    /**
     * Descripción evento aplicacion.
     */
    private String descripcion;

    /**
     * Resultado evento aplicacion (depende del evento).
     */
    private String resultado;

    /**
     * Propiedades evento aplicacion.
     */
    private ListaPropiedades propiedadesEvento;

    /**
     * En caso de error, establece codigo error.
     */
    private String codigoError;

    /**
     * En caso de error, establece traza error.
     */
    private String trazaError;

    /**
     * Método de acceso a tipoEvento de EventoAplicacion.
     *
     * @return tipoEvento
     */
    public TypeEvento getTipoEvento() {
        return tipoEvento;
    }

    /**
     * Método para establecer tipoEvento de EventoAplicacion.
     *
     * @param pTipoEvento
     *            tipoEvento a establecer
     */
    public void setTipoEvento(final TypeEvento pTipoEvento) {
        tipoEvento = pTipoEvento;
    }

    /**
     * Método de acceso a fecha de EventoAplicacion.
     *
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Método para establecer fecha de EventoAplicacion.
     *
     * @param pFechaEventoAplicacion
     *            fecha a establecer
     */
    public void setFecha(final Date pFechaEventoAplicacion) {
        // fecha de EventoAplicacion
        fecha = pFechaEventoAplicacion;
    }

    /**
     * Método de acceso a descripcion de EventoAplicacion.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        // descripcion de EventoAplicacion
        return descripcion;
    }

    /**
     * Método de acceso a resultado de EventoAplicacion.
     *
     * @return resultado
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * Método para establecer descripcion de EventoAplicacion.
     *
     * @param pDescripcion
     *            descripcion a establecer
     */
    public void setDescripcion(final String pDescripcion) {
        // descripcion de EventoAplicacion
        descripcion = pDescripcion;
    }

    /**
     * Método de acceso a idSesionTramitacion.
     *
     * @return idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        // Devuelve idSesionTramitacion de evento
        return idSesionTramitacion;
    }

    /**
     * Método de acceso a propiedadesEvento de EventoAplicacion.
     *
     * @return propiedadesEvento
     */
    public ListaPropiedades getPropiedadesEvento() {
        // Devuelve propiedadesEvento de EventoAplicacion
        return propiedadesEvento;
    }

    /**
     * Método para establecer resultado de EventoAplicacion.
     *
     * @param pResultado
     *            resultado a establecer
     */
    public void setResultado(final String pResultado) {
        resultado = pResultado;
    }

    /**
     * Método para establecer propiedadesEvento de EventoAplicacion.
     *
     * @param pPropiedadesEvento
     *            propiedadesEvento a establecer
     */
    public void setPropiedadesEvento(
            final ListaPropiedades pPropiedadesEvento) {
        propiedadesEvento = pPropiedadesEvento;
    }

    /**
     * Método para establecer idSesionTramitacion.
     *
     * @param pIdSesionTramitacion
     *            idSesionTramitacion a establecer
     */
    public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
        idSesionTramitacion = pIdSesionTramitacion;
    }

    public String getTrazaError() {
        return trazaError;
    }

    public void setTrazaError(String trazaError) {
        this.trazaError = trazaError;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

}
