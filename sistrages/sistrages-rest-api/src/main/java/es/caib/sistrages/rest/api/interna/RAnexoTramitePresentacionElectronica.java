package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Presentación electrónica anexo trámite.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RAnexoTramitePresentacionElectronica", description = "Descripcion de RAnexoTramitePresentacionElectronica")
public class RAnexoTramitePresentacionElectronica {

    /** Número instancias. */
	@ApiModelProperty(value = "Número instancias")
    private int instancias;

    /** Lista extensiones permitidas. Si vacía se permiten todas. */
	@ApiModelProperty(value = "Lista extensiones permitidas. Si vacía se permiten todas")
    private List<String> extensiones;

    /** Tamaño máximo (0 sin restricción). */
	@ApiModelProperty(value = "Tamaño máximo (0 sin restricción)")
    private int tamanyoMax;

    /** Tamaño unidad: KB / MB. */
	@ApiModelProperty(value = "Tamaño unidad: KB / MB")
    private String tamanyoUnidad;

    /** Convertir a PDF. */
	@ApiModelProperty(value = "Convertir a PDF")
    private boolean convertirPDF;

    /** Anexar firmado. */
	@ApiModelProperty(value = "Anexar firmado")
    private boolean anexarFirmado;

    /** Firmar electrónicamente. */
	@ApiModelProperty(value = "Firmar electrónicamente")
    private boolean firmar;

    /** Script firmantes. */
	@ApiModelProperty(value = "Script firmantes")
    private RScript scriptFirmantes;

    /** Script validacion. */
	@ApiModelProperty(value = "Script validacion")
    private RScript scriptValidacion;

    /**
     * Método de acceso a instancias.
     *
     * @return instancias
     */
    public int getInstancias() {
        return instancias;
    }

    /**
     * Método para establecer instancias.
     *
     * @param instancias
     *            instancias a establecer
     */
    public void setInstancias(int instancias) {
        this.instancias = instancias;
    }

    /**
     * Método de acceso a extensiones.
     *
     * @return extensiones
     */
    public List<String> getExtensiones() {
        return extensiones;
    }

    /**
     * Método para establecer extensiones.
     *
     * @param extensiones
     *            extensiones a establecer
     */
    public void setExtensiones(List<String> extensiones) {
        this.extensiones = extensiones;
    }

    /**
     * Método de acceso a tamanyoMax.
     *
     * @return tamanyoMax
     */
    public int getTamanyoMax() {
        return tamanyoMax;
    }

    /**
     * Método para establecer tamanyoMax.
     *
     * @param tamanyoMax
     *            tamanyoMax a establecer
     */
    public void setTamanyoMax(int tamanyoMax) {
        this.tamanyoMax = tamanyoMax;
    }

    /**
     * Método de acceso a tamanyoUnidad.
     *
     * @return tamanyoUnidad
     */
    public String getTamanyoUnidad() {
        return tamanyoUnidad;
    }

    /**
     * Método para establecer tamanyoUnidad.
     *
     * @param tamanyoUnidad
     *            tamanyoUnidad a establecer
     */
    public void setTamanyoUnidad(String tamanyoUnidad) {
        this.tamanyoUnidad = tamanyoUnidad;
    }

    /**
     * Método de acceso a convertirPDF.
     *
     * @return convertirPDF
     */
    public boolean isConvertirPDF() {
        return convertirPDF;
    }

    /**
     * Método para establecer convertirPDF.
     *
     * @param convertirPDF
     *            convertirPDF a establecer
     */
    public void setConvertirPDF(boolean convertirPDF) {
        this.convertirPDF = convertirPDF;
    }

    /**
     * Método de acceso a anexarFirmado.
     *
     * @return anexarFirmado
     */
    public boolean isAnexarFirmado() {
        return anexarFirmado;
    }

    /**
     * Método para establecer anexarFirmado.
     *
     * @param anexarFirmado
     *            anexarFirmado a establecer
     */
    public void setAnexarFirmado(boolean anexarFirmado) {
        this.anexarFirmado = anexarFirmado;
    }

    /**
     * Método de acceso a firmar.
     *
     * @return firmar
     */
    public boolean isFirmar() {
        return firmar;
    }

    /**
     * Método para establecer firmar.
     *
     * @param firmar
     *            firmar a establecer
     */
    public void setFirmar(boolean firmar) {
        this.firmar = firmar;
    }

    /**
     * Método de acceso a scriptFirmantes.
     *
     * @return scriptFirmantes
     */
    public RScript getScriptFirmantes() {
        return scriptFirmantes;
    }

    /**
     * Método para establecer scriptFirmantes.
     *
     * @param scriptFirmantes
     *            scriptFirmantes a establecer
     */
    public void setScriptFirmantes(RScript scriptFirmantes) {
        this.scriptFirmantes = scriptFirmantes;
    }

    /**
     * Método de acceso a scriptValidacion.
     *
     * @return scriptValidacion
     */
    public RScript getScriptValidacion() {
        return scriptValidacion;
    }

    /**
     * Método para establecer scriptValidacion.
     *
     * @param scriptValidacion
     *            scriptValidacion a establecer
     */
    public void setScriptValidacion(RScript scriptValidacion) {
        this.scriptValidacion = scriptValidacion;
    }

}
