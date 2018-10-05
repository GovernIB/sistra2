package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.io.Serializable;

/**
 * Datos documento trámite proveniente del Catálogo de Procedimientos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DefinicionDocumentoTramiteCP implements Serializable {

    /** Identificado documento en el catalogo de procedimientos. */
    private String identificador;

    /** Tipo documento: Solicitud (S) / Anexo (A). */
    private TypeDocumentoTramiteCP tipo;

    /** Titulo. */
    private String descripcion;

    /** Obligatorio. */
    private boolean obligatorio;

    /** Url plantilla. */
    private String urlPlantilla;

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
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public TypeDocumentoTramiteCP getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(TypeDocumentoTramiteCP tipo) {
        this.tipo = tipo;
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
     * Método de acceso a obligatorio.
     *
     * @return obligatorio
     */
    public boolean isObligatorio() {
        return obligatorio;
    }

    /**
     * Método para establecer obligatorio.
     *
     * @param obligatorio
     *            obligatorio a establecer
     */
    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    /**
     * Método de acceso a urlPlantilla.
     *
     * @return urlPlantilla
     */
    public String getUrlPlantilla() {
        return urlPlantilla;
    }

    /**
     * Método para establecer urlPlantilla.
     *
     * @param urlPlantilla
     *            urlPlantilla a establecer
     */
    public void setUrlPlantilla(String urlPlantilla) {
        this.urlPlantilla = urlPlantilla;
    }

}
