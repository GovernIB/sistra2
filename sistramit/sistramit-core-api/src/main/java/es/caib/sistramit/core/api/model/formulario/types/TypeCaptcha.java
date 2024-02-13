package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipo de captcha.
 * 
 * @author Indra
 * 
 */
public enum TypeCaptcha {

    /**  Captcha de texto (Código String: t). */
    TEXTO("t");

    /**
     * Valor como string.
     */
    private final String stringValueTipoCaptcha;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private TypeCaptcha(final String value) {
        stringValueTipoCaptcha = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stringValueTipoCaptcha;
    }

}
