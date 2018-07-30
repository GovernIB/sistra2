package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;
import java.util.List;

/**
 * Xml de un formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class XmlFormulario implements Serializable {

    /**
     * Constructor.
     *
     * @param pValores
     *            Valores formulario
     * @param pAccionPersonalizada
     *            Accion personalizada
     *
     */
    public XmlFormulario(final List<ValorCampo> pValores,
            final String pAccionPersonalizada) {
        super();
        accionPersonalizada = pAccionPersonalizada;
        valores = pValores;
    }

    /**
     * Constructor.
     */
    public XmlFormulario() {
        super();
    }

    /**
     * Acción personalizada (en caso de que la tenga).
     */
    private String accionPersonalizada;

    /**
     * Valores campos.
     */
    private List<ValorCampo> valores;

    /**
     * Método de acceso a accionPersonalizada.
     *
     * @return accionPersonalizada
     */
    public String getAccionPersonalizada() {
        return accionPersonalizada;
    }

    /**
     * Método para establecer accionPersonalizada.
     *
     * @param pAccionPersonalizada
     *            accionPersonalizada a establecer
     */
    public void setAccionPersonalizada(final String pAccionPersonalizada) {
        accionPersonalizada = pAccionPersonalizada;
    }

    /**
     * Método de acceso a valores.
     *
     * @return valores
     */
    public List<ValorCampo> getValores() {
        return valores;
    }

    /**
     * Método para establecer valores.
     *
     * @param pValores
     *            valores a establecer
     */
    public void setValores(final List<ValorCampo> pValores) {
        valores = pValores;
    }

}
