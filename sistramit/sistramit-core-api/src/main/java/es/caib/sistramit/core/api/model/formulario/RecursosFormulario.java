package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Recursos Formulario.
 */
@SuppressWarnings("serial")
public class RecursosFormulario implements Serializable {

    /** Atributo imagenes de RecursosFormulario. */
    private List<Imagen> imagenes = new ArrayList<>();

    /**
     * Método de acceso a imagenes.
     *
     * @return el imagenes
     */
    public final List<Imagen> getImagenes() {
        return imagenes;
    }

    /**
     * Método para settear el campo imagenes.
     *
     * @param pImagenes
     *            el imagenes a settear
     */
    public final void setImagenes(final List<Imagen> pImagenes) {
        imagenes = pImagenes;
    }

}
