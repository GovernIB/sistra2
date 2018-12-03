package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Configuración modificada a través script de cambio de estado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionModificadaCampo implements Serializable {
    /**
     * Id del campo.
     */
    private String id;
    /**
     * Indica si el campo es solo lectura.
     */
    private TypeSiNo soloLectura = TypeSiNo.NO;

    /**
     * Método de acceso a id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Método para establecer id.
     *
     * @param pId
     *            id a establecer
     */
    public void setId(final String pId) {
        id = pId;
    }

    /**
     * Método de acceso a soloLectura.
     *
     * @return soloLectura
     */
    public TypeSiNo getSoloLectura() {
        return soloLectura;
    }

    /**
     * Método para establecer soloLectura.
     *
     * @param pSoloLectura
     *            soloLectura a establecer
     */
    public void setSoloLectura(final TypeSiNo pSoloLectura) {
        soloLectura = pSoloLectura;
    }

    /**
     * Crea ConfiguracionModificadaCampo.
     *
     * @return ConfiguracionModificadaCampo
     */
    public static ConfiguracionModificadaCampo createNewConfiguracionModificadaCampo() {
        return new ConfiguracionModificadaCampo();
    }

}
