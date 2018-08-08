package es.caib.sistrages.rest.api.interna;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Componente formulario.
 *
 * @author Indra
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = RComponenteSeccion.class, name = "RComponenteSeccion"),
        @Type(value = RComponenteAviso.class, name = "RComponenteAviso"),
        @Type(value = RComponenteTextbox.class, name = "RComponenteTextbox"),
        @Type(value = RComponenteSelector.class, name = "RComponenteSelector"),
        @Type(value = RComponenteCheckbox.class, name = "RComponenteCheckbox")})
@ApiModel(value = "RComponente", description = "Descripcion de RComponente")
public abstract class RComponente {

    /** Identificador. */
	@ApiModelProperty(value = "Identificador")
    private String identificador;

    /** Tipo. */
	@ApiModelProperty(value = "Tipo")
    private String tipo;

    /** Etiqueta. */
	@ApiModelProperty(value = "Etiqueta")
    private String etiqueta;

    /** Mostrar etiqueta. */
	@ApiModelProperty(value = "Mostrar etiqueta")
    private boolean mostrarEtiqueta;

    /** Alineacion etiqueta: Izquierda (I), Centrada (C), Derecha (D). */
	@ApiModelProperty(value = "Alineacion etiqueta: Izquierda (I), Centrada (C), Derecha (D)")
    private String alineacion;

    /** Número columnas. */
	@ApiModelProperty(value = "Número columnas")
    private int columnas;

    /** Ayuda. */
	@ApiModelProperty(value = "Ayuda")
    private String ayuda;

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
     * Método de acceso a etiqueta.
     *
     * @return etiqueta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Método para establecer etiqueta.
     *
     * @param etiqueta
     *            etiqueta a establecer
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Método de acceso a mostrarEtiqueta.
     *
     * @return mostrarEtiqueta
     */
    public boolean isMostrarEtiqueta() {
        return mostrarEtiqueta;
    }

    /**
     * Método para establecer mostrarEtiqueta.
     *
     * @param mostrarEtiqueta
     *            mostrarEtiqueta a establecer
     */
    public void setMostrarEtiqueta(boolean mostrarEtiqueta) {
        this.mostrarEtiqueta = mostrarEtiqueta;
    }

    /**
     * Método de acceso a alineacion.
     *
     * @return alineacion
     */
    public String getAlineacion() {
        return alineacion;
    }

    /**
     * Método para establecer alineacion.
     *
     * @param alineacion
     *            alineacion a establecer
     */
    public void setAlineacion(String alineacion) {
        this.alineacion = alineacion;
    }

    /**
     * Método de acceso a columnas.
     *
     * @return columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Método para establecer columnas.
     *
     * @param columnas
     *            columnas a establecer
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * Método de acceso a ayuda.
     *
     * @return ayuda
     */
    public String getAyuda() {
        return ayuda;
    }

    /**
     * Método para establecer ayuda.
     *
     * @param ayuda
     *            ayuda a establecer
     */
    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

}
