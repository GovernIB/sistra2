package es.caib.sistrages.core.api.model.types;

public enum TypeNivelEidas {
    BAJO(1),SUSTANCIAL(2), ALTO(3);

    private int valor;

    TypeNivelEidas(final int iValor) {
        this.valor = iValor;
    }

    public static TypeNivelEidas fromString(final int iValor) {
        TypeNivelEidas retorno = null;
        for (final TypeNivelEidas tipo : TypeNivelEidas.values()) {
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
