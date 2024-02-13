package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCaptcha;

import java.io.Serializable;

/**
 * Opciones particularizadas de configuración de un campo captcha.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoCaptcha implements Serializable {

    /** Indica tipo de captcha. */
   private TypeCaptcha tipo = TypeCaptcha.TEXTO;

    /** Indica si muestra resolución por sonido. */
    private TypeSiNo sonido = TypeSiNo.SI;

    /**
     * Obtiene tipo.
     * @return tipo
     */
    public TypeCaptcha getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     * @param tipo a establecer
     */
    public void setTipo(TypeCaptcha tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene si genera sonido.
     * @return Obtiene si genera sonido.
     */
    public TypeSiNo getSonido() {
        return sonido;
    }

    /**
     * Establece si genera sonido.
     * @param sonido Establece si genera sonido.
     */
    public void setSonido(TypeSiNo sonido) {
        this.sonido = sonido;
    }

}
