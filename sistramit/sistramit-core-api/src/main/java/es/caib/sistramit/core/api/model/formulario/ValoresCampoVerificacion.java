package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

/**
 * Valores de un campo del formulario de tipo verificación (check).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValoresCampoVerificacion implements Serializable {

    /**
     * Valor cuando esta checked.
     */
    private String checked;

    /**
     * Valor cuando esta no checked.
     */
    private String noChecked;

    /**
     * Método de acceso a checked.
     *
     * @return checked
     */
    public String getChecked() {
        return checked;
    }

    /**
     * Método para establecer checked.
     *
     * @param pChecked
     *            checked a establecer
     */
    public void setChecked(final String pChecked) {
        checked = pChecked;
    }

    /**
     * Método de acceso a noChecked.
     *
     * @return noChecked
     */
    public String getNoChecked() {
        return noChecked;
    }

    /**
     * Método para establecer noChecked.
     *
     * @param pNoChecked
     *            noChecked a establecer
     */
    public void setNoChecked(final String pNoChecked) {
        noChecked = pNoChecked;
    }

}
