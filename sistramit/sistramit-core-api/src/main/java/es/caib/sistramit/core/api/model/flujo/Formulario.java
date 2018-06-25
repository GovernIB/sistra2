package es.caib.sistramit.core.api.model.flujo;

/**
 *
 * Información sobre un formulario de paso rellenar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Formulario extends DocumentoFirmado {

    /**
     * Método para Crea new formulario de la clase Formulario.
     *
     * @return el formulario
     */
    public static Formulario createNewFormulario() {
        return new Formulario();
    }

}
