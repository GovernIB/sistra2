package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.io.Serializable;

/**
 * Datos tasa trámite proveniente del Catálogo de Procedimientos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DefinicionTasaTramiteCP implements Serializable {

    /** Identificador tasa en el catalogo de procedimientos. */
    private String identificador;

    /** Título tasa. */
    private String descripcion;

    /** Modelo tasa. */
    private String modelo;

    /** Código tasa. */
    private String codigo;

    /** Concepto tasa. */
    private String concepto;

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
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Método de acceso a codigo.
     * 
     * @return codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para establecer codigo.
     * 
     * @param codigo
     *            codigo a establecer
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

}
