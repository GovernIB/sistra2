package es.caib.sistra2.commons.pdfcaib.model;
/**
 * @author Indra
 *
 */
public class ConfiguracionFuentes {

    /**Fuente del formulario*/
    private String fuente;
    /**Nombre de la fuente del formulario*/
    private String nombreFuente;
    /**Nombre de la fuente regular del formulario*/
    private String fuenteRegular;
    /**Nombre de la fuente bold del formulario*/
    private String fuenteBold;
    /**Tamaño de la cabecera del formulario*/
    private int tamanioCabecera;
    /**Tamaño de la sección del formulario*/
    private int tamanioSeccion;
    /**Tamaño de la etiqueta del formulario*/
    private int tamanioEtiqueta;
    /**Color de la cabecera del formulario*/
    private String colorCabecera;
    /**Color de la sección del formulario*/
    private String colorSeccion;

    /**
    * @return Devuelve tamanioCabecera
    */
    public int getTamanioCabecera() {
        return tamanioCabecera;
    }
    /**
    * @param tamanioCabecera Modifica tamanioCabecera
    */
    public void setTamanioCabecera(int tamanioCabecera) {
        this.tamanioCabecera = tamanioCabecera;
    }

    /**
    * @return Devuelve tamanioSeccion
    */
    public int getTamanioSeccion() {
        return tamanioSeccion;
    }
    /**
     * @param tamanioSeccion Modifica tamanioSeccion
     */
    public void setTamanioSeccion(int tamanioSeccion) {
        this.tamanioSeccion = tamanioSeccion;
    }
    /**
     * @return Devuelve tamanioEtiqueta
     */
    public int getTamanioEtiqueta() {
        return tamanioEtiqueta;
    }
    /**
     * @param tamanioEtiqueta Modifica tamanioEtiqueta
     */
    public void setTamanioEtiqueta(int tamanioEtiqueta) {
        this.tamanioEtiqueta = tamanioEtiqueta;
    }

    /**
     * @return Devuelve colorCabecera
     */
    public String getColorCabecera() {
        return colorCabecera;
    }

    /**
     * @param colorCabecera Modifica colorCabecera
     */
    public void setColorCabecera(String colorCabecera) {
        this.colorCabecera = colorCabecera;
    }

    /**
     * @return Devuelve colorSeccion
     */
    public String getColorSeccion() {
        return colorSeccion;
    }

    /**
     * @param colorSeccion Modifica colorSeccion
     */
    public void setColorSeccion(String colorSeccion) {
        this.colorSeccion = colorSeccion;
    }    
    
    /**
     * @return Devuelve fuente del formulario
     */
    public String getFuente() {
        return fuente;
    }

    /**
     * @return Devuelve fuente regular
     */
    public String getFuenteRegular() {
        return fuenteRegular;
    }

    /**
     * @return Devuelve fuente bold
     */
    public String getFuenteBold() {
        return fuenteBold;
    }

    /**
     * @param fuente Modifica fuente
     */
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    /**
     * @param nombreFuente Modifica nombreFuente
     */
    public void setNombreFuente(String nombreFuente) {
        this.nombreFuente = nombreFuente;
    }

/**
     * @param fuenteRegular Modifica fuenteRegular
     */
    public void setFuenteRegular(String fuenteRegular) {
        this.fuenteRegular = fuenteRegular;   }

    /**
     * @param fuenteBold Modifica fuenteBold
     */
    public void setFuenteBold(String fuenteBold) {
        this.fuenteBold = fuenteBold;
    }
}
