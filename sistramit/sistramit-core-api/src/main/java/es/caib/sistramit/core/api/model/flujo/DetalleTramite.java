package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Detalle tramite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetalleTramite implements Serializable {

    /** Id trámite. */
    private String idTramite;

    /** Idioma. */
    private String idioma;

    /** Título trámite. */
    private String titulo;

    /** Tipo flujo tramitación. */
    private TypeFlujoTramitacion tipoFlujo;

    /** Usuario autenticado. */
    private UsuarioAutenticadoInfo usuario;

    /** Debug habilitado. */
    private boolean debug;

    /** Fecha recuperacion definicion STG (dd/MM/yyyy hh:mm). */
    private String fechaDefinicion;

    /** Indica si es persistente. */
    private TypeSiNo persistente = TypeSiNo.NO;

    /** Dias persistencia. Si 0 persistencia infinita. */
    private int diasPersistencia;

    /** Estado de los pasos del trámite. */
    private DetallePasos detallePasos;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    public UsuarioAutenticadoInfo getUsuario() {
        return usuario;
    }

    public void setUsuario(final UsuarioAutenticadoInfo usuario) {
        this.usuario = usuario;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(final String idTramite) {
        this.idTramite = idTramite;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    /**
     * Método de acceso a tipoFlujo.
     *
     * @return tipoFlujo
     */
    public TypeFlujoTramitacion getTipoFlujo() {
        return tipoFlujo;
    }

    /**
     * Método para establecer tipoFlujo.
     *
     * @param tipoFlujo
     *            tipoFlujo a establecer
     */
    public void setTipoFlujo(TypeFlujoTramitacion tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
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
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Método de acceso a fechaDefinicion.
     *
     * @return fechaDefinicion
     */
    public String getFechaDefinicion() {
        return fechaDefinicion;
    }

    /**
     * Método para establecer fechaDefinicion.
     *
     * @param fechaDefinicion
     *            fechaDefinicion a establecer
     */
    public void setFechaDefinicion(String fechaDefinicion) {
        this.fechaDefinicion = fechaDefinicion;
    }

    /**
     * Método de acceso a persistente.
     *
     * @return persistente
     */
    public TypeSiNo getPersistente() {
        return persistente;
    }

    /**
     * Método para establecer persistente.
     *
     * @param persistente
     *            persistente a establecer
     */
    public void setPersistente(TypeSiNo persistente) {
        this.persistente = persistente;
    }

    /**
     * Método de acceso a diasPersistencia.
     *
     * @return diasPersistencia
     */
    public int getDiasPersistencia() {
        return diasPersistencia;
    }

    /**
     * Método para establecer diasPersistencia.
     *
     * @param diasPersistencia
     *            diasPersistencia a establecer
     */
    public void setDiasPersistencia(int diasPersistencia) {
        this.diasPersistencia = diasPersistencia;
    }

    /**
     * Método de acceso a tramite.
     *
     * @return tramite
     */
    public DetallePasos getDetallePasos() {
        return detallePasos;
    }

    /**
     * Método para establecer tramite.
     *
     * @param tramite
     *            tramite a establecer
     */
    public void setDetallePasos(DetallePasos tramite) {
        this.detallePasos = tramite;
    }

    /**
     * Imprime detalle tramite.
     *
     * @return Detalle tramite
     */
    public String print() {
        final StringBuffer strb = new StringBuffer(
                ConstantesNumero.N1000 * ConstantesNumero.N8);
        strb.append("\nDETALLE TRAMITE\n");
        strb.append("===============\n");
        strb.append("Usuario:" + getUsuario().print() + "\n");
        strb.append("Idioma:" + getIdioma() + "\n");
        strb.append("Titulo:" + getTitulo() + "\n");
        strb.append("Tipo flujo:" + getTipoFlujo() + "\n");
        strb.append("Tramite:\n" + getDetallePasos().print() + "\n");
        strb.append("===============\n");
        strb.append("FIN DETALLE TRAMITE\n");
        return strb.toString();
    }
}
