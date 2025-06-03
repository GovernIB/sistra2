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
     * Indica si el campo es visible.
     */
    private TypeSiNo oculto = TypeSiNo.NO;
    /**
     * Indica si el campo es obligatorio.
     */
    private TypeSiNo obligatorio = TypeSiNo.NO;

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
     * Crea ConfiguracionModificadaCampo.
     *
     * @return ConfiguracionModificadaCampo
     */
    public static ConfiguracionModificadaCampo createNewConfiguracionModificadaCampo() {
        return new ConfiguracionModificadaCampo();
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
     * Obtiene obligatorio.
     * @return obligatorio
     */
    public TypeSiNo getObligatorio() { return obligatorio; }

    /**
     * Establece obligatorio.
     * @param obligatorio obligatorio
     */
    public void setObligatorio(TypeSiNo obligatorio) { this.obligatorio = obligatorio; }

    /**
     * Obtiene visible.
     * @return visible
     */
    public TypeSiNo getOculto() { return oculto; }

    /**
     * Establece visible.
     * @param visible visible
     */
    public void setOculto(TypeSiNo visible) { this.oculto = visible; }


}
