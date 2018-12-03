package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;

/**
 * Configuración de un campo del formulario de tipo verificación (check).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoVerificacion extends ConfiguracionCampo {

    /**
     * Constructor.
     */
    public ConfiguracionCampoVerificacion() {
        super();
        setTipo(TypeCampo.VERIFICACION);
    }

    /**
     * Valores del campo (cuando esta checked y no checked).
     */
    private ValoresCampoVerificacion valores = new ValoresCampoVerificacion();

    /**
     * Método de acceso a valores.
     *
     * @return valores
     */
    public ValoresCampoVerificacion getValores() {
        return valores;
    }

    /**
     * Método para establecer valores.
     *
     * @param pValores
     *            valores a establecer
     */
    public void setValores(final ValoresCampoVerificacion pValores) {
        valores = pValores;
    }

}
