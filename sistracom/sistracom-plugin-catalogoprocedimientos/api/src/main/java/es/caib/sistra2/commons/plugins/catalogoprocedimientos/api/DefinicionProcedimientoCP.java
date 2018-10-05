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
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
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
     *            idProcedimientoSIA a establecer
     */
    public void setIdProcedimientoSIA(String idProcedimientoSIA) {
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
     *            descripcion a establecer
     */
    public void setDescripcion(String descripcion) {
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
     *            organoResponsableDir3 a establecer
     */
    public void setOrganoResponsableDir3(String organoResponsableDir3) {
        this.organoResponsableDir3 = organoResponsableDir3;
    }

}
