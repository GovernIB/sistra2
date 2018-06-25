package es.caib.sistrages.rest.api.interna;

/**
 * Plantilla formulario.
 *
 * @author Indra
 *
 */
public class RPlantillaFormulario {

    /** Identificador formateador. */
    private String formateador;

    /** Plantilla (path). */
    private String plantilla;

    /**
     * Método de acceso a formateador.
     *
     * @return formateador
     */
    public String getFormateador() {
        return formateador;
    }

    /**
     * Método para establecer formateador.
     *
     * @param formateador
     *            formateador a establecer
     */
    public void setFormateador(String formateador) {
        this.formateador = formateador;
    }

    /**
     * Método de acceso a plantilla.
     *
     * @return plantilla
     */
    public String getPlantilla() {
        return plantilla;
    }

    /**
     * Método para establecer plantilla.
     *
     * @param plantilla
     *            plantilla a establecer
     */
    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

}
