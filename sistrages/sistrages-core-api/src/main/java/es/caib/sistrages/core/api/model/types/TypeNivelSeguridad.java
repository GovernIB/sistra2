package es.caib.sistrages.core.api.model.types;

public enum TypeNivelSeguridad {

    BAJO(1), SUSTANCIAL(2), SUSTANCIAL_CERTIFICADO(3),ALTO(4);

    private int valor;

    TypeNivelSeguridad(final int iValor) {
        this.valor = iValor;
    }

    public static TypeNivelSeguridad fromValor(final int iValor) {
        TypeNivelSeguridad retorno = null;
        for (final TypeNivelSeguridad tipo : TypeNivelSeguridad.values()) {
            if (tipo != null && tipo.getValor() == iValor) {
                retorno = tipo;
                break;
            }
        }
        return retorno;
    }

    public int getValor() {
        return this.valor;
    }

}
