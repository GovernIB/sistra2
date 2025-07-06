package es.caib.sistrages.core.api.model.types;

public enum TypeUser {

    GESTOR_TRAMITES ( "GT"),
    PERSONAL_CAU("CAU");

    private String valor;

    private TypeUser(final String iValor) {
        this.valor = iValor;
    }

    public static TypeUser fromString(final String text) {
        TypeUser respuesta = null;
        if (text != null) {
            for (final TypeUser b : TypeUser.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;
    }


    @Override
    public String toString() {
        return valor;
    }
}
