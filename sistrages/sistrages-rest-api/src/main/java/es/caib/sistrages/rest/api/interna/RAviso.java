package es.caib.sistrages.rest.api.interna;

/**
 *
 * Mensaje aviso.
 *
 * @author Indra
 *
 */
public class RAviso {

    /** Mensaje aviso. */
    private RLiteral mensaje;

    /**
     * Tipo aviso: Todos (TOD), No autenticados (NAU), Autenticados (AUT), Con
     * pago (PAG), Con registro (REG), Con firma (FIR), Lista trámites (LIS)
     */
    private String tipo;

    /** Bloquear. */
    private boolean bloquear;

    /** Fecha inicio (formato YYYYMMDDHHMISS). */
    private String fechaInicio;

    /** Fecha fin (formato YYYYMMDDHHMISS). */
    private String fechaFin;

    /**
     * Lista versiones trámite. Formato: Trámite-Versión separados por “;” (p.e.
     * : TRAM1-1;TRAM2-1).
     */
    private String listaVersiones;

    /**
     * Método de acceso a mensaje.
     *
     * @return mensaje
     */
    public RLiteral getMensaje() {
        return mensaje;
    }

    /**
     * Método para establecer mensaje.
     *
     * @param mensaje
     *            mensaje a establecer
     */
    public void setMensaje(RLiteral mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Método de acceso a bloquear.
     *
     * @return bloquear
     */
    public boolean isBloquear() {
        return bloquear;
    }

    /**
     * Método para establecer bloquear.
     *
     * @param bloquear
     *            bloquear a establecer
     */
    public void setBloquear(boolean bloquear) {
        this.bloquear = bloquear;
    }

    /**
     * Método de acceso a fechaInicio.
     *
     * @return fechaInicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método para establecer fechaInicio.
     *
     * @param fechaInicio
     *            fechaInicio a establecer
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método de acceso a fechaFin.
     *
     * @return fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * Método para establecer fechaFin.
     *
     * @param fechaFin
     *            fechaFin a establecer
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Método de acceso a listaVersiones.
     *
     * @return listaVersiones
     */
    public String getListaVersiones() {
        return listaVersiones;
    }

    /**
     * Método para establecer listaVersiones.
     *
     * @param listaVersiones
     *            listaVersiones a establecer
     */
    public void setListaVersiones(String listaVersiones) {
        this.listaVersiones = listaVersiones;
    }

}
