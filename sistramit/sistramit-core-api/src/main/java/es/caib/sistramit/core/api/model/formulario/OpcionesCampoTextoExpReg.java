package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

/**
 * Opciones particularizadas de configuración de un campo del formulario de tipo
 * expresión regular.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoExpReg implements Serializable {

    /**
     * Expresión regular.
     */
    private String regexp;

    /**
     * Método de acceso a regexp.
     *
     * @return regexp
     */
    public String getRegexp() {
        return regexp;
    }

    /**
     * Método para establecer regexp.
     *
     * @param pRegexp
     *            regexp a establecer
     */
    public void setRegexp(final String pRegexp) {
        regexp = pRegexp;
    }

}
