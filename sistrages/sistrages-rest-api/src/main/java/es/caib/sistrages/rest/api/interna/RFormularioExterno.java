package es.caib.sistrages.rest.api.interna;

/**
 * Formulario externo.
 *
 * @author Indra
 *
 */
public class RFormularioExterno {

    /** Identificador gestor formularios externo. */
    private String identificadorGestorFormularios;

    /** Identificador formulario externo. */
    private String identificadorFormulario;

    /**
     * Método de acceso a identificadorGestorFormularios.
     *
     * @return identificadorGestorFormularios
     */
    public String getIdentificadorGestorFormularios() {
        return identificadorGestorFormularios;
    }

    /**
     * Método para establecer identificadorGestorFormularios.
     *
     * @param identificadorGestorFormularios
     *            identificadorGestorFormularios a establecer
     */
    public void setIdentificadorGestorFormularios(
            String identificadorGestorFormularios) {
        this.identificadorGestorFormularios = identificadorGestorFormularios;
    }

    /**
     * Método de acceso a identificadorFormulario.
     *
     * @return identificadorFormulario
     */
    public String getIdentificadorFormulario() {
        return identificadorFormulario;
    }

    /**
     * Método para establecer identificadorFormulario.
     *
     * @param identificadorFormulario
     *            identificadorFormulario a establecer
     */
    public void setIdentificadorFormulario(String identificadorFormulario) {
        this.identificadorFormulario = identificadorFormulario;
    }

}
